package br.com.nubank.core;

import br.com.nubank.core.entities.Operation;
import br.com.nubank.core.entities.Wallet;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WalletProcessorTest {

    @Test
    void calculateTax() {
        WalletProcessor processor = new WalletProcessor(1000.0, 10);
        double tax = processor.calculateTax(100.0);
        assertEquals(10.0, tax);
    }

    @Test
    void calculateTaxWithZeroProfit() {
        WalletProcessor processor = new WalletProcessor(1000.0, 10);
        double tax = processor.calculateTax(0.0);
        assertEquals(0.0, tax);
    }

    @Test
    void calculateTaxWithLargeProfit() {
        WalletProcessor processor = new WalletProcessor(1000.0, 10);
        double tax = processor.calculateTax(1000000.0);
        assertEquals(100000.0, tax);
    }

    @Test
    void calculateTaxWithSmallProfit() {
        WalletProcessor processor = new WalletProcessor(1000.0, 10);
        double tax = processor.calculateTax(0.01);
        assertEquals(0.00, tax, 0.01);
    }

    @Test
    void firstOperationIsSell() {
        Wallet wallet = Wallet.builder()
                .operations(List.of(new Operation("sell", 100.0, 10)))
                .build();
        WalletProcessor processor = new WalletProcessor(1000.0, 10);

        assertTrue(processor.firstOperationIsSell(wallet));
    }

    @Test
    void firstOperationIsSellWhenFirstOperationIsBuy() {
        Wallet wallet = Wallet.builder()
                .operations(List.of(new Operation("buy", 100.0, 10)))
                .build();
        WalletProcessor processor = new WalletProcessor(1000.0, 10);

        assertFalse(processor.firstOperationIsSell(wallet));
    }

    @Test
    void processBuyOperationUpdatesWeightedAveragePrice() {
        Wallet wallet = Wallet.builder()
                .weightedAveragePrice(100.0)
                .totalQuantity(10)
                .taxes(new ArrayList<>())
                .build();
        Operation operation = new Operation("buy", 150.0, 10);
        WalletProcessor processor = new WalletProcessor(1000.0, 10);

        processor.processBuyOperation(wallet, operation);

        assertEquals(125.0, wallet.getWeightedAveragePrice());
    }

    @Test
    void processBuyOperationUpdatesTotalQuantity() {
        Wallet wallet = Wallet.builder()
                .totalQuantity(10)
                .taxes(new ArrayList<>())
                .build();
        Operation operation = new Operation("buy", 150.0, 5);
        WalletProcessor processor = new WalletProcessor(1000.0, 10);

        processor.processBuyOperation(wallet, operation);

        assertEquals(15, wallet.getTotalQuantity());
    }

    @Test
    void processBuyOperationAddsZeroTax() {
        Wallet wallet = Wallet.builder()
                .taxes(new ArrayList<>())
                .build();
        Operation operation = new Operation("buy", 150.0, 5);
        WalletProcessor processor = new WalletProcessor(1000.0, 10);

        processor.processBuyOperation(wallet, operation);

        assertEquals(1, wallet.getTaxes().size());
        assertEquals(0.0, wallet.getTaxes().get(0).getValue());
    }

}