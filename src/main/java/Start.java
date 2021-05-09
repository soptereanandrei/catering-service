import presentation.Controller;
import presentation.StartGUI;

public class Start {
    public static void main(String[] args)
    {
        StartGUI gui = new StartGUI();
        Controller controller = new Controller(gui);
    }
}
