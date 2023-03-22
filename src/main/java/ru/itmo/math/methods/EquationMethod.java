package ru.itmo.math.methods;

import ru.itmo.math.entities.EquationOfOneArgs;

public interface EquationMethod {
    void root(EquationOfOneArgs equation, double a, double b, double eps);
}
