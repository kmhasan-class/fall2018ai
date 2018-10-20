package bd.edu.seu.fall2018ai.assignment1.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class Circle2DTest {

    @Test
    public void collidesWith() {
        Circle2D circle2D = new Circle2D(new Point2D(310.9294458109213, 198.9211799046742), 50.0);
        Rectangle2D rectangle2D = new Rectangle2D(new Point2D(400, 100), 400, 80);
        boolean expectedOutput = false;
        assertEquals(expectedOutput, circle2D.collidesWith(rectangle2D));
        assertEquals(expectedOutput, rectangle2D.collidesWith(circle2D));
    }
}