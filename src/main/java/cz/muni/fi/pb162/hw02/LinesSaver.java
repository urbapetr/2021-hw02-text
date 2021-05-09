package cz.muni.fi.pb162.hw02;

import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Class that is saving all lines and how many of them are there
 * @author Petr Urbanek
 */
public class LinesSaver {
    private List<String> allLines;

    /**
     * LinesSaver constructor
     * @param allLines all lines from file
     */
    public LinesSaver(List<String> allLines) {
        this.allLines = allLines;
    }

    public int getCounter() {
        return allLines.size();
    }

    /**
     * Filter unique lines
     */
    public void unique() {
        this.allLines = new ArrayList<>(new HashSet<>(allLines));
    }

    /**
     * Sort lines by natural ordering
     */
    public void sort() {
        Collections.sort(allLines);
    }

    /**
     * Filter duplicate lines
     */
    public void duplicates() {
        Set<String> newAllLines = new HashSet<>();
        Set<String> checker = new HashSet<>();
        for (String line : allLines) {
            if (!checker.add(line)){
                newAllLines.add(line);
            }
        }
        allLines.removeAll(allLines);
        allLines.addAll(newAllLines);
    }

    /**
     * Print lines (default operation)
     */
    public void lines() {
        for (String line : allLines) {
            System.out.println(line);
        }
    }

    /**
     * Counts the characters for each line (excluding line separators)
     */
    public void sizes() {
        for (String line : allLines) {
            System.out.println(line.length() + ": " + line);
        }
    }

    /**
     * Lists pairs of most similar (distinct) lines according to Levenshtein distance
     */
    public void similar() {
        int returnDistance = 0;
        String firstLine = "";
        String secondLine = "";
        LevenshteinDistance ld = new LevenshteinDistance();
        for (String line : allLines) {
            for (String line2 : allLines) {
                if (!line.equals(line2)) {
                    int distance = ld.apply(line, line2);
                    if (returnDistance == 0 || (distance < returnDistance)) {
                        returnDistance = distance;
                        firstLine = line;
                        secondLine = line2;
                    }
                }
            }
        }
        System.out.println("Distance of " + returnDistance);
        System.out.println(firstLine + " ~= " + secondLine);
    }
}
