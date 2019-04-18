package mossclient

import org.junit.Test
import java.io.File

class Tests {

    private val baseDir = Tests::class.java.classLoader.getResource("basefiles").file
    private val solutionDir = Tests::class.java.classLoader.getResource("solutionfiles").file

    @Test
    fun namedFileSubmission() {
        val bases = (1..2).map { File("$baseDir/file$it.txt") }
        val files = (1..29).map { NamedFile("student$it", File("$solutionDir/file$it.txt")) }
        val message = MossClient(System.getenv("MOSS_ID"), Language.JAVA).sendBaseFiles(bases).sendNamedFiles(files)
            .maxMatches(12).experimental(true).directoryBased(false).resultSize(15).getResult()
        println(message)
    }
}
