/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import hethongnhathuocduocankhang.dao.NhaCungCapDAO;
import hethongnhathuocduocankhang.entity.NhaCungCap;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class QuanLiNhaCungCapGUI extends JPanel {

    private JButton btnThem, btnXoa, btnSua;
    private JTextField txtTimKiem;
    private JTable table;
    private JComboBox<String> cmbTieuChiTimKiem;
    private DefaultTableModel model;

    public QuanLiNhaCungCapGUI() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        // 1. TẠO PANEL NORTH
        JPanel pnlNorth = new JPanel();
        pnlNorth.setLayout(new BorderLayout());

        // 1.1. Panel Chức năng
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

        cmbTieuChiTimKiem = new JComboBox<>(new String[]{"Mã NCC", "Tên NCC", "Số điện thoại", "Email"}); // THAY ĐỔI
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

        // 3. TẠO PANEL CENTER (Bảng dữ liệu)
        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));

        String[] columnNames = {"Mã NCC", "Tên NCC", "Địa chỉ", "Số điện thoại", "Email"};
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

        // 4. THÊM CÁC PANEL VÀO PANEL CHÍNH
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

    private void updateTable(ArrayList<NhaCungCap> dsNCC) {
        model.setRowCount(0);
        if (dsNCC == null) {
            return;
        }
        for (NhaCungCap ncc : dsNCC) {
            Object[] row = {
                ncc.getMaNCC(),
                ncc.getTenNCC(),
                ncc.getDiaChi(),
                ncc.getSdt(),
                ncc.getEmail()
            };
            model.addRow(row);
        }
    }

    private void updateTable() {
        ArrayList<NhaCungCap> dsNCC = NhaCungCapDAO.getAllNhaCungCap();
        updateTable(dsNCC);
    }

    private void addEvents() {
        btnThem.addActionListener(e -> xuLyThem());
        btnXoa.addActionListener(e -> xuLyXoa());
        btnSua.addActionListener(e -> xuLySua());
        txtTimKiem.addActionListener(e -> xuLyTimKiem());
        cmbTieuChiTimKiem.addActionListener(e -> {
            txtTimKiem.setText("");
            txtTimKiem.requestFocus();
        });
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                hienThiChiTietNhaCungCap(e);
            }
        });
    }

    private void xuLyTimKiem() {
        String tuKhoa = txtTimKiem.getText().trim();
        String tieuChi = cmbTieuChiTimKiem.getSelectedItem().toString();
        ArrayList<NhaCungCap> dsKetQua = new ArrayList<>();

        if (tuKhoa.isEmpty()) {
            dsKetQua = NhaCungCapDAO.getAllNhaCungCap();
        } else {
            switch (tieuChi) {
                case "Mã NCC":
                    NhaCungCap ncc = NhaCungCapDAO.timNCCTheoMa(tuKhoa);
                    if (ncc != null) {
                        dsKetQua.add(ncc);
                    }
                    break;
                case "Tên NCC":
                    dsKetQua = NhaCungCapDAO.timNCCTheoTen(tuKhoa);
                    break;
                case "Số điện thoại":
                    dsKetQua = NhaCungCapDAO.timNCCTheoSDT(tuKhoa);
                    break;
                case "Email":
                    dsKetQua = NhaCungCapDAO.timNCCTheoEmail(tuKhoa);
                    break;
            }
        }
        updateTable(dsKetQua);
    }

    private void xuLyThem() {
        ThemNhaCungCapGUI pnlThemNCC = new ThemNhaCungCapGUI();
        JDialog dialog = new JDialog();
        dialog.setTitle("Thêm Nhà Cung Cấp Mới");
        dialog.setModal(true);
        dialog.setResizable(false);
        dialog.setContentPane(pnlThemNCC);
        dialog.pack();
        dialog.setLocationRelativeTo(null);

        // Tạo mã NCC mới sử dụng hàm đã sửa
        int maNCCCUoiCung = NhaCungCapDAO.getMaNCCCUoiCung();
        maNCCCUoiCung++;
        String maNCCNew = String.format("NCC-%04d", maNCCCUoiCung); // THAY ĐỔI format
        pnlThemNCC.setTxtMaNCC(maNCCNew);

        dialog.setVisible(true);

        NhaCungCap nccNew = pnlThemNCC.getNhaCungCapMoi();

        if (nccNew != null) {
            if (NhaCungCapDAO.themNhaCungCap(nccNew)) {
                JOptionPane.showMessageDialog(this, "Thêm nhà cung cấp thành công!");
                updateTable();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm nhà cung cấp thất bại (SĐT hoặc Email có thể đã tồn tại).");
            }
        }
    }

    private void xuLyXoa() {
        int selectedRows[] = table.getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp cần xóa.");
            return;
        }

        String message;
        if (selectedRows.length == 1) {
            String tenNCC = model.getValueAt(selectedRows[0], 1).toString(); // THAY ĐỔI cột tên
            message = "Bạn có chắc muốn xóa nhà cung cấp '" + tenNCC + "' không?";
        } else {
            message = "Bạn có chắc muốn xóa " + selectedRows.length + " nhà cung cấp đã chọn không?";
        }

        int confirm = JOptionPane.showConfirmDialog(this, message, "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            int soLuongXoaThanhCong = 0;
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                int row = selectedRows[i];
                String maNCC = model.getValueAt(row, 0).toString(); // THAY ĐỔI cột mã
                if (NhaCungCapDAO.xoaNhaCungCap(maNCC)) {
                    soLuongXoaThanhCong++;
                }
            }

            if (soLuongXoaThanhCong > 0) {
                JOptionPane.showMessageDialog(this, "Đã xóa thành công " + soLuongXoaThanhCong + " nhà cung cấp."); 
                updateTable();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa nhà cung cấp thất bại (có thể do nhà cung cấp đang cung cấp sản phẩm).", "Lỗi xóa", JOptionPane.ERROR_MESSAGE);
            }
        }
        table.clearSelection();
    }

    private void xuLySua() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp cần sửa."); 
            return;
        }

        String maNCC = model.getValueAt(selectedRow, 0).toString(); 
        NhaCungCap nccCanSua = NhaCungCapDAO.timNCCTheoMa(maNCC); 

        if (nccCanSua == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy nhà cung cấp để sửa."); 
            return;
        }

        ThemNhaCungCapGUI pnlThemNCC = new ThemNhaCungCapGUI(); 
        JDialog dialog = new JDialog();
        dialog.setTitle("Sửa thông tin Nhà Cung Cấp"); 
        dialog.setModal(true);
        dialog.setResizable(false);
        dialog.setContentPane(pnlThemNCC);
        dialog.pack();
        dialog.setLocationRelativeTo(null);

        // Đổ dữ liệu cũ
        pnlThemNCC.setTxtMaNCC(nccCanSua.getMaNCC());   
        pnlThemNCC.setTxtTenNCC(nccCanSua.getTenNCC());  
        pnlThemNCC.setTxtDiaChi(nccCanSua.getDiaChi());   
        pnlThemNCC.setTxtSDT(nccCanSua.getSdt());      
        pnlThemNCC.setTxtEmail(nccCanSua.getEmail());   

        dialog.setVisible(true);

        NhaCungCap nccNew = pnlThemNCC.getNhaCungCapMoi(); 

        if (nccNew != null) {
            if (NhaCungCapDAO.suaNhaCungCap(maNCC, nccNew)) { 
                JOptionPane.showMessageDialog(this, "Sửa thông tin nhà cung cấp thành công!");
                updateTable();
            } else {
                JOptionPane.showMessageDialog(this, "Sửa thông tin nhà cung cấp thất bại (SĐT hoặc Email có thể đã tồn tại)."); 
            }
        }
    }

    private void hienThiChiTietNhaCungCap(MouseEvent e) { 
        int selectRow = table.getSelectedRow();
        if (selectRow != -1) {
            String maNCC = model.getValueAt(selectRow, 0).toString(); 
            NhaCungCap nccDaChon = NhaCungCapDAO.timNCCTheoMa(maNCC); 

            if (nccDaChon != null && e.getClickCount() == 2) {
                ThemNhaCungCapGUI pnlThemNCC = new ThemNhaCungCapGUI(); 
                JDialog dialog = new JDialog();
                dialog.setTitle("Thông tin chi tiết Nhà Cung Cấp"); 
                dialog.setModal(true);
                dialog.setResizable(false);
                dialog.setContentPane(pnlThemNCC);
                dialog.pack();
                dialog.setLocationRelativeTo(null);

                // Chỉ xem, không sửa
                pnlThemNCC.getBtnHuy().setVisible(false);
                pnlThemNCC.getBtnXacNhan().setText("Đóng");
                pnlThemNCC.getBtnXacNhan().addActionListener(l -> dialog.dispose());

                pnlThemNCC.getTxtTenNCC().setEditable(false); 
                pnlThemNCC.getTxtDiaChi().setEditable(false); 
                pnlThemNCC.getTxtSDT().setEditable(false);    
                pnlThemNCC.getTxtEmail().setEditable(false);  

                // Đổ dữ liệu
                pnlThemNCC.setTxtMaNCC(nccDaChon.getMaNCC());    
                pnlThemNCC.setTxtTenNCC(nccDaChon.getTenNCC());   
                pnlThemNCC.setTxtDiaChi(nccDaChon.getDiaChi());   
                pnlThemNCC.setTxtSDT(nccDaChon.getSdt());     
                pnlThemNCC.setTxtEmail(nccDaChon.getEmail());   

                dialog.setVisible(true);
            }
        }
    }
}
