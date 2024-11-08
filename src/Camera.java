import java.util.ArrayList;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class Camera {
    private Point3D position;
    private Angle3D angle;
    private BufferedImage screen;
    private Point3D[][] locations;
    private World world;
    
    public Camera() {
        position = new Point3D(0, 0, 0);
        angle = new Angle3D(0, 0, 0);
        screen = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
        locations = new Point3D[screen.getHeight()][screen.getWidth()];
        world = new World();
    }
    
    public Camera(double x, double y, double z) {
        position = new Point3D(x, y, z);
        angle = new Angle3D(0, 0, 0);
        screen = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
        locations = new Point3D[screen.getHeight()][screen.getWidth()];
        world = new World();
    }
    
    public Camera(Point3D position) {
        this.position = position;
        angle = new Angle3D(0, 0, 0);
        screen = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
        locations = new Point3D[screen.getHeight()][screen.getWidth()];
        world = new World();
    }

    public Camera(int width, int height) {
        position = new Point3D(0, 0, 0);
        angle = new Angle3D(0, 0, 0);
        screen = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        locations = new Point3D[screen.getHeight()][screen.getWidth()];
        world = new World();
    }

    public Camera(World world) {
        position = new Point3D(0, 0, 0);
        angle = new Angle3D(0, 0, 0);
        screen = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
        locations = new Point3D[screen.getHeight()][screen.getWidth()];
        this.world = world;
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
    
    public Angle3D getAngle() {
        return angle;
    }
    
    public void setAngle(Angle3D angle) {
        this.angle = angle;
    }
    
    public void setAngle(double x, double y, double z) {
        angle = new Angle3D(x, y, z);
    }
    
    public void rotate(Angle3D angle) {
        this.angle.x += angle.x;
        this.angle.y += angle.y;
        this.angle.z += angle.z;
    }
    
    public void rotate(double x, double y, double z) {
        angle.x += x;
        angle.y += y;
        angle.z += z;
    }

    public int getWidth() {
        return screen.getWidth();
    }

    public int getHeight() {
        return screen.getHeight();
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public BufferedImage render(World world) {
        int width = screen.getWidth();
        int height = screen.getHeight();
        screen = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        locations = new Point3D[screen.getHeight()][screen.getWidth()];
        RelativeFace[] faces = getRelativeFaces();

        for(int i = 0; i < faces.length; i++) {
            drawFace(faces[i], i);
        }

        return screen;
    }

    private RelativeFace[] getRelativeFaces() {
        ArrayList<Face> faces = world.getFaces();
        RelativeFace[] relativeFaces = new RelativeFace[faces.size()];

        for(int i = 0; i < faces.size(); i++) {
            if(faces.get(i) instanceof Triangle) {
                relativeFaces[i] = new RelativeTriangle((Triangle) faces.get(i));
            } else {
                relativeFaces[i] = new RelativeSphere((Sphere) faces.get(i));
            }
        }

        return relativeFaces;
    }

    private Point3D toRelativePoint(Point3D point) {
        double xRadians = angle.getXRadians();
        double yRadians = angle.getYRadians();
        double x = point.x - position.x;
        double y = point.y - position.y;
        double z = point.z - position.z;
        
        // // may need to change formulas
        double newX = x * Math.cos(xRadians) - z * Math.sin(xRadians);
        double newY = y * Math.cos(yRadians) - z * Math.sin(yRadians) * Math.cos(xRadians) - x * Math.sin(xRadians) * Math.sin(yRadians);
        double newZ = z * Math.cos(xRadians) * Math.cos(yRadians) + x * Math.sin(xRadians) * Math.cos(yRadians) + y * Math.sin(yRadians);
        return new Point3D(newX, newY, newZ);
    }

    private void drawFace(RelativeFace face, int iteration) {
        if(face.isBehindCamera()) return;

        ArrayList<Light> lights = world.getLights();
        RelativeFace[] relativeFaces = getRelativeFaces();
        int leftBound = face.get2DLeftBound();
        int rightBound = face.get2DRightBound();
        int highBound = face.get2DHighBound();
        int lowBound = face.get2DLowBound();

        for(int i = highBound; i <= lowBound; i++) {
            for(int j = leftBound; j <= rightBound; j++) {
                Point2D point2D = new Point2D(j, i);
                if(face.is2DPointInside(point2D)) {
                    Point3D point = face.getPointOnFace(point2D);
                    if(locations[i][j] == null || point.z < locations[i][j].z) {
                        double lightMod = 0.1;
                        for(int k = 0; k < lights.size(); k++) {
                            Point3D relativePoint = toRelativePoint(lights.get(k).getPosition());
                            Vector3D line = new Vector3D(relativePoint, relativePoint.x - point.x, relativePoint.y - point.y, relativePoint.z - point.z);
                            double angle = face.getIntersectionAngle(line);
                            double idk;
                            if(angle <= 90) {
                                idk = 1 - angle / 90 * .9;
                                // continue;
                            } else {
                                // idk =  1 - angle / 180 * .9;
                                continue;
                            }
                            boolean blocked = false;
                            for(int l = 0; l < relativeFaces.length; l++) {
                                if(l == iteration) continue;
                                if(relativeFaces[l].isBetween(point, relativePoint)) {
                                    blocked = true;
                                    break;
                                }
                            }
                            if(blocked) continue;
                            lightMod += idk;
                            if(lightMod >= 1) {
                                lightMod = 1;
                                break;
                            }
                        }
                        Color color = face.getColor();
                        int r = (int) (color.getRed() * lightMod);
                        int g = (int) (color.getGreen() * lightMod);
                        int b = (int) (color.getBlue() * lightMod);
                        
                        
                        int colorInt = new Color(r, g, b).getRGB();
                        screen.setRGB(j, i, colorInt);
                        locations[i][j] = point;
                    }
                }
            }
        }
    }

    private Point2D project(Point3D point) {
        double r = 200 / point.z;
        return new Point2D(screen.getWidth() / 2 + r * point.x, screen.getHeight() / 2 - r * point.y);
    }

    private class RelativeTriangle implements RelativeFace {
        private Triangle triangle;
        private Plane plane;
        private Triangle2D triangle2D;
        
        public RelativeTriangle(Triangle triangle) {
            convertTriangle(triangle);
            Point3D[] vertices = this.triangle.getVertices();
            plane = new Plane(vertices[0], vertices[1], vertices[2]);
            triangle2D = new Triangle2D(project(vertices[0]), project(vertices[1]), project(vertices[2]));
        }

        private void convertTriangle(Triangle triangle) {
            Point3D[] vertices = triangle.getVertices();
            Point3D[] newVertices = new Point3D[3];
            newVertices[0] = toRelativePoint(vertices[0]);
            newVertices[1] = toRelativePoint(vertices[1]);
            newVertices[2] = toRelativePoint(vertices[2]);
            this.triangle = new Triangle(newVertices[0], newVertices[1], newVertices[2]);
            this.triangle.setColor(triangle.getColor());
        }

        public Point3D getIntersection(Vector3D vector) {
            return plane.getIntersection(vector);
        }

        public Point3D getPointOnFace(Point2D screenPoint) {
            Point3D origin = new Point3D(0, 0, 0);
            double i = (screenPoint.x - screen.getWidth() / 2) / 200;
            double j = -(screenPoint.y - screen.getHeight() / 2) / 200;
            return getIntersection(new Vector3D(origin, i, j, 1));
        }
        
        public boolean isBetween(Point3D point1, Point3D point2) {
            double i = point2.x - point1.x, j = point2.y - point1.y, k = point2.z - point1.z;
            Vector3D vector = new Vector3D(point1, i, j, k);
            Point3D intersection = getIntersection(vector);
            Point2D intersection2D = intersection.getPoint2D();
            if(intersection2D.x < point1.getPoint2D().x && intersection2D.x < point2.getPoint2D().x) return false;
            else if(intersection2D.y < point1.getPoint2D().y && intersection2D.y < point2.getPoint2D().y) return false;
            else if(intersection2D.x > point1.getPoint2D().x && intersection2D.x > point2.getPoint2D().x) return false;
            else if(intersection2D.y > point1.getPoint2D().y && intersection2D.y > point2.getPoint2D().y) return false;
            Point3D[] vertices = triangle.getVertices();
            Point2D[] vertices2D = {vertices[0].getPoint2D(), vertices[1].getPoint2D(), vertices[2].getPoint2D()};
            Triangle2D tri = new Triangle2D(vertices2D[0], vertices2D[1], vertices2D[2]);
            return tri.isPointInside(intersection.getPoint2D());
        }

        public double getIntersectionAngle(Vector3D vector) {
            return plane.getIntersectionAngle(vector);
        }

        public boolean is2DPointInside(Point2D point) {
            return triangle2D.isPointInside(point);
        }

        public boolean isBehindCamera() {
            Point3D[] vertices = triangle.getVertices();
            for(int i = 0; i < vertices.length; i++) {
                if(vertices[i].z <= 0) {
                    return true;
                }
            }
            return false;
        }

        public int get2DLeftBound() {
            int leftBound = (int) Math.floor(triangle2D.getLeftmostPoint().x);
            return leftBound <= 0 ? 0 : leftBound;
        }

        public int get2DRightBound() {
            int rightBound = (int) Math.ceil(triangle2D.getRightmostPoint().x);
            return (rightBound > screen.getWidth() - 1) ? screen.getWidth() - 1 : rightBound;
        }

        public int get2DHighBound() {
            int highBound = (int) Math.floor(triangle2D.getHighestPoint().y);
            return highBound <= 0 ? 0 : highBound;
        }

        public int get2DLowBound() {
            int lowBound = (int) Math.ceil(triangle2D.getLowestPoint().y);
            return (lowBound > screen.getHeight() - 1) ? screen.getHeight() - 1 : lowBound;
        }

        public Color getColor() {
            return triangle.getColor();
        }
    }

    private class RelativeSphere implements RelativeFace {
        private Sphere sphere;
        private Circle2D circle;

        public RelativeSphere(Sphere sphere) {
            convertSphere(sphere);
            createCircle2D();
        }

        private void convertSphere(Sphere sphere) {
            Point3D center = sphere.getCenter();
            Point3D newCenter = toRelativePoint(center);
            this.sphere = new Sphere(newCenter, sphere.getRadius());
            this.sphere.setColor(sphere.getColor());
        }

        private void createCircle2D() {
            Point3D center = sphere.getCenter();
            Point2D center2D = project(center);
            double radius = sphere.getRadius();
            double radius2D = 200 / center.z * radius;
            circle = new Circle2D(center2D, radius2D);
        }

        public Point3D getIntersection(Vector3D vector) {
            Point3D origin = vector.getOrigin();
            double i = vector.getI(), j = vector.getJ(), k = vector.getK();
            Point3D center = sphere.getCenter();
            double a = i * i + j * j + k * k;
            double b = 2 * (i * (origin.x - center.x) + j * (origin.y - center.y) + k * (origin.z - center.z));
            // double c = Math.pow(origin.x - center.x, 2) + Math.pow(origin.y - center.y, 2) + Math.pow(origin.z - center.z, 2) - Math.pow(sphere.getRadius(), 2);
            double c = origin.x * origin.x - 2 * origin.x * center.x + center.x * center.x + origin.y * origin.y - 2 * origin.y * center.y + center.y * center.y + origin.z * origin.z - 2 * origin.z * center.z + center.z * center.z - sphere.getRadius() * sphere.getRadius();
            double t = getLowestRoot(a, b, c);
            if(t == Double.NaN) {
                return null;
            } else {
                return vector.getPoint(t);
            }
        }

        private double getLowestRoot(double a, double b, double c) {
            double d = b * b - 4 * a * c;
            if(d >= 0) {
                double ans1 = (-b + Math.sqrt(d)) / (2 * a);
                double ans2 = (-b - Math.sqrt(d)) / (2 * a);
                return ans1 < ans2 ? ans1 : ans2;
            } else {
                return Double.NaN;
            }
        }
        //(x - h)^2 + (y - k)^2 + (z - l)^2 = r^2
        //(x0 + xt - h)^2 + (y0 + yt - k)^2 + (z0 + zt - l)^2 = r^2
        //(x0^2 + x0xt - x0h + x0xt + xt^2 - xth - x0h - xth + h^2)
        //x0^2 + 2x0xt - 2x0h + xt^2 - 2xth + h^2 + y0^2 + 2y0yt - 2y0k + yt^2 - 2ytk + k^2 + z0^2 + 2z0zt - 2z0l + zt^2 - 2ztl + l^2 - r^2 = 0
        //                      ----                                      ----                                      ----
        //       -----                 ----                -----                 ----                -----                 ----
        //----           ----                 ---   ----           ----                 ---   ----           ----                 ---   ---
        // (x^2 + y^2 + z^2)t^2 + 2(x0x - xh + y0y - yk + z0z - zl)t + (x0^2 - 2x0h + h^2 + y0^2 - 2y0k + k^2 + z0^2 - 2z0l + l^2 - r^2) = 0

        public Point3D getPointOnFace(Point2D screenPoint) {
            Point3D origin = new Point3D(0, 0, 0);
            double i = (screenPoint.x - screen.getWidth() / 2) / 200;
            double j = -(screenPoint.y - screen.getHeight() / 2) / 200;
            return getIntersection(new Vector3D(origin, i, j, 1));
        }
        
        public boolean isBetween(Point3D point1, Point3D point2) {
            return false;
        }

        public double getIntersectionAngle(Vector3D vector) {
            Point3D origin = vector.getOrigin();
            double i = vector.getI(), j = vector.getJ(), k = vector.getK();
            Point3D o2 = new Point3D(origin.x + 0.01, origin.y + 0.01, origin.z + 0.01);
            Point3D o3 = new Point3D(origin.x + 0.02, origin.y + 0.02, origin.z - 0.02);
            Point3D p1 = getIntersection(new Vector3D(origin, i, j, k));
            Point3D p2 = getIntersection(new Vector3D(o2, i, j, k));
            Point3D p3 = getIntersection(new Vector3D(o3, i, j, k));
            Plane p = new Plane(p1, p2, p3);
            return p.getIntersectionAngle(vector);
        }

        public boolean is2DPointInside(Point2D point) {
            return circle.isPointInside(point);
        }

        public boolean isBehindCamera() {
            return sphere.getCenter().z - sphere.getRadius() <= 0;
        }

        public int get2DLeftBound() {
            int leftBound = (int) Math.floor(circle.getLeftmostPoint().x);
            return leftBound <= 0 ? 0 : leftBound;
        }

        public int get2DRightBound() {
            int rightBound = (int) Math.ceil(circle.getRightmostPoint().x);
            return (rightBound > screen.getWidth() - 1) ? screen.getWidth() - 1 : rightBound;
        }

        public int get2DHighBound() {
            int highBound = (int) Math.floor(circle.getHighestPoint().y);
            return highBound <= 0 ? 0 : highBound;
        }

        public int get2DLowBound() {
            int lowBound = (int) Math.ceil(circle.getLowestPoint().y);
            return (lowBound > screen.getHeight() - 1) ? screen.getHeight() - 1 : lowBound;
        }

        public Color getColor() {
            return sphere.getColor();
        }
    }
}