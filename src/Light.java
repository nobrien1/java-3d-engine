public class Light {
    private Point3D position;

    public Light() {
        position = new Point3D(0, 0, 0);
    }
    
    public Light(double x, double y, double z) {
        position = new Point3D(x, y, z);
    }
    
    public Light(Point3D position) {
        this.position = position;
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
}