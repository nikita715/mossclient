package org.flaxo.structures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Range {
    private int mLeft;
    private int mRight;

    private Range(int left, int right) {
        mLeft = left;
        mRight = right;
    }

    public static Range between(final int left, final int right) {
        if (left > right) throw new IllegalArgumentException("the left should be less than the right");
        return new Range(left, right);
    }

    public int leftBound() {
        return mLeft;
    }

    public int rightBound() {
        return mRight;
    }

    public boolean isBefore(final Range other) {
        return this.mRight < other.mLeft;
    }

    public boolean isAfter(final Range other) {
        return this.mLeft > other.mRight;
    }

    public boolean isConcurrent(final Range other) {
        return !(other.isAfter(this) || other.isBefore(this));
    }

    public boolean contains(final int value) {
        return mLeft <= value && mRight >= value;
    }

    public List<Integer> asList() {
        List<Integer> list = new ArrayList<>();
        for (int i = mLeft; i <= mRight; ++i) list.add(i);
        return list;
    }

    public Iterator<Integer> asIterator() {
        return this.asList().iterator();
    }
}
