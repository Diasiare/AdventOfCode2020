package com.fplymouth.aoc2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Day19 extends Day {
    public String part1() {
        List<String> input = getInputAsLines("Day19-rules.txt");
        List<String> messages = getInputAsLines("Day19-messages.txt");
        Map<Integer, String> rules = new HashMap<>();
        for (String line: input) {
            String[] s = line.split(":");
            rules.put(Integer.parseInt(s[0]), s[1].trim());
        }

        String rule0 = rules.get(0);
        String regex = toRegex(rule0, rules);
        Pattern p = Pattern.compile(regex);
        int count = 0;
        for (String message: messages) {
            count += p.matcher(message).matches() ? 1: 0;
        }
        return Integer.toString(count);
    }

    private String toRegex(String rule, Map<Integer, String> rules) {
        String[] parts = rule.split("\\|");
        if (parts.length > 1) {
          return "(" + toRegex(parts[0].trim(), rules) + "|" + toRegex(parts[1].trim(), rules) + ")";
        } else {
            String input = parts[0];
            if (input.contains("\"")) {
                return input.replaceAll("\"", "");
            } else {
                String out = "";
                for (String s: input.split(" ")) {
                    out += toRegex(rules.get(Integer.parseInt(s)), rules);
                }
                return out;
            }
        }
    }

    public String part2() {
        List<String> input = getInputAsLines("Day19-rules.txt");
        List<String> messages = getInputAsLines("Day19-messages.txt");
        Map<Integer, String> rules = new HashMap<>();
        for (String line: input) {
            String[] s = line.split(":");
            rules.put(Integer.parseInt(s[0]), s[1].trim());
        }
        // This relies on special knowledge that:
        // 0: 8 11
        // 8: 42 | 42 8
        // 11: 42 31 | 42 11 31
        // which describes a sequence matching 42 n times then 31 m times where:
        // n > 2 and m > 1
        // and n > m

        String rule42expr = toRegex(rules.get(42), rules);
        String rule31expr = toRegex(rules.get(31), rules);
        int maxLength = messages.stream().mapToInt(String::length).max().getAsInt();
        List<Pattern> p = new ArrayList<>();
        // Assume rule 31 and 42 always match at least one character.
        for (int i = 2; i < maxLength; i++) {
            p.add(Pattern.compile(rule42expr + "{" + i +"}" + rule31expr + "{1," + (i - 1) + "}"));
        }

        int count = 0;
        for (String message: messages) {
            boolean found = false;
            for (Pattern pattern: p) {
                if (pattern.matcher(message).matches()) {
                    found = true;
                    break;
                }
            }
            count += found ? 1: 0;
        }

        
        return Integer.toString(count);
    }
}
