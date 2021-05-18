import businessLayer.DeliveryService;
import dataLayer.Account;
import dataLayer.Serializer;
import presentation.Controller;
import presentation.StartGUI;

import java.util.List;

public class Start {
    public static void main(String[] args)
    {
        Serializer serializer = new Serializer();
//        serializer.writeObject(new Account("admin", "admin", 0xffff), "Accounts.ser");
//        serializer.appendObject(new Account("alt client", "client", 0x00ff), "Accounts.ser");
        List<Object> read = serializer.loadObjects("Accounts.ser");
        for (Object r : read)
            System.out.println(r);

        StartGUI gui = new StartGUI();
        Controller controller = new Controller(gui);
    }
}
