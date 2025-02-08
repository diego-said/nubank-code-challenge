package br.com.nubank.core.entities;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Wallet {

    private List<Operation> operations;
    private List<Tax> taxes;
    private double weightedAveragePrice;
    private int totalQuantity;
    private double loss;

}
