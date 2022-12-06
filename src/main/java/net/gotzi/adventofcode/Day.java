package net.gotzi.adventofcode;

import net.gotzi.adventofcode.api.DayInfo;
import net.gotzi.adventofcode.api.PuzzleInfo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public abstract class Day<T> {

    private final int day;
    private final int year;

    private List<String> lines;
    private String content;

    public Day(int day, int year) {
        this.day = day;
        this.year = year;

        File file = createFileStructure();
        try {
            load(file);
        } catch (IOException ignored) { }
    }

    private void load(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        this.lines = reader.lines().toList();

        Path path = Path.of(file.toURI());
        this.content = Files.readString(path);
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

        T puzzle1Solution = computePuzzle1();
        long puzzle1Time = System.currentTimeMillis() - milli;

        T puzzle2Solution = computePuzzle2();
        long puzzle2Time = System.currentTimeMillis() - puzzle1Time - milli;

        long timeSum = System.currentTimeMillis() - milli;

        return new DayInfo<>(this.day, this.year, new PuzzleInfo<>(puzzle1Time, puzzle1Solution),
                new PuzzleInfo<>(puzzle2Time, puzzle2Solution), timeSum);
    }

    public List<String> getLines() {
        return lines;
    }

    public String getContent() {
        return content;
    }

    public abstract T computePuzzle1();

    public abstract T computePuzzle2();

}
