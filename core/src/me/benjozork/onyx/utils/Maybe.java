package me.benjozork.onyx.utils;

public class Maybe<T> {

    private final T value;

    private Maybe(T value) {
        this.value = value;
    }

    public static <T> Maybe<T> empty() {
        return new Maybe<T>(null);
    }

    public static <T> Maybe<T> of(T value) {
        if (value == null)
            throw new NullPointerException("value may not be null! use Maybe.empty() or Maybe.unsure(value) instead");
        return new Maybe<T>(value);
    }

    public static <T> Maybe<T> unsure(T value) {
        return new Maybe<T>(value);
    }

    public T get() {
        return value;
    }

    public boolean exists() {
        return value != null;
    }

}
