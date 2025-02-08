package br.com.nubank.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Operation {

    @JsonProperty("operation")
    private String type;

    @JsonProperty("unit-cost")
    private double unitCost;

    @JsonProperty("quantity")
    private int quantity;

    @Override
    public String toString() {
        return "Operation{" +
                "type='" + type + '\'' +
                ", unitCost=" + unitCost +
                ", quantity=" + quantity +
                '}';
    }

}