package org.flaxo.structures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Структура данных - ряд целых чисел.
 * <p>
 * Левая и правая границы - включительны.
 */
public class Range {

    private int left;
    private int right;

    private Range(int left, int right) {
        if (left>right){
            throw new IllegalArgumentException("It can't be true");
        }
        this.left = left;
        this.right = right;
    }
    public int leftBound() {
        return (left);
    }

    public int rightBound() {
        return (right);
    }

    public static Range between(final int left, final int right) {
        return new Range(left, right);
    }
    public boolean isBefore(final Range test) {
        return (right < test.left);
    }

    public boolean isAfter(final Range test) {
        return (left > test.right);
    }

    public boolean isConcurrent(final Range test) {
        return (test.left <= right && test.right >= left);
    }

    public boolean contains(final int a) {
        return (a >= left && a <= right);
    }

    public List<Integer> asList() {
        List<Integer> list = new ArrayList<>();
        for (int i = left; i <= right; i++)
            list.add(i);
        return (list);
    }

    public Iterator<Integer> asIterator() {
        return (asList().iterator());
    }
}