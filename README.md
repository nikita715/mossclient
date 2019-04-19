# Mossclient

This is a simple client for Moss plagiarism analyzer

## Usage

Add this in your build.gradle

```
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    ...
    implementation 'com.github.nikita715:mossclient:1.0'
}
```

Client usage example
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

Also look at the [examples](https://github.com/nikita715/mossclient/tree/master/src/test/kotlin/mossclient)
