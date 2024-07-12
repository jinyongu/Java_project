package func;

import javax.swing.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.BindException;

public class Server extends Thread {
    static ArrayList<Socket> users = new ArrayList<Socket>();
    static ArrayList<String> userIds = new ArrayList<>();
    private Socket socket;
    private JPanel f;

    public Server(Socket socket) {
        this.socket = socket;
        users.add(socket);
        broadcastMessage(getClientId(socket) + " has connected");
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message;
            String id = getClientId(socket);
            String compareM = id + " has disconnected";
            while ((message = reader.readLine()) != null) {
                if (!message.equals(compareM)) {
                    broadcastMessage(id + " : " + message); // Include sender's name in the broadcasted message
                } else {
                    broadcastMessage(compareM);
                }
            }
        } catch (IOException e) {
            // Handle disconnection
            String id = getClientId(socket);
            users.remove(socket);
            broadcastMessage(id + " has disconnected");
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void broadcastMessage(String message) {
        for (Socket user : users) {
            try {
                OutputStream out = user.getOutputStream();
                PrintWriter writer = new PrintWriter(out, true);
                writer.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getClientId(Socket socket) {
        int index = users.indexOf(socket);
        return userIds.get(index);
    }

    public Server(int port) {
        try {
            ServerSocket ss = new ServerSocket(port);
            System.out.println("Server started on port " + port);
            while (true) {
                Socket user = ss.accept();
                BufferedReader reader = new BufferedReader(new InputStreamReader(user.getInputStream()));
                String id = reader.readLine(); // Read the client's ID upon connection
                System.out.println("Client connected: " + user.getLocalAddress() + " : " + user.getLocalPort() + " ID: " + id);
                userIds.add(id);
                Thread serverThread = new Server(user);
                serverThread.start();
            }
        } catch (BindException e) {
            System.out.println("Port " + port + " is already in use.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
