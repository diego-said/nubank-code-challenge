package br.com.nubank.util;

import br.com.nubank.core.entities.Operation;
import br.com.nubank.core.entities.Wallet;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WalletUtils {

    private WalletUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Cria uma carteira a partir de uma lista de operações, com id aletório e as demais propriedades inicializadas com valores padrão
     *
     * @param operations Lista de operações
     * @return Objeto Wallet
     */
    public static Wallet createWallet(List<Operation> operations) {
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
