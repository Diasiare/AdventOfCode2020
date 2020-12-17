package com.fplymouth.aoc2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day16 extends Day {
    private static final String MY_TICKET = 
     "157,101,107,179,181,163,191,109,97,103,89,113,167,127,151,53,83,61,59,173";

    public String part1() {
        List<String> rulesS = getInputAsLines("Day16-rules.txt");
        List<String> tickets = getInputAsLines("Day16-tickets.txt");
        List<Rule> rules = rulesS.stream().map(Rule::new).collect(Collectors.toList());

        long accum = 0;
        for (var ticket: tickets) {
            String[] numsS = ticket.split(",");
            for (var numS: numsS) {
                int num = Integer.parseInt(numS);

                boolean valid = false;
                for (Rule rule: rules) {
                    if (rule.isValid(num)) {
                        valid = true;
                        break;
                    }
                }

                if (!valid) {
                    accum += num;
                }
            }
        }
        return Long.toString(accum);
    }

    public String part2() {
        List<String> rulesS = getInputAsLines("Day16-rules.txt");
        List<String> ticketsS = getInputAsLines("Day16-tickets.txt");
        List<Rule> rules = rulesS.stream().map(Rule::new).collect(Collectors.toList());

        List<List<Integer>> tickets = ticketsS.stream()
            .map((s) -> toValidTicket(s, rules))
            .filter((s) -> s != null)
            .collect(Collectors.toList());

        List<Set<Rule>> validByIndex = new ArrayList<>();
        for (int i = 0; i < tickets.get(0).size(); i++) {
            validByIndex.add(validForIndex(rules, i, tickets));
        }
        List<Integer> orderToTraverse = IntStream.range(0, validByIndex.size()).boxed()
            .sorted((i1, i2) -> validByIndex.get(i1).size() - validByIndex.get(i2).size())
            .collect(Collectors.toList());
        
        Map<Integer, Rule> rorder = findOrder(new HashSet<>(rules), 0, validByIndex, orderToTraverse);
        List<Rule> order = new ArrayList<>();
        for (int i = 0; i < rorder.size(); i++) {
            order.add(rorder.get(i));
        }
        long accum = 1;
        String[] myTicket = MY_TICKET.split(",");
        for (int i = 0; i < order.size(); i++) {
            Rule rule = order.get(i);
            if (rule.name.startsWith("departure")) {
                accum *= Long.parseLong(myTicket[i]);
            }
        }
        return Long.toString(accum);

    }

    public Set<Rule> validForIndex(List<Rule> remaining, int index, List<List<Integer>> tickets) {
        Set<Rule> valid = new HashSet<>();
        for (var rule: remaining) {
            boolean v = tickets.stream().allMatch((ticket) -> rule.isValid(ticket.get(index)));
            if (v) {
                valid.add(rule);
            }
        }
        return valid;
    }

    public Map<Integer, Rule> findOrder(Set<Rule> remaining, int findex, List<Set<Rule>> validByIndex, List<Integer> orderToTraverse) {
        if (remaining.size() == 0) {
            return new HashMap<>();
        }

        int trueIndex = orderToTraverse.get(findex);
        Set<Rule> toItterate = new HashSet<>(remaining);
        toItterate.retainAll(validByIndex.get(trueIndex));
        for (Rule rule: toItterate) {
                remaining.remove(rule);
                var r = findOrder(remaining, findex + 1, validByIndex, orderToTraverse);
                remaining.add(rule);
                if (r != null) {
                    r.put(trueIndex, rule);
                    return r; 
                }
        }
        return null;
    }

    public List<Integer> toValidTicket(String s, List<Rule> rules) {
        String[] numsS = s.split(",");
        List<Integer> out = new ArrayList<>();
        for (var numS: numsS) {
            int num = Integer.parseInt(numS);
            out.add(num);

            boolean valid = false;
            for (Rule rule: rules) {
                if (rule.isValid(num)) {
                    valid = true;
                    break;
                }
            }
            if (!valid) return null;
        }
        return out;
    }

    private static class Rule {
        int lf;
        int hf;
        int ls;
        int hs;
        String name;

        public Rule(String ruleString) {
            String[] s = ruleString.split(": ");
            this.name = s[0];
            s = s[1].split(" or ");
            String[] f = s[0].split("-");
            String[] se = s[1].split("-");
            this.lf = Integer.parseInt(f[0]);
            this.hf = Integer.parseInt(f[1]);
            this.ls = Integer.parseInt(se[0]);
            this.hs = Integer.parseInt(se[1]);
        }

        public boolean isValid(int v) {
            return (lf <= v && v <= hf) || (ls <= v && v <= hs);
        }
    }
}
