
/*
 * Ну вы же понимаете, что код здесь только мой?
 * Well, you do understand that the code here is only mine?
 */
package net.steelswing.pe.shader.skybox;

import net.steelswing.pe.opengl.Window;
import net.steelswing.pe.shader.ShaderProgram;
import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector3f;

/**
 *
 * @author MrJavaCoder
 */
public class SkyboxShader extends ShaderProgram {

    private float rotation;

    public SkyboxShader() throws Exception {
        super(
                "net/steelswing/pe/shader/skybox/skybox.vs.glsl",
                "net/steelswing/pe/shader/skybox/skybox.fs.glsl");
    }

    @Override
    public void bindAttrib() {
        super.bindAttrib(0, "position");
    }

    public void loadProjMatrix(Matrix4f matrix) {
        super.setUniformMatrix4f("projMatrix", matrix);
    }

    public void loadFogColor(Vector3f color) {
        super.setUniformFloat3("fogColor", color);
    }

    public void loadViewMatrix(Matrix4f matrixIn) {
        Matrix4f matrix = new Matrix4f(matrixIn);
        matrix.m30 = 0;
        matrix.m31 = 0;
        matrix.m32 = 0;
        rotation += 0.4f * Window.getDelta();
//        System.out.println(rotation);

        Matrix4f.rotate((float) Math.toRadians(rotation / 2), new Vector3f(0, 1, 0), matrix, matrix);

        super.setUniformMatrix4f("viewMatrix", matrix);
    }
}
