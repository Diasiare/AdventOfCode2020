package com.fplymouth.aoc2020;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day1 extends Day {
 
    
    public String part1() {
        List<String> lines =  getInputAsLines("Day1.txt");
        Set<Integer> seen = new HashSet<>();

        for (var line: lines) {
            int asNum = Integer.parseInt(line);
            if (seen.contains(2020 - asNum)) {
                return Integer.toString(asNum * (2020 - asNum));
            }
            seen.add(asNum);
        }
        return "Failure";
    }

    public String part2() {
        List<String> lines =  getInputAsLines("Day1.txt");
        Set<Integer> seen = new HashSet<>();

        for (var line: lines) {
            int asNum = Integer.parseInt(line);
            seen.add(asNum);
        }

        for (int i = 0; i < lines.size(); i++) {
            int num1 = Integer.parseInt(lines.get(i));
            for (int j = i + 1; j < lines.size(); j++) {
                int num2 = Integer.parseInt(lines.get(j));
                if (seen.contains(2020 - num1 - num2)) {
                    return Integer.toString(num1 * num2 * (2020 - num1 - num2));
                }
            }
        }
        return "Failure";
    }
}
