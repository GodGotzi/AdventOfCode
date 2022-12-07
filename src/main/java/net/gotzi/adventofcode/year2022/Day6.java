package net.gotzi.adventofcode.year2022;

import net.gotzi.adventofcode.Day;

public class Day6 extends Day<Long> {


    public Day6() {
        super(6, 2022);
    }

    @Override
    public Long computePuzzle1() {
        String line = getLines().get(0);

        for (int i = 0; i < line.length()-4; i++) {
            String sequence = line.substring(i, i+4);

            if (!hasSameChar(sequence)) {
                return (long) i+4;
            }
        }

        return (long) 0;
    }

    private boolean hasSameChar(String sequence) {
        for (int i = 0; i < sequence.length(); i++) {
            for (int j = 0; j < sequence.length(); j++) {
                if (sequence.charAt(i) == sequence.charAt(j) && i != j)
                    return true;
            }
        }

        return false;
    }

    @Override
    public Long computePuzzle2() {
        String line = getLines().get(0);

        for (int i = 0; i < line.length()-14; i++) {
            String sequence = line.substring(i, i+14);

            if (!hasSameChar(sequence)) {
                return (long) i+14;
            }
        }

        return (long) 0;
    }
}
