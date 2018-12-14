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
        Vector2D vec = new Vector2D(1, 10);
        Vector2D newVec = vec.multiply(.1);
        Assertions.assertEquals(.1, newVec.getX());
        Assertions.assertEquals(1, newVec.getY());
    }

    @Test
    public void testVectorMultiplication() {
        Vector2D vec1 = new Vector2D(1, 2);
        Vector2D vec2 = new Vector2D(2, 4);
        Assertions.assertEquals(10, vec1.dot(vec2));
    }
}
