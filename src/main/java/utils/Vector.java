package utils;

/**
 * Interface for mathematical n-dimensional vectors. Includes methods for basic vector operations
 * including addition, subtraction, multiplication, and division.
 * @param <NdVector> The type of vector operated on. This vector takes on a dimensionality.
 */
public interface Vector<NdVector> {
    // Vector addition
    NdVector add(NdVector vec);

    // Vector subtraction
    NdVector subtract(NdVector vec);

    // Scalar multiplication
    NdVector multiply(double scalar);

    // Scalar division
    NdVector divide(double scalar);

    // Vector dot product
    double dot(NdVector vec);

    // Vector magnitude
    double norm();
}
