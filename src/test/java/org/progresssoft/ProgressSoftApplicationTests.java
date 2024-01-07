package org.progresssoft;

import jakarta.annotation.security.RunAs;
import org.junit.jupiter.api.Test;
import org.progresssoft.Model.Deal;
import org.progresssoft.Repository.DealRepository;
import org.progresssoft.Service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.concurrent.CountDownLatch;
@SpringBootTest
class ProgressSoftApplicationTests {
    @Autowired
    ProgressSoftApplication progressSoftApplication;
    @Autowired
    private StoreService storeService;
    @Test
    void testMultiThreading() {
        int numberOfThreads = 100;
        CountDownLatch latch = new CountDownLatch(1);
        Runnable runnable = () -> {
            try {
                latch.await(); // All threads will be stuck here until latch is released
                for (int i = 0; i < 100; i++) {
                    storeService.addDeals(List.of(Deal.builder()
                            .id((long) i)
                            .fromCurrency(Deal.Currency.USD)
                            .toCurrency(Deal.Currency.EUR)
                            .amount(100.0)
                            .build()));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
        for (int i = 0; i < numberOfThreads; i++) {
            new Thread(runnable).start();
        }
        System.out.println("All threads are ready to start");
        latch.countDown();
    }
}
