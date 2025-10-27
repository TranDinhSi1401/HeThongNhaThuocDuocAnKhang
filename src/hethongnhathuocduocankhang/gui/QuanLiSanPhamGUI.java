/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class QuanLiSanPhamGUI extends JPanel {

    private JButton btnThem, btnXoa, btnSua;
    private JTextField txtTimKiem;
    private JTable productTable;
    private JComboBox<String> cmbTieuChiTimKiem;
    private JComboBox<String> cmbBoLoc;
    private DefaultTableModel model;

    public QuanLiSanPhamGUI() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        // 1. TẠO PANEL NORTH
        JPanel pnlNorth = new JPanel();
        pnlNorth.setLayout(new BorderLayout());

        // 1.1. Panel Chức năng (LEFT - Thêm, Xóa, Sửa)
        JPanel pnlNorthLeft = new JPanel();
        pnlNorthLeft.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pnlNorthLeft.setBorder(new EmptyBorder(0, 0, 10, 0));

        // 1.1.1. Các nút chức năng (Thêm, Xóa, Xem chi tiết, Xuất Excel, Nhập Excel)
        btnThem = new JButton("Thêm");
        btnXoa = new JButton("Xóa");
        btnSua = new JButton("Sửa");
        
        setupTopButton(btnThem, new Color(50, 150, 250));
        setupTopButton(btnXoa, new Color(250, 100, 100));
        setupTopButton(btnSua, Color.LIGHT_GRAY);
        
        pnlNorthLeft.add(btnThem);
        pnlNorthLeft.add(btnXoa);
        pnlNorthLeft.add(btnSua);
        
        pnlNorth.add(pnlNorthLeft, BorderLayout.WEST);

        // 1.2. Panel Tìm kiếm và Lọc
        JPanel pnlNorthRight = new JPanel();
        pnlNorthRight.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        
        //Tiêu chí tìm kiếm
        cmbTieuChiTimKiem = new JComboBox<>(new String[]{"Mã sản phẩm", "Tên sản phẩm", "Nhà cung cấp"});
        cmbTieuChiTimKiem.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbTieuChiTimKiem.setPreferredSize(new Dimension(150, 30));
        
        // ComboBox Bộ lọc
        cmbBoLoc = new JComboBox<>(new String[]{"Tất cả", "Thuốc", "Thực phẩm chức năng"});
        cmbBoLoc.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbBoLoc.setPreferredSize(new Dimension(150, 30));
        
        // Thanh tìm kiếm
        txtTimKiem = new JTextField(35);
        txtTimKiem.setFont(new Font("Arial", Font.PLAIN, 14));
        txtTimKiem.setPreferredSize(new Dimension(200, 30));
        txtTimKiem.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            new EmptyBorder(5, 5, 5, 5)
        ));

        JPanel pnlTimKiem = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        pnlTimKiem.add(new JLabel("Tìm kiếm"));
        pnlTimKiem.add(txtTimKiem);
        
        pnlNorthRight.add(new JLabel("Tìm theo"));
        pnlNorthRight.add(cmbTieuChiTimKiem);
        pnlNorthRight.add(pnlTimKiem);
        
        pnlNorthRight.add(new JLabel("Lọc theo"));
        pnlNorthRight.add(cmbBoLoc);
        
        pnlNorth.add(pnlNorthRight, BorderLayout.EAST);

        this.add(pnlNorth, BorderLayout.NORTH);

        // 3. TẠO PANEL CENTER (Bảng dữ liệu)
        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));

        // 3.1. Bảng dữ liệu
        String[] columnNames = {"Mã SP", "Tên SP", "Số lô", "Ngày sản xuất", "Hạn sử dụng", "Thành phần", "Mô tả", "Loại sản phẩm"};
        Object[][] data = {
            {"", "", "", "", "", "", "", ""}, 
            {"", "", "", "", "", "", "", ""}, 
            {"", "", "", "", "", "", "", ""}, 
            {"", "", "", "", "", "", "", ""}, 
            {"", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", ""}, 
            {"", "", "", "", "", "", "", ""}, 
            {"", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", ""}, 
            {"", "", "", "", "", "", "", ""},
        };

        model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Ngăn chỉnh sửa trực tiếp trong bảng
            }
        };

        productTable = new JTable(model);
        productTable.setRowHeight(25);
        productTable.setFont(new Font("Arial", Font.PLAIN, 12));
        
        // Thiết lập Header
        productTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        productTable.getTableHeader().setBackground(new Color(220, 220, 220));
        productTable.getTableHeader().setReorderingAllowed(false); 
        
        productTable.setShowGrid(true);
        productTable.setGridColor(Color.LIGHT_GRAY); 

        JScrollPane scrollPane = new JScrollPane(productTable);
        
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // 4. THÊM CÁC PANEL VÀO PANEL CHÍNH
        this.add(pnlNorth, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
    }

    private void setupTopButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.PLAIN, 14)); 
        button.setMargin(new Insets(5, 10, 5, 10)); 
        button.setPreferredSize(new Dimension(100, 30));
    }
}