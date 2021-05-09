package presentation;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

public class ViewBackUp extends JFrame {

    public JPanel content;

    private JPanel views;

    private JPanel buttons;

    protected JButton getClientViewButton() {
        return clientViewButton;
    }
    protected JButton getProductViewButton() {
        return productViewButton;
    }
    protected JButton getOrderViewButton() {
        return orderViewButton;
    }
    private JButton clientViewButton;
    private JButton productViewButton;
    private JButton orderViewButton;

    protected JButton getClientAddButton() {
        return clientAddButton;
    }
    protected JButton getClientEditButton() {
        return clientEditButton;
    }
    protected JButton getClientDeleteButton() {
        return clientDeleteButton;
    }
    protected JButton getClientViewTableButton() {
        return clientViewTableButton;
    }
    private JTextField clientName;
    private JTextField clientAddress;
    private JTextField clientEmail;
    private JTextField clientAge;
    private JButton clientAddButton;
    private JButton clientEditButton;
    private JButton clientDeleteButton;
    private JButton clientViewTableButton;
    private JTextArea clientLogArea;
    private JTable clientsTable;


    protected JButton getProductAddButton() {
        return productAddButton;
    }
    protected JButton getProductEditButton() {
        return productEditButton;
    }
    protected JButton getProductDeleteButton() {
        return productDeleteButton;
    }
    protected JButton getProductViewTableButton() {
        return productViewTableButton;
    }
    private JTextField productName;
    private JTextField productCantity;
    private JButton productAddButton;
    private JButton productEditButton;
    private JButton productDeleteButton;
    private JButton productViewTableButton;
    private JTextArea productLogArea;
    private JTable productsTable;

    protected JButton getCreateOrderButton() {
        return createOrderButton;
    }
    private JComboBox<String> orderProductName;
    private JComboBox<String> orderClientName;
    private JTextField orderQuantity;
    private JButton createOrderButton;
    private JTextArea orderLogArea;

