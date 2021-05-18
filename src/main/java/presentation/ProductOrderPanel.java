package presentation;

import businessLayer.MenuItem;

import javax.swing.*;
import java.awt.*;

public class ProductOrderPanel extends ProductPanel {

    private static final Dimension buttonDim = new Dimension(150, 40);
    private static final Dimension textFieldDim = new Dimension(30, 25);
    private static final Dimension rigidAreaDim = new Dimension(0, 10);

    public int getQuantity() {
        return Integer.parseInt(quantity.getText());
    }
    private JTextField quantity;


    public ProductOrderPanel(MenuItem menuItem, ProductsPanel productsPanel)
    {
        super(menuItem, productsPanel);
        quantity.setText("1");
    }

    @Override
    protected JPanel createHeader(Object[] headerFields) {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));

        JPanel fieldsPanel = createFieldsPanel(headerFields);
        if (slicesCount > 1)
        {
            fieldsPanel.add(new JLabel("           "));
            fieldsPanel.add(new JLabel("Composited:"));
        }
        header.add(fieldsPanel);

        JPanel buttonsWrapper = new JPanel();
        ((FlowLayout)buttonsWrapper.getLayout()).setAlignment(FlowLayout.RIGHT);

        JPanel buttonsBox = new JPanel();
        buttonsBox.setLayout(new BoxLayout(buttonsBox, BoxLayout.Y_AXIS));
        buttonsBox.add(selectButton = GUIUtils.createButton("select", buttonDim));
        buttonsBox.add(Box.createRigidArea(rigidAreaDim));
        quantity = GUIUtils.createJTextField(textFieldDim);
        buttonsBox.add(GUIUtils.createInputRow("          Quantity: ", quantity));

        buttonsWrapper.add(buttonsBox);
        header.add(buttonsWrapper);

        return header;
    }

}
