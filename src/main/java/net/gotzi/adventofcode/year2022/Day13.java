package net.gotzi.adventofcode.year2022;

import net.gotzi.adventofcode.Day;
import net.gotzi.adventofcode.api.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Day13 extends Day<Integer> {
    public Day13() {
        super(13, 2022);
    }

    @Override
    public Integer computePuzzle1() {
        List<Pair<Element, Element>> packets = new ArrayList<>();
        String line;
        Element left;
        Element right;

        for (int i = 0; i <getLines().size(); i++) {
            line = getLines().get(i);
            if (line.contains("[")) {
                left = loadElement(line);
                right = loadElement(getLines().get(++i));
                packets.add(new Pair<>(left, right));
            }
        }

        int sum = 0;
        Pair<Element, Element> packet;

        for (int i = 0; i < packets.size(); i++) {
            packet = packets.get(i);
            if (compare(packet.value1(), packet.value2()) == 1) {
                sum += i+1;
            }
        }

        return sum;
    }

    private int compare(Element left, Element right) {
        if (left.value == -1 || right.value == -1) {
            List<Element> rightList;
            List<Element> leftList;

            if (left.value == -1 && right.value != -1) {
                rightList = new ArrayList<>();
                rightList.add(right);
                leftList = new ArrayList<>(left.elements);
            } else if (left.value != -1) {
                leftList = new LinkedList<>();
                leftList.add(left);
                rightList = new ArrayList<>(right.elements);
            } else {
                rightList = new ArrayList<>(right.elements);
                leftList = new ArrayList<>(left.elements);
            }

            int i = 0;
            while (i < leftList.size() && i < rightList.size()) {
                int ret = compare(leftList.get(i), rightList.get(i));
                if (ret == 1) return 1;
                if (ret == -1) return -1;

                i++;
            }


            if (i == leftList.size()) {
                if (rightList.size() == leftList.size())
                    return 0;
                return 1;
            }

            return -1;

        } else {
            if (left.value < right.value)
                return 1;
            else if (left.value == right.value)
                return 0;

            return -1;
        }
    }

    private Element loadElement(String line) {
        boolean number = false;
        Element parent = new Element(null);
        Element current = parent;
        Element newElement;

        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '[') {
                if (i != 0) {
                    newElement = new Element(current);
                    current.elements.add(newElement);
                    current = newElement;
                }

                number = true;
            } else if (line.charAt(i) == ']') {
                current = current.parent;
                number = false;
            } else if (line.charAt(i) == ',') {
                number = true;
            } else if (number) {
                int length = getNumberLength(i, line);
                String numberStr = line.substring(i, i + length);
                newElement = new Element(current);
                newElement.setValue(Integer.parseInt(numberStr));
                current.elements.add(newElement);
                number = false;
            }
        }

        return parent;
    }

    private int getNumberLength(int i, String line) {
        int index = 0;

        do {
            index++;
        } while (line.charAt(i + index) >= '0' && line.charAt(i + index) <= '9');

        return index;
    }

    public static class Element {

        private final Element parent;
        private final List<Element> elements = new ArrayList<>();

        private int value = -1;

        public Element(Element parent) {
            this.parent = parent;
        }

        public void setValue(int value) {
            this.value = value;
        }

        @Override
        public String toString() {

            if (elements.isEmpty() && value != -1)
                return String.valueOf(value);
            else return elements.toString();
        }
    }



    @Override
    public Integer computePuzzle2() {
        List<Element> packets = new ArrayList<>();
        String line;

        for (int i = 0; i <getLines().size(); i++) {
            line = getLines().get(i);
            if (line.contains("[")) {
                packets.add(loadElement(line));
            }
        }

        Element element1 = new Element(null);
        Element _element1 = new Element(null);
        _element1.value = 2;
        element1.elements.add(_element1);

        Element element2 = new Element(null);
        Element _element2 = new Element(null);
        _element2.value = 6;
        element2.elements.add(_element2);

        packets.add(element1);
        packets.add(element2);

        packets.sort((o1, o2) -> Day13.this.compare(o2, o1));

        return (packets.indexOf(element1) + 1) * (packets.indexOf(element2) + 1);
    }
}
