package com.fplymouth.aoc2020;

public class Day25 extends Day {
    private static final long PK1 = 19241437l;
    private static final long PK2 = 17346587l;
    private static final long DIV = 20201227l;
    private static final long MUL = 7;

    public String part1() {
        long start = 1;
        long count = 0;
        while (start != PK1) {
            count++;
            start = (start * MUL) % DIV;
        }

        long v = 1;
        for (long i = 0; i < count; i++) {
            v = (v * PK2) % DIV;
        }

        return Long.toString(v);
    }
}
