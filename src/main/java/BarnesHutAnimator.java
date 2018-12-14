import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import simulator.BarnesHut;
import simulator.helpers.ConfigLoader;
import simulator.objects.Particle;

import javax.management.InvalidAttributeValueException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Leo Kell leokello@gmail.com
 */
public class BarnesHutAnimator extends JPanel {

    private BarnesHut simulator;
    private ArrayList<Particle> particles;


    /**
     * Initializes the simulator object and stores the graphical objects used by the simulator.
     *
     * @param simulator Object of class implementing the Simulator interface.
     */
    public BarnesHutAnimator(@NotNull BarnesHut simulator) {
        this.simulator = simulator;
        this.particles = simulator.getParticles();
        Graphics2D g2 = (Graphics2D) getGraphics();
    }


    /**
     * Used to redraw the panel for each iteration of the animation.
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
     * Used to render all the graphical objects returned from the simulator.
     * Helper method for {@link package.class#paintComponent(Graphics)}  label }
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

    public static void main(String[] args) throws IOException, InvalidAttributeValueException, JSONException {

        String configFile;
        if (args.length == 0) {
            configFile = "src\\main\\resources\\DefaultConfig.json";
        } else {
            configFile = args[0];
        }

        ConfigLoader loader = new ConfigLoader(configFile);
        BarnesHut simulator = new BarnesHut(loader);
        BarnesHutAnimator animator = new BarnesHutAnimator(simulator);

        JFrame f = new JFrame("Barnes Hut Simulator");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(animator);
        f.setSize(800, 800);
        f.setVisible(true);

        Long start = System.currentTimeMillis();
        int i = 0;
        while (true) {
            animator.advanceFrame();
            animator.repaint();
            if (i == 100) System.out.println(System.currentTimeMillis() - start);
            i++;
        }
    }
}
