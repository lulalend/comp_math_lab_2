package ru.itmo.math.handlers;

import ru.itmo.math.entities.EquationOfOneArgs;
import ru.itmo.math.entities.EquationOfTwoArgs;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FileReader {

    private final ConsoleReader consoleReader;

    public FileReader(ConsoleReader consoleReader) {
        this.consoleReader = consoleReader;
    }

    public void readFromFile() {
        try {
            String file = "test1.txt", data;
            Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(file)));
            int typeOfProgram = 0, equationNumber = 0;

            data = scanner.next();
            if (isNumber(data)) {
                typeOfProgram = Integer.parseInt(data);
                if (typeOfProgram < 1 || typeOfProgram > 2) {
                    System.out.println("Первое число должно быть 1 или 2: " +
                            "Нелинейное уравнение(1), систему уравнений(2)");
                    System.exit(0);
                }
            } else {
                System.out.println("Первое символ - целое число!");
                System.exit(0);
            }

            if (typeOfProgram == 1) {
                data = scanner.next();
                if (isNumber(data)) {
                    equationNumber = Integer.parseInt(data);
                    if (equationNumber < 1 || equationNumber > 3) {
                        System.out.println("Второе число должно быть 1, или 2, или 3");
                        System.exit(0);
                    }
                } else {
                    System.out.println("Второй символ - целое число!");
                    System.exit(0);
                }
            }

            if (typeOfProgram == 1) {
                this.consoleReader.solveEquation(equationNumber);
            } else {
                this.consoleReader.chooseEquationSystem();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Проверьте путь до файла )");
        }
    }

    private boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}