package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RowColSumTest {
    @Test
    public void testRowColSum() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        RowColSum.Sums[] sums = RowColSum.sum(matrix);

        assertEquals(6, sums[0].getRowSum());
        assertEquals(15, sums[1].getRowSum());
        assertEquals(24, sums[2].getRowSum());

        assertEquals(12, sums[0].getColSum());
        assertEquals(15, sums[1].getColSum());
        assertEquals(18, sums[2].getColSum());
    }

    @Test
    public void testRowColAsyncSum() throws ExecutionException, InterruptedException {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        RowColSum.Sums[] sums = RowColSum.asyncSum(matrix);

        assertEquals(6, sums[0].getRowSum());
        assertEquals(15, sums[1].getRowSum());
        assertEquals(24, sums[2].getRowSum());

        assertEquals(12, sums[0].getColSum());
        assertEquals(15, sums[1].getColSum());
        assertEquals(18, sums[2].getColSum());
    }
}