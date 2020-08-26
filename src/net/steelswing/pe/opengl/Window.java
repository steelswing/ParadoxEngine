/*
 * Ну вы же понимаете, что код здесь только мой?
 * Well, you do understand that the code here is only mine?
 */

package net.steelswing.pe.opengl;

import java.nio.file.Files;
import net.steelswing.pe.io.Input;
import net.steelswing.pe.util.Log;
import net.steelswing.pe.util.Utils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.GLDebugMessageAMDCallback;
import org.lwjgl.opengl.GLDebugMessageARBCallback;
import org.lwjgl.opengl.GLDebugMessageCallback;
import org.lwjgl.system.MemoryUtil;
import org.lwjglx.LWJGLException;
import org.lwjglx.Sys;
import org.lwjglx.opengl.Display;
import org.lwjglx.opengl.DisplayMode;
import org.lwjglx.util.vector.Matrix4f;

/**
 *
 * @author MrJavaCoder
 */
public class Window {

    public static final String VERSION = "1.0.0, Build 25";

    private static int maxFps = 120;
    public static long lastFrame;

    private static long lastFrameTime;
    private static float delta;

    private static Matrix4f projectionMatrix;

    public static boolean //
            openGL30,
            ARB,
            EXT;

    volatile static boolean running;

    public static void create(String title, int width, int height) throws LWJGLException {
        GLFWErrorCallback.createPrint(System.err).set();
        new GLDebugMessageCallback() {
            @Override
            public void invoke(int source, int type, int id, int severity, int length, long message, long userParam) {
                String msg = MemoryUtil.memUTF8(MemoryUtil.memByteBuffer(message, length));
                Log.error(msg);
            }
        };
        new GLDebugMessageARBCallback() {
            @Override
            public void invoke(int source, int type, int id, int severity, int length, long message, long userParam) {
                String msg = MemoryUtil.memUTF8(MemoryUtil.memByteBuffer(message, length));
                Log.error(msg);
            }
        };
        new GLDebugMessageAMDCallback() {
            @Override
            public void invoke(int id, int category, int severity, int length, long message, long userParam) {
                String msg = MemoryUtil.memUTF8(MemoryUtil.memByteBuffer(message, length));
                Log.error(msg);
            }
        };

        Display.setDisplayMode(new DisplayMode(width, height));
        Display.setTitle(title);
        Display.setResizable(true);
        Display.create();
        GL11.glViewport(0, 0, width, height);

        GLCapabilities gLCapabilities = GL.createCapabilities();
        openGL30 = gLCapabilities.OpenGL30;
        ARB = gLCapabilities.GL_ARB_framebuffer_object;
        EXT = gLCapabilities.GL_EXT_framebuffer_object;

        Log.info("#===================INFO===================#");
        Log.info("OS name " + System.getProperty("os.name"));
        Log.info("OS version " + System.getProperty("os.version"));
        Log.info("ParadoxEngine version: " + VERSION);
        Log.info("LWJGL version " + org.lwjglx.Sys.getVersion());
        Log.info("OpenGL version " + GL11.glGetString(GL11.GL_VERSION));
        Log.info("OpenGL 3.0 is " + (gLCapabilities.OpenGL30 ? "" : "not ") + "supported!");
        Log.info("ARB_framebuffer_object is " + (gLCapabilities.GL_ARB_framebuffer_object ? "" : "not ") + "supported.");
        Log.info("EXT_framebuffer_object is " + (gLCapabilities.GL_EXT_framebuffer_object ? "" : "not ") + "supported.");
        Log.info("#==========================================#");
        lastFrameTime = getCurrentTime();
    }

    public static void runGameLoop() {
        if (!running) {
            running = true;
        } else {
            return;
        }
        try {
            Files.delete(Utils.TEMP_FOLDER.toPath());
        } catch (Exception e) {
        }
        RenderSystem.initialize();
        while (!Window.isCloseRequested()) {
            try {
                RenderSystem.update(delta);
            } catch (Exception e) {
                Log.error("UPDATE ERROR: (" + e.getClass().getCanonicalName() + ") " + e.getMessage());
                e.printStackTrace();
            }

            try {
                RenderSystem.render();
            } catch (Exception e) {
                Log.error("RENDER ERROR: (" + e.getClass().getCanonicalName() + ") " + e.getMessage());
                e.printStackTrace();
            }
            if (wasResized()) {
//                projectionMatrix = MathUtil.createProjectionMatrix(Camera.far, Camera.near, Camera.fov);
            }
            Window.update();
        }
        try {
            Files.delete(Utils.TEMP_FOLDER.toPath());
        } catch (Exception e) {
        }
        RenderSystem.destroy();
    }

    public static boolean isCloseRequested() {
        return Display.isCloseRequested();
    }

    public static boolean wasResized() {
        return Display.wasResized();
    }

    public static void update() {
        Input.update();
        Display.sync(maxFps);

//        GLFW.glfww
        Display.update();
        long currentFrameTime = getCurrentTime();
        delta = (currentFrameTime - lastFrameTime) / 1000f;
        lastFrameTime = currentFrameTime;

        GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
    }

    public static void destroyWindow() {
        Display.destroy();
    }

    public static int getWidth() {
        return Display.getWidth();
    }

    public static int getHeight() {
        return Display.getHeight();
    }

    public static int getMaxFps() {
        return maxFps;
    }

    public static void setMaxFps(int maxFps) {
        Window.maxFps = maxFps;
    }

    private static long getCurrentTime() {
        return Sys.getTime() * 1000 / Sys.getTimerResolution();
    }

    public static float getDelta() {
        return delta;
    }
}
