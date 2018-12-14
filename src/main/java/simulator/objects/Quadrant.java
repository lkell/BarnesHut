package simulator.objects;

import utils.Vector2D;

import java.awt.geom.Rectangle2D;

/**
 * Represents the two-dimensional space contained by the node of a Barnes Hut Tree.
 */
public class Quadrant extends Rectangle2D.Double {

    private Vector2D origin;
    private double length;
    private Vector2D center;
    private Quadrant upperLeft;
    private Quadrant upperRight;
    private Quadrant lowerLeft;
    private Quadrant lowerRight;

    public Quadrant(Vector2D upperLeft, double length) {
        super(upperLeft.getX(), upperLeft.getY(), length, length);
        this.origin = upperLeft;
        this.length = length;
        this.center = new Vector2D(getCenterX(), getCenterY());
    }

    /**
     * Returns boolean depending on whether this quadrant contains the given particle.
     * @param particle given particle
     * @return true if particle is contained, otherwise false
     */
    public boolean containsParticle(Particle particle) {
        return contains(particle.getPosition().getX(), particle.getPosition().getY());
    }

    /**
     * Returns the magnitude of the distance from the center of the quadrant to  the given particle.
     * @param particle given particle
     * @return distance to particle
     */
    public double distanceToParticle(Particle particle) {
        return center.distanceTo(particle.getPosition());
    }

    /**
     * Returns the upper left sub-quadrant of this quadrant.
     * If this is the first time this method is called, the reference to the upper left sub-quadrant is cached
     * so that this method can be implemented faster next time.
     * @return upperLeft quadrant.
     */
    public Quadrant upperLeft() {
        if (upperLeft == null) {
            upperLeft = new Quadrant(origin, length/2);
        }
        return upperLeft;
    }

    /**
     * Returns the upper right sub-quadrant of this quadrant.
     * If this is the first time this method is called, the reference to the upper right sub-quadrant is cached
     * so that this method can be implemented faster next time.
     * @return upperRight quadrant.
     */
    public Quadrant upperRight() {
        if (upperRight == null) {
            upperRight = new Quadrant(origin.add(new Vector2D(length/2, 0)), length/2);
        }
        return upperRight;
    }

    /**
     * Returns the lower left sub-quadrant of this quadrant.
     * If this is the first time this method is called, the reference to the lower left sub-quadrant is cached
     * so that this method can be implemented faster next time.
     * @return lowerLeft quadrant.
     */
    public Quadrant lowerLeft() {
        if (lowerLeft == null) {
            lowerLeft = new Quadrant(origin.add(new Vector2D(0, length/2)), length/2);
        }
        return lowerLeft;
    }

    /**
     * Returns the lower right sub-quadrant of this quadrant.
     * If this is the first time this method is called, the reference to the lower right sub-quadrant is cached
     * so that this method can be implemented faster next time.
     * @return lowerRight quadrant.
     */
    public Quadrant lowerRight() {
        if (lowerRight == null) {
            lowerRight = new Quadrant(origin.add(new Vector2D(length/2, length/2)), length/2);
        }
        return lowerRight;
    }
}
