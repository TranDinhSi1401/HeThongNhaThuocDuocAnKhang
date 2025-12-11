/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import hethongnhathuocduocankhang.dao.ChiTietHoaDonDAO;
import hethongnhathuocduocankhang.dao.KhachHangDAO;
import hethongnhathuocduocankhang.entity.KhachHang;
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
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class QuanLiKhachHangGUI extends JPanel {

    private JButton btnThem, btnXoa, btnSua;
    private JTextField txtTimKiem;
    private JTable table;
    private JComboBox<String> cmbTieuChiTimKiem;
    private DefaultTableModel model;

    // 1. KHAI BÁO THÊM 2 LABEL
    private JLabel lblTongSoDong;
    private JLabel lblSoDongChon;

    public QuanLiKhachHangGUI() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        // --- 1. TẠO PANEL NORTH (GIỮ NGUYÊN) ---
        JPanel pnlNorth = new JPanel();
        pnlNorth.setLayout(new BorderLayout());

        // 1.1. Panel Chức năng (LEFT)
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

        // 1.2. Panel Tìm kiếm
        JPanel pnlNorthRight = new JPanel();
        pnlNorthRight.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        cmbTieuChiTimKiem = new JComboBox<>(new String[]{"Mã khách hàng", "Tên khách hàng", "Số điện thoại"});
        cmbTieuChiTimKiem.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbTieuChiTimKiem.setPreferredSize(new Dimension(150, 30));

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

        // --- 2. TẠO PANEL CENTER (Bảng dữ liệu) ---
        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));

        // 2.1. Bảng dữ liệu - THÊM CỘT STT
        String[] columnNames = {"STT", "Mã KH", "Họ tên đệm", "Tên", "Số điện thoại", "Điểm tích lũy"};
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

        // Thiết lập Header
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(220, 220, 220));
        table.getTableHeader().setReorderingAllowed(false);

        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);

        // --- ĐOẠN CHỈNH KÍCH THƯỚC CỘT ---
        TableColumnModel columnModel = table.getColumnModel();

        // 1. Cột STT (Cột 0): Nhỏ xíu, căn giữa
        columnModel.getColumn(0).setPreferredWidth(40);
        columnModel.getColumn(0).setMaxWidth(40);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        columnModel.getColumn(0).setCellRenderer(centerRenderer);

        // 2. Cột Mã KH (Cột 1): Vừa đủ hiển thị "KH-XXXXX", căn giữa cho đẹp
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(1).setMaxWidth(100); // Khóa cứng chiều rộng tối đa là 100px
        columnModel.getColumn(1).setCellRenderer(centerRenderer); // Dùng chung căn giữa với STT

        // 3. Cột Số điện thoại (Cột 4): Cũng nên thu gọn lại chút và căn giữa
        columnModel.getColumn(4).setPreferredWidth(100);
        columnModel.getColumn(4).setMaxWidth(100);
        columnModel.getColumn(4).setCellRenderer(centerRenderer);

        // 4. Cột Điểm tích lũy (Cột 5): Căn giữa
        columnModel.getColumn(5).setCellRenderer(centerRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        this.add(centerPanel, BorderLayout.CENTER);

        // --- 3. TẠO PANEL SOUTH (FOOTER) MỚI ---
        JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        pnlSouth.setBorder(new EmptyBorder(5, 0, 0, 0));

        Font fontFooter = new Font("Arial", Font.BOLD, 13);

        lblTongSoDong = new JLabel("Tổng số khách hàng: 0");
        lblTongSoDong.setFont(fontFooter);
        lblTongSoDong.setForeground(new Color(0, 102, 204)); // Màu xanh đậm

        lblSoDongChon = new JLabel("Đang chọn: 0");
        lblSoDongChon.setFont(fontFooter);
        lblSoDongChon.setForeground(new Color(204, 0, 0)); // Màu đỏ đậm

        pnlSouth.add(lblTongSoDong);
        pnlSouth.add(new JSeparator(JSeparator.VERTICAL)); // Đường ngăn cách
        pnlSouth.add(lblSoDongChon);

        this.add(pnlSouth, BorderLayout.SOUTH);

        // Đăng kí sự kiện
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
            lblTongSoDong.setText("Tổng số khách hàng: 0");
            return;
        }

        int stt = 1; // Biến đếm STT
        for (KhachHang kh : dsKH) {
            Object[] row = {
                stt++, // Tăng STT
                kh.getMaKH(),
                kh.getHoTenDem(),
                kh.getTen(),
                kh.getSdt(),
                kh.getDiemTichLuy()
            };
            model.addRow(row);
        }

        // Cập nhật label tổng số dòng
        lblTongSoDong.setText("Tổng số khách hàng: " + dsKH.size());
    }

    private void updateTable() {
        ArrayList<KhachHang> dsKH = KhachHangDAO.getAllKhachHang();
        updateTable(dsKH);
    }

    //HÀM ĐĂNG KÝ TẤT CẢ SỰ KIỆN
    private void addEvents() {
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyThem();
            }
        });

        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyXoa();
            }
        });

        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLySua();
            }
        });

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
                hienThiChiTietKhachHang(e);
            }
        });

        // --- SỰ KIỆN CHỌN DÒNG TRONG TABLE ---
        // Sử dụng ListSelectionListener để bắt sự kiện tốt hơn MouseListener (hỗ trợ cả phím Shift/Ctrl)
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // getValueIsAdjusting() trả về true khi người dùng đang kéo chuột, 
                // chỉ cập nhật khi hành động kết thúc (false)
                if (!e.getValueIsAdjusting()) {
                    int soDongDaChon = table.getSelectedRowCount();
                    lblSoDongChon.setText("Đang chọn: " + soDongDaChon);
                }
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
        dialog.setModal(true);
        dialog.setResizable(false);
        dialog.setContentPane(pnlThemKH);
        dialog.pack();
        dialog.setLocationRelativeTo(null);

        int maKHCUoiCung = KhachHangDAO.getMaKHCUoiCung();
        maKHCUoiCung++;
        String maKHNew = String.format("KH-%05d", maKHCUoiCung);

        pnlThemKH.setTxtMaKhachHang(maKHNew);
        pnlThemKH.setTxtDiemTichLuy(0);
        pnlThemKH.getTxtDiemTichLuy().setEnabled(false);

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
            // Lưu ý: Cột 1 bây giờ là Mã KH, Cột 2 là Họ đệm, Cột 3 là Tên (do thêm STT vào cột 0)
            String tenKH = model.getValueAt(selectedRows[0], 2).toString() + " " + model.getValueAt(selectedRows[0], 3).toString();
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
                // Lấy mã KH ở cột 1 (vì cột 0 là STT)
                String maKH = model.getValueAt(row, 1).toString();

                if (KhachHangDAO.xoaKhachHang(maKH)) {
                    soLuongXoaThanhCong++;
                }
            }

            if (soLuongXoaThanhCong > 0) {
                JOptionPane.showMessageDialog(this, "Đã xóa thành công " + soLuongXoaThanhCong + " khách hàng.");
                updateTable();
                // Cập nhật lại số dòng chọn về 0 sau khi xóa
                lblSoDongChon.setText("Đang chọn: 0");
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

        // Lấy Mã KH ở cột 1 (vì cột 0 là STT)
        String maKH = model.getValueAt(selectedRow, 1).toString();
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

        pnlThemKH.setTxtMaKhachHang(khCanSua.getMaKH());
        pnlThemKH.setTxtHoTenDem(khCanSua.getHoTenDem());
        pnlThemKH.setTxtTen(khCanSua.getTen());
        pnlThemKH.setTxtSDT(khCanSua.getSdt());
        pnlThemKH.setTxtDiemTichLuy(khCanSua.getDiemTichLuy());
        pnlThemKH.getTxtDiemTichLuy().setEnabled(true);

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
            // Lấy Mã KH ở cột 1
            String maKH = model.getValueAt(selectRow, 1).toString();
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

                pnlThemKH.getBtnHuy().setVisible(false);
                pnlThemKH.getBtnXacNhan().setText("Đóng");
                pnlThemKH.getBtnXacNhan().addActionListener(l -> dialog.dispose());

                pnlThemKH.getTxtHoTenDem().setEditable(false);
                pnlThemKH.getTxtTen().setEditable(false);
                pnlThemKH.getTxtSDT().setEditable(false);
                pnlThemKH.getTxtDiemTichLuy().setEditable(false);

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
