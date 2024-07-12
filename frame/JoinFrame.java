package frame;

import func.Join;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.regex.Pattern;

public class JoinFrame extends JFrame{
    private static final int WIDTH = 380;
    private static final int HEIGHT = 290;
    private JPanel f;
    private JLabel idLabel;
    private JTextField idText;
    private JLabel passLabel;
    private JPasswordField passText;
    private JLabel checkLabel;
    private JPasswordField checkText;
    private JButton joinButton;
    public JoinFrame(String name) {
        super(name);

        idLabel = new JLabel("ID:");
        idLabel.setBounds(20, 20, 80, 30);

        idText = new JTextField();
        idText.setBounds(100, 20, 190, 30);

        passLabel = new JLabel("Password:");
        passLabel.setBounds(20, 75, 80, 30);

        passText = new JPasswordField();
        passText.setBounds(100, 75, 190, 30);

        checkLabel = new JLabel("Check: ");
        checkLabel.setBounds(20, 130, 80, 30);

        checkText = new JPasswordField();
        checkText.setBounds(100, 130, 190, 30);

        joinButton = new JButton("Join");
        joinButton.setBounds(210, 185, 80, 30);

        super.add(idLabel);
        super.add(idText);

        super.add(passLabel);
        super.add(passText);

        super.add(checkLabel);
        super.add(checkText);

        super.add(joinButton);

        super.setSize(WIDTH, HEIGHT);
        super.setLayout(null);
        super.setResizable(false);
        super.setVisible(true);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        joinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean check = true;
                String id = idText.getText();
                String password = passText.getText();
                Join joined = new Join();


                if (!(Pattern.matches("^[a-zA-Z0-9]*$", id))) {
                    if (id.length() < 5) {
                        JOptionPane.showMessageDialog(f, "ID more than 5 characters");
                    } else if (id.length() > 13) {
                        JOptionPane.showMessageDialog(f, "ID less than 13 characters");
                    }
                    else {
                        JOptionPane.showMessageDialog(f, "It is invalid ID");
                    }
                    check = false;
                } else {
                    if (password.length() < 8) {
                        JOptionPane.showMessageDialog(f, "Password more than 8 characters");
                        check = false;
                    } else {
                        if (!joined.isPassword(password)) {
                            JOptionPane.showMessageDialog(f, "Password must be contain specific character");
                            check = false;
                        }
                    }
                }

                if (check) {
                    if (password.equals(checkText.getText())) {
                        String data = id + " " + password;
                        switch (joined.isJoin(data)) {
                            case 0:
                                JOptionPane.showMessageDialog(f, "ID already exists");
                                break;
                            case 1:
                                System.out.println("file input");
                                File file = new File("./information.txt");
                                try {
                                    FileWriter fw = new FileWriter(file, true);
                                    fw.write(data + "\n");
                                    fw.close();
                                } catch (IOException fe) {
                                    fe.printStackTrace();
                                }
                                new LoginFrame("Login");
                                setVisible(false);
                                break;
                        }
                    } else {
                        JOptionPane.showMessageDialog(f, "Password not equals");
                    }
                }
            }
        });
    }
}
