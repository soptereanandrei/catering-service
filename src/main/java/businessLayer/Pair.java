package businessLayer;

import java.io.Serial;
import java.io.Serializable;

public class Pair <K, V> implements Serializable {
    @Serial
    private static final long serialVersionUID = 6128881098327659610L;

    public K key;
    public V value;

    public Pair(K key, V value)
    {
        this.key = key;
        this.value = value;
    }
}
