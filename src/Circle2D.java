class Circle2D {
    private Point2D center;
    private double radius;

    public Circle2D(Point2D center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public boolean isPointInside(Point2D point) {
        double distance = center.getDistance(point);
        return distance <= radius;
    }

    public Point2D getLeftmostPoint() {
        return new Point2D(center.x - radius, center.y);
    }

    public Point2D getRightmostPoint() {
        return new Point2D(center.x + radius, center.y);
    }

    public Point2D getHighestPoint() {
        return new Point2D(center.x, center.y - radius);
    }

    public Point2D getLowestPoint() {
        return new Point2D(center.x, center.y + radius);
    }
}