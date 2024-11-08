import java.util.ArrayList;
import java.awt.Color;

public class Model {
    private Point3D position;
    private Angle3D rotation;
    private ArrayList<Face> faces;
    
    public Model() {
        position = new Point3D(0, 0, 0);
        rotation = new Angle3D(0, 0, 0);
        faces = new ArrayList<Face>();
    }
    
    public Model(double x, double y, double z) {
        position = new Point3D(x, y, z);
        rotation = new Angle3D(0, 0, 0);
        faces = new ArrayList<Face>();
    }
    
    public Model(Point3D position) {
        this.position = position;
        rotation = new Angle3D(0, 0, 0);
        faces = new ArrayList<Face>();
    }
    
    public Point3D getPosition() {
        return position;
    }
    
    public void setPosition(Point3D position) {
        this.position = position;
    }
    
    public void setPosition(double x, double y, double z) {
        position = new Point3D(x, y, z);
    }
    
    public void move(double x, double y, double z) {
        position.x += x;
        position.y += y;
        position.z += z;
    }
    
    public Angle3D getRotation() {
        return rotation;
    }
    
    public void setRotation(Angle3D rotation) {
        this.rotation = rotation;
    }
    
    public void setRotation(double x, double y, double z) {
        rotation = new Angle3D(x, y, z);
    }
    
    public void rotate(Angle3D rotation) {
        this.rotation.x += rotation.x;
        this.rotation.y += rotation.y;
        this.rotation.z += rotation.z;
    }
    
    public void rotate(double x, double y, double z) {
        rotation.x += x;
        rotation.y += y;
        rotation.z += z;
    }
    
    public ArrayList<Face> getFaces() {
        ArrayList<Face> newFaces = new ArrayList<Face>();
        for(int i = 0; i < faces.size(); i++) {
            newFaces.add(toRelativeFace(faces.get(i)));
        }
        return newFaces;
    }

    private Face toRelativeFace(Face face) {
        Face newFace;
        if(face instanceof Triangle) {
            Triangle triangle = (Triangle) face;
            Point3D[] vertices = triangle.getVertices();
            Point3D[] newVertices = new Point3D[3];
            newVertices[0] = toRelativePoint(vertices[0]);
            newVertices[1] = toRelativePoint(vertices[1]);
            newVertices[2] = toRelativePoint(vertices[2]);
            newFace = new Triangle(newVertices[0], newVertices[1], newVertices[2]);
            newFace.setColor(triangle.getColor());
        } else {
            Sphere sphere = (Sphere) face;
            Point3D center = sphere.getCenter();
            Point3D newCenter = toRelativePoint(center);
            newFace = new Sphere(newCenter, sphere.getRadius());
            newFace.setColor(sphere.getColor());
        }
        return newFace;
    }

    private Point3D toRelativePoint(Point3D point) {
        double xRadians = rotation.getXRadians();
        double yRadians = rotation.getYRadians();
        // double zRadians = rotation.getZRadians();
        double x = point.x + position.x;
        double y = point.y + position.y;
        double z = point.z + position.z;

        // double x1 = x * Math.cos(xRadians);
        // double z1 = z * Math.sin(xRadians);

        double newX = x * Math.cos(xRadians) - z * Math.sin(xRadians);
        double newY = y * Math.cos(yRadians) - z * Math.sin(yRadians) * Math.cos(xRadians) - x * Math.sin(xRadians) * Math.sin(yRadians);
        double newZ = z * Math.cos(xRadians) * Math.cos(yRadians) + x * Math.sin(xRadians) * Math.cos(yRadians) + y * Math.sin(yRadians);
        return new Point3D(newX, newY, newZ);
    }
    
    public void addFace(Face face) {
        faces.add(face);
    }

    public void addTriangle(Point3D vertex1, Point3D vertex2, Point3D vertex3, Color color) {
        Triangle triangle = new Triangle(vertex1, vertex2, vertex3);
        triangle.setColor(color);
        faces.add(triangle);
    }

    public void addTriangle(Point3D vertex1, Point3D vertex2, Point3D vertex3) {
        Triangle triangle = new Triangle(vertex1, vertex2, vertex3);
        faces.add(triangle);
    }

    public void addSphere(Point3D center, double radius, Color color) {
        Sphere sphere = new Sphere(center, radius);
        sphere.setColor(color);
        faces.add(sphere);
    }

    public void addSphere(Point3D center, double radius) {
        Sphere sphere = new Sphere(center, radius);
        faces.add(sphere);
    }
}