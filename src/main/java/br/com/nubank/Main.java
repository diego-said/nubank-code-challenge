package br.com.nubank;

import br.com.nubank.entities.Operation;
import br.com.nubank.util.OperationUtils;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String line = null;
        do {
            line = scanner.nextLine();
            var operations = OperationUtils.convertToOperationsFromJSON(line);
            for(Operation operation : operations) {
                System.out.println(operation.toString());
            }
        } while (!line.isBlank());

        scanner.close();
    }

}