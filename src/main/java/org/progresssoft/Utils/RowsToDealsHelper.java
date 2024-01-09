package org.progresssoft.Utils;

import lombok.extern.slf4j.Slf4j;
import org.progresssoft.Model.Deal;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class RowsToDealsHelper {
    public ConversionSet convertRowsToDeals(List<String[]> csvRows){
        ConversionSet conversionSet = new ConversionSet();
        LinkedList<Deal> deals = new LinkedList<>();
        if (!csvRows.isEmpty()) {
            csvRows.remove(0);
        }
        for (String[] csvRow : csvRows) {
            try {
                Deal record = createRecordFromCsvRow(csvRow);
                deals.add(record);
            }catch (Exception e){
                conversionSet.addMessage(e.getMessage());
            }
        }
        conversionSet.setSuccessfulDeals(deals);
        return conversionSet;
    }
    private Deal createRecordFromCsvRow(String[] csvRow) throws Exception{
        try{
            Deal record = new Deal();
            record.setId(parseId(csvRow[0]));
            record.setFromCurrency(parseCurrency(csvRow[1]));
            record.setToCurrency(parseCurrency(csvRow[2]));
            record.setTimestamp(parseDate(csvRow[3]));
            record.setAmount(parseAmount(csvRow[4]));
            return record;
        }catch (Exception e){
            log.warn("Error processing Deal: " + Arrays.toString(csvRow) + " reason: " + e.getMessage());
            throw new Exception("Error processing Deal: " + Arrays.toString(csvRow) + " reason: " + e.getMessage());
        }
    }
    private long parseId(String id) throws IOException {
        try{
            return Long.parseLong(id);
        }catch (NumberFormatException e){
            throw new IOException("error parsing id.");
        }
    }
    private Deal.Currency parseCurrency(String currency) throws IOException{
        try{
            return Deal.Currency.valueOf(currency);
        }catch (IllegalArgumentException e){
            throw new IOException("error parsing currency.");
        }
    }
    private Date parseDate(String date) throws IOException{
        try {
            return new Date(date);
        } catch (Exception e) {
            throw new IOException("error parsing date.");
        }
    }
    private double parseAmount(String amount) throws IOException{
        try{
            return Double.parseDouble(amount);
        }catch (NumberFormatException e){
            throw new IOException("error parsing amount.");
        }
    }

}
