package cz.muni.fi.pb162.hw02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Facade for file reading
 */
public class FileLoader {

    /**
     * Reads file as lines
     * @param path path of the file
     * @return content of read file as lines
     * @throws IOException on IO error
     */
    public List<String> loadAsLines(String path) throws IOException {
        return Files.readAllLines(Paths.get(path));
    }
}
