package net.gotzi.adventofcode.year2022;

import net.gotzi.adventofcode.Day;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Day4 extends Day<Long> {
    public Day4() {
        super(4, 2022);
    }

    @Override
    public Long computePuzzle1() {
        int amount = 0;

        for (String line : getLines()) {
            String[] sections = line.split(",");
            HashSet<Integer> section1 = getRange(sections[0]);
            HashSet<Integer> section2 = getRange(sections[1]);

            if (section1.containsAll(section2) || section2.containsAll(section1))
                amount++;
        }

        return (long) amount;
    }

    private HashSet<Integer> getRange(String section) {
        List<Integer> list = new ArrayList<>();
        int lower = Integer.parseInt(section.split("-")[0]);
        int upper = Integer.parseInt(section.split("-")[1]);

        for (int i = lower; i < upper+1; i++) {
            list.add(i);
        }

        return new HashSet<>(list);
    }

    @Override
    public Long computePuzzle2() {

        int amount = 0;

        for (String line : getLines()) {
            String[] sections = line.split(",");
            HashSet<Integer> section1 = getRange(sections[0]);
            HashSet<Integer> section2 = getRange(sections[1]);

            if (containsAny(section1, section2))
                amount++;
        }

        return (long) amount;
    }

    private boolean containsAny(HashSet<Integer> section1, HashSet<Integer> section2) {
        return section1.stream().anyMatch(section2::contains);
    }
}
