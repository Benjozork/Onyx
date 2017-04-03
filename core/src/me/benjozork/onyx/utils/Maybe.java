package me.benjozork.onyx.utils;

/**
 * Represents an object that may or may not exist
 * @param <T> the type of the object
 * @author angelickite
 */
public class Maybe<T> {

    private final T value;

    private Maybe(T value) {
        this.value = value;
    }

    /**
     * Returns an empty {@link Maybe}
     * @param <T> the type of the object
     */
    public static <T> Maybe<T> empty() {
        return new Maybe<T>(null);
    }

    /**
     * Returns a {@link Maybe} containing a corresponding object
     * @param value the contained object
     */
    public static <T> Maybe<T> of(T value) {
        if (value == null)
            throw new NullPointerException("value may not be null! use Maybe.empty() or Maybe.unsure(value) instead");
        return new Maybe<T>(value);
    }

    /**
     * Returns a {@link Maybe} that may or may not contain an object
     * @param value the contained object
     */
    public static <T> Maybe<T> unsure(T value) {
        return new Maybe<T>(value);
    }

    public T get() {
        return value;
    }

    /**
     * Checks whether the {@link Maybe} contains an object
     */
    public boolean exists() {
        return value != null;
    }

}
