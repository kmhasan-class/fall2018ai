package bd.edu.seu.fall2018ai.assignment1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class Point2D {
    private double x;
    private double y;

    public double getSquaredDistance(Point2D that) {
        double dx = this.x - that.x;
        double dy = this.y - that.y;
        return dx * dx + dy * dy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point2D that = (Point2D) o;
        return Math.abs(this.x - that.x) < Constants.EPS &&
                Math.abs(this.y - that.y) < Constants.EPS;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
