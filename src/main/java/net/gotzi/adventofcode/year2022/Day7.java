package net.gotzi.adventofcode.year2022;

import net.gotzi.adventofcode.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Day7 extends Day<Long> {
    public Day7() {
        super(7, 2022);
    }

    @Override
    public Long computePuzzle1() {
        Directory directory = new Directory("/", null);
        loadFileTree(directory);


        return sumUpDirectories(directory);
    }

    private boolean isDirExisting(Directory directory, String dirName) {
        return directory.getElements().stream()
                .anyMatch(element -> element instanceof Directory dir && dir.getName().equals(dirName));
    }

    private boolean isFileExisting(Directory directory, String dirName) {
        return directory.getElements().stream()
                .anyMatch(element -> element instanceof File file && file.getName().equals(dirName));
    }

    private Directory cdCMD(Directory directory, String line) {
        String[] args = line.split(" ");


        if (args[2].equals("..") && directory.getParent() != null) {
            directory = directory.getParent();
        } else {
            Optional<Element> optionalDirectory =
                    directory.getElements().stream()
                            .filter(element -> element instanceof Directory dir && dir.getName().equals(args[2]))
                            .findFirst();

            if (optionalDirectory.isPresent())
                directory = (Directory) optionalDirectory.get();
            else {
                Directory newDir = new Directory(args[2], directory);
                directory.getElements().add(newDir);
                directory = newDir;
            }
        }

        return directory;
    }

    private void lsCMD(Directory directory, int i) {
        int j = i + 1;
        String[] args;

        while (j < getLines().size() && !getLines().get(j).contains("$")) {
            args = getLines().get(j).split(" ");
            if (getLines().get(j).contains("dir")) {
                if (!isDirExisting(directory, args[1])) {
                    directory.getElements().add(
                            new Directory(args[1], directory)
                    );
                }
            } else {
                if (!isFileExisting(directory, args[1])) {
                    long size = Long.parseLong(args[0]);

                    directory.getElements().add(
                            new File(args[1], size)
                    );

                    directory.addSize(size);
                }
            }

            j++;
        }
    }

    private long sumUpDirectories(Directory parentDirectory) {
        long size = 0;

        for (Element element : parentDirectory.getElements()) {
            if (element instanceof Directory dir) {
                if (dir.size <= 100000) {
                    size += dir.size;
                }

                size += sumUpDirectories(dir);
            }
        }

        return size;
    }

    @Override
    public Long computePuzzle2() {
        Directory directory = new Directory("/", null);
        loadFileTree(directory);

        return findDirectoryToDelete(30000000 - (70000000 - directory.size), directory);
    }

    private void loadFileTree(Directory directory) {
        String line;

        for (int i = 0; i < getLines().size(); i++) {
            line = getLines().get(i);

            if (line.contains("$") && !line.equals("$ cd /")) {
                if (line.contains("ls")) {
                    lsCMD(directory, i);
                } else if (line.contains("cd")) {
                    directory = cdCMD(directory, line);
                }
            }
        }
    }

    private long findDirectoryToDelete(long neededSpace, Directory dir) {
        long leastSpace = 70000000;
        long space;

        for (Element element : dir.getElements()) {
            if (element instanceof Directory newdir) {
                space = findDirectoryToDelete(neededSpace, newdir);
                if (neededSpace <= newdir.size && leastSpace > newdir.size) {
                    leastSpace = newdir.size;
                }

                if (neededSpace <= space && leastSpace > space) {
                    leastSpace = space;
                }
            }
        }

        return leastSpace;
    }

    public static class Element {

        private final String name;

        public Element(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static class Directory extends Element {

        private final List<Element> elements = new ArrayList<>();
        private long size;

        private final Directory parent;

        public Directory(String name, Directory parent) {
            super(name);
            this.parent = parent;
        }

        public List<Element> getElements() {
            return elements;
        }

        public void addSize(long size) {
            if (this.parent != null)
                this.parent.addSize(size);
            this.size = this.size + size;
        }

        public Directory getParent() {
            return parent;
        }

        @Override
        public String toString() {
            return "Directory{" +
                    "elements=" + elements +
                    ", size=" + size +
                    ", name='" + super.name + '\'' +
                    '}';
        }
    }

    public static class File extends Element {

        private final long size;

        public File(String name, long size) {
            super(name);

            this.size = size;
        }

        @Override
        public String toString() {
            return "File{" +
                    "size=" + size +
                    ", name='" + super.name + '\'' +
                    '}';
        }
    }
}
