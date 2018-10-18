package bd.edu.seu.fall2018ai.assignment1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Circle extends Shape {
    private double radius;
    private Point2D center;

    @Override
    public boolean collidesWith(Shape shape) {

        if (shape instanceof Circle) {
            Circle that = (Circle) shape;
            double radiusSum = this.radius + that.radius;
            return this.center.getSquaredDistance(that.center) <= radiusSum * radiusSum;
        }

        if (shape instanceof Rectangle) {
            Circle circle = this;

            Rectangle rectangle = (Rectangle) shape;

            double closestX = clamp(
                    circle.center.getX(),
                    rectangle.getCenter().getX() - rectangle.getHeight() / 2,
                    rectangle.getCenter().getX() + rectangle.getHeight() / 2
            );

            double closestY = clamp(
                    circle.center.getY(),
                    rectangle.getCenter().getY() - rectangle.getWidth() / 2,
                    rectangle.getCenter().getY() + rectangle.getWidth() / 2
            );

            double distanceX = circle.center.getX() - closestX;
            double distanceY = circle.center.getY() - closestY;

            double distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);

            return distanceSquared < (circle.radius * circle.radius);
        }
        return false;
    }
}
