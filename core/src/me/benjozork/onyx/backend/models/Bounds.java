package me.benjozork.onyx.backend.models;

public class Bounds {

    public final int width, height;

    public Bounds(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + height;
        result = prime * result + width;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Bounds other = (Bounds) obj;
        if (height != other.height) return false;
        if (width != other.width) return false;
        return true;
    }

    @Override
    public String toString() {
        return "Bounds [width=" + width + ", height=" + height + "]";
    }

}
