package businessLayer;

import dataLayer.Serializer;

import java.io.BufferedReader;
import java.io.Serial;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class DeliveryService implements IDeliveryServiceProcessing, Serializable {

    public HashSet<MenuItem> getMenu() {
        return menu;
    }
    @Serial
    private static final long serialVersionUID = 6529685098263257690L;
    private HashSet<MenuItem> menu;

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
    }

    @Override
    public void addProduct() {

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
