
package net.steelswing.pe.opengl.post.bloom;

import net.steelswing.pe.shader.ShaderProgram;


public class BrightFilterShader extends ShaderProgram {

    public BrightFilterShader() throws Exception {
        super(
                "net/steelswing/pe/shader/post/bloom/simple.vs.glsl",
                "net/steelswing/pe/shader/post/bloom/brightFilter.fs.glsl");
    }


    @Override
    public void bindAttrib() {
        super.bindAttrib(0, "position");
    }

}
