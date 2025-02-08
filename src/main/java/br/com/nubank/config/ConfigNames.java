package br.com.nubank.config;

/**
 * Enum que contém os nomes das configurações do sistema
 */
public enum ConfigNames {

    MAX_OPERATION_VALUE("max.operation.value"),
    ;

    private final String name;

    ConfigNames(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
