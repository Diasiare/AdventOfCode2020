package com.fplymouth.aoc2020;

import java.util.List;

public class Day3 extends Day {
    public String part1() {
        List<String> input =  getInputAsLines("Day3.txt");
        ForrestMap map = new ForrestMap(input);
        return Integer.toString(map.treeCount(1, 3));
    }

    public String part2() {
        List<String> input =  getInputAsLines("Day3.txt");
        ForrestMap map = new ForrestMap(input);
        long count = map.treeCount(1, 1);
        count *= map.treeCount(1, 3);
        count *= map.treeCount(1, 5);
        count *= map.treeCount(1, 7);
        count *= map.treeCount(2, 1);
        return Long.toString(count);
    }

    private class ForrestMap {
        private List<String> map;

        public ForrestMap(List<String> map) {
            this.map = map;
        }

        public boolean hasTree(int down, int right) {
            String row = map.get(down); 
            return row.charAt(right % row.length()) == '#';
        }

        public int treeCount(int down, int right) {
            int downp = 0;
            int rightp = 0;
            int count = 0;
            while (downp + down < map.size()) {
                downp += down;
                rightp += right;
                count += hasTree(downp, rightp) ? 1:0;
            }
            return count;
        }


    }
}
