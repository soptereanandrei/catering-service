package presentation;

import javax.swing.*;
import java.awt.*;

public class MessageBox extends JFrame {
    private JPanel content;

    public MessageBox(String message)
    {
        super();

        setSize(400, 200);
        setLocation(500, 500);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        content = new JPanel();
        JTextArea messageArea = new JTextArea();
        messageArea.setText(message);

        content.add(messageArea);

        add(content);
        setVisible(true);
    }

}
