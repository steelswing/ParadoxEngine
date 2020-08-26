
/*
 * Ну вы же понимаете, что код здесь только мой?
 * Well, you do understand that the code here is only mine?
 */
package net.steelswing.pe.shader;

import java.io.InputStream;
import java.nio.FloatBuffer;
import java.util.Scanner;
import net.steelswing.pe.opengl.MultiGL;
import net.steelswing.pe.opengl.Window;
import net.steelswing.pe.util.Log;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL21;
import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;
import org.lwjglx.util.vector.Vector4f;

/**
 *
 * @author MrJavaCoder
 */
public abstract class ShaderProgram {

    private int //
            programId = 0,
            vertexShaderId = 0,
            geometryShaderId = 0,
            fragmentShaderId = 0;

    private FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);

    public ShaderProgram(String vsPath, String fsPath) throws Exception {
        vertexShaderId = loadShader(vsPath, GL21.GL_VERTEX_SHADER);
        fragmentShaderId = loadShader(fsPath, GL21.GL_FRAGMENT_SHADER);
        programId = GL21.glCreateProgram();
        GL21.glAttachShader(programId, vertexShaderId);
        GL21.glAttachShader(programId, fragmentShaderId);
        bindAttrib();
        GL21.glLinkProgram(programId);
        GL21.glValidateProgram(programId);
    }

    public void bind() {
        GL21.glUseProgram(programId);
    }

    public void unbind() {
        GL21.glUseProgram(0);
    }

    public void destroy() {
        unbind();
        GL21.glDetachShader(programId, vertexShaderId);
        GL21.glDetachShader(programId, geometryShaderId);
        GL21.glDetachShader(programId, fragmentShaderId);

        GL21.glDeleteShader(vertexShaderId);
        GL21.glDeleteShader(geometryShaderId);
        GL21.glDeleteShader(fragmentShaderId);

        GL21.glDeleteProgram(programId);
    }

    protected abstract void bindAttrib();

    protected void bindAttrib(int attribute, String variableName) {
        GL21.glBindAttribLocation(programId, attribute, variableName);
    }

    protected void bindFragOutput(int id, String variableName) {
        MultiGL.glBindFragDataLocation(programId, id, variableName);
        
        
    }

    private static int loadShader(String file, int type) throws Exception {
        int shaderID = GL21.glCreateShader(type);

        String gl3file = file.replaceAll("vs", "vs3").replaceAll("fs", "fs3");

        String source;
        if (getResourceAsFile(gl3file) != null && Window.openGL30) {
            source = getResourceAsFile(gl3file);
            Log.debug("OpenGL 3.0 is supported & GL3 shader found, loading GL3 shader...");
        } else {
            source = getResourceAsFile(file);
        }

        GL21.glShaderSource(shaderID, source);

        GL21.glCompileShader(shaderID);
        if (GL21.glGetShaderi(shaderID, GL21.GL_COMPILE_STATUS) == GL21.GL_FALSE) {
            Log.error(GL21.glGetShaderInfoLog(shaderID, 1024));
            Log.error("File: " + file);
            Log.error("Could not compile shader!");
//            throw new RuntimeException("Could not compile shader!");
        }
        return shaderID;
    }

    public static String getResourceAsFile(String resourcePath) {
        String result = null;
        try {
            InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(resourcePath);
            if (in != null) {
                Scanner s = new Scanner(in).useDelimiter("\\A");
                result = s.hasNext() ? s.next() : "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // -- [INT] --

    public void setUniformInt(String name, int value) {
        GL21.glUniform1i(GL21.glGetUniformLocation(programId, name), value);
    }

    public void setUniformInt2(String name, int value1, int value2) {
        GL21.glUniform2i(GL21.glGetUniformLocation(programId, name), value1, value2);
    }

    public void setUniformInt3(String name, int value1, int value2, int value3) {
        GL21.glUniform3i(GL21.glGetUniformLocation(programId, name), value1, value2, value3);
    }

    public void setUniformInt4(String name, int value1, int value2, int value3, int value4) {
        GL21.glUniform4i(GL21.glGetUniformLocation(programId, name), value1, value2, value3, value4);
    }

    // -- [FLOAT] --

    public void setUniformFloat(String name, float value) {
        GL21.glUniform1f(GL21.glGetUniformLocation(programId, name), value);
    }

    public void setUniformFloat2(String name, Vector2f value) {
        GL21.glUniform2f(GL21.glGetUniformLocation(programId, name), value.x, value.y);
    }

    public void setUniformFloat3(String name, Vector3f value) {
        GL21.glUniform3f(GL21.glGetUniformLocation(programId, name), value.x, value.y, value.z);
    }

    public void setUniformFloat4(String name, Vector4f value) {
        GL21.glUniform4f(GL21.glGetUniformLocation(programId, name), value.x, value.y, value.z, value.w);
    }

    public void setUniformMatrix4f(String name, Matrix4f mat) {
        mat.store(buffer);
        buffer.flip();
        GL21.glUniformMatrix4fv(GL21.glGetUniformLocation(programId, name), false, buffer);
    }

    public void setUniformBoolean(String name, boolean value) {
        GL21.glUniform1f(GL21.glGetUniformLocation(programId, name), (value) ? 1 : 0);
    }
}
