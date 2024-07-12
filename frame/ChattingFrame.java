package frame;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ChattingFrame extends JFrame implements ActionListener {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 600;
    private JTextPane textPane;
    private JTextField text;
    private JButton enter;
    private JPanel p;
    private String message;
    private Socket socket;
    private PrintWriter writer;
    private String id;

    public ChattingFrame(String name, Socket socket, String id) {
        super(name);
        this.socket = socket;
        this.id = id;

        text = new JTextField(30);
        enter = new JButton("Enter");
        p = new JPanel(new BorderLayout());

        textPane = new JTextPane();
        textPane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textPane);

        p.add(text, BorderLayout.CENTER);
        p.add(enter, BorderLayout.EAST);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleDisconnection();
                dispose();
                System.exit(0);
            }
        });

        try {
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        text.addActionListener(this);
        enter.addActionListener(this);

        super.setSize(WIDTH, HEIGHT);
        super.add(scrollPane, BorderLayout.CENTER);
        super.add(p, BorderLayout.SOUTH);
        super.setResizable(false);
        super.setVisible(true);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void appendText(String text, boolean isSent, String sender) {
        StyledDocument doc = textPane.getStyledDocument();
        Style style = textPane.addStyle("Style", null);
        StyleConstants.setFontSize(style, 14);

        if (!isSent) {
            StyleConstants.setAlignment(style, StyleConstants.ALIGN_LEFT);
            StyleConstants.setForeground(style, Color.BLACK);
            text = sender + ": " + text;
        } else {
            StyleConstants.setAlignment(style, StyleConstants.ALIGN_RIGHT);
            StyleConstants.setForeground(style, Color.BLUE);
            text = "Me: " + text;
        }

        try {
            int length = doc.getLength();
            doc.insertString(length, text + "\n\n", style);
            doc.setParagraphAttributes(length, text.length(), style, false);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public void appendSystemMessage(String message) {
        StyledDocument doc = textPane.getStyledDocument();
        Style style = textPane.addStyle("SystemStyle", null);
        StyleConstants.setFontSize(style, 14);
        StyleConstants.setForeground(style, Color.RED);
        StyleConstants.setAlignment(style, StyleConstants.ALIGN_CENTER);

        try {
            int length = doc.getLength();
            doc.insertString(length, message + "\n", style);
            doc.setParagraphAttributes(length, message.length(), style, false);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void handleDisconnection() {
        try {
            if (writer != null) {
                writer.println(id + " has disconnected");
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (!text.getText().equals("")) {
            message = text.getText();
            writer.println(message);  // Send message without prefixing "me"
            text.setText("");
        }
    }
}
