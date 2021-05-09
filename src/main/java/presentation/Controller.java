package presentation;

import businessLayer.Logger;
;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private StartGUI startGUI;

    public Controller(StartGUI startGUI)
    {
        this.startGUI = startGUI;
        startGUI.getAdministratorGUIButton().addActionListener(new AdministratorGUISelectListener());
    }

    public class AdministratorGUISelectListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            AdministratorGUI administratorGUI = new AdministratorGUI();
            AuthenticationGUI authenticationGUI = new AuthenticationGUI();
            authenticationGUI.getLoginButton().addActionListener(new Logger());
        }
    }
}
