package com.fplymouth.aoc2020;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Day5 extends Day {
    public String part1() {
        List<String> passes = getInputAsLines("Day5.txt");
        int maxId = 0;
        for (var pass: passes) {
            int id = calcId(pass);
            maxId = Math.max(maxId, id);
        }

        return Integer.toString(maxId);
    }

    public String part2() {
        List<String> passes = getInputAsLines("Day5.txt");
        List<Integer> ids = passes.stream().map(this::calcId).collect(Collectors.toList());
        Collections.sort(ids);
        for (int i = 0; i < ids.size(); i++) {
            int a = ids.get(i);
            int b = ids.get(i + 1);
            if (a + 1 < b) {
                return Integer.toString(a + 1);
            }
        }
        return "Failure";
    }


    private int calcId(String pass) {
        String rowSelect = pass.substring(0, 7);
        String colSelect = pass.substring(7);
        int row = findRow(rowSelect);
        int col = findCol(colSelect);
        return row * 8 + col;
    }

    private int findRow(String first8) {
        int min = 0;
        int max = 128;
        for (char c: first8.toCharArray()) {
            int diff = (max - min)/2;
            if (c == 'F') {
                max = max - diff;
            } else {
                min = min + diff;
            }
        }
        return min;
    }

    private int findCol(String last3) {
        int min = 0;
        int max = 8;
        for (char c: last3.toCharArray()) {
            int diff = (max - min)/2;
            if (c == 'L') {
                max = max - diff;
            } else {
                min = min + diff;
            }
        }
        return min;
    }
}
