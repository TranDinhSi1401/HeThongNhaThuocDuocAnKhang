/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import hethongnhathuocduocankhang.dao.LichSuCaLamDAO; // THAY ĐỔI
import hethongnhathuocduocankhang.entity.LichSuCaLam; // THAY ĐỔI
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

public class QuanLiLichSuCaLamGUI extends JPanel { // THAY ĐỔI

    // private JButton btnThem, btnXoa, btnSua; // BỎ
    private JTextField txtTimKiem;
    private JTable table;
    private JComboBox<String> cmbTieuChiTimKiem;
    // private JComboBox<String> cmbBoLoc; // BỎ
    private DefaultTableModel model;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public QuanLiLichSuCaLamGUI() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        // 1. TẠO PANEL NORTH
        JPanel pnlNorth = new JPanel();
        pnlNorth.setLayout(new BorderLayout());

        // 1.1. Panel Chức năng (LEFT - TRỐNG)
        JPanel pnlNorthLeft = new JPanel();
        pnlNorthLeft.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pnlNorthLeft.setBorder(new EmptyBorder(0, 0, 10, 0));
        pnlNorth.add(pnlNorthLeft, BorderLayout.WEST);
        
        // (Đã xóa các nút Thêm, Xóa, Sửa)

        // 1.2. Panel Tìm kiếm (RIGHT)
        JPanel pnlNorthRight = new JPanel();
        pnlNorthRight.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        // THAY ĐỔI TIÊU CHÍ TÌM KIẾM
        cmbTieuChiTimKiem = new JComboBox<>(new String[]{
            "Mã NV", 
            "Tên Nhân Viên", 
            "Mã Ca", 
            "Ngày Làm (yyyy-MM-dd)"
        });
        cmbTieuChiTimKiem.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbTieuChiTimKiem.setPreferredSize(new Dimension(180, 30));

        // (Đã xóa cmbBoLoc)

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
        // (Đã xóa Lọc theo)

        pnlNorth.add(pnlNorthRight, BorderLayout.EAST);

        this.add(pnlNorth, BorderLayout.NORTH);

