package br.com.nubank;

import br.com.nubank.config.ConfigLoader;
import br.com.nubank.config.ConfigNames;
import br.com.nubank.core.WalletProcessor;
import br.com.nubank.core.entities.Operation;
import br.com.nubank.core.entities.Tax;
import br.com.nubank.core.entities.Wallet;
import br.com.nubank.util.OperationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class CapitalGains {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        final Optional<Double> maxOperationValue = ConfigLoader.getInstance().getConfigAsDouble(ConfigNames.MAX_OPERATION_VALUE);
        final Optional<Integer> taxPercent = ConfigLoader.getInstance().getConfigAsInteger(ConfigNames.TAX_PERCENT);

        String line;
        do {
            line = scanner.nextLine();
            var operations = OperationUtils.convertToOperationsFromJSON(line);
            var wallet = createWallet(operations);
            var taxes = new WalletProcessor(maxOperationValue.orElse(0.0), taxPercent.orElse(0)).processWallet(wallet);

            for(Tax tax : taxes) {
                System.out.println(tax.toString());
            }
        } while (!line.isBlank());

        scanner.close();
    }

    private static Wallet createWallet(List<Operation> operations) {
        return Wallet.builder()
                .walletId(UUID.randomUUID())
                .operations(operations)
                .taxes(new ArrayList<>(operations.size()))
                .weightedAveragePrice(0.0)
                .totalQuantity(0)
                .loss(0.0)
                .build();
    }

}