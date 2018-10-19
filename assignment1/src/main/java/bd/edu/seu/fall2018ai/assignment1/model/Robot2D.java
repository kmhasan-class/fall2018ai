package bd.edu.seu.fall2018ai.assignment1.model;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import lombok.Data;

@Data
public class Robot2D extends Circle2D {
    // angle in radian
    private double orientation;

    public Robot2D(Point2D center, double radius) {
        this(center, radius, 0.0);
    }

    public Robot2D(Point2D center, double radius, double orientation) {
        super(center, radius);
        this.orientation = orientation;
    }

    @Override
    public Group getJFXShape() {
        Group group = super.getJFXShape();
        group.getChildren().stream().forEach(node -> ((Shape) node).setFill(new Color(0, 0.6, 0, 0.7)));
        double innerRadius = 0.75 * super.getRadius();
        double outerRadius = 1.0 * super.getRadius();
        Polygon headingHand = new Polygon();
        headingHand.getPoints().addAll(new Double[]{
                super.getCenter().getX(), super.getCenter().getY(),
                innerRadius * Math.cos(orientation - Math.PI / 8) + super.getCenter().getX(), innerRadius * Math.sin(orientation - Math.PI / 8) + super.getCenter().getY(),
                outerRadius * Math.cos(orientation) + super.getCenter().getX(), outerRadius * Math.sin(orientation) + super.getCenter().getY(),
                innerRadius * Math.cos(orientation + Math.PI / 8) + super.getCenter().getX(), innerRadius * Math.sin(orientation + Math.PI / 8) + super.getCenter().getY()
        });
        headingHand.setFill(new Color(0, 0.2, 0.0, 0.8));
        group.getChildren().add(new Circle(super.getCenter().getX(), super.getCenter().getY(), super.getRadius() / 10));
        group.getChildren().add(headingHand);
        return group;
    }

    public void applyAction(Action action) {
        switch (action) {
            case MOVE_FORWARD:
                double dx = 1 * Math.cos(orientation);
                double dy = 1 * Math.sin(orientation);
                setCenter(new Point2D(dx + getCenter().getX(), dy + getCenter().getY()));
                break;
            case ROTATE_CCW:
                orientation += Math.toRadians(1);
                break;
            case ROTATE_CW:
                orientation -= Math.toRadians(1);
                break;
        }
    }
}
