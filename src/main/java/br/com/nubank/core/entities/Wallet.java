package br.com.nubank.core.entities;

import lombok.*;

import java.math.BigDecimal;
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

}
