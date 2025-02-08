package br.com.nubank.exceptions;

/**
 * Exceção lançada quando ocorre um erro ao converter uma string JSON em uma lista de objetos Operation
 */
public class OperationFromJSONException extends RuntimeException {

    public OperationFromJSONException(Throwable cause) {
        super(cause);
    }

}
