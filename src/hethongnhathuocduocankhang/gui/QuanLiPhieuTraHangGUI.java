/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import hethongnhathuocduocankhang.dao.PhieuTraHangDAO; // THAY ĐỔI
import hethongnhathuocduocankhang.entity.PhieuTraHang; // THAY ĐỔI
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
 * GUI này quản lý danh sách Phiếu Trả Hàng (Master).
 * Không có chức năng Thêm/Xóa/Sửa.
 */
public class QuanLiPhieuTraHangGUI extends JPanel { 

    // private JButton btnThem, btnXoa, btnSua; // BỎ
    private JTextField txtTimKiem;
    private JTable table;
    private JComboBox<String> cmbTieuChiTimKiem;
    // private JComboBox<String> cmbBoLoc; // BỎ
    private DefaultTableModel model;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public QuanLiPhieuTraHangGUI() {
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

        // THAY ĐỔI TIÊU CHÍ TÌM KIẾM
        cmbTieuChiTimKiem = new JComboBox<>(new String[]{
            "Mã Phiếu Trả", 
            "Mã Hóa Đơn Gốc", 
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

        // THAY ĐỔI CỘT
        String[] columnNames = {"Mã Phiếu Trả", "Mã Hóa Đơn Gốc", "Nhân Viên Lập", "Ngày Lập", "Tổng Tiền Hoàn Trả"};
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

    private void updateTable(ArrayList<PhieuTraHang> dsPTH) { // THAY ĐỔI
        model.setRowCount(0);
        if (dsPTH == null) {
            return;
        }
        for (PhieuTraHang pth : dsPTH) { // THAY ĐỔI
            String tenNV = (pth.getNhanVien() != null && pth.getNhanVien().getTen() != null)
                           ? pth.getNhanVien().getHoTenDem() + " " + pth.getNhanVien().getTen() 
                           : pth.getNhanVien().getMaNV(); 

            Object[] row = {
                pth.getMaPhieuTraHang(),
                pth.getHoaDon().getMaHoaDon(),
                tenNV,
                pth.getNgayLapPhieuTraHang().format(formatter), 
                String.format("%,.0f VND", pth.getTongTienHoanTra()) 
            };
            model.addRow(row);
        }
    }

    private void updateTable() {
        ArrayList<PhieuTraHang> dsPTH = PhieuTraHangDAO.getAllPhieuTraHang(); // THAY ĐỔI
        updateTable(dsPTH);
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
                hienThiChiTietPhieuTraHang(e); // THAY ĐỔI
            }
        });
    }

    private void xuLyTimKiem() {
        String tuKhoa = txtTimKiem.getText().trim();
        String tieuChi = cmbTieuChiTimKiem.getSelectedItem().toString();

        ArrayList<PhieuTraHang> dsKetQua = new ArrayList<>(); // THAY ĐỔI

        if (tuKhoa.isEmpty()) {
            dsKetQua = PhieuTraHangDAO.getAllPhieuTraHang(); // THAY ĐỔI
        } else {
            try {
                switch (tieuChi) {
                    case "Mã Phiếu Trả":
                        PhieuTraHang pth = PhieuTraHangDAO.timPTHTheoMa(tuKhoa); // THAY ĐỔI
                        if (pth != null) {
                            dsKetQua.add(pth);
                        }
                        break;
                    case "Mã Hóa Đơn Gốc":
                        dsKetQua = PhieuTraHangDAO.timPTHTheoMaHD(tuKhoa); // THAY ĐỔI
                        break;
                    case "Mã Nhân Viên":
                        dsKetQua = PhieuTraHangDAO.timPTHTheoMaNV(tuKhoa); // THAY ĐỔI
                        break;
                    case "Ngày Lập (yyyy-MM-dd)":
                        LocalDate date = LocalDate.parse(tuKhoa); 
                        dsKetQua = PhieuTraHangDAO.timPTHTheoNgayLap(date); // THAY ĐỔI
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
     * Mở JDialog chứa JTable hiển thị danh sách ChiTietPhieuTraHang
     */
    private void hienThiChiTietPhieuTraHang(MouseEvent e) { // THAY ĐỔI
        int selectRow = table.getSelectedRow();
        if (selectRow != -1) {
            String maPTH = model.getValueAt(selectRow, 0).toString(); 
            PhieuTraHang pthDaChon = PhieuTraHangDAO.timPTHTheoMa(maPTH); 

            if (pthDaChon != null && e.getClickCount() == 2) { 
                
                // 1. Tạo GUI mới (chứa JTable chi tiết)
                ChiTietPhieuTraHangGUI pnlChiTiet = new ChiTietPhieuTraHangGUI(); 
                
                // 2. Tải dữ liệu vào GUI
                pnlChiTiet.loadData(pthDaChon); // Truyền đối tượng PTH đầy đủ

                // 3. Tạo JDialog để chứa GUI
                JDialog dialog = new JDialog();
                dialog.setTitle("Danh sách chi tiết Phiếu Trả Hàng: " + pthDaChon.getMaPhieuTraHang()); 
                dialog.setModal(true);
                dialog.setResizable(true); 
                dialog.setContentPane(pnlChiTiet);
                dialog.setPreferredSize(new Dimension(900, 400)); // Đặt kích thước
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        }
    }
}