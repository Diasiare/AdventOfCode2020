package com.fplymouth.aoc2020;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Day23 extends Day {
    public static String INPUT = "963275481";
    public String part1() {
        Cup first = null;
        Cup last = null;
        Map<Integer, Cup> cups = new HashMap<>();
        for (char c: INPUT.toCharArray()) {
            String s = "" + c;
            int num = Integer.parseInt(s) - 1;
            Cup cup = new Cup(num);
            if (last != null) {
                last.setNext(cup);
            }
            last = cup;
            if (first == null) {
                first = cup;
            } 
            cups.put(num, cup);
        }
        last.setNext(first);

        Cup current = first;
        for (int i = 0; i < 100; i++) {
            first = current.next;
            last = first.next.next;
            current.setNext(last.next);
            Set<Integer> taken = Set.of(first.number, first.next.number, last.number);
            int destNum = (current.number  + INPUT.length() - 1) % INPUT.length();
            while (taken.contains(destNum)) {
                destNum = (destNum  + INPUT.length() - 1) % INPUT.length(); 
            }
            Cup dest = cups.get(destNum);
            Cup aDest = dest.next;
            dest.setNext(first);
            last.setNext(aDest);
            current = current.next;
        }

        String out = "";
        Cup c = cups.get(0).next;
        while (c.number != 0) {
            out += (c.number + 1);
            c = c.next;
        }
        return out;
    }

    public String part2() {
        Cup first = null;
        Cup last = null;
        int max = 0;
        Map<Integer, Cup> cups = new HashMap<>();
        for (char c: INPUT.toCharArray()) {
            String s = "" + c;
            int num = Integer.parseInt(s) - 1;
            max = Math.max(max, num);
            Cup cup = new Cup(num);
            if (last != null) {
                last.setNext(cup);
            }
            last = cup;
            if (first == null) {
                first = cup;
            } 
            cups.put(num, cup);
        }
        for (int i = max + 1; i < 1000000; i++ ){
            int num = i;
            max = Math.max(max, num);
            Cup cup = new Cup(num);
            last.setNext(cup);
            last = cup;
            cups.put(num, cup);
        }

        last.setNext(first);

        Cup current = first;
        for (int i = 0; i < 10000000; i++) {
            first = current.next;
            last = first.next.next;
            current.setNext(last.next);
            Set<Integer> taken = Set.of(first.number, first.next.number, last.number);
            int destNum = (current.number  + cups.size() - 1) % cups.size();
            while (taken.contains(destNum)) {
                destNum = (destNum  + cups.size() - 1) % cups.size(); 
            }
            Cup dest = cups.get(destNum);
            Cup aDest = dest.next;
            dest.setNext(first);
            last.setNext(aDest);
            current = current.next;
        }

        Cup c = cups.get(0).next;
        Cup c2 = c.next;
        return Long.toString((c.number + 1l) * (c2.number + 1l));
    }

    private class Cup {
        public final int number;
        public Cup next;
        public Cup prev;

        public Cup(int number) {
            this.number = number;
        } 

        public void setNext(Cup next) {
            this.next = next;
            next.prev = this;
        }
    }
}
