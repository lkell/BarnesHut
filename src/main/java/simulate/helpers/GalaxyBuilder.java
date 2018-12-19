package simulate.helpers;

import simulate.objects.Particle;
import utils.Vector2D;

import javax.management.InvalidAttributeValueException;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GalaxyBuilder {

    private double G;

    public GalaxyBuilder(double G) {
        this.G = G;
    }

    public ArrayList<Particle> buildGalaxy(Galaxy galaxy) throws InvalidAttributeValueException {
        Vector2D center = galaxy.getPosition();
        Vector2D velocity = galaxy.getVelocity();
        double radius = galaxy.getRadius();
        double mass = galaxy.getMass();
        int particleCount = galaxy.getParticleCount();
        Color color = galaxy.getColor();

        String model = galaxy.getModel();
        switch (model) {
            case "Disk":
                return buildDiskGalaxy(center, velocity, radius, mass, particleCount, color);
            case "Line":
                return buildLineGalaxy(center, velocity, radius, mass, particleCount, color);
            case "Plus":
                return buildPlusGalaxy(center, velocity, radius, mass, particleCount, color);
            case "Snail":
                return buildSnailGalaxy(center, velocity, radius, mass, particleCount, color);
            case "Out":
                return buildOutGalaxy(center, velocity, radius, mass, particleCount, color);
            default:
                throw new InvalidAttributeValueException("Specified galaxy model is not supported by GalaxyBuilder");
        }
    }


    /**
     * Used to build a disk-shaped galaxy. This highly simplified model places a massive body
     * in the center of the galaxy and randomly inserts bodies within a circle of the specified @params radius.
     * Particles are given an initial velocity such that their starting orbits are somewhat stable.
     * The mass of the galaxy decays inversely along the radial axis.
     *
     * @param center        position of the central massive body
     * @param velocity      overall starting velocity of the galaxy
     * @param radius        galaxy radius
     * @param mass          mass of the central massive body
     * @param particleCount the number of bodies making up the galaxy
     * @param color         color of the bodies in the galaxy. used when visualizing the galaxy
     * @return ArrayList of all the bodies making up the galaxy
     */
    private ArrayList<Particle> buildDiskGalaxy(Vector2D center, Vector2D velocity, double radius, double mass,
                                                int particleCount, Color color) {
        ArrayList<Particle> particles = new ArrayList<>();
        particles.add(new Particle(center, velocity, mass, color, 6));
        for (int i = 1; i < particleCount; i++) {
            double r = Math.random() * radius + radius / 10;
            double phi = Math.random() * 2 * Math.PI;
            double x = center.getX() + r * Math.cos(phi);
            double y = center.getY() + r * Math.sin(phi);
            double v = Math.sqrt(G * mass / r);
            double vX = v * Math.sin(phi) - velocity.getX();
            double vY = v * Math.cos(phi) - velocity.getX();
            particles.add(new Particle(x, y, -vX, vY, 0.0001, color, 1));
        }
        return particles;
    }

    private ArrayList<Particle> buildSnailGalaxy(Vector2D center, Vector2D velocity, double radius, double mass,
                                                 int particleCount, Color color) {
        ArrayList<Particle> particles = new ArrayList<>();
        particles.add(new Particle(center, velocity, mass, color, 6));
        for (int i = 1; i < particleCount; i++) {
            double r = Math.random() * radius + radius / 10;

            double[] angles = new double[]{0, 90, 80};
            int randomNum = new Random().nextInt(3);
            double phi = angles[randomNum];

            double x = center.getX() + r * Math.cos(phi);
            double y = center.getY() + r * Math.sin(phi);
            double v = Math.sqrt(G * mass / r);
            double vX = v * Math.sin(phi) - velocity.getX();
            double vY = v * Math.cos(phi) - velocity.getX();
            particles.add(new Particle(x, y, -vX, vY, 0.0001, color, 1));
        }
        return particles;
    }

    private ArrayList<Particle> buildPlusGalaxy(Vector2D center, Vector2D velocity, double radius, double mass,
                                                int particleCount, Color color) {
        ArrayList<Particle> particles = new ArrayList<>();
        particles.add(new Particle(center, velocity, mass, color, 6));
        for (int i = 1; i < particleCount; i++) {
            double r = Math.random() * radius + radius / 10;

            double[] angles = new double[]{0, Math.PI / 2, Math.PI, 3 * Math.PI / 2};
            int randomNum = new Random().nextInt(4);
            double phi = angles[randomNum];

            double x = center.getX() + r * Math.cos(phi);
            double y = center.getY() + r * Math.sin(phi);
            double v = Math.sqrt(G * mass / r);
            double vX = v * Math.sin(phi) - velocity.getX();
            double vY = v * Math.cos(phi) - velocity.getX();
            particles.add(new Particle(x, y, -vX, vY, 0.0001, color, 1));
        }
        return particles;
    }

    private ArrayList<Particle> buildLineGalaxy(Vector2D center, Vector2D velocity, double radius, double mass,
                                                int particleCount, Color color) {
        ArrayList<Particle> particles = new ArrayList<>();
        particles.add(new Particle(center, velocity, mass, color, 6));
        for (int i = 1; i < particleCount; i++) {
            double r = Math.random() * radius + radius / 10;

            double phi = Math.PI;

            double x = center.getX() + r * Math.cos(phi);
            double y = center.getY() + r * Math.sin(phi);
            double v = Math.sqrt(G * mass / r);
            double vX = v * Math.sin(phi) - velocity.getX();
            double vY = v * Math.cos(phi) - velocity.getX();
            particles.add(new Particle(x, y, -vX, vY, 0.0001, color, 1));
        }
        return particles;
    }

    private ArrayList<Particle> buildOutGalaxy(Vector2D center, Vector2D velocity, double radius, double mass,
                                               int particleCount, Color color) {
        ArrayList<Particle> particles = new ArrayList<>();
        particles.add(new Particle(center, velocity, mass, color, 6));
        for (int i = 0; i < particleCount; i++) {
            double r = Math.random() * radius + radius / 10;
            double phi = Math.random() * 2 * Math.PI;
            double x = center.getX() + r * Math.cos(phi);
            double y = center.getY() + r * Math.sin(phi);
            double v = Math.sqrt(G * mass / r);
            double vX = v * Math.cos(phi) - velocity.getX();
            double vY = v * Math.sin(phi) - velocity.getX();
            particles.add(new Particle(x, y, 0, 0, 0.0001, color, 1));
        }
        return particles;
    }

//    private ArrayList<Particle> buildOutGalaxy(Vector2D center, Vector2D velocity, double radius, double mass,
//                                                int particleCount, Color color) {
//        ArrayList<Particle> particles = new ArrayList<>();
//        particles.add(new Particle(center, velocity, mass, color, 6));
//        for (int i = 1; i < particleCount; i++) {
//            double r = Math.random() * radius + radius / 10;
//            double phi = Math.random() * 2 * Math.PI;
//            double x = center.getX() + r * Math.cos(phi);
//            double y = center.getY() + r * Math.sin(phi);
//            double v = Math.sqrt(G * mass / r);
//            double vX = v * Math.cos(phi) - velocity.getX();
//            double vY = v * Math.sin(phi) - velocity.getX();
//            particles.add(new Particle(x, y, -vX, vY, 0.0001, color, 1));
//        }
//        return particles;
//    }
}
