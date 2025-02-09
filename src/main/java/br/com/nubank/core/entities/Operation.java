package br.com.nubank.core.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Operation {

    @JsonProperty("operation")
    private String type;

    @JsonProperty("unit-cost")
    private double unitCost;

    @JsonProperty("quantity")
    private int quantity;

    /**
     * Calcula o custo total da operação
     *
     * @return Custo total
     */
    public double getTotalCost() {
        return unitCost * quantity;
    }

    /**
     * Calcula o lucro de uma operação de venda
     *
     * @param weightedAveragePrice Preço médio ponderado
     * @return valor do lucro
     */
    public double calculateProfit(double weightedAveragePrice) {
        return BigDecimal.valueOf(getUnitCost()).subtract(BigDecimal.valueOf(weightedAveragePrice))
                .multiply(BigDecimal.valueOf(getQuantity())).doubleValue();
    }

    /**
     * Calcula a perda de uma operação de venda
     *
     * @param weightedAveragePrice Preço médio ponderado
     * @return valor do prejuízo
     */
    public double calculateLoss(double weightedAveragePrice) {
        return BigDecimal.valueOf(weightedAveragePrice).subtract(BigDecimal.valueOf(getUnitCost()))
                .multiply(BigDecimal.valueOf(getQuantity())).doubleValue();
    }

    @Override
    public String toString() {
        return "Operation{" +
                "type='" + type + '\'' +
                ", unitCost=" + unitCost +
                ", quantity=" + quantity +
                '}';
    }

}