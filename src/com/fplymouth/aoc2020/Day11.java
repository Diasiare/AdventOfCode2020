package com.fplymouth.aoc2020;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Day11 extends Day {
    public String part1() {
        List<String> input = getInputAsLines("Day11.txt");
        List<List<Optional<Boolean>>> map = new ArrayList<>();

        for (String line: input) {
            List<Optional<Boolean>> row = new ArrayList<>();
            for (char c: line.toCharArray()) {
                if (c == 'L') {
                    row.add(Optional.of(false));
                } else {
                    row.add(Optional.empty());
                }
            }
            map.add(row);
        }

        List<List<Optional<Boolean>>> pMap = null;
        while (!map.equals(pMap)) {
            pMap = map;
            map = step(pMap);
        }
        
        int count = 0;
        for (var row: map) {
            for (var cel: row) {
                if (cel.orElse(false)) count++;
            }
        }

        return Integer.toString(count);
    }

    private Optional<Boolean> getValue(int row, int col, List<List<Optional<Boolean>>> map) {
        if (row < 0 || row >= map.size()) return Optional.empty();
        var rowm = map.get(row);
        if (col < 0 || col >= rowm.size()) return Optional.empty();
        return rowm.get(col);
    }

    private int countNearby(int row, int col, List<List<Optional<Boolean>>> map) {
        boolean[] vs = {
            getValue(row - 1, col - 1, map).orElse(false),
            getValue(row - 1, col, map).orElse(false),
            getValue(row - 1, col + 1, map).orElse(false),
            getValue(row, col - 1, map).orElse(false),
            getValue(row, col + 1, map).orElse(false),
            getValue(row + 1, col - 1, map).orElse(false),
            getValue(row + 1, col, map).orElse(false),
            getValue(row + 1, col + 1, map).orElse(false)
        };
        int count = 0;
        for (var v : vs) {
            if (v) count++;
        }
        return count;
    }

    private Optional<Boolean> nextValue(int row, int col, List<List<Optional<Boolean>>> map) {
        var start = getValue(row, col, map);
        if (!start.isPresent()) return start;
        boolean isOccupied = start.get();
        int nearbyOccupied = countNearby(row, col, map);
        if (isOccupied) {
            if (nearbyOccupied >= 4) {
                return Optional.of(false);
            }
            return start;
        } else {
            if (nearbyOccupied == 0) {
                return Optional.of(true);
            }
            return start;
        }
    }

    private List<List<Optional<Boolean>>> step(List<List<Optional<Boolean>>> map) {
        int rows = map.size();
        int cols = map.get(0).size();
        List<List<Optional<Boolean>>> outM = new ArrayList<>();
        for (int row = 0; row < rows; row ++) {
            List<Optional<Boolean>> outRow = new ArrayList<>();
            for (int col = 0; col < cols; col++) {
                outRow.add(nextValue(row, col, map));
            }
            outM.add(outRow);
        }
        return outM;
    } 

    public String part2() {
        List<String> input = getInputAsLines("Day11.txt");
        List<List<Optional<Boolean>>> map = new ArrayList<>();

        for (String line: input) {
            List<Optional<Boolean>> row = new ArrayList<>();
            for (char c: line.toCharArray()) {
                if (c == 'L') {
                    row.add(Optional.of(false));
                } else {
                    row.add(Optional.empty());
                }
            }
            map.add(row);
        }

        List<List<Optional<Boolean>>> pMap = null;
        while (!map.equals(pMap)) {
            pMap = map;
            map = step2(pMap);
        }
        
        int count = 0;
        for (var row: map) {
            for (var cel: row) {
                if (cel.orElse(false)) count++;
            }
        }

        return Integer.toString(count);
    }

    private Optional<Boolean> firstVisible(int row, int col, List<List<Optional<Boolean>>> map,
                int rDir, int cDir) {
        int rows = map.size();
        int cols = map.get(0).size();
        row += rDir;
        col += cDir;
        while (row >=0 && row < rows && col >= 0 && col < cols) {
            var v =  getValue(row, col, map);
            if (v.isPresent()) return v;
            row += rDir;
            col += cDir;
        }
        return Optional.empty();

    }

    private int countNearby2(int row, int col, List<List<Optional<Boolean>>> map) {
        boolean[] vs = {
            firstVisible(row, col, map, -1, -1).orElse(false),
            firstVisible(row, col, map, -1, 0).orElse(false),
            firstVisible(row, col, map, -1, +1).orElse(false),
            firstVisible(row, col, map, 0, -1).orElse(false),
            firstVisible(row, col, map, 0, +1).orElse(false),
            firstVisible(row, col, map, +1, -1).orElse(false),
            firstVisible(row, col, map, +1, 0).orElse(false),
            firstVisible(row, col, map, +1, +1).orElse(false),
        };
        int count = 0;
        for (var v : vs) {
            if (v) count++;
        }
        return count;
    }

    private Optional<Boolean> nextValue2(int row, int col, List<List<Optional<Boolean>>> map) {
        var start = getValue(row, col, map);
        if (!start.isPresent()) return start;
        boolean isOccupied = start.get();
        int nearbyOccupied = countNearby2(row, col, map);
        if (isOccupied) {
            if (nearbyOccupied >= 5) {
                return Optional.of(false);
            }
            return start;
        } else {
            if (nearbyOccupied == 0) {
                return Optional.of(true);
            }
            return start;
        }
    }

    private List<List<Optional<Boolean>>> step2(List<List<Optional<Boolean>>> map) {
        int rows = map.size();
        int cols = map.get(0).size();
        List<List<Optional<Boolean>>> outM = new ArrayList<>();
        for (int row = 0; row < rows; row ++) {
            List<Optional<Boolean>> outRow = new ArrayList<>();
            for (int col = 0; col < cols; col++) {
                outRow.add(nextValue2(row, col, map));
            }
            outM.add(outRow);
        }
        return outM;
    } 
    
}
