/*
 * Ну вы же понимаете, что код здесь только мой?
 * Well, you do understand that the code here is only mine?
 */

package net.steelswing.pe.opengl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.steelswing.pe.io.Input;
import net.steelswing.pe.model.TexturedModel;
import net.steelswing.pe.opengl.post.Fbo;
import net.steelswing.pe.opengl.post.PostProcessing;
import net.steelswing.pe.scene.Camera;
import net.steelswing.pe.scene.Scene;
import net.steelswing.pe.scene.object.GameObject;
import net.steelswing.pe.scene.renderer.ModelRenderer;
import net.steelswing.pe.scene.renderer.ShadowRenderer;
import net.steelswing.pe.scene.renderer.SkyboxRenderer;
import net.steelswing.pe.util.Log;
import net.steelswing.pe.util.MathUtil;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector3f;

/**
 *
 * @author MrJavaCoder
 */
public class RenderSystem {

    private static List<Scene> scenes;
    private static Camera currentCamera;

    private static RenderSettings settings;

    private static Matrix4f //
            viewMatrix,
            projectionMatrix;

    private static ModelRenderer modelRenderer;
    private static ShadowRenderer shadowRenderer;
    private static SkyboxRenderer skyboxRenderer;

    public static boolean debug;
    public static Vector3f debugColor = new Vector3f(0.3f, 0.5f, 1f);

    private static Fbo //
            outputFbo,
            outputFbo2,
            multisampleFbo;

    static {
        settings = new RenderSettings();
        scenes = new ArrayList<>();

        multisampleFbo = new Fbo(Window.getWidth(), Window.getHeight());
        outputFbo = new Fbo(Window.getWidth(), Window.getHeight(), Fbo.DEPTH_TEXTURE);
        outputFbo2 = new Fbo(Window.getWidth(), Window.getHeight(), Fbo.DEPTH_TEXTURE);

        currentCamera = new Camera();

        projectionMatrix = MathUtil.createProjectionMatrix(1000.0f, 0.001f, 70f);
        viewMatrix = currentCamera.getViewMatrix();

        modelRenderer = new ModelRenderer();
        shadowRenderer = new ShadowRenderer(new Light("TMP", new Vector3f(), new Vector3f()));
        skyboxRenderer = new SkyboxRenderer();
    }

    /**
     * Данный метод вызывается ДО запуска основного цикла (GameLoop'a).
     */
    public static void initialize() {
        try {
            PostProcessing.init();
        } catch (Exception ex) {
            Log.error("PostProcessing error: " + ex.getMessage());
            ex.printStackTrace();
        }
        modelRenderer.initialize();
        shadowRenderer.initialize();
        skyboxRenderer.initialize();
    }

    /**
     * Ну тут думаю понятно :)
     */
    public static void render() {
        // Shadow renderer
        for (Scene scene : scenes) {
            if (scene.isVisible()) {
                if (scene.getLights().size() > 0) {
                    shadowRenderer.setSun(scene.getLights().get(0));
                }
                for (Map.Entry<String, GameObject> entry : scene.getObjects().entrySet()) {
                    GameObject object = entry.getValue();
                    if (object.getModel() != null) {
                        shadowRenderer.addModel(object.getModel());
                    }
                }
            }
        }
        shadowRenderer.render();
        if (settings.isPost()) {
            multisampleFbo.bindFrameBuffer();
        }
        {
            // SHADOW MAP!
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glClearColor(0, 0, 0, 1);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            GL13.glActiveTexture(GL13.GL_TEXTURE1);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, getShadowMapTextureId());

            for (Scene scene : scenes) {
                if (scene.isVisible()) {
                    if (scene.getLights().size() > 0) {
                        modelRenderer.loadLights(scene.getLights());
                    }
                    for (Map.Entry<String, GameObject> entry : scene.getObjects().entrySet()) {
                        GameObject object = entry.getValue();
                        if (object.getModel() != null) { 
                            modelRenderer.addObject(object);
                        }
                    }
                }
            }

            modelRenderer.render();
//            if (debug) {
//                // Debug lines 
//                GL11.glLineWidth(2f);
//                GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
//                modelRenderer.loadDebugColor(debugColor);
//                modelRenderer.loadDebug(true);
//                modelRenderer.render();
//            }
            // Clear
            GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
            modelRenderer.clear();

        }
        Renderer.disableCullingFace();
        skyboxRenderer.render();

