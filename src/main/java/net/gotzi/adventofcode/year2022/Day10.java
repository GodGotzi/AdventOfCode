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
        long sum = 0;

        String line;
        StringBuilder screen = new StringBuilder();
        screen.append("\n");

        for (int i = 0; i < getLines().size(); i++) {
            line = getLines().get(i);
            if (line.contains("noop")) {
                sum += computeScreenPixel(screen, cycle, 1, i);
                ++cycle;
            } else {
                sum += computeScreenPixel(screen, cycle, 2, i-1);
                cycle += 2;
            }
        }

        System.out.println(screen);

        return screen.toString();
    }

    private long computeScreenPixel(StringBuilder screen, int cycle, int increase, int index) {
        int X = getXto(index);
        int CRTLocation;
        System.out.println(X);
        int i;

        for (i = cycle + 1 ; i <= cycle + increase; i++) {
            CRTLocation = (i % 40);
            if (Math.abs(CRTLocation-X-1) <= 1)
                screen.append("#");
            else screen.append(" ");

            if (i % 40 == 0) {
                screen.append("\n");
            }
        }

        return 0;
    }
}
