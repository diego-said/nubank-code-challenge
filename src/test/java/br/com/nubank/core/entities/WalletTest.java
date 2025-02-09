package br.com.nubank.core.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WalletTest {

    @Test
    void withdrawQuantitySoldReducesTotalQuantity() {
        Wallet wallet = Wallet.builder()
                .totalQuantity(10)
                .weightedAveragePrice(50.0)
                .build();
        Operation operation = new Operation();
        operation.setQuantity(5);

        wallet.withdrawQuantitySold(operation);

        assertEquals(5, wallet.getTotalQuantity());
        assertEquals(50.0, wallet.getWeightedAveragePrice());
    }

    @Test
    void withdrawQuantitySoldSetsWeightedAveragePriceToZeroWhenTotalQuantityIsZero() {
        Wallet wallet = Wallet.builder()
                .totalQuantity(5)
                .weightedAveragePrice(50.0)
                .build();
        Operation operation = new Operation();
        operation.setQuantity(5);

        wallet.withdrawQuantitySold(operation);

        assertEquals(0, wallet.getTotalQuantity());
        assertEquals(0.0, wallet.getWeightedAveragePrice());
    }

    @Test
    void withdrawQuantitySoldWithZeroQuantity() {
        Wallet wallet = Wallet.builder()
                .totalQuantity(10)
                .weightedAveragePrice(50.0)
                .build();
        Operation operation = new Operation();
        operation.setQuantity(0);

        wallet.withdrawQuantitySold(operation);

        assertEquals(10, wallet.getTotalQuantity());
        assertEquals(50.0, wallet.getWeightedAveragePrice());
    }

    @Test
    void addLossIncreasesLoss() {
        Wallet wallet = Wallet.builder()
                .loss(100.0)
                .build();

        wallet.addLoss(50.0);

        assertEquals(150.0, wallet.getLoss());
    }

    @Test
    void addLossWithZeroValue() {
        Wallet wallet = Wallet.builder()
                .loss(100.0)
                .build();

        wallet.addLoss(0.0);

        assertEquals(100.0, wallet.getLoss());
    }

    @Test
    void addLossWithInitialZeroLoss() {
        Wallet wallet = Wallet.builder()
                .loss(0.0)
                .build();

        wallet.addLoss(50.0);

        assertEquals(50.0, wallet.getLoss());
    }

    @Test
    void calculateWeightedAveragePrice() {
        Wallet wallet = new Wallet();
        wallet.setTotalQuantity(100);
        wallet.setWeightedAveragePrice(10.0);

        Operation operation = new Operation();
        operation.setQuantity(50);
        operation.setUnitCost(20.0);

        double result = wallet.calculateWeightedAveragePrice(operation);

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

        double result = wallet.calculateWeightedAveragePrice(operation);

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

        double result = wallet.calculateWeightedAveragePrice(operation);

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

        double result = wallet.calculateWeightedAveragePrice(operation);

        assertEquals(13.33, result, 0.01);
    }

}
