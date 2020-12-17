package com.fplymouth.aoc2020;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day17 extends Day {
    public String part1() {
        // z, y, x
        List<String> input = getInputAsLines("Day17.txt");
        Set<List<Integer>> currentWorld = new HashSet<>();
        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                if (c == '#') {
                    currentWorld.add(List.of(0, i, j));
                }
            }
        }

        for (int i = 0; i < 6; i ++) {
            currentWorld = step(currentWorld);
        }

        return Integer.toString(currentWorld.size());
    }

    private static final int[] VARIANCE = new int[] {-1 , 0, 1};

    private Set<List<Integer>> step(Set<List<Integer>> cur) {
        Set<List<Integer>> next = new HashSet<>();
        Set<List<Integer>> checked = new HashSet<>();
        List<Integer> checker = new ArrayList<>();
        checker.add(0);
        checker.add(0);
        checker.add(0);

        for (var point : cur) {
            for (int dz: VARIANCE) {
                checker.set(0, point.get(0) + dz);
                for (int dy: VARIANCE) {
                    checker.set(1, point.get(1) + dy);
                    for (int dx: VARIANCE) {
                        checker.set(2, point.get(2) + dx);
                        if (checked.contains(checker)) {
                            continue;
                        }
                        List<Integer> cPoint = new ArrayList<>(checker); 
                        checked.add(cPoint);

                        int nearbyCount = nearbyCount(checker.get(0), checker.get(1), checker.get(2), cur);
                        if (cur.contains(cPoint) && (nearbyCount == 2 || nearbyCount == 3)) {
                            next.add(cPoint);
                        } else if (!cur.contains(cPoint) && nearbyCount == 3) {
                            next.add(cPoint);
                        }
                    }
                }
            }
        }
        return next;
    }

    private int nearbyCount(int z, int y, int x, Set<List<Integer>> cur) {
        List<Integer> checker = new ArrayList<>();
        checker.add(0);
        checker.add(0);
        checker.add(0);
        int count = 0;
        for (int dz: VARIANCE) {
            checker.set(0, z + dz);
            for (int dy: VARIANCE) {
                checker.set(1, y + dy);
                for (int dx: VARIANCE) {
                    checker.set(2, x + dx);
                    if (dz == 0 && dy == 0 && dx == 0) {
                        continue;
                    }
                    if (cur.contains(checker)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public String part2() {
        // z, y, x, w
        List<String> input = getInputAsLines("Day17.txt");
        Set<List<Integer>> currentWorld = new HashSet<>();
        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                if (c == '#') {
                    currentWorld.add(List.of(0, i, j, 0));
                }
            }
        }

        for (int i = 0; i < 6; i ++) {
            currentWorld = step2(currentWorld);
        }

        return Integer.toString(currentWorld.size());
    }

    private Set<List<Integer>> step2(Set<List<Integer>> cur) {
        Set<List<Integer>> next = new HashSet<>();
        Set<List<Integer>> checked = new HashSet<>();
        List<Integer> checker = new ArrayList<>();
        checker.add(0);
        checker.add(0);
        checker.add(0);
        checker.add(0);

        for (var point : cur) {
            for (int dz: VARIANCE) {
                checker.set(0, point.get(0) + dz);
                for (int dy: VARIANCE) {
                    checker.set(1, point.get(1) + dy);
                    for (int dx: VARIANCE) {
                        checker.set(2, point.get(2) + dx);
                        for (int dw: VARIANCE) {
                            checker.set(3, point.get(3) + dw);
                            if (checked.contains(checker)) {
                                continue;
                            }
                            List<Integer> cPoint = new ArrayList<>(checker); 
                            checked.add(cPoint);

                            int nearbyCount = nearbyCount2(checker.get(0), checker.get(1), checker.get(2), checker.get(3), cur);
                            if (cur.contains(cPoint) && (nearbyCount == 2 || nearbyCount == 3)) {
                                next.add(cPoint);
                            } else if (!cur.contains(cPoint) && nearbyCount == 3) {
                                next.add(cPoint);
                            }
                        }
                    }
                }
            }
        }
        return next;
    }

    private int nearbyCount2(int z, int y, int x, int w, Set<List<Integer>> cur) {
        List<Integer> checker = new ArrayList<>();
        checker.add(0);
        checker.add(0);
        checker.add(0);
        checker.add(0);
        int count = 0;
        for (int dz: VARIANCE) {
            checker.set(0, z + dz);
            for (int dy: VARIANCE) {
                checker.set(1, y + dy);
                for (int dx: VARIANCE) {
                    checker.set(2, x + dx);
                    for (int dw: VARIANCE) {
                        checker.set(3, w + dw);
                        if (dz == 0 && dy == 0 && dx == 0 && dw == 0) {
                            continue;
                        }
                        if (cur.contains(checker)) {
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }
}
