public class Point2D {
    public double x, y;

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public double getDistance(Point2D other) {
        double xDif = x - other.x;
        double yDif = y - other.y;
        return Math.sqrt(xDif * xDif + yDif * yDif);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}