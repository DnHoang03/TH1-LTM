/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package th1.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import th1.controller.ClientControl;
import th1.entity.User;

/**
 *
 * @author Admin
 */
public class ClientView extends JFrame implements ActionListener {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public ClientView() {
        super("TCP Login MVC");
        txtUsername = new JTextField(15);
        txtPassword = new JPasswordField(15);
        txtPassword.setEchoChar('*');
        btnLogin = new JButton("Login");
        JPanel content = new JPanel();
        content.setLayout(new FlowLayout());
        content.add(new JLabel("Username:"));
        content.add(txtUsername);
        content.add(new JLabel("Password:"));
        content.add(txtPassword);
        content.add(btnLogin);
        this.setContentPane(content);
        this.pack();
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        btnLogin.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnLogin)) {
            char[] password = txtPassword.getPassword();
            String pass = new String(password);
            User model = new User(txtUsername.getText(),
                    pass);
            System.out.println("clicked");
            ClientControl clientCtr = new ClientControl();
            clientCtr.openConnection();
            clientCtr.sendData(model);
            String result = clientCtr.receiveData();
            if (result.equals("ok")) {
                showMessage("Login succesfully!");
                clientCtr.sendListSign();
                clientCtr.getUserList();
            } else {
                showMessage("Invalid username and/or password!");
            }
            clientCtr.closeConnection();
        }
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

}
