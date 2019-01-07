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


}
