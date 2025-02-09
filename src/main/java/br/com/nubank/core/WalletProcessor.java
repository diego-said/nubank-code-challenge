package br.com.nubank.core;

import br.com.nubank.core.entities.Operation;
import br.com.nubank.core.entities.OperationType;
import br.com.nubank.core.entities.Tax;
import br.com.nubank.core.entities.Wallet;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static java.lang.String.format;

@AllArgsConstructor
public class WalletProcessor {

    private static final Logger logger = LoggerFactory.getLogger(WalletProcessor.class);

    private double maxOperationValue;
    private int taxPercent;

    public List<Tax> processWallet(Wallet wallet) {
        if (wallet == null || wallet.getOperations() == null || wallet.getOperations().isEmpty()) {
            logger.info("Wallet is empty");
            return List.of();
        }

        if (firstOperationIsSell(wallet)) {
            logger.info(format("First operation of wallet with id [%s] is a sell operation", wallet.getWalletId()));
            return List.of();
        }

        for(Operation operation : wallet.getOperations()) {
            if (OperationType.BUY.name().equalsIgnoreCase(operation.getType())) {
                processBuyOperation(wallet, operation);
            } else {
                processSellOperation(wallet, operation);
            }
        }

        return wallet.getTaxes();
    }

    private boolean firstOperationIsSell(Wallet wallet) {
        return OperationType.SELL.name().equalsIgnoreCase(wallet.getOperations().getFirst().getType());
    }

    protected void processBuyOperation(Wallet wallet, Operation operation) {
        wallet.getTaxes().add(new Tax(0.0));

        final double actualWeightedAveragePrice = calculateWeightedAveragePrice(wallet, operation);
        wallet.setWeightedAveragePrice(actualWeightedAveragePrice);
        wallet.setTotalQuantity(wallet.getTotalQuantity() + operation.getQuantity());
    }

    /**
     * Calcula o preço médio ponderado de uma carteira após uma operação de compra
     *
     * @param wallet     Carteira
     * @param operation  Operação de compra
     * @return Preço médio ponderado
     */
    protected double calculateWeightedAveragePrice(Wallet wallet, Operation operation) {
        BigDecimal walletTotal = BigDecimal.valueOf(wallet.getTotalQuantity()).multiply(BigDecimal.valueOf(wallet.getWeightedAveragePrice()));
        BigDecimal operationTotal = BigDecimal.valueOf(operation.getQuantity()).multiply(BigDecimal.valueOf(operation.getUnitCost()));
        BigDecimal totalQuantity = BigDecimal.valueOf(wallet.getTotalQuantity()).add(BigDecimal.valueOf(operation.getQuantity()));
        return walletTotal.add(operationTotal).divide(totalQuantity, 2, RoundingMode.HALF_UP).doubleValue();
    }

    private void processSellOperation(Wallet wallet, Operation operation) {
        if (operation.getTotalCost() <= maxOperationValue) { // se a operação de venda for menor que o valor máximo permitido, não é cobrado imposto
            wallet.getTaxes().add(new Tax(0.0));

            if (operation.getUnitCost() < wallet.getWeightedAveragePrice()) { // verifica se ocorreu prejuízo para adicionar ao total de perdas
                addLossToWallet(wallet, operation);
            }

            withdrawQuantitySold(wallet, operation);
            return;
        }

        if (operation.getUnitCost() > wallet.getWeightedAveragePrice()) { // verifica se ocorreu lucro
            double profit = calculateProfit(wallet, operation);

            // remove do lucro os perdas passadas
            double diff = BigDecimal.valueOf(profit).subtract(BigDecimal.valueOf(wallet.getLoss())).doubleValue();
            if (diff > 0.0) { // se a diferença for maior que as perdas, o imposto é calculado sobre a diferença
                wallet.setLoss(0.0);
                wallet.getTaxes().add(new Tax(calculateTax(diff)));
            } else { // mesmo ocorrendo lucro, se houver perdas maiores que o lucro o imposto é zerado
                wallet.setLoss(BigDecimal.valueOf(wallet.getLoss()).subtract(BigDecimal.valueOf(profit)).doubleValue());
                wallet.getTaxes().add(new Tax(0.0));
            }
        } else {
            wallet.getTaxes().add(new Tax(0.0));

            addLossToWallet(wallet, operation);
        }

        withdrawQuantitySold(wallet, operation);
    }

    /**
     * Adiciona a perda de uma operação de venda à carteira
     *
     * @param wallet     Carteira
     * @param operation  Operação de venda
     */
    private void addLossToWallet(Wallet wallet, Operation operation) {
        double loss = calculateLoss(wallet, operation);
        wallet.setLoss(BigDecimal.valueOf(wallet.getLoss()).add(BigDecimal.valueOf(loss)).doubleValue());
    }

    /**
     * Remove a quantidade de uma operação de venda da carteira
     *
     * @param wallet     Carteira
     * @param operation  Operação de venda
     */
    private void withdrawQuantitySold(Wallet wallet, Operation operation) {
        wallet.setTotalQuantity(wallet.getTotalQuantity() - operation.getQuantity());
        if (wallet.getTotalQuantity() == 0) { // se a quantidade total da carteira for 0, o preço médio ponderado é zerado
            wallet.setWeightedAveragePrice(0.0);
        }
    }

    /**
     * Calcula o lucro de uma operação de venda
     *
     * @param wallet     Carteira
     * @param operation  Operação de venda
     * @return Lucro
     */
    private double calculateProfit(Wallet wallet, Operation operation) {
        return (operation.getUnitCost() - wallet.getWeightedAveragePrice()) * operation.getQuantity();
    }

    /**
     * Calcula a perda de uma operação de venda
     *
     * @param wallet     Carteira
     * @param operation  Operação de venda
     * @return Perda
     */
    private double calculateLoss(Wallet wallet, Operation operation) {
        return (wallet.getWeightedAveragePrice() - operation.getUnitCost()) * operation.getQuantity();
    }

    /**
     * Calcula o imposto sobre lucro de uma operação de venda
     *
     * @param profit Lucro da operação
     * @return Imposto
     */
    protected double calculateTax(double profit) {
        return BigDecimal.valueOf(profit).multiply(BigDecimal.valueOf(taxPercent)).divide(BigDecimal.valueOf(100),2, RoundingMode.HALF_UP).doubleValue();
    }

}
