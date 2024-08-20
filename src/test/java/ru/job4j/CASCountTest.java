package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CASCountTest {
    @Test
    void whenWorkTwoThreads() throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread firstThread = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        casCount.increment();
                    }
                }
        );
        Thread secondThread = new Thread(
                () -> {
                    for (int i = 0; i < 100; i++) {
                        casCount.increment();
                    }
                }
        );

        firstThread.start();
        secondThread.start();

        firstThread.join();
        secondThread.join();

        assertThat(casCount.get()).isEqualTo(110);
    }
}