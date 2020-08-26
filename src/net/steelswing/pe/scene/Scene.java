/*
 * Ну вы же понимаете, что код здесь только мой?
 * Well, you do understand that the code here is only mine?
 */

package net.steelswing.pe.scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.steelswing.pe.opengl.GameLoop;
import net.steelswing.pe.opengl.RenderSystem;
import net.steelswing.pe.opengl.Light;
import net.steelswing.pe.scene.object.GameObject;
import net.steelswing.pe.util.Log;

/**
 *
 * @author MrJavaCoder
 */
public class Scene extends GameLoop {

    private Map<String, GameObject> objects;
    private List<Light> lights;

    private boolean visible;
    private final String name;

    /**
     *
     * @param name название сцены
     * @param visible видимость сцены
     */
    public Scene(String name, boolean visible) {
        this.name = name;
        this.objects = new HashMap<>();
        this.lights = new ArrayList<>();
        this.visible = visible;
        RenderSystem.addScene(this);
    }

    /**
     *
     * @param name название сцены
     */
    public Scene(String name) {
        this(name, true);
    }

    /**
     * Апдейт всех объектов
     *
     * @param delta дельта тайм
     */
    @Override
    public void update(float delta) {
        for (Map.Entry<String, GameObject> object : objects.entrySet()) {
            object.getValue().update(delta);
//            for (Map.Entry<String, GameObject> child : object.getValue().getChildren().entrySet()) {
//                child.getValue().update(delta);
//            }
        }
    }

    /**
     * Метод для добавление игрового объекта.
     *
     * @param object объект.
     * @return текущую сцену с новый объектом.
     */
    public Scene addGameObject(GameObject object) {
        if (object == null) {
            Log.warn("(Scene: '" + name + "') Object = null!");
            return this;
        }
        if (objects.containsKey(object.getName())) {
            Log.warn("(Scene: '" + name + "') Object '" + object.getName() + "' exists");
            return this;
        }
        if (object.getModel() == null) {
            Log.warn("(Scene: '" + name + "') Object '" + object.getName() + "' is not contains model, or model = null!");
        }
        objects.put(object.getName(), object);
        return this;
    }

    /**
     * Метод для удаления объекта.
     *
     * @param name название объекта.
     * @return статус удаления (true - удалена, false - нет)
     */
    public boolean removeGameObject(String name) {
        boolean done = objects.containsKey(name);
        if (done) {
            objects.remove(name);
        }
        return done;
    }

    /**
     * Метод для удаления объекта.
     *
     * @param object объект.
     * @return статус удаления (true - удалена, false - нет)
     */
    public boolean removeGameObject(GameObject object) {
        boolean done = objects.containsValue(object);
        if (done) {
            objects.remove(object.getName(), object);
        }
        return done;
    }

    /**
     * Метод для добавление light.
     *
     * @param light light.
     * @return текущую сцену с новый объектом.
     */
    public Scene addLight(Light light) {
        if (light == null) {
            Log.warn("(Scene: '" + name + "') Light = null!");
            return this;
        }
        for (Light obj : lights) {
            if (obj.getName().equals(light.getName())) {
                Log.warn("(Scene: '" + name + "') Light '" + light.getName() + "' exists");
                return this;
            }
        }
        lights.add(light);
        return this;
    }

    /**
     * Метод для удаления light.
     *
     * @param name название light.
     * @return статус удаления (true - удалена, false - нет)
     */
    public boolean removeLight(String name) {
        boolean done = false;
        for (Light obj : lights) {
            if (obj.getName().equals(name)) {
                lights.remove(obj);
                done = true;
                break;
            }
        }
        return done;
    }

    /**
     * Метод для удаления light.
     *
     * @param light light.
     * @return статус удаления (true - удалена, false - нет)
     */
    public boolean removeLight(Light light) {
        boolean done = lights.contains(light);
        if (done) {
            lights.remove(light);
        }
        return done;
    }

    /**
     * Метод для установки текущей камеры.
     *
     * @param camera новая камера
     * @return текущую сцену с новый объектом.
     */
    public Scene setCurrentCamera(Camera camera) {
        if (camera == null) {
            Log.warn("New camera = null!");
            return this;
        }
        RenderSystem.setCurrentCamera(camera);
        return this;
    }

    /**
     *
     * @return текущую камеру.
     */
    public Camera getCurrentCamera() {
        return RenderSystem.getCurrentCamera();
    }

    /**
     *
     * @return список объектов этой сцены.
     */
    public Map<String, GameObject> getObjects() {
        return objects;
    }

    /**
     *
     * @return видна ли текущая сцена.
     */
    public boolean isVisible() {
        return visible;
    }

    public String getName() {
        return name;
    }

    /**
     *
     * @return список lights этой сцены.
     */
    public List<Light> getLights() {
        return lights;
    }
}
