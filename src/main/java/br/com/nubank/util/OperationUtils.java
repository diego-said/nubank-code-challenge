package br.com.nubank.util;

import br.com.nubank.entities.Operation;
import br.com.nubank.exceptions.OperationFromJSONException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class OperationUtils {

    private static final Logger logger = LoggerFactory.getLogger(OperationUtils.class);

    private OperationUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Converte uma string JSON em uma lista de objetos Operation
     *
     * @param json String JSON
     * @return Lista de objetos Operation
     * @throws OperationFromJSONException Se ocorrer um erro ao converter a string JSON
     */
    public static List<Operation> convertToOperationsFromJSON(String json) {
        if(json == null || json.isBlank()) {
            return List.of();
        }

        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, new TypeReference<>() {});
        } catch (IOException e) {
            logger.error("Failed to convert JSON to Operation list", e);
            throw new OperationFromJSONException(e);
        }
    }

}
