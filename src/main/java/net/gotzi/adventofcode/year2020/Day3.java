package net.gotzi.adventofcode.year2020;

import net.gotzi.adventofcode.Day;

import java.util.List;

public class Day3 extends Day<Long> {

    public Day3() {
        super(3, 2020);
    }

    @Override
    public Long computePuzzle1() {
        final List<String> lines = getLines();
        char[][] grid = new char[lines.size()][lines.get(0).length()];

        for (int i = 0; i < grid.length; i++) {
            grid[i] = lines.get(i).toCharArray();
        }

        return (long) encounter(grid, 1, 3);
    }

    private int encounter(char[][] grid, int right, int down) {
        int i = 0;
        int j = 0;
        int sum = 0;

        while (i != grid.length-1) {
            i += right;
            j += down;

            i %= grid.length;
            j %= grid[0].length;

            if (grid[i][j] == '#')
                sum++;
        }

        return sum;
    }

    @Override
    public Long computePuzzle2() {
        final List<String> lines = getLines();
        char[][] grid = new char[lines.size()][lines.get(0).length()];

        for (int i = 0; i < grid.length; i++) {
            grid[i] = lines.get(i).toCharArray();
        }

        long product = encounter(grid, 1, 1);
        product *= encounter(grid, 1, 3);
        product *= encounter(grid, 1, 5);
        product *= encounter(grid, 1, 7);
        product *= encounter(grid, 2, 1);

        return product;
    }
}
