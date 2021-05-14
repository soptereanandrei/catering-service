package businessLayer;

import dataLayer.Serializer;
import presentation.MessageBox;

import java.io.BufferedReader;
import java.io.Serial;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class DeliveryService implements IDeliveryServiceProcessing, Serializable {

    public HashSet<MenuItem> getMenu() {
        return menu;
    }
    @Serial
    private static final long serialVersionUID = 6529685098263257690L;
    private HashSet<MenuItem> menu;

    public DeliveryService()
    {
        menu = new HashSet<>(0);
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
    public void addProduct(Object[] fields) {

        try {
            assert fields[0] != null && fields[1] != null && fields[2] != null && fields[3] != null && fields[4] != null && fields[5] != null && fields[6] != null;
            int preSize = menu.size();

            MenuItem newItem = null;
            newItem = new BaseProduct(fields);

            menu.add(newItem);
            Serializer serializer = new Serializer();
            serializer.writeObject(this, "DeliveryService.ser");

            assert menu.size() == preSize + 1;
            assert menu.contains(newItem);
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
    public void modifyProduct(Object[] fields, List<MenuItem> selectedItems) {
        assert fields != null && selectedItems != null;
        try {
            if (selectedItems.size() > 0)
            {
                List<MenuItem> newItems = new ArrayList<>();
                for (MenuItem selectedItem : selectedItems)
                {

                }
            }
            else
                new MessageBox("You have to select which items want to modify");
        }
        catch (Exception e)
        {
            new MessageBox(e.getMessage());
        }
    }

    @Override
    public void removeProduct() {

    }

    @Override
    public void generateReports() {

    }

    @Override
    public void createNewOrder() {

    }

    @Override
    public void searchProduct() {

    }

//    public void test(){
//        Object[] arr = getMenu();
//        int nr = 0;
//        for (Object o : arr) {
//            System.out.println(o);
//            nr++;
//        }
//        System.out.println("count" + nr);
//    }
}
