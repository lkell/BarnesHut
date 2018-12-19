package animate;

import simulator.BarnesHut;
import simulator.objects.Quadrant;

import java.awt.*;
import java.util.Stack;

/**
 * Animator subclass that can be used to animate all of the particles and node quadrants in a Barnes Hut simulations.
 */
public class QuadrantAnimator extends Animator {

    private Stack<Quadrant> quadrants;

    /**
     * Initializes the animate.
     *
     * @param simulator Barnes Hut simulator instance.
     */
    public QuadrantAnimator(BarnesHut simulator) {
        super(simulator);
    }

    /**
     * Used to repaint the panel for each frame of the animation.
     *
     * @param g The Graphics object.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintQuadrants((Graphics2D) g);
    }

    /**
     * Used to paint all of the node quadrants in the simulator.
     *
     * @param g2 Graphics2D object.
     */
    private void paintQuadrants(Graphics2D g2) {
        g2.setColor(Color.BLUE);
        for (Quadrant quadrant : quadrants) {
            g2.draw(quadrant);
        }
    }

    /**
     * Animates the simulation in real-time by continuously advancing the simulator
     * one step and painting all of the particles.
     */
    @Override
    public void advanceFrame() {
        simulator.next();
        this.quadrants = simulator.getQuadrants();
    }

}
