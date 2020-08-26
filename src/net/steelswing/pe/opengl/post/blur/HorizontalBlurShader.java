
package net.steelswing.pe.opengl.post.blur;

import net.steelswing.pe.shader.ShaderProgram;


public class HorizontalBlurShader extends ShaderProgram {

    public HorizontalBlurShader() throws Exception {
        super(
                "net/steelswing/pe/shader/post/blur/horizontalBlur.vs.glsl",
                "net/steelswing/pe/shader/post/blur/blur.fs.glsl");
    }

    public void loadTargetWidth(float width) {
        super.setUniformFloat("targetWidth", width);
    }

    @Override
    public void bindAttrib() {
        super.bindAttrib(0, "position");
    }

}
