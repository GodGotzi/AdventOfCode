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

        for (String line : getLines()) {
            char direction = line.charAt(0);
            int amount = Integer.parseInt(line.split(" ")[1]);

            for (int i = 0; i < amount; i++) {
                switch (direction) {
                    case 'R' -> head.colum += 1;
                    case 'L' -> head.colum -= 1;
                    case 'D' -> head.row -= 1;
                    case 'U' -> head.row += 1;
                }

                moveTail(tail, head);

                if (needsPoint(points, tail))
                    points.add(new Point(tail.row, tail.colum));
            }
        }

        return (long) points.size();
    }

    private void moveTail(Point tail, Point head) {
        if (!isTouching(tail, head)) {
            tail.row += head.row == tail.row ? 0 : (head.row- tail.row) / Math.abs(head.row-tail.row);
            tail.colum += head.colum == tail.colum ? 0 : (head.colum- tail.colum) / Math.abs(head.colum-tail.colum);
        }
    }

    private boolean isTouching(Point tail, Point head) {
        return Math.abs(head.row- tail.row) <= 1 && Math.abs(head.colum-tail.colum) <= 1;
    }

    private void moveRope(Point[] knots) {
        for (int i = 1; i < knots.length; i++) {
            moveTail(knots[i], knots[i-1]);
        }
    }

    private boolean needsPoint(List<Point> points, Point point) {
        return points.stream().noneMatch(p -> point.colum == p.colum && point.row == p.row);
    }

    @Override
    public Long computePuzzle2() {
        Point[] knots = new Point[10];
        Point tail;

        for (int i = 0; i < knots.length; i++) {
            knots[i] = new Point(0, 0);
        }

        List<Point> points = new ArrayList<>();

        for (String line : getLines()) {
            char direction = line.charAt(0);
            int amount = Integer.parseInt(line.split(" ")[1]);

            for (int i = 0; i < amount; i++) {
                switch (direction) {
                    case 'R' -> knots[0].colum += 1;
                    case 'L' -> knots[0].colum -= 1;
                    case 'D' -> knots[0].row -= 1;
                    case 'U' -> knots[0].row += 1;
                }

                moveRope(knots);

                if (needsPoint(points, knots[9]))
                    points.add(new Point(knots[9].row, knots[9].colum));
            }
        }

        return (long) points.size();
    }

    public static class Point {
        private int row;
        private int colum;

        public Point(int row, int colum) {
            this.row = row;
            this.colum = colum;
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
}
