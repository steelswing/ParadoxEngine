/*
 * Ну вы же понимаете, что код здесь только мой?
 * Well, you do understand that the code here is only mine?
 */

package net.steelswing.pe.util.debug;

import net.steelswing.pe.io.model.ModelLoader;
import net.steelswing.pe.model.TexturedModel;
import net.steelswing.pe.util.Utils;

/**
 *
 * @author MrJavaCoder
 */
public class Shapes {

    public static final float[] // 
            CUBE_VERTICES = {-0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, -0.5f, -0.5f},
            CUBE_NORMALS = {0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, -1.0f, 1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, -1.0f, 0.0f},
            CUBE_UI = {0f, 0f, 0f, 0f, 1f, 0, 1f, 1f, 0f, 1f, 0f, 0f};
    public static final int[] CUBE_INDICES = {3, 1, 0, 5, 8, 2, 7, 9, 4, 10, 11, 6, 12, 13, 14, 15, 16, 17, 18, 3, 0, 19, 5, 2, 20, 7, 4, 21, 10, 6, 22, 12, 14, 23, 15, 17};

    public static TexturedModel CUBE_MODEL;// = new TexturedModel("CUBE_MODEL", ModelLoader.loadToVAO(CUBE_VERTICES, CUBE_UI, CUBE_NORMALS, CUBE_INDICES), new ModelTexture(2));
    public static TexturedModel SPHERE_MODEL; //= new TexturedModel("SPHERE_MODEL", ModelLoader.loadToVAO(CUBE_VERTICES, CUBE_UI, CUBE_NORMALS, CUBE_INDICES), new ModelTexture(1));


    static {
        try {
            SPHERE_MODEL
                    = ModelLoader.loadModel(Utils.getFileFromJar("net/steelswing/pe/util/debug/sphere.obj"),
                            Utils.getFileFromJar("net/steelswing/pe/util/debug/box.png"));

            CUBE_MODEL
                    = ModelLoader.loadModel(Utils.getFileFromJar("net/steelswing/pe/util/debug/box.obj"),
                            Utils.getFileFromJar("net/steelswing/pe/util/debug/box.png"));  
                

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     * @return Copy of the cube model
     */
    public static TexturedModel getCubeModel() {
        return CUBE_MODEL.copy();
    }

    /**
     *
     * @return Copy of the sphere model
     */
    public static TexturedModel getSphereModel() {
        return SPHERE_MODEL.copy();
    }

}
