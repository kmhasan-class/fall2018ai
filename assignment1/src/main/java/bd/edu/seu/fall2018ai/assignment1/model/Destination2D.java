package bd.edu.seu.fall2018ai.assignment1.model;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import lombok.Data;

@Data
public class Destination2D extends Circle2D {

    public Destination2D(Point2D center, double radius) {
        super(center, radius);
        super.setFill(new Color(0, 0.7, 0, 0.7));
        super.setCenterDrawable(true);
    }

    @Override
    public Group getJFXShape() {
        Group group = super.getJFXShape();
        return group;
    }
}
