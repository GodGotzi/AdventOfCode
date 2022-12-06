package net.gotzi.adventofcode.year2020;

import net.gotzi.adventofcode.Day;

import java.util.List;

public class Day1 extends Day<Long> {

    public Day1() {
        super(1, 2020);
    }

    @Override
    public Long computePuzzle1() {
        List<String> lines = getLines();

        int[] values = new int[lines.size()];

        for (int i = 0; i < values.length; i++) {
            values[i] = Integer.parseInt(lines.get(i));
        }

        for (int value : values) {
            for (int value2 : values) {
                for (int value3 : values) {
                    if (value + value2 + value3 == 2020)
                        return (long) value * value2 * value3;
                }
            }
        }

        return -1L;
    }

    @Override
    public Long computePuzzle2() {

        return 0L;
    }

}
