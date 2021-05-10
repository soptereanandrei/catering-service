import dataLayer.Account;
import dataLayer.Serializer;
import presentation.Controller;
import presentation.StartGUI;

import java.util.ArrayList;
import java.util.List;

public class Start {
    public static void main(String[] args)
    {
        Serializer serializer = new Serializer();
//        Account admin1 = new Account("admin", "admin", 0xffff);
//        Account admin2 = new Account("admin2", "admin2", 0xffff);
//        List<Object> objects = new ArrayList<>();
//        objects.add(admin1);
//        objects.add(admin2);
//        serializer.writeObjects(objects, "Accounts.ser");
        List<Object> read = serializer.load("Accounts.ser");
        for (Object r : read)
            System.out.println(r);

        StartGUI gui = new StartGUI();
        Controller controller = new Controller(gui);
    }
}
