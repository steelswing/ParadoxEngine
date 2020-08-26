
package net.steelswing.pe.opengl.post.bloom;

import net.steelswing.pe.opengl.post.ImageRenderer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;


public class CombineFilter {

    private ImageRenderer renderer;
    private CombineShader shader;

    public CombineFilter() throws Exception {
        shader = new CombineShader();
        shader.bind();
        shader.connectTextureUnits();
        shader.unbind();
        renderer = new ImageRenderer();
    }

    public void render(int colourTexture, int highlightTexture1) {
        shader.bind();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, colourTexture);

//        if (MagicSword.getInstance().getSettingsManager().isActiveBloomEffect()) {
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, highlightTexture1);
//        }

//        GL13.glActiveTexture(GL13.GL_TEXTURE2);
//        GL11.glBindTexture(GL11.GL_TEXTURE_2D, highlightTexture2);
//        
//        GL13.glActiveTexture(GL13.GL_TEXTURE3);
//        GL11.glBindTexture(GL11.GL_TEXTURE_2D, highlightTexture3);
//        
//        GL13.glActiveTexture(GL13.GL_TEXTURE4);
//        GL11.glBindTexture(GL11.GL_TEXTURE_2D, highlightTexture);
        renderer.renderQuad();
        shader.unbind();
    }

    public void setOnWater(boolean onWater) {
        shader.bind();
        shader.loadOnWater(onWater);
        shader.unbind();
    }

    public void setBloom(boolean useBloom) {
        shader.bind();
        shader.loadBloom(useBloom);
        shader.unbind();
    }

    public int get() {
        return renderer.getOutputTexture();
    }

    public void destroy() {
        renderer.destroy();
        shader.destroy();
    }

}
