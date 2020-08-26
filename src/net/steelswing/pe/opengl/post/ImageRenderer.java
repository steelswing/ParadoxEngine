
package net.steelswing.pe.opengl.post;

import org.lwjgl.opengl.GL11;

public class ImageRenderer {

    private Fbo fbo;
    private int width, height;

    public ImageRenderer(int width, int height) {
        this.fbo = new Fbo(this.width = width, this.height = height, Fbo.NONE);
    }

    public ImageRenderer() {

    }

    public void renderQuad() {
        if (fbo != null) {
            fbo.bindFrameBuffer();
        }
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
        if (fbo != null) {
            fbo.unbindFrameBuffer();
        }
    }
    
    public int getOutputTexture() {
        return fbo.getColorTexture();
    }

    public void destroy() {
        if (fbo != null) {
            fbo.destroy();
        }
    }

}
