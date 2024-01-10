package org.progresssoft;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.progresssoft.Model.Status;
import org.progresssoft.Repository.DealRepository;
import org.progresssoft.Service.StoreService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestStoreService {
    @InjectMocks
    private StoreService storeService;
    @Mock
    private DealRepository dealRepository;
    @Test
    void testSuccessCase() {
        MultipartFile file = new MockMultipartFile("file", "test.csv", "text/plain",
                ("ID,fromCurrencyISOCode,toCurrencyISOCode,dealTimestamp,dealAmount\n" +
                "1,AED,USD,2001-06-08 05:05:00,200\n" +
                "2,AED,USD,2001-06-08 05:05:00,3000\n" +
                "3,AED,USD,2001-06-08 05:05:00,100\n" +
                "4,AED,USD,2001-06-08 05:05:00,100\n" +
                "5,AED,USD,2001-06-08 05:05:00,100\n" +
                "6,AED,USD,2001-06-08 05:05:00,100\n" +
                "7,AED,USD,2001-06-08 05:05:00,100\n" +
                "8,AED,USD,2001-06-08 05:05:00,100").getBytes());
        Status status = storeService.addDeals(file);
        assertEquals("Success", status.getStatusType().toString());
    }
    @Test
    void testFailureCase() {
        MultipartFile file = new MockMultipartFile("file", "test.csv", "text/plain",
                ("ID,fromCurrencyISOCode,toCurrencyISOCode,dealTimestamp,dealAmount\n").getBytes());
        Status status = storeService.addDeals(file);
        assertEquals("Failed", status.getStatusType().toString());
    }
    @Test
    void testPartialCase(){
        MultipartFile file = new MockMultipartFile("file", "test.csv", "text/plain",
                ("ID,fromCurrencyISOCode,toCurrencyISOCode,dealTimestamp,dealAmount\n" +
                        "1,AED,USD,2001-06-08 05:05:00,200\n" +
                        "2,AED,USD,2001-06-08 05:05:00,3000\n" +
                        "3,AED,USD,2001-06-08 05:05:00,100\n" +
                        "4,AED,USD,2001-06-08 05:05:00,100\n" +
                        "5,AED,USD,2001-06-08 05:05:00,100\n" +
                        "6,AED,USD,2001-06-08 05:05:00,100\n" +
                        "7,AED,USD,2001-06-08 05:05:00,100\n" +
                        "8,AED,USD,2001-06-08 05:05:00,100\n" +
                        "9,AED,USD,2001-06-08 05:05:00,100\n" +
                        "10,AED,USD,2001-06-08 05:05:00,100\n" +
                        "11,AED,USD,2001-06-08 05:05:00,100\n" +
                        "12,AED,USD,2001-06-08 05:05:00,100\n" +
                        "13,AED,USD,2001-06-08 05:05:00,100\n" +
                        "14,AED,USD,2001-06-08 05:05:00,100\n" +
                        "15,AED,USD,2001-06-08 05:05:00,100\n" +
                        "16,AED,USD,2001-06-08 05:05:00,100\n" +
                        "17,AED,USD,2001-06-08 05:05:00,100\n" +
                        "18,AED,USD,2001-06-08 05:0asdx5asd:00,100\n").getBytes());
        Status status = storeService.addDeals(file);
        assertEquals("PartialSuccess", status.getStatusType().toString());
    }

}
