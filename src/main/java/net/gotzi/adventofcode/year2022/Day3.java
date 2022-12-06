package net.gotzi.adventofcode.year2022;

import net.gotzi.adventofcode.Day;

public class Day3 extends Day<Long> {

    public Day3() {
        super(3, 2022);
    }

    @Override
    public Long computePuzzle1() {
        int priorities = 0;
        int priority;

        for (String line : getLines()) {
            for (int i = 0; i < line.length(); i++) {
                if (i < line.length()/2) {
                    priority = compareCompartment(line.substring(line.length()/2), line.charAt(i));
                } else {
                    priority = compareCompartment(line.substring(0, line.length()/2 - 1), line.charAt(i));
                }

                if (priority != 0) {
                    priorities += priority;
                    break;
                }
            }
        }

        return (long) priorities;
    }

    private int compareCompartment(String compare, char charCompare) {
        if (compare.indexOf(charCompare) != -1)
            return getPriority(charCompare);

        return 0;
    }

    private int getPriority(char charCompare) {
        if (Character.isLowerCase(charCompare)) {
            return charCompare - 'a' + 1;
        } else {
            return charCompare - 'A' + 27;
        }
    }

    @Override
    public Long computePuzzle2() {
        int priorities = 0;
        String charsLeft;

        for (int i = 0; i < getLines().size(); i += 3) {
            charsLeft = getSameChars(getLines().get(i), getLines().get(i + 1));
            charsLeft = getSameChars(charsLeft, getLines().get( i + 2));

            priorities += getPriority(charsLeft.charAt(0));
        }

        return (long) priorities;
    }

    private String getSameChars(String elve1, String elve2) {
        int[] intArray = elve1.chars().filter(ch -> elve2.indexOf(ch) != -1).toArray();
        char[] charArray = new char[intArray.length];

        for (int i = 0; i < intArray.length; i++) {
            charArray[i] = (char) intArray[i];
        }

        return new String(charArray);
    }
}
