package animate;

import org.jetbrains.annotations.NotNull;
import simulator.BarnesHut;
import simulator.objects.Particle;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * JPanel subclass that can be used to animate all the bodies in a Barnes Hut simulation.
 */
public class Animator extends JPanel {

    protected BarnesHut simulator;
    private ArrayList<Particle> particles;


    /**
     * Initializes the the Animator.
     *
     * @param simulator Barnes Hut simulator instance.
     */
    public Animator(@NotNull BarnesHut simulator) {
        this.simulator = simulator;
        this.particles = simulator.getParticles();
    }


    /**
     * Used to repaint the panel for each frame of the animation.
     *
     * @param g The Graphics object.
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paintComponent(g2);
        setBackground(Color.BLACK);
        paintParticles(g2);
    }

    /**
     * Used to paint all of the bodies in the simulator.
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
     * Call the simulator's next() method so that that all particles obtain
     * new positions and velocities for the next frame.
     */
    public void advanceFrame() {
        simulator.next();
    }

    /**
     * Animates the simulation in real-time by continuously advancing the simulator
     * one step and painting all of the particles.
     */
    public void animate() {
        while (true) {
            advanceFrame();
            repaint();
        }
    }

    /**
     * Animates the simulation for the number of steps specified. Advances the simulation in each frame of the
     * animation.
     *
     * @param steps The number of frames and simulation steps in the animation.
     */
    public void animate(int steps) {
        Long start = System.currentTimeMillis();
        for (int i = 0; i < steps; i++) {
            advanceFrame();
            repaint();
            i++;
        }
    }
}
