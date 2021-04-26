package cz.muni.fi.pb162.hw02;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static cz.muni.fi.pb162.hw02.TestUtils.resourcePath;
import static org.assertj.core.api.Assertions.assertThat;

class FileLoaderTest {

    private FileLoader fileLoader;

    @BeforeEach
    void setup() {
        fileLoader = new FileLoader();
    }

    @Test
    void shouldReadSingleLineFileAsLines() throws URISyntaxException, IOException {
        List<String> lines = fileLoader.loadAsLines(resourcePath("/line.txt"));
        assertThat(lines).containsExactly("This is a single line!");
    }
    @Test
    void shouldReadMultiLineFileAsLines() throws URISyntaxException, IOException {
        List<String> lines = fileLoader.loadAsLines(resourcePath("/lines.txt"));
        assertThat(lines).containsExactly("This is a single line!", "This is another one!");
    }

}
