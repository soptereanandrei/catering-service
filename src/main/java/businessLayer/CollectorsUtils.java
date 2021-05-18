package businessLayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class CollectorsUtils {
    public static BiConsumer<HashMap<MenuItem, Integer>, List<Pair<MenuItem, Integer>>> orderedCountAcumulator()
    {
        return (map, listPairs) -> {
            listPairs.forEach(pair -> {
                MenuItem item = pair.key;
                Integer count = pair.value;
                if (map.containsKey(item)) {
                    Integer partialCount = map.get(item);
                    map.replace(item, partialCount + count);
                } else {
                    map.put(item, count);
                }
            });
        };
    }

    public static <K> BiConsumer<HashMap<K, Integer>, HashMap<K, Integer>> mapSumCombiner() {
            return (mapLeft, mapRight) -> {
            mapRight.keySet().forEach(item -> {
                Integer rightCount = mapRight.get(item);
                if (mapLeft.containsKey(item)) {
                    Integer partialCount = mapLeft.get(item);
                    mapLeft.replace(item, partialCount + rightCount);
                } else {
                    mapLeft.put(item, rightCount);
                }
            });
        };
    }

    public static BiConsumer<HashMap<String, Integer>, Order> clientOrdersCountAcumulator()
    {
        return (map, order) -> {
            String clientName = order.getClientID();
            if (map.containsKey(order.getClientID()))
            {
                Integer clientOrders = map.get(clientName);
                clientOrders = clientOrders + 1;
                map.put(clientName, clientOrders);
            }
            else
                map.put(clientName, 1);
        };
    }
}
