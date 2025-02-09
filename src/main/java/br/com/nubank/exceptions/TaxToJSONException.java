package br.com.nubank.exceptions;

/**
 * Exceção lançada quando ocorre um erro ao converter os impostos sobre operações de venda uma string JSON
 */
public class TaxToJSONException extends RuntimeException {

    public TaxToJSONException(Throwable cause) {
        super(cause);
    }

}
