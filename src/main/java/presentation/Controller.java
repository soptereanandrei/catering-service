package presentation;

import businessLayer.*;
import dataLayer.Serializer;
;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

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
            {
                administratorGUI.dispose();
                deliveryService.removePropertyChangeListener(administratorGUI.getProductsPanelV2());
            }

            administratorGUI = new AdministratorGUI(deliveryService.getMenu());
            administratorGUI.getImportButton().addActionListener(new ImportButtonListener());
            administratorGUI.getAddProductButton().addActionListener(new AddProductButtonListener());
            administratorGUI.getModifyProductButton().addActionListener(new ModifyProductButtonListener());
            administratorGUI.getCreateNewCompositedProductButton().addActionListener(new AddCompositedProductButtonListener());
            administratorGUI.getDeleteProductButton().addActionListener(new DeleteProductButtonListener());

            AuthenticationGUI authenticationGUI = new AuthenticationGUI();
            authenticationGUI.getLoginButton().addActionListener(new LoggerAdministrator(administratorGUI, authenticationGUI));

            deliveryService.createNewPropertyChangeSupport();
            deliveryService.addPropertyChangeListener(administratorGUI.getProductsPanelV2());
        }
    }

    public class AddProductButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object[] fields = administratorGUI.getInputFields();
            try {
                deliveryService.addProduct(new BaseProduct(fields));
                administratorGUI.getProductsPanelV2().showNoFilterItems();
            }
            catch (Exception ex) { new MessageBox(ex.getMessage()); }
        }
    }

    public class AddCompositedProductButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<MenuItem> selectedItems = administratorGUI.getSelectedItems();
            Object[] fields = administratorGUI.getInputFields();
            try {
                if (fields[0] == null || ((String)fields[0]).isBlank())
                    throw new Exception("Invalid name, write one in Title box");
                deliveryService.addProduct(new CompositedProduct((String)fields[0], selectedItems));
                administratorGUI.getProductsPanelV2().showNoFilterItems();
            }
            catch (Exception ex) { new MessageBox(ex.getMessage()); }
        }
    }

    public class ModifyProductButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<MenuItem> selectedItems = administratorGUI.getSelectedItems();
            Object[] fields = administratorGUI.getInputFields();
            try {
                deliveryService.modifyProduct(selectedItems, fields);
                administratorGUI.getProductsPanelV2().showNoFilterItems();
            }
            catch (Exception ex) { new MessageBox(ex.getMessage()); }
        }
    }

    public class DeleteProductButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<MenuItem> selectedItems = administratorGUI.getSelectedItems();
            deliveryService.removeProduct(selectedItems);
            administratorGUI.getProductsPanelV2().showNoFilterItems();
            administratorGUI.getProductsPanelV2().clearSelection();
        }
    }

    public class ImportButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            deliveryService.importProducts();
            administratorGUI.reinitializationMenu(deliveryService.getMenu());
        }
    }

}
