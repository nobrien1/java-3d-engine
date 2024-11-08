class Plane {
    private double a, b, c, d;

    public Plane(Point3D point1, Point3D point2, Point3D point3) {
        double a1 = point2.x - point1.x;
        double b1 = point2.y - point1.y;
        double c1 = point2.z - point1.z;
        double a2 = point3.x - point1.x;
        double b2 = point3.y - point1.y;
        double c2 = point3.z - point1.z;

        a = b1 * c2 - b2 * c1;
        b = a2 * c1 - a1 * c2;
        c = a1 * b2 - b1 * a2;
        d = -a * point1.x - b * point1.y - c * point1.z;
    }

    // public Point3D getPointOnPlane(Point2D screenPoint) {
    //     double x = (screenPoint.x - screen.getWidth() / 2) / 200;
    //     double y = -(screenPoint.y - screen.getHeight() / 2) / 200;
    //     double t = -d / (a * x + b * y + c);
    //     return new Point3D(x * t, y * t, t);
    // }

    //ax + by + cz + d = 0
    //a(o.x + it) + b(o.y + jt) + c(o.z + kt) + d = 0
    //ax0 + ait + by0 + bjt + cz0 + ckt + d = 0
    //ait + bjt + ckt = -d - ax0 - by0 - cz0
    //t(ai + bj + cz) = ...
    //t = (-d - ax0 - by0 - cz0) / (ai + bj + cz)

    public Point3D getIntersection(Point3D origin, double i, double j, double k) {
        double t = (-d - a * origin.x - b * origin.y - c * origin.z) / (a * i + b * j + c * k);
        double newX = origin.x + i * t;
        double newY = origin.y + j * t;
        double newZ = origin.z + k * t;
        return new Point3D(newX, newY, newZ);
    }

    public Point3D getIntersection(Vector3D vector) {
        Point3D origin = vector.getOrigin();
        double i = vector.getI(), j = vector.getJ(), k = vector.getK();
        return getIntersection(origin, i, j, k);
    }

    public double getIntersectionAngle(Vector3D vector) {
        double i = vector.getI(), j = vector.getJ(), k = vector.getK();
        double top = a * i + b * j + c * k;
        double bottom = Math.sqrt(a * a + b * b + c * c) * Math.sqrt(i * i + j * j + k * k);
        double idk = top / bottom;
        return Math.acos(idk) / Math.PI * 180;
    }
}