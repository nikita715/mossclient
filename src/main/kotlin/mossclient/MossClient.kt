package mossclient

import org.apache.commons.io.FileUtils
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.Socket

class MossClient(
    mossId: String,
    language: Language
) {

    private val socket: Socket = Socket("moss.stanford.edu", 7690)
    private val output: OutputStream
    private val input: BufferedReader
    private val language = language.ofMoss()

    init {
        socket.keepAlive = true
        output = socket.getOutputStream()
        output.write("moss $mossId\nlanguage ${this.language}\n".toByteArray())
        output.flush()
        input = BufferedReader(InputStreamReader(socket.getInputStream()))
        input.readLine()
    }

    private var i = 1

    fun sendNamedFiles(files: List<NamedFile>): MossClient {
        files.forEach { file -> sendFile(file) }
        return this
    }

    private fun sendCommand(command: String, value: String) {
        output.write("$command $value\n".toByteArray())
        output.flush()
    }

    fun directoryBased(value: Boolean): MossClient {
        sendCommand("directory", if (value) "1" else "0")
        return this
    }

    fun experimental(value: Boolean): MossClient {
        sendCommand("X", if (value) "1" else "0")
        return this
    }

    fun maxMatches(value: Int): MossClient {
        sendCommand("maxmatches", value.toString())
        return this
    }

    fun resultSize(value: Int): MossClient {
        sendCommand("show", value.toString())
        return this
    }

    fun sendFile(namedFile: NamedFile, isBase: Boolean = false) {
        val fileBytes = FileUtils.readFileToByteArray(namedFile.source)
        val id = if (isBase) 0 else i++
        val fileInfo = "file $id $language ${fileBytes.size} ${namedFile.name}\n".toByteArray()
        output.write(fileInfo)
        output.write(fileBytes)
    }

    fun sendFiles(files: List<File>): MossClient = sendNamedFiles(files.map { NamedFile(it.absolutePath, it) })

    fun sendBaseFiles(baseFiles: List<File>): MossClient {
        baseFiles.forEach { file ->
            sendFile(NamedFile(file.absolutePath, file), isBase = true)
        }
        return this
    }

    fun getResult(): String {
        output.write(("query 0 \n").toByteArray())
        val result = input.readLine()
        output.write("end\n".toByteArray())
        output.close()
        input.close()
        socket.close()
        return result
    }
}