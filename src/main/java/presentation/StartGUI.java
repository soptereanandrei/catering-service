package presentation;

import javax.swing.*;
import java.awt.*;

public class StartGUI extends JFrame {
    private JPanel content;
    private JPanel selectGUIButtonsPanel;

    public JButton getAdministratorGUIButton() {
        return administratorGUIButton;
    }

    public JButton getEmployeeGUIButton() {
        return employeeGUIButton;
    }

    public JButton getClientGUIButton() {
        return clientGUIButton;
    }

    private JButton administratorGUIButton;
    private JButton employeeGUIButton;
    private JButton clientGUIButton;

    public StartGUI()
    {
        content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.X_AXIS));

        Dimension rigidAreaDim = new Dimension(175,400);
        content.add(Box.createRigidArea(rigidAreaDim));
        createSelectViewButtons();
        content.add(Box.createRigidArea(rigidAreaDim));

        add(content);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocation(600, 300);
        setVisible(true);
    }

    private void createSelectViewButtons()
    {
        Dimension d = new Dimension(500, 400);
        Dimension selectButtonsDimension = new Dimension(150, 50);

        selectGUIButtonsPanel = new JPanel();
        selectGUIButtonsPanel.setPreferredSize(d);
        selectGUIButtonsPanel.setMaximumSize(d);
        selectGUIButtonsPanel.setLayout(new BoxLayout(selectGUIButtonsPanel, BoxLayout.Y_AXIS));

        administratorGUIButton = GUIUtils.createButton("Administrator", selectButtonsDimension);
        employeeGUIButton = GUIUtils.createButton("Employee", selectButtonsDimension);
        clientGUIButton = GUIUtils.createButton("Client", selectButtonsDimension);

        selectGUIButtonsPanel.add(Box.createRigidArea(new Dimension(10, 50)));
        selectGUIButtonsPanel.add(administratorGUIButton);
        selectGUIButtonsPanel.add(Box.createRigidArea(new Dimension(10, 50)));
        selectGUIButtonsPanel.add(employeeGUIButton);
        selectGUIButtonsPanel.add(Box.createRigidArea(new Dimension(10, 50)));
        selectGUIButtonsPanel.add(clientGUIButton);

        content.add(selectGUIButtonsPanel);
    }
}
