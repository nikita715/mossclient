package mossclient;

import kotlin.Pair;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;

public class JavaTests {

    private String baseDir = JavaTests.class.getClassLoader().getResource("basefiles").getFile();
    private String solutionDir = JavaTests.class.getClassLoader().getResource("solutionfiles").getFile();

    private List<File> bases = Stream.of(1, 2).map(i -> new File(baseDir + "/file" + i + ".txt")).collect(Collectors.toList());
    private List<Pair<String, File>> solutions = Stream.iterate(1, i -> i + 1).limit(29)
            .map(i -> new Pair<>("student" + i, new File(solutionDir + "/file" + i + ".txt"))).collect(Collectors.toList());

    @Test
    public void smallUploadTest() {
        List<File> bases = Arrays.asList(
                new File(baseDir + "/file1.txt"),
                new File(baseDir + "/file2.txt")
        );

        List<Pair<String, File>> solutions = Arrays.asList(
                new Pair<>("student1", new File(solutionDir + "/file1.txt")),
                new Pair<>("student2", new File(solutionDir + "/file2.txt")),
                new Pair<>("student3", new File(solutionDir + "/file3.txt"))
        );

        try (MossClient mossClient = new MossClient(System.getenv("MOSS_ID"), MossLanguage.JAVA)) {
            mossClient.submitFiles(bases, true);
            mossClient.submitNamedFiles(solutions);
            String resultUrl = mossClient.getResult();

            assertThat(resultUrl, startsWith("http://moss.stanford.edu/results/"));
            System.out.println(resultUrl);
        }

    }

    @Test
    public void bigUploadTest() {
        String resultUrl = new MossClient(System.getenv("MOSS_ID"), MossLanguage.JAVA)
                .experimental().resultSize(5).maxMatches(10)
                .submitFiles(bases, true).submitNamedFiles(solutions).getResult();
        assertThat(resultUrl, startsWith("http://moss.stanford.edu/results/"));
        System.out.println(resultUrl);
    }
}
