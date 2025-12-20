/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import hethongnhathuocduocankhang.dao.KhuyenMaiDAO;
import hethongnhathuocduocankhang.dao.NhanVienDAO;
import hethongnhathuocduocankhang.dao.TaiKhoanDAO;
import hethongnhathuocduocankhang.entity.KhuyenMai;
import hethongnhathuocduocankhang.entity.LoaiKhuyenMaiEnum;
import hethongnhathuocduocankhang.entity.NhanVien;
import hethongnhathuocduocankhang.entity.TaiKhoan;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class QuanLiKhuyenMaiGUI extends JPanel {

    private JButton btnThem, btnXoa, btnSua;
    private JTextField txtTimKiem;
    private JTable table;
    private JComboBox<String> cmbTieuChiTimKiem;
    private JComboBox<String> cmbBoLoc;
    private DefaultTableModel model;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private JLabel lblTongSoDong;
    private JLabel lblSoDongChon;

    public QuanLiKhuyenMaiGUI() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        // PANEL NORTH
        JPanel pnlNorth = new JPanel();
        pnlNorth.setLayout(new BorderLayout());

        // Chức năng
        JPanel pnlNorthLeft = new JPanel();
        pnlNorthLeft.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pnlNorthLeft.setBorder(new EmptyBorder(0, 0, 10, 0));

        btnThem = new JButton("Thêm - F6");
        btnXoa = new JButton("Xóa - Del");
        btnSua = new JButton("Sửa - F2");

        mapKeyToClickButton("F6", btnThem);
        mapKeyToClickButton("DELETE", btnXoa);
        mapKeyToClickButton("F2", btnSua);

        setupTopButton(btnThem, new Color(25, 118, 210)); // Xanh dương đậm
        setupTopButton(btnXoa, new Color(255, 51, 51));   // Đỏ tươi
        setupTopButton(btnSua, new Color(0, 203, 0));     // Xanh lá

        pnlNorthLeft.add(btnThem);
        pnlNorthLeft.add(btnXoa);
        pnlNorthLeft.add(btnSua);

        pnlNorth.add(pnlNorthLeft, BorderLayout.WEST);

        // Tìm kiếm & Lọc
        JPanel pnlNorthRight = new JPanel();
        pnlNorthRight.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        cmbTieuChiTimKiem = new JComboBox<>(new String[]{"Mã khuyến mãi", "Mô tả"});
        cmbTieuChiTimKiem.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbTieuChiTimKiem.setPreferredSize(new Dimension(150, 30));

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

        // PANEL CENTER (TABLE)
        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));

        String[] columnNames = {"STT", "Mã KM", "Mô tả", "Phần trăm", "Loại KM", "Bắt đầu", "Kết thúc", "SL Tối thiểu", "SL Tối đa"};
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

        // CẤU HÌNH KÍCH THƯỚC & CĂN CHỈNH CỘT
        TableColumnModel columnModel = table.getColumnModel();
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // 0. STT
        columnModel.getColumn(0).setPreferredWidth(40);
        columnModel.getColumn(0).setMaxWidth(40);
        columnModel.getColumn(0).setCellRenderer(centerRenderer);

        // 1. Mã KM
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(1).setMaxWidth(120);
        columnModel.getColumn(1).setCellRenderer(centerRenderer);

        // 2. Mô tả (Rộng)
        columnModel.getColumn(2).setPreferredWidth(200);

        // 3. Phần trăm
        columnModel.getColumn(3).setPreferredWidth(80);
        columnModel.getColumn(3).setCellRenderer(centerRenderer);

        // 4. Loại KM
        columnModel.getColumn(4).setPreferredWidth(100);
        columnModel.getColumn(4).setCellRenderer(centerRenderer);

        // 5 & 6. Ngày Bắt đầu - Kết thúc
        columnModel.getColumn(5).setPreferredWidth(120);
        columnModel.getColumn(5).setCellRenderer(centerRenderer);
        columnModel.getColumn(6).setPreferredWidth(120);
        columnModel.getColumn(6).setCellRenderer(centerRenderer);

        // 7 & 8. Số lượng
        columnModel.getColumn(7).setPreferredWidth(80);
        columnModel.getColumn(7).setCellRenderer(centerRenderer);
        columnModel.getColumn(8).setPreferredWidth(80);
        columnModel.getColumn(8).setCellRenderer(centerRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        this.add(centerPanel, BorderLayout.CENTER);

        // PANEL SOUTH (FOOTER)
        JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        pnlSouth.setBorder(new EmptyBorder(5, 0, 0, 0));

        Font fontFooter = new Font("Arial", Font.BOLD, 13);

        lblTongSoDong = new JLabel("Tổng số khuyến mãi: 0");
        lblTongSoDong.setFont(fontFooter);
        lblTongSoDong.setForeground(new Color(0, 102, 204));

        lblSoDongChon = new JLabel("Đang chọn: 0");
        lblSoDongChon.setFont(fontFooter);
        lblSoDongChon.setForeground(new Color(204, 0, 0));

        pnlSouth.add(lblTongSoDong);
        pnlSouth.add(new JSeparator(JSeparator.VERTICAL));
        pnlSouth.add(lblSoDongChon);

        this.add(pnlSouth, BorderLayout.SOUTH);

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

    private void updateTable(ArrayList<KhuyenMai> dsKM) {
        model.setRowCount(0);
        if (dsKM == null) {
            lblTongSoDong.setText("Tổng số khuyến mãi: 0");
            return;
        }
        int stt = 1;
        for (KhuyenMai km : dsKM) {
            Object[] row = {
                stt++,
                km.getMaKhuyenMai(),
                km.getMoTa(),
                String.format("%.2f%%", km.getPhanTram()),
                km.getLoaiKhuyenMai().toString(),
                km.getNgayBatDau().format(formatter),
                km.getNgayKetThuc().format(formatter),
                km.getSoLuongToiThieu(),
                km.getSoLuongToiDa()
            };
            model.addRow(row);
        }
        lblTongSoDong.setText("Tổng số khuyến mãi: " + dsKM.size());
    }

    private void updateTable() {
        ArrayList<KhuyenMai> dsKM = KhuyenMaiDAO.getAllKhuyenMai();
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
                hienThiChiTietKhuyenMai(e);
            }
        });

        // Sự kiện đếm số dòng chọn
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    lblSoDongChon.setText("Đang chọn: " + table.getSelectedRowCount());
                }
            }
        });
    }

    private void xuLyTimKiem() {
        String tuKhoa = txtTimKiem.getText().trim();
        String tieuChi = cmbTieuChiTimKiem.getSelectedItem().toString();
        ArrayList<KhuyenMai> dsKetQua = new ArrayList<>();

        if (tuKhoa.isEmpty()) {
            dsKetQua = KhuyenMaiDAO.getAllKhuyenMai();
        } else {
            switch (tieuChi) {
                case "Mã khuyến mãi":
                    KhuyenMai km = KhuyenMaiDAO.timKMTheoMa(tuKhoa);
                    if (km != null) {
                        dsKetQua.add(km);
                    }
                    break;
                case "Mô tả":
                    dsKetQua = KhuyenMaiDAO.timKMTheoMoTa(tuKhoa);
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
            try {
                LoaiKhuyenMaiEnum loai = LoaiKhuyenMaiEnum.valueOf(boLoc);
                ArrayList<KhuyenMai> dsDaLoc = KhuyenMaiDAO.timKMTheoLoai(loai);
                updateTable(dsDaLoc);
            } catch (IllegalArgumentException e) {
                updateTable();
            }
        }
    }

    private void xuLyThem() {
        ThemKhuyenMaiGUI pnlThemKM = new ThemKhuyenMaiGUI();
        JDialog dialog = new JDialog();
        dialog.setTitle("Thêm khuyến mãi mới");
        dialog.setModal(true);
        dialog.setResizable(false);
        dialog.setContentPane(pnlThemKM);
        dialog.pack();
        dialog.setLocationRelativeTo(null);

        int maKMCuoiCung = KhuyenMaiDAO.getMaKMCuoiCung();
        maKMCuoiCung++;
        String maKMNew = String.format("KM-%04d", maKMCuoiCung);
        pnlThemKM.setTxtMaKhuyenMai(maKMNew);

        boolean isSuccess = false;

        while (!isSuccess) {
            dialog.setVisible(true);

            KhuyenMai kmNew = pnlThemKM.getKhuyenMaiMoi();

            if (kmNew == null) {
                break;
            }

            if (KhuyenMaiDAO.themKhuyenMai(kmNew)) {
                JOptionPane.showMessageDialog(this, "Thêm khuyến mãi thành công!");
                updateTable();
                isSuccess = true;
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Thêm khuyến mãi thất bại.",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void xuLyXoa() {
        int selectedRows[] = table.getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khuyến mãi cần xóa.");
            return;
        }

        String message;
        if (selectedRows.length == 1) {
            String tenKM = model.getValueAt(selectedRows[0], 2).toString();
            message = "Bạn có chắc muốn xóa khuyến mãi '" + tenKM + "' không?";
        } else {
            message = "Bạn có chắc muốn xóa " + selectedRows.length + " khuyến mãi đã chọn không?";
        }

        int confirm = JOptionPane.showConfirmDialog(this, message, "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            int soLuongXoaThanhCong = 0;
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                int row = selectedRows[i];
                String maKM = model.getValueAt(row, 1).toString();
                if (KhuyenMaiDAO.xoaKhuyenMai(maKM)) {
                    soLuongXoaThanhCong++;
                }
            }

            if (soLuongXoaThanhCong > 0) {
                JOptionPane.showMessageDialog(this, "Đã xóa thành công " + soLuongXoaThanhCong + " khuyến mãi.");
                updateTable();
                lblSoDongChon.setText("Đang chọn: 0");
            } else {
                JOptionPane.showMessageDialog(this, "Xóa khuyến mãi thất bại (có thể do KM đã được áp dụng).", "Lỗi xóa", JOptionPane.ERROR_MESSAGE);
            }
        }
        table.clearSelection();
    }

    private void xuLySua() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khuyến mãi cần sửa.");
            return;
        }

        String maKM = model.getValueAt(selectedRow, 1).toString();
        KhuyenMai kmCanSua = KhuyenMaiDAO.timKMTheoMa(maKM);

        if (kmCanSua == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy khuyến mãi để sửa.");
            return;
        }

        ThemKhuyenMaiGUI pnlThemKM = new ThemKhuyenMaiGUI();
        JDialog dialog = new JDialog();
        dialog.setTitle("Sửa khuyến mãi");
        dialog.setModal(true);
        dialog.setResizable(false);
        dialog.setContentPane(pnlThemKM);
        dialog.pack();
        dialog.setLocationRelativeTo(null);

        pnlThemKM.setTxtMaKhuyenMai(kmCanSua.getMaKhuyenMai());
        pnlThemKM.setTxtMoTa(kmCanSua.getMoTa());
        pnlThemKM.setTxtPhanTram(kmCanSua.getPhanTram());
        pnlThemKM.setCmbLoaiKhuyenMai(kmCanSua.getLoaiKhuyenMai());
        pnlThemKM.setTxtNgayBatDau(kmCanSua.getNgayBatDau());
        pnlThemKM.setTxtNgayKetThuc(kmCanSua.getNgayKetThuc());
        pnlThemKM.setTxtSoLuongToiThieu(kmCanSua.getSoLuongToiThieu());
        pnlThemKM.setTxtSoLuongToiDa(kmCanSua.getSoLuongToiDa());

        boolean isSuccess = false;

        while (!isSuccess) {
            dialog.setVisible(true);

            KhuyenMai kmNew = pnlThemKM.getKhuyenMaiMoi();

            if (kmNew == null) {
                break;
            }

            if (KhuyenMaiDAO.suaKhuyenMai(maKM, kmNew)) {
                JOptionPane.showMessageDialog(this, "Sửa khuyến mãi thành công!");
                updateTable();
                isSuccess = true;
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Sửa khuyến mãi thất bại.",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void hienThiChiTietKhuyenMai(MouseEvent e) {
        int selectRow = table.getSelectedRow();
        if (selectRow != -1) {
            String maKM = model.getValueAt(selectRow, 1).toString();
            KhuyenMai kmDaChon = KhuyenMaiDAO.timKMTheoMa(maKM);

            if (kmDaChon != null && e.getClickCount() == 2) {
                ThemKhuyenMaiGUI pnlThemKM = new ThemKhuyenMaiGUI();
                JDialog dialog = new JDialog();
                dialog.setTitle("Thông tin chi tiết khuyến mãi");
                dialog.setModal(true);
                dialog.setResizable(false);
                dialog.setContentPane(pnlThemKM);
                dialog.pack();
                dialog.setLocationRelativeTo(null);

                pnlThemKM.getBtnHuy().setVisible(false);
                pnlThemKM.getBtnXacNhan().setVisible(false);

                pnlThemKM.getTxtMoTa().setEditable(false);
                pnlThemKM.getTxtPhanTram().setEditable(false);
                pnlThemKM.getCmbLoaiKhuyenMai().setEnabled(false);
                pnlThemKM.getTxtNgayBatDau().setEditable(false);
                pnlThemKM.getTxtNgayKetThuc().setEditable(false);
                pnlThemKM.getTxtSoLuongToiThieu().setEditable(false);
                pnlThemKM.getTxtSoLuongToiDa().setEditable(false);

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

    private void mapKeyToFocus(String key, JComponent component) {
        InputMap im = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = component.getActionMap();

        im.put(KeyStroke.getKeyStroke(key), "focus_" + key);
        am.put("focus_" + key, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                component.requestFocus();
                if (component instanceof JTextField jTextField) {
                    jTextField.selectAll();
                }
            }
        });
    }

    private void mapKeyToClickButton(String key, AbstractButton button) {
        InputMap im = button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = button.getActionMap();

        im.put(KeyStroke.getKeyStroke(key), "click_" + key);
        am.put("click_" + key, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.doClick(); // kích hoạt sự kiện button
            }
        });
    }
}
