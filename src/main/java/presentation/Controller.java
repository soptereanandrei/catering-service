package presentation;

import businessLayer.*;
import dataLayer.Serializer;
;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class Controller {
    private DeliveryService deliveryService;
    private AdministratorGUI administratorGUI = null;
    private ClientGUI clientGUI = null;

    public Controller(StartGUI startGUI)
    {
        if (Files.exists(Paths.get("DeliveryService.ser"))) {
            Serializer serializer = new Serializer();
            this.deliveryService = (DeliveryService) serializer.loadObject("DeliveryService.ser");
        }
        else
            this.deliveryService = new DeliveryService();
        deliveryService.createNewPropertyChangeSupport();

        startGUI.getAdministratorGUIButton().addActionListener(new AdministratorGUISelectListener());
        startGUI.getClientGUIButton().addActionListener(new ClientGUISelectButton());
        startGUI.getEmployeeGUIButton().addActionListener(new EmployeeGUIButtonListener());
    }

    public class AdministratorGUISelectListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (administratorGUI != null)
            {
                administratorGUI.dispose();
                deliveryService.removeMenuPropertyChangeListener(administratorGUI.getProductsPanel());
            }

            administratorGUI = new AdministratorGUI(deliveryService.getMenu());
            administratorGUI.getImportButton().addActionListener(new ImportButtonListener());
            administratorGUI.getAddProductButton().addActionListener(new AddProductButtonListener());
            administratorGUI.getModifyProductButton().addActionListener(new ModifyProductButtonListener());
            administratorGUI.getCreateNewCompositedProductButton().addActionListener(new AddCompositedProductButtonListener());
            administratorGUI.getDeleteProductButton().addActionListener(new DeleteProductButtonListener());
            administratorGUI.getGenerateReportsButton().addActionListener(new GenerateReportsButtonListener());

            AuthenticationGUI authenticationGUI = new AuthenticationGUI();
            authenticationGUI.getLoginButton().addActionListener(new LoggerAdministrator(administratorGUI, authenticationGUI));

            deliveryService.addMenuPropertyChangeListener(administratorGUI.getProductsPanel());
            administratorGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    deliveryService.removeMenuPropertyChangeListener(administratorGUI.getProductsPanel());
                    System.out.println("AdministratorGUI window closed properly");
                }
            });
        }
    }

    public class AddProductButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object[] fields = administratorGUI.getInputFields();
            try {
                deliveryService.addProduct(new BaseProduct(fields));
                administratorGUI.getProductsPanel().showNoFilterItems();
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
                administratorGUI.getProductsPanel().showNoFilterItems();
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
                administratorGUI.getProductsPanel().showNoFilterItems();
                administratorGUI.getProductsPanel().clearSelection();
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
            administratorGUI.getProductsPanel().showNoFilterItems();
            administratorGUI.getProductsPanel().clearSelection();
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

    public class GenerateReportsButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            GenerateReportWindow window = new GenerateReportWindow();
            window.getOrdersGenerateButton().addActionListener(e1 -> {
                try {
                    int startHour = window.getStartHour();
                    int endHour = window.getEndHour();
                    if (endHour - startHour <= 0 || endHour < 0 || startHour < 0 || endHour > 23 || startHour > 23)
                        throw new Exception("Invalid time interval");
                    deliveryService.generateReportsOfOrdersInInterval(startHour, endHour);
                }
                catch (Exception exp) { new MessageBox(exp.getMessage()); }
            });
            window.getProductsOrderedMoreThan().addActionListener(e1 -> {
                try {
                    int productsThreshold = window.getProductsOrderedThreshold();
                    if (productsThreshold <= 0)
                        throw new Exception("Invalid threshold, must be positive");
                    deliveryService.generateReportsOfProductsOrderedMoreThan(productsThreshold);
                }
                catch (Exception exp) { new MessageBox(exp.getMessage()); }
            });
            window.getClientsGenerateButton().addActionListener(e1 -> {
                try {
                    int orderedThreshold = window.getClientsOrderedThreshold();
                    int valueThreshold = window.getValueOfOrderThreshold();
                    if (orderedThreshold < 0 || valueThreshold < 0)
                        throw new Exception("Invalid threshold, must pe positive");
                    deliveryService.generateReportsOfClients(orderedThreshold, valueThreshold);
                }
                catch (Exception exp) { new MessageBox(exp.getMessage()); }
            });
            window.getProductsOrderedInADay().addActionListener(e1 -> {
                try {
                    String dateInput = window.getDate();
                    LocalDate date = LocalDate.parse(dateInput);
                    //System.out.println(date.toString());
                    deliveryService.generateReportOfProductsOrderedInDay(date);
                }
                catch (Exception exp) { new MessageBox(exp.getMessage() + "\n Use: yyyy-MM-dd format"); }
            });
        }
    }

    public class ClientGUISelectButton implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            clientGUI = new ClientGUI(deliveryService.getMenu());
            clientGUI.getOrderButton().addActionListener(new OrderButtonListener());

            AuthenticationGUI authenticationGUI = new AuthenticationGUI();
            authenticationGUI.getLoginButton().addActionListener(new LoggerClient(clientGUI, authenticationGUI));
        }
    }

    public class OrderButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            deliveryService.createNewOrder(clientGUI.getClientName(), clientGUI.getChooses());
            new MessageBox("Order placed");
        }
    }

    public class EmployeeGUIButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            EmployeeGUI employeeGUI = new EmployeeGUI();
            deliveryService.addOrdersPropertyChangeListener(employeeGUI);
            employeeGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    deliveryService.removeOrderPropertyChangeListener(employeeGUI);
                    System.out.println("EmployeeGUI window closed properly");
                }
            });
        }
    }

}
