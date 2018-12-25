package animate;

import com.google.common.io.Files;
import com.opencsv.CSVReader;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

/**
 * Animates an exported Barnes Hut simulation specified by given simulation directory. This directory must contain
 * valid ParticleProperties.json and Trajectories.csv files.
 */
public class ExportAnimator extends JPanel {

    private String simDirectory;
    private CSVReader reader;
    private Particle[] particles;

    /**
     * Initializes the animator using the given simulation output directory.
     * @param simDir The Barnes Hut simulation export directory.
     * @throws FileNotFoundException If ParticleProperties.json and Trajectories.csv files not found in simDir.
     */
    public ExportAnimator(String simDir) throws FileNotFoundException {
        this.simDirectory = simDir;
        createParticles();
        String trajectoriesFile = Paths.get(simDir, "Trajectories.csv").toString();
        reader = new CSVReader(new FileReader(trajectoriesFile));
        setBackground(Color.BLACK);
    }

    /**
     * Creates visual particle representations used by the animate method.
     */
    private void createParticles() {
        String propertiesFilePath = Paths.get(simDirectory, "ParticleProperties.json").toString();
        try {
            String propertiesText = Files.asCharSource(new File(propertiesFilePath), StandardCharsets.UTF_8).read();
            JSONObject properties = new JSONObject(propertiesText);
            int length = properties.length();
            particles = new Particle[properties.length()];
            for (int i = 0; i < properties.length(); i++) {
                String key = String.valueOf(i);
                int size = properties.getJSONObject(key).getInt("Size");
                int color = properties.getJSONObject(key).getInt("Color");
                particles[i] = new Particle(size, color);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void animate(double frameRate) {}

    /**
     * Steps through each state of the simulation and animates the particles.
     * @throws IOException
     */
    public void animate() throws IOException {
        reader.readNext(); // Skips the header line
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            for (int i = 0; i < nextLine.length / 2; i++) {
                particles[i].setPosition(Double.valueOf(nextLine[i*2]), Double.valueOf(nextLine[(2*i)+1]));
            }
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        paintParticles(g2);
    }

    private void paintParticles(Graphics2D g2) {
        g2.setColor(Color.GREEN);
        for (Particle particle: particles) {
            g2.setColor(particle.color);
            g2.draw(particle.getShape());
            g2.fill(particle.getShape());
        }
    }

    /**
     * Contains all elements required to create a basic visualizations of a simulated particle.
     */
    private class Particle {
        private int size;
        private Color color;
        private double x;
        private double y;

        private Particle(int size, int color) {
            this.size = size;
            this.color = new Color(color);
        }

        private void setPosition(double x, double y) {
            this.x = x;
            this.y = y;
        }

        private Ellipse2D.Double getShape() {
            return new Ellipse2D.Double(x-2, y-2, size, size);
        }
    }

    public static void main(String[] args) throws IOException {
        // Define the simulate config file
        String simDir;
        if (args.length == 0) {
            simDir = "output\\SimOutput1";
        } else {
            simDir = args[0];
        }

        // Create the Simulator and Animator
        ExportAnimator animator = new ExportAnimator(simDir);

        // Set up the JFrame and run the animation
        JFrame f = new JFrame("Barnes Hut Simulator");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(animator);
        f.setSize(800, 800);
        f.setVisible(true);
        animator.animate();
    }
}
