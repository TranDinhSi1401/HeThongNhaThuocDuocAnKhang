/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import hethongnhathuocduocankhang.dao.ChiTietHoaDonDAO;
import hethongnhathuocduocankhang.entity.ChiTietHoaDon;
import hethongnhathuocduocankhang.entity.HoaDon;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ChiTietHoaDonGUI extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JLabel lblMaHoaDon;
    private JLabel lblTongTien;

    public ChiTietHoaDonGUI() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        // 1. Panel Thông tin chung (Hiển thị Mã HD và Tổng Tiền)
        JPanel pnlNorth = new JPanel(new GridLayout(2, 2, 5, 5));
        lblMaHoaDon = new JLabel("Mã Hóa Đơn: ");
        lblTongTien = new JLabel("Tổng Tiền Hóa Đơn: ");
        lblMaHoaDon.setFont(new Font("Arial", Font.BOLD, 14));
        lblTongTien.setFont(new Font("Arial", Font.BOLD, 14));
        
        pnlNorth.add(lblMaHoaDon);
        pnlNorth.add(new JLabel(""));
        pnlNorth.add(lblTongTien);
        this.add(pnlNorth, BorderLayout.NORTH);

        // 2. Panel Bảng (Danh sách chi tiết)
        String[] columnNames = {
            "Mã CTHD",
            "Sản Phẩm",
            "ĐVT",
            "Số Lượng",
            "Đơn Giá",
            "Giảm Giá",
            "Thành Tiền"
        };
        Object[][] data = {};

        model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho sửa
            }
        };

        table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(220, 220, 220));

        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void loadData(HoaDon hoaDon) {
        if (hoaDon == null) return;

        // Cập nhật thông tin chung
        lblMaHoaDon.setText("Mã Hóa Đơn: " + hoaDon.getMaHoaDon());
        lblTongTien.setText("Tổng Tiền Hóa Đơn: " + String.format("%,.0f VND", hoaDon.getTongTien()));

        // Gọi DAO để lấy danh sách chi tiết
        ArrayList<ChiTietHoaDon> dsCTHD = ChiTietHoaDonDAO.getChiTietHoaDonTheoMaHD(hoaDon);

        // Đổ dữ liệu vào bảng
        model.setRowCount(0);
        for (ChiTietHoaDon cthd : dsCTHD) {
            Object[] row = {
                cthd.getMaChiTietHoaDon(),
                cthd.getDonViTinh().getSanPham().getTen(), 
                cthd.getDonViTinh().getTenDonVi(),       
                cthd.getSoLuong(),
                String.format("%,.0f", cthd.getDonGia()),
                String.format("%.0f%%", cthd.getGiamGia() * 100), 
                String.format("%,.0f", cthd.getThanhTien())
            };
            model.addRow(row);
        }
    }
}