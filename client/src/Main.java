import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Dmitry Tishchenko on 19.06.15.
 */
public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public static void createAndShowGUI() {
        LoginDialog d = new LoginDialog();
        if (d.getServerConnection() != null) {
            System.out.println("OK!");
        }
    }
}
