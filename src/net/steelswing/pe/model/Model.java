/*
 * Ну вы же понимаете, что код здесь только мой?
 * Well, you do understand that the code here is only mine?
 */

package net.steelswing.pe.model;

import net.steelswing.pe.util.Transformable;
import org.lwjglx.util.vector.Vector3f;

/**
 *
 * @author MrJavaCoder
 */
public class Model extends Transformable<Model> {
    
    private final String name;
    
    public Model(String name, Vector3f position, Vector3f rotation, Vector3f scale) {
        super(position, rotation, scale, new Vector3f());
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
