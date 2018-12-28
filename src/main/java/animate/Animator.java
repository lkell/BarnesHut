package animate;

import org.jetbrains.annotations.NotNull;
import simulate.BarnesHut;
import simulate.objects.Particle;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * JPanel subclass that can be used to animate all the bodies in a Barnes Hut simulation.
 */
public class Animator extends BarnesHutAnimator {

    protected BarnesHut simulator;
    private ArrayList<Particle> particles;


    /**
     * Initializes the the Animator.
     *
     * @param simulator Barnes Hut simulate instance.
     */
    public Animator(@NotNull BarnesHut simulator) {
        this.simulator = simulator;
        this.particles = simulator.getParticles();
        setBackground(Color.BLACK);
    }


    /**
     * Used to repaint the panel for each frame of the animation.
     *
     * @param g The Graphics object.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        paintParticles(g2);
    }

    /**
     * Used to paint all of the bodies in the simulate.
     *
     * @param g2 The Graphics2D object.
     */
    private void paintParticles(Graphics2D g2) {
        for (Particle object : particles) {
            g2.setColor(object.getColor());
            Shape shape = object.getShape();
            g2.draw(shape);
            g2.fill(shape);
        }
    }

    /**
     * Call the simulate's next() method so that that all particles obtain
     * new positions and velocities for the next frame.
     */
    public void advanceFrame() {
        simulator.next();
    }
}