        Renderer.enableCullingFace();
        if (settings.isPost()) {
            multisampleFbo.unbindFrameBuffer();
            multisampleFbo.resolveToFbo(MultiGL.GL_COLOR_ATTACHMENT0, outputFbo);
            multisampleFbo.resolveToFbo(MultiGL.GL_COLOR_ATTACHMENT1, outputFbo2);
            PostProcessing.doPostProcessing(outputFbo.getColorTexture(), outputFbo2.getColorTexture());
        }

    }

    /**
     * И тут тоже :)
     *
     * @param delta дельта тайм
     */
    public static void update(float delta) {
        if (Input.isKeyPressed(GLFW.GLFW_KEY_V)) {
            debug = !debug;
        }
        if (Input.isKeyPressed(GLFW.GLFW_KEY_P)) {
            getSettings().post = !getSettings().post;
        }
        if (currentCamera != null) {
            currentCamera.update();
            viewMatrix = currentCamera.getViewMatrix();
            if (Window.wasResized()) {
                projectionMatrix = MathUtil.createProjectionMatrix(currentCamera.getFar(), currentCamera.getNear(), currentCamera.getFov());
                try {
                    PostProcessing.updateFbos();
                } catch (Exception ex) {
                    Log.error("FBO update error: " + ex.getMessage());
                }
                multisampleFbo.destroy();
                outputFbo.destroy();
                outputFbo2.destroy();
                multisampleFbo = new Fbo(Window.getWidth(), Window.getHeight());
                outputFbo = new Fbo(Window.getWidth(), Window.getHeight(), Fbo.DEPTH_TEXTURE);
                outputFbo2 = new Fbo(Window.getWidth(), Window.getHeight(), Fbo.DEPTH_TEXTURE);
            }
        }
        for (Scene scene : scenes) {
            if (scene.isVisible()) {
                scene.update(delta);
            }
        }

        loadViewMatrix(viewMatrix);

    }

    /**
     * Данный метод вызывается при закрытии окна.
     */
    public static void destroy() {
        multisampleFbo.destroy();
        outputFbo.destroy();
        outputFbo2.destroy();
        modelRenderer.destroy();
        shadowRenderer.destroy();
        skyboxRenderer.destroy();
    }

    /**
     * Метод для добавление сцены.
     *
     * @param sceneIn
     */
    public static void addScene(Scene sceneIn) {
        if (sceneIn == null) {
            Log.warn("Scene = null!");
            return;
        }
        for (Scene scene : scenes) {
            if (scene.getName().equals(scene.getName())) {
                Log.warn("Scene '" + scene.getName() + "' exists");
                return;
            }
        }
        scenes.add(sceneIn);
    }

    /**
     * Метод для удаления сцены.
     *
     * @param name название сцены
     * @return статус удаления (true - удалена, false - нет)
     */
    public static boolean removeScene(String name) {
        boolean done = false;
        for (Scene obj : scenes) {
            if (obj.getName().equals(name)) {
                scenes.remove(obj);
                done = true;
                break;
            }
        }
        return done;
    }

    /**
     * Метод для удаления сцены.
     *
     * @param scene сцена
     * @return статус удаления (true - удалена, false - нет)
     */
    public static boolean removeScene(Scene scene) {
        boolean done = scenes.contains(scene);
        if (done) {
            scenes.remove(scene);
        }
        return done;
    }

    /**
     * Метод для загрузки матрицы вида.
     *
     * @param viewMatrix
     */
    public static void loadViewMatrix(Matrix4f viewMatrix) {
        modelRenderer.loadViewMatrix(viewMatrix);
    }

    /**
     * Метод для установки текущей камеры.
     *
     * @param currentCamera новая камера
     */
    public static void setCurrentCamera(Camera currentCamera) {
        RenderSystem.currentCamera = currentCamera;
    }

    /**
     *
     * @return текущую камеру.
     */
    public static Camera getCurrentCamera() {
        return currentCamera;
    }

    /**
     *
     * @return текущую матрицу проекции.
     */
    public static Matrix4f getProjectionMatrix() {
        return RenderSystem.projectionMatrix;
    }

    public static int getShadowMapTextureId() {
        return RenderSystem.shadowRenderer.getShadowMap();
    }

    public static Matrix4f getToShadowMapSpaceMatrix() {
        return RenderSystem.shadowRenderer.getToShadowMapSpaceMatrix();
    }

    public static RenderSettings getSettings() {
        return RenderSystem.settings;
    }

    public static void loadSettings(RenderSettings settings) {
        RenderSystem.settings = settings;
    }

    public static void loadSkyBoxModel(TexturedModel model) {
        RenderSystem.skyboxRenderer.loadSkyBoxModel(model);
    }

}
