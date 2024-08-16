package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {

    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent() throws IOException {
        return content(data -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return content(data -> data < 0x80);
    }

    private String content(Predicate<Character> filter) throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedReader input = new BufferedReader(new FileReader(file))) {
            int data;
            while ((data = input.read()) > 0) {
                char one = (char) data;
                if (filter.test(one)) {
                    output.append(one);
                }
            }
        }
        return output.toString();
    }

}
