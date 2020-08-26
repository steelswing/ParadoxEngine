
package net.steelswing.pe.opengl.shadow;

import java.util.List;
import net.steelswing.pe.model.Model;
import net.steelswing.pe.model.TexturedModel;
import net.steelswing.pe.model.loader.RawModel;
import net.steelswing.pe.opengl.MultiGL;
import net.steelswing.pe.shader.shadow.ShadowShader;
import net.steelswing.pe.util.MathUtil;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjglx.util.vector.Matrix4f;

public class ShadowMapEntityRenderer {

    private Matrix4f projectionViewMatrix;
    private ShadowShader shader;

    /**
     * @param shader
     * - the simple shader program being used for the shadow render
     * pass.
     * @param projectionViewMatrix
     * - the orthographic projection matrix multiplied by the light's
     * "view" matrix.
     */
    public ShadowMapEntityRenderer(ShadowShader shader, Matrix4f projectionViewMatrix) {
        this.shader = shader;
        this.projectionViewMatrix = projectionViewMatrix;
    }

    /**
     * Renders entieis to the shadow map. Each model is first bound and then all
     * of the entities using that model are rendered to the shadow map.
     *
     * @param models
     */
    public void render(List<Model> models) {
        for (Model raw : models) {
            if (raw instanceof TexturedModel) {
                TexturedModel model = (TexturedModel) raw;
                bindModel(model.getRawModel());

                prepareInstance(raw);
                GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }
        }
        GL20.glDisableVertexAttribArray(0);
        MultiGL.glBindVertexArray(0);
    }

    /**
     * Binds a raw model before rendering. Only the attribute 0 is enabled here
     * because that is where the positions are stored in the VAO, and only the
     * positions are required in the vertex shader.
     *
     * @param rawModel
     * - the model to be bound.
     */
    private void bindModel(RawModel rawModel) {
        MultiGL.glBindVertexArray(rawModel.getVaoId());
        GL20.glEnableVertexAttribArray(0);
    }

    /**
     * Prepares an entity to be rendered. The model matrix is created in the
     * usual way and then multiplied with the projection and view matrix (often
     * in the past we've done this in the vertex shader) to create the
     * mvp-matrix. This is then loaded to the vertex shader as a uniform.
     *
     * @param entity
     * - the entity to be prepared for rendering.
     */
    private void prepareInstance(Model model) {
        Matrix4f modelMatrix = MathUtil.createTransformationMatrix(model.getPosition(), model.getCenter(), model.getRotation(), model.getScale());
        Matrix4f mvpMatrix = Matrix4f.mul(projectionViewMatrix, modelMatrix, null);
        shader.loadMvpMatrix(mvpMatrix);
    }

}
