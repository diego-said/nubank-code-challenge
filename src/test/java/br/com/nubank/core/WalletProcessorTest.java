package br.com.nubank.core;

import br.com.nubank.core.entities.Operation;
import br.com.nubank.core.entities.Wallet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WalletProcessorTest {

    @Test
    void calculateWeightedAveragePriceWithValidInputs() {
        Wallet wallet = new Wallet();
        wallet.setTotalQuantity(100);
        wallet.setWeightedAveragePrice(10.0);

        Operation operation = new Operation();
        operation.setQuantity(50);
        operation.setUnitCost(20.0);

        WalletProcessor processor = new WalletProcessor(1000.0, 10);
        double result = processor.calculateWeightedAveragePrice(wallet, operation);

        assertEquals(13.33, result, 0.01);
    }

    @Test
    void calculateWeightedAveragePriceWithZeroWalletQuantity() {
        Wallet wallet = new Wallet();
        wallet.setTotalQuantity(0);
        wallet.setWeightedAveragePrice(0.0);

        Operation operation = new Operation();
        operation.setQuantity(50);
        operation.setUnitCost(20.0);

        WalletProcessor processor = new WalletProcessor(1000.0, 10);
        double result = processor.calculateWeightedAveragePrice(wallet, operation);

        assertEquals(20.0, result, 0.01);
    }

    @Test
    void calculateWeightedAveragePriceWithZeroOperationQuantity() {
        Wallet wallet = new Wallet();
        wallet.setTotalQuantity(100);
        wallet.setWeightedAveragePrice(10.0);

        Operation operation = new Operation();
        operation.setQuantity(0);
        operation.setUnitCost(20.0);

        WalletProcessor processor = new WalletProcessor(1000.0, 10);
        double result = processor.calculateWeightedAveragePrice(wallet, operation);

        assertEquals(10.0, result, 0.01);
    }

    @Test
    void calculateWeightedAveragePriceWithLargeQuantities() {
        Wallet wallet = new Wallet();
        wallet.setTotalQuantity(1000000);
        wallet.setWeightedAveragePrice(10.0);

        Operation operation = new Operation();
        operation.setQuantity(500000);
        operation.setUnitCost(20.0);

        WalletProcessor processor = new WalletProcessor(1000.0, 10);
        double result = processor.calculateWeightedAveragePrice(wallet, operation);

        assertEquals(13.33, result, 0.01);
    }

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

}