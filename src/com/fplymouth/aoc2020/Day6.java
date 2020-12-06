package com.fplymouth.aoc2020;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Day6 extends Day {
    public String part1() {
        String[] groups = getInput("Day6.txt").split("\n\n");
        return "" + Arrays.stream(groups).mapToInt(this::countForGroup)
            .sum();
    }

    private int countForGroup(String group) {
        Set<Character> seen = new HashSet<>();
        for (var person: group.split("\n")) {
            for (char input: person.toCharArray()) {
                seen.add(input);
            }
        }
        return seen.size();
    }

    public String part2() {
        String[] groups = getInput("Day6.txt").split("\n\n");
        return "" + Arrays.stream(groups).mapToInt(this::countForGroup2)
            .sum();
    }

    private int countForGroup2(String group) {
        return Arrays.stream(group.split("\n"))
            .map(this::toSet)
            .reduce((s1, s2) -> {
                var s = new HashSet<>(s1);
                s.retainAll(s2);
                return s;
            })
            .get().size();
    }

    private Set<Character> toSet(String person) {
        Set<Character> out = new HashSet<>();
        for (char input: person.toCharArray()) {
            out.add(input);
        }
        return out;
    }
}
