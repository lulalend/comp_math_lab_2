package ru.itmo.math.methods;

import ru.itmo.math.entities.EquationOfOneArgs;
import ru.itmo.math.handlers.Printer;

import java.util.Scanner;

public class SimpleIterEquationMethod implements EquationMethod {

    private double lambda;
    private double q;
    private EquationOfOneArgs equation;

    @Override
    public void root(EquationOfOneArgs equation, double a, double b, double eps) {
        setEquation(equation);

        double fa, fb, x, x0, fx0, fx1, fx, delta, x1;
        int currentIter = 0;
        boolean initApprox;
        fa = equation.calculate(a);
        fa = round(fa);
        fb = equation.calculate(b);
        fb = round(fb);
        setLambda(a, b);
        setQ(a, b);
//        if (this.q >= 1) {
//            System.out.println("Не сходится на этом интервале метод :(");
//            System.exit(0);
//        }

//        просим у пользователя начальное приближение
        initApprox = readInitApprox();
        if (initApprox) {
            x0 = a;
            fx0 = fa;
        } else {
            x0 = b;
            fx0 = fb;
        }

//        0ая итерация
        x1 = fi(x0, fx0);
        x1 = round(x1);
        fx1 = equation.calculate(x1);
        fx1 = round(fx1);
        delta = Math.abs(x1-x0);
        Printer printer = new Printer();
        printer.printIter(currentIter++, x0, x1, fi(x1, fx1), fx1, delta);

//        все остальные итерации
        while (delta > eps) {
            x = fi(x1, fx1);
            x = round(x);
            fx = equation.calculate(x);
            fx = round(fx);
            delta = Math.abs(x-x1);
            printer.printIter(currentIter++, x1, x, fi(x, fx), fx, delta);
            x1 = x;
            fx1 = fx;
        }
    }

//    функция для вычисления нового значения
    private double fi(double x, double fx) {
        return x + this.lambda*fx;
    }

    private boolean readInitApprox() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Выберите начальное приближение: левая граница(1) или правая граница(2)");
            String symbol = scanner.next();
            if ("1".equals(symbol)) {
                scanner.close();
                return true;
            } else if ("2".equals(symbol)) {
                scanner.close();
                return false;
            }
            System.out.println("Необходимо ввести 1 или 2!");
        }
    }

    private double round(double a) {
        return (double) Math.round(a * 10000d) / 10000d;
    }

    private void setEquation(EquationOfOneArgs equation) {
        this.equation = equation;
    }

    private void setLambda(double a, double b) {
        double eps = Math.abs(b-a)/100, maximum = Math.abs(equation.getDerivative(a)), i = a+eps;

        while (i <= b) {
            maximum = Math.max(maximum, Math.abs(equation.getDerivative(i)));
            i += eps;
        }

        this.lambda = -1/maximum;
    }

    private void setQ(double a, double b) {
        double f = equation.calculate(a), eps = Math.abs(b-a)/100, i = a+eps,
                maximum = Math.abs(equation.getDerivative(fi(a, f)));

        while (i <= b) {
            f = equation.calculate(i);
            maximum = Math.max(maximum, Math.abs(equation.getDerivative(fi(i, f))));
            i += eps;
        }

        this.q = maximum;
    }
}
