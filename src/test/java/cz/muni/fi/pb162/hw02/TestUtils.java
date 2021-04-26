package cz.muni.fi.pb162.hw02;

import java.net.URISyntaxException;
import java.nio.file.Paths;

public class TestUtils {
    private TestUtils() {
    }

    public static String resourcePath(String path) throws URISyntaxException {
        return Paths.get(TestUtils.class.getResource(path).toURI()).toAbsolutePath().toString();
    }
}