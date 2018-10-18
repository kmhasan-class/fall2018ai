package bd.edu.seu.fall2018ai.assignment1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Rectangle extends Shape {
    private Point2D center;
    private double width;
    private double height;

    @Override
    public boolean collidesWith(Shape shape) {
        if (shape instanceof Circle) {
            Rectangle rectangle = this;
            Circle circle = (Circle) shape;

            double closestX = clamp(
                    circle.getCenter().getX(),
                    rectangle.getCenter().getX() - rectangle.getHeight() / 2,
                    rectangle.getCenter().getX() + rectangle.getHeight() / 2
            );

            double closestY = clamp(
                    circle.getCenter().getY(),
                    rectangle.getCenter().getY() - rectangle.getWidth() / 2,
                    rectangle.getCenter().getY() + rectangle.getWidth() / 2
            );

            double distanceX = circle.getCenter().getX() - closestX;
            double distanceY = circle.getCenter().getY() - closestY;

            double distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);

            return distanceSquared < (circle.getRadius() * circle.getRadius());
        }
        return false;
    }
}
