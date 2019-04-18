package org.flaxo.structures;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * Структура данных - ряд целых чисел.
 *
 * Левая и правая границы - включительны.
 */
public class Range {

    //bounds of your range
    private int left;
    private int right;

    private Range(int left, int right) {
        this.left = left;
        this.right = right;
    }

    public static Range between(final int left, final int right) {
        if (left > right)
        {
            throw new IllegalArgumentException("left bound is bigger than right bound");
        }
        return new Range(left, right);
    }

    public int leftBound() {
        return this.left;
    }

    public int rightBound() {
        return this.right;
    }

    public boolean isBefore(final Range other) {
        return (this.rightBound() < other.leftBound());
    }

    public boolean isAfter(final Range other) {
        return (this.leftBound() > other.rightBound());
    }

    public boolean isConcurrent(final Range other) {
        return (this.rightBound() >= other.leftBound() &&
                this.leftBound() <= other.rightBound());
    }

    public boolean contains(final int value) {
        return (this.leftBound() <= value && this.rightBound() >= value);
    }

    public List<Integer> asList() {
        List<Integer> range = new ArrayList<>();
        for (int i = left; i < right + 1; i++)
        {
            range.add(i);
        }
        return range;
    }

    public Iterator<Integer> asIterator() {
        return this.asList().iterator();
    }
}
