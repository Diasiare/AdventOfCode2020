package com.fplymouth.aoc2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day18 extends Day {

    public String part1() {
        List<String> input = getInputAsLines("Day18.txt");
        long accum = 0;
        for (var line: input) {
            accum += new Calculator(line).calc();
        }
        return Long.toString(accum);
    }

    public String part2() {
        List<String> input = getInputAsLines("Day18.txt");
        long accum = 0;
        for (var line: input) {
            accum += new AdvancedCalculator(line).calc();
        }
        return Long.toString(accum);
    }



    private static class Calculator {
        private final List<String> input;
        private int index;

        public Calculator(String line) {
            line = line.replaceAll("\\(", "( ");
            line = line.replaceAll("\\)", " )");
            input = Arrays.asList(line.split(" "));
            index = 0;
        }

        public long calc() {
            long v = getValue();
            while (index < input.size()) {
                String operator = input.get(index++);
                if (")".equals(operator)) {
                    return v;
                }
                Long value = getValue();
                switch (operator) {
                    case "+": 
                        v += value;
                        break;
                    case "*":
                        v *= value;
                }
            }
    
            return v;
        }
    
        private long getValue() {
            String c = input.get(index++);
            if ("(".equals(c)) {
                return calc();
            } else {
                return Long.parseLong(c);
            }
        }
    }

    private class AdvancedCalculator {
        private final List<String> pinput;
        private List<String> input;
        private int index;

        public AdvancedCalculator(String line) {
            line = line.replaceAll("\\(", "( ");
            line = line.replaceAll("\\)", " )");
            pinput = Arrays.asList(line.split(" "));
            index = 0;
            print("Before:" + line);
            line = addParens();
            print("After:" + line);
            index = 0;
            line = line.replaceAll("\\(", "( ");
            line = line.replaceAll("\\)", " )");
            input = Arrays.asList(line.split(" "));

        }

        private String addParens() {
            boolean prevWasPlus = false;
            StringBuilder accum = new StringBuilder();
            while (index < pinput.size()) {
                String value = pinput.get(index++);
                if ("(".equals(value)) {
                    value = "(" + addParens();
                } 
                if (index >= pinput.size()) {
                    accum.append(value);
                    if (prevWasPlus) {
                        accum.append(")");
                    }
                    return accum.toString();
                }
                String operator = pinput.get(index++);
                if ("+".equals(operator)) {
                    if (!prevWasPlus) {
                        accum.append("(");
                        prevWasPlus = true;
                    }
                    accum.append(value);
                    accum.append(" + ");
                } else if (")".equals(operator)) {
                    accum.append(value);
                    if (prevWasPlus) {
                        accum.append(")");
                    }
                    accum.append(")");
                    return accum.toString();
                } else {
                    accum.append(value);
                    if (prevWasPlus) {
                        accum.append(")");
                        prevWasPlus = false;
                    }
                    accum.append(" * ");
                }
            }
            return accum.toString();
        }

        public long calc() {
            long v = getValue();
            while (index < input.size()) {
                String operator = input.get(index++);
                if (")".equals(operator)) {
                    return v;
                }
                Long value = getValue();
                switch (operator) {
                    case "+": 
                        v += value;
                        break;
                    case "*":
                        v *= value;
                }
            }
    
            return v;
        }
    
        private long getValue() {
            String c = input.get(index++);
            if ("(".equals(c)) {
                return calc();
            } else {
                return Long.parseLong(c);
            }
        }
    }
}
