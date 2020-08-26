/*
 * Ну вы же понимаете, что код здесь только мой?
 * Well, you do understand that the code here is only mine?
 */

package net.steelswing.test.settings;

/**
 *
 * @author MrJavaCoder
 */
public class SettingsManager {

    private int //
            waterQuality = 1,
            viewDistance = 100;
    
    private float //
            shadowDistance = 30f,
            shadowTransitionDistance = 10f;
    
    
    private boolean //
            bloom = false,
            post = false,
            multisampling = false;

    public SettingsManager() {
    }

    public int getWaterQuality() {
        return waterQuality;
    }

    public int getViewDistance() {
        return viewDistance;
    }

    public float getShadowDistance() {
        return shadowDistance;
    }

    public float getShadowTransitionDistance() {
        return shadowTransitionDistance;
    }

    public boolean isBloom() {
        return bloom;
    }

    public boolean isPost() {
        return post;
    }

    public boolean isMultisampling() {
        return multisampling;
    }
    
}
