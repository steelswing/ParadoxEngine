
package net.steelswing.pe.util;

import net.steelswing.pe.opengl.Window;
import net.steelswing.pe.scene.Camera;
import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;

public class MathUtil {

    public static float clamp(float value, float min, float max) {
        return Math.max(Math.min(value, max), min);
    }

    /**
     * Calculates the normal of the triangle made from the 3 vertices. The vertices must be specified in counter-clockwise order.
     *
     * @param vertex0
     * @param vertex1
     * @param vertex2
     * @return
     */
    public static Vector3f calcNormal(Vector3f vertex0, Vector3f vertex1, Vector3f vertex2) {
        Vector3f tangentA = Vector3f.sub(vertex1, vertex0, null);
        Vector3f tangentB = Vector3f.sub(vertex2, vertex0, null);
        Vector3f normal = Vector3f.cross(tangentA, tangentB, null);
        normal.normalise();
        return normal;
    }

    /**
     * OpenGL transformation matrix: https://philippegroarke.com/blog/2017/02/11/reference-opengl-model-view-projection-matrices/
     *
     * @param translation
     * @param scale
     * @return
     */
    public static Matrix4f createTransformationMatrix(Vector2f translation, Vector3f scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        Matrix4f.translate(translation, matrix, matrix);
        Matrix4f.scale(new Vector3f(scale.x, scale.y, scale.z), matrix, matrix);
        return matrix;
    }

