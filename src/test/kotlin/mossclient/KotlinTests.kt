package mossclient

import org.hamcrest.CoreMatchers.startsWith
import org.junit.Assert.assertThat
import org.junit.Test
import java.io.File
import kotlin.test.assertFailsWith

open class KotlinTests {

    private val baseDir = KotlinTests::class.java.classLoader.getResource("basefiles").file
    private val solutionDir = KotlinTests::class.java.classLoader.getResource("solutionfiles").file

    private val bases: List<File> = (1..2).map { File("$baseDir/file$it.txt") }
    private val solutions: List<Pair<String, File>> = (1..29).map { "student$it" to File("$solutionDir/file$it.txt") }

    @Test
    fun bigUploadTest() {
        val client = MossClient(System.getenv("MOSS_ID"), MossLanguage.JAVA)
            .submitFiles(bases, true)
            .submitNamedFiles(solutions)
            .rawOptions("-x -m 123")
            .directoryBased(false)
            .resultSize(4)
        val resultUrl = client.getResult()
        assertThat(resultUrl, startsWith("http://moss.stanford.edu/results/"))
        println(resultUrl)
    }

    @Test
    fun tryToSubmitTwoTimes() {
        val resultSize = MossClient(System.getenv("MOSS_ID"), MossLanguage.JAVA)
            .submitFiles(bases, true)
            .submitNamedFiles(solutions)
        resultSize.getResult()

        assertFailsWith(MossClientException::class) {
            resultSize.getResult()
        }
    }

    @Test
    fun tryToPassWrongOptions() {
        val client = MossClient(System.getenv("MOSS_ID"), MossLanguage.JAVA)
            .submitFiles(bases, true)
            .submitNamedFiles(solutions)
            .directoryBased(false)
            .resultSize(4)

        assertFailsWith(MossClientException::class) {
            client.rawOptions("wrong options")
        }

        assertThat(client.getResult(), startsWith("http://moss.stanford.edu/results/"))
    }


}
