package net.gotzi.adventofcode.year2022;

import net.gotzi.adventofcode.Day;

public class Day10 extends Day<String> {
    public Day10() {
        super(10, 2022);
    }

    @Override
    public String computePuzzle1() {
        int cycle = 0;
        long sum = 0;

        String line;

        for (int i = 0; i < getLines().size(); i++) {
            line = getLines().get(i);
            if (line.contains("noop")) {
                sum += addIfImportantCycle(cycle, 1, i);
                ++cycle;
            } else {
                sum += addIfImportantCycle(cycle, 2, i-1);
                cycle += 2;
            }
        }

        return String.valueOf(sum);
    }

    private long addIfImportantCycle(int cycle, int increase, int index) {
        long X = getXto(index);
        int i;

        for (i = cycle + 1 ; i <= cycle + increase; i++) {
            if ((i-20) % 40 == 0 && i <= 220) {
                return X * i;
            }
        }

        return 0;
    }

    private int getXto(int index) {
        int X = 1;

        String line;
        String[] split;
        for (int i = 0; i <= index; i++) {
            line = getLines().get(i);

            if (line.contains("addx")) {
                split = line.split(" ");
                X += Integer.parseInt(split[1]);
            }
        }

        return X;
    }

    @Override
    public String computePuzzle2() {
        int cycle = 0;

        String line;
        StringBuilder screen = new StringBuilder("\n");

        for (int i = 0; i < getLines().size(); i++) {
            line = getLines().get(i);
            if (line.contains("noop")) {
                computeScreenPixel(screen, cycle, 1, i);
                ++cycle;
            } else {
                computeScreenPixel(screen, cycle, 2, i-1);
                cycle += 2;
            }
        }

        return screen.toString();
    }

    private void computeScreenPixel(StringBuilder screen, int cycle, int increase, int index) {
        int X = getXto(index);
        int CRTLocation;
        int i;

        for (i = cycle+1; i <= cycle + increase; i++) {
            CRTLocation = (i-1) % 40;

            if (Math.abs(X - CRTLocation) <= 1) {
                screen.append("#");
            } else screen.append(" ");

            if (CRTLocation == 39) screen.append("\n");
        }
    }
}
