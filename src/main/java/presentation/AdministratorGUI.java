package presentation;

import businessLayer.MenuItem;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.List;

public class AdministratorGUI extends JFrame {

    public JButton getAddProductButton() {
        return addProductButton;
    }

    public JButton getModifyProductButton() {
        return modifyProductButton;
    }

    public JButton getDeleteProductButton() {
        return deleteProductButton;
    }

    public JButton getCreateNewCompositedProductButton() {
        return createNewCompositedProductButton;
    }

    public JButton getImportButton() {
        return importButton;
    }

    public JButton getGenerateReportsButton() {
        return generateReportsButton;
    }

    private JButton addProductButton;
    private JButton modifyProductButton;
    private JButton deleteProductButton;
    private JButton createNewCompositedProductButton;
    private JButton importButton;
    private JButton generateReportsButton;

    private ProductsPanel productsPanel;

    public AdministratorGUI(HashSet<MenuItem> menuItems)
    {
        JPanel content = new JPanel();
        Dimension panelDim = new Dimension(1000, 600);
        content.setSize(panelDim);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        content.add(createButtonsPanel());
        content.add(Box.createRigidArea(new Dimension(0, 40)));
        content.add(productsPanel = new ProductsPanel(menuItems, panelDim));

        add(content);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(panelDim.width + 20, panelDim.height);
        setLocation(500, 300);
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

    public void updateMenu(HashSet<MenuItem> newMenu)
    {
        productsPanel.updateMenu(newMenu);
    }

    public Object[] getInputFields()
    {
        return productsPanel.getInputFields();
    }

    public List<MenuItem> getSelectedItems(){
        System.out.println("Selected items are:");
        productsPanel.getSelectedItems().forEach(System.out::println);
        return null;
    }
}
