package net.gotzi.adventofcode.year2022;

import net.gotzi.adventofcode.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day11 extends Day<Long> {
    public Day11() {
        super(11, 2022);
    }

    @Override
    public Long computePuzzle1() {
        List<Monkey> monkeys = load();

        for (int i = 0; i < 20; i++) {
            for (Monkey monkey : monkeys) {
                for (Item item : monkey.getItemList()) {
                    item.runOperationSimple(monkey.getOperation().op, monkey.getOperation().num);
                    item.runOperationSimple('/', 3);

                    monkey.moveItem(item, monkey.getTest().testSimple(item), monkeys);
                    monkey.inspections++;
                }

                monkey.itemList.clear();
            }
        }

        monkeys.sort((o1, o2) -> {
            if (o1.inspections > o2.getInspections())
                return -1;
            else if (o1.inspections < o2.getInspections())
                return 1;
            return 0;
        });

        return ((long) monkeys.get(0).inspections * monkeys.get(1).inspections);
    }

    private List<Monkey> load() {
        List<Monkey> monkeys = new ArrayList<>();
        String line;

        for (int i = 0; i < getLines().size(); i++) {
            line = getLines().get(i);

            if (line.contains("Monkey")) {
                List<Item> startingItems = new ArrayList<>();
                Operation operation;
                Test test;

                line = getLines().get(i + 1);
                line = line.split(":")[1].trim().replaceAll(",", "");
                Arrays.stream(line.split(" ")).forEach(worryLevel ->
                        startingItems.add(
                                new Item(Integer.parseInt(worryLevel))
                        )
                );

                line = getLines().get(i + 2);
                line = line.split(":")[1].trim();
                operation = Operation.loadOperation(line);

                test = Test.loadTest(getLines().get(i+3), getLines().get(i+4), getLines().get(i+5));

                monkeys.add(new Monkey(operation, test, startingItems));
            }
        }

        return monkeys;
    }

    @Override
    public Long computePuzzle2() {
        List<Monkey> monkeys = load();

        for (int i = 0; i < 10000; i++) {
            for (Monkey monkey : monkeys) {
                for (Item item : monkey.getItemList()) {
                    int monkeyIndex;

                    item.runOperation(monkey.getOperation().op, monkey.getOperation().num);
                    monkeyIndex = monkey.getTest().test(item);

                    monkey.moveItem(item, monkeyIndex, monkeys);
                    monkey.inspections++;
                }

                monkey.itemList.clear();
            }
        }

        monkeys.sort((o1, o2) -> {
            if (o1.inspections > o2.getInspections())
                return -1;
            else if (o1.inspections < o2.getInspections())
                return 1;
            return 0;
        });

        return ((long) monkeys.get(0).inspections * monkeys.get(1).inspections);
    }

    public static class Monkey {

        private final List<Item> itemList;
        private final Operation operation;
        private final Test test;

        private int inspections = 0;

        public Monkey(Operation operation, Test test, List<Item> items) {
            this.itemList = new ArrayList<>(items);
            this.operation = operation;
            this.test = test;

        }

        private void addItem(Item item) {
            itemList.add(item);
        }

        private void moveItem(Item item, int monkeyIndex, List<Monkey> monkeys) {
            Monkey monkey = monkeys.get(monkeyIndex);
            monkey.addItem(item);
        }

        public Operation getOperation() {
            return operation;
        }

        public List<Item> getItemList() {
            return itemList;
        }

        public Test getTest() {
            return test;
        }

        public int getInspections() {
            return inspections;
        }

        @Override
        public String toString() {
            return "Monkey{" +
                    "itemList=" + itemList +
                    ", inspections=" + inspections +
                    '}';
        }
    }

    public record Test(int divisor, int monkeyIndexTrue, int monkeyIndexFalse) {

        public int test(Item item) {
            long worryLevel = item.startWorryLevel;
            long opNum;

            for (Operation operation : item.operations) {
                if (operation.num == -1)
                    opNum = worryLevel;
                else opNum = operation.num;

                switch (operation.op) {
                    case '+' -> worryLevel += opNum;
                    case '*' -> worryLevel *= opNum;
                    case '-' -> worryLevel -= opNum;
                    case '/' -> worryLevel /= opNum;
                }

                worryLevel = worryLevel % divisor;
            }

            if (worryLevel % divisor == 0)
                return monkeyIndexTrue;
            else return monkeyIndexFalse;
        }

        public int testSimple(Item item) {
            if (item.startWorryLevel % divisor == 0)
                return monkeyIndexTrue;
            else return monkeyIndexFalse;
        }

        public static Test loadTest(String... lines) {
            String test = lines[0].split(":")[1].trim();
            int divisor = Integer.parseInt(test.split(" ")[2]);

            String ifTrue = lines[1].split(":")[1].trim();
            int monkeyIndexTrue = Integer.parseInt(ifTrue.split(" ")[3]);

            String ifFalse = lines[2].split(":")[1].trim();
            int monkeyIndexFalse = Integer.parseInt(ifFalse.split(" ")[3]);

            return new Test(divisor, monkeyIndexTrue, monkeyIndexFalse);
        }
    }

    public record Operation(char op, long num) {

        public static Operation loadOperation(String str) {
            return new Operation(
                    str.split(" ")[3].charAt(0),
                    Operation.loadWorryLevel(str.split(" ")[4])
            );
        }

        private static int loadWorryLevel(String str) {
            if (str.contains("old"))
                return -1;
            else return Integer.parseInt(str);
        }

    }

    public static class Item {

        private long startWorryLevel;
        private final List<Operation> operations;

        public Item(long startWorryLevel) {
            this.startWorryLevel = startWorryLevel;
            this.operations = new ArrayList<>();
        }

        public void runOperation(char operation, long num) {
            operations.add(new Operation(operation, num));
        }

        public void runOperationSimple(char operation, long num) {
            if (num == -1)
                num = startWorryLevel;

            switch (operation) {
                case '+' -> startWorryLevel += num;
                case '*' -> startWorryLevel *= num;
                case '-' -> startWorryLevel -= num;
                case '/' -> startWorryLevel /= num;
            }
        }

        @Override
        public String toString() {
            return "Item{" +
                    "worryLevel=" + startWorryLevel +
                    '}';
        }
    }
}
