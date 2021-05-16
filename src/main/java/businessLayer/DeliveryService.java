package businessLayer;

import dataLayer.Serializer;
import presentation.MessageBox;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.Serial;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class DeliveryService implements IDeliveryServiceProcessing, Serializable {
    @Serial
    private static final long serialVersionUID = 6529685098263257690L;
    private transient PropertyChangeSupport support;

    public HashSet<MenuItem> getMenu() {
        return menu;
    }
    private HashSet<MenuItem> menu;

    public DeliveryService()
    {
        menu = new HashSet<>(0);
        support = new PropertyChangeSupport(this);
    }

    @Override
    public void importProducts() {
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

        try {
            assert item != null;
            int preSize = menu.size();

            menu.add(item);
            support.firePropertyChange("menu", null, item);
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
                    support.firePropertyChange("menu", selectedItem, modifiedItem);
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
        assert selectedItems != null;
        int preSize = menu.size();

        for (MenuItem selectedItem : selectedItems) {
            menu.remove(selectedItem);
            support.firePropertyChange("menu", selectedItem, null);
        }

        Serializer serializer = new Serializer();
        serializer.writeObject(this, "DeliveryService.ser");

        for (MenuItem selectedItem : selectedItems)
            assert !menu.contains(selectedItem);
        assert menu.size() == preSize - selectedItems.size();
    }

    @Override
    public void generateReports() {

    }

    @Override
    public void createNewOrder() {

    }

    public void createNewPropertyChangeSupport()
    {
        support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl)
    {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl)
    {
        support.removePropertyChangeListener(pcl);
    }

}
