package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RowColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;


        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        int arrayLength = matrix.length;
        Sums[] sums = new Sums[arrayLength];

        for (int i = 0; i < arrayLength; i++) {
            sums[i] = new Sums();
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j < arrayLength; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }
            sums[i].rowSum = rowSum;
            sums[i].colSum = colSum;
        }

        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int arrayLength = matrix.length;
        Sums[] sums = new Sums[arrayLength];
        CompletableFuture<Sums> completableFuture;

        for (int i = 0; i < arrayLength; i++) {
            int finalI = i;
            completableFuture = CompletableFuture.supplyAsync(() -> {
                Sums sums1 = new Sums();
                int rowSum = 0;
                int colSum = 0;
                for (int j = 0; j < arrayLength; j++) {
                    rowSum += matrix[finalI][j];
                    colSum += matrix[j][finalI];
                }
                sums1.setRowSum(rowSum);
                sums1.setColSum(colSum);
                return sums1;
            });

            sums[i] = completableFuture.get();
        }
        return sums;
    }

}