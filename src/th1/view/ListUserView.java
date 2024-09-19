/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package th1.view;

/**
 *
 * @author Admin
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import th1.entity.User;

public class ListUserView extends JFrame {

    private JTable userTable;
    private DefaultTableModel tableModel;

    public ListUserView(List<String> users) {
        super("User List View");

        // Cài đặt dữ liệu cho bảng
        String[] columnNames = {"Username"};
        tableModel = new DefaultTableModel(columnNames, 0);
        userTable = new JTable(tableModel);

        // Thêm dữ liệu vào bảng
        for (String name : users) {
            Object[] row = {name};
            tableModel.addRow(row);
        }

        // Cài đặt giao diện
        JScrollPane scrollPane = new JScrollPane(userTable);
        this.add(scrollPane, BorderLayout.CENTER);

        // Cài đặt kích thước cửa sổ
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        // Tạo dữ liệu mẫu
        

        // Tạo và hiển thị giao diện
//        SwingUtilities.invokeLater(() -> new UserListView(users));
    }
}
