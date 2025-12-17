/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import hethongnhathuocduocankhang.dao.ChiTietPhieuDatDAO;
import hethongnhathuocduocankhang.entity.ChiTietPhieuDatHang;
import hethongnhathuocduocankhang.entity.PhieuDatHang;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ChiTietPhieuDatHangGUI extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JLabel lblMaPhieuDat;
    private JLabel lblTongTien;

    public ChiTietPhieuDatHangGUI() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Panel Thông tin chung (Hiển thị Mã PDH và Tổng Tiền)
        JPanel pnlNorth = new JPanel(new GridLayout(2, 2, 5, 5));
        lblMaPhieuDat = new JLabel("Mã Phiếu Đặt: ");
        lblTongTien = new JLabel("Tổng Tiền Phiếu Đặt: ");
        lblMaPhieuDat.setFont(new Font("Arial", Font.BOLD, 14));
        lblTongTien.setFont(new Font("Arial", Font.BOLD, 14));
        
        pnlNorth.add(lblMaPhieuDat);
        pnlNorth.add(new JLabel(""));
        pnlNorth.add(lblTongTien);
        this.add(pnlNorth, BorderLayout.NORTH);

        // Panel Bảng (Danh sách chi tiết)
        String[] columnNames = {
            "Mã SP",
            "Tên Sản Phẩm",
            "Số Lượng",
            "Đơn Giá",
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

    public void loadData(PhieuDatHang phieuDatHang) {
        if (phieuDatHang == null) return;

        lblMaPhieuDat.setText("Mã Phiếu Đặt: " + phieuDatHang.getMaPhieuDat());
        lblTongTien.setText("Tổng Tiền Phiếu Đặt: " + String.format("%,.0f VND", phieuDatHang.getTongTien()));

        ArrayList<ChiTietPhieuDatHang> dsChiTiet = ChiTietPhieuDatDAO.getChiTietTheoMaPDH(phieuDatHang.getMaPhieuDat());

        model.setRowCount(0);
        for (ChiTietPhieuDatHang ctpd : dsChiTiet) {
            Object[] row = {
                ctpd.getSanPham().getMaSP(),
                ctpd.getSanPham().getTen(),
                ctpd.getSoLuong(),
                String.format("%,.0f", ctpd.getDonGia()),
                String.format("%,.0f", ctpd.getThanhTien())
            };
            model.addRow(row);
        }
    }
}