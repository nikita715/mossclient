package mossclient;

import kotlin.Pair;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;

public class JavaTests {

    private String baseDir = JavaTests.class.getClassLoader().getResource("basefiles").getFile();
    private String solutionDir = JavaTests.class.getClassLoader().getResource("solutionfiles").getFile();

    private List<File> bases = Stream.of(1, 2).map(i -> new File(baseDir + "/file" + i + ".txt")).collect(toList());
    private List<Pair<String, File>> solutions = Stream.iterate(1, i -> i + 1).limit(29)
            .map(i -> new Pair<>("student" + i, new File(solutionDir + "/file" + i + ".txt"))).collect(toList());
    private String mossId = System.getenv("MOSS_ID");

    @Test
    public void sampleUpload() {
        List<File> bases = Arrays.asList(
                new File(baseDir + "/file1.txt"),
                new File(baseDir + "/file2.txt")
        );

        List<Pair<String, File>> solutions = Arrays.asList(
                new Pair<>("student1", new File(solutionDir + "/file1.txt")),
                new Pair<>("student2", new File(solutionDir + "/file2.txt")),
                new Pair<>("student3", new File(solutionDir + "/file3.txt"))
        );

        try (MossClient mossClient = new MossClient(mossId, MossLanguage.JAVA)) {
            mossClient.submitFiles(bases, true);
            mossClient.submitNamedFiles(solutions);
            String resultUrl = mossClient.getResult();

            assertThat(resultUrl, startsWith("http://moss.stanford.edu/results/"));
            System.out.println(resultUrl);
        }
    }

    @Test
    public void uploadFiles() {
        List<File> bases = Arrays.asList(
                new File(baseDir + "/file1.txt"),
                new File(baseDir + "/file2.txt")
        );

        List<File> solutions = Arrays.asList(
                new File(solutionDir + "/file1.txt"),
                new File(solutionDir + "/file2.txt"),
                new File(solutionDir + "/file3.txt")
        );

        try (MossClient mossClient = new MossClient(mossId, MossLanguage.JAVA)) {
            mossClient.submitFiles(bases, true);
            mossClient.submitFiles(solutions);
            String resultUrl = mossClient.getResult();

            assertThat(resultUrl, startsWith("http://moss.stanford.edu/results/"));
            System.out.println(resultUrl);
        }
    }

    @Test
    public void fullFeaturedUpload() {
        String resultUrl = new MossClient(mossId, MossLanguage.JAVA)
                .experimental()
                .resultSize(5)
                .maxMatches(10)
                .directoryBased(false)
                .comment("important comment")
                .submitFiles(bases, true)
                .submitNamedFiles(solutions)
                .getResult();
        assertThat(resultUrl, startsWith("http://moss.stanford.edu/results/"));
        System.out.println(resultUrl);
    }
}
