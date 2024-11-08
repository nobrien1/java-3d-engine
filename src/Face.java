import java.awt.Color;

public abstract class Face {
    private Color color;

    public Face() {
        color = Color.GRAY;
    }

    public Face(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}