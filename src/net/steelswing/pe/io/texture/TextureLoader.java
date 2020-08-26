/*
 * Ну вы же понимаете, что код здесь только мой?
 * Well, you do understand that the code here is only mine?
 */

package net.steelswing.pe.io.texture;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import net.steelswing.pe.opengl.MultiGL;
import net.steelswing.pe.util.Log;
import org.lwjgl.opengl.GL21;
import org.slick.opengl.PNGDecoder;
import org.slick.opengl.Texture;
import static org.slick.opengl.TextureLoader.getTexture;

/**
 *
 * @author MrJavaCoder
 */
public class TextureLoader {

    private static List<Integer> textures = new ArrayList<>();

    public static int loadTexture(String texturePath, int filter) throws Exception {
        Texture texture = getTexture("PNG", new FileInputStream(texturePath), filter);
        int textureId = texture.getTextureID();
        textures.add(textureId);
        if (filter != GL21.GL_NEAREST) {
            enableMipMap();
        }

        return textureId;
    }

    public static int loadTexture(File texturePath) throws Exception {
        Texture texture = getTexture("PNG", new FileInputStream(texturePath));
        int textureId = texture.getTextureID();
//        textures.add(textureId);
////        enableMipMap();
////        if (GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic) {
////            float ammout = Math.min(4f, GL21.glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
////            GL21.glTexParameterf(GL21.GL_TEXTURE_2D, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, ammout);
////             GL21.glTexParameteri(GL21.GL_TEXTURE_CUBE_MAP, GL21.GL_TEXTURE_MAG_FILTER, GL21.GL_NEAREST);
////        } else {
////            Log.error("Anisotropic is not supported :(");
////        }
//        
//        int textureId = TextureLoaderNew.loadTexture(texturePath).getId();
// 
//        textures.add(textureId);
        return textureId;
    }

    public static int loadCubeMap(String[] texturePaths) {
        int texId = GL21.glGenTextures();
        GL21.glActiveTexture(GL21.GL_TEXTURE0);
        GL21.glBindTexture(GL21.GL_TEXTURE_CUBE_MAP, texId);

        for (int i = 0; i < texturePaths.length; i++) {
            TextureData data = decodeTextureFile(texturePaths[i]);
            GL21.glTexImage2D(GL21.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL21.GL_RGBA, data.getWidth(), data.getHeight(), 0, GL21.GL_RGBA, GL21.GL_UNSIGNED_BYTE, data.getBuffer());
        }
        GL21.glTexParameteri(GL21.GL_TEXTURE_CUBE_MAP, GL21.GL_TEXTURE_MAG_FILTER, GL21.GL_LINEAR);
        GL21.glTexParameteri(GL21.GL_TEXTURE_CUBE_MAP, GL21.GL_TEXTURE_MIN_FILTER, GL21.GL_LINEAR);
        textures.add(texId);
        return texId;
    }

    private static TextureData decodeTextureFile(String fileName) {
        int width = 0;
        int height = 0;
        ByteBuffer buffer = null;
        try {
            FileInputStream in = new FileInputStream(fileName);
            PNGDecoder decoder = new PNGDecoder(in);
            width = decoder.getWidth();
            height = decoder.getHeight();
            buffer = ByteBuffer.allocateDirect(4 * width * height);
            decoder.decode(buffer, width * 4, PNGDecoder.RGBA);
            buffer.flip();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.error("Tried to load texture " + fileName + ", didn't work");
        }

        return new TextureData(buffer, width, height);
    }

    private static void enableMipMap() {
        MultiGL.glGenerateMipmap(GL21.GL_TEXTURE_2D);
        GL21.glTexParameteri(GL21.GL_TEXTURE_2D, GL21.GL_TEXTURE_MAG_FILTER, GL21.GL_NEAREST);
        GL21.glTexParameteri(GL21.GL_TEXTURE_2D, GL21.GL_TEXTURE_MIN_FILTER, GL21.GL_NEAREST_MIPMAP_NEAREST);
//        GL21.glTexParameteri(GL21.GL_TEXTURE_2D, GL21.GL_TEXTURE_WRAP_S, GL15.GL_CLAMP_TO_EDGE);
//        GL21.glTexParameteri(GL21.GL_TEXTURE_2D, GL21.GL_TEXTURE_WRAP_T, GL15.GL_CLAMP_TO_EDGE);
    }

    public static void destroy() {
        textures.forEach((t) -> {
            GL21.glDeleteTextures(t);
        });
        textures.clear();
    }
}
