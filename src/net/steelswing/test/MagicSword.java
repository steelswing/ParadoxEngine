/*
 * Ну вы же понимаете, что код здесь только мой?
 * Well, you do understand that the code here is only mine?
 */

package net.steelswing.test;

import net.steelswing.test.model.ModelManager;
import net.steelswing.test.settings.SettingsManager;
import net.steelswing.pe.collision.AABB;
import net.steelswing.pe.io.Input;
import net.steelswing.pe.io.model.ModelLoader;
import net.steelswing.pe.io.texture.TextureLoader;
import net.steelswing.pe.opengl.Light;
import net.steelswing.pe.opengl.RenderSystem;
import net.steelswing.pe.scene.Camera;
import net.steelswing.pe.scene.Scene;
import net.steelswing.pe.scene.object.GameObject;
import net.steelswing.pe.util.Initializer;
import net.steelswing.pe.util.Log;
import net.steelswing.pe.util.MousePicker;
import org.lwjgl.glfw.GLFW;
import org.lwjglx.util.vector.Vector3f;

/**
 *
 * @author MrJavaCoder
 */
public class MagicSword implements Initializer {

    private static MagicSword instance;
    private final String resourceFolder;
    private SettingsManager settingsManager;

    public MagicSword(String resourceFolder) throws Exception {
        instance = this;
        this.resourceFolder = resourceFolder;
        // Pre-init
        settingsManager = new SettingsManager();

    }

    private AABB camera;

    private float light = 0.1f;

    private int lastX = 0, lastY = 0;

    private boolean red, green, blue;

    @Override
    public void initialize() {
        try {
            ModelManager.init(resourceFolder);
        } catch (Exception ex) {
            Log.error("ModelManager error: " + ex.getMessage());
            ex.printStackTrace();
        }
        RenderSystem.loadSkyBoxModel(ModelManager.SKYBOX_MODEL);
        Light sun = new Light("SUN", new Vector3f(100000, 150000, 100000), new Vector3f(1.3f, 1.3f, 1.3f)); // Главный источник света
        Scene mainScene = new Scene("Main scene").setCurrentCamera(new Camera()).addLight(sun);
        MousePicker mp = new MousePicker(mainScene.getCurrentCamera(), RenderSystem.getProjectionMatrix());

//
        GameObject box2 = new GameObject("Model_2", ModelManager.BOX_MODEL.copy(), new Vector3f(0, 0, 0), new Vector3f(1, 5, 1)) {
            @Override
            public void update(float delta) {
            }
        }.setScale(new Vector3f(1, 1, 1)).addPosition(5, 0, 5);
        box2.addPosition(0, 0, 6);
        mainScene.addGameObject(box2);

        GameObject tree = new GameObject("Model_222", ModelManager.TREE_BIG_MODEL.copy(), new Vector3f(0, 0, 0), new Vector3f(3, 1, 2)) {
            @Override
            public void update(float delta) {
                mp.update();
//                        camera = new AABB(mainScene.getCurrentCamera().getPosition(), new Vector3f(
//                                mainScene.getCurrentCamera().getPosition().x + 1,
//                                mainScene.getCurrentCamera().getPosition().y + 1,
//                                mainScene.getCurrentCamera().getPosition().z + 1));

//                        mainScene.removeLight("1");
//                        mainScene.removeLight("2");
//                        mainScene.removeLight("3");
                if (Input.isKeyPressed(GLFW.GLFW_KEY_EQUAL)) {
                    light += 0.1f;
                }
                if (Input.isKeyPressed(GLFW.GLFW_KEY_MINUS)) {
                    light -= 0.1f;
                }
                if (Input.isKeyPressed(GLFW.GLFW_KEY_R)) {
                    try {
                        ModelManager.init(resourceFolder);
                    } catch (Exception ex) {
                        Log.error("ModelManager error: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                }
                updateModel();
                if (getName().contains("Model_1")) {
                }
            }
        };
        tree.setScale(0.01f).setPosition(new Vector3f(0, 0, 0));
        mainScene.addGameObject(tree);

        GameObject box = new GameObject("BOX", ModelManager.BOX_BLUE_MODEL.copy(), new Vector3f(0f), new Vector3f(1, 1, 1)) {

            private float x = position.x, z = position.z, y = position.y;

            @Override
            public void update(float delta) {
//                Vector3f newPos = new Vector3f(mp.getCurrentTerrainPoint().x, mp.getCurrentTerrainPoint().y, mp.getCurrentTerrainPoint().z);
//                x = newPos.x;
//                z = newPos.z;
//                move();

                float speed = 0.1f;
                if (Input.isKeyDown(GLFW.GLFW_KEY_UP)) {
                    x += speed;
                    move();
                }
                if (Input.isKeyDown(GLFW.GLFW_KEY_DOWN)) {
                    x -= speed;
                    move();
                }

                if (Input.isKeyDown(GLFW.GLFW_KEY_RIGHT)) {
                    z += speed;
                    move();
                }
                if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT)) {
                    z -= speed;
                    move();
                }

                updateModel();
            }

            private void move() {
                boolean red = false;

                AABB[] colliders = new AABB[5];
                colliders[0] = tree.getAabb();
                colliders[1] = box2.getAabb();

                if (aabb.check(new Vector3f(x, position.y, position.z), this.getEnd(), colliders)) {
                    position.x = x;
                    red = true;
                } else {
                    x = position.x;
                    red = false;
                }
                
                
                
                if (aabb.check(new Vector3f(position.x, position.y, z), this.getEnd(), colliders)) {
                    position.z = z;
                    red = true;
                } else {
                    z = position.z;
                    red = false;
                }
                
                if (red) {
                    RenderSystem.debugColor = new Vector3f(0.3f, 0.5f, 1f);
                } else {
                    RenderSystem.debugColor = new Vector3f(1f, 0.5f, 0.3f);
                }
            }



            @Override
            public void updateModel() {
                super.updateModel();
                x = position.x;
                z = position.z;
                y = position.y;
            }

        };
        box.setPosition(new Vector3f(2, 0, 5));
        mainScene.addGameObject(box);
    }

// END GAME LOOP }

    @Override
    public void destroy() {
        ModelLoader.destroy();
        TextureLoader.destroy();
    }

    public static MagicSword getInstance() {
        return instance;
    }

    public String getResourceFolder() {
        return resourceFolder;
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    @Override
    public String toString() {
        return "MagicSword{" + "resourceFolder=" + resourceFolder + ", settingsManager=" + settingsManager + ", camera=" + camera + ", light=" + light + ", lastX=" + lastX + ", lastY=" + lastY + ", red=" + red + ", green=" + green + ", blue=" + blue + '}';
    }

}
