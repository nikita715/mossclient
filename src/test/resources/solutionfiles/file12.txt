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
        if (left > right)
            throw new IllegalArgumentException("Left must be less than right");

        this.left = left;
        this.right = right;
    }

    /**
     * Возвращает ряд чисел между переданными левой и правой границами.
     * <p>
     * Границы включаются в ряд.
     *
     * @param left  Левая граница ряда.
     * @param right Правая граница ряда.
     * @return Ряд чисел между левой и правой границами включительно.
     */
    public static Range between(final int left, final int right) {
        // todo: Необходимо добавить реализацию метода
        return new Range(left, right);
    }

    /**
     * Возвращает значение левой границы ряда.
     *
     * @return Значение левой границы ряда.
     */
    public int leftBound() {
        // todo: Необходимо добавить реализацию метода
        return left;
    }

    /**
     * Возвращает значение правой границы ряда.
     *
     * @return Значение правой границы ряда.
     */
    public int rightBound() {
        // todo: Необходимо добавить реализацию метода
        return right;
    }

    /**
     * Проверяет, является ли текущий ряд предшествующим переданному.
     *
     * @param other Ряд, который предположительно находится следует после текущего.
     * @return true, если правая граница текущего ряда меньше,
     * чем левая граница переданного, иначе - false.
     */
    public boolean isBefore(final Range other) {
        return right < other.left;
    }

    /**
     * Проверяет, является ли текущий ряд следующим после переданного.
     *
     * @param other Ряд, который предположительно предшествует текущему.
     * @return true, если левая граница текущего ряда больше,
     * чем правая граница переданного, иначе - false.
     */
    public boolean isAfter(final Range other) {
        return left > other.right;
    }

    /**
     * Проверяет, пересекаются ли текущий и переданный ряды.
     * <p>
     * Совпадение границ рядов означает их пересечение.
     *
     * @param other Ряд, с которым необходимо проверить пересечение.
     * @return true, если ряды пересекаются, иначе - false.
     */
    public boolean isConcurrent(final Range other) {
        // todo: Необходимо добавить реализацию метода
        return other.left <= right && other.right >= left;
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
        return value >= left && value <= right;
    }

    /**
     * Возвращает список чисел ряда.
     * <p>
     * Границы ряда включаются в список.
     *
     * @return Список чисел, входящих в ряд.
     */
    public List<Integer> asList() {
        // todo: Необходимо добавить реализацию метода
        List<Integer> list = new ArrayList<>();
        for (int i = left; i <= right; i++)
            list.add(i);
        return list;
    }

    /**
     * Возвращает итератор по числам, входящим в ряд.
     * <p>
     * Границы ряда включаются в итератор.
     *
     * @return Итератор по числам, входящим в ряд
     */
    public Iterator<Integer> asIterator() {
        // todo: Необходимо добавить реализацию метода
        //throw new UnsupportedOperationException("Method is not implemented yet");
        return asList().iterator();
    }
}
