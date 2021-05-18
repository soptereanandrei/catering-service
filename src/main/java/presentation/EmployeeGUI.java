package presentation;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
public class EmployeeGUI extends JFrame implements PropertyChangeListener {

    private static final Dimension rigidDimension = new Dimension(0, 30);
    private JPanel content;
    private JScrollPane scrollPane;
    private JPanel orders;

    public EmployeeGUI()
    {
        content = new JPanel();
        Dimension panelDim = new Dimension(1000, 600);
        content.setSize(panelDim);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        orders = new JPanel();
        orders.setLayout(new BoxLayout(orders, BoxLayout.Y_AXIS));
        orders.setSize(panelDim);
        scrollPane = new JScrollPane(orders);
        scrollPane.setSize(panelDim);

        content.add(scrollPane);

        add(content);
        setSize(panelDim);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocation(500, 300);
        setVisible(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String bill = (String)evt.getNewValue();

        orders.add(createOrderPanel(bill));
        orders.add(Box.createRigidArea(rigidDimension));

        orders.updateUI();
        scrollPane.setViewportView(orders);
        scrollPane.updateUI();
        content.updateUI();
    }

    private JPanel createOrderPanel(String bill)
    {
        JPanel orderPanel = new JPanel();
        orderPanel.setLayout(new BoxLayout(orderPanel, BoxLayout.Y_AXIS));

        bill.lines().forEach(line -> orderPanel.add(new JLabel(line)));

        orderPanel.setBorder(BorderFactory.createMatteBorder(3, 3,3, 3, Color.CYAN));
        return orderPanel;
    }

}
