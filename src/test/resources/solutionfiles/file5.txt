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

    private final int lB;
    private final int rB;

    private Range(final int lB, final int rB) {
        this.lB = lB;
        this.rB = rB;
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
        if (left > right) {
            throw new IllegalArgumentException("Right bound doesn't exceed left bound");
        }
        return new Range(left, right);
    }

    /**
     * Возвращает значение левой границы ряда.
     *
     * @return Значение левой границы ряда.
     */
    public int leftBound() {
        return this.lB;
    }

    /**
     * Возвращает значение правой границы ряда.
     *
     * @return Значение правой границы ряда.
     */
    public int rightBound() {
        return this.rB;
    }

    /**
     * Проверяет, является ли текущий ряд предшествующим переданному.
     *
     * @param other Ряд, который предположительно находится следует после текущего.
     * @return true, если правая граница текущего ряда меньше,
     * чем левая граница переданного, иначе - false.
     */
    public boolean isBefore(final Range other) {
        return this.rB < other.lB;
    }

    /**
     * Проверяет, является ли текущий ряд следующим после переданного.
     *
     * @param other Ряд, который предположительно предшествует текущему.
     * @return true, если левая граница текущего ряда больше,
     * чем правая граница переданного, иначе - false.
     */
    public boolean isAfter(final Range other) {
        return this.lB > other.rB;
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
        return (this.lB <= other.rB && this.rB >= other.lB);
    }

    /**
     * Проверяет, находится ли переданный ряд в границах текущего.
     *
     * @param value Ряд, предположительно находящийся в границах текущего.
     * @return true, если левая граница переданного ряда больше левой границы текущего,
     * а правая граница переданного ряда меньше правой границы текушего, иначе - false.
     */

    public boolean contains(final int value) {
        return (this.rB >= value && this.lB <= value);
    }


    /**
     * Возвращает список чисел ряда.
     *
     * Границы ряда включаются в список.
     *
     * @return Список чисел, входящих в ряд.
     */

    public List<Integer> asList() {
        final int SizeOfList = this.rB - this.lB + 1;
        final List<Integer> list = new ArrayList<>(SizeOfList);

        int i = this.lB;
        while (i <= this.rB) {
            list.add(i);
            i++;
        }
        return list;
    }

    /**
     * Возвращает итератор по числам, входящим в ряд.
     *
     * Границы ряда включаются в итератор.
     *
     * @return Итератор по числам, входящим в ряд
     */

    public Iterator<Integer> asIterator() {
        return (this.asList().iterator());
    }
}
