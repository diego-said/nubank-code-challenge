package br.com.nubank.core.entities;

import lombok.*;

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

}
