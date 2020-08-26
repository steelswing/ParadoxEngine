/*
 * Ну вы же понимаете, что код здесь только мой?
 * Well, you do understand that the code here is only mine?
 */

package net.steelswing.pe.scene.object;

import net.steelswing.pe.collision.AABB;
import net.steelswing.pe.model.Model;
import net.steelswing.pe.util.MathUtil;
import net.steelswing.pe.util.Transformable;
import org.lwjglx.util.vector.Vector3f;

/**
 *
 * @author MrJavaCoder
 */
public abstract class GameObject extends Transformable<GameObject> {

    protected Model model;
    protected final String name;

    protected Vector3f start, end;
    protected AABB aabb;

    /**
     *
     * @param name название
     * @param model модель
     * @param aabbbStart XYZ AABB относительно модели
     * @param aabbEnd XYZ AABB относительно модели
     */
    public GameObject(String name, Model model, Vector3f aabbbStart, Vector3f aabbEnd) {
        super(new Vector3f(0f), new Vector3f(0f), new Vector3f(0f), new Vector3f(1f));
        this.name = name;
        this.model = model;
        this.start = aabbbStart;
        this.end = aabbEnd;
        this.aabb = new AABB(MathUtil.sum(position, start), MathUtil.sum(position, end));
        this.updateModel();
    }

    public GameObject(String name, Model model, Vector3f position, Vector3f rotation, Vector3f scale) {
        super(position, new Vector3f(), rotation, scale);
        this.name = name;
        this.model = model;
        this.start = new Vector3f(1);
        this.end = new Vector3f(1);
        this.aabb = new AABB(MathUtil.sum(position, start), MathUtil.sum(position, end));
        this.updateModel();
    }

    public Model getModel() {
        return model;
    }

    public String getName() {
        return name;
    }

    @Override
    public GameObject setPosition(Vector3f position) {
        super.setPosition(position);
        updateModel();
        return this;
    }

    @Override
    public GameObject addCenter(float x, float y, float z) {
        super.addCenter(x, y, z);
        updateModel();
        return this;
    }

    @Override
    public GameObject addScale(float x, float y, float z) {
        super.addScale(x, y, z);
        updateModel();
        return this;
    }

    @Override
    public GameObject addRotation(float x, float y, float z) {
        super.addRotation(x, y, z);
        updateModel();
        return this;
    }

    @Override
    public GameObject addPosition(float x, float y, float z) {
        super.addPosition(x, y, z);
        updateModel();
        return this;
    }

    @Override
    public GameObject setCenter(Vector3f center) {
        super.setCenter(center);
        updateModel();
        return this;
    }

    @Override
    public Vector3f getCenter() {
        return center;
    }

    @Override
    public GameObject setScale(float scale) {
        super.setScale(scale);
        updateModel();
        return this;
    }

    @Override
    public GameObject setScale(Vector3f scale) {
        super.setScale(scale);
        updateModel();
        return this;
    }

    @Override
    public Vector3f getScale() {
        return scale;
    }

    @Override
    public GameObject setRotation(Vector3f rotation) {
        super.setRotation(rotation);
        updateModel();
        return this;
    }

    @Override
    public Vector3f getRotation() {
        return rotation;
    }

    @Override
    public Vector3f getPosition() {
        return position;
    }

    public AABB getAabb() {
        return aabb;
    }

    public Vector3f getStart() {
        return start;
    }

    public void setStart(Vector3f start) {
        this.start = start;
    }

    public Vector3f getEnd() {
        return end;
    }

    public void setEnd(Vector3f end) {
        this.end = end;
    }

    public void updateModel() {
        model.setPosition(position);
        model.setScale(scale);
        model.setRotation(rotation);
        model.setCenter(center);

        model.setPosition(new Vector3f(position.x + (scale.x / 2), position.y + (scale.y / 2), position.z + (scale.z / 2)));
        aabb = new AABB(MathUtil.sum(position, start), MathUtil.sum(position, end));

    }

    public abstract void update(float delta);

}
