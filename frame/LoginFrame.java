package frame;

import func.Login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class LoginFrame extends JFrame{
    private static final int WIDTH = 380;
    private static final int HEIGHT = 250;
    private JPanel f;
    private JLabel idLabel;
    private JTextField idText;
    private JLabel passLabel;
    private JPasswordField passText;
    private JButton loginButton;
    private JButton joinButton;

    private JButton leaveButton;
    public LoginFrame(String name) {
        super(name);

        idLabel = new JLabel("ID:");
        idLabel.setBounds(20, 20, 80, 30);

        idText = new JTextField();
        idText.setBounds(100, 20, 190, 30);

        passLabel = new JLabel("Password:");
        passLabel.setBounds(20, 75, 80, 30);

        passText = new JPasswordField();
        passText.setBounds(100, 75, 190, 30);

        loginButton = new JButton("Login");
        loginButton.setBounds(100, 120, 80, 30);

        joinButton = new JButton("Join");
        joinButton.setBounds(210, 120, 80, 30);

        leaveButton = new JButton("Leave");
        leaveButton.setBounds(100, 165, 80, 30);

        super.add(idLabel);
        super.add(idText);

        super.add(passLabel);
        super.add(passText);

        super.add(loginButton);
        super.add(joinButton);
        super.add(leaveButton);

        super.setSize(WIDTH, HEIGHT);
        super.setLayout(null);
        super.setResizable(false);
        super.setVisible(true);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String data = idText.getText() + " " + passText.getText();
                Login log = new Login();
                switch(log.isLogin(data)) {
                    case 0:
                        new PortFrame("port");
                        setVisible(false);
                        break;
                    case 1:
                        JOptionPane.showMessageDialog(f, "Wrong password");
                        break;
                    case 2:
                        JOptionPane.showMessageDialog(f, "Do join first");
                        break;
                }
            }
        });

        joinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new JoinFrame("Join");
                setVisible(false);
            }
        });

        leaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LeaveFrame("Leave");
                setVisible(false);
            }
        });

    }
}
