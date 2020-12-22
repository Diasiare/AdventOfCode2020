package com.fplymouth.aoc2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day21 extends Day {
    public String part1() {
        List<String> input = getInputAsLines("Day21.txt");
        Map<String, Set<String>> alergenToCauses = new HashMap<>();
        Map<String, Integer> ingredientCount = new HashMap<>();
        Set<String> ingredients = new HashSet<>();
        for (var line: input) {
            String[] p = line.split("\\(");
            String ingS = p[0].trim();
            Set<String> ing = Arrays.stream(ingS.split(" ")).collect(Collectors.toSet());
            ingredients.addAll(ing);
            String alersS = p[1].replaceAll("\\)", "").replace("contains ", "");
            String[] alers = alersS.split(", ");
            for (var i: ing) {
                ingredientCount.compute(i, (k, v) -> v == null ? 1 : v + 1);
            }
            for (var aler: alers) {
                alergenToCauses.compute(aler, (k, v) -> {
                    if (v == null) return ing;
                    v = new HashSet<>(v);
                    v.retainAll(ing);
                    return v;
                });
            }
        }

        Set<String> safeIngredients = new HashSet<>(ingredients);
        for (var v: alergenToCauses.values()) {
            safeIngredients.removeAll(v);
        }
        int count = safeIngredients.stream().mapToInt(i -> ingredientCount.get(i))
            .sum();
        return Integer.toString(count);
    }

    public String part2() {
        List<String> input = getInputAsLines("Day21.txt");
        Map<String, Set<String>> alergenToCauses = new HashMap<>();
        Map<String, Integer> ingredientCount = new HashMap<>();
        Set<String> ingredients = new HashSet<>();
        for (var line: input) {
            String[] p = line.split("\\(");
            String ingS = p[0].trim();
            Set<String> ing = Arrays.stream(ingS.split(" ")).collect(Collectors.toSet());
            ingredients.addAll(ing);
            String alersS = p[1].replaceAll("\\)", "").replace("contains ", "");
            String[] alers = alersS.split(", ");
            for (var i: ing) {
                ingredientCount.compute(i, (k, v) -> v == null ? 1 : v + 1);
            }
            for (var aler: alers) {
                alergenToCauses.compute(aler, (k, v) -> {
                    if (v == null) return ing;
                    v = new HashSet<>(v);
                    v.retainAll(ing);
                    return v;
                });
            }
        }

        HashMap<String, String> alergenToIngredient = new HashMap<>();
        while (alergenToIngredient.size() < alergenToCauses.size()) {
            Collection<String> known = alergenToIngredient.values();
            for (var e: alergenToCauses.entrySet()) {
                String alergen = e.getKey();
                if (alergenToIngredient.containsKey(e)) continue;
                Set<String> causes = new HashSet<>(e.getValue());
                causes.removeAll(known);
                if (causes.size() == 1) {
                    for (var cause: causes) {
                        alergenToIngredient.put(alergen, cause);
                    }
                }
            }
        }
        HashMap<String, String> ingToAler = new HashMap<>();
        for (var e: alergenToIngredient.entrySet()) {
            ingToAler.put(e.getValue(), e.getKey());
        }

        List<String> causes = new ArrayList<>(ingToAler.keySet());
        Collections.sort(causes, (c1, c2) -> ingToAler.get(c1).compareTo(ingToAler.get(c2)));
        StringBuilder s = new StringBuilder();

        return String.join(",", causes);
    }
    
}
