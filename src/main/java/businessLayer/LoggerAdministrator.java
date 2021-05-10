package businessLayer;

import dataLayer.Account;
import presentation.AuthenticationGUI;

import javax.swing.*;

public class LoggerAdministrator extends Logger {

    public LoggerAdministrator(JFrame GUI, AuthenticationGUI loginGUI)
    {
        super(GUI, loginGUI);
    }

    @Override
    protected Account createAccountObject(String username, String password)
    {
        return new Account(username, password, 0xffff);
    }
}
