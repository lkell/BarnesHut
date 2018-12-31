package export;

import com.opencsv.CSVWriter;
import me.tongfei.progressbar.ProgressBar;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import simulate.BarnesHut;
import simulate.helpers.ConfigLoader;
import simulate.objects.Particle;

import javax.management.InvalidAttributeValueException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Exports the particle properties and trajectories of a BarnesHut simulation into the specified output directory.
 * Particle properties are exported into a ParticleProperties.json file, and trajectories are exported into a
 * Trajectories.csv file. Since the the whole Trajectories output file is written line-by-line rather than all at once,
 * this class can be used to export "large" simulations with many steps.
 */
public class LargeExporter {

    private BarnesHut simulator;
    private ArrayList<Particle> particles;

    public LargeExporter(@NotNull BarnesHut simulator) {
        this.simulator = simulator;
        this.particles = simulator.getParticles();
    }

    /**
     * Runs the simulation of the given simulator and exports the particle properties and trajectory files to the
     * specified directory path.
     * @param simulator A BarnesHut simulator
     * @param outputDir The simulator export directory
     * @param steps Number of steps run in the simulation
     */
    public static void exportSimulation(BarnesHut simulator, String outputDir, int steps) {
        LargeExporter exporter = new LargeExporter(simulator);
        exporter.exportSimulation(outputDir, steps);
    }

    /**
     * Runs the simulation and exports the particle property and trajectory files to the specified directory path.
     */
    public void exportSimulation(String outputDir, int steps) {
        createOutDir(outputDir);
        exportParticleProperties(outputDir);
        exportParticleTrajectories(outputDir, steps);
    }

    /**
     * Exports a JSON file containing particle property information, including size and color.
     */
    private void exportParticleProperties(String outputDir) {
        System.out.println("Exporting particle properties");
        JSONObject properties = getParticleProperties();
        Path propertiesPath = Paths.get(outputDir, "ParticleProperties.json");
        try (BufferedWriter writer = Files.newBufferedWriter(propertiesPath, Charset.forName("UTF-8"))) {
            writer.write(properties.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Exports the particle trajectories as a CSV file. Every two columns, in order, represent the x- and y-components
     * respectively of a single particle.
     * @param outputDir The simulator export directory.
     * @param steps Number of steps used to create the output file.
     */
    private void exportParticleTrajectories(String outputDir, int steps) {
        Path trajectoriesPath = Paths.get(outputDir, "Trajectories.csv");
        File trajectories = new File(trajectoriesPath.toString());
        try (ProgressBar simulatorProgress = new ProgressBar("Exporting simulation", steps)) {
            FileWriter outputFile = new FileWriter(trajectories);
            CSVWriter writer = new CSVWriter(outputFile);
            String[] header = makeHeader();
            writer.writeNext(header);
            for (int step = 0; step < steps - 1; step++) {
                simulator.next();
                writer.writeNext(positions());
                simulatorProgress.step();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a JSONObject containing information on the particle properties, including size and color.
     *
     * @return JSONObject representation of particle properties.
     */
    private JSONObject getParticleProperties() {
        JSONObject particleProperties = new JSONObject();
        for (int i = 0; i < particles.size(); i++) {
            JSONObject particleData = new JSONObject();
            try {
                particleData.put("Size", particles.get(i).getSize());
                particleData.put("Color", particles.get(i).getColor().getRGB());
                particleProperties.put(String.valueOf(i), particleData);
            } catch (JSONException e) {
                System.out.println("Unable to export particle data");
                e.printStackTrace();
                System.exit(-1);
            }
        }
        return particleProperties;
    }

    public void createOutDir(String outPutDir) {
        File directory = new File(outPutDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    /**
     * Returns the positions of all particles in a the simulator's current state.
     * @return Array of particle positions.
     */
    private String[] positions() {
        String[] positions = new String[particles.size() * 2];
        for (int i = 0; i < particles.size(); i++) {
            positions[2 * i] = String.valueOf(particles.get(i).getPosition().getX());
            positions[(2 * i) + 1] = String.valueOf(particles.get(i).getPosition().getY());
        }
        return positions;
    }

    /**
     * Creates a header row for the CSV trajectories file.
     * @return String header array.
     */
    private String[] makeHeader() {
        String[] header = new String[particles.size() * 2];
        for (int i = 0; i < particles.size(); i++) {
            header[2 * i] = "P" + String.valueOf(i) + "_X";
            header[(2 * i) + 1] = "P" + String.valueOf(i) + "_Y";
        }
        return header;
    }

    public static void main(String[] args) throws InvalidAttributeValueException, JSONException, IOException {
        String configFile, outputDir;
        if (args.length == 2) {
            configFile = args[0];
            outputDir = args[1];
        } else {
            configFile = "src\\main\\resources\\DefaultSimulatorConfig.json";
            outputDir = "output\\SimOutput1";
        }
        ConfigLoader loader = new ConfigLoader(configFile);
        BarnesHut simulator = new BarnesHut(loader);
        LargeExporter.exportSimulation(simulator, outputDir, 10000);
    }
}
