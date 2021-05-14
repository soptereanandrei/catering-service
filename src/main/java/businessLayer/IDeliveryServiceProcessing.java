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
     * @param fields - wrapper array of fields of new product
     * @pre fields != null
     * @pre @forall fields[i] : (1 ... 7) != null
     * @post size() = size()@pre + 1
     * @post menu.contains(newItem)
     */
    void addProduct(Object[] fields);

    /**
     * Will modified fields of selected items for each not null field in fields array
     * @param fields - wrapper array of fields which will be modified
     * @param selectedItems - object of which fields will be modified
     * @pre fields != null && selectedItems != null
     * @post @forall fields[i] != null : (1 ... 7) -> selectedItems.field = field
     * @post @forall fields[i] == null : (1 ... 7) -> selectedItems.field = field@pre
     * @post menu.size() == menu.size()@pre
     */
    void modifyProduct(Object[] fields, List<MenuItem> selectedItems);

    void removeProduct();
    void generateReports();
    void createNewOrder();
    void searchProduct();
}
