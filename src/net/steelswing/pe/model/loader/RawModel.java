/*
 * Ну вы же понимаете, что код здесь только мой?
 * Well, you do understand that the code here is only mine?
 */

package net.steelswing.pe.model.loader;

/**
 *
 * @author MrJavaCoder
 */
public class RawModel {

    private int vaoId, vertexCount;

    public RawModel(int vaoId, int vertexCount) {
        this.vaoId = vaoId;
        this.vertexCount = vertexCount;
    }

    public int getVaoId() {
        return vaoId;
    }

    public int getVertexCount() {
        return vertexCount;
    }
    
    public RawModel copy() {
        return new RawModel(vaoId, vertexCount);
    }
}
