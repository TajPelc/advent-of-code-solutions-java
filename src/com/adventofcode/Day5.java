package com.adventofcode;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Doesn't He Have Intern-Elves For This?
 */
public class Day5 {
    public static void main(String[] args) {
        final String fileName = "sources/Day5Input.txt";

        StringAnalyzer ridiculousAnalyzer = new StringAnalyzer();
        EvenBetterStringAnalyzer properAnalyzer = new EvenBetterStringAnalyzer();

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(line -> {
                ridiculousAnalyzer.analyze(line);
                properAnalyzer.analyze(line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(ridiculousAnalyzer.getReport());
        System.out.println(properAnalyzer.getReport());
    }
}

/**
 * Realizing the error of his ways, Santa has switched to a better model of determining whether a string is naughty or nice.
 * None of the old rules apply, as they are all clearly ridiculous.
 */
class EvenBetterStringAnalyzer extends StringAnalyzer {
    @Override
    public void analyze(String line) {
        Boolean hasRepeats = false;
        Boolean hasSandwitched = false;
        char[] chars = line.toCharArray();

        for (int i = 1; i < chars.length; i++) {
            char previous = chars[i-1];
            char current = chars[i];
            char next = ' ';
            int countMatches;

            if (i < chars.length - 1) {
                next = chars[i+1];
            }

            countMatches = StringUtils.countMatches(line, "" + previous + current);

            if (countMatches > 1 && !(previous == current && current == next)) {
                hasRepeats = true;
            }

            if (previous == next) {
                hasSandwitched = true;
            }

            if (hasRepeats && hasSandwitched) {
                numberOfNice += 1;
                break;
            }
        }

        if (!hasRepeats || !hasSandwitched) {
            numberOfNaughty +=1;
        }
    }
}

/**
 * Santa needs help figuring out which strings in his text file are naughty or nice.
 */
class StringAnalyzer {
    Integer numberOfNaughty = 0;
    Integer numberOfNice    = 0;

    public void analyze(String line) {
        Integer vowels = (line.length() - line.replaceAll("a|e|i|o|u", "").length());
        Integer doubleLetters = line.length() - line.replaceAll("(\\w)\\1+", "").length();

        if (
            vowels >= 3
                && doubleLetters > 0
                && !line.contains("ab")
                && !line.contains("cd")
                && !line.contains("pq")
                && !line.contains("xy")
            ) {
            numberOfNice +=1;
        } else {
            numberOfNaughty +=1;
        }
    }

    public String getReport() {
        return "Found " + numberOfNaughty + " naughty and " + numberOfNice + " nice strings.";
    }
}
