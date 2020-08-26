/*
 * ���� ���� ���� ������������������, ������ ������ ���������� ������������ ������?
 * Well, you do understand that the code here is only mine?
 */

package net.steelswing.pe.opengl;

import org.lwjgl.opengl.ARBBlendFuncExtended;
import org.lwjgl.opengl.ARBFramebufferObject;
import org.lwjgl.opengl.ARBGeometryShader4;
import org.lwjgl.opengl.ARBVertexArrayObject;
import org.lwjgl.opengl.EXTFramebufferBlit;
import org.lwjgl.opengl.EXTFramebufferMultisample;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.EXTGPUShader4;
import org.lwjgl.opengl.EXTGeometryShader4;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

/**
 *
 * @author MrJavaCoder
 */
public class MultiGL {

    private static FrameBufferObjectMode fboMode;

    public static int //
            GL_FRAMEBUFFER,
            GL_COLOR_ATTACHMENT0,
            GL_COLOR_ATTACHMENT1,
            GL_DEPTH_ATTACHMENT,
            GL_RENDERBUFFER,
            GL_DRAW_FRAMEBUFFER,
            GL_READ_FRAMEBUFFER;

    public static void initialize(FrameBufferObjectMode type) {
//        MultiGL.fboMode = FrameBufferObjectMode.EXT;
        MultiGL.fboMode = type;
//        if (!Window.openGL30) {
//            if (Window.ARB) {
//                type = FrameBufferObjectMode.ARB;
//            } else if (Window.EXT) {
//                type = FrameBufferObjectMode.EXT;
//            } else {
//                throw new Error("(FBO) is not supported on your platform! :(((((((((");
//            }
//        } else {
//        }
//        type = FrameBufferObjectMode.EXT;
//
//        if (System.getProperty("os.name").contains("mac")) {
//            type = FrameBufferObjectMode.ARB;
//        }
//        if (OsCheck.getOperatingSystemType() == OsCheck.OSType.MacOS) {
//            type = FrameBufferObjectMode.ARB;
//        }

//        type = FrameBufferObjectMode.EXT;
//        GL21.
        switch (fboMode) {
            case OPENGL:
                GL_FRAMEBUFFER = GL30.GL_FRAMEBUFFER;
                GL_COLOR_ATTACHMENT0 = GL30.GL_COLOR_ATTACHMENT0;
                GL_COLOR_ATTACHMENT1 = GL30.GL_COLOR_ATTACHMENT1;
                GL_DEPTH_ATTACHMENT = GL30.GL_DEPTH_ATTACHMENT;
                GL_RENDERBUFFER = GL30.GL_RENDERBUFFER;
                GL_DRAW_FRAMEBUFFER = GL30.GL_DRAW_FRAMEBUFFER;
                GL_READ_FRAMEBUFFER = GL30.GL_READ_FRAMEBUFFER;
                break;
            case ARB:
                GL_FRAMEBUFFER = ARBFramebufferObject.GL_FRAMEBUFFER;
                GL_COLOR_ATTACHMENT0 = ARBFramebufferObject.GL_COLOR_ATTACHMENT0;
                GL_COLOR_ATTACHMENT1 = ARBFramebufferObject.GL_COLOR_ATTACHMENT1;
                GL_DEPTH_ATTACHMENT = ARBFramebufferObject.GL_DEPTH_ATTACHMENT;
                GL_RENDERBUFFER = ARBFramebufferObject.GL_RENDERBUFFER;
                GL_DRAW_FRAMEBUFFER = ARBFramebufferObject.GL_DRAW_FRAMEBUFFER;
                GL_READ_FRAMEBUFFER = ARBFramebufferObject.GL_READ_FRAMEBUFFER;
                break;
            case EXT:
                GL_FRAMEBUFFER = EXTFramebufferObject.GL_FRAMEBUFFER_EXT;
                GL_COLOR_ATTACHMENT0 = EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT;
                GL_COLOR_ATTACHMENT1 = EXTFramebufferObject.GL_COLOR_ATTACHMENT1_EXT;
                GL_DEPTH_ATTACHMENT = EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT;
                GL_RENDERBUFFER = EXTFramebufferObject.GL_RENDERBUFFER_EXT;
                GL_DRAW_FRAMEBUFFER = EXTFramebufferBlit.GL_DRAW_FRAMEBUFFER_EXT;
                GL_READ_FRAMEBUFFER = EXTFramebufferBlit.GL_READ_FRAMEBUFFER_EXT;
                break;
        }
    }

    public static void glBindVertexArray(int value) {
        switch (fboMode) {
            case OPENGL:
                GL30.glBindVertexArray(value);
                break;
            case ARB:
                ARBVertexArrayObject.glBindVertexArray(value);
                break;
            case EXT:
                ARBVertexArrayObject.glBindVertexArray(value);
                break;
            case ES:
//                OESVertexArrayObject.glBindVertexArrayOES(value);
                break;
        }
    }

