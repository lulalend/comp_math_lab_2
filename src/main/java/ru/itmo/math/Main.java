package ru.itmo.math;

import ru.itmo.math.entities.EquationOfOneArgs;
import ru.itmo.math.entities.EquationOfTwoArgs;
import ru.itmo.math.handlers.ConsoleReader;
import ru.itmo.math.handlers.FileReader;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        EquationOfOneArgs equationOfOneArgsFirst = new EquationOfOneArgs() {
            @Override
            public String getName() {
                return "2,3x\u00B3 + 5,75x\u00B2 - 7,41x - 10,6 = 0";
            }

            @Override
            public double calculate(double x) {
                return 2.3*x*x*x + 5.75*x*x - 7.41*x - 10.6;
            }
        };

        EquationOfOneArgs equationOfOneArgsSecond = new EquationOfOneArgs() {
            @Override
            public String getName() {
                return "exp(x)/5 - 2(x-1)\u00B2 = 0";
            }

            @Override
            public double calculate(double x) {
                return Math.exp(x)/5 - 2*(x-1)*(x-1);
            }
        };

        EquationOfOneArgs equationOfOneArgsThird = new EquationOfOneArgs() {
            @Override
            public String getName() {
                return "3sin(x) + 0,35x - 3,8 = 0";
            }

            @Override
            public double calculate(double x) {
                return 3*Math.sin(x) + 0.35*x - 3.8;
            }
        };

        Map<Integer, EquationOfOneArgs> equations = new TreeMap<>();
        equations.put(1, equationOfOneArgsFirst);
        equations.put(2, equationOfOneArgsSecond);
        equations.put(3, equationOfOneArgsThird);

        List<EquationOfTwoArgs> firstSystem = new ArrayList<>();

        EquationOfTwoArgs firstEquationOfTwoArgs = new EquationOfTwoArgs() {
            @Override
            public String getName() {
                return "0,1x\u00B2  + x + 0,2y\u00B2 - 0,3 = 0";
            }

            @Override
            public double calculate(double x, double y) {
                return 0.1*x*x + x + 0.2*y*y - 0.3;
            }
        };

        EquationOfTwoArgs secondEquationOfTwoArgs = new EquationOfTwoArgs() {
            @Override
            public String getName() {
                return "0,2x\u00B2 + y + 0,1x*y - 0,7 = 0";
            }

            @Override
            public double calculate(double x, double y) {
                return 0.2*x*x + y + 0.1*x*y - 0.7;
            }
        };

        firstSystem.add(firstEquationOfTwoArgs);
        firstSystem.add(secondEquationOfTwoArgs);

        List<EquationOfTwoArgs> secondSystem = new ArrayList<>();

        firstEquationOfTwoArgs = new EquationOfTwoArgs() {
            @Override
            public String getName() {
                return "x\u2074 - y\u2075 + 3x + 3sin(x) = 0";
            }

            @Override
            public double calculate(double x, double y) {
                return x*x*x*x - y*y*y*y*y + 3*x + 3*Math.sin(x);
            }
        };

        secondEquationOfTwoArgs = new EquationOfTwoArgs() {
            @Override
            public String getName() {
                return "exp(7x) - 5y\u00B3x - 7 = 0";
            }

            @Override
            public double calculate(double x, double y) {
                return Math.exp(7*x) - 5*y*x - 7;
            }
        };

        secondSystem.add(firstEquationOfTwoArgs);
        secondSystem.add(secondEquationOfTwoArgs);

        Map<Integer, List<EquationOfTwoArgs>> systems = new TreeMap<>();
        systems.put(1, firstSystem);
        systems.put(2, secondSystem);

        while (true) {
            System.out.println("Будем считывать данные с консоли(1) или файла(2)?");
            String symbol = scanner.next();

            if ("1".equals(symbol)) {
                new ConsoleReader(scanner, equations, systems).readFromConsole();
                break;
            } else if ("2".equals(symbol)) {
                ConsoleReader consoleReader = new ConsoleReader(scanner, equations, systems);
                new FileReader(consoleReader).readFromFile();
                break;
            }

            System.out.println("Необходимо ввести 1 или 2!");
        }

        scanner.close();
    }
}