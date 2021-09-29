import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LRUCacheTest {
    LRUCache<Integer, Integer> lru;

    @BeforeEach
    public void init() {
        lru = new LRUCache<>();
    }

    @Test
    void getTest() {
        lru.put(1, 1);
        lru.put(2, 2);
        lru.put(3, 3);
        lru.put(4, 4);
        lru.put(5, 5);
        lru.put(6, 6);
        assertEquals(lru.get(2), 2);
    }

    @Test
    void getNullTest() {
        lru.put(1, 1);
        lru.put(2, 2);
        lru.put(3, 3);
        lru.put(4, 4);
        lru.put(5, 5);
        lru.put(6, 6);
        assertNull(lru.get(1));
    }

    @Test
    void putTest() {
        lru.put(2, 4);
        assertEquals(lru.get(2), 4);
    }

    @Test
    void rewritingTest() {
        lru.put(2, 3);
        lru.put(2, 4);
        assertEquals(lru.get(2), 4);
    }

    @Test
    void isEmptyTest() {
        lru.put(1, 2);
        lru.put(2, 3);
        lru.put(3, 4);
        lru.erase(1);
        lru.erase(2);
        lru.erase(3);
        assertEquals(lru.isEmpty(), true);
    }
}