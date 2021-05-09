package presentation;

import javax.swing.*;
import java.awt.*;

public class AdministratorGUI extends JFrame {
    private JPanel content;

    public AdministratorGUI()
    {
        content = new JPanel();
        Dimension panelDim = new Dimension(1000, 1000);
        content.setSize(panelDim);
        //((FlowLayout)content.getLayout()).setAlignment(FlowLayout.CENTER);

        add(content);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(panelDim);
    }

}
