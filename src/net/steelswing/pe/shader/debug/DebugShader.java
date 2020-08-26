
/*
 * Ну вы же понимаете, что код здесь только мой?
 * Well, you do understand that the code here is only mine?
 */
package net.steelswing.pe.shader.debug;

import net.steelswing.pe.shader.ShaderProgram;
import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector3f;

/**
 *
 * @author MrJavaCoder
 */
public class DebugShader extends ShaderProgram {

    private float rotation;

    public DebugShader() throws Exception {
        super(
                "net/steelswing/pe/shader/debug/debug.vs.glsl",
                "net/steelswing/pe/shader/debug/debug.fs.glsl");
    }

    @Override
    public void bindAttrib() {
        super.bindAttrib(0, "position");
    }

    public void loadProjMatrix(Matrix4f matrix) {
        super.setUniformMatrix4f("projMatrix", matrix);
    }

    public void loadViewMatrix(Matrix4f matrix) {
        super.setUniformMatrix4f("viewMatrix", matrix);
    }

    public void loadTransMatrix(Matrix4f matrix) {
        super.setUniformMatrix4f("tranMatrix", matrix);
    }

    public void loadColor(Vector3f color) {
        super.setUniformFloat3("color", color);
    }
}
