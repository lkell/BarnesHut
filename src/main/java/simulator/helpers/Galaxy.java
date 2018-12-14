package simulator.helpers;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Vector2D;

import java.awt.*;

public class Galaxy {

    private String model;
    private double mass;
    private double radius;
    private int particleCount;
    private Vector2D position;
    private Vector2D velocity;
    private Color color;

    Galaxy(JSONObject galaxyJson) throws JSONException {
        this.model = galaxyJson.getString("Model");
        this.mass = galaxyJson.getDouble("Mass");
        this.radius = galaxyJson.getDouble("Radius");
        this.particleCount = galaxyJson.getInt("Particles");
        this.position = toVector2D(galaxyJson.getJSONArray("Position"));
        this.velocity = toVector2D(galaxyJson.getJSONArray("Velocity"));
        setColor(galaxyJson.getString("Color"));
    }

    @NotNull
    private Vector2D toVector2D(JSONArray array) throws JSONException {
        return new Vector2D(array.getDouble(0), array.getDouble(1));
    }

    private void setColor(String colorString) {
        switch (colorString) {
            case "Red":
                this.color = Color.RED;
                break;
            case "Blue":
                this.color = Color.BLUE;
                break;
            case "Green":
                this.color = Color.GREEN;
                break;
            case "Yellow":
                this.color = Color.YELLOW;
                break;
            case "PURPLE":
                this.color = Color.ORANGE;
                break;
            default:
                this.color = Color.RED;
                break;
        }
    }

    public String getModel() {
        return model;
    }

    public Color getColor() {
        return color;
    }

    public double getMass() {
        return mass;
    }

    public int getParticleCount() {
        return particleCount;
    }

    public double getRadius() {
        return radius;
    }

    public Vector2D getPosition() {
        return position;
    }

    public Vector2D getVelocity() {
        return velocity;
    }
}
