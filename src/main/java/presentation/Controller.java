package presentation;

import businessLayer.DeliveryService;
import businessLayer.LoggerAdministrator;
import dataLayer.Serializer;
;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Controller {
    private DeliveryService deliveryService;
    private AdministratorGUI administratorGUI = null;

    public Controller(StartGUI startGUI)
    {
        if (Files.exists(Paths.get("DeliveryService.ser"))) {
            Serializer serializer = new Serializer();
            this.deliveryService = (DeliveryService) serializer.loadObject("DeliveryService.ser");
        }
        else
            this.deliveryService = new DeliveryService();

        //this.startGUI = startGUI;
        startGUI.getAdministratorGUIButton().addActionListener(new AdministratorGUISelectListener());
    }

    public class AdministratorGUISelectListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (administratorGUI != null)
                administratorGUI.dispose();

            administratorGUI = new AdministratorGUI(deliveryService.getMenu());
            administratorGUI.getImportButton().addActionListener(new ImportButtonListener());
            administratorGUI.getAddProductButton().addActionListener(new AddProductButtonListener());

            AuthenticationGUI authenticationGUI = new AuthenticationGUI();
            authenticationGUI.getLoginButton().addActionListener(new LoggerAdministrator(administratorGUI, authenticationGUI));
        }
    }

    public class AddProductButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object[] fields = administratorGUI.getInputFields();
            deliveryService.addProduct(fields);
            administratorGUI.updateMenu(deliveryService.getMenu());
        }
    }

    public class ImportButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            deliveryService.importProducts();
            administratorGUI.updateMenu(deliveryService.getMenu());
        }
    }

}
