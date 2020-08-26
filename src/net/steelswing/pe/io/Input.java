/*
 * Ну вы же понимаете, что код здесь только мой?
 * Well, you do understand that the code here is only mine?
 */

package net.steelswing.pe.io;

import org.lwjgl.glfw.GLFW;
import org.lwjglx.input.Mouse;
import org.lwjglx.opengl.Display;

/**
 *
 * @author MrJavaCoder
 */
public class Input {

    private static boolean[] pressedKeys = new boolean[GLFW.GLFW_KEY_LAST];
    private static boolean[] pressedButtons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];

    private static boolean mouseEntered;

    public static boolean isKeyDown(int keyCode) {
        if (keyCode < GLFW.GLFW_KEY_SPACE) {
            return false;
        }
        return GLFW.glfwGetKey(Display.Window.handle, keyCode) == GLFW.GLFW_TRUE;
    }

    public static boolean isKeyPressed(int keyCode) {
        return isKeyDown(keyCode) && !pressedKeys[keyCode];
    }

    public static boolean isKeyReleased(int keyCode) {
        return isKeyDown(keyCode) && pressedKeys[keyCode];
    }

    public static boolean isMouseButtonDown(int keyCode) {
        return Mouse.isButtonDown(keyCode);
    }

    public static boolean isMouseButtonPressed(int keyCode) {
        return isMouseButtonDown(keyCode) && !pressedButtons[keyCode];
    }

    public static boolean isMouseButtonReleased(int keyCode) {
        return isMouseButtonDown(keyCode) && pressedButtons[keyCode];
    }

    public static void update() {
        for (int i = 0; i < pressedKeys.length; i++) {
            pressedKeys[i] = isKeyDown(i);
            if (i < pressedButtons.length) {
                pressedButtons[i] = isMouseButtonDown(i);
            }
        }
    }

    public static void setMousePos(int x, int y) {
        Mouse.setCursorPosition(x, y);
    }

    public static int getMouseX() {
        return Mouse.getX();
    }

    public static int getMouseY() {
        return Mouse.getY();
    }

    public static boolean isMouseEntered() {
        return mouseEntered;
    }
}
