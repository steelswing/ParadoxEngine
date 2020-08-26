
package net.steelswing.pe.scene.renderer;

import java.util.ArrayList;
import java.util.List;
import net.steelswing.pe.model.Model;
import net.steelswing.pe.opengl.RenderSystem;
import net.steelswing.pe.opengl.Light;
import net.steelswing.pe.opengl.Renderer;
import net.steelswing.pe.opengl.shadow.ShadowBox;
import net.steelswing.pe.opengl.shadow.ShadowFrameBuffer;
import net.steelswing.pe.opengl.shadow.ShadowMapEntityRenderer;
import net.steelswing.pe.shader.shadow.ShadowShader;
import net.steelswing.pe.util.Log;
import org.lwjgl.opengl.GL11;
import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;


/**
 *
 * @author MrJavaCoder
 */
public class ShadowRenderer extends Renderer {

    private static int size = 2048 * 2;

    private ShadowFrameBuffer shadowFbo;
    private ShadowShader shader;
    private ShadowBox shadowBox;
    private Matrix4f //
            projectionMatrix = new Matrix4f(),
            lightViewMatrix = new Matrix4f(),
            projectionViewMatrix = new Matrix4f(),
            offset = createOffset();

    private List<Model> models = new ArrayList<>();
    private Light sun;

    private ShadowMapEntityRenderer entityRenderer;

    @Override
    public void initialize() {

    }

    public ShadowRenderer(Light sun) {
        try {
            shader = new ShadowShader();
        } catch (Exception ex) {
            Log.error("CRITICAL ERROR DURING INITIALIZATION OF THE SHADER: " + ex.getMessage());
            ex.printStackTrace();
        }
        shadowBox = new ShadowBox(lightViewMatrix, RenderSystem.getCurrentCamera());
        shadowFbo = new ShadowFrameBuffer(size, size);
        entityRenderer = new ShadowMapEntityRenderer(shader, projectionViewMatrix);
    }

    private int lastShadowMapSize = 0;


    @Override
    public void render() {
        if (lastShadowMapSize != size) {
            lastShadowMapSize = size;
            shadowFbo = new ShadowFrameBuffer(size, size);
        }

        shadowBox.update();
        Vector3f sunPosition = sun.getPosition();
        Vector3f lightDirection = new Vector3f(-sunPosition.x, -sunPosition.y, -sunPosition.z);
        prepare(lightDirection, shadowBox);
        entityRenderer.render(models);
        finish();
        clear();
    }

    /**
     * Метод для добавления модели.
     *
     * @param model
     */
    public void addModel(Model model) {
        models.add(model);
    }

    /**
     * Метод для удаления модели.
     *
     * @param model
     * @return результат удаления модели (true - удалена, false - нет).
     */
    public boolean removeModel(Model model) {
        boolean exists = models.contains(model);
        if (exists) {
            models.remove(model);
        }
        return exists;
    }

    /**
     * Удаляет все модели.
     */
    public void clear() {
        models.clear();
    }

    /**
     * This biased projection-view matrix is used to convert fragments into
     * "shadow map space" when rendering the main render pass. It converts a
     * world space position into a 2D coordinate on the shadow map. This is
     * needed for the second part of shadow mapping.
     *
     * @return The to-shadow-map-space matrix.
     */
    public Matrix4f getToShadowMapSpaceMatrix() {
        return Matrix4f.mul(offset, projectionViewMatrix, null);
    }

    /**
     * Clean up the shader and FBO on closing.
     */
    @Override
    public void destroy() {
        shader.destroy();
        shadowFbo.destroy();
    }

    /**
     * @return The ID of the shadow map texture. The ID will always stay the
     * same, even when the contents of the shadow map texture change
     * each frame.
     */
    public int getShadowMap() {
        return shadowFbo.getShadowMap();
    }

    /**
     * @return The light's "view" matrix.
     */
    protected Matrix4f getLightSpaceTransform() {
        return lightViewMatrix;
    }

