/*
 * Ну вы же понимаете, что код здесь только мой?
 * Well, you do understand that the code here is only mine?
 */

package net.steelswing.test;

import net.steelswing.pe.opengl.MultiGL;
import net.steelswing.pe.opengl.Window;
import net.steelswing.pe.util.Log;

/**
 *
 * @author MrJavaCoder
 */
public class MSApplet {

    public static void main(String[] a) throws Exception {
        System.setProperty("org.lwjgl.util.NoChecks", "false");
        System.setProperty("org.lwjgl.util.Debug", "true");
        System.setProperty("org.lwjgl.util.DebugLoader", "true");
        System.setProperty("org.lwjgl.util.DebugFunctions", "true");
//        System.setProperty("org.lwjgl.util.DebugStream", "System.err");

        System.setProperty("org.lwjgl.librarypath", "C:\\Users\\MrJavaCoder\\Desktop\\JAVA_LIBS\\LWJGL2\\natives");
//        Thread.currentThread().setName("CLIENT_THREAD");
        if (a.length <= 0) {
            MultiGL.initialize(MultiGL.FrameBufferObjectMode.OPENGL);
            Window.create("Unity engine 8", 1280, 620);
            {
                MagicSword magicSword = new MagicSword("C:\\Users\\MrJavaCoder\\Desktop\\MagicSword");
                magicSword.initialize();
                Window.runGameLoop();
                magicSword.destroy();
            }
            Window.destroyWindow();
        } else {
            if (a.length >= 2) {
                try {
                    MultiGL.FrameBufferObjectMode newType = MultiGL.FrameBufferObjectMode.valueOf(a[1].toUpperCase().replace(" ", ""));
                    MultiGL.initialize(newType);
                    Log.info("USE FBO MODE: " + MultiGL.getFboMode());
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.info("#===============Друголёк, АГРУМЕНТЫ:==============#");
                    for (int i = 0; i < MultiGL.FrameBufferObjectMode.values().length; i++) {
                        Log.info("FBO MODE: " + MultiGL.FrameBufferObjectMode.values()[i]);
                    }
                    Log.info("#=================================================#");
                    Window.destroyWindow();
                    return;
                }
            } else {
                Log.info("Use: java -jar JAR 'RESOURCE-PATH' 'FBO TYPE'");
                Log.info("#===============Друголёк, АГРУМЕНТЫ:==============#");
                for (int i = 0; i < MultiGL.FrameBufferObjectMode.values().length; i++) {
                    Log.info("FBO MODE: " + MultiGL.FrameBufferObjectMode.values()[i]);
                }
                Log.info("#=================================================#");
                Window.destroyWindow();
                return;
            }
            Window.create("Unity engine 8", 1280, 620);
            {
                MagicSword magicSword = new MagicSword(a[0]);
                magicSword.initialize();
                Window.runGameLoop();
                magicSword.destroy();
            }
            Window.destroyWindow();
        }

    }
}
