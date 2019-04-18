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

    private int left = 0;
    private int right = 0;

    public Range(int left, int right) {
        if(left > right){
          throw new IllegalArgumentException("WRONG!Lower bound is greater then upper bound");
        }
        this.left = left;
        this.right = right;
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
        return new Range(left, right);
    }

    /**
     * Возвращает значение левой границы ряда.
     *
     * @return Значение левой границы ряда.
     */
    public int leftBound() {
        return this.left;
    }

    /**
     * Возвращает значение правой границы ряда.
     *
     * @return Значение правой границы ряда.
     */
    public int rightBound() {
        return this.right;
    }

    /**
     * Проверяет, является ли текущий ряд предшествующим переданному.
     *
     * @param other Ряд, который предположительно находится следует после текущего.
     * @return true, если правая граница текущего ряда меньше,
     * чем левая граница переданного, иначе - false.
     */
    public boolean isBefore(final Range other) {
        return this.right < other.left;
    }

    /**
     * Проверяет, является ли текущий ряд следующим после переданного.
     *
     * @param other Ряд, который предположительно предшествует текущему.
     * @return true, если левая граница текущего ряда больше,
     * чем правая граница переданного, иначе - false.
     */
    public boolean isAfter(final Range other) {
        return this.left > other.right;
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
        return (this.right >= other.left) && (this.left <= other.right);
    }

    /**
     * Проверяет, находится ли переданный ряд в границах текущего.
     *
     * @param value Ряд, предположительно находящийся в границах текущего.
     * @return true, если левая граница переданного ряда больше левой границы текущего,
     * а правая граница переданного ряда меньше правой границы текушего, иначе - false.
     */
    public boolean contains(final int value) {
         return (this.left <= value) && (this.right >= value);
    }

    /**
     * Возвращает список чисел ряда.
     *
     * Границы ряда включаются в список.
     *
     * @return Список чисел, входящих в ряд.
     */
    public List<Integer> asList() {
        List<Integer> numList = new ArrayList<>();
        for(int i = left; i <= right; i++)
            numList.add(i);
        return numList;
    }

    /**
     * Возвращает итератор по числам, входящим в ряд.
     *
     * Границы ряда включаются в итератор.
     *
     * @return Итератор по числам, входящим в ряд
     */
    public Iterator<Integer> asIterator() {
        return this.asList().iterator();
    }
}
