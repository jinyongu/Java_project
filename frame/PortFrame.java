package frame;

import func.Client;
import func.Server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PortFrame extends JFrame implements ActionListener {
    private static final int WIDTH = 380;
    private static final int HEIGHT = 250;

    private JLabel portLabel;
    private JLabel idLabel;
    private JTextField portIn;
    private JTextField idIn;
    private JButton check;
    private int port;
    private String id;
    private JPanel f;
    private JButton make;

    public PortFrame(String name) {
        super(name);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });

        portLabel = new JLabel("Port: ");
        portLabel.setBounds(20, 75, 80, 30);

        idLabel = new JLabel("Name: ");
        idLabel.setBounds(20, 20, 80, 30);

        portIn = new JTextField();
        portIn.setBounds(100, 75, 190, 30);

        idIn = new JTextField();
        idIn.setBounds(100, 20, 190, 30);

        check = new JButton("Join");
        check.setBounds(210, 120, 80, 30);

        make = new JButton("Make");
        make.setBounds(100, 120, 80, 30);

        super.add(portLabel);
        super.add(idLabel);
        super.add(portIn);
        super.add(idIn);
        super.add(check);
        super.add(make);

        check.addActionListener(this);
        make.addActionListener(this);

        super.setSize(WIDTH, HEIGHT);
        super.setLayout(null);
        super.setResizable(false);
        super.setVisible(true);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCmd = e.getActionCommand();

        if (actionCmd.equals("Join") || actionCmd.equals("Make")) {
            if (portIn.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Input your Port Number");
            } else if (idIn.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Input your nickname");
            } else {
                try {
                    port = Integer.parseInt(portIn.getText());
                    id = idIn.getText();

                    if (actionCmd.equals("Make")) {
                        new Thread(() -> new Server(port)).start();
                    }

                    Client c = new Client(id, port);

                    if (c.getIsConnected()) {
                        setVisible(false);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid port number. Please enter a valid integer.");
                }
            }
        }
    }
}
