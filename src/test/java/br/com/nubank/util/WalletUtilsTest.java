package br.com.nubank.util;

import br.com.nubank.core.entities.Operation;
import br.com.nubank.core.entities.Wallet;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WalletUtilsTest {

    @Test
    void createWalletWithValidOperations() {
        List<Operation> operations = List.of(new Operation());
        Wallet wallet = WalletUtils.createWallet(operations);
        assertNotNull(wallet);
        assertEquals(operations, wallet.getOperations());
        assertNotNull(wallet.getTaxes());
        assertEquals(0.0, wallet.getWeightedAveragePrice());
        assertEquals(0, wallet.getTotalQuantity());
        assertEquals(0.0, wallet.getLoss());
    }

    @Test
    void createWalletWithEmptyOperations() {
        List<Operation> operations = Collections.emptyList();
        Wallet wallet = WalletUtils.createWallet(operations);
        assertNotNull(wallet);
        assertTrue(wallet.getOperations().isEmpty());
        assertTrue(wallet.getTaxes().isEmpty());
        assertEquals(0.0, wallet.getWeightedAveragePrice());
        assertEquals(0, wallet.getTotalQuantity());
        assertEquals(0.0, wallet.getLoss());
    }

    @Test
    void createWalletWithNullOperations() {
        assertThrows(NullPointerException.class, () -> WalletUtils.createWallet(null));
    }

    @Test
    void createWalletGeneratesUniqueId() {
        List<Operation> operations = List.of(new Operation());
        Wallet wallet1 = WalletUtils.createWallet(operations);
        Wallet wallet2 = WalletUtils.createWallet(operations);
        assertNotNull(wallet1.getWalletId());
        assertNotNull(wallet2.getWalletId());
        assertNotEquals(wallet1.getWalletId(), wallet2.getWalletId());
    }

}
