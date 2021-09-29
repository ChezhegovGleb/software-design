import java.util.HashMap;
import java.util.LinkedList;

public class LRUCache<K, V>{
    private LinkedList<K> queue = new LinkedList<>();
    private HashMap<K, V> map = new HashMap<>();

    private int CACHE_CAPACITY = 5;

    V get(K key) {
        assert(!map.isEmpty());
        if (map.containsKey(key)) {
            var result = map.get(key);
            queue.remove(key);
            queue.addFirst(key);
            assert(queue.contains(key));
            return result;
        }
        return null;
    }

    void put(K key, V value) {
        if (map.containsKey(key)) {
            queue.remove(key);
            assert(!queue.contains(key));
        } else {
            if (queue.size() == CACHE_CAPACITY) {
                K removableElem = queue.removeLast();
                map.remove(removableElem);
                assert(!map.containsKey(removableElem));
            }
        }
        queue.addFirst(key);
        map.put(key, value);
        assert(map.containsKey(key));
    }

    void erase(K key) {
        assert(map.containsKey(key));
        if (map.containsKey(key)) {
            queue.remove(key);
            map.remove(key);
            assert(!map.containsKey(key));
        }
    }

    Boolean isEmpty() {
        return queue.size() == 0;
    }
}