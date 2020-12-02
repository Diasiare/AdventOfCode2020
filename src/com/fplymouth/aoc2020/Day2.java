package com.fplymouth.aoc2020;

import java.util.List;

public class Day2 extends Day {
    public String part1() {
        List<String> lines = getInputAsLines("Day2.txt");
        int count = 0;
        for (String line: lines) {
            String[] parts = line.split(": ");
            count += checkRule(parts[0], parts[1].trim()) ? 1: 0;
        }
        return "" + count;
    }

    private boolean checkRule(String rule, String pwd) {
        String[] s1 = rule.split(" ");
        char letter = s1[1].charAt(0);
        String[] counts = s1[0].split("-");
        int min = Integer.parseInt(counts[0]);
        int max = Integer.parseInt(counts[1]);
        int count = 0;
        for (int i = 0; i < pwd.length(); i++) {
            char c = pwd.charAt(i);
            count += c == letter ? 1 : 0;
        }
        return min <= count && count <= max;
    }

    public String part2() {
        List<String> lines = getInputAsLines("Day2.txt");
        int count = 0;
        for (String line: lines) {
            String[] parts = line.split(": ");
            count += checkRule2(parts[0], parts[1].trim()) ? 1: 0;
        }
        return "" + count;
    }

    private boolean checkRule2(String rule, String pwd) {
        String[] s1 = rule.split(" ");
        char letter = s1[1].charAt(0);
        String[] counts = s1[0].split("-");
        int index1 = Integer.parseInt(counts[0]);
        int index2 = Integer.parseInt(counts[1]);
        return (pwd.length() >= index1 && pwd.charAt(index1 - 1) == letter) ^ (pwd.length() >= index2 && pwd.charAt(index2 - 1) == letter);
    }
}
