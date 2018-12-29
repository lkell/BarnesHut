package simulate.objects;

import utils.Vector2D;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Point particle representation of a body or system of bodies in motion.
 */
public class Particle {

    private final double mass;
    private Vector2D position;
    private Vector2D velocity;
    private Color color;
    private int size;
    private int bounds;

    public Particle(Vector2D position, Vector2D velocity, double mass, Color color, int size) {
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
        this.color = color;
        this.size = size;
        this.bounds = Math.floorDiv(size, 2);
    }

    public Particle(double x, double y, double vX, double vY, double mass, Color color, int size) {
        this.position = new Vector2D(x, y);
        this.velocity = new Vector2D(vX, vY);
        this.mass = mass;
        this.color = color;
        this.size = size;
        this.bounds = Math.floorDiv(size, 2);
    }

    /**
     * Returns the particle's shape
     * @return Shape as Ellipse2D.Double
     */
    public Ellipse2D.Double getShape() {
        return new Ellipse2D.Double(position.getX() - bounds, position.getY() - bounds, size, size);
    }

    /**
     * Returns the particle's size. The size has no impact on the simulation, but it can be useful when
     * visualizing the simulation.
     * @return Particle's size.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Returns the particle's color
     * @return Color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Implements the euler-chromer step to update the position and velocity by one time frame.
     * @param time_step Time step
     * @param root Root of the Barnes-Hut tree
     * @param theta Monopole Acceptance Criterion
     */
    public void eulerStep(double time_step, Node root, double theta) {
        Vector2D acceleration = root.getForce(this, theta).divide(mass);
        velocity = velocity.add(acceleration.multiply(time_step));
        position = position.add(velocity.multiply(time_step));
    }

    /**
     * Returns the particles mass
     * @return Mass
     */
    public double getMass() {
        return mass;
    }

    /**
     * Returns the particles' position
     * @return Position
     */
    public Vector2D getPosition() {
        return position;
    }

    /**
     * Sets the particle's position
     * @param position Position
     */
    public void setPosition(Vector2D position) {
        this.position = position;
    }

    /**
     * Returns the particle's velocity
     * @return Velocity
     */
    public Vector2D getVelocity() {
        return velocity;
    }

    /**
     * Set's the particle's velcoity
     * @param velocity Velocity
     */
    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }
}
