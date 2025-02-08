package br.com.nubank.util;

import br.com.nubank.entities.Operation;
import br.com.nubank.exceptions.OperationFromJSONException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OperationUtilsTest {

    @Test
    void convertToOperationsFromJSONWithValidJSON() {
        String json = "[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000},{\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\": 5000}]";
        List<Operation> operations = OperationUtils.convertToOperationsFromJSON(json);
        assertEquals(2, operations.size());
        assertEquals("buy", operations.get(0).getType());
        assertEquals(10.00, operations.get(0).getUnitCost());
        assertEquals(10000, operations.get(0).getQuantity());
        assertEquals("sell", operations.get(1).getType());
        assertEquals(20.00, operations.get(1).getUnitCost());
        assertEquals(5000, operations.get(1).getQuantity());
    }

    @Test
    void convertToOperationsFromJSONWithInvalidJSON() {
        String json = "[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": \"invalid\"}]";
        assertThrows(OperationFromJSONException.class, () -> OperationUtils.convertToOperationsFromJSON(json));
    }

    @Test
    void convertToOperationsFromJSONWithEmptyArray() {
        List<Operation> operations = OperationUtils.convertToOperationsFromJSON("[]");
        assertTrue(operations.isEmpty());
    }

    @Test
    void convertToOperationsFromJSONWithNull() {
        List<Operation> operations = OperationUtils.convertToOperationsFromJSON(null);
        assertTrue(operations.isEmpty());
    }

    @Test
    void convertToOperationsFromJSONWithEmptyString() {
        List<Operation> operations = OperationUtils.convertToOperationsFromJSON("");
        assertTrue(operations.isEmpty());
    }

}