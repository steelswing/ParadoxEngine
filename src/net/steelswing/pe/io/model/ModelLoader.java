/*
 * Ну вы же понимаете, что код здесь только мой?
 * Well, you do understand that the code here is only mine?
 */

package net.steelswing.pe.io.model;

import java.io.File;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import net.steelswing.pe.io.texture.TextureLoader;
import net.steelswing.pe.model.ModelTexture;
import net.steelswing.pe.model.TexturedModel;
import net.steelswing.pe.model.loader.ModelData;
import net.steelswing.pe.model.loader.RawModel;
import net.steelswing.pe.opengl.MultiGL;
import net.steelswing.pe.util.Log;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

/**
 *
 * @author MrJavaCoder
 */
public class ModelLoader {

    private static List<Integer> // 
            vertexArrayObjects = new ArrayList<>(),
            vertexBufferObjects = new ArrayList<>();

    public static RawModel loadToVAO(float[] positions, float[] uiPositions, float[] normals, int[] indices) {
        int vaoId = createVAO();
        bindIndicesVBO(indices);
        {
            storeDataInAttribList(0, 3, positions);
            storeDataInAttribList(1, 2, uiPositions);
            storeDataInAttribList(2, 3, normals);
        }
        unbindVAO();
        return new RawModel(vaoId, indices.length);
    }

    public static RawModel loadToVAO(float[] positions, int dimentions) {
        int vaoId = createVAO();
        storeDataInAttribList(0, dimentions, positions);
        unbindVAO();
        return new RawModel(vaoId, positions.length / dimentions);
    }

    public static int loadToVAO(float[] positions, float[] uiPositions) {
        int vaoId = createVAO();
        storeDataInAttribList(0, 2, positions);
        storeDataInAttribList(1, 2, uiPositions);
        unbindVAO();
        return vaoId;
    }

    public static void destroy() {
        vertexArrayObjects.forEach((vao) -> {
            MultiGL.glDeleteVertexArrays(vao);
        });
        vertexBufferObjects.forEach((vbo) -> {
            GL15.glDeleteBuffers(vbo);
        });

        vertexArrayObjects.clear();
        vertexBufferObjects.clear();
    }

    private static int createVAO() {
        int vaoId = MultiGL.glGenVertexArrays();
        vertexArrayObjects.add(vaoId);
        MultiGL.glBindVertexArray(vaoId);
        return vaoId;
    }

    private static void storeDataInAttribList(int attribNumber, int uiSize, float[] data) {
        int vboId = GL15.glGenBuffers();
        vertexBufferObjects.add(vboId);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        {
            FloatBuffer buffer = storeDataInFloatBuffer(data);
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
            GL20.glVertexAttribPointer(attribNumber, uiSize, GL11.GL_FLOAT, false, 0, 0);
        }
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private static FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private static IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private static void unbindVAO() {
        MultiGL.glBindVertexArray(0);
    }

    private static void bindIndicesVBO(int[] indices) {
        int vboId = GL15.glGenBuffers();
        vertexBufferObjects.add(vboId);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboId);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, storeDataInIntBuffer(indices), GL15.GL_STATIC_DRAW);
    }

    /**
     * Метод для загрузки 3D obj моделей
     *
     * @param objPath Полный путь к obj-файлу
     * @param texturePath Полный путь к png-файлу текстуры
     * @return
     * @throws Exception
     */
    public static TexturedModel loadModel(File objPath, File texturePath) throws Exception {
        Log.debug("Loading '" + objPath.getName() + "' & '" + texturePath.getName() + "' model & texture files...");
        ModelData data = OBJLoader.load(objPath);
        ModelTexture texture = new ModelTexture(TextureLoader.loadTexture(texturePath));
        return new TexturedModel(objPath.getName(), ModelLoader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices()), texture);
    }

    /**
     * Метод для загрузки 3D obj моделей
     *
     * @param objPath Полный путь к obj-файлу
     * @param texturePath Полный путь к png-файлу текстуры
     * @param specularMap Полный путь к png-файлу specular-map
     * @return
     * @throws Exception
     */
    public static TexturedModel loadModel(File objPath, File texturePath, File specularMap) throws Exception {
        Log.debug("Loading '" + objPath.getName() + "' & '" + texturePath.getName() + "' & '" + specularMap.getName() + "' model & texture files...");
        ModelData data = OBJLoader.load(objPath);
        ModelTexture texture = new ModelTexture(TextureLoader.loadTexture(texturePath));
        texture.setUseSpecularMap(true);
        texture.setSpecularId(TextureLoader.loadTexture(specularMap));
        return new TexturedModel(objPath.getName(), ModelLoader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices()), texture);
    }
}
