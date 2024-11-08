import java.awt.Color;

interface RelativeFace {
    public Point3D getIntersection(Vector3D vector);
    public Point3D getPointOnFace(Point2D screenPoint);
    public boolean isBetween(Point3D point1, Point3D point2);
    public double getIntersectionAngle(Vector3D vector);
    public boolean is2DPointInside(Point2D point);
    public boolean isBehindCamera();
    public int get2DLeftBound();
    public int get2DRightBound();
    public int get2DHighBound();
    public int get2DLowBound();
    public Color getColor();
}