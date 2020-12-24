package com.fplymouth.aoc2020;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Day24 extends Day {
    public String part1() {
        Map<Entry<Integer, Integer>, Boolean> hexes = new HashMap<>();

        List<String> lines = getInputAsLines("Day24.txt");

        for (String line : lines) {
            int index = 0;
            Entry<Integer, Integer> tile = Map.entry(0, 0);
            while (index < line.length()) {
                String s = "" + line.charAt(index++);
                if (s.equals("n") || s.equals("s")) {
                    s += line.charAt(index++);
                }

                switch (s) {
                    case "w":
                        tile = west(tile);
                        break;
                    case "e":
                        tile = east(tile);
                        break;
                    case "se":
                        tile = se(tile);
                        break;
                    case "sw":
                        tile = sw(tile);
                        break;
                    case "ne":
                        tile = ne(tile);
                        break;
                    case "nw":
                        tile = nw(tile);
                        break;

                }
            }
            hexes.compute(tile, (k, v) -> v == null? true : !v);
        }
        int count = 0;
        for (boolean v: hexes.values()) {
            if (v) count ++;
        }
        return Integer.toString(count);
    }

    public Entry<Integer, Integer> west(Entry<Integer, Integer> start) {
        return Map.entry(start.getKey() - 1, start.getValue());
    }
    public Entry<Integer, Integer> east(Entry<Integer, Integer> start) {
        return Map.entry(start.getKey() + 1, start.getValue());
    }
    public Entry<Integer, Integer> sw(Entry<Integer, Integer> start) {
        return Map.entry(start.getKey() - 1, start.getValue() - 1);
    }
    public Entry<Integer, Integer> se(Entry<Integer, Integer> start) {
        return Map.entry(start.getKey(), start.getValue() - 1);
    }
    public Entry<Integer, Integer> nw(Entry<Integer, Integer> start) {
        return Map.entry(start.getKey(), start.getValue() + 1);
    }
    public Entry<Integer, Integer> ne(Entry<Integer, Integer> start) {
        return Map.entry(start.getKey() + 1, start.getValue() + 1);
    }

    public String part2() {
        Map<Entry<Integer, Integer>, Boolean> hexes = new HashMap<>();

        List<String> lines = getInputAsLines("Day24.txt");

        for (String line : lines) {
            int index = 0;
            Entry<Integer, Integer> tile = Map.entry(0, 0);
            while (index < line.length()) {
                String s = "" + line.charAt(index++);
                if (s.equals("n") || s.equals("s")) {
                    s += line.charAt(index++);
                }

                switch (s) {
                    case "w":
                        tile = west(tile);
                        break;
                    case "e":
                        tile = east(tile);
                        break;
                    case "se":
                        tile = se(tile);
                        break;
                    case "sw":
                        tile = sw(tile);
                        break;
                    case "ne":
                        tile = ne(tile);
                        break;
                    case "nw":
                        tile = nw(tile);
                        break;

                }
            }
            hexes.compute(tile, (k, v) -> v == null? true : !v);
        }


        for (int i = 0; i < 100; i++) {
            hexes = nextDay(hexes);
        }

        int count = 0;
        for (boolean v: hexes.values()) {
            if (v) count ++;
        }
        return Integer.toString(count);
    }

    private Map<Entry<Integer, Integer>, Boolean> nextDay(Map<Entry<Integer, Integer>, Boolean> hexes) {
        Map<Entry<Integer, Integer>, Boolean> nexDay = new HashMap<>();
        for (var hex: hexes.keySet()) {
            if (isBlackNexDay(hex, hexes)) {
                nexDay.put(hex, true);
            }
            for (var adj: adj(hex)) {
                if (isBlackNexDay(adj, hexes)) {
                    nexDay.put(adj, true);
                }    
            }
        }
        return nexDay;
    }

    private boolean isBlackNexDay(Entry<Integer, Integer> hex, Map<Entry<Integer, Integer>, Boolean> hexes) {
        boolean isBlack = hexes.getOrDefault(hex, false);
        int count = 0;
        for (var adj: adj(hex)) {
            count += hexes.getOrDefault(adj, false) ? 1 : 0;
        }
        if (isBlack) {
            return count > 0 && count < 3;
        } else {
            return count == 2;
        }
    }

    private List<Entry<Integer, Integer>> adj(Entry<Integer, Integer> hex) {
        return List.of(west(hex), east(hex), sw(hex),  se(hex), nw(hex), ne(hex));
    }



    
}
