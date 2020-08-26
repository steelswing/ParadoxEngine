
package net.steelswing.pe.opengl.post.bloom;

import net.steelswing.pe.shader.ShaderProgram;


public class CombineShader extends ShaderProgram {

    public CombineShader() throws Exception {
        super(
                "net/steelswing/pe/shader/post/bloom/simple.vs.glsl",
                "net/steelswing/pe/shader/post/bloom/combine.fs.glsl");
    }

    public void connectTextureUnits() {
        super.setUniformInt("colorTexture", 0);
//        if (MagicSword.getInstance().getSettingsManager().isActiveBloomEffect()) {
        super.setUniformInt("highlightTexture", 1);
//        }
    }

    @Override
    public void bindAttrib() {
        super.bindAttrib(0, "position");
    }

    public void loadOnWater(boolean onWater) {
        super.setUniformBoolean("onWater", onWater);
    }

    public void loadBloom(boolean onBloom) {
        super.setUniformBoolean("onBloom", onBloom);
    }

}
