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
    //private StartGUI startGUI;

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
            AdministratorGUI administratorGUI = new AdministratorGUI(deliveryService.getMenu());
            administratorGUI.getImportButton().addActionListener(new ImportButtonListener(administratorGUI));

            AuthenticationGUI authenticationGUI = new AuthenticationGUI();
            authenticationGUI.getLoginButton().addActionListener(new LoggerAdministrator(administratorGUI, authenticationGUI));
        }
    }

    public class ImportButtonListener implements ActionListener {

        private final AdministratorGUI administratorGUI;

        public ImportButtonListener(AdministratorGUI administratorGUI)
        {
            this.administratorGUI = administratorGUI;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            deliveryService.importProducts();
            administratorGUI.updateTable();
        }
    }
}
