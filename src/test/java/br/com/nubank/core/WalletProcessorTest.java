package br.com.nubank.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

}