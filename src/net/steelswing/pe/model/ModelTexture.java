/*
 * Ну вы же понимаете, что код здесь только мой?
 * Well, you do understand that the code here is only mine?
 */

package net.steelswing.pe.model;

/**
 *
 * @author MrJavaCoder
 */
public class ModelTexture {

    private int //
            id, 
            specularId,
            numberOfRow = 1,
            textureIndex = 1;

    private float //
            shineDumper = 1,
            reflectivity = 0;

    private boolean //
            useFakeLight = false,
            useSpecularMap = false,
            hasTransparensy = false;

    public ModelTexture(int textureId) {
        this.id = textureId;
    }

    public boolean isUseFakeLight() {
        return useFakeLight;
    }

    public void setUseFakeLight(boolean useFakeLight) {
        this.useFakeLight = useFakeLight;
    }

    public boolean isHasTransparensy() {
        return hasTransparensy;
    }

    public void setHasTransparensy(boolean hasTransparensy) {
        this.hasTransparensy = hasTransparensy;
    }

    public float getShineDumper() {
        return shineDumper;
    }

    public void setShineDumper(float shineDumper) {
        this.shineDumper = shineDumper;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }

    public int getNumberOfRow() {
        return numberOfRow;
    }

    public void setNumberOfRow(int numberOfRow) {
        this.numberOfRow = numberOfRow;
    }

    public boolean isUseSpecularMap() {
        return useSpecularMap;
    }

    public ModelTexture copy() {
        ModelTexture t = new ModelTexture(id);
        t.hasTransparensy = hasTransparensy;
        t.numberOfRow = numberOfRow;
        t.reflectivity = reflectivity;
        t.shineDumper = shineDumper;
        t.specularId = specularId;
        t.textureIndex = textureIndex;
        t.useFakeLight = useFakeLight;
        t.useSpecularMap = useSpecularMap;
        return t;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSpecularId(int specularId) {
        this.specularId = specularId;
    }

    public void setUseSpecularMap(boolean useSpecularMap) {
        this.useSpecularMap = useSpecularMap;
        this.useSpecularMap = true;
    }

    public int getId() {
        return id;
    }

    public int getSpecularId() {
        return specularId;
    }
    
    // Texture

    public float getTextureXOffset() {
        int column = textureIndex % numberOfRow;
        return (float) column / (float) numberOfRow;
    }

    public float getTextureYOffset() {
        int column = textureIndex / numberOfRow;
        return (float) column / (float) numberOfRow;
    }

    public int getTextureIndex() {
        return textureIndex;
    }

    public void setTextureIndex(int textureIndex) {
        this.textureIndex = textureIndex;
    }

}
