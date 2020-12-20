package com.fplymouth.aoc2020;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day20 extends Day {
    public String part1() {
        String[] tilesAsStrings = getInput("Day20.txt").split("\n\n");

        Map<Integer, List<String>> tiles = new HashMap<>();
        for (var ts: tilesAsStrings) {
            String[] lines = ts.split("\n");
            String title = lines[0].replace("Tile ", "").replace(":", "");
            int number = Integer.parseInt(title);
            
            List<String> rows = new ArrayList<>();
            for (int i = 1; i < lines.length; i++) {
                rows.add(lines[i]);
            }
            tiles.put(number, rows);
        }

        Map<String, Integer> sideCounts = new HashMap<>();
        Map<String, List<Integer>> sideToTileMap = new HashMap<>();
        for (Map.Entry<Integer, List<String>> tile: tiles.entrySet()) {
            var sides = getSides(tile.getValue());
            for (String side: sides) {
                String rSide = reverse(side);
                sideCounts.compute(side, (k, v) -> v == null? 1: v + 1);
                sideCounts.compute(rSide, (k, v) -> v == null? 1: v + 1);
                sideToTileMap.compute(side, (k, v) -> {
                    if (v == null) {
                        return new ArrayList<>(List.of(tile.getKey()));
                    } else {
                        v.add(tile.getKey());
                        return v;
                    }
                });
                sideToTileMap.compute(rSide, (k, v) -> {
                    if (v == null) {
                        return new ArrayList<>(List.of(tile.getKey()));
                    } else {
                        v.add(tile.getKey());
                        return v;
                    }
                });
            }
        }

        int size  = (int) Math.sqrt((double) tiles.size());
        int[][] map = new int[12][12];

        long start = 1;
        for (var tile: tiles.entrySet()) {
            var sides = getSides(tile.getValue());
            int count = 0;
            for (var side: sides) {
                count += sideCounts.get(side);
            }
            if (count == 6) {
                start *= tile.getKey();
                continue;
            }
        }
        return Long.toString(start);
    }


    public String part2() {
        String[] tilesAsStrings = getInput("Day20.txt").split("\n\n");

        Map<Integer, List<String>> tiles = new HashMap<>();
        for (var ts: tilesAsStrings) {
            String[] lines = ts.split("\n");
            String title = lines[0].replace("Tile ", "").replace(":", "");
            int number = Integer.parseInt(title);
            
            List<String> rows = new ArrayList<>();
            for (int i = 1; i < lines.length; i++) {
                rows.add(lines[i]);
            }
            tiles.put(number, rows);
        }

        Map<String, Integer> sideCounts = new HashMap<>();
        Map<String, List<Integer>> sideToTileMap = new HashMap<>();
        for (Map.Entry<Integer, List<String>> tile: tiles.entrySet()) {
            var sides = getSides(tile.getValue());
            for (String side: sides) {
                String rSide = reverse(side);
                sideCounts.compute(side, (k, v) -> v == null? 1: v + 1);
                sideCounts.compute(rSide, (k, v) -> v == null? 1: v + 1);
                sideToTileMap.compute(side, (k, v) -> {
                    if (v == null) {
                        return new ArrayList<>(List.of(tile.getKey()));
                    } else {
                        v.add(tile.getKey());
                        return v;
                    }
                });
                sideToTileMap.compute(rSide, (k, v) -> {
                    if (v == null) {
                        return new ArrayList<>(List.of(tile.getKey()));
                    } else {
                        v.add(tile.getKey());
                        return v;
                    }
                });
            }
        }

        int size  = (int) Math.sqrt((double) tiles.size());
        List<String>[][] map = new List[size][size];

        int start = 0;
        for (var tile: tiles.entrySet()) {
            var sides = getSides(tile.getValue());
            int count = 0;
            for (var side: sides) {
                count += sideCounts.get(side);
            }
            if (count == 6) {
                start = tile.getKey();
                break;
            }
        }
        
        var startTile = tiles.get(start);
        var startTileSides = getSides(startTile);
        int startRCount = -1;
        for (int i = 0; i < startTileSides.size(); i++) {
            if (sideCounts.get(startTileSides.get(i)) == 1) {
                startRCount = 3 - i;
                if (i == 0 && sideCounts.get(startTileSides.get(i + 1)) != 1) {
                    startRCount = 0;
                }
                break;
            }
        } 
        map[0][0] = rotate(startTile, startRCount);

        Set<Integer> added = new HashSet<>();
        added.add(start);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i + j == 0) continue;
                String west = j == 0 ? null : east(map[i][j - 1]);
                String north = i == 0 ? null : south(map[i - 1][j]);
                String targetSide = west == null ? north: west;
                var nums = sideToTileMap.get(targetSide);
                int num = nums.get(0);
                if (added.contains(num)) num = nums.get(1);
                added.add(num);
                var tile = tiles.get(num);
                map[i][j] = rotateToMatch(tile, west, north);
                if (west != null && !west(map[i][j]).equals(west)) {
                    throw new RuntimeException("Failure West");
                }
                if (north != null && !north(map[i][j]).equals(north)) {
                    throw new RuntimeException("Failure North");
                }
            }
        }

        String stringMap = mapToString(map, 1);
        
        List<String> monster = List.of("                  # ", "#    ##    ##    ###", " #  #  #  #  #  #   ");
        Set<Set<Map.Entry<Integer, Integer>>> monsters = new HashSet<>();
        for (int i = 0; i < 4; i++) {
            monsters.add(monsterToPointSet(rotate(monster, i)));
        }
        for (int i = 0; i < 4; i++) {
            monsters.add(monsterToPointSet(rotate(flipNorthSouth(monster), i)));
        }
        for (int i = 0; i < 4; i++) {
            monsters.add(monsterToPointSet(rotate(flipNorthSouth(rotate(flipNorthSouth(monster), 1)), i)));
        }

        String[] rowMap = stringMap.split("\n");
        int height = rowMap.length;
        int width = rowMap[0].length();
        
        Set<Map.Entry<Integer, Integer>> monsterPoints = new HashSet<>();
        for (int i = 0; i < height; i++ ) {
            for (int j = 0; j < width; j++) {
                for (var monsterp: monsters) {
                    monsterPoints.addAll(monsterPoints(i, j, monsterp, rowMap));
                }
            }
        }
        int waveCount = 0;
        for (char c: stringMap.toCharArray()) {
            if (c == '#') waveCount++;
        }

        return Integer.toString(waveCount - monsterPoints.size());
    }

    private Set<Map.Entry<Integer, Integer>> monsterPoints(int i, int j, 
        Set<Map.Entry<Integer, Integer>> monster, String[] rowMap) {
            int height = rowMap.length;
            int width = rowMap[0].length();
            Set<Map.Entry<Integer, Integer>> points = new HashSet<>();
            for (var point: monster) {
                if (point.getKey() + i >= height) return Set.of();
                if (point.getValue() + j >= width) return Set.of();
                if (rowMap[point.getKey() + i].charAt(point.getValue() + j) != '#') return Set.of();
                points.add(Map.entry(point.getKey() + i, point.getValue() + j));
            }
            return points;
        }

    private Set<Map.Entry<Integer, Integer>> monsterToPointSet(List<String> monster) {
        Set<Map.Entry<Integer, Integer>> out = new HashSet<>();
        for (int i = 0; i <  monster.size(); i++) {
            String row = monster.get(i);
            for (int j = 0; j < row.length(); j++) {
                if (monster.get(i).charAt(j) == '#') {
                    out.add(Map.entry(i, j));
                }
            }
        }
        return out;
    }

    private String mapToString(List<String>[][] map, int offset) {
        StringBuilder b = new StringBuilder();
        for (List<String>[] row : map) {
            int tileSize = row[0].size();
            for (int i = offset; i < tileSize - offset; i++) {
                for (var tile: row) {
                    String tileRow = tile.get(i);
                    b.append(tileRow.substring(offset, tileRow.length() - offset));
                }
                b.append("\n");
            }
        }
        return b.toString();
    }

    private List<String> rotateToMatch(List<String> tile, String west, String north) {
        int rotationSteps =  -1;
        var sides = getSides(tile);
        String targetSide = west == null ? north: west;
        for (int i = 0; i < sides.size(); i++) {
            var side = sides.get(i);
            if (side.equals(targetSide) || side.equals(reverse(targetSide))) {
                rotationSteps = 3 - i;
            }
        }
        tile = rotate(tile, rotationSteps);
        if (!west(tile).equals(targetSide)) {
            tile = flipNorthSouth(tile);
        }
        if (!west(tile).equals(targetSide)) {
            throw new RuntimeException("Failure");
        }
        if (west == null) {
            tile = flipNorthSouth(tile);
            tile = rotate(tile, 1);
        }
        return tile;
    }

    private List<String> rotate(List<String> tile, int steps) {
        if (steps == 0) return tile;
        List<String> out = new ArrayList<>();        
        for (int i = 0; i < tile.get(0).length(); i++) {
            String row = "";
            for (int j = 0; j < tile.size(); j ++) {
                row += tile.get(j).charAt(i);
            }
            out.add(reverse(row));
        }
        return rotate(out, steps - 1);
    }

    private List<String> flipNorthSouth(List<String> tile) {
        List<String> out = new ArrayList<>();
        for (int i = tile.size() - 1; i >= 0; i--) {
            out.add(tile.get(i));
        }
        return out;
    }

    private String north(List<String> tile) {
        return tile.get(0);
    }

    private String south(List<String> tile) {
        return tile.get(tile.size() - 1);
    }

    private String east(List<String> tile) {
        String out = "";
        for (var row :tile) {
            out += row.charAt(row.length() - 1);
        }
        return out;
    }

    private String west(List<String> tile) {
        String out = "";
        for (var row :tile) {
            out += row.charAt(0);
        }
        return out;
    }

    private List<String> getSides(List<String> tile) {
        return List.of(north(tile), east(tile), south(tile), west(tile));
    }

    private String reverse(String s) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) { 
            builder.append(s.charAt(s.length() - i - 1));
        }
        return builder.toString();
    }
}
