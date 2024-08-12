package ru.job4j.thread;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        var startAt = System.currentTimeMillis();
        try (InputStream input = new URL(url).openStream();
             OutputStream output = new FileOutputStream("output.xml")) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            var dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                var downloadAt = System.nanoTime();
                output.write(dataBuffer, 0, bytesRead);
                var timeDownloadPacket = System.nanoTime() - downloadAt;
                System.out.println("Read 1024 bytes : " + timeDownloadPacket + " nano.");

                var speedDownloadPacket = (dataBuffer.length / timeDownloadPacket) * 1000000;
                if (speed == 1000) {
                    var timeSleep = speedDownloadPacket / speed;
                    Thread.sleep(timeSleep);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
