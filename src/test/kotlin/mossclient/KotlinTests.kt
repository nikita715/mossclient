package mossclient

import org.hamcrest.CoreMatchers.startsWith
import org.junit.Assert.assertThat
import org.junit.Test
import java.io.File

class KotlinTests {

    private val baseDir = KotlinTests::class.java.classLoader.getResource("basefiles").file
    private val solutionDir = KotlinTests::class.java.classLoader.getResource("solutionfiles").file

    @Test
    fun bigUploadTest() {
        val bases: List<File> = (1..2).map { File("$baseDir/file$it.txt") }
        val files: List<Pair<String, File>> = (1..29).map { "student$it" to File("$solutionDir/file$it.txt") }

        val resultUrl =
            MossClient(System.getenv("MOSS_ID"), Language.JAVA).submitFiles(bases, true).submitNamedFiles(files)
            .maxMatches(12).experimental(true).directoryBased(false).resultSize(15).getResult()

        assertThat(resultUrl, startsWith("http://moss.stanford.edu/results/"))
        println(resultUrl)
    }
}