    public static void glBindFramebuffer(int target, int framebufferIn) {
        switch (fboMode) {
            case OPENGL:
                GL30.glBindFramebuffer(target, framebufferIn);
                break;
            case ARB:
                ARBFramebufferObject.glBindFramebuffer(target, framebufferIn);
                break;
            case EXT:
                EXTFramebufferObject.glBindFramebufferEXT(target, framebufferIn);
                break;
            case ES:
//                GLES20.glBindFramebuffer(target, framebufferIn);
                break;
        }
    }

    public static void glBindRenderbuffer(int target, int renderbuffer) {
        switch (fboMode) {
            case OPENGL:
                GL30.glBindRenderbuffer(target, renderbuffer);
                break;
            case ARB:
                ARBFramebufferObject.glBindRenderbuffer(target, renderbuffer);
                break;
            case EXT:
                EXTFramebufferObject.glBindRenderbufferEXT(target, renderbuffer);
                break;
            case ES:
//                GLES20.glBindRenderbuffer(target, renderbuffer);
                break;
        }
    }

    public static void glDeleteFramebuffers(int framebufferIn) {
        switch (fboMode) {
            case OPENGL:
                GL30.glDeleteFramebuffers(framebufferIn);
                break;
            case ARB:
                ARBFramebufferObject.glDeleteFramebuffers(framebufferIn);
                break;
            case EXT:
                EXTFramebufferObject.glDeleteFramebuffersEXT(framebufferIn);
                break;
            case ES:
//                GLES20.glDeleteFramebuffers(framebufferIn);
                break;
        }
    }

    public static void glDeleteRenderbuffers(int renderbuffer) {
        switch (fboMode) {
            case OPENGL:
                GL30.glDeleteRenderbuffers(renderbuffer);
                break;
            case ARB:
                ARBFramebufferObject.glDeleteRenderbuffers(renderbuffer);
                break;
            case EXT:
                EXTFramebufferObject.glDeleteRenderbuffersEXT(renderbuffer);
                break;
            case ES:
//                GLES20.glDeleteRenderbuffers(renderbuffer);
                break;
        }
    }

    public static int glGenFramebuffers() {
        switch (fboMode) {
            case OPENGL:
                return GL30.glGenFramebuffers();
            case ARB:
                return ARBFramebufferObject.glGenFramebuffers();
            case EXT:
                return EXTFramebufferObject.glGenFramebuffersEXT();
            case ES:
//                return GLES20.glGenFramebuffers();
        }
        throw new IllegalArgumentException("glGenFramebuffers error: function not supported!");
    }

