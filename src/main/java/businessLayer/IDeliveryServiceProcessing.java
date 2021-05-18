package businessLayer;

import java.time.LocalDate;
import java.util.List;

public interface IDeliveryServiceProcessing {
    /**
     * Check for @invariant
     * @return if objects maintain the invariant
     */
    boolean isWellFormer();

    /**
     * Import products from products.csv file
     * @pre true
     * @post menu.size == 13987
     * @post menu.contains(@first) && menu.contains(@last)
     * @invariant isWellFormered()
     */
    void importProducts();

    /**
     * Add a new product to menu
     * @param item - the new product
     * @pre item != null
     * @post size() = size()@pre + 1
     * @post menu.contains(item)
     * @invariant isWellFormered()
     */
    void addProduct(MenuItem item);

    /**
     * Will modified fields of selected items for each not null field in fields array
     * @param selectedItems - object of which fields will be modified
     * @param fields - wrapper array of fields which will be modified
     * @pre fields != null && selectedItems != null
     * @post @forall fields[i] != null : (1 ... 7) -> selectedItems.field = field
     * @post @forall fields[i] == null : (1 ... 7) -> selectedItems.field = field@pre
     * @post menu.size() == menu.size()@pre
     * @invariant isWellFormered()
     */
    void modifyProduct(List<MenuItem> selectedItems, Object[] fields);

    /**
     * Will delete from menu selected items
     * @param selectedItems - items that want to be deleted
     * @pre selectedItems != null
     * @post @forall selectedItems[i] : (1 ... 7) -> menu.contains(selectedItems[i]) == false
     * @post menu.size() == menu.size()@pre - selectedItems.size()
     * @invariant isWellFormered()
     */
    void removeProduct(List<MenuItem> selectedItems);

    /**
     * Create a order from client choices
     * @param client - client name
     * @param choices - selected items with their quantity
     * @pre client != null
     * @pre choices != null
     * @post orders.size() == orders.size()@pre + 1
     * @post orders.contains(Order(choises)) == true
     * @invariant isWellFormered()
     */
    void createNewOrder(String client, List<Pair<MenuItem, Integer>> choices);

    /**
     * Generate all orders placed beetween a start hour and a end hour
     * @param startHour
     * @param endHour
     * @pre endHour - startHour > 0 && endHour > 0 && startHour > 0 && endHour < 23 && startHour < 23
     * @post orders.size() = order.size()@pre
     * @invariant isWellFormered()
     */
    void generateReportsOfOrdersInInterval(int startHour, int endHour);

    /**
     * Generate all products that was ordered more than a given times
     * @param threshold given time
     * @pre threshold > 0
     * @post orders.size() = order.size()@pre
     * @invariant isWellFormered()
     */
    void generateReportsOfProductsOrderedMoreThan(int threshold);

    /**
     * Generate all clients which have ordered orders more than ordered threshold
     * orders with price higher than value threshold
     * @param orderedThreshold
     * @param valueThreshold
     * @pre orderedThreshold >= 0 && valueThreshold >= 0
     * @post orders.size() = order.size()@pre
     * @invariant isWellFormered()
     */
    void generateReportsOfClients(int orderedThreshold, int valueThreshold);

    /**
     * Generate all products that was ordered in a specific date
     * @param date when, format yyyy-MM-dd
     * @pre date != null
     * @post orders.size() = order.size()@pre
     * @invariant isWellFormered()
     */
    void generateReportOfProductsOrderedInDay(LocalDate date);
}
