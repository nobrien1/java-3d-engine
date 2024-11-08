class Triangle2D {
    private Point2D[] vertices;

    public Triangle2D(Point2D vertex1, Point2D vertex2, Point2D vertex3) {
        vertices = new Point2D[]{vertex1, vertex2, vertex3};
    }

    public boolean isPointInside(Point2D point) {
        double triangleArea = getTriangleArea(vertices[0], vertices[1], vertices[2]);
        double area1 = getTriangleArea(point, vertices[1], vertices[2]);
        double area2 = getTriangleArea(vertices[0], point, vertices[2]);
        double area3 = getTriangleArea(vertices[0], vertices[1], point);
        return Math.abs(triangleArea - (area1 + area2 + area3)) < 0.001;
    }

    private static double getTriangleArea(Point2D vertex1, Point2D vertex2, Point2D vertex3) {
        return Math.abs((vertex1.x * (vertex2.y - vertex3.y) + vertex2.x * (vertex3.y - vertex1.y) + vertex3.x * (vertex1.y - vertex2.y)) / 2);
    }

    public Point2D getLeftmostPoint() {
        Point2D leftmost = vertices[0];
        for(int i = 1; i < vertices.length; i++) {
            if(vertices[i].x < leftmost.x) {
                leftmost = vertices[i];
            }
        }
        return leftmost;
    }

    public Point2D getRightmostPoint() {
        Point2D rightmost = vertices[0];
        for(int i = 1; i < vertices.length; i++) {
            if(vertices[i].x > rightmost.x) {
                rightmost = vertices[i];
            }
        }
        return rightmost;
    }

    public Point2D getHighestPoint() {
        Point2D highest = vertices[0];
        for(int i = 1; i < vertices.length; i++) {
            if(vertices[i].y < highest.y) {
                highest = vertices[i];
            }
        }
        return highest;
    }

    public Point2D getLowestPoint() {
        Point2D lowest = vertices[0];
        for(int i = 1; i < vertices.length; i++) {
            if(vertices[i].y > lowest.y) {
                lowest = vertices[i];
            }
        }
        return lowest;
    }
}