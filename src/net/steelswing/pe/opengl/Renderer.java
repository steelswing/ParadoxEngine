/*
 * Ну вы же понимаете, что код здесь только мой?
 * Well, you do understand that the code here is only mine?
 */

package net.steelswing.pe.opengl;

import net.steelswing.pe.util.Initializer;
import org.lwjgl.opengl.GL21;

/**
 *
 * @author MrJavaCoder
 */
public abstract class Renderer implements Initializer {
    
    public abstract void render();
    
    public static void enableCullingFace() {
        GL21.glEnable(GL21.GL_CULL_FACE);
        GL21.glCullFace(GL21.GL_BACK);
    }

    public static void disableCullingFace() {
        GL21.glDisable(GL21.GL_CULL_FACE);
    }
}
