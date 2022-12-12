package net.gotzi.adventofcode.year2022;

import net.gotzi.adventofcode.Day;

import java.util.*;
import java.util.List;

public class Day12 extends Day<Integer> {
    public Day12() {
        super(12, 2022);
    }

    @Override
    public Integer computePuzzle1() {
        char[][] grid = new char[getLines().size()][getLines().get(0).length()];
        Point start = null;
        Point end = null;
        for (int i = 0; i < getLines().size(); i++) {
            grid[i] = getLines().get(i).toCharArray();
            for (int j = 0; j < getLines().get(i).length(); j++) {
                if (grid[i][j] == 'S') {
                    grid[i][j] = 'a';
                    start = new Point(i, j, 'a');
                } else if (grid[i][j] == 'E') {
                    grid[i][j] = 'z';
                    end = new Point(i, j, 'z');
                }

            }
        }

        return computeDijkstra(grid, start, end);
    }

    private int computeDijkstra(char[][] grid, Point start, Point end) {
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        for (int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited[0].length; j++) {
                visited[i][j] = false;
            }
        }

        PriorityQueue<QueueItem> queue = new PriorityQueue<>();
        queue.add(new QueueItem(start, 0));
        QueueItem queueItem;

        while (!(Objects.requireNonNull(queueItem = queue.poll())).point.equals(end)) {

            if (!visited[queueItem.point.i][queueItem.point.j]) {
                visited[queueItem.point.i][queueItem.point.j] = true;

                for (Point neighbor : neighbors(grid, queueItem.point)) {
                    queue.offer(new QueueItem(neighbor, queueItem.steps + 1));
                }
            }
        }

        return queueItem.steps;
    }

    private int computeDijkstraPuzzle2(char[][] grid, Point end) {
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        for (int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited[0].length; j++) {
                visited[i][j] = false;
            }
        }

        PriorityQueue<QueueItem> queue = new PriorityQueue<>();
        queue.add(new QueueItem(end, 0));
        QueueItem queueItem;

        while ((Objects.requireNonNull(queueItem = queue.poll())).point.height != 'a') {
            if (!visited[queueItem.point.i][queueItem.point.j]) {
                visited[queueItem.point.i][queueItem.point.j] = true;

                for (Point neighbor : neighborsPuzzle2(grid, queueItem.point)) {
                    queue.offer(new QueueItem(neighbor, queueItem.steps + 1));
                }
            }
        }

        return queueItem.steps;
    }

    private boolean isOnGrid(char[][] grid, int i, int j) {
        return i < grid.length && i >= 0 && j < grid[0].length && j >= 0;
    }

    private List<Point> neighbors(char[][] grid, Point point) {
        List<Point> points = new ArrayList<>();
        int iNew;
        int jNew;

        for (int[] d : new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}}) {
            iNew = point.i + d[0];
            jNew = point.j + d[1];

            if (isOnGrid(grid, iNew, jNew) && grid[iNew][jNew] <= point.height+1)
                points.add(new Point(iNew, jNew, grid[iNew][jNew]));
        }

        return points;
    }

    private List<Point> neighborsPuzzle2(char[][] grid, Point point) {
        List<Point> points = new ArrayList<>();
        int iNew;
        int jNew;

        for (int[] d : new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}}) {
            iNew = point.i + d[0];
            jNew = point.j + d[1];

            if (isOnGrid(grid, iNew, jNew) && grid[iNew][jNew]+1 >= point.height)
                points.add(new Point(iNew, jNew, grid[iNew][jNew]));
        }

        return points;
    }

    @Override
    public Integer computePuzzle2() {
        char[][] grid = new char[getLines().size()][getLines().get(0).length()];
        Point end = null;
        for (int i = 0; i < getLines().size(); i++) {
            grid[i] = getLines().get(i).toCharArray();
            for (int j = 0; j < getLines().get(i).length(); j++) {
                if (grid[i][j] == 'S') {
                    grid[i][j] = 'a';
                } else if (grid[i][j] == 'E') {
                    grid[i][j] = 'z';
                    end = new Point(i, j, 'z');
                }

            }
        }

        return computeDijkstraPuzzle2(grid, end);
    }

    public record QueueItem(Point point, int steps) implements Comparable<QueueItem> {

        @Override
        public int compareTo(QueueItem o) {
            return Integer.compare(steps, o.steps);
        }

        @Override
        public String toString() {
            return "QueueItem{" +
                    "point=" + point +
                    ", steps=" + steps +
                    '}';
        }
    }

    public record Point(int i, int j, char height) {

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return i == point.i && j == point.j && height == point.height;
        }

        @Override
        public int hashCode() {
            return Objects.hash(i, j);
        }

    }
}
