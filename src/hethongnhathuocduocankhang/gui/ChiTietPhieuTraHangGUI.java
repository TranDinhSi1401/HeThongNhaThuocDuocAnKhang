/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import hethongnhathuocduocankhang.dao.PhieuTraHangDAO;
import hethongnhathuocduocankhang.entity.ChiTietPhieuTraHang;
import hethongnhathuocduocankhang.entity.PhieuTraHang;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ChiTietPhieuTraHangGUI extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JLabel lblMaPhieuTra;
    private JLabel lblTongTienHoan;

    public ChiTietPhieuTraHangGUI() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        // 1. Panel Thông tin chung
        JPanel pnlNorth = new JPanel(new GridLayout(2, 2, 5, 5));
        lblMaPhieuTra = new JLabel("Mã Phiếu Trả: ");
        lblTongTienHoan = new JLabel("Tổng Tiền Hoàn Trả: ");
        lblMaPhieuTra.setFont(new Font("Arial", Font.BOLD, 14));
        lblTongTienHoan.setFont(new Font("Arial", Font.BOLD, 14));
        
        pnlNorth.add(lblMaPhieuTra);
        pnlNorth.add(new JLabel(""));
        pnlNorth.add(lblTongTienHoan);
        this.add(pnlNorth, BorderLayout.NORTH);

        // 2. Panel Bảng (Danh sách chi tiết)
        String[] columnNames = {
            "Mã CTHD",
            "Sản Phẩm",
            "Đơn vị tính",
            "Số Lượng Trả",
            "Lý Do Trả",
            "Tình Trạng SP",
            "Giá Trị Hoàn",
            "Tiền Hoàn Trả"
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

    public void loadData(PhieuTraHang phieuTraHang) {
        if (phieuTraHang == null) return;

        // Cập nhật thông tin chung
        lblMaPhieuTra.setText("Mã Phiếu Trả: " + phieuTraHang.getMaPhieuTraHang());
        lblTongTienHoan.setText("Tổng Tiền Hoàn Trả: " + String.format("%,.0f VND", phieuTraHang.getTongTienHoanTra()));

        // Gọi DAO để lấy danh sách chi tiết
        ArrayList<ChiTietPhieuTraHang> dsChiTiet = PhieuTraHangDAO.getChiTietTheoMaPTH(phieuTraHang);

        // Đổ dữ liệu vào bảng
        model.setRowCount(0);
        for (ChiTietPhieuTraHang ct : dsChiTiet) {
            Object[] row = {
                ct.getChiTietHoaDon().getMaChiTietHoaDon(),
                ct.getChiTietHoaDon().getDonViTinh().getSanPham().getTen(), // Tên SP
                ct.getChiTietHoaDon().getDonViTinh().getTenDonVi(),
                ct.getSoLuong(),
                ct.getTruongHopDoiTra().toString(),
                ct.getTinhTrangSanPham().toString(),
                ct.getGiaTriHoanTra(),
                String.format("%,.0f", ct.getThanhTienHoanTra())
            };
            if(row[4].equals("HANG_LOI_DO_NHA_SAN_XUAT")){
                row[4] = "Hàng lỗi do nhà sản xuất";
            }
            else if(row[4].equals("DI_UNG_MAN_CAM")){
                row[4] = "Khách hàng dị ứng, mẫn cảm";
            }
            else{
                row[4] = "Nhu cầu khách hàng";
            }
            
            if(row[5].equals("HANG_NGUYEN_VEN")){
                row[5] = "Hàng nguyên vẹn";
            }
            else if(row[5].equals("HANG_KHONG_NGUYEN_VEN")){
                row[5] = "Hàng không nguyên vẹn";
            }
            else{
                row[5] = "Hàng đã sử dụng";
            }
            
            
            model.addRow(row);
        }
    }
}