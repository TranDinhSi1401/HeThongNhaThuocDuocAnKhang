/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import hethongnhathuocduocankhang.dao.HoaDonDAO; 
import hethongnhathuocduocankhang.entity.HoaDon; 
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class QuanLiHoaDonGUI extends JPanel { 

    private JTextField txtTimKiem;
    private JTable table;
    private JComboBox<String> cmbTieuChiTimKiem;
    private JComboBox<String> cmbBoLoc;
    private DefaultTableModel model;

    public QuanLiHoaDonGUI() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        // 1. TẠO PANEL NORTH
        JPanel pnlNorth = new JPanel();
        pnlNorth.setLayout(new BorderLayout());

        // 1.1. Panel Chức năng (LEFT - BỎ CÁC NÚT)
        JPanel pnlNorthLeft = new JPanel();
        pnlNorthLeft.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pnlNorthLeft.setBorder(new EmptyBorder(0, 0, 10, 0));
        pnlNorth.add(pnlNorthLeft, BorderLayout.WEST);

        // 1.2. Panel Tìm kiếm và Lọc (Giữ lại)
        JPanel pnlNorthRight = new JPanel();
        pnlNorthRight.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        cmbTieuChiTimKiem = new JComboBox<>(new String[]{
            "Mã Hóa Đơn", 
            "Mã Nhân Viên", 
            "Mã Khách Hàng", 
            "SĐT Khách Hàng", 
            "Ngày lập (yyyy-MM-dd)"
        }); 
        cmbTieuChiTimKiem.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbTieuChiTimKiem.setPreferredSize(new Dimension(180, 30)); 

        cmbBoLoc = new JComboBox<>(new String[]{
            "Tất cả", 
            "Đã thanh toán", 
            "Chưa thanh toán", 
            "Tiền mặt", 
            "Chuyển khoản"
        }); 
        cmbBoLoc.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbBoLoc.setPreferredSize(new Dimension(150, 30));

        txtTimKiem = new JTextField(20);
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

        String[] columnNames = {
            "Mã HĐ", 
            "Nhân viên", 
            "Khách hàng", 
            "Ngày lập", 
            "Tổng tiền", 
            "Hình thức TT", 
            "Trạng thái"
        }; 
        Object[][] data = {};

        model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(220, 220, 220));
        table.getTableHeader().setReorderingAllowed(false);
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);

        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        this.add(pnlNorth, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);

        updateTable();
        addEvents();
    }

    private void updateTable(ArrayList<HoaDon> dsHD) { 
        model.setRowCount(0);
        if (dsHD == null) {
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (HoaDon hd : dsHD) { 
            String tenNV = (hd.getNhanVien() != null && hd.getNhanVien().getTen() != null)
                           ? hd.getNhanVien().getHoTenDem() + " " + hd.getNhanVien().getTen() 
                           : hd.getNhanVien().getMaNV(); 
                           
            String tenKH = (hd.getKhachHang() != null && hd.getKhachHang().getTen() != null)
                           ? hd.getKhachHang().getHoTenDem() + " " + hd.getKhachHang().getTen()
                           : hd.getKhachHang().getMaKH();
            
            Object[] row = {
                hd.getMaHoaDon(),
                tenNV,
                tenKH,
                hd.getNgayLapHoaDon().format(formatter),
                String.format("%,.0f VND", hd.getTongTien()), 
                hd.isChuyenKhoan() ? "Chuyển khoản" : "Tiền mặt",
                hd.isTrangThai() ? "Đã thanh toán" : "Chưa thanh toán"
            };
            model.addRow(row);
        }
    }

    private void updateTable() {
        ArrayList<HoaDon> dsHD = HoaDonDAO.getAllHoaDon(); 
        updateTable(dsHD);
    }

    private void addEvents() {
        txtTimKiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyTimKiem();
            }
        });

        cmbBoLoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyLoc();
            }
        });

        cmbTieuChiTimKiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtTimKiem.setText("");
                txtTimKiem.requestFocus();
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                hienThiChiTietHoaDon(e); 
            }
        });
    }

    private void xuLyTimKiem() {
        String tuKhoa = txtTimKiem.getText().trim();
        String tieuChi = cmbTieuChiTimKiem.getSelectedItem().toString();
        ArrayList<HoaDon> dsKetQua = new ArrayList<>(); 

        if (tuKhoa.isEmpty()) {
            dsKetQua = HoaDonDAO.getAllHoaDon(); 
        } else {
            try {
                switch (tieuChi) {
                    case "Mã Hóa Đơn":
                        HoaDon hd = HoaDonDAO.getHoaDonTheoMaHD(tuKhoa);
                        if (hd != null) {
                            dsKetQua.add(hd);
                        }
                        break;
                    case "Mã Nhân Viên":
                        dsKetQua = HoaDonDAO.timHDTheoMaNV(tuKhoa); 
                        break;
                    case "Mã Khách Hàng":
                        dsKetQua = HoaDonDAO.timHDTheoMaKH(tuKhoa); 
                        break;
                    case "SĐT Khách Hàng":
                        dsKetQua = HoaDonDAO.timHDTheoSDTKH(tuKhoa); 
                        break;
                    case "Ngày lập (yyyy-MM-dd)":
                        LocalDate date = LocalDate.parse(tuKhoa);
                        dsKetQua = HoaDonDAO.timHDTheoNgayLap(date); 
                        break;
                }
            } catch (DateTimeParseException e) {
                 JOptionPane.showMessageDialog(this, "Ngày nhập phải đúng định dạng YYYY-MM-DD.", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
            }
        }
        updateTable(dsKetQua); 
    }

    private void xuLyLoc() {
        String boLoc = cmbBoLoc.getSelectedItem().toString();
        ArrayList<HoaDon> dsDaLoc = new ArrayList<>();

        switch (boLoc) {
            case "Tất cả":
                dsDaLoc = HoaDonDAO.getAllHoaDon();
                break;
            case "Đã thanh toán":
                dsDaLoc = HoaDonDAO.timHDTheoTrangThai(true); 
                break;
            case "Chưa thanh toán":
                dsDaLoc = HoaDonDAO.timHDTheoTrangThai(false); 
                break;
            case "Tiền mặt":
                dsDaLoc = HoaDonDAO.timHDTheoHinhThuc(false); 
                break;
            case "Chuyển khoản":
                dsDaLoc = HoaDonDAO.timHDTheoHinhThuc(true); 
                break;
        }
        updateTable(dsDaLoc);
    }

    private void hienThiChiTietHoaDon(MouseEvent e) { 
        int selectRow = table.getSelectedRow();
        if (selectRow != -1) {
            String maHD = model.getValueAt(selectRow, 0).toString(); 
            HoaDon hdDaChon = HoaDonDAO.getHoaDonTheoMaHD(maHD); 

            if (hdDaChon != null && e.getClickCount() == 2) {
                
                // 1. Tạo GUI mới (chứa JTable)
                ChiTietHoaDonGUI pnlChiTiet = new ChiTietHoaDonGUI(); 
                
                // 2. Tải dữ liệu vào GUI
                pnlChiTiet.loadData(hdDaChon);

                // 3. Tạo JDialog để chứa GUI
                JDialog dialog = new JDialog();
                dialog.setTitle("Danh sách chi tiết Hóa Đơn: " + hdDaChon.getMaHoaDon()); 
                dialog.setModal(true);
                dialog.setResizable(true); // Cho phép thay đổi kích thước
                dialog.setContentPane(pnlChiTiet);
                dialog.setPreferredSize(new Dimension(800, 400));
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        }
    }
}