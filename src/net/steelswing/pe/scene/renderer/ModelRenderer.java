/*
 * Ну вы же понимаете, что код здесь только мой?
 * Well, you do understand that the code here is only mine?
 */

package net.steelswing.pe.scene.renderer;

import java.util.ArrayList;
import java.util.List;
import net.steelswing.pe.model.TexturedModel;
import net.steelswing.pe.model.loader.RawModel;
import net.steelswing.pe.opengl.Light;
import net.steelswing.pe.opengl.MultiGL;
import net.steelswing.pe.opengl.RenderSystem;
import net.steelswing.pe.opengl.Renderer;
import net.steelswing.pe.scene.object.GameObject;
import net.steelswing.pe.shader.common.CommonShader;
import net.steelswing.pe.util.Log;
import net.steelswing.pe.util.MathUtil;
import net.steelswing.pe.util.debug.Shapes;
import org.lwjgl.opengl.GL21;
import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;

/**
 *
 * @author MrJavaCoder
 */
public class ModelRenderer extends Renderer {

    private CommonShader shader;
    private List<GameObject> objects = new ArrayList<>();
    private Vector3f skyColor = new Vector3f(201 / 255f, 209 / 255f, 255 / 255f);


    public ModelRenderer() {
        try {
            this.shader = new CommonShader();
            this.shader.bind();
            {
                shader.loadProjMatrix(RenderSystem.getProjectionMatrix());
                shader.loadSkyColor(skyColor);
                shader.loadFakeLightValue(true);
            }
            this.shader.unbind();
        } catch (Exception ex) {
            Log.error("CRITICAL ERROR DURING INITIALIZATION OF THE SHADER: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Метод для загрузки матрицы вида
     *
     * @param viewMatrix
     */
    public void loadViewMatrix(Matrix4f viewMatrix) {
        this.shader.bind();
        {
            shader.loadViewMatrix(viewMatrix);
        }
        this.shader.unbind();
    }

    /**
     * Метод для загрузки матрицы проекции
     *
     * @param projectionMatrix
     */
    public void loadProjectionMatrix(Matrix4f projectionMatrix) {
        this.shader.bind();
        {
            shader.loadProjMatrix(projectionMatrix);
        }
        this.shader.unbind();
    }

    @Override
    public void initialize() {

    }

    public void loadLights(List<Light> lights) {
        this.shader.bind();
        {
            shader.loadLights(lights);
        }
        this.shader.unbind();
    }

    public void loadDebug(boolean debug) {
        this.shader.bind();
        {
            shader.loadDebug(debug);
        }
        this.shader.unbind();
    }

    public void loadDebugColor(Vector3f color) {
        this.shader.bind();
        {
            shader.loadDebugColor(color);
        }
        this.shader.unbind();
    }

    @Override
    public void render() {
        this.shader.bind();
        {
            shader.connectTextureUnits();
            shader.loadActiveShadow(RenderSystem.getSettings().isRenderShadows());
            shader.loadToShadowMapSpace(RenderSystem.getToShadowMapSpaceMatrix());
            shader.loadProjMatrix(RenderSystem.getProjectionMatrix());
            shader.loadShadowDistance(RenderSystem.getSettings().getShadowDistance());
            shader.loadShadowRenderDistance(RenderSystem.getSettings().getShadowRenderDistance());
            shader.loadShadowTransitionDistance(RenderSystem.getSettings().getShadowTransitionDistance());
            shader.loadShadowMapSize(RenderSystem.getSettings().getShadowMapSize());

            for (GameObject obj : objects) {
                if (RenderSystem.getSettings().isShowCollision()) {
                    // Включение дебага в шейдерах
                    shader.loadDebug(true);
                    shader.loadDebugColor(RenderSystem.debugColor);
                    {
                        GL21.glLineWidth(2f);
                        GL21.glPolygonMode(GL21.GL_FRONT_AND_BACK, GL21.GL_LINE);
                        TexturedModel cb = Shapes.CUBE_MODEL.copy();
                        cb.setScale(obj.getEnd());
                        cb.setPosition(new Vector3f(
                                obj.getAabb().getMinExtents().x + (cb.getScale().x / 2),
                                obj.getAabb().getMinExtents().y + (cb.getScale().y / 2),
                                obj.getAabb().getMinExtents().z + (cb.getScale().z / 2)));
                        initModel(cb);
                        disableCullingFace();
                        GL21.glDrawElements(GL21.GL_TRIANGLES, cb.getRawModel().getVertexCount(), GL21.GL_UNSIGNED_INT, 0);
                        unbindModel();
                    }
                    // Выключение дебага в шейдерах
                    shader.loadDebug(false);
                    GL21.glPolygonMode(GL21.GL_FRONT_AND_BACK, GL21.GL_FILL);
                }
                if (RenderSystem.getSettings().isShowPolygons()) {
                    // Включение дебага в шейдерах
                    shader.loadDebug(true);
                    shader.loadDebugColor(new Vector3f(0.5f, 1f, 0.3f));
                    GL21.glLineWidth(2f);
                    GL21.glPolygonMode(GL21.GL_FRONT_AND_BACK, GL21.GL_LINE);
                    if (obj.getModel() instanceof TexturedModel) {
                        TexturedModel model = (TexturedModel) obj.getModel();
                        initModel(model);
                        GL21.glDrawElements(GL21.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL21.GL_UNSIGNED_INT, 0);
                        unbindModel();
                    }
                    // Выключение дебага в шейдерах
                    shader.loadDebug(false);
                    GL21.glPolygonMode(GL21.GL_FRONT_AND_BACK, GL21.GL_FILL);
                }

                // Рендер модели
                if (obj.getModel() instanceof TexturedModel) {
                    TexturedModel model = (TexturedModel) obj.getModel();
                    initModel(model);
                    GL21.glDrawElements(GL21.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL21.GL_UNSIGNED_INT, 0);
                    unbindModel();
                }
            }
        }
        this.shader.unbind();
    }

    /**
     * Метод для добавления GameObject.
     *
     * @param object
     */
    public void addObject(GameObject object) {
        objects.add(object);
    }

    /**
     * Метод для удаления GameObject.
     *
     * @param model
     * @return результат удаления модели (true - удалена, false - нет).
     */
    public boolean removeObject(GameObject model) {
        boolean exists = objects.contains(model);
        if (exists) {
            objects.remove(model);
        }
        return exists;
    }

    /**
     * Удаляет все модели.
     */
    public void clear() {
        objects.clear();
    }

    private void initModel(TexturedModel model) {
//        Log.info("Начинаю инициализировать модель: " + model.getName());
        RawModel raw = model.getRawModel();

        MultiGL.glBindVertexArray(raw.getVaoId());

        GL21.glEnableVertexAttribArray(0);
        GL21.glEnableVertexAttribArray(1);
        GL21.glEnableVertexAttribArray(2);

        shader.loadNumberOfRows(model.getModelTexture().getNumberOfRow());

        if (model.getModelTexture().isHasTransparensy()) {
            disableCullingFace();
        }

        shader.loadFakeLightValue(model.getModelTexture().isUseFakeLight());
        shader.loadShineValue(model.getModelTexture().getShineDumper(), model.getModelTexture().getReflectivity());
        GL21.glActiveTexture(GL21.GL_TEXTURE0);
        GL21.glBindTexture(GL21.GL_TEXTURE_2D, model.getModelTexture().getId());

        shader.loadUseSpecularMap(model.getModelTexture().isUseSpecularMap());
        if (model.getModelTexture().isUseSpecularMap()) {
            GL21.glActiveTexture(GL21.GL_TEXTURE2);
            GL21.glBindTexture(GL21.GL_TEXTURE_2D, model.getModelTexture().getSpecularId());
        }

        Matrix4f matrix = MathUtil.createTransformationMatrix(model.getPosition(), model.getCenter(), model.getRotation(), model.getScale());

        shader.loadTranMatrix(matrix);
        shader.loadOffset(new Vector2f(model.getModelTexture().getTextureXOffset(), model.getModelTexture().getTextureYOffset()));
    }

    public void unbindModel() {
        enableCullingFace();
        GL21.glDisableVertexAttribArray(0);
        GL21.glDisableVertexAttribArray(1);
        GL21.glDisableVertexAttribArray(2);
        MultiGL.glBindVertexArray(0);
    }

    @Override
    public void destroy() {
        objects.clear();
        shader.destroy();
    }


}
