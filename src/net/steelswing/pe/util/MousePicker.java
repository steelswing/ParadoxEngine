/*
 * Ну вы же понимаете, что код здесь только мой?
 * Well, you do understand that the code here is only mine?
 */

package net.steelswing.pe.util;

import net.steelswing.pe.opengl.RenderSystem;
import net.steelswing.pe.scene.Camera;
import org.lwjglx.input.Mouse;
import org.lwjglx.opengl.Display;
import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;
import org.lwjglx.util.vector.Vector4f;

/**
 *
 * @author MrJavaCoder
 */
public class MousePicker {

    private static final int RECURSION_COUNT = 200;
    private static final float RAY_RANGE = 600;

    private Vector3f currentRay = new Vector3f();

    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;
    private Camera camera;

//    private Terrain terrain;
    private Vector3f currentTerrainPoint;

    public MousePicker(Camera cam, Matrix4f projection) {
        camera = cam;
        projectionMatrix = projection;
        viewMatrix = cam.getViewMatrix();
//        this.terrain = terrain;
    }

    public Vector3f getCurrentTerrainPoint() {
        return currentTerrainPoint;
    }

    public Vector3f getCurrentRay() {
        return currentRay;
    }

    public void update() {
        viewMatrix = camera.getViewMatrix();
        currentRay = calculateMouseRay();
        if (intersectionInRange(0, RAY_RANGE, currentRay)) {
            currentTerrainPoint = binarySearch(0, 0, RAY_RANGE, currentRay);
        } else {
            currentTerrainPoint = new Vector3f(0, 0, 0);
        }
    }

    private Vector3f calculateMouseRay() {
        Vector2f normalizedCoords = null;
        if (RenderSystem.getCurrentCamera().showCursor) {
            normalizedCoords = getNormalisedDeviceCoordinates(Mouse.getX(), Mouse.getY());//getNormalisedDeviceCoordinates(Display.getWidth() / 2, Display.getHeight() / 2);
        } else {
            normalizedCoords = getNormalisedDeviceCoordinates(Display.getWidth() / 2, Display.getHeight() / 2);
        }
        Vector4f clipCoords = new Vector4f(normalizedCoords.x, normalizedCoords.y, 4, 1.0f);
        Vector4f eyeCoords = toEyeCoords(clipCoords);
        return toWorldCoords(eyeCoords);
    }

    private Vector3f toWorldCoords(Vector4f eyeCoords) {
        Matrix4f invertedView = Matrix4f.invert(viewMatrix, null);
        Vector4f rayWorld = Matrix4f.transform(invertedView, eyeCoords, null);
        Vector3f mouseRay = new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
        mouseRay.normalise();
        return mouseRay;
    }

    private Vector4f toEyeCoords(Vector4f clipCoords) {
        Matrix4f invertedProjection = Matrix4f.invert(projectionMatrix, null);
        Vector4f eyeCoords = Matrix4f.transform(invertedProjection, clipCoords, null);
        return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
    }

    private Vector2f getNormalisedDeviceCoordinates(float mouseX, float mouseY) {
        float x = (2.0f * mouseX) / Display.getWidth() - 1f;
        float y = (2.0f * mouseY) / Display.getHeight() - 1f;
        return new Vector2f(x, y);
    }

    //**********************************************************

    public Vector3f getPointOnRay(Vector3f ray, float distance) {
        Vector3f camPos = camera.getPosition();
        Vector3f start = new Vector3f(camPos.x, camPos.y, camPos.z);
        Vector3f scaledRay = new Vector3f(ray.x * distance, ray.y * distance, ray.z * distance);
        return Vector3f.add(start, scaledRay, null);
    }

    public Vector3f getPointOnRay(Vector3f ray, Vector3f start, float distance) {
        Vector3f camPos = camera.getPosition();
//        Vector3f start = new Vector3f(camPos.x, camPos.y, camPos.z);
        Vector3f scaledRay = new Vector3f(ray.x * distance, ray.y * distance, ray.z * distance);
        return Vector3f.add(start, scaledRay, null);
    }

    private Vector3f binarySearch(int count, float start, float finish, Vector3f ray) {
        float half = start + ((finish - start) / 2f);
        if (count >= RECURSION_COUNT) {
            Vector3f endPoint = getPointOnRay(ray, half);
//            Terrain terrain = getTerrain(endPoint.getX(), endPoint.getZ());
//            if (terrain != null) {
//                return endPoint;
//            } else {
//                return null;
//            }
            return endPoint;
        }
        if (intersectionInRange(start, half, ray)) {
            return binarySearch(count + 1, start, half, ray);
        } else {
            return binarySearch(count + 1, half, finish, ray);
        }
    }

    private boolean intersectionInRange(float start, float finish, Vector3f ray) {
        Vector3f startPoint = getPointOnRay(ray, start);
        Vector3f endPoint = getPointOnRay(ray, finish);
        if (!isUnderGround(startPoint) && isUnderGround(endPoint)) {
            return true;
        } else {
            return false;
        }
    }

    public float height = 0;

    private boolean isUnderGround(Vector3f testPoint) {
//        Terrain terrain = getTerrain(testPoint.getX(), testPoint.getZ());
//        if (terrain != null) {
//            height = terrain.getTerrainHeight(testPoint.getX(), testPoint.getZ());
//        }
        if (testPoint.y < height) {
            return true;
        } else {
            return false;
        }
    }

//    private Terrain getTerrain(float worldX, float worldZ) {
//        return terrain;
//    }
}
