/*
 * Ну вы же понимаете, что код здесь только мой?
 * Well, you do understand that the code here is only mine?
 */

package net.steelswing.pe.model;

import net.steelswing.pe.model.loader.RawModel;
import org.lwjglx.util.vector.Vector3f;

/**
 *
 * @author MrJavaCoder
 */
public class TexturedModel extends Model {

    private RawModel rawModel;
    private ModelTexture modelTexture;

    public TexturedModel(String name, RawModel rawModel, ModelTexture modelTexture, Vector3f position, Vector3f rotation, Vector3f scale) {
        super(name, position, rotation, scale);
        this.rawModel = rawModel;
        this.modelTexture = modelTexture;
    }

    public TexturedModel(String name, RawModel rawModel, ModelTexture modelTexture) {
        this(name, rawModel, modelTexture, new Vector3f(), new Vector3f(0, 0, 0), new Vector3f(1f, 1f, 1f));
        setRotation(new Vector3f(0, 0, 0));
    }

    public RawModel getRawModel() {
        return rawModel;
    }

    public ModelTexture getModelTexture() {
        return modelTexture;
    }
    
    public TexturedModel copy() {
        return new TexturedModel(getName() + "_COPY", rawModel, modelTexture.copy());
    }

}
