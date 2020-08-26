/*
 * Ну вы же понимаете, что код здесь только мой?
 * Well, you do understand that the code here is only mine?
 */

package net.steelswing.pe.util;

import org.lwjglx.util.vector.Vector3f;

/**
 *
 * @author MrJavaCoder
 * @param <E>
 */
public class Transformable<E> {
    
    private E element;
    
    protected Vector3f // Vectors
            position,
            center,
            rotation,
            scale;
    
    public Transformable(  Vector3f position, Vector3f center, Vector3f rotation, Vector3f scale) {
        this.position = position;
        this.rotation = rotation;
        this.center = center;
        this.scale = scale;
    }

    public Transformable() {
        this(new Vector3f(), new Vector3f(), new Vector3f(), new Vector3f(1f, 1f, 1f));
    }

    public void setElement(E element) {
        this.element = element;
    }

    public Vector3f getPosition() {
        return position;
    }

    public E setPosition(Vector3f position) {
        this.position = position;
        return element;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public E setRotation(Vector3f rotation) {
        this.rotation = rotation;
        return element;
    }

    public Vector3f getScale() {
        return scale;
    }

    public E setScale(Vector3f scale) {
        this.scale = scale;
        return element;
    }

    public E setScale(float scale) {
        this.scale.x = scale;
        this.scale.y = scale;
        this.scale.z = scale;
        return element;
    }

    public Vector3f getCenter() {
        return center;
    }

    public E setCenter(Vector3f center) {
        this.center = center;
        return element;
    }

    /**
     * Добавляет n значение к текущей позиции
     *
     * @param x значение X
     * @param y значение Y
     * @param z значение Z
     * @return 
     */
    public E addPosition(float x, float y, float z) {
        this.position.x += x;
        this.position.y += y;
        this.position.z += z;
        return element;
    }

    /**
     * Добавляет n значение к текущему повороту
     *
     * @param x значение X
     * @param y значение Y
     * @param z значение Z
     * @return 
     */
    public E addRotation(float x, float y, float z) {
        this.rotation.x += x;
        this.rotation.y += y;
        this.rotation.z += z;
        return element;
    }

    /**
     * Добавляет n значение к текущему размеру
     *
     * @param x значение X
     * @param y значение Y
     * @param z значение Z
     * @return 
     */
    public E addScale(float x, float y, float z) {
        this.scale.x += x;
        this.scale.y += y;
        this.scale.z += z;
        return element;
    }

    /**
     * Добавляет n значение к текущему center
     *
     * @param x значение X
     * @param y значение Y
     * @param z значение Z
     * @return 
     */
    public E addCenter(float x, float y, float z) {
        this.center.x += x;
        this.center.y += y;
        this.center.z += z;
        return element;
    }
}
