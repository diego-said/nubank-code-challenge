package br.com.nubank;

import br.com.nubank.config.ConfigLoader;
import br.com.nubank.config.ConfigNames;
import br.com.nubank.core.entities.Operation;
import br.com.nubank.util.OperationUtils;

import java.util.Optional;
import java.util.Scanner;

public class CapitalGains {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        final Optional<Double> maxOperationValue = ConfigLoader.getInstance().getConfigAsDouble(ConfigNames.MAX_OPERATION_VALUE);
        maxOperationValue.ifPresent(value -> System.out.println("maxOperationValue: " + value));

        String line;
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