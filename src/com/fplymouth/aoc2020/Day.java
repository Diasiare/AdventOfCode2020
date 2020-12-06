package com.fplymouth.aoc2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public abstract class Day {

    protected String getInput(String name) {
        return String.join("\n", getInputAsLines(name));
    }

    protected List<String> getInputAsLines(String name) {
        try {
            return Files.readAllLines(Path.of("input", name));
        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
            return List.of();
        }
    }

    protected void print(int v) {
        print(Integer.toString(v));
    }

    protected void print(String s) {
        System.out.println(s);
    }

    public abstract String part1();
    public String part2() {
        return "Not finished";
    }
}
