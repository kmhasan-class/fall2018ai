package bd.edu.seu.fall2018ai.assignment1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CircleShape extends Shape {
    private double radius;
    private Point2D center;

    @Override
    public boolean collidesWith(Shape shape) {

        if (shape instanceof CircleShape) {
            CircleShape that = (CircleShape) shape;
            double radiusSum = this.radius + that.radius;
            return this.center.getSquaredDistance(that.center) <= radiusSum * radiusSum;
        }

        if (shape instanceof RectangleShape) {
            CircleShape circleShape = this;

            RectangleShape rectangleShape = (RectangleShape) shape;

            double closestX = clamp(
                    circleShape.center.getX(),
                    rectangleShape.getCenter().getX() - rectangleShape.getHeight() / 2,
                    rectangleShape.getCenter().getX() + rectangleShape.getHeight() / 2
            );

            double closestY = clamp(
                    circleShape.center.getY(),
                    rectangleShape.getCenter().getY() - rectangleShape.getWidth() / 2,
                    rectangleShape.getCenter().getY() + rectangleShape.getWidth() / 2
            );

            double distanceX = circleShape.center.getX() - closestX;
            double distanceY = circleShape.center.getY() - closestY;

            double distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);

            return distanceSquared < (circleShape.radius * circleShape.radius);
        }
        return false;
    }
}
