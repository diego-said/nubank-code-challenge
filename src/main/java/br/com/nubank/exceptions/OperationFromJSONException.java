package br.com.nubank.exceptions;

/**
 * Exceção lançada quando ocorre um erro ao converter uma string JSON em uma lista de operações de compra e venda
 */
public class OperationFromJSONException extends RuntimeException {

    public OperationFromJSONException(Throwable cause) {
        super(cause);
    }

}
