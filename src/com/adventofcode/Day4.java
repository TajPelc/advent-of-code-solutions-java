package com.adventofcode;

import org.apache.commons.codec.digest.DigestUtils;

import java.time.Duration;
import java.time.Instant;

/**
 * The Ideal Stocking Stuffer
 */
public class Day4 {
    public static void main(String[] args) {
        String MD5Input = "iwrupvqb";
        Duration t;

        Instant start = Instant.now();

        System.out.println(findMD5ThatStarsWith(MD5Input, "00000"));

        t = Duration.between(start, Instant.now());
        System.out.println("Completed in: " + t.getSeconds() + "." + t.getNano() + "s");

        System.out.println(findMD5ThatStarsWith(MD5Input, "000000"));

        t = Duration.between(start, Instant.now());
        System.out.println("Completed in: " + t.getSeconds() + "." + t.getNano() + "s");
    }

    public static MD5Result findMD5ThatStarsWith(String input, String test) {

        String digest;
        String combined;

        int i = 1;

        do {
            combined = input.concat(String.valueOf(i));
            digest = DigestUtils.md5Hex(combined);
            i++;
        } while (!digest.startsWith(test));

        return new MD5Result(digest, i - 1);
    }
}

class MD5Result {
    String digest;
    Integer iteration;

    public MD5Result(String digest, Integer iteration) {
        this.digest = digest;
        this.iteration = iteration;
    }

    @Override
    public String toString() {
        return "Finding the hash \"" + digest + "\" took " + iteration + " iterations.";
    }
}
