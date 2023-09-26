package org.cross.words.Utilities;

import java.util.Scanner;

public class InputUtil {
    private static Scanner scanner = new Scanner(System.in);

    public static String getInputString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    public static int getInputInt(String message) {
        System.out.print(message);
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter an integer.");
            scanner.next(); // Clear the incorrect input
        }
        int number = scanner.nextInt();
        scanner.nextLine(); // Clear the pending newline
        return number;
    }

    public static double getInputDouble(String message) {
        System.out.print(message);
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a decimal number.");
            scanner.next(); // Clear the incorrect input
        }
        double number = scanner.nextDouble();
        scanner.nextLine(); // Clear the pending newline
        return number;
    }

    public static void closeScanner() {
        scanner.close();
    }
}
