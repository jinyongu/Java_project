package func;

import frame.ChattingFrame;

import javax.swing.*;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;

public class Client {
    private boolean isConnected = true;
    private ChattingFrame frame;
    private Socket socket;
    private String id;

    public Client(String id, int port) {
        this.id = id;
        try {
            socket = new Socket("127.0.0.1", port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(id); // Send the client's ID to the server upon connection
            frame = new ChattingFrame(id + "'s Chatting", socket, id);
            System.out.println("Server connection successful");
            Thread rThread = new Receiver(socket, frame, id);
            rThread.start();
        } catch (IOException e) {
            isConnected = false;
            if (e.getMessage().contains("Connection refused")) {
                JOptionPane.showMessageDialog(null, "Server is not available on port " + port);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to connect to server: " + e.getMessage());
            }
            System.out.println("Server connection failed");
        }
    }

    public boolean getIsConnected() {
        return isConnected;
    }

    private void handleDisconnection() {
        frame.appendSystemMessage(id + " has disconnected");
    }

    class Receiver extends Thread {
        private Socket socket;
        private ChattingFrame frame;
        private String id;

        public Receiver(Socket socket, ChattingFrame frame, String id) {
            this.socket = socket;
            this.frame = frame;
            this.id = id;
        }

        @Override
        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message;
                while (!socket.isClosed() && (message = reader.readLine()) != null) {
                    int colonIndex = message.indexOf(":");
                    if (colonIndex != -1) {
                        String sender = message.substring(0, colonIndex).trim();
                        String content = message.substring(colonIndex + 1).trim();
                        if (!sender.equals(id)) {
                            frame.appendText(content, false, sender);
                        } else {
                            frame.appendText(content, true, sender);
                        }
                    } else {
                        // Handle system messages (e.g., connection notice)
                        frame.appendSystemMessage(message);
                    }
                }
                handleDisconnection(); // Handle disconnection when the loop exits
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
