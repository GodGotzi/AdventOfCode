package net.gotzi.adventofcode.api;

public record DayInfo<T>(int day, int year, PuzzleInfo<T> puzzle1, PuzzleInfo<T> puzzle2, long millis) {
}

