package presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

public class AdministratorGUI extends JFrame {

    private JButton importButton;
    private JButton addProductButton;
    private JButton modifyProductButton;
    private JButton deleteProductButton;
    private JButton createNewCompositedProductButton;
    private JButton generateReportsButton;

    private JComboBox<String> title;
    private JComboBox<Float> rating;
    private JComboBox<Float> calories;
    private JComboBox<Float> protein;
    private JComboBox<Float> fat;
    private JComboBox<Float> sodium;
    private JComboBox<Float> price;
    private JButton findButton;

    private JTable productsTabel;

    public AdministratorGUI()
    {
        JPanel content = new JPanel();
        Dimension panelDim = new Dimension(1000, 600);
        content.setSize(panelDim);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        content.add(createButtonsPanel());
        content.add(Box.createRigidArea(new Dimension(0, 40)));
        content.add(createInputPanel());
        content.add(createTablePanel());

        add(content);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(panelDim);
        setLocation(500, 500);

    }

    private JPanel createButtonsPanel()
    {
        JPanel buttonsPanel = new JPanel();
        Dimension panelDim = new Dimension(1000, 125);
        buttonsPanel.setMaximumSize(panelDim);
        buttonsPanel.setLayout(new GridLayout(2, 3, 25, 25));

        Dimension buttonsDim = new Dimension(200, 50);
        addProductButton = GUIUtils.createButton("Add product", buttonsDim);
        modifyProductButton = GUIUtils.createButton("Modify products", buttonsDim);
        deleteProductButton = GUIUtils.createButton("Delete products", buttonsDim);
        createNewCompositedProductButton = GUIUtils.createButton("Create composite product", buttonsDim);
        importButton = GUIUtils.createButton("Import", buttonsDim);
        generateReportsButton = GUIUtils.createButton("Generate reports", buttonsDim);

        buttonsPanel.add(addProductButton);
        buttonsPanel.add(modifyProductButton);
        buttonsPanel.add(deleteProductButton);
        buttonsPanel.add(createNewCompositedProductButton);
        buttonsPanel.add(importButton);
        buttonsPanel.add(generateReportsButton);

        return buttonsPanel;
    }

    private JPanel createInputPanel()
    {
        JPanel inputPanel = new JPanel();
        Dimension panelDim = new Dimension(1000, 50);
        inputPanel.setMaximumSize(panelDim);
        inputPanel.setLayout(new GridLayout(2, 8));

        String[] labelName = new String[] { "Title", "Rating", "Calories", "Protein", "Fat", "Sodium", "Price", "" };
        for (String s : labelName)
            inputPanel.add(new JLabel(s));

        inputPanel.add(title = GUIUtils.createJComboBox(null, true));
        inputPanel.add(rating = GUIUtils.createJComboBox(null, true));
        inputPanel.add(calories = GUIUtils.createJComboBox(null, true));
        inputPanel.add(protein = GUIUtils.createJComboBox(null, true));
        inputPanel.add(fat = GUIUtils.createJComboBox(null, true));
        inputPanel.add(sodium = GUIUtils.createJComboBox(null, true));
        inputPanel.add(price = GUIUtils.createJComboBox(null, true));
        findButton = new JButton("Find");
        inputPanel.add(findButton);

        return inputPanel;
    }

    private JPanel createTablePanel()
    {
        JPanel tablePanel = new JPanel();
        tablePanel.setPreferredSize(new Dimension(1000, 500));

        DefaultTableModel tableModel = new DefaultTableModel();
        String[] headerNames = new String[] { "Title", "Rating", "Calories", "Protein", "Fat", "Sodium", "Price", "Selected" };
        for (String s : headerNames)
            tableModel.addColumn(s);

        productsTabel = new JTable();
        productsTabel.setModel(tableModel);

        tablePanel.add(new JScrollPane(productsTabel));

        return tablePanel;
    }
}
