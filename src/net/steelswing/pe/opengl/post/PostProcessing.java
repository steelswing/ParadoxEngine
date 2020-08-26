
package net.steelswing.pe.opengl.post;


import net.steelswing.pe.io.model.ModelLoader;
import net.steelswing.pe.model.loader.RawModel;
import net.steelswing.pe.opengl.RenderSystem;
import net.steelswing.pe.opengl.MultiGL;
import net.steelswing.pe.opengl.Window;
import net.steelswing.pe.opengl.post.bloom.CombineFilter;
import net.steelswing.pe.opengl.post.blur.HorizontalBlur;
import net.steelswing.pe.opengl.post.blur.VerticalBlur;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class PostProcessing {

    private static final float[] POSITIONS = {-1, 1, -1, -1, 1, 1, 1, -1};
    private static RawModel quad;
//    private static BrightFilter brightFilter;
    private static CombineFilter combineFilter;
    // Blur     
    private static HorizontalBlur hBlur8;
    private static VerticalBlur vBlur8;

    private static VerticalBlur vBlur2;
    private static HorizontalBlur hBlur2;

    public static void init() throws Exception {
        quad = ModelLoader.loadToVAO(POSITIONS, 2);

        if (RenderSystem.getSettings().isBloomEffect()) {
//            brightFilter = new BrightFilter(Window.getWidth() / 2, Window.getHeight() / 2);
            hBlur8 = new HorizontalBlur(Window.getWidth() / 8, Window.getHeight() / 8);
            vBlur8 = new VerticalBlur(Window.getWidth() / 8, Window.getHeight() / 8);

        }
        vBlur2 = new VerticalBlur(Window.getWidth() / 8, Window.getHeight() / 8);
        hBlur2 = new HorizontalBlur(Window.getWidth() / 8, Window.getHeight() / 8);

        combineFilter = new CombineFilter();
    }

    public static void updateFbos() throws Exception {
        if (RenderSystem.getSettings().isBloomEffect()) {
//            brightFilter.destroy();
            hBlur8.destroy();
            vBlur8.destroy();

//            brightFilter = new BrightFilter(Window.getWidth() / 2, Window.getHeight() / 2);
            hBlur8 = new HorizontalBlur(Window.getWidth() / 8, Window.getHeight() / 8);
            vBlur8 = new VerticalBlur(Window.getWidth() / 8, Window.getHeight() / 8);
        }

        vBlur2.destroy();
        hBlur2.destroy();
        combineFilter.destroy();
        combineFilter = new CombineFilter();
        vBlur2 = new VerticalBlur(Window.getWidth() / 5, Window.getHeight() / 5);
        hBlur2 = new HorizontalBlur(Window.getWidth() / 5, Window.getHeight() / 5);
    }

    public static void multisamplingUpdate() {
        hBlur8.destroy();
        vBlur8.destroy();
        vBlur2.destroy();
        hBlur2.destroy();
    }

    public static void doPostProcessing(int colorTexture, int brightTexture) {
        start();

        combineFilter.setBloom(RenderSystem.getSettings().isBloomEffect());

        if (RenderSystem.getSettings().isBloomEffect()) {
            hBlur8.render(brightTexture);
            vBlur8.render(hBlur8.getOutputTexture());
//            if (1 > 0 && RenderSystem.getCurrentCamera().getPosition().y < 0) {
//                hBlur2.render(colorTexture);
//                vBlur2.render(hBlur2.getOutputTexture());
//
//                combineFilter.setOnWater(true);
//                combineFilter.render(vBlur2.getOutputTexture(), vBlur2.getOutputTexture());
//            } else {
//                combineFilter.setOnWater(false);
//            }
            combineFilter.render(colorTexture, vBlur8.getOutputTexture());
        } else {
            if (1 > 0 && RenderSystem.getCurrentCamera().getPosition().y < 0) {
                hBlur2.render(colorTexture);
                vBlur2.render(hBlur2.getOutputTexture());

                combineFilter.setOnWater(true);
                combineFilter.render(vBlur2.getOutputTexture(), vBlur2.getOutputTexture());
            } else {
                combineFilter.setOnWater(false);
                combineFilter.render(colorTexture, colorTexture);
            }
        }
        end();
    }

    public static void destroy() {
        combineFilter.destroy();
        if (RenderSystem.getSettings().isBloomEffect()) {
//            brightFilter.destroy();
            hBlur8.destroy();
            vBlur8.destroy();
        }
        vBlur2.destroy();
        hBlur2.destroy();
    }


    public static void start() {
        MultiGL.glBindVertexArray(quad.getVaoId());
        GL20.glEnableVertexAttribArray(0);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
    }

    public static void end() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL20.glDisableVertexAttribArray(0);
        MultiGL.glBindVertexArray(0);
    }


}
