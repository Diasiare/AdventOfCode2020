package com.fplymouth.aoc2020;

import java.util.List;
import java.util.Map;

public class Day12 extends Day {
    private static final Map<Integer, String>  FACING_TO_DIR = Map.of(0, "N", 1, "E", 2, "S" , 3, "W"); 

    public String part1() {
        List<String> lines = getInputAsLines("Day12.txt");

        int x = 0;
        int y = 0;
        int facing = 1;
        for (var line: lines) {
            String letter = line.substring(0, 1);
            int num = Integer.parseInt(line.substring(1));
            if ("F".equals(letter)) {
                while (facing < 0) facing += 4;
                facing = facing % 4;
                letter = FACING_TO_DIR.get(facing % 4);
            }
            switch (letter) {
                case "N":
                    y += num;
                    break;
                case "S":
                    y -= num;
                    break;
                case "E":
                    x += num;
                    break;
                case "W":
                    x -= num;
                    break;
                case "R": 
                    facing += num / 90;
                    break;
                case "L": 
                    facing -= num / 90;
                    break;
            }
        }

        return Integer.toString(Math.abs(x) + Math.abs(y));
    }

    public String part2() {
        List<String> lines = getInputAsLines("Day12.txt");

        int x = 10;
        int y = 1;
        long sx = 0;
        long sy = 0;
        for (var line: lines) {
            String letter = line.substring(0, 1);
            int num = Integer.parseInt(line.substring(1));

            switch (letter) {
                case "F":
                    sx += x * num;
                    sy += y * num;
                    break;
                case "N":
                    y += num;
                    break;
                case "S":
                    y -= num;
                    break;
                case "E":
                    x += num;
                    break;
                case "W":
                    x -= num;
                    break;
                case "R":
                    for (int i = 0; i < num / 90; i++) {
                        int oldx = x;
                        x = y;
                        y = -oldx;
                    } 
                    break;
                case "L": 
                    for (int i = 0; i < num / 90; i++) {
                        int oldx = x;
                        x = -y;
                        y = oldx;
                    } 
                    break;
            }
        }

        return Long.toString(Math.abs(sx) + Math.abs(sy));
    }
}
