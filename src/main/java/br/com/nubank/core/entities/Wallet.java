package br.com.nubank.core.entities;

import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Wallet {

    private UUID walletId;

    private List<Operation> operations;
    private List<Tax> taxes;
    private double weightedAveragePrice;
    private int totalQuantity;
    private double loss;

    /**
     * Remove a quantidade de uma operação de venda da carteira
     *
     * @param operation  Operação de venda
     */
    public void withdrawQuantitySold(Operation operation) {
        setTotalQuantity(getTotalQuantity() - operation.getQuantity());
        if (getTotalQuantity() == 0) { // se a quantidade total da carteira for 0, o preço médio ponderado é zerado
            setWeightedAveragePrice(0.0);
        }
    }

    /**
     * Adiciona a perda de uma operação de venda à carteira
     *
     * @param loss  valor da perda da operação de venda
     */
    public void addLoss(double loss) {
        setLoss(BigDecimal.valueOf(getLoss()).add(BigDecimal.valueOf(loss)).doubleValue());
    }

    /**
     * Calcula o preço médio ponderado da carteira após uma operação de compra
     *
     * @param operation  Operação de compra
     * @return Preço médio ponderado
     */
    public double calculateWeightedAveragePrice(Operation operation) {
        BigDecimal walletTotal = BigDecimal.valueOf(getTotalQuantity()).multiply(BigDecimal.valueOf(getWeightedAveragePrice()));
        BigDecimal operationTotal = BigDecimal.valueOf(operation.getQuantity()).multiply(BigDecimal.valueOf(operation.getUnitCost()));
        BigDecimal quantityTotal = BigDecimal.valueOf(getTotalQuantity()).add(BigDecimal.valueOf(operation.getQuantity()));
        return walletTotal.add(operationTotal).divide(quantityTotal, 2, RoundingMode.HALF_UP).doubleValue();
    }

}
