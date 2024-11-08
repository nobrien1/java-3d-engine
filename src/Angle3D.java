public class Angle3D {
    public double x, y, z;

    public Angle3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getXRadians() {
        return Math.toRadians(x);
    }

    public double getYRadians() {
        return Math.toRadians(y);
    }
    
    public double getZRadians() {
        return Math.toRadians(z);
    }
}