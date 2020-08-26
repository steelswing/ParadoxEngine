/*
 * Ну вы же понимаете, что код здесь только мой?
 * Well, you do understand that the code here is only mine?
 */

package net.steelswing.pe.scene.renderer;

import net.steelswing.pe.model.TexturedModel;
import net.steelswing.pe.opengl.RenderSystem;
import net.steelswing.pe.opengl.MultiGL;
import net.steelswing.pe.opengl.Renderer;
import net.steelswing.pe.shader.skybox.SkyboxShader;
import net.steelswing.pe.util.Log;
import org.lwjgl.opengl.GL21;
import org.lwjglx.util.vector.Vector3f;

/**
 *
 * @author MrJavaCoder
 */
public class SkyboxRenderer extends Renderer {

    private TexturedModel model;
    private SkyboxShader shader;

    public SkyboxRenderer() {
        try {
            this.shader = new SkyboxShader();
        } catch (Exception ex) {
            Log.error("CRITICAL ERROR DURING INITIALIZATION OF THE SHADER: " + ex.getMessage());
            ex.printStackTrace();
        }
        this.shader.bind();
        {
            this.shader.loadProjMatrix(RenderSystem.getProjectionMatrix());
        }
        this.shader.unbind();
    }

    @Override
    public void render() {
        if (model == null) {
            return;
        }
        if(model.getRawModel() == null) {
            return;
        }
        shader.bind();
        {
            shader.loadViewMatrix(RenderSystem.getCurrentCamera().getViewMatrix());
            shader.loadFogColor(new Vector3f(1, 0, 0));
            MultiGL.glBindVertexArray(model.getRawModel().getVaoId());
            GL21.glEnableVertexAttribArray(0);
            {
                GL21.glDrawElements(GL21.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL21.GL_UNSIGNED_INT, 0);
            }
            GL21.glDisableVertexAttribArray(0);
            MultiGL.glBindVertexArray(0);
        }
        shader.unbind();
    }
    
    public void loadSkyBoxModel(TexturedModel model) {
        this.model = model;
    }

    public SkyboxShader getShader() {
        return shader;
    }

    @Override
    public void destroy() {
        shader.destroy();
    }

    @Override
    public void initialize() {

    }


}
