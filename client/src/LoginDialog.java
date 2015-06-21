import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextField textField1;
    private String ip;
    private ServerConnection connection;

    public LoginDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ip = textField1.getText();
                try {
                    connection = new ServerConnection(ip);
                    setVisible(false);
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Something went wrong");
                    ex.printStackTrace();
                }
            }
        });
        pack();
        setVisible(true);
    }

    public ServerConnection getServerConnection() {
        return connection;
    }
}
