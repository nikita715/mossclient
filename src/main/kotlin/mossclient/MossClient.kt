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

    private fun sendCommand(command: String, value: String) {
        output.write("$command $value\n".toByteArray())
        output.flush()
    }

    fun directoryBased(value: Boolean = true): MossClient {
        sendCommand("directory", if (value) "1" else "0")
        return this
    }

    fun experimental(value: Boolean = true): MossClient {
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

    private fun readFileBytes(file: File): ByteArray = FileUtils.readFileToByteArray(file)

    private var fileIndex = 1

    @JvmOverloads
    fun submitFile(file: File, isBase: Boolean = false): MossClient {
        submitNamedFile(file.absolutePath to file, isBase)
        submitByteArrayFile(Pair(file.absolutePath, readFileBytes(file)), isBase)
        return this
    }

    @JvmOverloads
    fun submitNamedFile(namedFile: Pair<String, File>, isBase: Boolean = false): MossClient {
        submitByteArrayFile(Pair(namedFile.first, readFileBytes(namedFile.second)), isBase)
        return this
    }

    @JvmOverloads
    fun submitStringFile(namedFile: Pair<String, String>, isBase: Boolean = false): MossClient {
        submitByteArrayFile(Pair(namedFile.first, namedFile.second.toByteArray()), isBase)
        return this
    }

    @JvmOverloads
    fun submitByteArrayFile(namedFile: Pair<String, ByteArray>, isBase: Boolean = false): MossClient {
        val id = if (isBase) 0 else fileIndex++
        val fileInfo = "file $id $language ${namedFile.second.size} ${namedFile.first}\n".toByteArray()
        output.write(fileInfo)
        output.write(namedFile.second)
        return this
    }

    @JvmOverloads
    fun submitFiles(files: List<File>, isBase: Boolean = false): MossClient {
        files.forEach { file ->
            submitFile(file, isBase)
        }
        return this
    }

    @JvmOverloads
    fun submitNamedFiles(files: List<Pair<String, File>>, isBase: Boolean = false): MossClient {
        files.forEach { file ->
            submitNamedFile(file, isBase)
        }
        return this
    }

    @JvmOverloads
    fun submitStringFiles(files: List<Pair<String, String>>, isBase: Boolean = false): MossClient {
        files.forEach { file ->
            submitStringFile(file, isBase)
        }
        return this
    }

    @JvmOverloads
    fun submitByteArrayFiles(files: List<Pair<String, ByteArray>>, isBase: Boolean = false): MossClient {
        files.forEach { file ->
            submitByteArrayFile(file, isBase)
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