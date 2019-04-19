package mossclient

import org.apache.commons.io.FileUtils
import java.io.BufferedReader
import java.io.Closeable
import java.io.File
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.Socket

/**
 * Simple moss client
 * @author Nikita Stepochkin
 */
class MossClient(
    mossId: String,
    language: MossLanguage
) : Closeable {

    private val socket: Socket = Socket("moss.stanford.edu", 7690)
    private val output: OutputStream
    private val input: BufferedReader
    private val language = language.ofMoss()
    private var stage: MossClientStage
    private var reportComment: String = ""
    private var fileIndex = 1

    /**
     * Initialize the client
     */
    init {
        socket.keepAlive = true
        output = socket.getOutputStream()
        output.write("moss $mossId\nlanguage ${this.language}\n".toByteArray())
        output.flush()
        input = BufferedReader(InputStreamReader(socket.getInputStream()))
        input.readLine()
        stage = MossClientStage.WAITING
    }

    /**
     * Send raw moss options like in terminal
     */
    fun rawOptions(commands: String): MossClient {
        var index = 0
        val separatedCommands = commands.split(" ")
        while (index < separatedCommands.size) {
            val command: String = separatedCommands[index++]
            when (command) {
                "-d" -> {
                    sendCommand("directory", "1")
                }
                "-x" -> {
                    sendCommand("X", "1")
                }
                "-m" -> {
                    if (index >= separatedCommands.size) parsingException()
                    sendCommand("maxmatches", separatedCommands[index++])
                }
                "-n" -> {
                    if (index >= separatedCommands.size) parsingException()
                    sendCommand("show", separatedCommands[index++])
                }
                "-c" -> {
                    if (index >= separatedCommands.size) parsingException()
                    reportComment = separatedCommands[index++]
                }
                else -> parsingException()
            }
        }
        return this
    }

    /**
     * The -d option
     */
    @JvmOverloads
    fun directoryBased(value: Boolean = true): MossClient {
        sendCommand("directory", if (value) "1" else "0")
        return this
    }

    /**
     * The -x option
     */
    @JvmOverloads
    fun experimental(value: Boolean = true): MossClient {
        sendCommand("X", if (value) "1" else "0")
        return this
    }

    /**
     * The -m option
     */
    fun maxMatches(value: Int): MossClient {
        sendCommand("maxmatches", value.toString())
        return this
    }


    /**
     * The -c option
     */
    fun comment(value: String): MossClient {
        reportComment = value
        return this
    }

    /**
     * The -n option
     */
    fun resultSize(value: Int): MossClient {
        sendCommand("show", value.toString())
        return this
    }

    private fun sendCommand(command: String, value: String) {
        assertActive()
        output.write("$command $value\n".toByteArray())
        output.flush()
    }

    /**
     * Submit a file
     */
    @JvmOverloads
    fun submitFile(file: File, isBase: Boolean = false): MossClient {
        submitByteArrayFile(Pair(file.absolutePath, readFileBytes(file)), isBase)
        return this
    }

    /**
     * Submit a file with the corresponding name
     */
    @JvmOverloads
    fun submitNamedFile(namedFile: Pair<String, File>, isBase: Boolean = false): MossClient {
        submitByteArrayFile(Pair(namedFile.first, readFileBytes(namedFile.second)), isBase)
        return this
    }

    /**
     * Submit a string file with the corresponding name
     */
    @JvmOverloads
    fun submitStringFile(namedFile: Pair<String, String>, isBase: Boolean = false): MossClient {
        submitByteArrayFile(Pair(namedFile.first, namedFile.second.toByteArray()), isBase)
        return this
    }

    /**
     * Submit a byte array file with the corresponding name
     */
    @JvmOverloads
    fun submitByteArrayFile(namedFile: Pair<String, ByteArray>, isBase: Boolean = false): MossClient {
        assertActive()
        val id = if (isBase) 0 else fileIndex++
        val fileInfo = "file $id $language ${namedFile.second.size} ${namedFile.first}\n".toByteArray()
        output.write(fileInfo)
        output.write(namedFile.second)
        return this
    }

    /**
     * Submit list of files
     */
    @JvmOverloads
    fun submitFiles(files: List<File>, isBase: Boolean = false): MossClient {
        files.forEach { file ->
            submitFile(file, isBase)
        }
        return this
    }

    /**
     * Submit list of files with the corresponding names
     */
    @JvmOverloads
    fun submitNamedFiles(files: List<Pair<String, File>>, isBase: Boolean = false): MossClient {
        files.forEach { file ->
            submitNamedFile(file, isBase)
        }
        return this
    }

    /**
     * Submit list of string files with the corresponding names
     */
    @JvmOverloads
    fun submitStringFiles(files: List<Pair<String, String>>, isBase: Boolean = false): MossClient {
        files.forEach { file ->
            submitStringFile(file, isBase)
        }
        return this
    }

    /**
     * Submit list of byte array files with the corresponding names
     */
    @JvmOverloads
    fun submitByteArrayFiles(files: List<Pair<String, ByteArray>>, isBase: Boolean = false): MossClient {
        files.forEach { file ->
            submitByteArrayFile(file, isBase)
        }
        return this
    }

    private fun assertActive() {
        if (stage != MossClientStage.WAITING) terminatedException()
    }

    private fun terminatedException(): Nothing = throw MossClientException("The client is terminated.")

    private fun parsingException(): Nothing = throw MossClientException("Unable to parse the commands.")

    private fun readFileBytes(file: File): ByteArray = FileUtils.readFileToByteArray(file)

    /**
     * Execute and terminate the client
     * @return link to the analysis result
     */
    fun getResult(): String {
        assertActive()
        output.write(("query 0 $reportComment\n").toByteArray())
        val result = input.readLine()
        output.write("end\n".toByteArray())
        close()
        return result
    }

    /**
     * Close the connections with moss
     */
    override fun close() {
        stage = MossClientStage.TERMINATED
        output.close()
        input.close()
        socket.close()
    }
}
