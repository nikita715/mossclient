[![Build Status](https://travis-ci.com/nikita715/mossclient.svg?branch=master)](https://travis-ci.com/nikita715/mossclient)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/53df0c7ed2014c1bb4d846b2403d02e3)](https://www.codacy.com/app/nikita715/mossclient?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=nikita715/mossclient&amp;utm_campaign=Badge_Grade)
# Mossclient

This is a simple client for [Moss](http://theory.stanford.edu/~aiken/moss/) plagiarism analyzer

## Usage

1. Obtain the moss id by following the [Moss](http://theory.stanford.edu/~aiken/moss/) registration instructions. You will find the id in the middle of the submision script in your mailbox.

2. Add this in your build.gradle

```
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    ...
    implementation 'com.github.nikita715:mossclient:1.1'
}
```

3. Submit files for analysis
```
List<File> bases = Arrays.asList(
        new File("path/to/basefile1"),
        new File("path/to/basefile2")
);

List<Pair<String, File>> solutions = Arrays.asList(
        new Pair<>("student1", new File("path/to/solution1")),
        new Pair<>("student2", new File("path/to/solution2")),
        new Pair<>("student3", new File("path/to/solution3"))
);

String mossId = "12345678"

MossClient mossClient = new MossClient(mossId, Language.JAVA);
mossClient.submitFiles(bases, true);
mossClient.submitNamedFiles(solutions);
String resultUrl = mossClient.getResult();
```

4. Look at the analysis result by the `resultUrl`

<img src="https://github.com/nikita715/mossclient/blob/master/docs/MossScreenshot.jpg"/>

Also look at the [examples](https://github.com/nikita715/mossclient/tree/master/src/test/kotlin/mossclient)