    public static void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level) {
//        glFramebufferTexture
            EXTGeometryShader4.glFramebufferTextureEXT(target, attachment, texture, level);
        EXTFramebufferObject.glFramebufferTexture2DEXT(target, attachment, textarget, texture, level);
        ARBFramebufferObject.glFramebufferTexture2D(target, attachment, textarget, texture, level);
//        switch (fboMode) {
//            case OPENGL:
//                GL30.glFramebufferTexture2D(target, attachment, textarget, texture, level);
//                break;
//            case ARB:
//                ARBFramebufferObject.glFramebufferTexture2D(target, attachment, textarget, texture, level);
//                break;
//            case EXT:
//                EXTFramebufferObject.glFramebufferTexture2DEXT(target, attachment, textarget, texture, level);
//                break;
//            case ES:
////                glFramebufferTexture
////                GLES20.glFramebufferTexture2D(target, attachment, textarget, texture, level);
//                break;
//        }
    }

    public static void glFramebufferTexture(int target, int attachment, int textarget, int texture, int level) {
        switch (fboMode) {
//            case OPENGL:
//                GL30.glFramebufferTexture2D(target, attachment, textarget, texture, level);
//                break;
//            case ARB:
//                ARBFramebufferObject.glFramebufferTexture2D(target, attachment, textarget, texture, level);
//                break;
//            case EXT:
//                EXTFramebufferObject.glFramebufferTexture2DEXT(target, attachment, textarget, texture, level);
//                break;
//            case ES:
////                GLES20.glFramebufferTexture2D(target, attachment, textarget, texture, level);
//                break;
        }
    }

    public static void glFramebufferTexture(int target, int attachment, int texture, int level) {
        switch (fboMode) {
            case OPENGL:
                GL32.glFramebufferTexture(target, attachment, texture, level);
                break;
            case ARB:
                ARBGeometryShader4.glFramebufferTextureARB(target, attachment, texture, level);
                break;
            case EXT:
                EXTGeometryShader4.glFramebufferTextureEXT(target, attachment, texture, level);
                break;
            case ES:
//                GLES32.glFramebufferTexture(target, attachment, texture, level);
                break;
        }
    }

    public static void glFramebufferRenderbuffer(int target, int attachment, int renderBufferTarget, int renderBuffer) {
        switch (fboMode) {
            case OPENGL:
                GL30.glFramebufferRenderbuffer(target, attachment, renderBufferTarget, renderBuffer);
                break;
            case ARB:
                ARBFramebufferObject.glFramebufferRenderbuffer(target, attachment, renderBufferTarget, renderBuffer);
                break;
            case EXT:
                EXTFramebufferObject.glFramebufferRenderbufferEXT(target, attachment, renderBufferTarget, renderBuffer);
                break;
            case ES:
                
//                GLES20.glFramebufferRenderbuffer(target, attachment, renderBufferTarget, renderBuffer);
                break;
        }
    }

    public static void glRenderbufferStorage(int target, int internalFormat, int width, int height) {
        switch (fboMode) {
//            case OPENGL:
//                GL30.glRenderbufferStorage(target, internalFormat, width, height);
//                break;
//            case ARB:
//                ARBFramebufferObject.glRenderbufferStorage(target, internalFormat, width, height);
//                break;
//            case EXT:
//                EXTFramebufferObject.glRenderbufferStorageEXT(target, internalFormat, width, height);
//                break;
//            case ES:
////                GLES20.glRenderbufferStorage(target, internalFormat, width, height);
//                break;
        }
    }

    public static int glGenRenderbuffers() {
        switch (fboMode) {
            case OPENGL:
                return GL30.glGenRenderbuffers();
            case ARB:
                return ARBFramebufferObject.glGenRenderbuffers();
            case EXT:
                return EXTFramebufferObject.glGenRenderbuffersEXT();
            case ES:
//                return GLES20.glGenRenderbuffers();
        }
        throw new IllegalArgumentException("glGenRenderbuffers error: function not supported!");
    }

    public static int glGenVertexArrays() {
        switch (fboMode) {
            case OPENGL:
                return GL30.glGenVertexArrays();
            case ARB:
                return ARBVertexArrayObject.glGenVertexArrays();
            case EXT:
                return ARBVertexArrayObject.glGenVertexArrays();
            case ES:
//               return GLES30.glGenVertexArrays();
//                return OESVertexArrayObject.glGenVertexArraysOES();
        }
        throw new IllegalArgumentException("glGenVertexArrays error: function not supported!");
    }

    public static void glDeleteVertexArrays(int vao) {
        switch (fboMode) {
            case OPENGL:
                GL30.glDeleteVertexArrays(vao);
                break;
            case ARB:
                ARBVertexArrayObject.glDeleteVertexArrays(vao);
                break;
            case EXT:
                ARBVertexArrayObject.glDeleteVertexArrays(vao);
                break;
            case ES:
//                OESVertexArrayObject.glDeleteVertexArraysOES(vao);
                break;
        }
    }

    public static void glGenerateMipmap(int GL_TEXTURE_2D) {
        switch (fboMode) {
            case OPENGL:
                GL30.glGenerateMipmap(GL_TEXTURE_2D);
                break;
            case ARB:
                ARBFramebufferObject.glGenerateMipmap(GL_TEXTURE_2D);
                break;
            case EXT:
                EXTFramebufferObject.glGenerateMipmapEXT(GL_TEXTURE_2D);
                break;
            case ES:
//                GLES20.glGenerateMipmap(GL_TEXTURE_2D);
                break;
        }
    }

    public static void glRenderbufferStorageMultiSample(int GL_RENDERBUFFER, int i, int GL_DEPTH_COMPONENT24, int width, int height) {
        switch (fboMode) {
            case OPENGL:
                GL30.glRenderbufferStorageMultisample(GL_RENDERBUFFER, i, GL_DEPTH_COMPONENT24, width, height);
                break;
            case ARB:
                ARBFramebufferObject.glRenderbufferStorageMultisample(GL_RENDERBUFFER, i, GL_DEPTH_COMPONENT24, width, height);
                break;
            case EXT:
                EXTFramebufferMultisample.glRenderbufferStorageMultisampleEXT(GL_RENDERBUFFER, i, GL_DEPTH_COMPONENT24, width, height);
                break;
            case ES:

//                    glRenderBufferStorageMultisamp
                break;
        }
    }

    public static void glBlitFramebuffer(int i, int i0, int width, int height, int i1, int i2, int width0, int height0, int i3, int GL_NEAREST) {
        switch (fboMode) {
            case OPENGL:
                GL30.glBlitFramebuffer(i, i0, width, height, i1, i2, width0, height0, i3, GL_NEAREST);
                break;
            case ARB:
                ARBFramebufferObject.glBlitFramebuffer(i, i0, width, height, i1, i2, width0, height0, i3, GL_NEAREST);
                break;
            case EXT:
                EXTFramebufferBlit.glBlitFramebufferEXT(i, i0, width, height, i1, i2, width0, height0, i3, GL_NEAREST);
                break;
            case ES:
//                glBlit
                break;
        }
    }

    public static void glBindFragDataLocation(int programId, int id, String variableName) {
        switch (fboMode) {
            case OPENGL:
                GL30.glBindFragDataLocation(programId, id, variableName);
                break;
            case ARB:
                ARBBlendFuncExtended.glBindFragDataLocationIndexed(programId, programId, id, variableName);
                break;
            case EXT:
                EXTGPUShader4.glBindFragDataLocationEXT(programId, id, variableName);
                break;
            case ES:
//                glBindFragDa
                break;
        }
    }

    public static FrameBufferObjectMode getFboMode() {
        return fboMode;
    }
    
    public static enum FrameBufferObjectMode {
        OPENGL,
        ARB,
        EXT,
        ES
    }
}
