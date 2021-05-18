package presentation;

import javax.swing.*;
import java.awt.*;

public class GenerateReportWindow extends JFrame {
    public JButton getOrdersGenerateButton() {
        return ordersGenerateButton;
    }

    public JButton getProductsOrderedMoreThan() {
        return productsOrderedMoreThan;
    }

    public JButton getClientsGenerateButton() {
        return clientsGenerateButton;
    }

    public JButton getProductsOrderedInADay() {
        return productsOrderedInADay;
    }

    private JButton ordersGenerateButton;
    private JButton productsOrderedMoreThan;
    private JButton clientsGenerateButton;
    private JButton productsOrderedInADay;

    public int getStartHour() {
        return Integer.parseInt(startHour.getText());
    }

    public int getEndHour() {
        return Integer.parseInt(endHour.getText());
    }

    public int getProductsOrderedThreshold() {
        return Integer.parseInt(productsOrderedThreshold.getText());
    }

    public int getClientsOrderedThreshold() {
        return Integer.parseInt(clientsOrderedThreshold.getText());
    }

    public int getValueOfOrderThreshold() {
        return Integer.parseInt(valueOfOrderThreshold.getText());
    }

    public String getDate() {
        return date.getText();
    }

    private JTextField startHour;
    private JTextField endHour;
    private JTextField productsOrderedThreshold;
    private JTextField clientsOrderedThreshold;
    private JTextField valueOfOrderThreshold;
    private JTextField date;

    private static final Dimension textFieldsDim = new Dimension(300, 40);
    private static final Dimension buttonsDim = new Dimension(400, 50);

    public GenerateReportWindow()
    {
        super();

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.X_AXIS));
        Dimension panelDim = new Dimension(900, 400);
        content.setSize(panelDim);

        content.add(createInputPanel());
        content.add(createButtonsPanel());

        add(content);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(panelDim.width + 20, panelDim.height);
        setLocation(500, 300);
        setVisible(true);
    }

    private JPanel createInputPanel()
    {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        startHour = GUIUtils.createJTextField(textFieldsDim);
        inputPanel.add(GUIUtils.createInputRow("Start hour :                                     ", startHour));
        endHour = GUIUtils.createJTextField(textFieldsDim);
        inputPanel.add(GUIUtils.createInputRow("End hour :                                        ", endHour));
        productsOrderedThreshold = GUIUtils.createJTextField(textFieldsDim);
        inputPanel.add(GUIUtils.createInputRow("Products ordered more than :   ", productsOrderedThreshold));
        clientsOrderedThreshold = GUIUtils.createJTextField(textFieldsDim);
        inputPanel.add(GUIUtils.createInputRow("Client that ordered more than : ", clientsOrderedThreshold));
        valueOfOrderThreshold = GUIUtils.createJTextField(textFieldsDim);
        inputPanel.add(GUIUtils.createInputRow("Value of order higher than :       ", valueOfOrderThreshold));
        date = GUIUtils.createJTextField(textFieldsDim);
        inputPanel.add(GUIUtils.createInputRow("Date :                                                ", date));

        return  inputPanel;
    }

    private JPanel createButtonsPanel()
    {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));

        ordersGenerateButton = GUIUtils.createButton("Generate orders in a daytime interval", buttonsDim);
        buttonsPanel.add(ordersGenerateButton);
        productsOrderedMoreThan = GUIUtils.createButton("Generate products ordered more than", buttonsDim);
        buttonsPanel.add(productsOrderedMoreThan);
        clientsGenerateButton = GUIUtils.createButton("Generate clients that ordered and paid more than", buttonsDim);
        buttonsPanel.add(clientsGenerateButton);
        productsOrderedInADay = GUIUtils.createButton("Generate products in a specific day", buttonsDim);
        buttonsPanel.add(productsOrderedInADay);

        return  buttonsPanel;
    }
}
