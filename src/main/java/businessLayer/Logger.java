package businessLayer;

import dataLayer.Account;
import dataLayer.Serializer;
import presentation.AuthenticationGUI;
import presentation.MessageBox;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public abstract class Logger implements ActionListener {

    private JFrame GUI;
    private AuthenticationGUI authenticationGUI;

    public Logger(JFrame GUI, AuthenticationGUI loginGUI)
    {
        this.GUI = GUI;
        this.authenticationGUI = loginGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Serializer serializer = new Serializer();

            List<Object> accounts = serializer.load("Accounts.ser");

            Account loginAccount = createAccountObject(authenticationGUI.getUsername(), authenticationGUI.getPassword());
            Account existingAccount;
            boolean login = false;
            for (Object obj : accounts) {
                existingAccount = (Account) obj;
                if (existingAccount.equals(loginAccount))
                {
                    login = true;
                    break;
                }
            }

            if (login)
            {
                authenticationGUI.dispose();
                GUI.setVisible(true);
            }
            else
            {
                new MessageBox("Invalid username or password\nLogin failed");
            }

        }
        catch (Exception exp)
        {
            exp.printStackTrace();
        }
    }

    protected abstract Account createAccountObject(String username, String password);
}
