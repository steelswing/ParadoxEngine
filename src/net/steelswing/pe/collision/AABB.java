/*
 * Ну вы же понимаете, что код здесь только мой?
 * Well, you do understand that the code here is only mine?
 */

package net.steelswing.pe.collision;

import net.steelswing.pe.util.MathUtil;
import org.lwjglx.util.vector.Vector3f;

/**
 *
 * @author MrJavaCoder
 */
public class AABB {

    private Vector3f //
            minExtents,
            maxExtents;

    public AABB(Vector3f minExtents, Vector3f maxExtents) {
        this.minExtents = minExtents;
        this.maxExtents = maxExtents;
    }

    public IntersectData intersectAABB(AABB other) {
//        boolean x = (minExtents.x <= other.maxExtents.x && maxExtents.x >= other.minExtents.x); // X
//        boolean y = (minExtents.y <= other.maxExtents.y && maxExtents.y >= other.minExtents.y); // Y
//        boolean z = (minExtents.z <= other.maxExtents.z && maxExtents.z >= other.minExtents.z); // Z
//        return new IntersectData(x, y, z);

        Vector3f distances1 = new Vector3f();
        Vector3f distances2 = new Vector3f();
        Vector3f.sub(other.getMinExtents(), this.maxExtents, distances1);
        Vector3f.sub(this.minExtents, other.getMaxExtents(), distances2);
        Vector3f distances = MathUtil.vectorMax3f(distances1, distances2);
        float maxDistance = MathUtil.vectorMaxComponent3f(distances);
        return new IntersectData(maxDistance <= 0, maxDistance);
    }

    public boolean check(Vector3f xyz, Vector3f size, AABB... colliders) {
        AABB checker = new AABB(xyz, MathUtil.sum(xyz, size, new Vector3f(0)));

        AABB current = null;
        for (AABB collider : colliders) {
            if (collider == null) {
                continue;
            }
            if (checker.intersectAABB(collider).isIntersecting()) {
                current = collider;
            }
        }
        return current != null ? !checker.intersectAABB(current).isIntersecting() : true;
    }

    public static boolean aabb(Vector3f a, Vector3f aa, Vector3f b, Vector3f bb) {
        return (a.x <= bb.x && aa.x >= b.x)
                && (a.y <= bb.y && aa.y >= b.y)
                && (a.z <= bb.z && aa.z >= b.z);
    }

    public Vector3f getMinExtents() {
        return this.minExtents;
    }

    public Vector3f getMaxExtents() {
        return this.maxExtents;
    }

    public void setMinExtents(Vector3f minExtents) {
        this.minExtents = minExtents;
    }

    public void setMaxExtents(Vector3f maxExtents) {
        this.maxExtents = maxExtents;
    }

    @Override
    public String toString() {
        return "AABB{" + "minExtents=" + minExtents + ", maxExtents=" + maxExtents + '}';
    }
}
