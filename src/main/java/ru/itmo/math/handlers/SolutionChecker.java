package ru.itmo.math.handlers;

import ru.itmo.math.entities.EquationOfOneArgs;
import ru.itmo.math.entities.EquationOfTwoArgs;

import java.util.List;

public class SolutionChecker {
    private final EquationOfOneArgs equation;
    private final List<EquationOfTwoArgs> system;

    public SolutionChecker(EquationOfOneArgs equation) {
        this.equation = equation;
        this.system = null;
    }
    public SolutionChecker(List<EquationOfTwoArgs> system) {
        this.equation = null;
        this.system = system;
    }

    public void checkSolutionCount(double a, double b) {
        double step = Math.abs(b-a)/100, x = a+step, f2, f1 = this.equation.calculate(a);
        boolean flag = false;
        while(x <= b) {
            f2 = this.equation.calculate(x);
            if (f1 * f2 < 0) {
                if (!flag) {
                    flag = true;
                } else {
                    throw new IllegalArgumentException("На выбранном участке более, чем 1 решение!");
                }
            }
            f1 = f2;
            x += step;
        }
        if (!flag) {
            throw new IllegalArgumentException("На выбранном участке нет корней!");
        }
    }

    public void checkSolutionCount(double x1, double x2, double y1, double y2) {
        EquationOfTwoArgs equation1 = this.system.get(0);
        EquationOfTwoArgs equation2 = this.system.get(1);

        if ((equation2.calculate(x1, y1) > equation1.calculate(x1, y1)
                && equation2.calculate(x2, y2) > equation1.calculate(x2, y2)) ||
                (equation2.calculate(x1, y1) < equation1.calculate(x1, y1)
                        && equation2.calculate(x2, y2) < equation1.calculate(x2, y2))) {
            throw new IllegalArgumentException("На выбранном участке нет корней, либо их чётное количество!");
        }

    }
}
