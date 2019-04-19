package mossclient

import org.hamcrest.CoreMatchers.startsWith
import org.junit.Assert.assertThat
import org.junit.Test
import java.io.File
import kotlin.test.assertFailsWith

open class KotlinTests {

    private val mossId = System.getenv("MOSS_ID")
    private val baseDir = KotlinTests::class.java.classLoader.getResource("basefiles").file
    private val solutionDir = KotlinTests::class.java.classLoader.getResource("solutionfiles").file

    private val bases: List<File> = (1..2).map { File("$baseDir/file$it.txt") }
    private val solutions: List<Pair<String, File>> = (1..29).map { "student$it" to File("$solutionDir/file$it.txt") }

    @Test
    fun fullFeaturedTest() {
        val client = MossClient(mossId, MossLanguage.JAVA)
            .submitFiles(bases, true)
            .submitNamedFiles(solutions)
            .rawOptions("-x -m 123")
            .directoryBased(false)
            .comment("important comment")
            .resultSize(4)
        val resultUrl = client.getResult()
        assertThat(resultUrl, startsWith("http://moss.stanford.edu/results/"))
        println(resultUrl)
    }

    @Test
    fun testRawOptions() {
        val client = MossClient(mossId, MossLanguage.JAVA)
            .submitFiles(bases, true)
            .submitNamedFiles(solutions)
            .rawOptions("-x -m 123 -d -n 150 -c message")
            .rawOptions("   -x -m 123    ")
            .rawOptions(" -x   -m     12 ")
            .rawOptions("")
            .rawOptions("                ")
            .rawOptions("      -c comment")

        assertFailsWith<MossClientException>(
            { client.rawOptions("-m -1") },
            { client.rawOptions("-m 0") },
            { client.rawOptions("-m abc") },
            { client.rawOptions("-n -1") },
            { client.rawOptions("-n 0") },
            { client.rawOptions("-n abc") },
            { client.rawOptions("qwerty") }
        )

        val resultUrl = client.getResult()
        assertThat(resultUrl, startsWith("http://moss.stanford.edu/results/"))
        println(resultUrl)
    }

    @Test
    fun tryToSubmitTwoTimes() {
        val client = MossClient(mossId, MossLanguage.JAVA)
            .submitFiles(bases, true)
            .submitNamedFiles(solutions)
        println(client.getResult())

        assertFailsWith(MossClientException::class) {
            client.getResult()
        }
    }

    @Test
    fun tryToPassWrongOptions() {
        val client = MossClient(mossId, MossLanguage.JAVA)
            .submitFiles(bases, true)
            .submitNamedFiles(solutions)
            .directoryBased(false)
            .resultSize(4)

        assertFailsWith(MossClientException::class) {
            client.rawOptions("wrong options")
        }

        val resultUrl = client.getResult()
        assertThat(resultUrl, startsWith("http://moss.stanford.edu/results/"))
        println(resultUrl)
    }

    @Test
    fun submitStringFiles() {

        val client = MossClient(mossId, MossLanguage.JAVA)

        val file1String = """
public class Range {
    private int leftB;
    private int rightB;
    private Range() {}
    private Range(int leftB, int rightB){
            this.leftB = leftB;
            this.rightB = rightB;
    }
}"""

        val file2String = """
public class Range {

    private int left = 0;
    private int right = 0;

    public Range(int left, int right) {
        if(left > right){
          throw new IllegalArgumentException("WRONG!Lower bound is greater then upper bound");
        }
        this.left = left;
        this.right = right;
    }
}"""

        client.submitStringFiles(
            listOf(
                "student1" to file1String,
                "student2" to file2String
            )
        )

        val resultUrl = client.getResult()
        assertThat(resultUrl, startsWith("http://moss.stanford.edu/results/"))
        println(resultUrl)

    }

    @Test
    fun submitByteArrayFiles() {

        val client = MossClient(mossId, MossLanguage.JAVA)

        val bytes = listOf<Byte>(
            10, 112, 117, 98, 108, 105, 99, 32, 99, 108, 97, 115, 115, 32, 82, 97,
            110, 103, 101, 32, 123, 10, 32, 32, 32, 32, 112, 114, 105, 118, 97, 116, 101, 32, 105, 110,
            116, 32, 108, 101, 102, 116, 66, 59, 10, 32, 32, 32, 32, 112, 114, 105, 118, 97, 116, 101,
            32, 105, 110, 116, 32, 114, 105, 103, 104, 116, 66, 59, 10, 32, 32, 32, 32, 112, 114, 105
        )

        client.submitByteArrayFiles(
            listOf(
                "student1" to ByteArray(bytes.size, bytes::get),
                "student2" to ByteArray(bytes.size, bytes::get)
            )
        )

        val resultUrl = client.getResult()
        assertThat(resultUrl, startsWith("http://moss.stanford.edu/results/"))
        println(resultUrl)

    }

    inline fun <reified T : Throwable> assertFailsWith(vararg actions: () -> Unit) {
        actions.forEach {
            assertFailsWith(T::class, it)
        }
    }

}
