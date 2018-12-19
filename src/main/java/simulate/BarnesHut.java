package simulate;

import org.jetbrains.annotations.NotNull;
import simulate.helpers.ConfigLoader;
import simulate.helpers.Galaxy;
import simulate.helpers.GalaxyBuilder;
import simulate.objects.Node;
import simulate.objects.Particle;
import simulate.objects.Quadrant;
import utils.Vector2D;

import javax.management.InvalidAttributeValueException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * The purpose of this class is to simulate the motion of the a collection of bodies due to the gravitational force in two dimensions.
 * The simulate treats each body as a point particle and implements the Barnes-Hut algorithm, which can be
 * read about <a href="https://en.wikipedia.org/wiki/Barnes%E2%80%93Hut_simulation">here</a>.
 * This class implements the Simulator interface; once initialized, the next() method can be continuously called in
 * order to update the simulation one time-step at a time. Doing so update the the positions and velocities of the simulate's particles.
 */
public class BarnesHut {

    private final double G;
    private final double theta;
    private final double timeStep;
    private final Quadrant quadrant;
    private Node root;
    private ArrayList<Particle> particles = new ArrayList<>();

    public BarnesHut(double G, double theta, double timeStep, Quadrant quadrant,
                     Iterable<Galaxy> galaxies) throws InvalidAttributeValueException {
        this.quadrant = quadrant;
        this.G = G;
        this.theta = theta;
        this.timeStep = timeStep;
        this.setUpGalaxies(galaxies);
    }

    public BarnesHut(ConfigLoader loader) throws InvalidAttributeValueException {
        this.G = loader.getG();
        this.theta = loader.getTheta();
        this.timeStep = loader.getTimeStep();
        int[] origin = loader.getOrigin();
        this.quadrant = new Quadrant(new Vector2D(origin[0], origin[1]), loader.getSize());
        System.out.println(this.quadrant.lowerLeft());
        this.setUpGalaxies(loader.getGalaxies());
    }

    /**
     * Advances the simulation forward by one time-step. Updates the positions and velocities of the
     * simulate's particles. These quantities are computed in parallel.
     */
    public void next() {
        buildTree();
        particles.parallelStream().forEach(particle -> particle.eulerStep(timeStep, root, theta));
    }

    /**
     * Getter method for the simulate's particles.
     *
     * @return the simulated particles
     */
    public ArrayList<Particle> getParticles() {
        return particles;
    }

    /**
     * Returns a stack of all the quadrants that make up the Barnes Hut tree.
     *
     * @return Stack of all Quadrants in the tree.
     */
    public Stack<Quadrant> getQuadrants() {
        Stack<Quadrant> quadrants = new Stack<>();
        root.addQuadrantsToStack(quadrants);
        return quadrants;
    }

    /**
     * Rebuilds the quad tree. Initializes the root node using the simulate's quadrant and recursively inserts
     * all of the particles into the tree.
     */
    private void buildTree() {
        root = new Node(quadrant, G);
        for (Particle particle : particles) {
            root.insertParticle(particle);
        }
    }

    /**
     * Sets up the galaxies in the simulation by creating point-particle representations of all the bodies.
     *
     * @param galaxies galaxy objects as defined in the ConfigLoader
     */
    private void setUpGalaxies(@NotNull Iterable<Galaxy> galaxies) throws InvalidAttributeValueException {
        GalaxyBuilder builder = new GalaxyBuilder(G);
        for (Galaxy galaxy : galaxies) {
            particles.addAll(builder.buildGalaxy(galaxy));
        }
    }
}
