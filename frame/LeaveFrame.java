package frame;


import func.Leave;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class LeaveFrame extends JFrame {
    private static final int WIDTH = 380;
    private static final int HEIGHT = 250;
    private JLabel idLabel;
    private JTextField idText;
    private JLabel passLabel;
    private JPasswordField passText;
    private JButton leaveButton;
    private JPanel f;
    private String info = "";
    private String str;

    public LeaveFrame(String name) {
        super(name);

        idLabel = new JLabel("ID:");
        idLabel.setBounds(20, 20, 80, 30);

        idText = new JTextField();
        idText.setBounds(100, 20, 190, 30);

        passLabel = new JLabel("Password:");
        passLabel.setBounds(20, 75, 80, 30);

        passText = new JPasswordField();
        passText.setBounds(100, 75, 190, 30);

        leaveButton = new JButton("Leave");
        leaveButton.setBounds(210, 165, 80, 30);

        super.add(idLabel);
        super.add(idText);

        super.add(passLabel);
        super.add(passText);

        super.add(leaveButton);

        super.setSize(WIDTH, HEIGHT);
        super.setLayout(null);
        super.setResizable(false);
        super.setVisible(true);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        leaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String data = idText.getText() + " " + passText.getText();
                Leave left = new Leave();
                switch (left.isLeave(data)) {
                    case 0:
                        try {
                            FileInputStream in = new FileInputStream("information.txt");
                            BufferedReader br = new BufferedReader(new InputStreamReader(in));
                            while ((str = br.readLine()) != null) {
                                if (!str.equals(data)) {
                                    info += str;
                                    info += "\n";
                                }
                            }
                            br.close();

                            File file = new File("./information.txt");
                            FileWriter fw = new FileWriter(file);
                            fw.write(info);
                            fw.close();
                        } catch(IOException fe) {
                            fe.printStackTrace();
                        }
                        new LoginFrame("Login");
                        setVisible(false);
                        break;
                    case 1:
                        JOptionPane.showMessageDialog(f, "Wrong Password");
                        break;
                    case 2:
                        JOptionPane.showMessageDialog(f, "There is no ID");
                        break;
                }
            }
        });
    }
}
