package ru.kashin;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Type 'exit' to finish.");
        System.out.println("Type arithmetic expression:");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String expr = scanner.nextLine();
            if (expr.equals("exit"))
                break;
            System.out.println(" = " + Calculator.calculate(expr));
        }
    }
}
