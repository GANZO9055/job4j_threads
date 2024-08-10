package ru.job4j.concurrent;

public class Wget {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    try {
                        int i = 0;
                        System.out.println("Начало загрузки.");
                        while (i++ < 100) {
                            Thread.sleep(1000);
                            System.out.print("\rЗагрузка : " + i  + "%");
                        }
                        System.out.println();
                        System.out.println("Загрузка завершена.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        thread.start();
    }
}
