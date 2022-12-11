package net.gotzi.adventofcode.year2022;

import net.gotzi.adventofcode.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day9 extends Day<Long> {
    public Day9() {
        super(9, 2022);
    }

    @Override
    public Long computePuzzle1() {
        Point tail = new Point(0, 0);
        Point head = new Point(0, 0);
        List<Point> points = new ArrayList<>();
        Point lastHeadPoint;

        for (String line : getLines()) {
            char direction = line.charAt(0);
            int amount = line.charAt(2) - '0';

            for (int i = 0; i < amount; i++) {
                lastHeadPoint = new Point(head.row, head.colum);

                switch (direction) {
                    case 'R' -> head.colum += 1;
                    case 'L' -> head.colum -= 1;
                    case 'D' -> head.row -= 1;
                    case 'U' -> head.row += 1;
                }
                
                tail = calculateNewTailPoint(tail, lastHeadPoint, head);
                        
                if (!containsPoint(points, tail))
                    points.add(tail);
            }
        }

        int size = getHighestCoordinate(points) + 2;
        this.printGrid(points, size);

        return (long) points.size();
    }

    private void printGrid(List<Point> points, int highestScore) {
        char[][] grid = new char[highestScore*2][highestScore*2];

        points.forEach(point -> grid[point.row + highestScore][point.colum + highestScore] = '#');

        for (int i = grid.length-1; i >= 0; i--) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print((grid[i][j] == '#' ? '#' : ' '));
            }

            System.out.print("\n");
        }
    }

    private int getHighestCoordinate(List<Point> points) {
        int max = 0;

        for (Point p : points) max = Math.max(Math.max(p.row, p.colum), max);

        return max;
    }

    private Point calculateNewTailPoint(Point tailPoint, Point lastHeadPoint, Point newHeadPoint) {
        Point tail = new Point(tailPoint.row, tailPoint.colum);

        if (Math.abs(newHeadPoint.row-tailPoint.row) + Math.abs(newHeadPoint.colum -tailPoint.colum) > 2) {
            tail.row = lastHeadPoint.row;
            tail.colum = lastHeadPoint.colum;
        } else if (Math.abs(newHeadPoint.colum-tailPoint.colum) > 1 || Math.abs(tailPoint.colum-newHeadPoint.colum) > 1)
            tail.colum += tailPoint.colum < newHeadPoint.colum ? 1 : -1;
        else if (Math.abs(newHeadPoint.row-tailPoint.row) > 1 || Math.abs(tailPoint.row-newHeadPoint.row) > 1)
            tail.row += tailPoint.row < newHeadPoint.row ? 1 : -1;

        return tail;
    }

    private boolean containsPoint(List<Point> points, Point point) {
        return points.stream().anyMatch(p -> point.colum == p.colum && point.row == p.row);
    }

    public static class Point {
        private int row;
        private int colum;

        public Point(int row, int colum) {
            this.row = row;
            this.colum = colum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return row == point.row && colum == point.colum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, colum);
        }

        @Override
        public String toString() {
            return "Point{" +
                    "row=" + row +
                    ", colum=" + colum +
                    '}';
        }
    }

    @Override
    public Long computePuzzle2() {
        return null;
    }
}
