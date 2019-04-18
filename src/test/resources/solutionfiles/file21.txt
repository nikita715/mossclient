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
    int left;
    int right;

    private Range(int left, int right) {
       if (left > right)
       throw new IllegalArgumentException("Lower bound is greater then upper bound");
        this.left = left;
        this.right = right;
    }
    private List rng() {
    ArrayList<Integer> rng = new ArrayList<>(right-left+1);
        int k=this.left;
        while (k<=this.right) {rng.add(k++); }
        return rng;
    }

    /**
     * Возвращает ряд чисел между переданными левой и правой границами.
     *
     * Границы включаются в ряд.
     *
     * @param left Левая граница ряда.
     * @param right Правая граница ряда.
     * @return Ряд чисел между левой и правой границами включительно.
     */
    public static Range between(final int left, final int right) {
        // todo: Необходимо добавить реализацию метода
        return new Range(left, right);
        //throw new UnsupportedOperationException("Method is not implemented yet");
    }

    /**
     * Возвращает значение левой границы ряда.
     *
     * @return Значение левой границы ряда.
     */
    public int leftBound() {
        // todo: Необходимо добавить реализацию метода
        return this.left;
        //throw new UnsupportedOperationException("Method is not implemented yet");
    }

    /**
     * Возвращает значение правой границы ряда.
     *
     * @return Значение правой границы ряда.
     */
    public int rightBound() {
        // todo: Необходимо добавить реализацию метода
        return this.right;
        //throw new UnsupportedOperationException("Method is not implemented yet");
    }

    /**
     * Проверяет, является ли текущий ряд предшествующим переданному.
     *
     * @param other Ряд, который предположительно находится следует после текущего.
     * @return true, если правая граница текущего ряда меньше,
     * чем левая граница переданного, иначе - false.
     */
    public boolean isBefore(final Range other) {
        // todo: Необходимо добавить реализацию метода
       return other.leftBound()>this.right;
       // throw new UnsupportedOperationException("Method is not implemented yet");
    }

    /**
     * Проверяет, является ли текущий ряд следующим после переданного.
     *
     * @param other Ряд, который предположительно предшествует текущему.
     * @return true, если левая граница текущего ряда больше,
     * чем правая граница переданного, иначе - false.
     */
    public boolean isAfter(final Range other) {
        // todo: Необходимо добавить реализацию метода
        return this.left > other.rightBound();
        //throw new UnsupportedOperationException("Method is not implemented yet");
    }

    /**
     * Проверяет, пересекаются ли текущий и переданный ряды.
     *
     * Совпадение границ рядов означает их пересечение.
     *
     * @param other Ряд, с которым необходимо проверить пересечение.
     * @return true, если ряды пересекаются, иначе - false.
     */
    public boolean isConcurrent(final Range other) {
        // todo: Необходимо добавить реализацию метода
       return !this.isAfter(other) && !this.isBefore(other) && (other.leftBound()<=this.left || other.rightBound()>=this.rightBound());
       // throw new UnsupportedOperationException("Method is not implemented yet");
    }

    /**
     * Проверяет, находится ли переданный ряд в границах текущего.
     *
     * @param value Ряд, предположительно находящийся в границах текущего.
     * @return true, если левая граница переданного ряда больше левой границы текущего,
     * а правая граница переданного ряда меньше правой границы текушего, иначе - false.
     */
    public boolean contains(final int value) {
        // todo: Необходимо добавить реализацию метода
        return this.left <= value && this.right >= value;
       // throw new UnsupportedOperationException("Method is not implemented yet");
    }

    /**
     * Возвращает список чисел ряда.
     *
     * Границы ряда включаются в список.
     *
     * @return Список чисел, входящих в ряд.
     */
    public List asList() {
        // todo: Необходимо добавить реализацию метода
        return rng();
      //  throw new UnsupportedOperationException("Method is not implemented yet");
    }

    /**
     * Возвращает итератор по числам, входящим в ряд.
     *
     * Границы ряда включаются в итератор.
     *
     * @return Итератор по числам, входящим в ряд
     */
    public Iterator asIterator() {
        return rng().iterator();
        // todo: Необходимо добавить реализацию метода
    //    throw new UnsupportedOperationException("Method is not implemented yet");
    }
}
