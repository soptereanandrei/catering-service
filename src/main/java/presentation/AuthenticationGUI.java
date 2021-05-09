package presentation;

import javax.swing.*;
import java.awt.*;

public class AuthenticationGUI extends JFrame {
    private JPanel content;
    private JTextField username;
    private JPasswordField password;
    private JButton loginButton;
    public JButton getLoginButton() {
        return loginButton;
    }

    private final Dimension frameSize = new Dimension(500, 500);

    public AuthenticationGUI()
    {
        super();
        setSize(frameSize);
        setLocation(600, 300);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.X_AXIS));

        Dimension textFieldDim = new Dimension(200, 50);
        username = GUIUtils.createJTextField(textFieldDim);
        password = new JPasswordField();
        password.setPreferredSize(textFieldDim);
        password.setMaximumSize(textFieldDim);
        password.setEchoChar('*');

        JPanel usernameRow = GUIUtils.createInputRow("Username:   ", username);
        JPanel passwordRow = GUIUtils.createInputRow("Password:   ", password);
        loginButton = GUIUtils.createButton("Login", new Dimension(textFieldDim.width + 80, textFieldDim.height));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        Dimension rigidAreaDim = new Dimension(0, 30);
        inputPanel.add(Box.createRigidArea(rigidAreaDim));
        inputPanel.add(usernameRow);
        inputPanel.add(Box.createRigidArea(rigidAreaDim));
        inputPanel.add(passwordRow);
        inputPanel.add(Box.createRigidArea(rigidAreaDim));
        inputPanel.add(loginButton);

        Dimension rigidAreaDim2 = new Dimension(100, 0);
        content.add(Box.createRigidArea(rigidAreaDim2));
        content.add(inputPanel);
        content.add(Box.createRigidArea(rigidAreaDim));

        add(content);
        setVisible(true);
    }
}
