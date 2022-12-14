package net.gotzi.adventofcode.year2022;

import net.gotzi.adventofcode.Day;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Day1 extends Day<Long> {

    public Day1() {
        super(1, 2022);
    }

    @Override
    public Long computePuzzle1() {
        int max = 0;
        int sum = 0;

        for (String line : getLines()) {
            if (!line.equals("")) {
                sum += Integer.parseInt(line);
            } else if (sum > max) {
                max = sum;
                sum = 0;
            } else {
                sum = 0;
            }
        }


        return (long) max;
    }

    @Override
    public Long computePuzzle2() {
        List<Integer> elves = new LinkedList<>();

        int sum = 0;

        for (String line : getLines()) {
            if (!line.equals("")) {
                sum += Integer.parseInt(line);
            } else {
                elves.add(sum);
                sum = 0;
            }
        }

        Collections.sort(elves);

        return (long) (elves.get(elves.size()-1) + elves.get(elves.size()-2) + elves.get(elves.size()-3));
    }
}
