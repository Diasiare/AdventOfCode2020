package com.fplymouth.aoc2020;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Day4 extends Day {
    public String part1() {
        String input = getInput("Day4.txt");
        String[] passportStrings = input.split("\n\n");
        return "" + Arrays.stream(passportStrings).map(Passport::new)
            .filter(Passport::isValidPPorId)
            .count();
    }

    public String part2() {
        String input = getInput("Day4.txt");
        String[] passportStrings = input.split("\n\n");
        return "" + Arrays.stream(passportStrings).map(Passport::new)
            .filter(Passport::isValidPPorId)
            .filter(Passport::allFieldsValid)
            .count();
    }

    private class Passport {
        private final Set<String> needed = Set.of("byr", "eyr", "hgt", "hcl", "ecl", "pid", "iyr");
        private final Set<String> eyeColors = Set.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
        private Map<String, String> values = new HashMap<>();


        public Passport(String asString) {
            for (var kv: asString.split("\\s")) {
                String[] parts = kv.split(":");
                values.put(parts[0], parts[1]);
            }
        }

        public boolean isValidPPorId() {
            return needed.stream().allMatch(values::containsKey);
        }

        public boolean allFieldsValid() {
            int byr = Integer.parseInt(values.get("byr"));
            if (values.get("byr").length() != 4 || 1920 > byr || byr > 2002) return false;
            int iyr = Integer.parseInt(values.get("iyr"));
            if (values.get("iyr").length() != 4 || 2010 > iyr || iyr > 2020) return false;
            int eyr = Integer.parseInt(values.get("eyr"));
            if (values.get("eyr").length() != 4 || 2020 > eyr || eyr > 2030) return false;
            if (!heightValid()) return false;
            if (!values.get("hcl").matches("#[0-9a-f]{6}")) return false;
            if (!eyeColors.contains(values.get("ecl"))) return false;
            if (!values.get("pid").matches("(\\d){9}")) return false;
            return true;
        }
        
        private boolean isBetween(int v,int  low,int high) {
            return low <= v && v <= high;
        }

        private boolean heightValid() {
            String hgt = values.get("hgt");
            String nums = hgt.substring(0, hgt.length() - 2);
            if (hgt.matches("\\d+cm")) {
                return isBetween(Integer.parseInt(nums), 150, 193);
            } else if (hgt.matches("\\d+in")) {
                return isBetween(Integer.parseInt(nums), 59, 76);
            }
            return false;
        }


    }
}
