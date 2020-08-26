/*
 * Ну вы же понимаете, что код здесь только мой?
 * Well, you do understand that the code here is only mine?
 */

package net.steelswing.test.model;

import java.io.File;
import net.steelswing.pe.io.model.ModelLoader;
import net.steelswing.pe.model.TexturedModel;

/**
 *
 * @author MrJavaCoder
 */
public class ModelManager {

    // Tree's
    public static TexturedModel TREE_BIG_MODEL;
    public static TexturedModel TREE_MINI_MODEL;
    // Shapes
    public static TexturedModel BOX_MODEL;
    public static TexturedModel BOX_BLUE_MODEL;
    // Grass
    public static TexturedModel GRASS_1_MODEL;
    public static TexturedModel GRASS_2_MODEL;
    // Animals
    public static TexturedModel PIG_MODEL;

    // SKYBOX
    public static TexturedModel SKYBOX_MODEL;

    public static TexturedModel LAMP_MODEL;

    public static TexturedModel DOOR_MODEL;

    // Editor
    public static TexturedModel MOVE_TOOL_MODEL;

    public static void init(String resourceFolder) throws Exception {
        TREE_BIG_MODEL = ModelLoader.loadModel(
                new File(resourceFolder + File.separator + "models" + File.separator + "trees" + File.separator + "tree_1.obj"),
                new File(resourceFolder + File.separator + "textures" + File.separator + "trees" + File.separator + "tree_main.png"));
        BOX_MODEL = ModelLoader.loadModel(
                new File(resourceFolder + File.separator + "models" + File.separator + "box" + File.separator + "box.obj"),
                new File(resourceFolder + File.separator + "textures" + File.separator + "box" + File.separator + "floor.png"),
                new File(resourceFolder + File.separator + "textures" + File.separator + "box" + File.separator + "floor_green.png"));
        BOX_BLUE_MODEL = ModelLoader.loadModel(
                new File(resourceFolder + File.separator + "models" + File.separator + "box" + File.separator + "box.obj"),
                new File(resourceFolder + File.separator + "textures" + File.separator + "box" + File.separator + "floor_blue.png"),
                new File(resourceFolder + File.separator + "textures" + File.separator + "box" + File.separator + "floor_green.png"));
        LAMP_MODEL = ModelLoader.loadModel(
                new File(resourceFolder + File.separator + "models" + File.separator + "lamps" + File.separator + "lamp.obj"),
                new File(resourceFolder + File.separator + "textures" + File.separator + "lamps" + File.separator + "lamp.png"),
                new File(resourceFolder + File.separator + "textures" + File.separator + "lamps" + File.separator + "lamp_light.png"));
        SKYBOX_MODEL = ModelLoader.loadModel(
                new File(resourceFolder + File.separator + "models" + File.separator + "skybox" + File.separator + "skybox.obj"),
                new File(resourceFolder + File.separator + "textures" + File.separator + "skybox" + File.separator + "skybox.png"));
        DOOR_MODEL = ModelLoader.loadModel(
                new File(resourceFolder + File.separator + "models" + File.separator + "box" + File.separator + "box.obj"),
                new File(resourceFolder + File.separator + "textures" + File.separator + "box" + File.separator + "box.png"),
                new File(resourceFolder + File.separator + "textures" + File.separator + "box" + File.separator + "box_green.png"));
        MOVE_TOOL_MODEL = ModelLoader.loadModel(
                new File(resourceFolder + File.separator + "models" + File.separator + "editor" + File.separator + "move.obj"),
                new File(resourceFolder + File.separator + "textures" + File.separator + "editor" + File.separator + "main.png"),
                new File(resourceFolder + File.separator + "textures" + File.separator + "editor" + File.separator + "main_light.png"));
    }
}
