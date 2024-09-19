/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package th1.controller;

/**
 *
 * @author Admin
 */
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import th1.entity.User;

public class ServerControl {

    private Connection con;
    private ServerSocket myServer;
    private int serverPort = 8888;

    public ServerControl() {
        getDBConnection("th1", "root", "root");
        openServer(serverPort);
        while (true) {
            listenning();
        }
    }

    private void getDBConnection(String dbName, String username,
            String password) {
        String dbUrl = "jdbc:mysql://localhost:3306/" + dbName;
        String dbClass = "com.mysql.cj.jdbc.Driver";
        try {
            Class.forName(dbClass);
            con = DriverManager.getConnection(dbUrl,
                    username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openServer(int portNumber) {
        try {
            myServer = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listenning() {
        try {
            Socket clientSocket = myServer.accept();
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            Object o = ois.readObject();
            if (o instanceof User) {
                User user = (User) o;
                if (checkUser(user)) {
                    String query = "Select * FROM users WHERE username ='"
                + user.getUserName()
                + "' AND password ='" + user.getPassword() + "'";
                    try {
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    List<String> names = new ArrayList<>();
                    while (rs.next()) {
                        names.add(rs.getString("username"));
                    }
                    oos.writeObject(names);
                    try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(oos))) {
                        for (String name : names) {
                            writer.write(name);
                            writer.newLine(); // Thêm một dòng mới cho mỗi người dùng
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    throw e;
                }
                    oos.writeObject("ok");
                    JOptionPane.showMessageDialog(null, "This is an information message.", "Information", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    oos.writeObject("false");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkUser(User user) throws Exception {
        String query = "Select * FROM users WHERE username ='"
                + user.getUserName()
                + "' AND password ='" + user.getPassword() + "'";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            throw e;
        }
        return false;
    }
}
