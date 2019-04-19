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

    private var fileIndex = 1

    @JvmOverloads
    fun submitFile(file: File, isBase: Boolean = false): MossClient {
        submitNamedFile(NamedFile(file.absolutePath, file), isBase)
        return this
    }

    @JvmOverloads
    fun submitNamedFile(namedFile: NamedFile<File>, isBase: Boolean = false): MossClient {
        val fileBytes = FileUtils.readFileToByteArray(namedFile.source)
        submitByteArrayFile(NamedFile(namedFile.name, fileBytes), isBase)
        return this
    }

    @JvmOverloads
    fun submitStringFile(namedFile: NamedFile<String>, isBase: Boolean = false): MossClient {
        submitByteArrayFile(NamedFile(namedFile.name, namedFile.source.toByteArray()), isBase)
        return this
    }

    @JvmOverloads
    fun submitByteArrayFile(namedFile: NamedFile<ByteArray>, isBase: Boolean = false): MossClient {
        val id = if (isBase) 0 else fileIndex++
        val fileInfo = "file $id $language ${namedFile.source.size} ${namedFile.name}\n".toByteArray()
        output.write(fileInfo)
        output.write(namedFile.source)
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
    fun submitNamedFiles(files: List<NamedFile<File>>, isBase: Boolean = false): MossClient {
        files.forEach { file ->
            submitNamedFile(file, isBase)
        }
        return this
    }

    @JvmOverloads
    fun submitStringFiles(files: List<NamedFile<String>>, isBase: Boolean = false): MossClient {
        files.forEach { file ->
            submitStringFile(file, isBase)
        }
        return this
    }

    @JvmOverloads
    fun submitByteArrayFiles(files: List<NamedFile<ByteArray>>, isBase: Boolean = false): MossClient {
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