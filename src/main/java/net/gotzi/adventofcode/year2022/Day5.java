package net.gotzi.adventofcode.year2022;

import net.gotzi.adventofcode.Day;

import java.util.*;

public class Day5 extends Day<String> {


    public Day5() {
        super(5, 2022);
    }

    @Override
    public String computePuzzle1() {
        List<String> commands = new ArrayList<>();
        List<Stack<Character>> stacks = new ArrayList<>();
        boolean stackAmountFound = false;

        for (String line : getLines()) {
            if (line.contains("move"))
                commands.add(line);
            else if (line.contains("1"))
                stackAmountFound = true;
            else if (!stackAmountFound) {

                for (int i = 0; i < line.toCharArray().length; i++) {

                    if (line.charAt(i) != '[' && line.charAt(i) != ']' && line.charAt(i) != ' ') {
                        while ((i-1)/3 >= stacks.size()) {
                            stacks.add(new Stack<>());
                        }

                        Stack<Character> stack = stacks.get((i-1)/3);
                        stack.push(line.charAt(i));
                    }
                }
            }
        }

        stacks.forEach(Collections::reverse);

        new ArrayList<>(stacks).forEach(stack -> {
            if (stack.isEmpty())
                stacks.remove(stack);
        });

        compute(commands, stacks);

        StringBuilder stringBuilder = new StringBuilder();
        stacks.forEach(stack -> {
            if (!stack.isEmpty())
                stringBuilder.append(stack.peek());
        });

        return stringBuilder.toString();
    }

    private void compute(List<String> commands, List<Stack<Character>> stacks) {
        for (String cmd : commands) {
            String[] split = cmd.split(" ");
            int move = Integer.parseInt(split[1]);
            int from = Integer.parseInt(split[3]);
            int to = Integer.parseInt(split[5]);

            for (int i = 0; i < move; i++) {
                if (!stacks.get(from - 1).isEmpty()) {
                    char ch = stacks.get(from-1).pop();
                    stacks.get(to-1).add(ch);
                }
            }

            //stacks.forEach(System.out::println);
        }
    }

    @Override
    public String computePuzzle2() {
        List<String> commands = new ArrayList<>();
        List<Stack<Character>> stacks = new ArrayList<>();
        boolean stackAmountFound = false;

        for (String line : getLines()) {
            if (line.contains("move"))
                commands.add(line);
            else if (line.contains("1"))
                stackAmountFound = true;
            else if (!stackAmountFound) {

                for (int i = 0; i < line.toCharArray().length; i++) {

                    if (line.charAt(i) != '[' && line.charAt(i) != ']' && line.charAt(i) != ' ') {
                        while ((i-1)/3 >= stacks.size()) {
                            stacks.add(new Stack<>());
                        }

                        Stack<Character> stack = stacks.get((i-1)/3);
                        stack.push(line.charAt(i));
                    }
                }
            }
        }

        stacks.forEach(Collections::reverse);

        new ArrayList<>(stacks).forEach(stack -> {
            if (stack.isEmpty())
                stacks.remove(stack);
        });

        compute2(commands, stacks);

        StringBuilder stringBuilder = new StringBuilder();
        stacks.forEach(stack -> {
            if (!stack.isEmpty())
                stringBuilder.append(stack.peek());
        });

        return stringBuilder.toString();
    }

    private void compute2(List<String> commands, List<Stack<Character>> stacks) {
        String[] split;
        int move;
        int from;
        int to;
        List<Character> stack;

        for (String cmd : commands) {
            split = cmd.split(" ");
            move = Integer.parseInt(split[1]);
            from = Integer.parseInt(split[3]);
            to = Integer.parseInt(split[5]);
            stack = new ArrayList<>();

            for (int i = 0; i < move; i++) {
                if (!stacks.get(from - 1).isEmpty()) {
                    char ch = stacks.get(from-1).pop();
                    stack.add(ch);
                }
            }

            Collections.reverse(stack);

            stacks.get(to-1).addAll(stack);
        }
    }
}
