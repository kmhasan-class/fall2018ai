package bd.edu.seu.fall2018ai.assignment1.model;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(of = {"center", "radius"})
public class Circle2D extends Shape2D {
    protected Point2D center;
    protected double radius;

    private final Circle circle;
    private final Group group;
    private final Circle centerCircle;

    private boolean centerDrawable;



    public Circle2D(Point2D center, double radius) {
        this.center = center;
        this.radius = radius;
        centerDrawable = false;
        circle = new Circle(center.getX(), center.getY(), radius, new Color(0.8, 0, 0, 0.7));
        group = new Group();
        centerCircle = new Circle(center.getX(), center.getY(), radius / 10);
        group.getChildren().add(circle);
    }

    public void setFill(Paint paint) {
        circle.setFill(paint);
    }

    @Override
    public boolean collidesWith(Shape2D shape) {

        if (shape instanceof Circle2D) {
            Circle2D that = (Circle2D) shape;
            double radiusSum = this.radius + that.radius;
            return this.center.getSquaredDistance(that.center) <= radiusSum * radiusSum;
        }

        if (shape instanceof Rectangle2D) {
            Circle2D circle2D = this;

            Rectangle2D rectangle2D = (Rectangle2D) shape;

            double closestX = clamp(
                    circle2D.center.getX(),
                    rectangle2D.getCenter().getX() - rectangle2D.getWidth() / 2,
                    rectangle2D.getCenter().getX() + rectangle2D.getWidth() / 2
            );

            double closestY = clamp(
                    circle2D.center.getY(),
                    rectangle2D.getCenter().getY() - rectangle2D.getHeight() / 2,
                    rectangle2D.getCenter().getY() + rectangle2D.getHeight() / 2
            );

            double distanceX = circle2D.center.getX() - closestX;
            double distanceY = circle2D.center.getY() - closestY;

            double distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);

            return distanceSquared < (circle2D.radius * circle2D.radius);
        }
        return false;
    }

    @Override
    public Group getJFXShape() {
        circle.setCenterX(center.getX());
        circle.setCenterY(center.getY());
        centerCircle.setCenterX(center.getX());
        centerCircle.setCenterY(center.getY());
        if (centerDrawable && !group.getChildren().contains(centerCircle))
            group.getChildren().add(centerCircle);
        return group;
    }
}
