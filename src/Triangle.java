import java.awt.Color;

public class Triangle extends Face {
    private Point3D[] vertices;

    public Triangle() {
        vertices = new Point3D[]{new Point3D(0, 0, 0), new Point3D(0, 0, 0), new Point3D(0, 0, 0)};
    }

    public Triangle(Color color) {
        super(color);
        vertices = new Point3D[]{new Point3D(0, 0, 0), new Point3D(0, 0, 0), new Point3D(0, 0, 0)};
    }
    
    public Triangle(Point3D vertex1, Point3D vertex2, Point3D vertex3) {
        vertices = new Point3D[]{vertex1, vertex2, vertex3};
    }
    
    public Point3D[] getVertices() {
        return vertices;
    }
    
    public void setVertices(Point3D vertex1, Point3D vertex2, Point3D vertex3) {
        vertices = new Point3D[]{vertex1, vertex2, vertex3};
    }
}