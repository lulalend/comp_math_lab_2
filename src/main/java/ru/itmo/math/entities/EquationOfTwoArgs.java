package ru.itmo.math.entities;

public interface EquationOfTwoArgs {
    String getName();
    double calculate(double x, double y);

    default double getDerivativeFirstArg(double x, double y) {
        double deltaX = 0.000001;
        return (calculate(x+deltaX, y) - calculate(x, y) )/(deltaX);
    }

    default double getDerivativeSecondArg(double x, double y) {
        double deltaY = 0.000001;
        return (calculate(x, y+deltaY) - calculate(x, y))/(deltaY);
    }
}