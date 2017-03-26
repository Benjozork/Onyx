package me.benjozork.onyx.utils.collection;

import com.badlogic.gdx.utils.Array;

/**
 * A circular list, meaning calling next() when at the last element will wrap back
 * to the first element of the list, ad infinitum.
 * @param <T> the type of the items in the list
 */
public class CycleList<T> {

    private final Array<T> items = new Array<T>();
    private int currentIndex = 0;

    private CycleList(T... items) {
        for (T item : items) {
            this.items.add(item);
        }
    }

    private CycleList(Array<T> items) {
        for (T item : items) {
            this.items.add(item);
        }
    }

    public static <T> CycleList<T> of(T... items) {
        if (items == null || items.length == 0) {
            throw new IllegalArgumentException("Cycle requires at least one item!");
        }

        return new CycleList<T>(items);
    }

    public static <T> CycleList<T> of(Array<T> items) {
        if (items == null || items.size == 0) {
            throw new IllegalArgumentException("Cycle requires at least one item!");
        }

        return new CycleList<T>(items);
    }

    public T next() {
        currentIndex++;
        currentIndex %= items.size;
        return items.get(currentIndex);
    }

    public T current() {
        return items.get(currentIndex);
    }

}