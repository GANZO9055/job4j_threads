package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {

    private final List<Thread> threads = new LinkedList<>();
    private final int size = Runtime.getRuntime().availableProcessors();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);

    public ThreadPool() {
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(
                    () -> {
                        try {
                            while (!Thread.currentThread().isInterrupted()) {
                                Runnable runnable = tasks.poll();
                                if (runnable != null) {
                                    runnable.run();
                                }
                            }
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
            );
            thread.start();
            threads.add(thread);

        }
    }

    /*
    этот метод должен добавлять задачи в блокирующую очередь tasks
     */
    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    /*
    этот метод завершит все запущенные задачи
     */
    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();

        for (int i = 0; i < 100; i++) {
            threadPool.work(
                    () -> System.out.println(
                            Thread.currentThread().getName() + " working on task"
                    )
            );
        }

        threadPool.shutdown();
    }
}
