/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import hethongnhathuocduocankhang.dao.NhanVienDAO;
import hethongnhathuocduocankhang.entity.NhanVien;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class QuanLiNhanVienGUI extends JPanel {

    private JButton btnThem, btnXoa, btnSua;
    private JTextField txtTimKiem;
    private JTable table;
    private JComboBox<String> cmbTieuChiTimKiem;
    private JComboBox<String> cmbBoLoc;
    private DefaultTableModel model;

    public QuanLiNhanVienGUI() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        // 1. TẠO PANEL NORTH
        JPanel pnlNorth = new JPanel();
        pnlNorth.setLayout(new BorderLayout());

        // 1.1. Panel Chức năng (LEFT - Thêm, Xóa, Sửa)
        JPanel pnlNorthLeft = new JPanel();
        pnlNorthLeft.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pnlNorthLeft.setBorder(new EmptyBorder(0, 0, 10, 0));

        // 1.1.1. Các nút chức năng (Thêm, Xóa, Sửa)
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

        // 1.2. Panel Tìm kiếm và Lọc
        JPanel pnlNorthRight = new JPanel();
        pnlNorthRight.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        //Tiêu chí tìm kiếm
        cmbTieuChiTimKiem = new JComboBox<>(new String[]{"Mã nhân viên", "Tên nhân viên", "SĐT", "CCCD"});
        cmbTieuChiTimKiem.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbTieuChiTimKiem.setPreferredSize(new Dimension(150, 30));

        // ComboBox Bộ lọc
        cmbBoLoc = new JComboBox<>(new String[]{"Tất cả", "Đang làm", "Đã nghỉ"});
        cmbBoLoc.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbBoLoc.setPreferredSize(new Dimension(150, 30));

        // Thanh tìm kiếm
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

        // 3.1. Bảng dữ liệu
        String[] columnNames = {"Mã NV", "Họ tên đệm", "Tên", "SĐT", "CCCD", "Giới tính", "Ngày sinh", "Địa chỉ", "Trạng thái"};
        Object[][] data = {};

        model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Ngăn chỉnh sửa trực tiếp trong bảng
            }
        };

        table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 12));

        // Thiết lập Header
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(220, 220, 220));
        table.getTableHeader().setReorderingAllowed(false);

        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);

        JScrollPane scrollPane = new JScrollPane(table);

        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // 4. THÊM CÁC PANEL VÀO PANEL CHÍNH
        this.add(pnlNorth, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);

        //Đăng kí sự kiện
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
            return;
        }
        for (NhanVien nv : dsNV) {
            Object[] row = {
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
    }

    private void updateTable() {
        ArrayList<NhanVien> dsNV = NhanVienDAO.getAllNhanVien();
        updateTable(dsNV);
    }

    //HÀM ĐĂNG KÝ TẤT CẢ SỰ KIỆN
    private void addEvents() {
        // Sự kiện cho nút Thêm
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyThem();
            }
        });

        // Sự kiện cho nút Xóa
        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyXoa();
            }
        });

        // Sự kiện cho nút Sửa
        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLySua();
            }
        });

        // Sự kiện khi nhấn Enter trên thanh tìm kiếm
        txtTimKiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyTimKiem();
            }
        });

        // Sự kiện khi thay đổi bộ lọc
        cmbBoLoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyLoc();
            }
        });

        // Sự kiện khi đổi tiêu chí tìm kiếm -> Xóa rỗng ô tìm kiếm
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
                hienThiChiTietNhanVien(e);
            }
        });
    }

    //HÀM XỬ LÝ TÌM KIẾM VÀ LỌC
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

    //HÀM XỬ LÝ LỌC
    private void xuLyLoc() {
        String boLoc = cmbBoLoc.getSelectedItem().toString();

        if (boLoc.equals("Tất cả")) {
            updateTable();
        } else {
            // "Đang làm" -> false, "Đã nghỉ" -> true
            boolean daNghiViec = boLoc.equals("Đã nghỉ");
            ArrayList<NhanVien> dsDaLoc = NhanVienDAO.timNVTheoTrangThai(daNghiViec);
            updateTable(dsDaLoc);
        }
    }

    //HÀM XỬ LÝ NÚT THÊM
    private void xuLyThem() {
        ThemNhanVienGUI pnlThemNV = new ThemNhanVienGUI();
        JDialog dialog = new JDialog();
        dialog.setTitle("Thêm nhân viên mới");
        dialog.setModal(true); // Chặn tương tác với cửa sổ chính
        dialog.setResizable(false);
        dialog.setContentPane(pnlThemNV);
        dialog.pack(); // Tự động điều chỉnh kích thước JDialog cho vừa với panel
        dialog.setLocationRelativeTo(null);
        
        //Tạo mã NV mới
        int maNVCUoiCung = NhanVienDAO.getMaNVCuoiCung();
        maNVCUoiCung++;
        String maNVNew = String.format("NV-%04d", maNVCUoiCung);
        pnlThemNV.setTxtMaNhanVien(maNVNew);
        
        dialog.setVisible(true);

        NhanVien nvNew = pnlThemNV.getNhanVienMoi();

        if (nvNew != null) {
            if (NhanVienDAO.themNhanVien(nvNew)) {
                JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!");
                updateTable();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm nhân viên thất bại.");
            }
        }
    }

    //HÀM XỬ LÝ NÚT XÓA
    private void xuLyXoa() {
        int selectedRows[] = table.getSelectedRows();

        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa.");
            return;
        }

        String message;
        if (selectedRows.length == 1) {
            String tenNV = model.getValueAt(selectedRows[0], 1).toString() + " " + model.getValueAt(selectedRows[0], 2).toString();
            message = "Bạn có chắc muốn xóa nhân viên '" + tenNV + "' không?";
        } else {
            message = "Bạn có chắc muốn xóa " + selectedRows.length + " nhân viên đã chọn không?";
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                message,
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            int soLuongXoaThanhCong = 0;

            for (int i = selectedRows.length - 1; i >= 0; i--) {
                int row = selectedRows[i];
                String maNV = model.getValueAt(row, 0).toString();

                if (NhanVienDAO.xoaNhanVien(maNV)) {
                    soLuongXoaThanhCong++;
                }
            }

            if (soLuongXoaThanhCong > 0) {
                JOptionPane.showMessageDialog(this, "Đã xóa thành công " + soLuongXoaThanhCong + " nhân viên.");
                updateTable();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa nhân viên thất bại (có thể do nhân viên đã lập hóa đơn).", "Lỗi xóa", JOptionPane.ERROR_MESSAGE);
            }
        }
        table.clearSelection();
    }

    //HÀM XỬ LÝ NÚT SỬA
    private void xuLySua() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần sửa.");
            return;
        }

        String maNV = model.getValueAt(selectedRow, 0).toString();
        NhanVien nvCanSua = NhanVienDAO.timNVTheoMa(maNV);

        if (nvCanSua == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên để sửa.");
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

        // Đổ dữ liệu cũ lên form
        pnlThemNV.setTxtMaNhanVien(nvCanSua.getMaNV());
        pnlThemNV.setTxtHoTenDem(nvCanSua.getHoTenDem());
        pnlThemNV.setTxtTen(nvCanSua.getTen());
        pnlThemNV.setTxtSDT(nvCanSua.getSdt());
        pnlThemNV.setTxtCCCD(nvCanSua.getCccd());
        pnlThemNV.setCmbGioiTinh(nvCanSua.isGioiTinh());
        pnlThemNV.setTxtNgaySinh(nvCanSua.getNgaySinh());
        pnlThemNV.setTxtDiaChi(nvCanSua.getDiaChi());
        pnlThemNV.setChkNghiViec(nvCanSua.isNghiViec());


        dialog.setVisible(true);

        NhanVien nvNew = pnlThemNV.getNhanVienMoi();

        if (nvNew != null) {
            if (NhanVienDAO.suaNhanVien(maNV, nvNew)) {
                JOptionPane.showMessageDialog(this, "Sửa thông tin nhân viên thành công!");
                updateTable();
            } else {
                JOptionPane.showMessageDialog(this, "Sửa thông tin nhân viên thất bại.");
            }
        }
    }

    private void hienThiChiTietNhanVien(MouseEvent e) {
        int selectRow = table.getSelectedRow();
        if (selectRow != -1) {
            String maNV = model.getValueAt(selectRow, 0).toString();
            NhanVien nvDaChon = NhanVienDAO.timNVTheoMa(maNV);
            
            if (e.getClickCount() == 2) { // Double click
                ThemNhanVienGUI pnlThemNV = new ThemNhanVienGUI();
                JDialog dialog = new JDialog();
                dialog.setTitle("Thông tin chi tiết nhân viên");
                dialog.setModal(true);
                dialog.setResizable(false);
                dialog.setContentPane(pnlThemNV);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                
                // Ẩn nút
                pnlThemNV.getBtnHuy().setVisible(false);
                pnlThemNV.getBtnXacNhan().setText("Đóng");
                pnlThemNV.getBtnXacNhan().addActionListener(l -> dialog.dispose());
                
                // Vô hiệu hóa chỉnh sửa
                pnlThemNV.getTxtHoTenDem().setEditable(false);
                pnlThemNV.getTxtTen().setEditable(false);
                pnlThemNV.getTxtSDT().setEditable(false);
                pnlThemNV.getTxtCCCD().setEditable(false);
                pnlThemNV.getTxtNgaySinh().setEditable(false);
                pnlThemNV.getTxtDiaChi().setEditable(false);
                pnlThemNV.getCmbGioiTinh().setEnabled(false);
                pnlThemNV.getChkNghiViec().setEnabled(false);


                pnlThemNV.setTxtMaNhanVien(nvDaChon.getMaNV());
                pnlThemNV.setTxtHoTenDem(nvDaChon.getHoTenDem());
                pnlThemNV.setTxtTen(nvDaChon.getTen());
                pnlThemNV.setTxtSDT(nvDaChon.getSdt());
                pnlThemNV.setTxtCCCD(nvDaChon.getCccd());
                pnlThemNV.setCmbGioiTinh(nvDaChon.isGioiTinh());
                pnlThemNV.setTxtNgaySinh(nvDaChon.getNgaySinh());
                pnlThemNV.setTxtDiaChi(nvDaChon.getDiaChi());
                pnlThemNV.setChkNghiViec(nvDaChon.isNghiViec());

                dialog.setVisible(true);
            }
        }
    }
}