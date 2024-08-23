package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SearchIndexInArray<T> extends RecursiveTask<Integer> {
    private T[] array;
    private int from;
    private int to;
    private T value;

    public SearchIndexInArray(T[] array, int from, int to, T value) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.value = value;
    }

    @Override
    protected Integer compute() {
        if (to - from < 10) {
            return linerSearch();
        }

        int middle = (from + to) / 2;

        SearchIndexInArray<T> leftTask = new SearchIndexInArray<>(array, from, middle, value);
        SearchIndexInArray<T> rightSort = new SearchIndexInArray<>(array, middle + 1, to, value);

        leftTask.fork();
        rightSort.fork();

        int left = leftTask.join();
        int right = rightSort.join();

        return Math.max(left, right);
    }

    private Integer linerSearch() {
        int result = 0;
        for (int i = from; i < to; i++) {
            if (array[i].equals(value)) {
                result = i;
                break;
            }
        }
        return result;
    }

    public static <T> Integer parallelSearch(T[] array, T value) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new SearchIndexInArray<>(array, 0, array.length, value));
    }
}
