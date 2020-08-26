/*
 * Ну вы же понимаете, что код здесь только мой?
 * Well, you do understand that the code here is only mine?
 */

package net.steelswing.pe.shader.common;

import java.util.List;
import net.steelswing.pe.opengl.Light;
import net.steelswing.pe.shader.ShaderProgram;
import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;
import org.lwjglx.util.vector.Vector4f;

/**
 *
 * @author MrJavaCoder
 */
public class CommonShader extends ShaderProgram {

    private final int maxLights = 25;

    public CommonShader() throws Exception {
        super(
                "net/steelswing/pe/shader/common/common.vs.glsl",
                "net/steelswing/pe/shader/common/common.fs.glsl");
    }

    @Override
    protected void bindAttrib() {
//        super.bindFragOutput(0, variableName);

        super.bindAttrib(0, "position");
        super.bindAttrib(1, "uiCoords");
        super.bindAttrib(2, "normals");
    }

    public void connectTextureUnits() {
        super.setUniformInt("textureSampler", 0);
        super.setUniformInt("shadowMap", 1);
        super.setUniformInt("specularSampler", 2);
    }

    public void loadLights(List<Light> lights) {
        for (int i = 0; i < maxLights; i++) {
            if (i < lights.size()) {
                Light l = lights.get(i);
                super.setUniformFloat3("lightPosition[" + i + "]", l.getPosition());
                super.setUniformFloat3("lightColor[" + i + "]", l.getColor());
                super.setUniformFloat3("attenuation[" + i + "]", l.getAttenuation());
            } else {
                super.setUniformFloat3("lightPosition[" + i + "]", new Vector3f(0, 0, 0));
                super.setUniformFloat3("lightColor[" + i + "]", new Vector3f(0, 0, 0));
                super.setUniformFloat3("attenuation[" + i + "]", new Vector3f(1, 0, 0));
            }
        }
    }

    public void loadTranMatrix(Matrix4f matrix) {
        super.setUniformMatrix4f("tranMatrix", matrix);
    }

    public void loadProjMatrix(Matrix4f matrix) {
        super.setUniformMatrix4f("projMatrix", matrix);
    }

    public void loadViewMatrix(Matrix4f matrix) {
        super.setUniformMatrix4f("viewMatrix", matrix);
    }

    public void loadShineValue(float damper, float reflectivity) {
        super.setUniformFloat("shineDamper", damper);
        super.setUniformFloat("reflectivity", reflectivity);
    }

    public void loadFakeLightValue(boolean value) {
        super.setUniformBoolean("useFakeLight", value);
    }

    public void loadSkyColor(Vector3f color) {
        super.setUniformFloat3("skyColor", color);
    }

    public void loadNumberOfRows(int value) {
        super.setUniformFloat("numberOfRows", value);
    }

    public void loadOffset(Vector2f value) {
        super.setUniformFloat2("offset", value);
    }

    public void loadToShadowMapSpace(Matrix4f matrix) {
        super.setUniformMatrix4f("toShadowMapSpace", matrix);
    }

    public void loadClipPlane(Vector4f value) {
        super.setUniformFloat4("plane", value);
    }

    public void loadShadowDistance(float value) {
        super.setUniformFloat("shadowDistance", value);
    }

    public void loadShadowRenderDistance(float value) {
        super.setUniformFloat("shadowRenderDistance", value);
    }

    public void loadShadowTransitionDistance(float value) {
        super.setUniformFloat("shadowTransitionDistance", value);
    }

    public void loadActiveShadow(boolean value) {
        super.setUniformBoolean("activeShadow", value);
    }

    public void loadPcfCount(int pcfCount) {
        super.setUniformInt("pcfCount", pcfCount);
    }

    public void loadShadowMapSize(int shadowMapSize) {
        super.setUniformInt("shadowMapSize", shadowMapSize);
    }

    public void loadDebug(boolean value) {
        super.setUniformBoolean("debug", value);
    }

    public void loadDebugColor(Vector3f color) {
        super.setUniformFloat3("debugColor", color);
    }

    public void loadUseSpecularMap(boolean value) {
        super.setUniformBoolean("useSpecularMap", value);
    }
}
