package br.com.nubank;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String line = null;
        do {
            line = scanner.nextLine();
            System.out.println("You entered:" + line);
        } while (!line.isBlank());

        scanner.close();
    }
}