public class Point3D extends Point2D {
    public double z;

    public Point3D(double x, double y, double z) {
        super(x, y);
        this.z = z;
    }
    
    public Point2D getPoint2D() {
        return new Point2D(x, y);
    }

    public double getDistance(Point3D other) {
        double xDif = x - other.x;
        double yDif = y - other.y;
        double zDif = z - other.z;
        double xyDistance = Math.sqrt(xDif * xDif + yDif * yDif);
        return Math.sqrt(zDif * zDif + xyDistance * xyDistance);
    }

    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}