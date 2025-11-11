/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import hethongnhathuocduocankhang.dao.KhuyenMaiDAO;
import hethongnhathuocduocankhang.entity.KhuyenMai;
import hethongnhathuocduocankhang.entity.LoaiKhuyenMaiEnum;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class QuanLiKhuyenMaiGUI extends JPanel { // THAY ĐỔI

    private JButton btnThem, btnXoa, btnSua;
    private JTextField txtTimKiem;
    private JTable table;
    private JComboBox<String> cmbTieuChiTimKiem;
    private JComboBox<String> cmbBoLoc;
    private DefaultTableModel model;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public QuanLiKhuyenMaiGUI() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel pnlNorth = new JPanel();
        pnlNorth.setLayout(new BorderLayout());

        JPanel pnlNorthLeft = new JPanel();
        pnlNorthLeft.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pnlNorthLeft.setBorder(new EmptyBorder(0, 0, 10, 0));

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

        JPanel pnlNorthRight = new JPanel();
        pnlNorthRight.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        // THAY ĐỔI TIÊU CHÍ TÌM KIẾM
        cmbTieuChiTimKiem = new JComboBox<>(new String[]{"Mã khuyến mãi", "Mô tả"});
        cmbTieuChiTimKiem.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbTieuChiTimKiem.setPreferredSize(new Dimension(150, 30));

        // THAY ĐỔI BỘ LỌC
        cmbBoLoc = new JComboBox<>(new String[]{"Tất cả", "SO_LUONG", "MUA", "NHA_SAN_XUAT", "NGUNG_BAN"});
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

        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));

        // THAY ĐỔI CỘT
        String[] columnNames = {"Mã KM", "Mô tả", "Phần trăm", "Loại KM", "Bắt đầu", "Kết thúc", "SL Tối thiểu", "SL Tối đa"};
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

    private void setupTopButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setMargin(new Insets(5, 10, 5, 10));
        button.setPreferredSize(new Dimension(100, 30));
    }

    private void updateTable(ArrayList<KhuyenMai> dsKM) { // THAY ĐỔI
        model.setRowCount(0);
        if (dsKM == null) {
            return;
        }
        for (KhuyenMai km : dsKM) { // THAY ĐỔI
            Object[] row = {
                km.getMaKhuyenMai(),
                km.getMoTa(),
                String.format("%.2f%%", km.getPhanTram()), // Định dạng %
                km.getLoaiKhuyenMai().toString(),
                km.getNgayBatDau().format(formatter), // Định dạng ngày
                km.getNgayKetThuc().format(formatter), // Định dạng ngày
                km.getSoLuongToiThieu(),
                km.getSoLuongToiDa()
            };
            model.addRow(row);
        }
    }

    private void updateTable() {
        ArrayList<KhuyenMai> dsKM = KhuyenMaiDAO.getAllKhuyenMai(); // THAY ĐỔI
        updateTable(dsKM);
    }

    private void addEvents() {
        btnThem.addActionListener(e -> xuLyThem());
        btnXoa.addActionListener(e -> xuLyXoa());
        btnSua.addActionListener(e -> xuLySua());
        txtTimKiem.addActionListener(e -> xuLyTimKiem());
        cmbBoLoc.addActionListener(e -> xuLyLoc());

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
                hienThiChiTietKhuyenMai(e); // THAY ĐỔI
            }
        });
    }

    private void xuLyTimKiem() {
        String tuKhoa = txtTimKiem.getText().trim();
        String tieuChi = cmbTieuChiTimKiem.getSelectedItem().toString();

        ArrayList<KhuyenMai> dsKetQua = new ArrayList<>(); // THAY ĐỔI

        if (tuKhoa.isEmpty()) {
            dsKetQua = KhuyenMaiDAO.getAllKhuyenMai(); // THAY ĐỔI
        } else {
            switch (tieuChi) {
                case "Mã khuyến mãi": // THAY ĐỔI
                    KhuyenMai km = KhuyenMaiDAO.timKMTheoMa(tuKhoa); // THAY ĐỔI
                    if (km != null) {
                        dsKetQua.add(km);
                    }
                    break;
                case "Mô tả": // THAY ĐỔI
                    dsKetQua = KhuyenMaiDAO.timKMTheoMoTa(tuKhoa); // THAY ĐỔI
                    break;
            }
        }
        updateTable(dsKetQua);
    }

    private void xuLyLoc() {
        String boLoc = cmbBoLoc.getSelectedItem().toString();

        if (boLoc.equals("Tất cả")) {
            updateTable();
        } else {
            LoaiKhuyenMaiEnum loai = LoaiKhuyenMaiEnum.valueOf(boLoc); // Chuyển String sang Enum
            ArrayList<KhuyenMai> dsDaLoc = KhuyenMaiDAO.timKMTheoLoai(loai); // THAY ĐỔI
            updateTable(dsDaLoc);
        }
    }

    private void xuLyThem() {
        ThemKhuyenMaiGUI pnlThemKM = new ThemKhuyenMaiGUI(); // THAY ĐỔI
        JDialog dialog = new JDialog();
        dialog.setTitle("Thêm khuyến mãi mới"); // THAY ĐỔI
        dialog.setModal(true);
        dialog.setResizable(false);
        dialog.setContentPane(pnlThemKM);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        
        // SỬ DỤNG DAO ĐÃ SỬA LỖI
        int maKMCuoiCung = KhuyenMaiDAO.getMaKMCuoiCung();
        maKMCuoiCung++;
        String maKMNew = String.format("KM-%04d", maKMCuoiCung); // ĐỊNH DẠNG "KM-XXXX"
        pnlThemKM.setTxtMaKhuyenMai(maKMNew);
        
        dialog.setVisible(true);

        KhuyenMai kmNew = pnlThemKM.getKhuyenMaiMoi(); // THAY ĐỔI

        if (kmNew != null) {
            if (KhuyenMaiDAO.themKhuyenMai(kmNew)) { // THAY ĐỔI
                JOptionPane.showMessageDialog(this, "Thêm khuyến mãi thành công!"); // THAY ĐỔI
                updateTable();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm khuyến mãi thất bại."); // THAY ĐỔI
            }
        }
    }

    private void xuLyXoa() {
        int selectedRows[] = table.getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khuyến mãi cần xóa."); // THAY ĐỔI
            return;
        }

        String message;
        if (selectedRows.length == 1) {
            String tenKM = model.getValueAt(selectedRows[0], 1).toString(); // Cột 1 là Mô tả
            message = "Bạn có chắc muốn xóa khuyến mãi '" + tenKM + "' không?"; // THAY ĐỔI
        } else {
            message = "Bạn có chắc muốn xóa " + selectedRows.length + " khuyến mãi đã chọn không?"; // THAY ĐỔI
        }

        int confirm = JOptionPane.showConfirmDialog(this, message, "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            int soLuongXoaThanhCong = 0;
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                int row = selectedRows[i];
                String maKM = model.getValueAt(row, 0).toString(); // Cột 0 là Mã
                if (KhuyenMaiDAO.xoaKhuyenMai(maKM)) { // THAY ĐỔI
                    soLuongXoaThanhCong++;
                }
            }

            if (soLuongXoaThanhCong > 0) {
                JOptionPane.showMessageDialog(this, "Đã xóa thành công " + soLuongXoaThanhCong + " khuyến mãi."); // THAY ĐỔI
                updateTable();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa khuyến mãi thất bại (có thể do KM đã được áp dụng).", "Lỗi xóa", JOptionPane.ERROR_MESSAGE); // THAY ĐỔI
            }
        }
        table.clearSelection();
    }

    private void xuLySua() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khuyến mãi cần sửa."); // THAY ĐỔI
            return;
        }

        String maKM = model.getValueAt(selectedRow, 0).toString(); // THAY ĐỔI
        KhuyenMai kmCanSua = KhuyenMaiDAO.timKMTheoMa(maKM); // THAY ĐỔI

        if (kmCanSua == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy khuyến mãi để sửa."); // THAY ĐỔI
            return;
        }

        ThemKhuyenMaiGUI pnlThemKM = new ThemKhuyenMaiGUI(); // THAY ĐỔI
        JDialog dialog = new JDialog();
        dialog.setTitle("Sửa khuyến mãi"); // THAY ĐỔI
        dialog.setModal(true);
        dialog.setResizable(false);
        dialog.setContentPane(pnlThemKM);
        dialog.pack();
        dialog.setLocationRelativeTo(null);

        // Đổ dữ liệu cũ vào form
        pnlThemKM.setTxtMaKhuyenMai(kmCanSua.getMaKhuyenMai());
        pnlThemKM.setTxtMoTa(kmCanSua.getMoTa());
        pnlThemKM.setTxtPhanTram(kmCanSua.getPhanTram());
        pnlThemKM.setCmbLoaiKhuyenMai(kmCanSua.getLoaiKhuyenMai());
        pnlThemKM.setTxtNgayBatDau(kmCanSua.getNgayBatDau());
        pnlThemKM.setTxtNgayKetThuc(kmCanSua.getNgayKetThuc());
        pnlThemKM.setTxtSoLuongToiThieu(kmCanSua.getSoLuongToiThieu());
        pnlThemKM.setTxtSoLuongToiDa(kmCanSua.getSoLuongToiDa());

        dialog.setVisible(true);

        KhuyenMai kmNew = pnlThemKM.getKhuyenMaiMoi(); // THAY ĐỔI

        if (kmNew != null) {
            if (KhuyenMaiDAO.suaKhuyenMai(maKM, kmNew)) { // THAY ĐỔI
                JOptionPane.showMessageDialog(this, "Sửa khuyến mãi thành công!"); // THAY ĐỔI
                updateTable();
            } else {
                JOptionPane.showMessageDialog(this, "Sửa khuyến mãi thất bại."); // THAY ĐỔI
            }
        }
    }

    private void hienThiChiTietKhuyenMai(MouseEvent e) { // THAY ĐỔI
        int selectRow = table.getSelectedRow();
        if (selectRow != -1) {
            String maKM = model.getValueAt(selectRow, 0).toString(); // THAY ĐỔI
            KhuyenMai kmDaChon = KhuyenMaiDAO.timKMTheoMa(maKM); // THAY ĐỔI

            if (kmDaChon != null && e.getClickCount() == 2) {
                ThemKhuyenMaiGUI pnlThemKM = new ThemKhuyenMaiGUI(); // THAY ĐỔI
                JDialog dialog = new JDialog();
                dialog.setTitle("Thông tin chi tiết khuyến mãi"); // THAY ĐỔI
                dialog.setModal(true);
                dialog.setResizable(false);
                dialog.setContentPane(pnlThemKM);
                dialog.pack();
                dialog.setLocationRelativeTo(null);

                pnlThemKM.getBtnHuy().setVisible(false);
                pnlThemKM.getBtnXacNhan().setVisible(false);
                
                // Vô hiệu hóa chỉnh sửa
                pnlThemKM.getTxtMoTa().setEditable(false);
                pnlThemKM.getTxtPhanTram().setEditable(false);
                pnlThemKM.getCmbLoaiKhuyenMai().setEnabled(false);
                pnlThemKM.getTxtNgayBatDau().setEditable(false);
                pnlThemKM.getTxtNgayKetThuc().setEditable(false);
                pnlThemKM.getTxtSoLuongToiThieu().setEditable(false);
                // pnlThemKM.getTxtSoLuongToiDa().setEditable(false); // Cần getter cho field mới
                
                // Đổ dữ liệu
                pnlThemKM.setTxtMaKhuyenMai(kmDaChon.getMaKhuyenMai());
                pnlThemKM.setTxtMoTa(kmDaChon.getMoTa());
                pnlThemKM.setTxtPhanTram(kmDaChon.getPhanTram());
                pnlThemKM.setCmbLoaiKhuyenMai(kmDaChon.getLoaiKhuyenMai());
                pnlThemKM.setTxtNgayBatDau(kmDaChon.getNgayBatDau());
                pnlThemKM.setTxtNgayKetThuc(kmDaChon.getNgayKetThuc());
                pnlThemKM.setTxtSoLuongToiThieu(kmDaChon.getSoLuongToiThieu());
                pnlThemKM.setTxtSoLuongToiDa(kmDaChon.getSoLuongToiDa());

                dialog.setVisible(true);
            }
        }
    }
}