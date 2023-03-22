package ru.itmo.math.methods;

import ru.itmo.math.entities.EquationOfTwoArgs;
import ru.itmo.math.handlers.Printer;

import java.util.List;

public class SimpleIterSystemMethod {

    private double firstLambda, secondLambda, q;
    private final List<EquationOfTwoArgs> system;

    public SimpleIterSystemMethod(List<EquationOfTwoArgs> system) {
        this.system = system;
    }

    public void root(double x1, double x2, double y1, double y2, double eps) {
        double x0, y0, x, y, deltaX, deltaY, delta;
        setQ(x1, x2, y1, y2);
//        if (this.q >= 1) {
//            System.out.println("Не сходится система на этом интервале :(");
//            System.exit(0);
//        }
        int currentIter = 0;
        setFirstLambda(x1, x2, y1, y2);
        setSecondLambda(x1, x2, y1, y2);

        x0 = x1;
        y0 = y1;
        delta = -1;
        Printer printer = new Printer();
        printer.printIterSystem(currentIter++, x0, y0, delta);

        delta = 1 + eps;
//        все итерации
        while (delta > eps) {
            x = fiFirst(x0, y0);
            x = round(x);
            deltaX = Math.abs(x-x0);
            y = fiSecond(x0, y0);
            y = round(y);
            deltaY = Math.abs(y-y0);
            delta = Math.max(deltaX, deltaY);
            x0 = x;
            y0 = y;
            printer.printIterSystem(currentIter++, x, y, delta);
        }
    }

    private double round(double a) {
        return (double) Math.round(a * 10000d) / 10000d;
    }

    private double fiFirst(double x, double y) {
        return x + this.firstLambda * this.system.get(0).calculate(x, y);
    }

    private double fiSecond(double x, double y) {
        return y + this.secondLambda * this.system.get(1).calculate(x, y);
    }

    private void setFirstLambda(double x1, double x2, double y1, double y2) {
        double eps1 = Math.abs(x2 - x1) / 100, eps2 = Math.abs(y2 - y1), maximum = -1;

        for (double i = x1; i <= x2; i = i + eps1) {
            for (double j = y1 + eps2; j <= y2; j = j + eps2) {
                maximum = Math.max(Math.abs(this.system.get(0).getDerivativeFirstArg(i, j))
                        + Math.abs(this.system.get(0).getDerivativeFirstArg(i, j)), maximum);
            }
        }

        this.firstLambda = -1 / maximum;
    }

    private void setSecondLambda(double x1, double x2, double y1, double y2) {
        double eps1 = Math.abs(x2 - x1) / 100, eps2 = Math.abs(y2 - y1), maximum = -1;

        for (double i = x1; i <= x2; i = i + eps1) {
            for (double j = y1 + eps2; j <= y2; j = j + eps2) {
                maximum = Math.max(Math.abs(this.system.get(1).getDerivativeFirstArg(i, j))
                        + Math.abs(this.system.get(1).getDerivativeFirstArg(i, j)), maximum);
            }
        }

        this.secondLambda = -1 / maximum;
    }

    private void setQ(double x1, double x2, double y1, double y2) {
        double eps1 = Math.abs(x2 - x1) / 10, eps2 = Math.abs(y2 - y1) / 10,
                maximum = -1, currentMaximum;

        for (double i = x1; i <= x2; i = i + eps1) {
            for (double j = y1; j <= y2; j = j + eps2) {
                currentMaximum = Math.max(
                        Math.abs(this.system.get(0).getDerivativeFirstArg(i, j))
                                + Math.abs(this.system.get(0).getDerivativeSecondArg(i, j)),
                        Math.abs(this.system.get(1).getDerivativeFirstArg(i, j))
                                + Math.abs(this.system.get(1).getDerivativeSecondArg(i, j)));
                maximum = Math.max(maximum, currentMaximum);
            }
        }

        this.q = maximum;
    }
}