        // 3. TẠO PANEL CENTER (Bảng dữ liệu)
        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));

        // THAY ĐỔI CỘT
        String[] columnNames = {"Mã NV", "Tên Nhân Viên", "Ngày Làm", "Mã Ca", "Giờ Vào", "Giờ Ra", "Ghi Chú"};
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

    // (Đã xóa setupTopButton)

    private void updateTable(ArrayList<LichSuCaLam> dsLS) { // THAY ĐỔI
        model.setRowCount(0);
        if (dsLS == null) {
            return;
        }
        for (LichSuCaLam ls : dsLS) { // THAY ĐỔI
            // Lấy tên từ đối tượng (DAO đã tải đầy đủ)
            String tenNV = (ls.getNhanVien() != null && ls.getNhanVien().getTen() != null)
                           ? ls.getNhanVien().getHoTenDem() + " " + ls.getNhanVien().getTen() 
                           : ls.getNhanVien().getMaNV(); 
            
            String maCa = (ls.getCaLam() != null) ? ls.getCaLam().getMaCa() : "N/A";
            
            String gioRa = (ls.getThoiGianRaCa() != null) 
                           ? ls.getThoiGianRaCa().format(timeFormatter) 
                           : "Chưa ra ca";

            Object[] row = {
                ls.getNhanVien().getMaNV(),
                tenNV,
                ls.getNgayLamViec().format(dateFormatter),
                maCa,
                ls.getThoiGianVaoCa().format(timeFormatter),
                gioRa,
                ls.getGhiChu()
            };
            model.addRow(row);
        }
    }

    private void updateTable() {
        ArrayList<LichSuCaLam> dsLS = LichSuCaLamDAO.getAllLichSuCaLam(); // THAY ĐỔI
        updateTable(dsLS);
    }

    private void addEvents() {
        // (Đã xóa sự kiện cho 3 nút Thêm Xóa Sửa)
        
        txtTimKiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyTimKiem();
            }
        });
        
        // (Đã xóa sự kiện cho cmbBoLoc)

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
                hienThiChiTietLichSuCaLam(e); // THAY ĐỔI
            }
        });
    }

    private void xuLyTimKiem() {
        String tuKhoa = txtTimKiem.getText().trim();
        String tieuChi = cmbTieuChiTimKiem.getSelectedItem().toString();

        ArrayList<LichSuCaLam> dsKetQua = new ArrayList<>(); // THAY ĐỔI

        if (tuKhoa.isEmpty()) {
            dsKetQua = LichSuCaLamDAO.getAllLichSuCaLam(); // THAY ĐỔI
        } else {
            try {
                switch (tieuChi) {
                    case "Mã NV":
                        dsKetQua = LichSuCaLamDAO.timTheoMaNV(tuKhoa); // THAY ĐỔI
                        break;
                    case "Tên Nhân Viên":
                        dsKetQua = LichSuCaLamDAO.timTheoTenNV(tuKhoa); // THAY ĐỔI
                        break;
                    case "Mã Ca":
                        dsKetQua = LichSuCaLamDAO.timTheoMaCa(tuKhoa); // THAY ĐỔI
                        break;
                    case "Ngày Làm (yyyy-MM-dd)":
                        LocalDate date = LocalDate.parse(tuKhoa); // Chuyển String sang LocalDate
                        dsKetQua = LichSuCaLamDAO.timTheoNgayLam(date); // THAY ĐỔI
                        break;
                }
            } catch (DateTimeParseException e) {
                 JOptionPane.showMessageDialog(this, "Ngày nhập phải đúng định dạng YYYY-MM-DD.", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
            }
        }
        updateTable(dsKetQua); 
    }

    // (Đã xóa xuLyLoc, xuLyThem, xuLyXoa, xuLySua)

    private void hienThiChiTietLichSuCaLam(MouseEvent e) { // THAY ĐỔI
        int selectRow = table.getSelectedRow();
        if (selectRow != -1) {
            
            // Không cần gọi DAO, chỉ lấy dữ liệu đã hiển thị trên bảng
            if (e.getClickCount() == 2) { // Double click
                ChiTietLichSuCaLamGUI pnlChiTiet = new ChiTietLichSuCaLamGUI(); // THAY ĐỔI
                JDialog dialog = new JDialog();
                dialog.setTitle("Thông tin chi tiết Ca Làm"); // THAY ĐỔI
                dialog.setModal(true);
                dialog.setResizable(false);
                dialog.setContentPane(pnlChiTiet);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                
                // Lấy dữ liệu String từ bảng
                String maNV = model.getValueAt(selectRow, 0).toString();
                String tenNV = model.getValueAt(selectRow, 1).toString();
                String ngayLam = model.getValueAt(selectRow, 2).toString();
                String maCa = model.getValueAt(selectRow, 3).toString();
                String gioVao = model.getValueAt(selectRow, 4).toString();
                String gioRa = model.getValueAt(selectRow, 5).toString();
                Object ghiChuObj = model.getValueAt(selectRow, 6);
                String ghiChu = (ghiChuObj != null) ? ghiChuObj.toString() : "";

                // Đổ dữ liệu
                pnlChiTiet.setTxtMaNhanVien(maNV);
                pnlChiTiet.setTxtTenNhanVien(tenNV);
                pnlChiTiet.setTxtNgayLam(ngayLam);
                pnlChiTiet.setTxtMaCa(maCa);
                pnlChiTiet.setTxtGioVao(gioVao);
                pnlChiTiet.setTxtGioRa(gioRa);
                pnlChiTiet.setTxtGhiChu(ghiChu);
                
                // Cấu hình nút Đóng
                pnlChiTiet.getBtnHuy().setVisible(false);
                pnlChiTiet.getBtnXacNhan().setVisible(true);
                pnlChiTiet.getBtnXacNhan().setText("Đóng");
                
                // Xóa listener cũ (nếu có) và thêm listener mới
                for (ActionListener al : pnlChiTiet.getBtnXacNhan().getActionListeners()) {
                    pnlChiTiet.getBtnXacNhan().removeActionListener(al);
                }
                pnlChiTiet.getBtnXacNhan().addActionListener(l -> dialog.dispose());

                dialog.setVisible(true);
            }
        }
    }
}