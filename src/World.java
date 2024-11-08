import java.util.ArrayList;

public class World {
    private ArrayList<Model> models;
    private ArrayList<Light> lights;
    
    public World() {
        models = new ArrayList<Model>();
        lights = new ArrayList<Light>();
    }
    
    public ArrayList<Model> getModels() {
        return models;
    }
    
    public void addModel(Model model) {
        models.add(model);
    }

    public ArrayList<Light> getLights() {
        return lights;
    }
    
    public void addLight(Light light) {
        lights.add(light);
    }

    public ArrayList<Face> getFaces() {
        ArrayList<Face> faces = new ArrayList<Face>();
        for(int i = 0; i < models.size(); i++) {
            ArrayList<Face> modelFaces = models.get(i).getFaces();
            faces.addAll(modelFaces);
        }
        return faces;
    }
}