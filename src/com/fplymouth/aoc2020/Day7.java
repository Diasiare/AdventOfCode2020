package com.fplymouth.aoc2020;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Map.Entry;

public class Day7 extends Day {

    public Day7() {
        List<String> rules = getInputAsLines("Day7.txt");
        for (var r: rules) {
            parseRule(r);
        }
    }

    public String part1() {
        BagRule myBag = BagRule.getRule("shiny gold");
        Set<String> canBeContainedIn = new HashSet<>();
        Queue<BagRule> toSearch = new ArrayDeque<>();
        toSearch.add(myBag);
        while (!toSearch.isEmpty()) {
            BagRule elem = toSearch.poll();
            if (canBeContainedIn.contains(elem.color)) {
                continue;
            }
            canBeContainedIn.add(elem.color);
            for (var e: elem.canBeContainedIn) {
                toSearch.add(e);
            }
        }
        return Integer.toString(canBeContainedIn.size() - 1);
    }

    public String part2() {
        BagRule myBag = BagRule.getRule("shiny gold");
        return Integer.toString(mustContain(myBag));
    }

    public int mustContain(BagRule base) {
        int count = 0;
        for (Entry<BagRule, Integer> e: base.mustContain.entrySet()) {
            count += e.getValue() + (e.getValue() * mustContain(e.getKey()));
        }
        return count;
    }

    private void parseRule(String rule) {
        String[] s1 = rule.split(" bags contain ");
        String mainColor = s1[0];
        BagRule mainRule = BagRule.getRule(mainColor);
        String rest = s1[1];
        String[] contained = rest.split(" bags?(,|\\.)");
        if ("no other".equals(contained[0].trim())) {
            return;
        }
        for (String cont: contained) {
            String[] p = cont.trim().split(" ");
            int num = Integer.parseInt(p[0]);
            String name = p[1];
            for (int i = 2; i < p.length; i++) {
                name += " " + p[i];
            }
            var subRule = BagRule.getRule(name);
            mainRule.addLink(subRule, num);
        }
    }

    private static class BagRule {
        public static final Map<String, BagRule> rules = new HashMap<>();
        public final String color;
        public final Map<BagRule, Integer> mustContain = new HashMap<>();
        public final Set<BagRule> canBeContainedIn = new HashSet<>();

        private BagRule(String color) {
            this.color = color;
        }

        public static BagRule getRule(String color) {
            if (!rules.containsKey(color)) {
                rules.put(color, new BagRule(color));
            }
            return rules.get(color);
        }

        public void addLink(BagRule rule, int count) {
            this.mustContain.put(rule, count);
            rule.canBeContainedIn.add(this);
        }
    }
    
}
