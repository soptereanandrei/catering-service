package presentation;

import businessLayer.MenuItem;
import businessLayer.Pair;
import dataLayer.Account;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ClientGUI extends JFrame {

    public String getClientName() {
        return account.getUsername();
    }
    public void setAccount(Account account) {
        this.account = account;
    }
    private Account account;

    public JButton getOrderButton() {
        return orderButton;
    }
    private JButton orderButton;

    public ProductsPanel<ProductOrderPanel> getProductsPanel() {
        return productsPanel;
    }
    private final ProductsPanel<ProductOrderPanel> productsPanel;

    public ClientGUI(HashSet<MenuItem> menuItems)
    {
        JPanel content = new JPanel();
        Dimension panelDim = new Dimension(1000, 600);
        content.setSize(panelDim);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        Dimension buttonsDim = new Dimension(200, 50);
        content.add(orderButton = GUIUtils.createButton("Order", buttonsDim));
        content.add(productsPanel = new ProductsPanel<>(menuItems, ProductOrderPanel.class));

        add(content);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(panelDim.width + 20, panelDim.height);
        setLocation(500, 300);
    }

    public List<Pair<MenuItem, Integer>> getChooses()
    {
        List<MenuItem> selectedItems = productsPanel.getSelectedItems();

        List<Pair<MenuItem, Integer>> choices = new ArrayList<>(selectedItems.size());

        for (MenuItem selectedItem : selectedItems)
            choices.add(new Pair<>(selectedItem, productsPanel.getQuantity(selectedItem)));

        return choices;
    }

//    public static class Choice implements Serializable
//    {
//        public MenuItem item;
//        public int quantity;
//
//        public Choice(MenuItem item, int quantity)
//        {
//            this.item = item;
//            this.quantity = quantity;
//        }
//    }
}
