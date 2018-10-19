package bd.edu.seu.fall2018ai.assignment1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RectangleShape extends Shape {
    private Point2D center;
    private double width;
    private double height;

    @Override
    public boolean collidesWith(Shape shape) {
        if (shape instanceof CircleShape) {
            RectangleShape rectangleShape = this;
            CircleShape circleShape = (CircleShape) shape;

            double closestX = clamp(
                    circleShape.getCenter().getX(),
                    rectangleShape.getCenter().getX() - rectangleShape.getHeight() / 2,
                    rectangleShape.getCenter().getX() + rectangleShape.getHeight() / 2
            );

            double closestY = clamp(
                    circleShape.getCenter().getY(),
                    rectangleShape.getCenter().getY() - rectangleShape.getWidth() / 2,
                    rectangleShape.getCenter().getY() + rectangleShape.getWidth() / 2
            );

            double distanceX = circleShape.getCenter().getX() - closestX;
            double distanceY = circleShape.getCenter().getY() - closestY;

            double distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);

            return distanceSquared < (circleShape.getRadius() * circleShape.getRadius());
        }
        return false;
    }
}
