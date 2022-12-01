package net.gotzi.adventofcode;

import net.gotzi.adventofcode.api.DayInfo;
import net.gotzi.adventofcode.api.PuzzleInfo;
import net.gotzi.adventofcode.year2020.Day3;
import net.gotzi.adventofcode.year2022.Day1;

public class Loader {

    private static Day loadDay() {
        return new Day1();
    }

    private static void printPuzzle(PuzzleInfo info, int num) {
        System.out.println("Puzzle " + num);
        System.out.printf("Computing time: %d%n", info.millis());
        System.out.printf("SOLUTION: %d%n", info.solution());
    }

    public static void main(String[] args) {
        Day day = loadDay();


        DayInfo dayInfo = day.run();

        System.out.println("~".repeat(50));
        System.out.printf("Day %d Year %d%n", dayInfo.day(), dayInfo.year());
        System.out.println("~".repeat(50));
        System.out.print("\n");
        printPuzzle(dayInfo.puzzle1(), 1);
        System.out.print("\n");
        System.out.println("-".repeat(50));
        System.out.print("\n");
        printPuzzle(dayInfo.puzzle2(), 2);
        System.out.print("\n");
        System.out.println("~".repeat(50));
        System.out.printf("Sum of computing time: %d%n", dayInfo.millis());
        System.out.println("~".repeat(50));
    }

}
