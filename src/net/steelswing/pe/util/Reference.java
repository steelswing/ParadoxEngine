
package net.steelswing.pe.util;

public class Reference<T> {

    private T value;

    public T getValue() {
        return value;
    }

    public void setValue(T t) {
        this.value = t;
    }
}
