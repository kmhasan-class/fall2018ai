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
    }

    @Override
    public Group getJFXShape() {
        Group group = super.getJFXShape();
        group.getChildren().stream().forEach(node -> ((Shape) node).setFill(new Color(0, 0.8, 0, 0.7)));
        group.getChildren().add(new Circle(super.getCenter().getX(), super.getCenter().getY(), super.getRadius() / 10));
        return group;
    }
}
