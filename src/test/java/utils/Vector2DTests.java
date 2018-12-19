package utils;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class Vector2DTests {


    @Test
    public void getX() {
        Vector2D vec = new Vector2D(1, 2);
        Assertions.assertEquals(1, vec.getX());
    }

    @Test
    public void getY() {
        Vector2D vec = new Vector2D(1, 2);
        Assertions.assertEquals(2, vec.getY());
    }

    @Test
    public void testScalarMultiplication() {
        Assertions.assertEquals(new Vector2D(1, 10).multiply(.1), new Vector2D(.1, 1));
    }

    @Test
    public void testVectorMultiplication() {
        Vector2D vec1 = new Vector2D(1, 2);
        Vector2D vec2 = new Vector2D(2, 3);
        Assertions.assertEquals(10, vec1.dot(vec2));
    }

    @Test
    public void testEquals() {
        Assertions.assertEquals(new Vector2D(1, 1), new Vector2D(1, 1));
    }

    @Test
    public void testScalarDivision() {
        Assertions.assertEquals(new Vector2D(10, 20).divide(5), new Vector2D(2, 4));
    }

    @Test
    public void testNorm() {
        Assertions.assertEquals(new Vector2D(3, 4).norm(), 5);
    }

    @Test
    public void testMidpoint() {
        Assertions.assertEquals(new Vector2D(0, 0).midpoint(new Vector2D(10, 10)), new Vector2D(5, 5));
    }
}
