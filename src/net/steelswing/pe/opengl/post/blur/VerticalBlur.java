
package net.steelswing.pe.opengl.post.blur;

import net.steelswing.pe.opengl.post.ImageRenderer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;


public class VerticalBlur {

    private ImageRenderer renderer;
    private VerticalBlurShader shader;

    public VerticalBlur(int targetFboWidth, int targetFboHeight) throws Exception {
        shader = new VerticalBlurShader();
        renderer = new ImageRenderer(targetFboWidth, targetFboHeight);
        shader.bind();
        shader.loadTargetHeight(targetFboHeight);
        shader.unbind();
    }


    public void render(int texture) {
        shader.bind();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
        renderer.renderQuad();
        shader.unbind();
    }

    public int getOutputTexture() {
        return renderer.getOutputTexture();
    }

    public void destroy() {
        renderer.destroy();
        shader.destroy();
    }
}
