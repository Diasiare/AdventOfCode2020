package com.fplymouth.aoc2020;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day10 extends Day {
    public String part1() {
        List<Integer> addapters = getInputAsLines("Day10.txt").stream().mapToInt(Integer::parseInt)
            .boxed().collect(Collectors.toList());

        Collections.sort(addapters);
        int maxEl = addapters.get(addapters.size() - 1);
        addapters.add(maxEl + 3);
        int oneCount = 0;
        int threeCount = 0;

        for (int i = -1; i < addapters.size() - 1; i++) {
            int diff = addapters.get(i + 1) - (i == -1 ? 0 : addapters.get(i));
            if (diff == 3) threeCount++;
            else if (diff == 1) oneCount++;
        }

        return Integer.toString(oneCount * threeCount);
    }

    public String part2() {
        List<Integer> addapters = getInputAsLines("Day10.txt").stream().mapToInt(Integer::parseInt)
            .boxed().collect(Collectors.toList());

        addapters.add(0);
        Collections.sort(addapters);
        long total = arrangementsOverSegment(addapters, 0, new HashMap<>());

        return Long.toString(total);
    }

    private long arrangementsOverSegment(List<Integer> segment, int current, 
        Map<Integer, Long> memmory) {
        if (current == segment.size() - 1) return 1;
        if (memmory.containsKey(current)) return memmory.get(current);
        long total = arrangementsOverSegment(segment, current + 1, memmory);
        if (segment.size() > current + 2 && segment.get(current + 2) - segment.get(current) <= 3) {
            total += arrangementsOverSegment(segment, current + 2, memmory);
        }
        if (segment.size() > current + 3 && segment.get(current + 3) - segment.get(current) <= 3) {
            total += arrangementsOverSegment(segment, current + 3, memmory);
        }
        memmory.put(current, total);
        return total;
    }
}
