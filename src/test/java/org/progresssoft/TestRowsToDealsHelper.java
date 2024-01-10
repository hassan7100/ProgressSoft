package org.progresssoft;

import org.junit.jupiter.api.Test;
import org.progresssoft.Utils.ConversionSet;
import org.progresssoft.Utils.RowsToDealsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestRowsToDealsHelper {
    @Autowired
    private RowsToDealsHelper rowsToDealsHelper;
    @Test
    void testParsingRandomData() {
        String[] row = {"1", "2", "3", "4", "5"};
        String[] row2 = {"1", "2", "3", "4", "5"};
        List<String[]> csvRows = List.of(row, row2);
        ConversionSet conversionSet = rowsToDealsHelper.convertRowsToDeals(csvRows);
        assertEquals(0, conversionSet.getSuccessfulDeals().size());
    }
    @Test
    void testParsingValidData() {
        String[] row = {"1", "USD", "EUR", "2020-01-01 00:00:00", "20.0"};
        String[] row2 = {"2", "USD", "EUR", "2020-01-01 00:00:00", "10.0"};
        List<String[]> csvRows = List.of(row, row2);
        ConversionSet conversionSet = rowsToDealsHelper.convertRowsToDeals(csvRows);
        assertEquals(2, conversionSet.getSuccessfulDeals().size());
    }
    @Test
    void testWrongId() {
        String[] row = {"1a", "USD", "EUR", "2020-01-01 00:00:00", "20.0"};
        String[] row2 = {"2", "USD", "EUR", "2020-01-01 00:00:00", "10.0"};
        List<String[]> csvRows = List.of(row, row2);
        ConversionSet conversionSet = rowsToDealsHelper.convertRowsToDeals(csvRows);
        assertEquals(1, conversionSet.getSuccessfulDeals().size());
    }
    @Test
    void testWrongCurrency() {
        String[] row = {"1", "USDD", "EUR", "2020-01-01 00:00:00", "20.0"};
        String[] row2 = {"2", "USD", "EUR", "2020-01-01 00:00:00", "10.0"};
        List<String[]> csvRows = List.of(row, row2);
        ConversionSet conversionSet = rowsToDealsHelper.convertRowsToDeals(csvRows);
        assertEquals(1, conversionSet.getSuccessfulDeals().size());
    }
    @Test
    void testWrongDate() {
        String[] row = {"1", "USD", "EUR", "2020-02-02 0dfd0:00:00", "20.0"};
        String[] row2 = {"2", "USD", "EUR", "2020-01-01 00:00:00", "10.0"};
        List<String[]> csvRows = List.of(row, row2);
        ConversionSet conversionSet = rowsToDealsHelper.convertRowsToDeals(csvRows);
        conversionSet.getSuccessfulDeals().stream().map(deal -> deal.getTimestamp()).forEach(System.out::println);
        assertEquals(1, conversionSet.getSuccessfulDeals().size());
    }
    @Test
    void testWrongAmount() {
        String[] row = {"1", "USD", "EUR", "2020-01-01 00:00:00", "20.0a"};
        String[] row2 = {"2", "USD", "EUR", "2020-01-01 00:00:00", "10.0"};
        List<String[]> csvRows = List.of(row, row2);
        ConversionSet conversionSet = rowsToDealsHelper.convertRowsToDeals(csvRows);
        assertEquals(1, conversionSet.getSuccessfulDeals().size());
    }
    @Test
    void testWrongRow() {
        String[] row = {"1", "USD", "EUR", "2020-01-01 00:00:00", "20.0"};
        String[] row2 = {"2", "USD", "EUR", "2020-01-01 00:00:00"};
        List<String[]> csvRows = List.of(row, row2);
        ConversionSet conversionSet = rowsToDealsHelper.convertRowsToDeals(csvRows);
        assertEquals(1, conversionSet.getSuccessfulDeals().size());
    }
    @Test
    void testCurrencyCase() {
        String[] row = {"1", "usd", "eur", "2020-01-01 00:00:00", "20.0"};
        String[] row2 = {"2", "usd", "eur", "2020-01-01 00:00:00", "10.0"};
        List<String[]> csvRows = List.of(row, row2);
        ConversionSet conversionSet = rowsToDealsHelper.convertRowsToDeals(csvRows);
        assertEquals(2, conversionSet.getSuccessfulDeals().size());
    }
}