    /**
     * Я ебал это обьяснять, поэтому почитай это:
     * <p>
     * OpenGL transformation matrix: https://philippegroarke.com/blog/2017/02/11/reference-opengl-model-view-projection-matrices/ or Google
     *
     * @param pos
     * @param center
     * @param rotation
     * @param scale
     * @return
     */
    public static Matrix4f createTransformationMatrix(Vector3f pos, Vector3f center, Vector3f rotation, Vector3f scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
//        Matrix4f.translate(pos, matrix, matrix);
        Matrix4f.translate(pos, matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(rotation.x), new Vector3f(1, 0, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(rotation.y), new Vector3f(0, 1, 0), matrix, matrix);
//        Matrix4f.translate(center, matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(rotation.z), new Vector3f(0, 0, 1), matrix, matrix);
        Matrix4f.scale(new Vector3f(scale.x, scale.y, scale.z), matrix, matrix);

        return matrix;
    }

    /**
     * OpenGL view matrix: https://philippegroarke.com/blog/2017/02/11/reference-opengl-model-view-projection-matrices/ or Google
     *
     * @param camera
     * @return
     */
    public static Matrix4f createViewMatrix(Camera camera) {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.setIdentity();
        Matrix4f.rotate((float) Math.toRadians(camera.getRotation().x), new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
        Matrix4f.rotate((float) Math.toRadians(camera.getRotation().y), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
        Vector3f cameraPos = camera.getPosition();
        Vector3f negativeCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
        return viewMatrix;
    }

    /**
     * OpenGL view matrix: https://philippegroarke.com/blog/2017/02/11/reference-opengl-model-view-projection-matrices/ or Google
     *
     * @return
     */
    public static Matrix4f createViewMatrix() {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.setIdentity();
        Matrix4f.rotate((float) Math.toRadians(0), new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
        Matrix4f.rotate((float) Math.toRadians(0), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
        Vector3f negativeCameraPos = new Vector3f(0, 0, 0);
        Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
        return viewMatrix;
    }

    /**
     * OpenGL projection matrix: https://philippegroarke.com/blog/2017/02/11/reference-opengl-model-view-projection-matrices/ or Google
     *
     * @param far
     * @param near
     * @param fov
     * @return
     */
    public static Matrix4f createProjectionMatrix(float far, float near, float fov) {
        Matrix4f projection = new Matrix4f();

        float aspectRatio = (float) Window.getWidth() / (float) Window.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(fov / 2f))));
        float x_scale = y_scale / aspectRatio;
        float frustum_length = far - near;

        projection.m00 = x_scale;
        projection.m11 = y_scale;
        projection.m22 = -((far + near) / frustum_length);
        projection.m23 = -1;
        projection.m32 = -((2 * near * far) / frustum_length);
        projection.m33 = 0;

        return projection;
    }

    public static float distance(Vector3f vec1, Vector3f vec2) {
        return (float) Math.sqrt((vec1.x - vec2.x) * (vec1.x - vec2.x) + (vec1.y - vec2.y) * (vec1.y - vec2.y) + (vec1.z - vec2.z) * (vec1.z - vec2.z));
    }

    /*
     * returns a vector that contains larger of each component of input vectors
     */
    public static Vector3f vectorMax3f(Vector3f v1, Vector3f v2) {
        return new Vector3f(
                Math.max(v1.x, v2.x),
                Math.max(v1.y, v2.y),
                Math.max(v1.z, v2.z)
        );
    }

    /*
     * returns max component in a vector
     */
    public static float vectorMaxComponent3f(Vector3f v) {
        float max = v.getX();
        if (v.getY() > max) {
            max = v.getY();
        }
        if (v.getZ() > max) {
            max = v.getZ();
        }
        return max;
    }

    public static boolean aabb(Vector3f a, Vector3f aa, Vector3f b, Vector3f bb) {
        return (a.x <= bb.x && aa.x >= b.x) && (a.y <= bb.y && aa.y >= b.y) && (a.z <= bb.z && aa.z >= b.z);
    }

    public static Vector3f sum(Vector3f... vectors) {
        Vector3f result = new Vector3f();
        for (Vector3f vector : vectors) {
            result.x += vector.x;
            result.y += vector.y;
            result.z += vector.z;
        }

        return result;
    }

    public static float getBoundingSphere(float[] positions) {
        float biggestSquare = 0;
        for (int i = 0; i < positions.length / 3; i++) {
            float x = positions[3 * i + 0];
            float y = positions[3 * i + 1];
            float z = positions[3 * i + 2];
            float distSq = x * x + y * y + z * z;
            if (distSq > biggestSquare) {
                biggestSquare = distSq;
            }
        }
        return (float) Math.sqrt(biggestSquare);
    }

//    public static AABB getBoundingBox(float[] positions) {
//        float minX = Float.MAX_VALUE;
//        float minY = Float.MAX_VALUE;
//        float minZ = Float.MAX_VALUE;
//
//        float maxX = -Float.MAX_VALUE;
//        float maxY = -Float.MAX_VALUE;
//        float maxZ = -Float.MAX_VALUE;
//
//        for (int i = 0; i < positions.length / 3; i++) {
//            float x = positions[3 * i + 0];
//            float y = positions[3 * i + 1];
//            float z = positions[3 * i + 2];
//
//            if (x < minX) {
//                minX = x;
//            }
//            if (x > maxX) {
//                maxX = x;
//            }
//
//            if (y < minY) {
//                minY = y;
//            }
//            if (y > maxY) {
//                maxY = y;
//            }
//
//            if (z < minZ) {
//                minZ = z;
//            }
//            if (z > maxZ) {
//                maxZ = z;
//            }
//        }
//        return new AABB(new Vector3f(minX, minY, minZ), new Vector3f(maxX, maxY, maxZ));
//    }

    public static boolean isPointInTriangle(Vector3f point, Vector3f pa, Vector3f pb, Vector3f pc) {
        Vector3f e10 = Vector3f.sub(pb, pa, null);
        Vector3f e20 = Vector3f.sub(pc, pa, null);

        float a = Vector3f.dot(e10, e10);
        float b = Vector3f.dot(e10, e20);
        float c = Vector3f.dot(e20, e20);
        float ac_bb = (a * c) - (b * b);

        Vector3f vp = new Vector3f(point.x - pa.x, point.y - pa.y, point.z - pa.z);
        float d = Vector3f.dot(vp, e10);
        float e = Vector3f.dot(vp, e20);
        float x = (d * c) - (e * b);
        float y = (e * a) - (d * b);
        float z = x + y - ac_bb;

        return (((int) z & ~(int) x | (int) y) & 0x80000000) != 0;
    }

    public static boolean getLowestRoot(float a, float b, float c, float maxR, Reference<Float> root) {
        float determinant = b * b - 4.0f * a * c;
        if (determinant < 0.0f) {
            return false;
        }

        float sqrtD = (float) Math.sqrt(determinant);
        float r1 = (-b - sqrtD) / (2 * a);
        float r2 = (-b + sqrtD) / (2 * a);
        if (r1 > r2) {
            float temp = r1;
            r1 = r2;
            r2 = temp;
        }

        if (r1 > 0 && r1 < maxR) {
            root.setValue(r1);
            return true;
        }
        if (r2 > 0 && r2 < maxR) {
            root.setValue(r2);
            return true;
        }
        return false;
    }
}