    public ViewBackUp()
    {
        content = new JPanel();
        content.setSize( 1000, 1000);
        ((FlowLayout)content.getLayout()).setAlignment(FlowLayout.LEFT);


        createButtons();
        views = new JPanel(new CardLayout());
        Dimension viewsDim = new Dimension(1000, 150);
        views.add(createClientsView(viewsDim), "client");
        views.add(createProductsView(viewsDim), "product");
        views.add(createOrdersView(viewsDim), "order");

        content.add(views);

        add(content);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1125, 1000);
        setVisible(true);
    }

    protected void changeView(String viewName)
    {
        ((CardLayout)views.getLayout()).show(views, viewName);
    }

    protected String[] wrapClientInputFields()
    {
        return new String[] {
                clientName.getText(),
                clientAddress.getText(),
                clientEmail.getText(),
                clientAge.getText()
        };
    }

    protected String[] wrapProductInputFields()
    {
        return new String[] {
                productName.getText(),
                productCantity.getText()
        };
    }

    protected String[] wrapOrderInputFields()
    {
        return new String[] {
                (String)orderProductName.getSelectedItem(),
                (String)orderClientName.getSelectedItem(),
                orderQuantity.getText()
        };
    }

    protected void setTable(TableModel model, int viewIndex)
    {
        switch (viewIndex)
        {
            case 0:
                clientsTable.setModel(model);
                break;
            case 1:
                productsTable.setModel(model);
                break;
        }
    }

    protected void printLog(String msg, int viewIndex)
    {
        switch (viewIndex)
        {
            case 0:
                clientLogArea.append(msg + "\n");
                break;
            case 1:
                productLogArea.append(msg + "\n");
                break;
            case 2:
                orderLogArea.append(msg + "\n");
                break;
        }
    }

    protected void setAvailableClientsAndProductsToOrder(String[] clientsName, String[] productsName)
    {
        if (clientsName != null) {
            DefaultComboBoxModel<String> clientsModel = new DefaultComboBoxModel<>(clientsName);
            orderClientName.setModel(clientsModel);
        }

        if (productsName != null) {
            DefaultComboBoxModel<String> productsModel = new DefaultComboBoxModel<>(productsName);
            orderProductName.setModel(productsModel);
        }
    }

    private void createButtons()
    {
        Dimension d = new Dimension(600, 100);
        Dimension buttonsDim = new Dimension(100, 50);

        buttons = new JPanel();
        buttons.setPreferredSize(d);
        buttons.setMaximumSize(d);

        clientViewButton = createButton("Clients", buttonsDim);
        productViewButton = createButton("Products", buttonsDim);
        orderViewButton = createButton("Orders", buttonsDim);

        buttons.add(clientViewButton);
        buttons.add(Box.createRigidArea(new Dimension(10, 100)));
        buttons.add(productViewButton);
        buttons.add(Box.createRigidArea(new Dimension(10, 100)));
        buttons.add(orderViewButton);

        buttons.setLayout(new FlowLayout());
        ((FlowLayout)buttons.getLayout()).setAlignment(FlowLayout.LEFT);

        content.add(buttons);
    }


    private JPanel createClientsView(Dimension viewDim)
    {
        JPanel clientsView = new JPanel();
        clientsView.setLayout(new BoxLayout(clientsView, BoxLayout.Y_AXIS));

        JPanel clientsInputView = new JPanel();
        clientsInputView.setPreferredSize(viewDim);
        clientsInputView.setMaximumSize(viewDim);
        clientsInputView.setLayout(new BoxLayout(clientsInputView, BoxLayout.X_AXIS));
        JPanel inputPanel = createInputPanelOfClientView();
        JPanel buttonPanel = createButtonPanelOfClientView();
        clientsInputView.add(inputPanel);
        clientsInputView.add(buttonPanel);
        clientsView.add(clientsInputView);

        clientLogArea = new JTextArea(20, 100);
        clientsView.add(new JScrollPane(clientLogArea));

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Client ID");
        model.addColumn("Client Name");
        model.addColumn("Client Address");
        model.addColumn("Client Email");
        model.addColumn("Client Age");
        clientsTable = new JTable(model);
        clientsView.add(new JScrollPane(clientsTable));

        return clientsView;
    }

    private JPanel createInputPanelOfClientView()
    {
        JPanel inputPanel = new JPanel();
        Dimension panelDim = new Dimension(1000, 400);
        inputPanel.setPreferredSize(panelDim);
        inputPanel.setMaximumSize(panelDim);
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        Dimension textFieldDim = new Dimension(200, 30);
        clientName = createJTextField(textFieldDim);
        clientAddress = createJTextField(textFieldDim);
        clientEmail = createJTextField(textFieldDim);
        clientAge = createJTextField(textFieldDim);

        inputPanel.add(createInputRow("Client Name:      ", clientName));
        inputPanel.add(createInputRow("Client Address: ", clientAddress));
        inputPanel.add(createInputRow("Client Email:       ", clientEmail));
        inputPanel.add(createInputRow("Client Age:          ", clientAge));

        inputPanel.setAlignmentX(0.0f);
        return inputPanel;
    }

    private JPanel createButtonPanelOfClientView()
    {
        Dimension panelDimension = new Dimension(400, 400);

        JPanel buttonCol1 = new JPanel();
        buttonCol1.setPreferredSize(panelDimension);
        buttonCol1.setMaximumSize(panelDimension);
        buttonCol1.setLayout(new BoxLayout(buttonCol1, BoxLayout.Y_AXIS));

        JPanel buttonCol2 = new JPanel();
        buttonCol2.setPreferredSize(panelDimension);
        buttonCol2.setMaximumSize(panelDimension);
        buttonCol2.setLayout(new BoxLayout(buttonCol2, BoxLayout.Y_AXIS));

        Dimension buttonDim = new Dimension(200, 50);
        clientAddButton = createButton("Add Client", buttonDim);
        clientEditButton = createButton("Edit Client", buttonDim);
        clientDeleteButton = createButton("Delete Client", buttonDim);
        clientViewTableButton = createButton("View Client Table", buttonDim);

        buttonCol1.add(clientAddButton);
        buttonCol1.add(clientEditButton);

        buttonCol2.add(clientDeleteButton);
        buttonCol2.add(clientViewTableButton);

        JPanel buttonsPanel = new JPanel();
        Dimension allDim = new Dimension(panelDimension.width * 2, panelDimension.height);
        buttonsPanel.setPreferredSize(allDim);
        buttonsPanel.setMaximumSize(allDim);
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));

        buttonsPanel.add(buttonCol1);
        buttonsPanel.add(buttonCol2);

        buttonsPanel.setAlignmentX(0.0f);
        return buttonsPanel;
    }

    private JPanel createProductsView(Dimension viewDim)
    {
        JPanel productsView = new JPanel();
        productsView.setLayout(new BoxLayout(productsView, BoxLayout.Y_AXIS));

        JPanel productsInputView = new JPanel();
        productsInputView.setPreferredSize(viewDim);
        productsInputView.setMaximumSize(viewDim);
        productsInputView.setLayout(new BoxLayout(productsInputView, BoxLayout.X_AXIS));
        JPanel inputPanel = createInputPanelOfProductsView();
        JPanel buttonPanel = createButtonPanelOfProductsView();
        productsInputView.add(inputPanel);
        productsInputView.add(buttonPanel);
        productsView.add(productsInputView);

        productLogArea = new JTextArea(20, 100);
        productsView.add(new JScrollPane(productLogArea));

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Product ID");
        model.addColumn("Product Name");
        model.addColumn("Product Quantity");
        productsTable = new JTable(model);
        productsView.add(new JScrollPane(productsTable));

        return productsView;
    }

    private JPanel createInputPanelOfProductsView()
    {
        JPanel inputPanel = new JPanel();
        Dimension panelDim = new Dimension(1000, 400);
        inputPanel.setPreferredSize(panelDim);
        inputPanel.setMaximumSize(panelDim);
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        Dimension textFieldDim = new Dimension(200, 30);
        productName = createJTextField(textFieldDim);
        productCantity = createJTextField(textFieldDim);

        inputPanel.add(createInputRow("Product Name:       ", productName));
        inputPanel.add(createInputRow("Product Quantity:  ", productCantity));

        inputPanel.setAlignmentX(0.0f);
        return inputPanel;
    }

    private JPanel createButtonPanelOfProductsView()
    {
        Dimension panelDimension = new Dimension(400, 400);

        JPanel buttonCol1 = new JPanel();
        buttonCol1.setPreferredSize(panelDimension);
        buttonCol1.setMaximumSize(panelDimension);
        buttonCol1.setLayout(new BoxLayout(buttonCol1, BoxLayout.Y_AXIS));

        JPanel buttonCol2 = new JPanel();
        buttonCol2.setPreferredSize(panelDimension);
        buttonCol2.setMaximumSize(panelDimension);
        buttonCol2.setLayout(new BoxLayout(buttonCol2, BoxLayout.Y_AXIS));

        Dimension buttonDim = new Dimension(200, 50);
        productAddButton = createButton("Add Product", buttonDim);
        productEditButton = createButton("Edit Product", buttonDim);
        productDeleteButton = createButton("Delete Product", buttonDim);
        productViewTableButton = createButton("View Product Table", buttonDim);

        buttonCol1.add(productAddButton);
        buttonCol1.add(productEditButton);

        buttonCol2.add(productDeleteButton);
        buttonCol2.add(productViewTableButton);

        JPanel buttonsPanel = new JPanel();
        Dimension allDim = new Dimension(panelDimension.width * 2, panelDimension.height);
        buttonsPanel.setPreferredSize(allDim);
        buttonsPanel.setMaximumSize(allDim);
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));

        buttonsPanel.add(buttonCol1);
        buttonsPanel.add(buttonCol2);

        buttonsPanel.setAlignmentX(0.0f);
        return buttonsPanel;
    }

    private JPanel createOrdersView(Dimension viewDim)
    {
        JPanel orderView = new JPanel();
        orderView.setLayout(new BoxLayout(orderView, BoxLayout.Y_AXIS));

        JPanel orderInputView = new JPanel();
        orderInputView.setPreferredSize(viewDim);
        orderInputView.setMaximumSize(viewDim);
        orderInputView.setLayout(new BoxLayout(orderInputView, BoxLayout.X_AXIS));
        JPanel inputPanel = createInputPanelOfOrdersView();
        JPanel buttonPanel = createButtonPanelOfOrdersView();
        orderInputView.add(inputPanel);
        orderInputView.add(buttonPanel);
        orderView.add(orderInputView);

        orderLogArea = new JTextArea(20, 100);
        orderView.add(new JScrollPane(orderLogArea));

        return orderView;
    }

    private JPanel createInputPanelOfOrdersView()
    {
        JPanel inputPanel = new JPanel();
        Dimension panelDim = new Dimension(500, 400);
        inputPanel.setPreferredSize(panelDim);
        inputPanel.setMaximumSize(panelDim);
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        Dimension textFieldDim = new Dimension(200, 30);
        orderProductName = createJComboBox(textFieldDim);
        orderClientName = createJComboBox(textFieldDim);
        orderQuantity = createJTextField(textFieldDim);

        inputPanel.add(createInputRow("Product Name:  ", orderProductName));
        inputPanel.add(createInputRow("Client Name:      ", orderClientName));
        inputPanel.add(createInputRow("Order Quantity: ", orderQuantity));

        inputPanel.setAlignmentX(0.0f);
        return inputPanel;
    }

    private JPanel createButtonPanelOfOrdersView()
    {
        JPanel buttonsPanel = new JPanel();
        Dimension panelDimension = new Dimension(400, 400);
        buttonsPanel.setPreferredSize(panelDimension);
        buttonsPanel.setMaximumSize(panelDimension);

        Dimension buttonDim = new Dimension(200, 50);
        createOrderButton = createButton("Create order", buttonDim);

        buttonsPanel.add(createOrderButton);

        buttonsPanel.setAlignmentX(0.0f);
        return buttonsPanel;
    }

    private JButton createButton(String msg, Dimension d)
    {
        JButton b = new JButton(msg);
        b.setPreferredSize(d);
        b.setMaximumSize(d);
        b.setAlignmentX(0.0f);
        return  b;
    }

    private JTextField createJTextField(Dimension d)
    {
        JTextField f = new JTextField();
        f.setPreferredSize(d);
        f.setMaximumSize(d);
        return f;
    }

    private JComboBox createJComboBox(Dimension d)
    {
        JComboBox<String> b = new JComboBox<>();
        b.setPreferredSize(d);
        b.setMaximumSize(d);
        return  b;
    }

    private JPanel createInputRow(String name, JComponent textField)
    {
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));

        rowPanel.add(new JLabel(name));
        rowPanel.add(textField);

        rowPanel.setAlignmentX(0.0f);
        return rowPanel;
    }

}
