package presentation;

import businessLayer.MenuItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductPanel extends JPanel {
    //private static final Dimension sliceDimension = new Dimension(1000, 500);
    private static final Dimension buttonsDim = new Dimension(100, 50);

    private final MenuItem menuItem;

    private final ProductsPanel productsPanel;
    protected JButton selectButton;
    protected int slicesCount;

    public ProductPanel(MenuItem menuItem, ProductsPanel productsPanel)
    {
        super();

        this.menuItem = menuItem;
        this.productsPanel = productsPanel;

        slicesCount = menuItem.getCompositeItemsCount();
        setLayout(new GridLayout(slicesCount, 1));
        //Dimension dim = new Dimension(sliceDimension.width, sliceDimension.height * slicesCount);
        //setSize(dim);

        List<Object[]> rows = menuItem.getRepresentation();
        add(createHeader(rows.get(0)));

        for (int i = 1; i < slicesCount; i++)
        {
            add(createFieldsPanel(rows.get(i)));
        }

        setBorder(BorderFactory.createMatteBorder(4,4,4,4,Color.CYAN));

        selectButton.addActionListener(new SelectButtonListener(this));
    }

    public void forceUnselect()
    {
        ((SelectButtonListener)selectButton.getActionListeners()[0]).forceUnselect();
    }

    protected JPanel createHeader(Object[] headerFields)
    {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));

        JPanel fieldsPanel = createFieldsPanel(headerFields);
        if (slicesCount > 1)
        {
            fieldsPanel.add(new JLabel("           "));
            fieldsPanel.add(new JLabel("Composited:"));
        }
        header.add(fieldsPanel);
        JPanel buttonWrapper = new JPanel();
        ((FlowLayout)buttonWrapper.getLayout()).setAlignment(FlowLayout.RIGHT);
        buttonWrapper.add(selectButton = GUIUtils.createButton("Select", buttonsDim));
        header.add(buttonWrapper);

        return header;
    }

    protected JPanel createFieldsPanel(Object[] fields)
    {
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));

        fieldsPanel.add(new JLabel("Title: " + fields[0]));
        fieldsPanel.add(new JLabel("Rating: " + fields[1]));
        fieldsPanel.add(new JLabel("Calories: " + fields[2]));
        fieldsPanel.add(new JLabel("Proteins: " + fields[3]));
        fieldsPanel.add(new JLabel("Fat: " + fields[4]));
        fieldsPanel.add(new JLabel("Sodium: " + fields[5]));
        fieldsPanel.add(new JLabel("Price: " + fields[6]));

        return fieldsPanel;
    }

    private static class SelectButtonListener implements ActionListener
    {
        private ProductPanel productPanel;
        private boolean selected = false;

        public SelectButtonListener(ProductPanel productPanel)
        {
            this.productPanel = productPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            selected = !selected;
            if (selected)
            {
                productPanel.selectButton.setText("Unselect");
                productPanel.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.red));
                productPanel.productsPanel.addSelectedItem(productPanel.menuItem);
            }
            else
            {
                productPanel.selectButton.setText("Select");
                productPanel.setBorder(BorderFactory.createMatteBorder(4,4,4,4,Color.CYAN));
                productPanel.productsPanel.removeSelectedItem(productPanel.menuItem);
            }
        }

        public void forceUnselect()
        {
            selected = false;
            productPanel.selectButton.setText("Select");
            productPanel.setBorder(BorderFactory.createMatteBorder(4,4,4,4,Color.CYAN));
        }
    }
}
