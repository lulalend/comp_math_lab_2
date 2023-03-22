package ru.itmo.math.handlers;

public class Printer {
    public void printIter(int Iterate,
                          double x0,
                          double x,
                          double fi,
                          double fx,
                          double delta) {

        if (Iterate == 0) {
            System.out.printf("%3s | %4s    |  %4s | %4s | %s | %4s    |%n",
                    "№", "x", "next_x", "φ(next_x)", "f(next_x)", "∆");
            System.out.println("-----------------------------------------------------------");
        }
        System.out.printf("%3d | ", Iterate);
        System.out.printf("%7.4f | ", x0);
        System.out.printf("%7.4f | ", x);
        System.out.printf("%9.4f | ", fi);
        System.out.printf("%9.4f | ", fx);
        System.out.printf("%7.4f | %n", delta);
    }

    public void print(int Iterate,
                      double a,
                      double b,
                      double x,
                      double fx,
                      double delta) {

        if (Iterate == 0) {
            System.out.printf("%3s | %4s    | %4s    | %4s    |  %s   | %4s    |%n",
                    "№", "a", "b", "x", "f(x)", "∆");
            System.out.println("-------------------------------------------------------");
        }

        System.out.printf("%3d | ", Iterate);
        System.out.printf("%7.4f | ", a);
        System.out.printf("%7.4f | ", b);
        System.out.printf("%7.4f | ", x);
        System.out.printf("%7.4f | ", fx);
        System.out.printf("%7.4f | %n", delta);
    }

    public void printIterSystem(int Iterate,
                      double x,
                      double y,
                      double delta) {

        if (Iterate == 0) {
            System.out.printf("%3s | %4s    | %4s    | %4s    |%n",
                    "№", "x", "y", "∆");
            System.out.println("-----------------------------------------");
        }

        if (delta == -1) {
            System.out.printf("%3d | ", Iterate);
            System.out.printf("%7.4f | ", x);
            System.out.printf("%7.4f | ", y);
            System.out.printf(" %3s    | %n", "-");
        } else {
            System.out.printf("%3d | ", Iterate);
            System.out.printf("%7.4f | ", x);
            System.out.printf("%7.4f | ", y);
            System.out.printf("%7.4f | %n", delta);
        }
    }
}
