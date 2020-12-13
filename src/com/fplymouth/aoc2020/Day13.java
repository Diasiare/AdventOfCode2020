package com.fplymouth.aoc2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Day13 extends Day {

    public String part1() {
        String[] input = getInput("Day13.txt").split("\n");
        int startTime = Integer.parseInt(input[0]);
        List<Integer> times = Arrays.stream(input[1].split(","))
           .filter(s -> !s.equals("x")).mapToInt(Integer::parseInt).boxed()
           .collect(Collectors.toList());
        int minTime = Integer.MAX_VALUE;
        int minValue = -1;
        for (int value: times) {
            int time = (value - (startTime % value)) % value;
            if (time < minTime) {
                minValue = value;
                minTime = time;
            }
        }
        return Integer.toString(minTime * minValue); 
    }

    /**
     * x1*v1 = t
     * x2*v2 - 1 = t
     * x3*v3 - 2 = t
     * 
     * (x1*v1 + 1)/x2 = v3 
     * 
     * t = 0 mod x1
     * t = 1 mod x2
     * t = 2 mod x3
     * 
     * t = x1*v1 = 
     * 
     * x*v  = x*17*a
     * x*v  = x*37*b - x*8
     */

     public String part2() {
        String[] input = getInput("Day13.txt").split("\n");
        Map<Integer, Integer> times = new HashMap<>();
        String[] timesL = input[1].split(","); 
        for (int i = 0; i < timesL.length; i++) {
            String v = timesL[i];
            if ("x".equals(v)) continue;
            int p = Integer.parseInt(v);
            times.put(p, (100*p - i) % p);
        }
        List<Entry<Integer, Integer>> vs = new ArrayList<>(times.entrySet());
        long x = vs.get(0).getValue();
        long mult = 1;
        for (int i = 0; i < vs.size() - 1; i++) {
            mult *= vs.get(i).getKey();
            long mod = vs.get(i+1).getKey();
            long x2 = vs.get(i + 1).getValue();
            long j = -1;
            while ((x + mult * ++j) % mod != x2 % mod) {}
            print(x + " + " + mult*j+ "(" +mult+ "*" + j + ")"  + " = " + x2 % mod  + " mod " + mod);
            x = x + mult * j;
        }
        return Long.toString(x);
     }
    
}
