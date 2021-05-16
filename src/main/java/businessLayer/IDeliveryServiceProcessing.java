package businessLayer;

import java.util.List;

public interface IDeliveryServiceProcessing {
    /**
     * Import products from products.csv file
     * @pre true
     * @post menu.size == 13987
     * @post menu.contains(@first) && menu.contains(@last)
     */
    void importProducts();

    /**
     * Add a new product to menu
     * @param item - the new product
     * @pre item != null
     * @post size() = size()@pre + 1
     * @post menu.contains(item)
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
     */
    void modifyProduct(List<MenuItem> selectedItems, Object[] fields);

    /**
     * Will delete from menu selected items
     * @param selectedItems - items that want to be deleted
     * @pre selectedItems != null
     * @post @forall selectedItems[i] : (1 ... 7) -> menu.contains(selectedItems[i]) == false
     * @post menu.size() == menu.size()@pre - selectedItems.size()
     */
    void removeProduct(List<MenuItem> selectedItems);

    void generateReports();
    void createNewOrder();
}
