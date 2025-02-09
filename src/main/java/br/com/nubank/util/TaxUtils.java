package br.com.nubank.util;

import br.com.nubank.core.entities.Tax;
import br.com.nubank.exceptions.TaxToJSONException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TaxUtils {

    private static final Logger logger = LoggerFactory.getLogger(TaxUtils.class);

    private TaxUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Converte uma lista de impostos sobre operações de venda em sua representação JSON
     *
     * @param taxList Lista de objetos Tax
     * @return String JSON
     */
    public static String convertTaxListToJson(List<Tax> taxList) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(taxList);
        } catch (JsonProcessingException e) {
            logger.error("Failed to convert Tax list to JSON", e);
            throw new TaxToJSONException(e);
        }
    }

}
