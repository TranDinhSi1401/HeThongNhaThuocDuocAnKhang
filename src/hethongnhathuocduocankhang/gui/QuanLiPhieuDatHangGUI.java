/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import hethongnhathuocduocankhang.dao.PhieuDatHangDAO; // Dùng DAO bạn cung cấp
import hethongnhathuocduocankhang.entity.PhieuDatHang; 
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

/**
 *
 * @author GIGABYTE
 * GUI này quản lý danh sách Phiếu Đặt Hàng (Master).
 * Không có chức năng Thêm/Xóa/Sửa.
 */
public class QuanLiPhieuDatHangGUI extends JPanel { 

    // private JButton btnThem, btnXoa, btnSua; // BỎ
    private JTextField txtTimKiem;
    private JTable table;
    private JComboBox<String> cmbTieuChiTimKiem;
    // private JComboBox<String> cmbBoLoc; // BỎ
    private DefaultTableModel model;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public QuanLiPhieuDatHangGUI() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel pnlNorth = new JPanel();
        pnlNorth.setLayout(new BorderLayout());

        JPanel pnlNorthLeft = new JPanel();
        pnlNorthLeft.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pnlNorthLeft.setBorder(new EmptyBorder(0, 0, 10, 0));
        pnlNorth.add(pnlNorthLeft, BorderLayout.WEST);
        
        JPanel pnlNorthRight = new JPanel();
        pnlNorthRight.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        cmbTieuChiTimKiem = new JComboBox<>(new String[]{
            "Mã Phiếu Đặt", 
            "Mã Nhà Cung Cấp", 
            "Mã Nhân Viên", 
            "Ngày Lập (yyyy-MM-dd)"
        });
        cmbTieuChiTimKiem.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbTieuChiTimKiem.setPreferredSize(new Dimension(180, 30));

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

        pnlNorth.add(pnlNorthRight, BorderLayout.EAST);
        this.add(pnlNorth, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));

        String[] columnNames = {"Mã Phiếu Đặt", "Nhà Cung Cấp", "Nhân Viên Lập", "Ngày Lập", "Tổng Tiền"};
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

    private void updateTable(ArrayList<PhieuDatHang> dsPDH) { 
        model.setRowCount(0);
        if (dsPDH == null) {
            return;
        }
        for (PhieuDatHang pdh : dsPDH) { 
            // Dùng DAO gộp (đã có NhanVienDAO, NhaCungCapDAO)
            String tenNCC = (pdh.getNhaCungCap() != null && pdh.getNhaCungCap().getTenNCC() != null)
                           ? pdh.getNhaCungCap().getTenNCC()
                           : pdh.getNhaCungCap().getMaNCC(); 
                           
            String tenNV = (pdh.getNhanVien() != null && pdh.getNhanVien().getTen() != null)
                           ? pdh.getNhanVien().getHoTenDem() + " " + pdh.getNhanVien().getTen() 
                           : pdh.getNhanVien().getMaNV(); 

            Object[] row = {
                pdh.getMaPhieuDat(),
                tenNCC,
                tenNV,
                pdh.getNgayLap().format(formatter), 
                String.format("%,.0f VND", pdh.getTongTien()) 
            };
            model.addRow(row);
        }
    }

    private void updateTable() {
        // Sử dụng hàm DAO bạn cung cấp
        ArrayList<PhieuDatHang> dsPDH = PhieuDatHangDAO.getAllPhieuDatHang(); 
        updateTable(dsPDH);
    }

    private void addEvents() {
        txtTimKiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyTimKiem();
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
                hienThiChiTietPhieuDatHang(e); 
            }
        });
    }

    private void xuLyTimKiem() {
        String tuKhoa = txtTimKiem.getText().trim();
        String tieuChi = cmbTieuChiTimKiem.getSelectedItem().toString();

        ArrayList<PhieuDatHang> dsKetQua = new ArrayList<>(); 

        if (tuKhoa.isEmpty()) {
            dsKetQua = PhieuDatHangDAO.getAllPhieuDatHang(); 
        } else {
            try {
                switch (tieuChi) {
                    case "Mã Phiếu Đặt":
                        PhieuDatHang pdh = PhieuDatHangDAO.timPDHTheoMa(tuKhoa); 
                        if (pdh != null) {
                            dsKetQua.add(pdh);
                        }
                        break;
                    case "Mã Nhà Cung Cấp":
                        dsKetQua = PhieuDatHangDAO.timPDHTheoMaNCC(tuKhoa); 
                        break;
                    case "Mã Nhân Viên":
                        dsKetQua = PhieuDatHangDAO.timPDHTheoMaNV(tuKhoa); 
                        break;
                    case "Ngày Lập (yyyy-MM-dd)":
                        LocalDate date = LocalDate.parse(tuKhoa); 
                        dsKetQua = PhieuDatHangDAO.timPDHTheoNgayLap(date); 
                        break;
                }
            } catch (DateTimeParseException e) {
                 JOptionPane.showMessageDialog(this, "Ngày nhập phải đúng định dạng YYYY-MM-DD.", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
            }
        }
        updateTable(dsKetQua); 
    }

    /**
     * SỬA LẠI HÀM NÀY
     * Mở JDialog chứa JTable hiển thị danh sách ChiTietPhieuDatHang
     */
    private void hienThiChiTietPhieuDatHang(MouseEvent e) { 
        int selectRow = table.getSelectedRow();
        if (selectRow != -1) {
            String maPDH = model.getValueAt(selectRow, 0).toString(); 
            // Dùng DAO của bạn để lấy PDH đầy đủ
            PhieuDatHang pdhDaChon = PhieuDatHangDAO.timPDHTheoMa(maPDH); 

            if (pdhDaChon != null && e.getClickCount() == 2) { 
                
                // 1. Tạo GUI mới (chứa JTable chi tiết)
                ChiTietPhieuDatHangGUI pnlChiTiet = new ChiTietPhieuDatHangGUI(); 
                
                // 2. Tải dữ liệu vào GUI
                pnlChiTiet.loadData(pdhDaChon); // Truyền đối tượng PDH đầy đủ

                // 3. Tạo JDialog để chứa GUI
                JDialog dialog = new JDialog();
                dialog.setTitle("Danh sách chi tiết Phiếu Đặt: " + pdhDaChon.getMaPhieuDat()); 
                dialog.setModal(true);
                dialog.setResizable(true); // Cho phép thay đổi kích thước
                dialog.setContentPane(pnlChiTiet);
                dialog.setPreferredSize(new Dimension(800, 400)); // Đặt kích thước
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        }
    }
}