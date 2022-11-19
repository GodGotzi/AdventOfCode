package net.gotzi.adventofcode;

import net.gotzi.adventofcode.api.DayInfo;
import net.gotzi.adventofcode.api.PuzzleInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public abstract class Day {

    private final int day;
    private final int year;

    private List<String> lines;

    public Day(int day, int year) {
        this.day = day;
        this.year = year;

        File file = createFileStructure();
        try {
            load(file);
        } catch (FileNotFoundException e) {}
    }

    private void load(File file) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        this.lines = reader.lines().toList();
    }

    private File createFileStructure() {
        File dayFile;

        try {
            File inputFolder = new File("input");
            if (!inputFolder.exists()) inputFolder.mkdirs();

            File folder = new File("input/" + year);
            if (!folder.exists()) folder.mkdirs();

            dayFile = new File("input/" + year + "/day" + day + ".txt");
            if (!dayFile.exists()) dayFile.createNewFile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return dayFile;
    }

    protected DayInfo run() {

        long milli = System.currentTimeMillis();

        int puzzle1Solution = computePuzzle1();
        long puzzle1Time = System.currentTimeMillis() - milli;

        int puzzle2Solution = computePuzzle2();
        long puzzle2Time = System.currentTimeMillis() - puzzle1Time - milli;

        long timeSum = System.currentTimeMillis() - milli;

        DayInfo dayInfo = new DayInfo(this.day, this.year, new PuzzleInfo(puzzle1Time, puzzle1Solution),
                new PuzzleInfo(puzzle2Time, puzzle2Solution), timeSum);

        return dayInfo;
    }

    public List<String> getLines() {
        return lines;
    }

    public abstract int computePuzzle1();

    public abstract int computePuzzle2();

}
