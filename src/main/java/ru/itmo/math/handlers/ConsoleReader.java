package ru.itmo.math.handlers;

import ru.itmo.math.entities.EquationOfOneArgs;
import ru.itmo.math.entities.EquationOfTwoArgs;
import ru.itmo.math.methods.*;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleReader {

    private final Scanner scanner;
    private final Map<Integer, EquationOfOneArgs> equations;
    private final Map<Integer, List<EquationOfTwoArgs>> systems;
    private Painter painter;

    public ConsoleReader(Scanner scanner,
                         Map<Integer, EquationOfOneArgs> equations,
                         Map<Integer, List<EquationOfTwoArgs>> systems) {
        this.scanner = scanner;
        this.equations = equations;
        this.systems = systems;
    }

    public void readFromConsole() {
        while (true) {
            System.out.println("Что будем решать? Нелинейное уравнение(1), систему уравнений(2)");
            String symbol = scanner.next();

            if ("1".equals(symbol)) {
                int equationNumber = chooseEquation();
                solveEquation(equationNumber);
                break;
            } else if ("2".equals(symbol)) {
                int systemNumber = chooseEquationSystem();
                solveSystem(systemNumber);
                break;
            }
            System.out.println("Необходимо ввести 1 или 2!");
        }

        closePainter();
    }

    public void solveEquation(int equationNumber) {
        EquationMethod equationMethod = chooseEquationMethod();
        double a, b, eps;

        this.painter = new Painter(true, equationNumber);
        painter.show();
        SolutionChecker solutionChecker = new SolutionChecker(equations.get(equationNumber));

        while (true) {
            try {
                a = getLeftBoundary("интервала");
                b = getRightBoundary("интервала");
                solutionChecker.checkSolutionCount(a, b);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        eps = getInaccuracy();
        equationMethod.root(equations.get(equationNumber), a, b, eps);
    }

    private int chooseEquation() {
        int equationNumber;

        System.out.println("Есть вот такие уравнения:");

        for (int key : equations.keySet()) {
            System.out.printf(equations.get(key).getName() + " (%d)%n", key);
        }

        while (true) {
            System.out.println("Какое выбираем? :)");
            String line = scanner.next();

            if (isNumber(line)) {
                equationNumber = Integer.parseInt(line);
                if (equationNumber >= 1 && equationNumber <= equations.size()) {
                    break;
                } else {
                    System.out.printf("Нужно ввести число, из диапазона от 1 до %d!%n", equations.size());
                }
            } else {
                System.out.println("Нужно ввести целое число!");
            }
        }

        return  equationNumber;
    }

    private EquationMethod chooseEquationMethod() {

        System.out.println("Есть методы: метод хорд(1), метод секущих(2), " +
                "метод простой итерации(3)");
        EquationMethod equationMethod;

        while (true) {
            System.out.println("Какое выбираем? :)");
            String line = scanner.next();

            if ("1".equals(line)) {
                equationMethod = new ChordEquationMethod();
                break;
            } else if ("2".equals(line)) {
                equationMethod = new SecantEquationMethod();
                break;
            } else if ("3".equals(line)) {
                equationMethod = new SimpleIterEquationMethod();
                break;
            }

            System.out.println("Необходимо ввести или 1, или 2, или 3!");
        }

        return equationMethod;
    }

    private double getLeftBoundary(String str) {
        double leftBoundary;

        while (true) {
            System.out.printf("Введите левую границу %s:%n", str);
            try {
                leftBoundary = Double.parseDouble(scanner.next());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка! Дробные числа записывать через точку...");
            }
        }

        return leftBoundary;
    }

    private double getRightBoundary(String str) {
        double rightBoundary;

        while (true) {
            System.out.printf("Введите правую границу %s:%n", str);
            try {
                rightBoundary = Double.parseDouble(scanner.next());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка! Дробные числа записывать через точку...");
            }
        }

        return rightBoundary;
    }

    private double getInaccuracy() {
        double inaccuracy;

        while (true) {
            System.out.println("Введите погрешность:");
            try {
                inaccuracy = Double.parseDouble(scanner.next());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка! Дробные числа записывать через точку...");
            }
        }

        return inaccuracy;
    }

    public int chooseEquationSystem() {
        int systemNumber;

        System.out.println("Есть вот такие системы:");

        for (int key : systems.keySet()) {
            System.out.println(systems.get(key).get(0).getName());
            System.out.printf(systems.get(key).get(1).getName() + " (%d)%n", key);
        }

        while (true) {
            System.out.println("Какое выбираем? :)");
            String line = scanner.next();

            if (isNumber(line)) {
                systemNumber = Integer.parseInt(line);
                if (systemNumber >= 1 && systemNumber <= systems.size()) {
                    break;
                } else {
                    System.out.printf("Нужно ввести число, из диапазона от 1 до %d!%n", systems.size());
                }
            } else {
                System.out.println("Нужно ввести целое число!");
            }
        }

        return  systemNumber;
    }

    public void solveSystem(int systemNumber) {
        double x1, x2, y1, y2, eps;
        this.painter = new Painter(false, systemNumber);
        painter.show();
        SolutionChecker solutionChecker = new SolutionChecker(systems.get(systemNumber));

        while (true) {
            try {
                x1 = getLeftBoundary("x");
                x2 = getRightBoundary("x");
                y1 = getLeftBoundary("y");
                y2 = getRightBoundary("y");
                solutionChecker.checkSolutionCount(x1, x2, y1, y2);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        eps = getInaccuracy();

        SimpleIterSystemMethod method = new SimpleIterSystemMethod(systems.get(systemNumber));
        method.root(x1, x2, y1, y2, eps);
    }

    private boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void closePainter() {
        System.out.println();
        System.out.println("Нажмите что-то, чтобы закончить программу");
        scanner.next();
        painter.close();
    }
}