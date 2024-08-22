package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SearchIndexInArrayTest {

    @Test
    void SearchInArrayLess10() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        SearchIndexInArray search = new SearchIndexInArray<>(array, 0, 10, 8);

        Integer result  = search.compute();

        assertThat(result).isEqualTo(7);
    }

    @Test
    void SearchInArray() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};


        SearchIndexInArray search = new SearchIndexInArray<>(array, 0, 10, 11);

        Integer result  = search.compute();

        assertThat(result).isEqualTo(10);
    }

    @Test
    void whenTypesDifferent() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};


        SearchIndexInArray search = new SearchIndexInArray<>(array, 0, 10, "11");

        Integer result  = search.compute();

        assertThat(result).isEqualTo(0);
    }

    @Test
    void whenItemNotFound() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};


        SearchIndexInArray search = new SearchIndexInArray<>(array, 0, 10, 13);

        Integer result  = search.compute();

        assertThat(result).isEqualTo(0);
    }
}