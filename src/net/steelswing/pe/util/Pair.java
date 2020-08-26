
package net.steelswing.pe.util;

public class Pair<T> {

    private T o1;
    private T o2;

    public Pair(T item1, T item2) {
        this.o1 = item1;
        this.o2 = item2;
    }

    public T getFirst() {
        return o1;
    }

    public T getSecond() {
        return o2;
    }

    public final int hashCode() {
        return o1.hashCode() * o2.hashCode();
    }

    public final boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (other instanceof Pair) {
            final Pair<T> otherpair = (Pair<T>) other;
            return ((o1.equals(otherpair.o1) && o2.equals(otherpair.o2))
                    || (o1.equals(otherpair.o2) && o2.equals(otherpair.o1)));
        } else {
            return false;
        }
    }

    public final boolean contains(T o) {
        return (o == o1 || o == o2);
    }

    public String toString() {
        return o1.toString() + " " + o2.toString();
    }
}
