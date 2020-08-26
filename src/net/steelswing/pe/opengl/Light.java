/*
 * Ну вы же понимаете, что код здесь только мой?
 * Well, you do understand that the code here is only mine?
 */

package net.steelswing.pe.opengl;

import org.lwjglx.util.vector.Vector3f;

/**
 *
 * @author MrJavaCoder
 */
public class Light {
    
    private final String name;

    private Vector3f position, color, attenuation;

    public Light(String name, Vector3f position, Vector3f color) {
        this.name = name;
        this.position = position;
        this.color = color;
        this.attenuation = new Vector3f(1, 0, 0);
    }

    public Light(String name, Vector3f position, Vector3f color, Vector3f attenuation) {
        this.name = name;
        this.position = position;
        this.color = color;
        this.attenuation = attenuation;
    }

    public Vector3f getAttenuation() {
        return attenuation;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }
}
