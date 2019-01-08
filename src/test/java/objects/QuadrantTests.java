package objects;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import simulate.objects.Particle;
import simulate.objects.Quadrant;
import utils.Vector2D;

import java.awt.*;

public class QuadrantTests {

    private Quadrant quadrant;

    @Before
    public void setUp() {
        quadrant = new Quadrant(new Vector2D(0, 0), 800);
    }

    @Test
    public void testContainsParticle() {
        Particle particle = new Particle(0, 0, 0, 0, 1, Color.RED, 1);
        for (int i = 0; i < 800; i = i + 10) {
            for (int j = 0; j < 800; j = j + 10) {
                particle.setPosition(new Vector2D(i, j));
                Assertions.assertTrue(quadrant.containsParticle(particle));
            }
        }
        for (int i = 0; i < 800; i = i + 10) {
            particle.setPosition(new Vector2D(i, -5));
            Assertions.assertFalse(quadrant.containsParticle(particle));
            particle.setPosition(new Vector2D(i, 805));
            Assertions.assertFalse(quadrant.containsParticle(particle));
        }
        for (int i = 0; i < 800; i = i + 10) {
            particle.setPosition(new Vector2D(-5, i));
            Assertions.assertFalse(quadrant.containsParticle(particle));
            particle.setPosition(new Vector2D(805, i));
            Assertions.assertFalse(quadrant.containsParticle(particle));
        }
    }

    @Test
    public void testDistanceToParticle() {
        double distanceToCenter = 0;
        Particle particle = new Particle(400, 400, 0, 0, 1, Color.RED, 1);
        Assertions.assertEquals(quadrant.distanceToParticle(particle), distanceToCenter);

        double distanceToCorner = 400 * Math.sqrt(2);
        int[] endPoints = new int[]{0, 800};
        for (int x: endPoints) {
            for (int y: endPoints) {
                particle.setPosition(new Vector2D(x, y));
                Assertions.assertEquals(quadrant.distanceToParticle(particle), distanceToCorner);
            }
        }

        double distanceToEdge = 400;
        particle.setPosition(new Vector2D(0, 400));
        Assertions.assertEquals(quadrant.distanceToParticle(particle), distanceToEdge);
        particle.setPosition(new Vector2D(400, 0));
        Assertions.assertEquals(quadrant.distanceToParticle(particle), distanceToEdge);
        particle.setPosition(new Vector2D(400, 800));
        Assertions.assertEquals(quadrant.distanceToParticle(particle), distanceToEdge);
        particle.setPosition(new Vector2D(800, 400));
        Assertions.assertEquals(quadrant.distanceToParticle(particle), distanceToEdge);
    }

    @Test
    public void testUpperLeft() {
        Quadrant upperLeft = quadrant.upperLeft();
        Particle particle = new Particle(200, 200, 0, 0, 1, Color.RED, 1);
        Assertions.assertEquals(upperLeft.distanceToParticle(particle), 0);
    }

    @Test
    public void testUpperRight() {
        Quadrant upperRight = quadrant.upperLeft();
        Particle particle = new Particle(600, 200, 0, 0, 1, Color.RED, 1);
        Assertions.assertEquals(upperRight.distanceToParticle(particle), 0);
    }

    @Test
    public void lowerLeft() {
        Quadrant lowerLeft = quadrant.upperLeft();
        Particle particle = new Particle(400, 200, 0, 0, 1, Color.RED, 1);
        Assertions.assertEquals(lowerLeft.distanceToParticle(particle), 0);
    }

    @Test
    public void lowerRight() {
        Quadrant lowerRight = quadrant.upperLeft();
        Particle particle = new Particle(400, 400, 0, 0, 1, Color.RED, 1);
        Assertions.assertEquals(lowerRight.distanceToParticle(particle), 0);
    }
}
