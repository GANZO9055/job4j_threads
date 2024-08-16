package ru.job4j.io;

import java.io.*;

public class SaveFile {

    private final File file;

    public SaveFile(File file) {
        this.file = file;
    }

    public void saveContent(String content) throws IOException {
        BufferedWriter o = new BufferedWriter(new FileWriter(file));
        for (int i = 0; i < content.length(); i++) {
            o.write(content.charAt(i));
        }
    }
}
