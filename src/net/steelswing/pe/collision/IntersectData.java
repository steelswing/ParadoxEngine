
package net.steelswing.pe.collision;

public class IntersectData {

//    public boolean x, y, z;
//
//    public IntersectData(boolean x, boolean y, boolean z) {
//        this.x = x;
//        this.y = y;
//        this.z = z;
//    }
//
//
//    public boolean isIntersect() {
//        return x && y && z;
//    }

    private boolean isIntersecting;
    private float maxDistance;

    public IntersectData(boolean isIntersecting, float maxDistance) {
        this.isIntersecting = isIntersecting;
        this.maxDistance = maxDistance;
    }

    public boolean isIntersecting() {
        return isIntersecting;
    }

    public float getMaxDistance() {
        return maxDistance;
    }

}
