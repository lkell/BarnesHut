import animate.Animator;
import org.json.JSONException;
import simulate.BarnesHut;
import simulate.helpers.ConfigLoader;

import javax.management.InvalidAttributeValueException;
import javax.swing.*;
import java.io.IOException;

/**
 * This class demonstrates the functionality of of the Barnes Hut simulate by initializing the BarnesHut class using
 * a config file either located in the resources directory or passed in as a command line argument, and visualizes the
 * simulation using the Animator class.
 */
public class Demo {
    public static void main(String[] args) throws IOException, InvalidAttributeValueException, JSONException {

        // Define the simulate config file
        String configFile;
        if (args.length == 0) {
            configFile = "src\\main\\resources\\DefaultConfig.json";
        } else {
            configFile = args[0];
        }

        // Create the Simulator and Animator
        ConfigLoader loader = new ConfigLoader(configFile);
        BarnesHut simulator = new BarnesHut(loader);
        Animator animator = new Animator(simulator);

        // Set up the JFrame and run the animation
        JFrame f = new JFrame("Barnes Hut Simulator");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(animator);
        f.setSize(800, 800);
        f.setVisible(true);
        animator.animate();
    }
}
