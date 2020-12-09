package com.fplymouth.aoc2020;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day8 extends Day {

	public String part1() {
        List<String> lines = getInputAsLines("Day8.txt");
        List<Instruction> program = lines.stream()
            .map(line -> {
                String[] p = line.split(" ");
                OP op = stringToOp(p[0]);
                int value = Integer.parseInt(p[1]);
                return new Instruction(op, value);
            }).collect(Collectors.toList());
        
        int acc = 0;
        int currIndex = 0;
        Set<Integer> seenIndexes = new HashSet<>();
        while(!seenIndexes.contains(currIndex)) {
            seenIndexes.add(currIndex);
            Instruction i = program.get(currIndex);
            switch (i.op) {
                case JMP:
                    currIndex += i.value;
                    continue;
                case ACC:
                    acc += i.value;
                case NOP:
                    currIndex++;
            }
        }
        return Integer.toString(acc);
    }

    public String part2() {
        List<String> lines = getInputAsLines("Day8.txt");
        List<Instruction> program = lines.stream()
            .map(line -> {
                String[] p = line.split(" ");
                OP op = stringToOp(p[0]);
                int value = Integer.parseInt(p[1]);
                return new Instruction(op, value);
            }).collect(Collectors.toList());

        for (int i = 0; i < program.size(); i++) {
            Integer v = tryMutation(program, i);
            if (v != null) {
                return v.toString();
            }
        }
        return "FAILED";
    }

    public Integer tryMutation(List<Instruction> program, int lineToMutate) {
        OP orig = program.get(lineToMutate).op;
        if (orig == OP.ACC) return null;
        program.get(lineToMutate).op = orig == OP.NOP? OP.JMP: OP.NOP;

        int acc = 0;
        int currIndex = 0;
        Set<Integer> seenIndexes = new HashSet<>();
        while(!seenIndexes.contains(currIndex) && currIndex >= 0 && currIndex < program.size()) {
            seenIndexes.add(currIndex);
            Instruction i = program.get(currIndex);
            switch (i.op) {
                case JMP:
                    currIndex += i.value;
                    continue;
                case ACC:
                    acc += i.value;
                case NOP:
                    currIndex++;
            }
        }
        program.get(lineToMutate).op = orig;
        return currIndex == program.size()? acc : null;
    }

    private OP stringToOp(String s) {
        switch (s) {
            case "jmp": 
                return OP.JMP;
            case "acc":
                return OP.ACC;
            case "nop":
                return OP.NOP;
            default:
                print("Got op: " + s);
                System.exit(1);
                return null;
        }
    }

    private class Instruction {
        public OP op;
        public final int value;

        Instruction(OP op, int value) {
            this.op = op;
            this.value = value;
        }
    }

    private enum OP {
        NOP,
        JMP,
        ACC
    }
}
