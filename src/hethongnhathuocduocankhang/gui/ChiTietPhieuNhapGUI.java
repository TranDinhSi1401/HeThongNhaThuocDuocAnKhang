/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import hethongnhathuocduocankhang.dao.ChiTietPhieuNhapDAO;
import hethongnhathuocduocankhang.entity.ChiTietPhieuNhap;
import hethongnhathuocduocankhang.entity.PhieuNhap;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ChiTietPhieuNhapGUI extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JLabel lblMaPhieuNhap;
    private JLabel lblTongTien;

    public ChiTietPhieuNhapGUI() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        // 1. Panel Thông tin chung
        JPanel pnlNorth = new JPanel(new GridLayout(2, 2, 5, 5));
        lblMaPhieuNhap = new JLabel("Mã Phiếu Nhập: ");
        lblTongTien = new JLabel("Tổng Tiền: ");
        lblMaPhieuNhap.setFont(new Font("Arial", Font.BOLD, 14));
        lblTongTien.setFont(new Font("Arial", Font.BOLD, 14));
        
        pnlNorth.add(lblMaPhieuNhap);
        pnlNorth.add(new JLabel(""));
        pnlNorth.add(lblTongTien);
        this.add(pnlNorth, BorderLayout.NORTH);

        // 2. Panel Bảng (Danh sách chi tiết)
        String[] columnNames = {
            "Mã Lô Sản Phẩm",
            "Sản Phẩm",
            "Nhà Cung Cấp",
            "Số Lượng",
            "Đơn Giá",
            "Thành Tiền",
            "Số Lượng Yêu Cầu",
            "Ghi Chú"
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

    public void loadData(PhieuNhap phieuNhap) {
        if (phieuNhap == null) return;

        // Cập nhật thông tin chung
        lblMaPhieuNhap.setText("Mã Phiếu Nhập: " + phieuNhap.getMaPhieuNhap());
        lblTongTien.setText("Tổng Tiền: " + String.format("%,.0f VND", phieuNhap.getTongTien()));

        // Gọi DAO để lấy danh sách chi tiết
        ArrayList<ChiTietPhieuNhap> dsChiTiet = ChiTietPhieuNhapDAO.getChiTietByMaPhieuNhap(phieuNhap.getMaPhieuNhap());

        // Đổ dữ liệu vào bảng
        model.setRowCount(0);
        for (ChiTietPhieuNhap ct : dsChiTiet) {
            Object[] row = {
                ct.getMaLoSanPham().getMaLoSanPham(),
                ct.getMaLoSanPham().getSanPham().getTen(),
                ct.getNcc().getTenNCC(),
                ct.getSoLuong(),
                String.format("%,.0f", ct.getDonGia()),
                String.format("%,.0f", ct.getThanhTien()),
                ct.getSoLuongYeuCau(),
                ct.getGhiChu()
            };
            model.addRow(row);
        }
    }
}