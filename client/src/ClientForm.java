import javax.swing.*;

/**
 * Created by Dmitry Tishchenko on 24.06.15.
 */
public class ClientForm extends JFrame {
    private JTable filesTable;
    private JButton button1;
    private JButton button2;
    private JProgressBar progressBar1;
    private JPanel panel;

    public ClientForm() {
        super("Torrent");
//        createUIComponents();
        setContentPane(panel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        Object[] headers = {"File", "Downloaded", "Available"};
        Object[][] data = {};
        filesTable = new JTable(data, headers);
    }
}
