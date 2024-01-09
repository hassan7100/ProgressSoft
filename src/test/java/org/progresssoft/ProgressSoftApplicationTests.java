package org.progresssoft;

import org.junit.jupiter.api.Test;
import org.progresssoft.Service.StoreService;
import org.progresssoft.Utils.TimeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ProgressSoftApplicationTests {
    @Autowired
    ProgressSoftApplication progressSoftApplication;
    @Autowired
    private StoreService storeService;
    @Test
    void testTimeSet() {
        TimeSet<Integer> timeSet = new TimeSet<>(1000000);
        timeSet.add(1);
        timeSet.add(2);
        timeSet.add(3);
        assertTrue(timeSet.contains(1));
    }
    @Test
    void testStoreService() throws InterruptedException {
        TimeSet<Integer> timeSet = new TimeSet<>(1000);
        timeSet.add(1);
        timeSet.add(2);
        timeSet.add(3);
        Thread.sleep(200);
        assertFalse(timeSet.contains(1));
    }
    @Test
    void test3() {

    }
}
