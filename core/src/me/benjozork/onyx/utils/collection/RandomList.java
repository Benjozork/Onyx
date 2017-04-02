package me.benjozork.onyx.utils.collection;

import com.badlogic.gdx.utils.Array;

import java.util.Random;

/**
 * A list that will return a random item each time next() is called.
 * @param <T> the type of the items in the list
 * @author Benjozork
 */
public class RandomList<T> {

    private final Array<T> items = new Array<T>();
    private Random random = new Random();
    private int currentIndex = 0;
    private T prevItem;

    private RandomList(T... items) {
        for (T item : items) {
            this.items.add(item);
        }
    }

    private RandomList(Array<T> items) {
        for (T item : items) {
            this.items.add(item);
        }
    }

    public static <T> RandomList<T> of(T... items) {
        if (items == null || items.length == 0) {
            throw new IllegalArgumentException("RandomList requires at least one item!");
        }

        return new RandomList<T>(items);
    }

    public static <T> RandomList<T> of(Array<T> items) {
        if (items == null || items.size == 0) {
            throw new IllegalArgumentException("RandomList requires at least one item!");
        }

        return new RandomList<T>(items);
    }

    /**
     * Returns the next element randomly chosen
     * @return the element
     */
    public T next() {
        prevItem = items.get(currentIndex);
        int index = random.nextInt(items.size);
        currentIndex = index;
        return items.get(index);
    }

    /**
     * Returns the next element randomly chosen
     * @param ignorePrevItem whether the previously picked element should be ignored
     * @return the element
     */
    public T next(boolean ignorePrevItem) {
        int prev = currentIndex, index = 0;
        do {
            index = random.nextInt(items.size);
        } while (prev == index);
        currentIndex = index;
        return items.get(index);
    }

    public T current() {
        return items.get(currentIndex);
    }

}