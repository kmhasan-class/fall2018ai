package bd.edu.seu.fall2018ai.assignment1.model;

public abstract class Shape {
    public abstract boolean collidesWith(Shape that);

    public double clamp(double value, double min, double max) {
        if (value < min)
            value = min;
        if (value > max)
            value = max;
        return value;
    }
}
