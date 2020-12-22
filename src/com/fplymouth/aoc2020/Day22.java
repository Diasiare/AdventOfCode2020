package com.fplymouth.aoc2020;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day22 extends Day {
    public String part1() {
        String decksS = getInput("Day22.txt");
        String[] p = decksS.split("\n\n");
        var deck1 = parseDeck(p[0]);
        var deck2 = parseDeck(p[1]);
        List<Deque<Integer>> decks = List.of(deck1, deck2);
        int round = 0;
        while (deck1.size() != 0 && deck2.size() != 0) {
            playRound(decks.get(round % 2), decks.get((round + 1) % 2));
            round = (round + 1) % 2;
        }
        long score = 0;
        if (deck1.size() == 0) {
            score = score(deck2);
        } else {
            score = score(deck1);
        }

        return Long.toString(score);

    }

    private long score(Deque<Integer> d) {
        long score = 0l;
        int mult = d.size();
        for (int c: d) {
            score += mult * c;
            mult--;
        }
        return score;
    }

    private void playRound(Deque<Integer> d1, Deque<Integer> d2) {
        int c1 = d1.pop();
        int c2 = d2.pop();
        if (c1 < c2) {
            d2.add(c2);
            d2.add(c1);
        } else {
            d1.add(c1);
            d1.add(c2);
        }
    }

    private Deque<Integer> parseDeck(String deckString) {
        String[] p = deckString.split("\n");
        Deque<Integer> out = new ArrayDeque<>();
        for (int i = 1; i < p.length; i++) {
            out.add(Integer.parseInt(p[i]));
        }
        return out;
    }

    public String part2() {
        String decksS = getInput("Day22.txt");
        String[] p = decksS.split("\n\n");
        var deck1 = parseDeck(p[0]);
        var deck2 = parseDeck(p[1]);
        Victory v = playRecursiveGame(deck1, deck2);
        long score = score(v.winningPlayersDeck);
        return Long.toString(score);
    }

    private HashMap<String, Victory> memory = new HashMap<>();

    public Victory playRecursiveGame(Deque<Integer> p1, Deque<Integer> p2) {
        long count = 0;
        Set<String> states = new HashSet<>();
        while (!p1.isEmpty() && !p2.isEmpty()) {
            if (p1.size() + p2.size() == 50) {
                print(++count + "\n");
            }
            String state = stringify(p1, p2);
            if (states.contains(state)) {
                return new Victory(true, p1);
            }
            states.add(state);

            int c1 = p1.pop();
            int c2 = p2.pop();
            boolean playerOneWon;
            if (c1 <= p1.size() && c2 <= p2.size()) {
                Deque<Integer> rp1 = new ArrayDeque<>();
                int addc = 0;
                for (var v: p1) {
                    if (addc == c1) {
                        break;
                    }
                    addc++;
                    rp1.add(v);
                }
                Deque<Integer> rp2 = new ArrayDeque<>();
                addc = 0;
                for (var v: p2) {
                    if (addc == c2) {
                        break;
                    }
                    addc++;
                    rp2.add(v);
                }

                String startState = stringify(rp1, rp2);
                if (memory.containsKey(startState)) {
                    Victory v = memory.get(startState);
                    playerOneWon = v.playerOneWon;
                } else {
                    Victory v = playRecursiveGame(rp1, rp2);
                    memory.put(startState, v);
                    playerOneWon = v.playerOneWon;
                }
            } else {
                playerOneWon = c1 > c2;
            }
            
            if (!playerOneWon) {
                p2.add(c2);
                p2.add(c1);
            } else {
                p1.add(c1);
                p1.add(c2);
            }

        }
        return new Victory(p2.isEmpty(), p2.isEmpty() ? p1: p2);
    }

    public String stringify(Deque<Integer> p1, Deque<Integer> p2) {
        String out = "";
        for (var i : p1) {
            out += i + ",";
        }
        out += "/";
        for (var i : p2) {
            out += i + ",";
        }
        return out;
    }

    private class Victory {
        public boolean playerOneWon;
        public Deque<Integer> winningPlayersDeck;

        public Victory(boolean p, Deque<Integer> d) {
            this.playerOneWon = p;
            this.winningPlayersDeck = d;
        }
    }
}
