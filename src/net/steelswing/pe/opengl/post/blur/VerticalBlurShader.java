
package net.steelswing.pe.opengl.post.blur;

import net.steelswing.pe.shader.ShaderProgram;


public class VerticalBlurShader extends ShaderProgram {

    public VerticalBlurShader() throws Exception {
        super(
                "net/steelswing/pe/shader/post/blur/verticalBlur.vs.glsl",
                "net/steelswing/pe/shader/post/blur/blur.fs.glsl");
    }

    public void loadTargetHeight(float height) {
        super.setUniformFloat("targetHeight", height);
    }

    @Override
    public void bindAttrib() {
        super.bindAttrib(0, "position");
    }
}
