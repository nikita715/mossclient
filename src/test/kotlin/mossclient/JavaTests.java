package mossclient;

import kotlin.Pair;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;

public class JavaTests {

    private String baseDir = JavaTests.class.getClassLoader().getResource("basefiles").getFile();
    private String solutionDir = JavaTests.class.getClassLoader().getResource("solutionfiles").getFile();

    @Test
    public void t() {
        List<File> bases = Stream.of(1, 2).map(i -> new File(baseDir + "/file" + i + ".txt")).collect(Collectors.toList());
        List<Pair<String, File>> solutions = Stream.iterate(1, i -> i + 1).limit(29)
                .map(i -> new Pair<>("student" + i, new File(solutionDir + "/file" + i + ".txt"))).collect(Collectors.toList());

        String resultUrl = new MossClient(System.getenv("MOSS_ID"), Language.JAVA)
                .submitFiles(bases, true).submitNamedFiles(solutions).getResult();
        assertThat(resultUrl, startsWith("http://moss.stanford.edu/results/"));
        System.out.println(resultUrl);
    }
}
