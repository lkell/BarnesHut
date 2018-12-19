import animate.QuadrantAnimator;
import org.json.JSONException;
import simulator.BarnesHut;
import simulator.helpers.ConfigLoader;

import javax.management.InvalidAttributeValueException;
import javax.swing.*;
import java.io.IOException;

public class Demo {
    public static void main(String[] args) throws IOException, InvalidAttributeValueException, JSONException {

        String configFile;
        if (args.length == 0) {
            configFile = "src\\main\\resources\\DefaultConfig.json";
        } else {
            configFile = args[0];
        }

        ConfigLoader loader = new ConfigLoader(configFile);
        BarnesHut simulator = new BarnesHut(loader);
        QuadrantAnimator animator = new QuadrantAnimator(simulator);

        JFrame f = new JFrame("Barnes Hut Simulator");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(animator);
        f.setSize(800, 800);
        f.setVisible(true);

        animator.animate();
    }
}
