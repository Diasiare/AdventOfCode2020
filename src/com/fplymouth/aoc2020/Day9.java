package com.fplymouth.aoc2020;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class Day9 extends Day {
 
    
    public String part1() {
        var input = getInputAsLines("Day9.txt");
        List<Long> numbers = input.stream().mapToLong(Long::parseLong).boxed()
            .collect(Collectors.toList());

        Map<Long, Integer> avail = new HashMap<>();
        Queue<Long> queue = new ArrayDeque<>();

        for (int i = 0; i < 25; i++) {
            long v = numbers.get(i);
            avail.compute(v, (k, c) -> c == null ? 1 : c + 1);
            queue.add(v);
        }

        for (int i = 25; i < numbers.size(); i++) {
            long lookFor = numbers.get(i);
            boolean success = false;
            for (long v: queue) {
                if (lookFor - v != v && avail.containsKey(lookFor - v)) {
                    success = true;
                    break;
                }
            }
            if (!success) {
                return Long.toString(lookFor);
            }
            avail.compute(lookFor, (k, c) -> c == null ? 1 : c + 1);
            queue.add(lookFor);
            long toRemove = queue.poll();
            avail.compute(toRemove, (k, c) -> c.equals(1) ? null : c - 1);

        }
        return "Failure";
    }

    public String part2() {
        long sumTo = Long.parseLong(part1());
        var input = getInputAsLines("Day9.txt");
        List<Long> numbers = input.stream().mapToLong(Long::parseLong).boxed()
            .collect(Collectors.toList());
        
        int high = 0;
        int low = 0;
        long sum = numbers.get(0);
        while (sum != sumTo || high == low) {
            if (sum < sumTo) {
                sum += numbers.get(++high);
            } else {
                sum -= numbers.get(low++);
            }
        }

        long small = numbers.get(low);
        long large = numbers.get(low);
        for (int i = low; i <= high; i++) {
            small = Math.min(small, numbers.get(i));
            large = Math.max(large, numbers.get(i));
        }

        return Long.toString(small + large);
    }

}
