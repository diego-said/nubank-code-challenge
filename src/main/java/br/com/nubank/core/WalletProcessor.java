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
            //verfica se a operação possui quantidade ou custo unitário negativo
            if(operation.getQuantity() < 0 || operation.getUnitCost() < 0.0) {
                logger.error(format("Operation with quantity or unit cost less than or equal to zero in wallet with id [%s]", wallet.getWalletId()));
                return List.of();
            }

            if (OperationType.BUY.name().equalsIgnoreCase(operation.getType())) {
                processBuyOperation(wallet, operation);
            } else {
                processSellOperation(wallet, operation);
            }
        }

        return wallet.getTaxes();
    }

    protected boolean firstOperationIsSell(Wallet wallet) {
        return OperationType.SELL.name().equalsIgnoreCase(wallet.getOperations().getFirst().getType());
    }

    protected void processBuyOperation(Wallet wallet, Operation operation) {
        wallet.getTaxes().add(new Tax(0.0));

        final double actualWeightedAveragePrice = wallet.calculateWeightedAveragePrice(operation);
        wallet.setWeightedAveragePrice(actualWeightedAveragePrice);
        wallet.setTotalQuantity(wallet.getTotalQuantity() + operation.getQuantity());
    }

    protected void processSellOperation(Wallet wallet, Operation operation) {
        if (operation.getTotalCost() <= maxOperationValue) { // se a operação de venda for menor que o valor máximo permitido, não é cobrado imposto
            wallet.getTaxes().add(new Tax(0.0));

            if (operation.getUnitCost() < wallet.getWeightedAveragePrice()) { // verifica se ocorreu prejuízo para adicionar ao total de perdas
                wallet.addLoss(operation.calculateLoss(wallet.getWeightedAveragePrice()));
            }

            wallet.withdrawQuantitySold(operation);
            return;
        }

        if (operation.getUnitCost() > wallet.getWeightedAveragePrice()) { // verifica se ocorreu lucro
            double profit = operation.calculateProfit(wallet.getWeightedAveragePrice());

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
            wallet.addLoss(operation.calculateLoss(wallet.getWeightedAveragePrice()));
        }

        wallet.withdrawQuantitySold(operation);
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
