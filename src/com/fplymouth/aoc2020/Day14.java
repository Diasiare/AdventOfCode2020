package com.fplymouth.aoc2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14 extends Day {
    private static final long BASE_OR_MASK = 0;
    private static final long BASE_AND_MASK = 0xfffffffffffl;

    public String part1() {
        Map<Long, Long> memmory = new HashMap<>();
        List<String> input = getInputAsLines("Day14.txt");


        long andMask = BASE_AND_MASK;
        long orMask = BASE_OR_MASK;
        for (String line: input) {
            String[] l = line.split(" = ");
            String command = l[0];
            String value = l[1];
            if (command.equals("mask")) {
                andMask = BASE_AND_MASK;
                orMask = BASE_OR_MASK;
                for (int i = 0; i < value.length(); i++) {
                    char c = value.charAt(value.length() - i -1);
                    if (c == '1') {
                        orMask = orMask | (1l << i );
                    } else if (c == '0') {
                        andMask = ~((~andMask) | (1l << i));
                    }
                }
            } else {
                long toWrite = Long.parseLong(value);
                toWrite = (toWrite | orMask) & andMask;
                long location = Long.parseLong(command.replace("mem[", "").replace("]", ""));
                memmory.put(location, toWrite);
            }
        }

        long total = 0;
        for (long v: memmory.values()) {
            total += v;
        }
        return Long.toString(total);
    }

    public String part2() {
        Map<Long, Long> memmory = new HashMap<>();
        List<String> input = getInputAsLines("Day14.txt");


        List<Integer> floatingBits = new ArrayList<>();
        long orMask = BASE_OR_MASK;
        for (String line: input) {
            String[] l = line.split(" = ");
            String command = l[0];
            String value = l[1];
            if (command.equals("mask")) {
                floatingBits = new ArrayList<>();
                orMask = BASE_OR_MASK;
                for (int i = 0; i < value.length(); i++) {
                    char c = value.charAt(value.length() - i -1);
                    if (c == '1') {
                        orMask = orMask | (1l << i );
                    } else if (c == 'X') {
                        floatingBits.add(i);
                    }
                }
            } else {
                long toWrite = Long.parseLong(value);
                long baseAdress = Long.parseLong(command.replace("mem[", "").replace("]", ""));
                baseAdress = baseAdress | orMask; 
                List<Long> locations = List.of(baseAdress);
                for (int floating: floatingBits) {
                    List<Long> newLocations = new ArrayList<>();
                    for (long location: locations) {
                        newLocations.add(location);
                        newLocations.add(location ^ (1l << floating));
                    }
                    locations = newLocations;
                }

                for (long location: locations) {
                    memmory.put(location, toWrite);
                }
            }
        }

        long total = 0;
        for (var v: memmory.entrySet()) {
            total += v.getValue();
        }
        return Long.toString(total);
    }
}
