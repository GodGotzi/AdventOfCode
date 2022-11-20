package net.gotzi.adventofcode.year2020;

import net.gotzi.adventofcode.Day;

import java.util.List;

public class Day2 extends Day {

    public Day2() {
        super(2, 2020);
    }

    @Override
    public long computePuzzle1() {
        List<String> lines = getLines();
        String[] args;
        char scope;
        int upper;
        int lower;

        int sum = 0;

        for (String str : lines) {
            args = str.split(" ");
            scope = args[1].charAt(0);
            lower = Integer.parseInt(args[0].split("-")[0]);
            upper = Integer.parseInt(args[0].split("-")[1]);

            if (isValidPuzzle1(scope, lower, upper, args[2])) sum++;
        }

        return sum;
    }

    private boolean isValidPuzzle1(char scope, int lower, int upper, String pw) {
        char[] chars = pw.toCharArray();

        int limit = 0;

        for (char c : chars) {
            if (c == scope) limit++;
        }

        return limit <= upper && limit >= lower;
    }

    @Override
    public long computePuzzle2() {
        List<String> lines = getLines();
        String[] args;
        char scope;
        int upper;
        int lower;

        int sum = 0;

        for (String str : lines) {
            args = str.split(" ");
            scope = args[1].charAt(0);
            lower = Integer.parseInt(args[0].split("-")[0]);
            upper = Integer.parseInt(args[0].split("-")[1]);

            if (isValidPuzzle2(scope, lower, upper, args[2])) sum++;
        }

        return sum;
    }

    private boolean isValidPuzzle2(char scope, int lower, int upper, String pw) {
        char[] chars = pw.toCharArray();

        return chars.length >= upper && chars.length >= lower &&
                ((chars[lower - 1] == scope && chars[upper - 1] != scope) ||
                        (chars[lower - 1] != scope && chars[upper - 1] == scope));
    }
}