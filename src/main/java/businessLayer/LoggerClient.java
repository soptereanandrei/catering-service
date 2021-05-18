package businessLayer;

import dataLayer.Account;
import presentation.AuthenticationGUI;

import javax.swing.*;

public class LoggerClient extends Logger {
    public LoggerClient(JFrame GUI, AuthenticationGUI loginGUI)
    {
        super(GUI, loginGUI);
    }

    @Override
    protected Account createAccountObject(String username, String password)
    {
        return new Account(username, password, 0x00ff);
    }
}
