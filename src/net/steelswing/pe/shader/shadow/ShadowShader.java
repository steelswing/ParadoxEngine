
package net.steelswing.pe.shader.shadow;

import net.steelswing.pe.shader.ShaderProgram;
import org.lwjglx.util.vector.Matrix4f;

public class ShadowShader extends ShaderProgram {
    
    public ShadowShader() throws Exception {
        super(
                "net/steelswing/pe/shader/shadow/shadow.vs.glsl",
                "net/steelswing/pe/shader/shadow/shadow.fs.glsl");
    }

    public void loadMvpMatrix(Matrix4f mvpMatrix) {
        super.setUniformMatrix4f("mvpMatrix", mvpMatrix);
    }

    @Override
    protected void bindAttrib() {
        super.bindAttrib(0, "in_position");
    }
}
