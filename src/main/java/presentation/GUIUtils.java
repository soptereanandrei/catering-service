package presentation;

import javax.swing.*;
import java.awt.*;

public class GUIUtils {
    public static JButton createButton(String msg, Dimension d)
    {
        JButton b = new JButton(msg);
        b.setPreferredSize(d);
        b.setMaximumSize(d);
        //b.setAlignmentX(0.0f);
        return  b;
    }

    public static JTextField createJTextField(Dimension d)
    {
        JTextField f = new JTextField();
        f.setPreferredSize(d);
        f.setMaximumSize(d);
        return f;
    }

    public static JComboBox createJComboBox(Dimension d)
    {
        JComboBox<String> b = new JComboBox<>();
        b.setPreferredSize(d);
        b.setMaximumSize(d);
        return  b;
    }

    public static JPanel createInputRow(String name, JComponent textField)
    {
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));

        rowPanel.add(new JLabel(name));
        rowPanel.add(textField);

        rowPanel.setAlignmentX(0.0f);
        return rowPanel;
    }

    public static <T> JComboBox<T> createJComboBox(Dimension d, boolean editable)
    {
        JComboBox<T> comboBox = new JComboBox<>();
        if (d != null) {
            comboBox.setPreferredSize(d);
            comboBox.setMaximumSize(d);
        }
        comboBox.setEditable(editable);

        return comboBox;
    }

}
