class Vector3D {
    private double i, j, k;
    private Point3D origin;

    public Vector3D(Point3D origin, double i, double j, double k) {
        this.origin = origin;
        this.i = i;
        this.j = j;
        this.k = k;
    }

    public double getI() {
        return i;
    }

    public void setI(double i) {
        this.i = i;
    }

    public double getJ() {
        return j;
    }

    public void setJ(double j) {
        this.j = j;
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }

    public Point3D getOrigin() {
        return origin;
    }

    public void setOrigin(double x, double y, double z) {
        origin = new Point3D(x, y, z);
    }

    public void setOrigin(Point3D origin) {
        this.origin = origin;
    }

    public Point3D getPoint(double t) {
        double x = origin.x + i * t, y = origin.y + j * t, z = origin.z + k * t;
        return new Point3D(x, y, z);
    }
}