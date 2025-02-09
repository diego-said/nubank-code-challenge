package br.com.nubank.core.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OperationTest {

    @Test
    void calculateProfit() {
        Operation operation = new Operation("sell", 150.0, 10);
        double profit = operation.calculateProfit(100.0);
        assertEquals(500.0, profit);
    }

    @Test
    void calculateProfitWithZeroProfit() {
        Operation operation = new Operation("sell", 100.0, 10);
        double profit = operation.calculateProfit(100.0);
        assertEquals(0.0, profit);
    }

    @Test
    void calculateProfitWithZeroQuantity() {
        Operation operation = new Operation("sell", 150.0, 0);
        double profit = operation.calculateProfit(100.0);
        assertEquals(0.0, profit);
    }

    @Test
    void calculateLoss() {
        Operation operation = new Operation("sell", 50.0, 10);
        double loss = operation.calculateLoss(100.0);
        assertEquals(500.0, loss);
    }

    @Test
    void calculateLossWithZeroLoss() {
        Operation operation = new Operation("sell", 100.0, 10);
        double loss = operation.calculateLoss(100.0);
        assertEquals(0.0, loss);
    }

    @Test
    void calculateLossWithZeroQuantity() {
        Operation operation = new Operation("sell", 50.0, 0);
        double loss = operation.calculateLoss(100.0);
        assertEquals(0.0, loss);
    }

}
