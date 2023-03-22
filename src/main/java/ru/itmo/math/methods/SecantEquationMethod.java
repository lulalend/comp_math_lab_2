package ru.itmo.math.methods;

import ru.itmo.math.entities.EquationOfOneArgs;
import ru.itmo.math.handlers.Printer;

public class SecantEquationMethod implements EquationMethod {
    @Override
    public void root(EquationOfOneArgs equation, double a, double b, double eps) {
        double x, fa, fb, fx, delta = 1 + eps;
        int currentIter = 0;
        Printer printer = new Printer();
        fa = equation.calculate(a);
        fa = round(fa);
        fb = equation.calculate(b);
        fb = round(fb);

        if (fa * equation.getSecondDerivative(a) <= 0) {
            x = a;
            fx = fa;
            a = b;
            fa = fb;
            b = x;
            fb = fx;
        }

        while (delta > eps) {
            x = b - fb * (b - a) / (fb - fa);
            x = round(x);
            fx = equation.calculate(x);
            fx = round(fx);

            if (fa * fx > 0) {
                delta = Math.abs(x - a);
                a = x;
                fa = fx;
            } else {
                delta = Math.abs(x - b);
                b = x;
                fb = fx;
            }

            printer.print(currentIter++, a, b, x, fx, delta);
        }
    }

    private double round(double a) {
        return (double) Math.round(a * 10000d) / 10000d;
    }
}
