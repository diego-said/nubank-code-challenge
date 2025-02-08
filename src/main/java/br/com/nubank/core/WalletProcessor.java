package br.com.nubank.core;

import br.com.nubank.core.entities.Operation;
import br.com.nubank.core.entities.OperationType;
import br.com.nubank.core.entities.Tax;
import br.com.nubank.core.entities.Wallet;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

import static java.lang.String.format;

@AllArgsConstructor
public class WalletProcessor {

    private static final Logger logger = LoggerFactory.getLogger(WalletProcessor.class);

    private UUID walletId = UUID.randomUUID();

    private double maxOperationValue;
    private int taxPercent;

    public List<Tax> processWallet(Wallet wallet) {
        if (wallet == null || wallet.getOperations() == null || wallet.getOperations().isEmpty()) {
            logger.info(format("Wallet with id [%s] is empty", walletId));
            return List.of();
        }

        logger.info(format("Processing wallet with id [%s]", walletId));

        if (firstOperationIsSell(wallet)) {
            logger.info(format("First operation of wallet with id [%s] is a sell operation", walletId));
            return List.of();
        }

        return null;
    }

    private boolean firstOperationIsSell(Wallet wallet) {
        return OperationType.SELL.name().equalsIgnoreCase(wallet.getOperations().getFirst().getType());
    }

}
