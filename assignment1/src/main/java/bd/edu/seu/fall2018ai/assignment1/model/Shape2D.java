package bd.edu.seu.fall2018ai.assignment1.model;

import javafx.scene.Group;
import javafx.scene.shape.Shape;

public abstract class Shape2D {
    public abstract boolean collidesWith(Shape2D that);

    public abstract Group getJFXShape();

    public double clamp(double value, double min, double max) {
        if (value < min)
            value = min;
        if (value > max)
            value = max;
        return value;
    }
}
