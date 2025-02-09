package br.com.nubank.util;

import br.com.nubank.core.entities.Tax;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaxUtilsTest {

    @Test
    void convertTaxListToJsonWithValidList() {
        List<Tax> taxList = List.of(new Tax(100.00));
        String json = TaxUtils.convertTaxListToJson(taxList);
        assertEquals("[{\"tax\":100.0}]", json);
    }

    @Test
    void convertTaxListToJsonWithEmptyList() {
        List<Tax> taxList = Collections.emptyList();
        String json = TaxUtils.convertTaxListToJson(taxList);
        assertEquals("[]", json);
    }

}