import java.awt.Color;

public class Sphere extends Face {
    private Point3D center;
    private double radius;

    public Sphere() {
        center = new Point3D(0, 0, 0);
        radius = 0;
    }

    public Sphere(Color color) {
        super(color);
        center = new Point3D(0, 0, 0);
        radius = 0;
    }

    public Sphere(Point3D center, double radius) {
        super();
        this.center = center;
        this.radius = radius;
    }

    public Point3D getCenter() {
        return center;
    }

    public void setCenter(Point3D center) {
        this.center = center;
    }

    public void setCenter(double x, double y, double z) {
        center = new Point3D(x, y, z);
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}