package cz.muni.fi.pb162.hw02.impl;

import cz.muni.fi.pb162.hw02.Messages;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cz.muni.fi.pb162.hw02.TestUtils.resourcePath;

@ExtendWith(SoftAssertionsExtension.class)
class ApplicationTest {

    private static PrintStream outBcp;
    private static PrintStream errBcp;

    private static ByteArrayOutputStream out = new ByteArrayOutputStream();
    private static PrintStream outStream = new PrintStream(out);
    private static ByteArrayOutputStream err = new ByteArrayOutputStream();
    private static PrintStream errStream = new PrintStream(err);

    @InjectSoftAssertions
    private SoftAssertions softly;

    @BeforeAll
    static void setupClass() {
        outBcp = System.out;
        errBcp = System.err;
        System.setOut(outStream);
        System.setErr(errStream);
    }

    @AfterAll
    static void teardownClass() {
        System.setOut(outBcp);
        System.setErr(errStream);
    }

    @AfterEach
    private void teardown() {
        out.reset();
        err.reset();
    }

    private void assertOutput(Object... expected) {
        String actual = out.toString().trim();
        Arrays.stream(expected).forEach(o -> {
            softly.assertThat(actual).contains(String.valueOf(o));
        });
    }

    private void assertOutputInOrder(Object... expected) {
        String actual = out.toString().trim();
        List<String> actualLines = actual.lines().collect(Collectors.toList());
        List<String> expectedLines = Arrays.stream(expected).map(String::valueOf).collect(Collectors.toList());

        softly.assertThat(actualLines).containsExactlyElementsOf(expectedLines);
    }

    private void assertError(Object... expected) {
        String actual = err.toString().trim();
        Arrays.stream(expected).forEach(o -> {
            softly.assertThat(actual).contains(String.valueOf(o));
        });
    }

    private void execute(String path, String... args) {
        List<String> cmd = new ArrayList<>();
        Collections.addAll(cmd, "--file", path);
        Collections.addAll(cmd, args);

        Application.main(cmd.toArray(new String[0]));
    }


    @Test
    void shouldGetLines() throws URISyntaxException, IOException {
        execute(resourcePath("/lines.txt"), "lines");

        assertOutput("This is a single line!", "This is another one!");
    }

    @Test
    void shouldGetLinesWithImplicitCommand() throws URISyntaxException, IOException {
        execute(resourcePath("/lines.txt"));

        assertOutput("This is a single line!", "This is another one!");
    }

    @Test
    void shouldCountLinesInSingleLineFile() throws URISyntaxException {
        long expected = "This is a single line!".lines().count();
        execute(resourcePath("/line.txt"), "count");

        assertOutput(expected);
    }

    @Test
    void shouldCountLinesInMultiLinesFile() throws URISyntaxException {
        long expected = (
                "This is a single line!\n" +
                        "This is another one!"
        ).lines().count();
        execute(resourcePath("/lines.txt"), "count");

        assertOutput(expected);
    }

    @Test
    void shouldCountUniqueLinesInSingleLineFile() throws URISyntaxException {
        long expected = "This is a single line!".lines().count();
        execute(resourcePath("/line.txt"), "-u", "count");

        assertOutput(expected);
    }

    @Test
    void shouldCountUniqueLinesInMultiLinesFile() throws URISyntaxException {
        long expected = (
                "This is a single line!\n" +
                        "This is another one!"
        ).lines().count();
        execute(resourcePath("/lines.txt"), "-u", "count");

        assertOutput(expected);
    }

    @Test
    void shouldCountUniqueLinesInMultiLinesFileWithDuplicities() throws URISyntaxException {
        long expected = (
                "This is a single line!\n" +
                        "This is another one!\n" +
                        "This one is unique!"
        ).lines().count();
        execute(resourcePath("/duplicities.txt"), "-u", "count");

        assertOutput(expected);
    }

    @Test
    void shouldCountDuplicateLinesInMultiLinesFile() throws URISyntaxException, IOException {
        long expected = (
                "This is a single line!\n" +
                        "This is another one!\n"
        ).lines().count();
        execute(resourcePath("/duplicities.txt"), "-d", "count");

        assertOutput(expected);
    }


    @Test
    void shouldSortFileLines() throws URISyntaxException {
        execute(resourcePath("/mixed.txt"), "-s", "lines");

        assertOutputInOrder(
                "3. line...",
                "A line!",
                "Gotta go fast",
                "This is a single line!",
                "This is a single line.",
                "This is another one!",
                "This one is unique!"
        );
    }

    @Test
    void shouldSortFileLinesWithImplicitCommand() throws URISyntaxException {
        execute(resourcePath("/mixed.txt"), "-s");

        assertOutputInOrder(
                "3. line...",
                "A line!",
                "Gotta go fast",
                "This is a single line!",
                "This is a single line.",
                "This is another one!",
                "This one is unique!"
        );
    }

    @Test
    void shouldCountFileLineSizes() throws URISyntaxException, IOException {
        String[] expected = Stream.of(
                "This is a single line!",
                "This is another one!",
                "This one is unique!"
        )
                .map(s -> s.length() + ": " + s)
                .toArray(String[]::new);

        execute(resourcePath("/duplicities.txt"), "sizes");

        assertOutput((Object[]) expected);
    }


    @Test
    void shouldFindSimilarLinesInDuplicities() throws URISyntaxException {
        testSimilarities(
                "/duplicities.txt", 9,
                pairs("This is a single line!", "This is another one!")
        );
    }

    @Test
    void shouldFindSimilarLinesInMixed() throws URISyntaxException {
        testSimilarities(
                "/mixed.txt", 1,
                pairs("This is a single line!", "This is a single line.")
        );
    }

    @Test
    void shouldNotAllowDuplicateAndUniqueOptionsTogether() throws URISyntaxException {
        execute(resourcePath("/duplicities.txt"), "-d", "-u");
        
        assertError(Messages.INVALID_OPTION_COMBINATION);
    }

    @Test
    void shouldHandleIOError() throws URISyntaxException {
        String path = "/invalid/path/to/file.txt";
        String expectedMessage = String.format(Messages.IO_ERROR, path);
        execute(path);

        assertError(expectedMessage);
    }

    private void testSimilarities(String path, int distance, List<String[]> expected) throws URISyntaxException {
        String[] expectedOutput = expected
                .stream()
                .map(p -> p[0] + " ~= " + p[1])
                .toArray(String[]::new);
        execute(resourcePath(path), "similar");

        assertOutput("Distance of " + distance);
        assertOutput((Object[]) expectedOutput);
    }

    private List<String[]> pairs(String... items) {
        if (items.length % 2 != 0) {
            throw new IllegalArgumentException("Even number of items expected");
        }

        List<String[]> pairs = new ArrayList<>();
        for (int i = 0; i < items.length; i += 2) {
            pairs.add(new String[]{items[i], items[i + 1]});
        }

        return pairs;
    }

}
