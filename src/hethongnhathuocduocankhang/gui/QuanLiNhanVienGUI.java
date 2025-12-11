/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import hethongnhathuocduocankhang.dao.NhanVienDAO;
import hethongnhathuocduocankhang.dao.TaiKhoanDAO;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class QuanLiNhanVienGUI extends JPanel {

    private JButton btnThem, btnXoa, btnSua;
    private JTextField txtTimKiem;
    private JTable table;
    private JComboBox<String> cmbTieuChiTimKiem;
    private JComboBox<String> cmbBoLoc;
    private DefaultTableModel model;

    // Label hiển thị số lượng ở footer
    private JLabel lblTongSoDong;
    private JLabel lblSoDongChon;

    public QuanLiNhanVienGUI() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        // --- 1. PANEL NORTH ---
        JPanel pnlNorth = new JPanel();
        pnlNorth.setLayout(new BorderLayout());

        // 1.1. Chức năng
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

        // 1.2. Tìm kiếm & Lọc
        JPanel pnlNorthRight = new JPanel();
        pnlNorthRight.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        cmbTieuChiTimKiem = new JComboBox<>(new String[]{"Mã nhân viên", "Tên nhân viên", "SĐT", "CCCD"});
        cmbTieuChiTimKiem.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbTieuChiTimKiem.setPreferredSize(new Dimension(150, 30));

        cmbBoLoc = new JComboBox<>(new String[]{"Tất cả", "Đang làm", "Đã nghỉ"});
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

        // --- 2. PANEL CENTER (TABLE) ---
        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));

        // Thêm cột STT vào đầu
        String[] columnNames = {"STT", "Mã NV", "Họ tên đệm", "Tên", "SĐT", "CCCD", "Giới tính", "Ngày sinh", "Địa chỉ", "Trạng thái"};
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

        // Header
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(220, 220, 220));
        table.getTableHeader().setReorderingAllowed(false);

        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);

        // --- CẤU HÌNH KÍCH THƯỚC & CĂN CHỈNH CỘT ---
        TableColumnModel columnModel = table.getColumnModel();
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // 0. STT: Nhỏ, Căn giữa
        columnModel.getColumn(0).setPreferredWidth(40);
        columnModel.getColumn(0).setMaxWidth(40);
        columnModel.getColumn(0).setCellRenderer(centerRenderer);

        // 1. Mã NV: Vừa phải, Căn giữa
        columnModel.getColumn(1).setPreferredWidth(80);
        columnModel.getColumn(1).setMaxWidth(100);
        columnModel.getColumn(1).setCellRenderer(centerRenderer);

        // 2. Họ tên đệm: Rộng
        columnModel.getColumn(2).setPreferredWidth(150);

        // 3. Tên: Vừa phải
        columnModel.getColumn(3).setPreferredWidth(80);

        // 4. SĐT: Căn giữa
        columnModel.getColumn(4).setPreferredWidth(100);
        columnModel.getColumn(4).setCellRenderer(centerRenderer);

        // 5. CCCD: Căn giữa
        columnModel.getColumn(5).setPreferredWidth(110);
        columnModel.getColumn(5).setCellRenderer(centerRenderer);

        // 6. Giới tính: Nhỏ, Căn giữa
        columnModel.getColumn(6).setPreferredWidth(70);
        columnModel.getColumn(6).setMaxWidth(80);
        columnModel.getColumn(6).setCellRenderer(centerRenderer);

        // 7. Ngày sinh: Căn giữa
        columnModel.getColumn(7).setPreferredWidth(90);
        columnModel.getColumn(7).setCellRenderer(centerRenderer);

        // 8. Địa chỉ: Rộng (Tự giãn nở các phần còn lại)
        columnModel.getColumn(8).setPreferredWidth(150);

        // 9. Trạng thái: Căn giữa
        columnModel.getColumn(9).setPreferredWidth(90);
        columnModel.getColumn(9).setCellRenderer(centerRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        this.add(centerPanel, BorderLayout.CENTER);

        // --- 3. PANEL SOUTH (FOOTER) ---
        JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        pnlSouth.setBorder(new EmptyBorder(5, 0, 0, 0));

        Font fontFooter = new Font("Arial", Font.BOLD, 13);

        lblTongSoDong = new JLabel("Tổng số nhân viên: 0");
        lblTongSoDong.setFont(fontFooter);
        lblTongSoDong.setForeground(new Color(0, 102, 204));

        lblSoDongChon = new JLabel("Đang chọn: 0");
        lblSoDongChon.setFont(fontFooter);
        lblSoDongChon.setForeground(new Color(204, 0, 0));

        pnlSouth.add(lblTongSoDong);
        pnlSouth.add(new JSeparator(JSeparator.VERTICAL));
        pnlSouth.add(lblSoDongChon);

        this.add(pnlSouth, BorderLayout.SOUTH);

        // Event
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

    private void updateTable(ArrayList<NhanVien> dsNV) {
        model.setRowCount(0);
        if (dsNV == null) {
            lblTongSoDong.setText("Tổng số nhân viên: 0");
            return;
        }
        int stt = 1;
        for (NhanVien nv : dsNV) {
            Object[] row = {
                stt++,
                nv.getMaNV(),
                nv.getHoTenDem(),
                nv.getTen(),
                nv.getSdt(),
                nv.getCccd(),
                nv.isGioiTinh() ? "Nam" : "Nữ",
                nv.getNgaySinh().toString(),
                nv.getDiaChi(),
                nv.isNghiViec() ? "Đã nghỉ" : "Đang làm"
            };
            model.addRow(row);
        }
        lblTongSoDong.setText("Tổng số nhân viên: " + dsNV.size());
    }

    private void updateTable() {
        ArrayList<NhanVien> dsNV = NhanVienDAO.getAllNhanVien();
        updateTable(dsNV);
    }

    private void addEvents() {
        btnThem.addActionListener(e -> xuLyThem());
        btnXoa.addActionListener(e -> xuLyXoa());
        btnSua.addActionListener(e -> xuLySua());
        txtTimKiem.addActionListener(e -> xuLyTimKiem());
        cmbBoLoc.addActionListener(e -> xuLyLoc());

        cmbTieuChiTimKiem.addActionListener(e -> {
            txtTimKiem.setText("");
            txtTimKiem.requestFocus();
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                hienThiChiTietNhanVien(e);
            }
        });

        // Sự kiện đếm dòng chọn (hỗ trợ shift/ctrl)
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                lblSoDongChon.setText("Đang chọn: " + table.getSelectedRowCount());
            }
        });
    }

    private void xuLyTimKiem() {
        String tuKhoa = txtTimKiem.getText().trim();
        String tieuChi = cmbTieuChiTimKiem.getSelectedItem().toString();
        ArrayList<NhanVien> dsKetQua = new ArrayList<>();

        if (tuKhoa.isEmpty()) {
            dsKetQua = NhanVienDAO.getAllNhanVien();
        } else {
            switch (tieuChi) {
                case "Mã nhân viên":
                    NhanVien nv = NhanVienDAO.timNVTheoMa(tuKhoa);
                    if (nv != null) {
                        dsKetQua.add(nv);
                    }
                    break;
                case "Tên nhân viên":
                    dsKetQua = NhanVienDAO.timNVTheoTen(tuKhoa);
                    break;
                case "SĐT":
                    dsKetQua = NhanVienDAO.timNVTheoSDT(tuKhoa);
                    break;
                case "CCCD":
                    dsKetQua = NhanVienDAO.timNVTheoCCCD(tuKhoa);
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
            boolean daNghiViec = boLoc.equals("Đã nghỉ");
            updateTable(NhanVienDAO.timNVTheoTrangThai(daNghiViec));
        }
    }

    private void xuLyThem() {
        ThemNhanVienGUI pnlThemNV = new ThemNhanVienGUI();
        JDialog dialog = new JDialog();
        dialog.setTitle("Thêm nhân viên mới");
        dialog.setModal(true);
        dialog.setResizable(false);
        dialog.setContentPane(pnlThemNV);
        dialog.pack();
        dialog.setLocationRelativeTo(null);

        int maNVCUoiCung = NhanVienDAO.getMaNVCuoiCung();
        String maNVNew = String.format("NV-%04d", maNVCUoiCung + 1);
        pnlThemNV.setTxtMaNhanVien(maNVNew);
        pnlThemNV.setTxtTenDangNhap(maNVNew);
        pnlThemNV.setTxtNgayTao(LocalDateTime.now());

        dialog.setVisible(true);
        NhanVien nvNew = pnlThemNV.getNhanVienMoi();
        TaiKhoan tkNew = pnlThemNV.getTaiKhoanMoi();
        if (nvNew != null && tkNew != null) {
            if (NhanVienDAO.themNhanVien(nvNew) && TaiKhoanDAO.themTaiKhoan(tkNew)) {
                JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!");
                updateTable();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm nhân viên thất bại.");
            }
        }
    }

    private void xuLyXoa() {
        int selectedRows[] = table.getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa.");
            return;
        }

        String message = (selectedRows.length == 1)
                ? "Bạn có chắc muốn xóa nhân viên '" + model.getValueAt(selectedRows[0], 3).toString() + "' không?"
                : // Index 3 là Tên
                "Bạn có chắc muốn xóa " + selectedRows.length + " nhân viên đã chọn không?";

        if (JOptionPane.showConfirmDialog(this, message, "Xác nhận xóa", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            int count = 0;
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                // Lấy mã NV ở cột 1 (cột 0 là STT)
                String maNV = model.getValueAt(selectedRows[i], 1).toString();
                if (NhanVienDAO.xoaNhanVien(maNV) && TaiKhoanDAO.xoaTaiKhoan(maNV)) {
                    count++;
                }
            }
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "Đã xóa thành công " + count + " nhân viên.");
                updateTable();
                lblSoDongChon.setText("Đang chọn: 0");
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại (có thể nhân viên đã lập hóa đơn).", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
        table.clearSelection();
    }

    private void xuLySua() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần sửa.");
            return;
        }

        String maNV = model.getValueAt(selectedRow, 1).toString();
        NhanVien nvCanSua = NhanVienDAO.timNVTheoMa(maNV);
        TaiKhoan tkCanSua = TaiKhoanDAO.getTaiKhoanTheoTenDangNhap(maNV);

        if (nvCanSua == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên.");
            return;
        }

        ThemNhanVienGUI pnlThemNV = new ThemNhanVienGUI();
        JDialog dialog = new JDialog();
        dialog.setTitle("Sửa thông tin nhân viên");
        dialog.setModal(true);
        dialog.setResizable(false);
        dialog.setContentPane(pnlThemNV);
        dialog.pack();
        dialog.setLocationRelativeTo(null);

        pnlThemNV.setTxtMaNhanVien(nvCanSua.getMaNV());
        pnlThemNV.setTxtHoTenDem(nvCanSua.getHoTenDem());
        pnlThemNV.setTxtTen(nvCanSua.getTen());
        pnlThemNV.setTxtSDT(nvCanSua.getSdt());
        pnlThemNV.setTxtCCCD(nvCanSua.getCccd());
        pnlThemNV.setCmbGioiTinh(nvCanSua.isGioiTinh());
        pnlThemNV.setTxtNgaySinh(nvCanSua.getNgaySinh());
        pnlThemNV.setTxtDiaChi(nvCanSua.getDiaChi());
        pnlThemNV.setChkNghiViec(nvCanSua.isNghiViec());

        pnlThemNV.setTxtTenDangNhap(maNV);
        pnlThemNV.setTxtMatKhau(tkCanSua.getMatKhau());
        pnlThemNV.setChkQuanLy(tkCanSua.isQuanLy());
        pnlThemNV.setChkBiKhoa(tkCanSua.isBiKhoa());
        pnlThemNV.setTxtEmail(tkCanSua.getEmail());
        pnlThemNV.setTxtNgayTao(tkCanSua.getNgayTao());
        

        dialog.setVisible(true);
        NhanVien nvNew = pnlThemNV.getNhanVienMoi();
        TaiKhoan tkNew = pnlThemNV.getTaiKhoanMoi();
        if (nvNew != null && tkNew != null) {
            if (NhanVienDAO.suaNhanVien(maNV, nvNew) && TaiKhoanDAO.capNhatTaiKhoan(tkNew)) {
                JOptionPane.showMessageDialog(this, "Sửa thông tin nhân viên thành công!");
                updateTable();
            } else {
                JOptionPane.showMessageDialog(this, "Sửa thông tin nhân viên thất bại.");
            }
        }
    }

    private void hienThiChiTietNhanVien(MouseEvent e) {
        int selectRow = table.getSelectedRow();
        if (selectRow != -1 && e.getClickCount() == 2) {
            // Lấy mã NV ở cột 1
            String maNV = model.getValueAt(selectRow, 1).toString();
            NhanVien nvDaChon = NhanVienDAO.timNVTheoMa(maNV);

            ThemNhanVienGUI pnlThemNV = new ThemNhanVienGUI();
            JDialog dialog = new JDialog();
            dialog.setTitle("Thông tin chi tiết nhân viên");
            dialog.setModal(true);
            dialog.setResizable(false);
            dialog.setContentPane(pnlThemNV);
            dialog.pack();
            dialog.setLocationRelativeTo(null);

            pnlThemNV.getBtnHuy().setVisible(false);
            pnlThemNV.getBtnXacNhan().setText("Đóng");
            pnlThemNV.getBtnXacNhan().addActionListener(l -> dialog.dispose());

            pnlThemNV.getTxtHoTenDem().setEditable(false);
            pnlThemNV.getTxtTen().setEditable(false);
            pnlThemNV.getTxtSDT().setEditable(false);
            pnlThemNV.getTxtCCCD().setEditable(false);
            pnlThemNV.getTxtNgaySinh().setEditable(false);
            pnlThemNV.getTxtDiaChi().setEditable(false);
            pnlThemNV.getCmbGioiTinh().setEnabled(false);
            pnlThemNV.getChkNghiViec().setEnabled(false);
            pnlThemNV.getChkBiKhoa().setEnabled(false);
            pnlThemNV.getChkQuanLy().setEnabled(false);
            pnlThemNV.getTxtEmail().setEditable(false);
            pnlThemNV.getTxtTenDangNhap().setEditable(false);
            pnlThemNV.getTxtMatKhau().setEditable(false);

            pnlThemNV.setTxtMaNhanVien(nvDaChon.getMaNV());
            pnlThemNV.setTxtHoTenDem(nvDaChon.getHoTenDem());
            pnlThemNV.setTxtTen(nvDaChon.getTen());
            pnlThemNV.setTxtSDT(nvDaChon.getSdt());
            pnlThemNV.setTxtCCCD(nvDaChon.getCccd());
            pnlThemNV.setCmbGioiTinh(nvDaChon.isGioiTinh());
            pnlThemNV.setTxtNgaySinh(nvDaChon.getNgaySinh());
            pnlThemNV.setTxtDiaChi(nvDaChon.getDiaChi());
            pnlThemNV.setChkNghiViec(nvDaChon.isNghiViec());
            TaiKhoan tk = TaiKhoanDAO.getTaiKhoanTheoTenDangNhap(nvDaChon.getMaNV());
            pnlThemNV.setChkBiKhoa(tk.isBiKhoa());
            pnlThemNV.setChkQuanLy(tk.isQuanLy());
            pnlThemNV.setTxtEmail(tk.getEmail());
            pnlThemNV.setTxtTenDangNhap(nvDaChon.getMaNV());
            pnlThemNV.setTxtMatKhau(tk.getMatKhau());
            pnlThemNV.setTxtNgayTao(tk.getNgayTao());

            dialog.setVisible(true);
        }
    }
}
