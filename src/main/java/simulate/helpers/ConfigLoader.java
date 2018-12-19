package simulate.helpers;

import com.google.common.io.Files;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Stack;

/**
 * Parses the BarnesHutSimulator config file and stores the relevant values for convenient access.
 */
public class ConfigLoader {

    private Stack<Galaxy> galaxies;
    private int size;
    private int[] origin;
    private double G;
    private double theta;
    private double timeStep;
    private JSONObject fileContents;

    public ConfigLoader(String configFilePath) throws IOException, JSONException {
        String fileText = Files.asCharSource(new File(configFilePath), StandardCharsets.UTF_8).read();
        fileContents = new JSONObject(fileText);
        loadGalaxies();
        loadSimParams();
    }

    private void loadGalaxies() throws JSONException {
        this.galaxies = new Stack<>();
        JSONObject galaxyJson = fileContents.getJSONObject("Galaxies");
        Iterator galaxies = galaxyJson.keys();
        while (galaxies.hasNext()) {
            String galaxy = galaxies.next().toString();
            JSONObject galaxyVals = galaxyJson.getJSONObject(galaxy);
            this.galaxies.push(new Galaxy(galaxyVals));
        }
    }

    private void loadSimParams() throws JSONException {
        this.size = fileContents.getInt("Size");
        JSONArray origin = fileContents.getJSONArray("Origin");
        this.origin = new int[]{origin.getInt(0), origin.getInt(1)};
        this.G = fileContents.getDouble("G");
        this.theta = fileContents.getDouble("Theta");
        this.timeStep = fileContents.getDouble("TimeStep");
    }

    public int getSize() {
        return size;
    }

    public int[] getOrigin() {
        return origin;
    }

    public double getG() {
        return G;
    }

    public double getTheta() {
        return theta;
    }

    public double getTimeStep() {
        return timeStep;
    }

    public Stack<Galaxy> getGalaxies() {
        return galaxies;
    }
}
