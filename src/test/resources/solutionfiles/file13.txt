package org.flaxo.structures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Структура данных - ряд целых чисел.
 *
 * Левая и правая границы - включительны.
 */
public class Range {

    private Range() {}
    private int left;
    private int right;
    private Range(int left, int right){
        if(left <= right) {
            this.left = left;
            this.right = right;
        } else {
            throw new IllegalArgumentException("lower bound is greater than an upper bound");
        }
    }
    public static Range between( int left, final int right) {
        return new Range(left, right);
    }

    public int leftBound() {
        return this.left;
    }

    public int rightBound() {
        return this.right;
    }

    public boolean isBefore(final Range other) {

        return other.left > this.right;
    }

    public boolean isAfter(final Range other) {
        return other.right < this.left;
    }

    public boolean isConcurrent(final Range other) {
        return (this.left <= other.right)&&(this.right >= other.left)||((other.left >= this.left)&&(other.left <= this.right));
    }

    public boolean contains(final int value) {
        return (value >= this.left && value <= this.right);
    }

    public List<Integer> asList() {
        List<Integer> list = new ArrayList<>();
        for(int i = this.left; i <= this.right; i++){
            list.add(i);
        }
        return list;
    }
    public Iterator<Integer> asIterator() {
        return this.asList().iterator();
    }
}
