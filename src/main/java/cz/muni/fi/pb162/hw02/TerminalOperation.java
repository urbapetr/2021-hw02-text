package cz.muni.fi.pb162.hw02;

import java.util.Arrays;

/**
 * Terminal Operations
 */
public enum TerminalOperation {
    LINES,
    COUNT,
    SIZES,
    SIMILAR;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    /**
     * Returns {@link TerminalOperation} for given name in case insensitive manner
     * @param name name of the operation
     * @return {@link TerminalOperation} instance
     */
    public static TerminalOperation forName(String name) {
        return Arrays
                .stream(values())
                .filter(o -> o.name().equalsIgnoreCase(name))
                .findAny()
                .orElseThrow();
    }
}
