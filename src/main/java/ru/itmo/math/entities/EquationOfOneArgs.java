package ru.itmo.math.entities;

public interface EquationOfOneArgs {
    String getName();
    double calculate(double x);

    default double getDerivative(double x) {
        double eps = 0.000001;
        return (calculate(x+eps) - calculate(x))/(eps);
    }

    default double getSecondDerivative(double x) {
        double eps = 0.000001, firstDerivative = getDerivative(x);
        return (calculate(firstDerivative+eps) - calculate(firstDerivative))/(eps);
    }
}
