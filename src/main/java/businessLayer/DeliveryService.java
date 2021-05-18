package businessLayer;

import dataLayer.BillCreator;
import dataLayer.Serializer;
import dataLayer.Writer;
import presentation.MessageBox;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.Serial;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class DeliveryService implements IDeliveryServiceProcessing, Serializable {
    @Serial
    private static final long serialVersionUID = 6529685098263257690L;
    private transient PropertyChangeSupport menuPropertySupport;
    private transient PropertyChangeSupport ordersPropertySupport;

    public HashSet<MenuItem> getMenu() {
        return menu;
    }
    private HashSet<MenuItem> menu;
    private HashMap<Order, List<Pair<MenuItem, Integer>>> orders;

    public DeliveryService()
    {
        menu = new HashSet<>(0);
        orders = new HashMap<>();
    }

    @Override
    public boolean isWellFormer() {
        if (menu == null)
            return false;
        if (orders == null)
            return false;
        if (menuPropertySupport == null || ordersPropertySupport == null)
            return false;

        return true;
    }

    @Override
    public void importProducts() {
        assert isWellFormer();

        try {
            Path path = Paths.get("products.csv");
            BufferedReader buffer = Files.newBufferedReader(path);

            menu = new HashSet<>();
            buffer.lines().skip(1).distinct()
                    .map(line -> Arrays.asList(line.split(",")))
                    .forEach(list -> menu.add(new BaseProduct(list))
                    );

            Serializer serializer = new Serializer();
            serializer.writeObject(this, "DeliveryService.ser");
        }
        catch (Exception e)
        { e.printStackTrace(); }

        assert menu.size() == 13987;
        assert menu.stream().filter(menuItem ->
                menuItem.getTitle().contentEquals("Fresh Corn Tortillas ")
                        && Float.compare(menuItem.getRating(), 3.75f) == 0
                        && menuItem.getCalories() == 23
                        && menuItem.getProteins() == 1
                        && menuItem.getFat() == 2
                        && menuItem.getSodium() == 61
                        && menuItem.getPrice() == 79
        ).count() == 1 &&
                menu.stream().filter(menuItem ->
                menuItem.getTitle().contentEquals("Pear-Cranberry Mincemeat Lattice Pie ")
                        && Float.compare(menuItem.getRating(), 4.375f) == 0
                        && menuItem.getCalories() == 29997918
                        && menuItem.getProteins() == 200210
                        && menuItem.getFat() == 1716279
                        && menuItem.getSodium() == 27570999
                        && menuItem.getPrice() == 15
        ).count() == 1;
    }

    @Override
    public void addProduct(MenuItem item) {
        assert isWellFormer();
        try {
            assert item != null;
            int preSize = menu.size();

            menu.add(item);
            menuPropertySupport.firePropertyChange("menu", null, item);
            Serializer serializer = new Serializer();
            serializer.writeObject(this, "DeliveryService.ser");

            assert menu.size() == preSize + 1;
            assert menu.contains(item);
        }
        catch (Exception e)
        {
            new MessageBox("Invalid input : " + e.getMessage());
        }
        catch (AssertionError a)
        {
            new MessageBox("Assertion error : " + a.getMessage());
        }
    }

    @Override
    public void modifyProduct(List<MenuItem> selectedItems, Object[] fields) {
        assert isWellFormer();
        assert fields != null && selectedItems != null;
        int preSize = menu.size();

        try {
            if (selectedItems.size() > 0)
            {
                for (MenuItem selectedItem : selectedItems)
                {
                    MenuItem modifiedItem = selectedItem.modifyItem(fields);
                    menu.remove(selectedItem);
                    menu.add(modifiedItem);
                    menuPropertySupport.firePropertyChange("menu", selectedItem, modifiedItem);
                }

                Serializer serializer = new Serializer();
                serializer.writeObject(this, "DeliveryService.ser");
            }
            else
                new MessageBox("You have to select which items want to modify");
        }
        catch (Exception e)
        {
            new MessageBox(e.getMessage());
        }

        assert menu.size() == preSize;
    }

    @Override
    public void removeProduct(List<MenuItem> selectedItems) {
        assert isWellFormer();
        assert selectedItems != null;
        int preSize = menu.size();

        for (MenuItem selectedItem : selectedItems) {
            menu.remove(selectedItem);
            menuPropertySupport.firePropertyChange("menu", selectedItem, null);
        }

        Serializer serializer = new Serializer();
        serializer.writeObject(this, "DeliveryService.ser");

        for (MenuItem selectedItem : selectedItems)
            assert !menu.contains(selectedItem);
        assert menu.size() == preSize - selectedItems.size();
    }

    @Override
    public void createNewOrder(String client, List<Pair<MenuItem, Integer>> choices) {
        assert isWellFormer();
        assert client != null;
        assert choices != null;
        int preSize = orders.size();

        Order newOrder = new Order(orders.size(), client, choices);
        orders.put(newOrder, choices);

        String bill = BillCreator.createBill(newOrder, choices);
        ordersPropertySupport.firePropertyChange("orders", null, bill);

        Serializer serializer = new Serializer();
        serializer.writeObject(this, "DeliveryService.ser");

        assert orders.size() == preSize + 1;
        assert orders.containsKey(newOrder);
    }

    @Override
    public void generateReportsOfOrdersInInterval(int startHour, int endHour)
    {
        assert isWellFormer();
        assert endHour - startHour > 0 && endHour > 0 && startHour > 0 && endHour < 23 && startHour < 23;
        int preSize = orders.size();

        String title = "Orders ordered beetween " + startHour + " and " + endHour;

        List<Order> orders = this.orders.keySet().stream().filter(order -> {
            int hour = DateUtils.getHour(order.getOrderDate());
            return startHour <= hour && hour <= endHour;
        }).collect(Collectors.toList());

        StringBuilder report = new StringBuilder();
        orders.forEach(order -> {
            report.append("Order : ").append(order.getOrderID()).append(" by ").append(order.getClientID()).append(" with price ").append(order.getPrice()).append(" in date ").append(order.getOrderDate()).append(" composited: ").append('\n');
            List<Pair<MenuItem, Integer>> orderedItem = this.orders.get(order);
            orderedItem.forEach(item -> {
                List<Object[]> rows = item.key.getRepresentation();
                Object[] row = rows.get(0);
                report.append("Title: " + row[0] + " Rating: " + row[1] + " Calories: " + row[2] + " Proteins: " + row[3] + " Fat: " + row[4] + " Sodium: " + row[5] + " Price: " + row[6] + " Quantity: " + item.value + "\n");
            });
            report.append("\n");
        });

        Writer.write(report.toString(), title);

        assert orders.size() == preSize;
    }

    @Override
    public void generateReportsOfProductsOrderedMoreThan(int threshold) {
        assert isWellFormer();
        assert threshold > 0;
        int preSize = orders.size();

        String title = "Products ordered more than " + threshold + " times";
        HashMap<MenuItem, Integer> productsOrderedCount = this.orders.values().stream().collect(HashMap::new, CollectorsUtils.orderedCountAcumulator(), CollectorsUtils.mapSumCombiner());
        List<MenuItem> validProducts = productsOrderedCount.keySet().stream().filter(item -> productsOrderedCount.get(item) > threshold).collect(Collectors.toList());

        StringBuilder report = new StringBuilder();
        validProducts.forEach(item -> {
            report.append("Product ordered by ").append(productsOrderedCount.get(item)).append(" times\n");
            Object[] row = item.getRepresentation().get(0);
            report.append("Title: ")
                    .append(row[0])
                    .append(" Rating: ")
                    .append(row[1])
                    .append(" Calories: ")
                    .append(row[2])
                    .append(" Proteins: ").
                    append(row[3])
                    .append(" Fat: ").append(row[4]).append(" Sodium: ").append(row[5]).append(" Price: ").append(row[6]).append("\n");
            report.append("\n");
        });

        Writer.write(report.toString(), title);

        assert orders.size() == preSize;
    }

    @Override
    public void generateReportsOfClients(int orderedThreshold, int valueThreshold) {
        assert isWellFormer();
        assert orderedThreshold >= 0 && valueThreshold >= 0;
        int preSize = orders.size();

        List<String> clients = orders.keySet().stream()
                .filter(order -> order.getPrice() > valueThreshold)
                .collect(HashMap::new, CollectorsUtils.clientOrdersCountAcumulator(), CollectorsUtils.mapSumCombiner())
                .entrySet().stream().filter(entry -> entry.getValue() > orderedThreshold).map(Map.Entry::getKey).collect(Collectors.toList());

        String title = "Clients name witch ordered more than " + orderedThreshold + " orders with a value higher than " + valueThreshold;
        StringBuilder report = new StringBuilder();
        clients.forEach(client -> report.append(client).append("\n"));
        Writer.write(report.toString(), title);

        assert orders.size() == preSize;
    }

    @Override
    public void generateReportOfProductsOrderedInDay(LocalDate date) {
        assert isWellFormer();
        assert date != null;
        int preSize = orders.size();

        HashMap<MenuItem, Integer> items = orders.entrySet().stream().filter(entry -> DateUtils.getToLocalDate(entry.getKey().getOrderDate()).compareTo(date) == 0)
                .map(Map.Entry::getValue).collect(HashMap::new, CollectorsUtils.orderedCountAcumulator(), CollectorsUtils.mapSumCombiner());

        String title = "Products ordered in " + date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        StringBuilder report = new StringBuilder();
        items.forEach((key, value) -> report.append(key.getTitle()).append(" ordered by ").append(value).append(" times\n"));
        Writer.write(report.toString(), title);

        assert orders.size() == preSize;
    }


    public void createNewPropertyChangeSupport()
    {
        menuPropertySupport = new PropertyChangeSupport(this);
        ordersPropertySupport = new PropertyChangeSupport(this);
    }

    public void addMenuPropertyChangeListener(PropertyChangeListener pcl)
    {
        menuPropertySupport.addPropertyChangeListener(pcl);
    }

    public void removeMenuPropertyChangeListener(PropertyChangeListener pcl)
    {
        menuPropertySupport.removePropertyChangeListener(pcl);
    }

    public void addOrdersPropertyChangeListener(PropertyChangeListener pcl)
    {
        ordersPropertySupport.addPropertyChangeListener(pcl);
    }

    public void removeOrderPropertyChangeListener(PropertyChangeListener pcl)
    {
        ordersPropertySupport.removePropertyChangeListener(pcl);
    }
}
