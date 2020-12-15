package com.fplymouth.aoc2020;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day15 extends Day {
    private static final String INPUT = "19,0,5,1,10,13";

    public String part1() {
        List<Integer> input = Arrays.stream(INPUT.split(",")).mapToInt(Integer::parseInt)
            .boxed().collect(Collectors.toList());
        
        Map<Integer, Integer> lastSeen = new HashMap<>(); 
        for (int i = 0; i < input.size() - 1; i++) {
            lastSeen.put(input.get(i), i);
        }

        int lastSpoken = input.get(input.size() - 1);
        for (int i = input.size(); i < 2020; i++) {
            int nextValue;
            if (!lastSeen.containsKey(lastSpoken)) {
                nextValue = 0;
            } else {
                nextValue = i - lastSeen.get(lastSpoken) - 1;
            }
            lastSeen.put(lastSpoken, i - 1);
            lastSpoken = nextValue;
        }
        return Integer.toString(lastSpoken);

    }

    public String part2() {
        List<Integer> input = Arrays.stream(INPUT.split(",")).mapToInt(Integer::parseInt)
            .boxed().collect(Collectors.toList());
        
        Map<Integer, Integer> lastSeen = new HashMap<>(); 
        for (int i = 0; i < input.size() - 1; i++) {
            lastSeen.put(input.get(i), i);
        }

        int lastSpoken = input.get(input.size() - 1);
        for (int i = input.size(); i < 30000000; i++) {
            int nextValue;
            if (!lastSeen.containsKey(lastSpoken)) {
                nextValue = 0;
            } else {
                nextValue = i - lastSeen.get(lastSpoken) - 1;
            }
            lastSeen.put(lastSpoken, i - 1);
            lastSpoken = nextValue;
        }
        return Integer.toString(lastSpoken);

    }
}
