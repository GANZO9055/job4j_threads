package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RowColSumTest {
    @Test
    public void testRowColSum() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        Sums[] sums = RowColSum.sum(matrix);
        Sums[] result = {
                new Sums(6, 12),
                new Sums(15, 15),
                new Sums(24, 18)
        };

        assertArrayEquals(sums, result);
    }

    @Test
    public void testRowColAsyncSum() throws ExecutionException, InterruptedException {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        Sums[] sums = RowColSum.asyncSum(matrix);
        Sums[] result = {
                new Sums(6, 12),
                new Sums(15, 15),
                new Sums(24, 18)
        };

        assertArrayEquals(sums, result);
    }
}