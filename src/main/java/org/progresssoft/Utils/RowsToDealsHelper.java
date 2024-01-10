package org.progresssoft.Utils;

import lombok.extern.slf4j.Slf4j;
import org.progresssoft.Model.Deal;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class RowsToDealsHelper {
    public ConversionSet convertRowsToDeals(List<String[]> csvRows){
        ConversionSet conversionSet = new ConversionSet();
        LinkedList<Deal> deals = new LinkedList<>();
        for (String[] csvRow : csvRows) {
            try {
                if("fromCurrencyISOCode".equals(csvRow[1])){
                    continue;
                }
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
            record.setFromCurrency(parseCurrency(csvRow[1].toUpperCase()));
            record.setToCurrency(parseCurrency(csvRow[2].toUpperCase()));
            record.setTimestamp(parseDate(csvRow[3]));
            record.setAmount(parseAmount(csvRow[4]));
            return record;
        }catch(ArrayIndexOutOfBoundsException e){
            log.warn("Error processing Deal: {} reason: not enough fields.", Arrays.toString(csvRow));
            throw new Exception("Error processing Deal: " + Arrays.toString(csvRow) + ", reason: not enough fields.");
        }
        catch (Exception e){
            log.warn("Error processing Deal: " + Arrays.toString(csvRow) + ", reason: " + e.getMessage());
            throw new Exception("Error processing Deal: " + Arrays.toString(csvRow) + ", reason: " + e.getMessage());
        }
    }
    private long parseId(String id) throws IOException {
        try{
            if (id.isEmpty())
                throw new NullPointerException("error parsing id.");
            return Long.parseLong(id);
        } catch (NullPointerException e){
            throw new IOException("id is empty.");
        }
        catch (NumberFormatException e){
            throw new IOException("error parsing id, please check the format.");
        }
    }
    private Deal.Currency parseCurrency(String currency) throws IOException{
        try{
            if (currency.isEmpty())
                throw new NullPointerException("error parsing currency, please check the format.");
            return Deal.Currency.valueOf(currency);
        }catch (NullPointerException e){
            throw new IOException("currency is empty.");
        }
        catch (IllegalArgumentException e){
            throw new IOException("error parsing currency.");
        }
    }
    private Date parseDate(String date) throws IOException{
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormat.setLenient(false);
            return dateFormat.parse(date);
        } catch (Exception e) {
            throw new IOException("error parsing date, please check the format.");
        }
    }
    private double parseAmount(String amount) throws IOException{
        try{
            return Double.parseDouble(amount);
        }catch (NumberFormatException e){
            throw new IOException("error parsing amount, please check the format.");
        }
    }

}
