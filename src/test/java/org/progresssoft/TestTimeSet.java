package org.progresssoft;

import org.junit.jupiter.api.Test;
import org.progresssoft.Utils.TimeSet;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestTimeSet {
    @Test
    void testUnExpiredElement() {
        TimeSet<Integer> timeSet = new TimeSet<>(1000);
        timeSet.add(1);
        timeSet.add(2);
        timeSet.add(3);
        assertTrue(timeSet.contains(1)); // should be true because the element has not expired
    }
    @Test
    void testExpiredElement() throws InterruptedException {
        TimeSet<Integer> timeSet = new TimeSet<>(1000);
        timeSet.add(1);
        timeSet.add(2);
        timeSet.add(3);
        Thread.sleep(2000);
        assertFalse(timeSet.contains(1)); // should be false because the element has expired
    }
    @Test
    void testSizeWithoutExpiredElements() {
        TimeSet<Integer> timeSet = new TimeSet<>(1000);
        timeSet.add(1);
        timeSet.add(2);
        timeSet.add(3);
        assertEquals(3, timeSet.size()); // expecting 3 because no elements have expired
    }
    @Test
    void testSizeWithExpiredElements() throws InterruptedException {
        TimeSet<Integer> timeSet = new TimeSet<>(1000);
        timeSet.add(1);
        timeSet.add(2);
        timeSet.add(3);
        Thread.sleep(2000);
        assertEquals(0, timeSet.size()); // expecting 0 because all elements have expired
    }
}
