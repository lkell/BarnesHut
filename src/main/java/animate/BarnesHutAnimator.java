package animate;

import javax.swing.*;

/**
 * Abstract Barns Hut Animator. Extends JPanel class and has methods to advance the animation by one frame
 * or run the entire animation at once.
 */
public abstract class BarnesHutAnimator extends JPanel {
    /**
     * Advances the animator by one frame by incrementing the simulation one step.
     */
    abstract void advanceFrame();

    /**
     * Animates an entire Barnes Hut simulation.
     */
    public void animate() {
        while (true) {
            advanceFrame();
            repaint();
        }
    }

    public void animate(int steps) {
        for (int i = 0; i < steps; i++) {
            advanceFrame();
            repaint();
        }
    }
}
