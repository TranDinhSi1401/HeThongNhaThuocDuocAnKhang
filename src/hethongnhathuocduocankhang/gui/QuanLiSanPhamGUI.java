/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import hethongnhathuocduocankhang.dao.SanPhamDAO;
import hethongnhathuocduocankhang.entity.LoaiSanPhamEnum;
import hethongnhathuocduocankhang.entity.SanPham;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class QuanLiSanPhamGUI extends JPanel {

    private JButton btnThem, btnXoa, btnSua;
    private JTextField txtTimKiem;
    private JTable table;
    private JComboBox<String> cmbTieuChiTimKiem;
    private JComboBox<String> cmbBoLoc;
    private DefaultTableModel model;

    public QuanLiSanPhamGUI() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        // 1. TẠO PANEL NORTH
        JPanel pnlNorth = new JPanel();
        pnlNorth.setLayout(new BorderLayout());

        // 1.1. Panel Chức năng (LEFT - Thêm, Xóa, Sửa)
        JPanel pnlNorthLeft = new JPanel();
        pnlNorthLeft.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pnlNorthLeft.setBorder(new EmptyBorder(0, 0, 10, 0));

        // 1.1.1. Các nút chức năng (Thêm, Xóa, Xem chi tiết, Xuất Excel, Nhập Excel)
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
        cmbTieuChiTimKiem = new JComboBox<>(new String[]{"Mã sản phẩm", "Tên sản phẩm", "Mã nhà cung cấp"});
        cmbTieuChiTimKiem.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbTieuChiTimKiem.setPreferredSize(new Dimension(150, 30));

        // ComboBox Bộ lọc
        cmbBoLoc = new JComboBox<>(new String[]{"Tất cả", "Thuốc", "Thực phẩm chức năng"});
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
        String[] columnNames = {"Mã SP", "Tên SP", "Mô tả", "Thành phần", "Loại sản phẩm", "Tồn tối thiểu", "Tồn tối đa"};
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

    private void updateTable(ArrayList<SanPham> dsSP) {
        model.setRowCount(0);
        if (dsSP == null) {
            return;
        }
        for (SanPham sp : dsSP) {
            Object[] row = {
                sp.getMaSP(),
                sp.getTen(),
                sp.getMoTa(),
                sp.getThanhPhan(),
                sp.getLoaiSanPham().toString(),
                sp.getTonToiThieu(),
                sp.getTonToiDa()
            };
            model.addRow(row);
        }
    }

    private void updateTable() {
        ArrayList<SanPham> dsSP = SanPhamDAO.getAllSanPham();
        updateTable(dsSP);
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
                hienThiChiTietSanPham(e);
            }
        });
    }

    //HÀM XỬ LÝ TÌM KIẾM VÀ LỌC
    private void xuLyTimKiem() {
        String tuKhoa = txtTimKiem.getText().trim();
        String tieuChi = cmbTieuChiTimKiem.getSelectedItem().toString();

        ArrayList<SanPham> dsKetQua = new ArrayList<>();

        if (tuKhoa.isEmpty()) {
            dsKetQua = SanPhamDAO.getAllSanPham();
        } else {
            switch (tieuChi) {
                case "Mã sản phẩm":
                    SanPham sp = SanPhamDAO.timSPTheoMa(tuKhoa);
                    if (sp != null) {
                        dsKetQua.add(sp);
                    }
                    break;
                case "Tên sản phẩm":
                    dsKetQua = SanPhamDAO.timSPTheoTen(tuKhoa);
                    break;
                case "Mã nhà cung cấp":
                    dsKetQua = SanPhamDAO.timSPTheoMaNCC(tuKhoa);
                    break;
            }
        }
            updateTable(dsKetQua); // Hiển thị kết quả đã lọc

    }

    //HÀM XỬ LÝ TÌM KIẾM VÀ LỌC
    private void xuLyLoc() {
        String boLoc = cmbBoLoc.getSelectedItem().toString();

        if (boLoc.equals("Thuốc")) {
            boLoc = "THUOC";
        } else if (boLoc.equals("Thực phẩm chức năng")) {
            boLoc = "THUC_PHAM_CHUC_NANG";
        }

        if (boLoc.equals("Tất cả")) {
            updateTable();
        } else {
            ArrayList<SanPham> dsDaLoc = SanPhamDAO.timSPTheoLoai(boLoc);
            updateTable(dsDaLoc);
        }
    }

    //HÀM XỬ LÝ NÚT THÊM
    private void xuLyThem() {
        ThemSanPhamGUI pnlThemSP = new ThemSanPhamGUI();
        JDialog dialog = new JDialog();
        dialog.setTitle("Thêm sản phẩm mới");
        dialog.setModal(true); // Chặn tương tác với cửa sổ chính
        dialog.setResizable(false);
        dialog.setContentPane(pnlThemSP);
        dialog.pack(); // Tự động điều chỉnh kích thước JDialog cho vừa với panel
        dialog.setLocationRelativeTo(null);
        int maSPCuoiCung = SanPhamDAO.getMaSPCuoiCung();
        maSPCuoiCung++;
        String maSPNew = String.format("%04d", maSPCuoiCung);
        pnlThemSP.setTxtMaSanPham("SP-" + maSPNew);
        dialog.setVisible(true);

        SanPham spNew = pnlThemSP.getSanPhamMoi();

        if (spNew != null) {
            if (SanPhamDAO.themSanPham(spNew)) {
                JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!");
                updateTable();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm sản phẩm thất bại.");
            }
        }
    }

    //HÀM XỬ LÝ NÚT XÓA
    private void xuLyXoa() {
    int selectedRows[] = table.getSelectedRows();
    
    if (selectedRows.length == 0) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa.");
        return;
    }

    String message;
    if (selectedRows.length == 1) {
        // Cần kiểm tra lại: Cột 0 là Mã SP, Cột 1 là Tên SP (thường là vậy)
        // Nếu tên SP ở cột 2, code của bạn đúng. Nhưng cần đồng nhất cột 0, 1, 2
        String tenSP = model.getValueAt(selectedRows[0], 1).toString(); 
        message = "Bạn có chắc muốn xóa sản phẩm '" + tenSP + "' không?";
    } else {
        // Lỗi chính tả: ' sản phẩm'
        message = "Bạn có chắc muốn xóa " + selectedRows.length + " sản phẩm đã chọn không?";
    }

    int confirm = JOptionPane.showConfirmDialog(this,
            message,
            "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        int soLuongXoaThanhCong = 0;


        for (int i = selectedRows.length - 1; i >= 0; i--) { 
            int row = selectedRows[i];
            String maSP = model.getValueAt(row, 0).toString();
            
            if (SanPhamDAO.xoaSanPham(maSP)) {
                soLuongXoaThanhCong++;
            }
        }

        if (soLuongXoaThanhCong > 0) {
            JOptionPane.showMessageDialog(this, "Đã xóa thành công " + soLuongXoaThanhCong + " sản phẩm.");
            updateTable();
        } else {
            JOptionPane.showMessageDialog(this, "Xóa sản phẩm thất bại.", "Lỗi xóa", JOptionPane.ERROR_MESSAGE);
        }

    } 
    
    table.clearSelection(); 
}

    //HÀM XỬ LÝ NÚT SỬA
    private void xuLySua() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần sửa.");
            return;
        }

        String maSP = model.getValueAt(selectedRow, 0).toString();
        SanPham spCanSua = SanPhamDAO.timSPTheoMa(maSP);

        if (spCanSua == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm để sửa.");
            return;
        }

        ThemSanPhamGUI pnlThemSP = new ThemSanPhamGUI();
        JDialog dialog = new JDialog();
        dialog.setTitle("Sửa sản phẩm");
        dialog.setModal(true);
        dialog.setResizable(false);
        dialog.setContentPane(pnlThemSP);
        dialog.pack();
        dialog.setLocationRelativeTo(null);

        pnlThemSP.setTxtMaSanPham(spCanSua.getMaSP());
        pnlThemSP.setTxtTenSanPham(spCanSua.getTen());
        pnlThemSP.setTxtMoTa(spCanSua.getMoTa());
        pnlThemSP.setTxtThanhPhan(spCanSua.getThanhPhan());
        pnlThemSP.setCmbLoaiSanPham(spCanSua.getLoaiSanPham());
        pnlThemSP.setTxtSoLuongToiThieu(spCanSua.getTonToiThieu());
        pnlThemSP.setTxtSoLuongToiDa(spCanSua.getTonToiDa());

        dialog.setVisible(true);

        SanPham spNew = pnlThemSP.getSanPhamMoi();

        if (spNew != null) {
            if (SanPhamDAO.suaSanPham(maSP, spNew)) {
                JOptionPane.showMessageDialog(this, "Sửa sản phẩm thành công!");
                updateTable();
            } else {
                JOptionPane.showMessageDialog(this, "Sửa sản phẩm thất bại.");
            }
        }
    }

    private void hienThiChiTietSanPham(MouseEvent e) {
        int selectRow = table.getSelectedRow();
        if (selectRow != -1) {
            String maSP = model.getValueAt(selectRow, 0).toString();
            SanPham spDaChon = SanPhamDAO.timSPTheoMa(maSP);
            if (e.getClickCount() == 2) {
                ThemSanPhamGUI pnlThemSP = new ThemSanPhamGUI();
                JDialog dialog = new JDialog();
                dialog.setTitle("Thông tin chi tiết sản phẩm");
                dialog.setModal(true);
                dialog.setResizable(false);
                dialog.setContentPane(pnlThemSP);
                dialog.pack();
                dialog.setLocationRelativeTo(null);

                pnlThemSP.getBtnHuy().setVisible(false);
                pnlThemSP.getBtnXacNhan().setVisible(false);

                pnlThemSP.setTxtMaSanPham(spDaChon.getMaSP());
                pnlThemSP.setTxtTenSanPham(spDaChon.getTen());
                pnlThemSP.setTxtMoTa(spDaChon.getMoTa());
                pnlThemSP.setTxtThanhPhan(spDaChon.getThanhPhan());
                pnlThemSP.setCmbLoaiSanPham(spDaChon.getLoaiSanPham());
                pnlThemSP.setTxtSoLuongToiThieu(spDaChon.getTonToiThieu());
                pnlThemSP.setTxtSoLuongToiDa(spDaChon.getTonToiDa());

                dialog.setVisible(true);
            }
        }
    }
}
