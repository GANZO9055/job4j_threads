package ru.job4j.thread;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String out;

    public Wget(String url, int speed, String out) {
        this.url = url;
        this.speed = speed;
        this.out = out;
    }

    @Override
    public void run() {
        var startAt = System.currentTimeMillis();
        try (InputStream input = new URL(url).openStream();
             OutputStream output = new FileOutputStream(out)) {
            var startTime = System.currentTimeMillis();
            var dataBuffer = new byte[1024];
            int bytesRead;
            int downloadBytes = 0;
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                output.write(dataBuffer, 0, bytesRead);
                downloadBytes += bytesRead;

                if (downloadBytes >= speed) {
                    var timeDownload = System.currentTimeMillis() - startTime;

                    if (timeDownload < 1000) {
                        Thread.sleep(1000 - timeDownload);
                    }

                    downloadBytes = 0;
                    startTime = System.currentTimeMillis();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 3) {
            throw new IllegalArgumentException("Параметры не заданы!");
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String out = args[2];
        Thread wget = new Thread(new Wget(url, speed, out));
        wget.start();
        wget.join();
    }
}
