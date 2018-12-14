package simulator.objects;

import org.jetbrains.annotations.NotNull;
import utils.Vector2D;

import java.util.Stack;

/**
 * Node in the Barnes Hut Tree. Contains information on the simulated region as well as the particles
 * contained in the node. Contains references to up to four children nodes.
 */
public class Node {
    private Quadrant quadrant;
    private double G;
    private Node upperLeft;
    private Node upperRight;
    private Node lowerLeft;
    private Node lowerRight;
    private Particle particle;
    private ParticleAggregate particleAggregate; // Aggregate of all particles contained by the node.
    private boolean hasChildren = false; // Initially nodes do not contain children

    public Node(Quadrant quadrant, double G) {
        this.quadrant = quadrant;
        this.G = G;
    }


    /**
     * Used to add the particle to the current node. If the the node already contains a particle or
     * has any subnodes, the the newParticle and potentially existing particle get reinserted
     * into the appropriate subnodes.
     * @param newParticle Particle added to the node.
     */
    public void insertParticle(Particle newParticle) {
        updateParticleAggregate(newParticle);
        if (hasChildren)
            reInsertParticle(newParticle);
        else {
            if (particle == null) {
                particle = newParticle;
            } else {
                reInsertParticle(newParticle);
                reInsertParticle(particle);
                particle = null;
                hasChildren = true;
            }
        }
    }

    /**
     * Adds the inserted particle into the particleAggregate.
     * @param newParticle Particle inserted into the node.
     */
    private void updateParticleAggregate(Particle newParticle) {
        if (particleAggregate == null) {
            particleAggregate = new ParticleAggregate(newParticle);
        } else {
            particleAggregate.addParticle(newParticle);
        }
    }

    /**
     * Recursively inserts the given particle into the appropriate subnode.
     * Creates the appropriate subnode if it does not already exist.
     * @param particle Particle inserted to quad tree
     */

    private void reInsertParticle(Particle particle) {
        if (quadrant.lowerLeft().containsParticle(particle)) {
            if (lowerLeft == null) lowerLeft = new Node(quadrant.lowerLeft(), G);
            lowerLeft.insertParticle(particle);
        } else if (quadrant.lowerRight().containsParticle(particle)) {
            if (lowerRight == null) lowerRight = new Node(quadrant.lowerRight(), G);
            lowerRight.insertParticle(particle);
        } else if (quadrant.upperLeft().containsParticle(particle)) {
            if (upperLeft == null) upperLeft = new Node(quadrant.upperLeft(), G);
            upperLeft.insertParticle(particle);
        } else if (quadrant.upperRight().containsParticle(particle)) {
            if (upperRight == null) upperRight = new Node(quadrant.upperRight(), G);
            upperRight.insertParticle(particle);
        }
    }

    /**
     * Returns force exerted on a particle due to the aggregate of particles in the given node.
     * @param particle the particle on which the gravitational force from this node is to be computed
     * @param theta monopole acceptance criterion
     * @return Vector2D representation of the exerted force
     */
    public Vector2D getForce(Particle particle, double theta) {
        if (!hasChildren) {
            if (this.particle == particle)
                return Vector2D.NullVector();
            return particleAggregate.calcForce(particle, G);
        }
        if (quadrant.getHeight() / quadrant.distanceToParticle(particle) < theta)
            return particleAggregate.calcForce(particle, G);
        return  getForceFromSubNodes(particle, G, theta);
    }

    /**
     * Recusively adds up the forces from all the subnodes.
     * @param particle the particle on which the gravitational force from this node is to be computed
     * @param G the gravitational constant
     * @param theta monopole acceptance criterion
     * @return Vector2D representation of the resulting force from all the subnodes
     */
    private Vector2D getForceFromSubNodes(Particle particle, double G, double theta) {
        Vector2D force = Vector2D.NullVector();
        if (lowerLeft != null)
            force = force.add(lowerLeft.getForce(particle, theta));
        if (lowerRight != null)
            force = force.add(lowerRight.getForce(particle, theta));
        if (upperLeft != null)
            force = force.add(upperLeft.getForce(particle, theta));
        if (upperRight != null)
            force = force.add(upperRight.getForce(particle, theta));
        return force;
    }

    /**
     * Pushes the quadrant and the quadrant of all subnodes onto the stack
     * @param quadrants Stack of quadrants
     */
    public void addQuadrantsToStack(Stack<Quadrant> quadrants) {
        quadrants.push(quadrant);
        if (upperLeft != null) {
            upperLeft.addQuadrantsToStack(quadrants);
        }
        if (upperRight != null) {
            upperRight.addQuadrantsToStack(quadrants);
        }
        if (lowerLeft != null) {
            lowerLeft.addQuadrantsToStack(quadrants);
        }
        if (lowerRight != null) {
            lowerRight.addQuadrantsToStack(quadrants);
        }
    }

    /**
     * Represents the aggregation of all the particles contained in the tree starting at the current node.
     * Contains information on the mass and center of mass of the particles.
     */
    private static class ParticleAggregate {

        private double mass;
        private Vector2D centerOfMass;

        ParticleAggregate(@NotNull Particle particle) {
            mass = particle.getMass();
            centerOfMass = particle.getPosition();
        }

        private void addParticle(@NotNull Particle particle) {
            Vector2D centerOfMassNum = centerOfMass.multiply(mass).add(particle.getPosition().multiply(particle.getMass()));
            mass += particle.getMass();
            centerOfMass = centerOfMassNum.divide(mass);
        }

        private Vector2D calcForce(@NotNull Particle particle, double G) {
            Vector2D position = particle.getPosition();
            double distance = position.distanceTo(centerOfMass);
            double product = (-G * mass * particle.getMass()) / (distance * distance * distance);
            return position.subtract(centerOfMass).multiply(product);
        }
    }
}
