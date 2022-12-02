package net.gotzi.adventofcode.year2022;

import net.gotzi.adventofcode.Day;

public class Day2 extends Day {

    public Day2() {
        super(2, 2022);
    }

    @Override
    public long computePuzzle1() {
        int totalScore = 0;

        for (String line : getLines()) {
            String[] split = line.split(" ");
            char opponent = split[0].charAt(0);
            char me = split[1].charAt(0);

            totalScore += me - 'X' + 1;
            totalScore += checkWin(opponent - 'A', me - 'X');
        }

        return totalScore;
    }

    private int checkWin(int opponent, int me) {

        if (opponent == me)
            return 3;
        else if (opponent - me == -1 || (opponent == 2 && me == 0))
            return 6;
        else return 0;
    }

    @Override
    public long computePuzzle2() {
        int totalScore = 0;

        for (String line : getLines()) {
            String[] split = line.split(" ");
            char opponent = split[0].charAt(0);
            char me = split[1].charAt(0);

            int winScore = (me - 'X') * 3;
            totalScore += winScore;

            for (int i = 0; i < 3; i++) {
                if (checkWin(opponent - 'A', i) == winScore) {
                    totalScore += i + 1;
                    break;
                }
            }
        }

        return totalScore;
    }
}
