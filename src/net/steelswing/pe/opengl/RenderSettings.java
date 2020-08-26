/*
 * Ну вы же понимаете, что код здесь только мой?
 * Well, you do understand that the code here is only mine?
 */

package net.steelswing.pe.opengl;

/**
 *
 * @author MrJavaCoder
 */
public class RenderSettings {

    // Shadows
    private boolean //
            renderShadows = true,
            usePcf = false;
    private int //
            pcfCount = 1,
            shadowMapSize = 2048 * 2;
    private float //
            shadowDistance = 10,
            shadowRenderDistance = shadowDistance * 3,
            shadowTransitionDistance = 10;

    // Etc
    private boolean multisampling = true;
    private int sampling = 1;

    // Post processing
    public boolean post = true;
    private boolean bloomEffect = true;

    // Debug
    public boolean //
            showPolygons = false,
            showCollision = true;


    public boolean isRenderShadows() {
        return renderShadows;
    }

    public boolean isUsePcf() {
        return usePcf;
    }

    public int getShadowMapSize() {
        return shadowMapSize;
    }

    public int getPcfCount() {
        return pcfCount;
    }

    public float getShadowDistance() {
        return shadowDistance;
    }

    public float getShadowRenderDistance() {
        return shadowRenderDistance;
    }

    public float getShadowTransitionDistance() {
        return shadowTransitionDistance;
    }

    public boolean isMultisampling() {
        return multisampling;
    }

    public int getSampling() {
        return sampling;
    }

    public boolean isBloomEffect() {
        return bloomEffect;
    }

    public boolean isPost() {
        return post;
    }

    public boolean isShowCollision() {
        return showCollision;
    }

    public boolean isShowPolygons() {
        return showPolygons;
    }

}