    /**
     * Prepare for the shadow render pass. This first updates the dimensions of
     * the orthographic "view cuboid" based on the information that was
     * calculated in the {@link SHadowBox} class. The light's "view" matrix is
     * also calculated based on the light's direction and the center position of
     * the "view cuboid" which was also calculated in the {@link ShadowBox}
     * class. These two matrices are multiplied together to create the
     * projection-view matrix. This matrix determines the size, position, and
     * orientation of the "view cuboid" in the world. This method also binds the
     * shadows FBO so that everything rendered after this gets rendered to the
     * FBO. It also enables depth testing, and clears any data that is in the
     * FBOs depth attachment from last frame. The simple shader program is also
     * started.
     *
     * @param lightDirection
     * - the direction of the light rays coming from the sun.
     * @param box
     * - the shadow box, which contains all the info about the
     * "view cuboid".
     */
    private void prepare(Vector3f lightDirection, ShadowBox box) {
        updateOrthoProjectionMatrix(box.getWidth(), box.getHeight(), box.getLength());
        updateLightViewMatrix(lightDirection, box.getCenter());
        Matrix4f.mul(projectionMatrix, lightViewMatrix, projectionViewMatrix);
        shadowFbo.bindFrameBuffer();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
        shader.bind();
    }

    /**
     * Finish the shadow render pass. Stops the shader and unbinds the shadow
     * FBO, so everything rendered after this point is rendered to the screen,
     * rather than to the shadow FBO.
     */
    private void finish() {
        shader.unbind();
        shadowFbo.unbindFrameBuffer();
    }

    /**
     * Updates the "view" matrix of the light. This creates a view matrix which
     * will line up the direction of the "view cuboid" with the direction of the
     * light. The light itself has no position, so the "view" matrix is centered
     * at the center of the "view cuboid". The created view matrix determines
     * where and how the "view cuboid" is positioned in the world. The size of
     * the view cuboid, however, is determined by the projection matrix.
     *
     * @param direction
     * - the light direction, and therefore the direction that the
     * "view cuboid" should be pointing.
     * @param center
     * - the center of the "view cuboid" in world space.
     */
    private void updateLightViewMatrix(Vector3f direction, Vector3f center) {
        direction.normalise();
        center.negate();
        lightViewMatrix.setIdentity();
        float pitch = (float) Math.acos(new Vector2f(direction.x, direction.z).length());
        Matrix4f.rotate(pitch, new Vector3f(1, 0, 0), lightViewMatrix, lightViewMatrix);
        float yaw = (float) Math.toDegrees(((float) Math.atan(direction.x / direction.z)));
        yaw = direction.z > 0 ? yaw - 180 : yaw;
        Matrix4f.rotate((float) -Math.toRadians(yaw), new Vector3f(0, 1, 0), lightViewMatrix, lightViewMatrix);
        Matrix4f.translate(center, lightViewMatrix, lightViewMatrix);
    }

    /**
     * Creates the orthographic projection matrix. This projection matrix
     * basically sets the width, length and height of the "view cuboid", based
     * on the values that were calculated in the {@link ShadowBox} class.
     *
     * @param width
     * - shadow box width.
     * @param height
     * - shadow box height.
     * @param length
     * - shadow box length.
     */
    private void updateOrthoProjectionMatrix(float width, float height, float length) {
        projectionMatrix.setIdentity();
        projectionMatrix.m00 = 2f / width;
        projectionMatrix.m11 = 2f / height;
        projectionMatrix.m22 = -2f / length;
        projectionMatrix.m33 = 1;
    }

    /**
     * Create the offset for part of the conversion to shadow map space. This
     * conversion is necessary to convert from one coordinate system to the
     * coordinate system that we can use to sample to shadow map.
     *
     * @return The offset as a matrix (so that it's easy to apply to other matrices).
     */
    private static Matrix4f createOffset() {
        Matrix4f offset = new Matrix4f();
        offset.translate(new Vector3f(0.5f, 0.5f, 0.5f));
        offset.scale(new Vector3f(0.5f, 0.5f, 0.5f));
        return offset;
    }

    public void setSun(Light sun) {
        this.sun = sun;
    }
}
