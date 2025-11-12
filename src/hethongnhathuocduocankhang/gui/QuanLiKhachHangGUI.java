/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import hethongnhathuocduocankhang.dao.KhachHangDAO;
import hethongnhathuocduocankhang.entity.KhachHang;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class QuanLiKhachHangGUI extends JPanel {

    private JButton btnThem, btnXoa, btnSua;
    private JTextField txtTimKiem;
    private JTable table;
    private JComboBox<String> cmbTieuChiTimKiem;
    private DefaultTableModel model;

    public QuanLiKhachHangGUI() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        // 1. TẠO PANEL NORTH
        JPanel pnlNorth = new JPanel();
        pnlNorth.setLayout(new BorderLayout());

        // 1.1. Panel Chức năng (LEFT - Thêm, Xóa, Sửa)
        JPanel pnlNorthLeft = new JPanel();
        pnlNorthLeft.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pnlNorthLeft.setBorder(new EmptyBorder(0, 0, 10, 0));

        // 1.1.1. Các nút chức năng
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

        // 1.2. Panel Tìm kiếm
        JPanel pnlNorthRight = new JPanel();
        pnlNorthRight.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        //Tiêu chí tìm kiếm
        cmbTieuChiTimKiem = new JComboBox<>(new String[]{"Mã khách hàng", "Tên khách hàng", "Số điện thoại"});
        cmbTieuChiTimKiem.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbTieuChiTimKiem.setPreferredSize(new Dimension(150, 30));

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

        pnlNorth.add(pnlNorthRight, BorderLayout.EAST);

        this.add(pnlNorth, BorderLayout.NORTH);

        // 3. TẠO PANEL CENTER (Bảng dữ liệu)
        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));

        // 3.1. Bảng dữ liệu
        String[] columnNames = {"Mã KH", "Họ tên đệm", "Tên", "Số điện thoại", "Điểm tích lũy"}; // THAY ĐỔI
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

    private void updateTable(ArrayList<KhachHang> dsKH) {
        model.setRowCount(0);
        if (dsKH == null) {
            return;
        }
        for (KhachHang kh : dsKH) {
            Object[] row = {
                kh.getMaKH(),
                kh.getHoTenDem(),
                kh.getTen(),
                kh.getSdt(),
                kh.getDiemTichLuy()
            };
            model.addRow(row);
        }
    }

    private void updateTable() {
        ArrayList<KhachHang> dsKH = KhachHangDAO.getAllKhachHang();
        updateTable(dsKH);
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
                hienThiChiTietKhachHang(e);
            }
        });
    }

    //HÀM XỬ LÝ TÌM KIẾM
    private void xuLyTimKiem() {
        String tuKhoa = txtTimKiem.getText().trim();
        String tieuChi = cmbTieuChiTimKiem.getSelectedItem().toString();

        ArrayList<KhachHang> dsKetQua = new ArrayList<>();

        if (tuKhoa.isEmpty()) {
            dsKetQua = KhachHangDAO.getAllKhachHang();
        } else {
            switch (tieuChi) {
                case "Mã khách hàng":
                    KhachHang kh = KhachHangDAO.timKHTheoMa(tuKhoa);
                    if (kh != null) {
                        dsKetQua.add(kh);
                    }
                    break;
                case "Tên khách hàng":
                    dsKetQua = KhachHangDAO.timKHTheoTen(tuKhoa);
                    break;
                case "Số điện thoại":
                    dsKetQua = KhachHangDAO.timKHTheoSDT(tuKhoa);
                    break;
            }
        }
        updateTable(dsKetQua);

    }

    //HÀM XỬ LÝ NÚT THÊM
    private void xuLyThem() {
        ThemKhachHangGUI pnlThemKH = new ThemKhachHangGUI();
        JDialog dialog = new JDialog();
        dialog.setTitle("Thêm khách hàng mới");
        dialog.setModal(true); // Chặn tương tác với cửa sổ chính
        dialog.setResizable(false);
        dialog.setContentPane(pnlThemKH);
        dialog.pack(); // Tự động điều chỉnh kích thước JDialog
        dialog.setLocationRelativeTo(null);
        
        int maKHCUoiCung = KhachHangDAO.getMaKHCUoiCung();
        maKHCUoiCung++; 
        String maKHNew = String.format("KH-%05d", maKHCUoiCung); // Định dạng KH-XXXXX
        
        pnlThemKH.setTxtMaKhachHang(maKHNew);
        pnlThemKH.setTxtDiemTichLuy(0); // Khách hàng mới có 0 điểm
        pnlThemKH.getTxtDiemTichLuy().setEnabled(false); // Không cho sửa điểm khi thêm
        
        dialog.setVisible(true);

        KhachHang khNew = pnlThemKH.getKhachHangMoi();

        if (khNew != null) {
            if (KhachHangDAO.themKhachHang(khNew)) {
                JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!");
                updateTable();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm khách hàng thất bại (có thể trùng SĐT).");
            }
        }
    }

    //HÀM XỬ LÝ NÚT XÓA
    private void xuLyXoa() {
        int selectedRows[] = table.getSelectedRows();

        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần xóa.");
            return;
        }

        String message;
        if (selectedRows.length == 1) {
            String tenKH = model.getValueAt(selectedRows[0], 1).toString() + " " + model.getValueAt(selectedRows[0], 2).toString();
            message = "Bạn có chắc muốn xóa khách hàng '" + tenKH + "' không?";
        } else {
            message = "Bạn có chắc muốn xóa " + selectedRows.length + " khách hàng đã chọn không?";
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                message,
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            int soLuongXoaThanhCong = 0;

            for (int i = selectedRows.length - 1; i >= 0; i--) {
                int row = selectedRows[i];
                String maKH = model.getValueAt(row, 0).toString();

                if (KhachHangDAO.xoaKhachHang(maKH)) {
                    soLuongXoaThanhCong++;
                }
            }

            if (soLuongXoaThanhCong > 0) {
                JOptionPane.showMessageDialog(this, "Đã xóa thành công " + soLuongXoaThanhCong + " khách hàng.");
                updateTable();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa khách hàng thất bại (có thể do khách hàng đã có hóa đơn).", "Lỗi xóa", JOptionPane.ERROR_MESSAGE);
            }
        }
        table.clearSelection();
    }

    //HÀM XỬ LÝ NÚT SỬA
    private void xuLySua() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần sửa.");
            return;
        }

        String maKH = model.getValueAt(selectedRow, 0).toString();
        KhachHang khCanSua = KhachHangDAO.timKHTheoMa(maKH);

        if (khCanSua == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng để sửa.");
            return;
        }

        ThemKhachHangGUI pnlThemKH = new ThemKhachHangGUI();
        JDialog dialog = new JDialog();
        dialog.setTitle("Sửa thông tin khách hàng");
        dialog.setModal(true);
        dialog.setResizable(false);
        dialog.setContentPane(pnlThemKH);
        dialog.pack();
        dialog.setLocationRelativeTo(null);

        // Đổ dữ liệu cũ lên form
        pnlThemKH.setTxtMaKhachHang(khCanSua.getMaKH());
        pnlThemKH.setTxtHoTenDem(khCanSua.getHoTenDem());
        pnlThemKH.setTxtTen(khCanSua.getTen());
        pnlThemKH.setTxtSDT(khCanSua.getSdt());
        pnlThemKH.setTxtDiemTichLuy(khCanSua.getDiemTichLuy());
        pnlThemKH.getTxtDiemTichLuy().setEnabled(true); // Cho phép sửa điểm

        dialog.setVisible(true);

        KhachHang khNew = pnlThemKH.getKhachHangMoi();

        if (khNew != null) {
            if (KhachHangDAO.suaKhachHang(maKH, khNew)) {
                JOptionPane.showMessageDialog(this, "Sửa thông tin khách hàng thành công!");
                updateTable();
            } else {
                JOptionPane.showMessageDialog(this, "Sửa thông tin khách hàng thất bại (có thể trùng SĐT).");
            }
        }
    }

    private void hienThiChiTietKhachHang(MouseEvent e) {
        int selectRow = table.getSelectedRow();
        if (selectRow != -1) {
            String maKH = model.getValueAt(selectRow, 0).toString();
            KhachHang khDaChon = KhachHangDAO.timKHTheoMa(maKH);
            
            if (e.getClickCount() == 2) { // Double click
                ThemKhachHangGUI pnlThemKH = new ThemKhachHangGUI();
                JDialog dialog = new JDialog();
                dialog.setTitle("Thông tin chi tiết khách hàng");
                dialog.setModal(true);
                dialog.setResizable(false);
                dialog.setContentPane(pnlThemKH);
                dialog.pack();
                dialog.setLocationRelativeTo(null);

                // Ẩn nút Hủy, đổi tên nút Xác nhận -> Đóng
                pnlThemKH.getBtnHuy().setVisible(false);
                pnlThemKH.getBtnXacNhan().setText("Đóng");
                pnlThemKH.getBtnXacNhan().addActionListener(l -> dialog.dispose());
                
                // Vô hiệu hóa chỉnh sửa
                pnlThemKH.getTxtHoTenDem().setEditable(false);
                pnlThemKH.getTxtTen().setEditable(false);
                pnlThemKH.getTxtSDT().setEditable(false);
                pnlThemKH.getTxtDiemTichLuy().setEditable(false);

                // Đổ dữ liệu
                pnlThemKH.setTxtMaKhachHang(khDaChon.getMaKH());
                pnlThemKH.setTxtHoTenDem(khDaChon.getHoTenDem());
                pnlThemKH.setTxtTen(khDaChon.getTen());
                pnlThemKH.setTxtSDT(khDaChon.getSdt());
                pnlThemKH.setTxtDiemTichLuy(khDaChon.getDiemTichLuy());

                dialog.setVisible(true);
            }
        }
    }
}