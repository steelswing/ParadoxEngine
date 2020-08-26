/*
 * Ну вы же понимаете, что код здесь только мой?
 * Well, you do understand that the code here is only mine?
 */

package net.steelswing.pe.scene;

import net.steelswing.pe.io.Input;
import net.steelswing.pe.opengl.Window;
import net.steelswing.pe.util.MathUtil;
import net.steelswing.pe.util.Transformable;
import org.lwjgl.glfw.GLFW;
import org.lwjglx.input.Mouse;
import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector3f;

/**
 *
 * @author MrJavaCoder
 */
public class Camera extends Transformable<Camera> {

    private float // 
            fov = 75.0f,
            near = 0.1f,
            far = 1000.0f;
    private Matrix4f viewMatrix;

    private float //
            camSpeed = 30f,
            // ROTATION
            mouseSpeed = 0.5f,
            maxLookUp = 85,
            maxLookDown = -85;

    public boolean //
            showCursor = true,
            cursorControl,
            freecam,
            blockedRotation;

    public Camera() {
        this.viewMatrix = MathUtil.createViewMatrix(this);
        setElement(this);
    }

    public Camera(Vector3f position, Vector3f rotation, Vector3f scale) {
        super(position, new Vector3f(), rotation, scale);
        setElement(this);
    }

    public Camera(boolean freecam, Vector3f position, Vector3f rotation, Vector3f scale) {
        super(position, new Vector3f(), rotation, scale);
        setElement(this);
        this.freecam = freecam;
    }

    public void update() {
        this.viewMatrix = MathUtil.createViewMatrix(this);

        this.rotation();
        float speed = (camSpeed * 0.2f) * Window.getDelta();

        if (Input.isKeyDown(GLFW.GLFW_KEY_W)) {
            move(rotation.y - 180, speed);
        }
        if (Input.isKeyDown(GLFW.GLFW_KEY_S)) {
            move(rotation.y, speed);
        }
        if (Input.isKeyDown(GLFW.GLFW_KEY_A)) {
            move(rotation.y + 90, speed);
        }
        if (Input.isKeyDown(GLFW.GLFW_KEY_D)) {
            move(rotation.y - 90, speed);
        }
        if (Input.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
            if (showCursor) {
                showCursor = false;
                Mouse.setGrabbed(true);
            } else {
                showCursor = true;
                Mouse.setGrabbed(false);
            }
        }
        if (Input.isKeyPressed(GLFW.GLFW_KEY_F)) {
            freecam = !freecam;
        }
        if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL)) {
            camSpeed = 100;
        } else {
            camSpeed = 50f;
        }
        if (Input.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
            position.y += speed;
        }
        if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            position.y -= speed;
        }
    }

    private void rotation() {
        if (!blockedRotation && (!showCursor && !cursorControl) || showCursor && Mouse.isButtonDown(0) && !blockedRotation) {
            float mouseDX = Mouse.getDX() * mouseSpeed * 0.16f;
            float mouseDY = Mouse.getDY() * mouseSpeed * 0.16f;

            if (rotation.y + mouseDX >= 360) {
                rotation.y = rotation.y + mouseDX - 360;
            } else if (rotation.y + mouseDX < 0) {
                rotation.y = 360 - rotation.y + mouseDX;
            } else {
                rotation.y += mouseDX;
            }

            if (rotation.x - mouseDY >= maxLookDown && rotation.x - mouseDY <= maxLookUp) {
                rotation.x += -mouseDY;
            } else if (rotation.x - mouseDY < maxLookDown) {
                rotation.x = maxLookDown;
            } else if (rotation.x - mouseDY > maxLookUp) {
                rotation.x = maxLookUp;
            }
        }
    }

    private void move(float angle, float speed) {
        float adjacent = speed * (float) Math.cos(Math.toRadians(angle));
        float opposite = (float) (Math.sin(Math.toRadians(angle)) * speed);
        position.z += adjacent;
        position.x -= opposite;
    }

    public float getYaw() {
        return rotation.x;
    }

    public float getPitch() {
        return rotation.y;
    }

    public boolean isFreecam() {
        return freecam;
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public float getFov() {
        return fov;
    }

    public float getNear() {
        return near;
    }

    public float getFar() {
        return far;
    }
}
