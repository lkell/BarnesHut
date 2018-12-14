package utils;

import org.jetbrains.annotations.NotNull;

/**
 * Two dimensional implementation of the utils.Vector interface
 */
public class Vector2D implements Vector<Vector2D> {

    private final double x;
    private final double y;

    /**
     * Constructs the vector using the given components.
     * @param x x component
     * @param y y component
     */
    public Vector2D(@NotNull Number x, @NotNull Number y) {
        this.x = x.doubleValue();
        this.y = y.doubleValue();
    }

    /**
     * Returns a Vector2D object with 0 magnitude.
     * @return A null-vector Vector2D.
     */
    public static Vector2D NullVector() {
        return new Vector2D(0, 0);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /**
     * Vector addition.
     * @param that Vector2D to be added to this vector.
     * @return The vector sum returned as as new Vector2D.
     */
    @NotNull
    public Vector2D add(@NotNull Vector2D that) {
        return new Vector2D(this.x + that.x, this.y + that.y);
    }

    /**
     * Subtracts a vector from the given vector.
     * @param that Vector used to subtract this vector.
     * @return Vector2D representing the difference.
     */
    public Vector2D subtract(Vector2D that) {
        return new Vector2D(this.x - that.x,this.y - that.y);
    }

    /**
     * Performs scalar multiplication and returns the resulting vector.
     * @param scalar Scalar used to divide the vector.
     * @return The vector quotient.
     */
    public Vector2D multiply(double scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }

    /**
     * Performs scalar division and returns the resulting vector.
     * @param scalar scalar vector multiplier
     * @return
     */
    public Vector2D divide(double scalar) {
        return new Vector2D(this.x / scalar, this.y / scalar);
    }

    /**
     * Returns the value of the dot product between this vector and that one.
     * @param that other vector
     * @return The dot product.
     */
    public double dot(Vector2D that) {
        return this.x * that.getX() + this.y * that.y;
    }

    /**
     * Returns a Vector2D representing the midpoint between this vector and that one.
     * @param that other vector
     * @return
     */
    public Vector2D midpoint(Vector2D that) {
        return new Vector2D(this.x + that.x,this.y + that.y).multiply(0.5);
    }

    /**
     * Computes the magnitude of the distance between this and that vector.
     * @param that Other vector.
     * @return Magnitude of the distance.
     */
    public double distanceTo(Vector2D that) {
        return this.subtract(that).norm();
    }

    /**
     * Computes the magnitude of the vector.
     * @return The vector magnitude.
     */
    public double norm() {
        return Math.sqrt(x*x + y*y);
    }

    public Vector2D copy() {
        return new Vector2D(x, y);
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }

    /**
     * Method that checks for equality by inspecting the vector x and y components.
     * @param obj Object to be compared to.
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Vector2D)) {
            return false;
        }

        Vector2D other = (Vector2D) obj;

        return x == other.x & y == other.y;
    }
}
