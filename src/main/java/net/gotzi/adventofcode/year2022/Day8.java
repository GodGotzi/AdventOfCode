package net.gotzi.adventofcode.year2022;

import net.gotzi.adventofcode.Day;

public class Day8 extends Day<Long> {
    public Day8() {
        super(8, 2022);
    }

    @Override
    public Long computePuzzle1() {
        long sum = 0;
        byte[][] grid = loadGrid();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (isEdge(grid, i, j) || isVisible(grid, i, j))
                    sum++;
            }
        }

        return sum;
    }

    private byte[][] loadGrid() {
        byte[][] grid = new byte[getLines().size()][getLines().get(0).length()];
        char[] line;


        for (int i = 0; i < getLines().size(); i++) {
            for (int j = 0; j < getLines().get(i).length(); j++) {
                line = getLines().get(i).toCharArray();
                grid[i][j] = (byte) (line[j] - '0');
            }
        }

        return grid;
    }

    private boolean isEdge(byte[][] grid, int i, int j) {
        return i == 0 || j == 0 || i == grid.length - 1 || j == grid[i].length - 1;
    }

    private boolean isVisible(byte[][] grid, int i, int j) {
        return isVisibleHorizontal(grid, i, j) || isVisibleVertical(grid, i, j);
    }

    private boolean isVisibleHorizontal(byte[][] grid, int i, int j) {
        boolean beforeFlag = false, afterFlag = false;

        for (int k = 0; k < grid[i].length; k++) {
            if (grid[i][k] >= grid[i][j] && k != j) {
                if (k < j)
                    beforeFlag = true;
                else afterFlag = true;
            }
        }

        return !beforeFlag || !afterFlag;
    }

    private boolean isVisibleVertical(byte[][] grid, int i, int j) {
        boolean beforeFlag = false, afterFlag = false;

        for (int k = 0; k < grid.length; k++) {
            if (grid[k][j] >= grid[i][j] && k != i) {
                if (k < i)
                    beforeFlag = true;
                else afterFlag = true;
            }
        }

        return !beforeFlag || !afterFlag;
    }

    @Override
    public Long computePuzzle2() {
        long maxScenicScore = 0;
        long scenicScore;
        byte[][] grid = loadGrid();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                scenicScore = getScenicScore(grid, i, j);
                if (scenicScore > maxScenicScore)
                    maxScenicScore = scenicScore;
            }
        }

        return maxScenicScore;
    }

    private long getScenicScore(byte[][] grid, int i, int j) {
        return getScenicScoreHorizontal(grid, i, j) * getScenicScoreVertical(grid, i, j);
    }

    private long getScenicScoreHorizontal(byte[][] grid, int i, int j) {
        long leftView = j, rightView = grid[0].length -1 -j;

        for (int k = 0; k < grid[i].length; k++) {
            if (grid[i][k] >= grid[i][j] && k != j) {
                if (k < j) {
                    leftView = j - k;
                } else if ((k - j) < rightView) {
                    rightView = k - j;
                }
            }
        }

        return rightView * leftView;
    }

    private long getScenicScoreVertical(byte[][] grid, int i, int j) {
        long upView = i, downView = grid.length -1 -i;

        for (int k = 0; k < grid.length; k++) {
            if (grid[k][j] >= grid[i][j] && k != i) {
                if (k < i) {
                    upView = i - k;
                } else if ((k - i) < downView)  {
                    downView = k - i;
                }
            }
        }

        return upView * downView;
    }
}
