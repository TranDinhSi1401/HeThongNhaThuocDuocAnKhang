/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import hethongnhathuocduocankhang.bus.QuanLyLoBUS;
import hethongnhathuocduocankhang.dao.ChiTietPhieuNhapDAO;
import hethongnhathuocduocankhang.dao.DonViTinhDAO;
import hethongnhathuocduocankhang.dao.LichSuLoDAO;
import hethongnhathuocduocankhang.dao.LoSanPhamDAO;
import hethongnhathuocduocankhang.dao.NhaCungCapDAO;
import hethongnhathuocduocankhang.dao.SanPhamCungCapDAO;
import hethongnhathuocduocankhang.dao.MaVachSanPhamDAO;
import hethongnhathuocduocankhang.dao.NhanVienDAO;
import hethongnhathuocduocankhang.dao.PhieuNhapDAO;
import hethongnhathuocduocankhang.dao.SanPhamDAO;
import hethongnhathuocduocankhang.entity.ChiTietPhieuNhap;
import hethongnhathuocduocankhang.entity.DonViTinh;
import hethongnhathuocduocankhang.entity.LichSuLo;
import hethongnhathuocduocankhang.entity.LoSanPham;
import hethongnhathuocduocankhang.entity.MaVachSanPham;
import hethongnhathuocduocankhang.entity.NhaCungCap;
import hethongnhathuocduocankhang.entity.NhanVien;
import hethongnhathuocduocankhang.entity.PhieuNhap;
import hethongnhathuocduocankhang.entity.SanPham;
import hethongnhathuocduocankhang.entity.TaiKhoan;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import org.apache.poi.ss.usermodel.DateUtil;
import java.io.File;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 *
 * @author admin
 */
public class LoSanPhamGUI extends javax.swing.JPanel {
    
    private String ngayLap;
    TaiKhoan tk = GiaoDienChinhGUI.getTk();
    /**
     * Creates new form LoSanPhamGUI
     * @throws java.sql.SQLException
     */
    public LoSanPhamGUI() throws SQLException {
        initComponents();
        focusTxt(txtTimKiem, "Nhập mã lô...");
        focusTxt(txtMaLoSP, "Nhập thông tin tìm kiếm...");
        
        tblTab.addChangeListener(e->{
            int x = tblTab.getSelectedIndex();
            if(x==0){
                reLoadQuanLyLo();
            }else if(x==1){
                try {
                    reLoadTheoDoiVaCanhBao();
                } catch (SQLException ex) {
                    Logger.getLogger(LoSanPhamGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        // tải dữ liệu của các lô hàng đang có vào bảng
        //loadTuPlashScreening();
        loadDanhSachLoSanPham();
        // kiểm tra trạng thái của các lô hàng
        capNhatSoLo();
        //tblLoSanPham.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        loadLichSuLo();
        tblLoSanPham.getColumnModel().getColumn(0).setPreferredWidth(120);
        tblLoSanPham.getColumnModel().getColumn(1).setPreferredWidth(307);
        tblLoSanPham.getColumnModel().getColumn(2).setPreferredWidth(200);
        tblLoSanPham.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblLoSanPham.getColumnModel().getColumn(4).setPreferredWidth(100);
        
        tblThemSanPham.getColumnModel().getColumn(0).setPreferredWidth(80);//0
        tblThemSanPham.getColumnModel().getColumn(1).setPreferredWidth(260);
        tblThemSanPham.getColumnModel().getColumn(2).setPreferredWidth(130);
        tblThemSanPham.getColumnModel().getColumn(3).setPreferredWidth(180);
        tblThemSanPham.getColumnModel().getColumn(4).setPreferredWidth(80);//4
        tblThemSanPham.getColumnModel().getColumn(5).setPreferredWidth(90);
        tblThemSanPham.getColumnModel().getColumn(6).setPreferredWidth(90);
        tblThemSanPham.getColumnModel().getColumn(7).setPreferredWidth(90);
        tblThemSanPham.getColumnModel().getColumn(8).setPreferredWidth(90);
        tblThemSanPham.getColumnModel().getColumn(9).setPreferredWidth(80);
        tblThemSanPham.getColumnModel().getColumn(10).setPreferredWidth(60);
        tblThemSanPham.getColumnModel().getColumn(11).setPreferredWidth(110);

        tblKetQua.getColumnModel().getColumn(0).setPreferredWidth(70);
        tblKetQua.getColumnModel().getColumn(1).setPreferredWidth(250);
        tblKetQua.getColumnModel().getColumn(2).setPreferredWidth(150);
        tblKetQua.getColumnModel().getColumn(3).setPreferredWidth(230);
        tblKetQua.getColumnModel().getColumn(4).setPreferredWidth(60);
        tblKetQua.getColumnModel().getColumn(5).setPreferredWidth(75);
        tblKetQua.getColumnModel().getColumn(6).setPreferredWidth(75);
        tblKetQua.getColumnModel().getColumn(7).setPreferredWidth(80);


        tblThemSanPham.getTableHeader().setReorderingAllowed(false);
        tblLoSanPham.getTableHeader().setReorderingAllowed(false);
        tblKetQua.getTableHeader().setReorderingAllowed(false);
        tblLichSuHoatDong.getTableHeader().setReorderingAllowed(false);

        // truyền dữ liệu để hiển thị
        tblLoSanPham.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (e.getValueIsAdjusting()) {
                int x1 = tblLoSanPham.getSelectedRow();
                if (x1 >= 0) {
                    String maSP = tblLoSanPham.getValueAt(x1, 0).toString();
                    String ten = tblLoSanPham.getValueAt(x1, 1).toString();
                    String maLO = tblLoSanPham.getValueAt(x1, 2).toString();
                    String donVi = tblLoSanPham.getValueAt(x1, 3).toString();
                    String sl = tblLoSanPham.getValueAt(x1, 4).toString();
                    NhaCungCap ncc = NhaCungCapDAO.timNCCTheoMa(SanPhamCungCapDAO.getSanPhamCungCap(maSP).getNhaCungCap().getMaNCC());
                    LoSanPham lo = LoSanPhamDAO.timLoSanPham(maLO);
                    
                    txtMaSanPham.setText(maSP);
                    txtTenSanPham.setText(ten);
                    txtMaLo.setText(maLO);
                    txtNhaCungCap.setText(ncc.getTenNCC());
                    txtNgaySanXuat.setText(lo.getNgaySanXuat().toString());
                    txtNgayHetHan.setText(lo.getNgayHetHan().toString());
                    txtDonViTinh.setText(donVi);
                    txtSoLuong.setText(sl);
                    txtGiaNhap.setText(SanPhamCungCapDAO.getSanPhamCungCap(maSP).getGiaNhap()+ " ₫");
                }
            }
        });
        
        SwingUtilities.invokeLater(() -> {
            tblTab.requestFocusInWindow();
            cmbTimKiemTheo.requestFocusInWindow();
            cmbTimKiemTheo.setSelectedIndex(0);
        });


        tblTab.addChangeListener((ChangeEvent e) -> {
        int index = tblTab.getSelectedIndex();
            switch (index) {
                case 0 -> CanhBao.requestFocusInWindow();
                case 1 -> QuanLyLo.requestFocusInWindow();
                default -> {
                }
            }
    });

        mapKeyToFocus("F3", txtTimKiem, QuanLyLo);
        mapKeyToFocus("F3", txtMaLoSP, CanhBao);
        mapKeyToClickButton("F4", btnXacNhan, QuanLyLo);
        mapKeyToClickButton("F4", btnTimTheoThongTin, CanhBao);
        mapKeyToClickButton("F6", btnThemSanPhamTuExcel, QuanLyLo);
        mapKeyToClickButton("F7", btnChonTatCa, QuanLyLo);
        mapKeyToClickButton("F8", btnTimLoHetHan, QuanLyLo);
        mapKeyToClickButton("F9", btnHuyLo, QuanLyLo);
        mapKeyToClickButton("F10", btnXoaTrangLo, QuanLyLo);
        mapKeyToClickButton("F10", txtLamMoi, CanhBao);
        
        btnThemSanPhamTuExcel.setToolTipText("Thêm danh sách lô từ file excel");
        btnChonTatCa.setToolTipText("Chọn hoặc hủy chọn tất cả");
        btnXoaSanPham.setToolTipText("Xóa sản phẩm được chọn");
        btnXacNhan.setToolTipText("Nhập lô được chọn");
        btnTimLoHetHan.setToolTipText("Tìm danh sách lô hết hạn");

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        tblTab = new javax.swing.JTabbedPane();
        CanhBao = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtConHan = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtSapHetHan = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtHetHan = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtDaHuy = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblLichSuHoatDong = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        txtLamMoi = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtMaLoSP = new javax.swing.JTextField();
        cmbTrangThai = new javax.swing.JComboBox<>();
        btnTimTheoThongTin = new javax.swing.JButton();
        cmbTimKiemTheo = new javax.swing.JComboBox<>();
        jPanel21 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblKetQua = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        QuanLyLo = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblThemSanPham = new javax.swing.JTable();
        btnThemSanPhamTuExcel = new javax.swing.JButton();
        btnXacNhan = new javax.swing.JButton();
        btnXoaSanPham = new javax.swing.JButton();
        btnChonTatCa = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtMaSanPham = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTenSanPham = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtNhaCungCap = new javax.swing.JTextField();
        txtNgaySanXuat = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtNgayHetHan = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtGiaNhap = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtMaLo = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtDonViTinh = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        btnXoaTrangLo = new javax.swing.JButton();
        btnTimLoHetHan = new javax.swing.JButton();
        jPanel23 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLoSanPham = new javax.swing.JTable();
        btnHuyLo = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        CanhBao.setLayout(new java.awt.BorderLayout());

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông kê tổng quan"));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.GridLayout(1, 4, 10, 15));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Còn hạn:");

        txtConHan.setText("0");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtConHan)
                .addContainerGap(146, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtConHan))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel6);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Sắp hết hạn:");

        txtSapHetHan.setText("0");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSapHetHan)
                .addContainerGap(124, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtSapHetHan))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel15);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Hết hạn:");

        txtHetHan.setText("0");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtHetHan)
                .addContainerGap(149, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtHetHan))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel16);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("Đã hủy:");

        txtDaHuy.setText("0");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDaHuy)
                .addContainerGap(154, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtDaHuy))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel17);

        jPanel8.add(jPanel4, java.awt.BorderLayout.CENTER);

        CanhBao.add(jPanel8, java.awt.BorderLayout.PAGE_START);

        tblLichSuHoatDong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã lô", "Thời gian", "Loại thao tác", "Số lượng sau", "Ghi chú", "Người thực hiện"
            }
        ));
        jScrollPane4.setViewportView(tblLichSuHoatDong);

        jLabel12.setText("Lịch sử hoạt động");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 920, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        CanhBao.add(jPanel7, java.awt.BorderLayout.PAGE_END);

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm lô"));
        jPanel14.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 910, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel14.add(jPanel18, java.awt.BorderLayout.PAGE_START);

        jPanel19.setLayout(new java.awt.BorderLayout());

        txtLamMoi.setText("Xóa trắng [F10]");
        txtLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLamMoiActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Tìm kiếm theo:");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel18.setText("Trạng thái:");

        txtMaLoSP.setText("Nhập thông tin tìm kiếm... [F3]");
        txtMaLoSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaLoSPActionPerformed(evt);
            }
        });

        cmbTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Còn hạn", "Sắp hết hạn", "Hết hạn", "Đã hủy" }));
        cmbTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTrangThaiActionPerformed(evt);
            }
        });

        btnTimTheoThongTin.setText("Tìm [F4]");
        btnTimTheoThongTin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimTheoThongTinActionPerformed(evt);
            }
        });

        cmbTimKiemTheo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Mã lô sản phẩm", "Mã sản phẩm", "Tên sản phẩm", "Nhà cung cấp" }));
        cmbTimKiemTheo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTimKiemTheoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbTimKiemTheo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaLoSP, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                .addGap(31, 31, 31)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTimTheoThongTin, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLamMoi)
                .addGap(10, 10, 10))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(cmbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnTimTheoThongTin, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtMaLoSP, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTimKiemTheo, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel19.add(jPanel20, java.awt.BorderLayout.PAGE_START);

        tblKetQua.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Mã lô", "Nhà cung cấp", "Số lượng(viên)", "Ngày sản xuất", "Ngày hết hạn", "Trạng thái"
            }
        ));
        jScrollPane2.setViewportView(tblKetQua);

        jLabel9.setText("Kết quả tìm kiếm");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 910, Short.MAX_VALUE)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(4, 4, 4)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
        );

        jPanel19.add(jPanel21, java.awt.BorderLayout.CENTER);

        jPanel14.add(jPanel19, java.awt.BorderLayout.CENTER);

        CanhBao.add(jPanel14, java.awt.BorderLayout.CENTER);

        tblTab.addTab("Theo dõi & Cảnh báo", CanhBao);

        QuanLyLo.setLayout(new java.awt.BorderLayout());

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách sản phẩm mới"));

        tblThemSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Mã lô", "Nhà cung cấp", "Đơn vị tính", "Ngày sản xuất", "Ngày hết hạn", "Số lượng đặt", "Số lượng giao", "Giá nhập", "Chọn", "Ghi chú"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblThemSanPham);

        btnThemSanPhamTuExcel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnThemSanPhamTuExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/excel.png")));
        btnThemSanPhamTuExcel.setText("Excel [F6]");
        btnThemSanPhamTuExcel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnThemSanPhamTuExcel.setIconTextGap(6);
        btnThemSanPhamTuExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSanPhamTuExcelActionPerformed(evt);
            }
        });

        btnXacNhan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXacNhan.setText("Thêm lô [F4]");
        btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanActionPerformed(evt);
            }
        });

        btnXoaSanPham.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXoaSanPham.setText("Xóa [F9]");
        btnXoaSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSanPhamActionPerformed(evt);
            }
        });

        btnChonTatCa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnChonTatCa.setText("Chọn tất cả [F7]");
        btnChonTatCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonTatCaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 898, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnThemSanPhamTuExcel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnChonTatCa, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnXoaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThemSanPhamTuExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChonTatCa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE))
        );

        QuanLyLo.add(jPanel12, java.awt.BorderLayout.PAGE_START);

        jPanel13.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin chi tiết"));

        jLabel3.setText("Mã sản phẩm:");

        jLabel4.setText("Tên sản phầm:");

        txtTenSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenSanPhamActionPerformed(evt);
            }
        });

        jLabel5.setText("Nhà cung cấp:");

        txtNhaCungCap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNhaCungCapActionPerformed(evt);
            }
        });

        txtNgaySanXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgaySanXuatActionPerformed(evt);
            }
        });

        jLabel6.setText("Ngày sản xuất:");

        jLabel7.setText("Ngày hết hạn");

        txtNgayHetHan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayHetHanActionPerformed(evt);
            }
        });

        jLabel8.setText("Giá nhập:");

        txtGiaNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaNhapActionPerformed(evt);
            }
        });

        jLabel25.setText("Mã lô:");

        txtMaLo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaLoActionPerformed(evt);
            }
        });

        jLabel14.setText("Đơn vị tính:");

        txtDonViTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDonViTinhActionPerformed(evt);
            }
        });

        jLabel15.setText("Số lượng:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                            .addComponent(txtMaSanPham)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtGiaNhap, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNgayHetHan, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNgaySanXuat, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel25)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDonViTinh, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNhaCungCap, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtMaLo, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtSoLuong))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(txtMaLo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNhaCungCap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtDonViTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNgaySanXuat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNgayHetHan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel13.add(jPanel1, java.awt.BorderLayout.LINE_END);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách lô"));
        jPanel3.setLayout(new java.awt.BorderLayout());

        txtTimKiem.setText("Nhập mã lô...[F3]");
        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        btnXoaTrangLo.setText("Xóa trắng [F10]");
        btnXoaTrangLo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaTrangLoActionPerformed(evt);
            }
        });

        btnTimLoHetHan.setText("Tìm lô hết hạn [F8]");
        btnTimLoHetHan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimLoHetHanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXoaTrangLo)
                .addGap(12, 12, 12)
                .addComponent(btnTimLoHetHan, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                        .addComponent(btnXoaTrangLo, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                    .addComponent(btnTimLoHetHan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel3.add(jPanel22, java.awt.BorderLayout.PAGE_START);

        jPanel23.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách lô trong hệ thống"));

        tblLoSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Mã lô", "Đơn vị tính", "Số lượng"
            }
        ));
        jScrollPane1.setViewportView(tblLoSanPham);

        btnHuyLo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnHuyLo.setText("Hủy lô [F9]");
        btnHuyLo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyLoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnHuyLo)))
                .addContainerGap())
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnHuyLo, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.add(jPanel23, java.awt.BorderLayout.CENTER);

        jPanel13.add(jPanel3, java.awt.BorderLayout.CENTER);

        QuanLyLo.add(jPanel13, java.awt.BorderLayout.CENTER);

        tblTab.addTab("Quản lý lô", QuanLyLo);

        jPanel2.add(tblTab);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnHuyLoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyLoActionPerformed
        DefaultTableModel tbl = (DefaultTableModel) tblLoSanPham.getModel();
        int x = tblLoSanPham.getSelectedRow();
        if(tblLoSanPham.getRowCount()==0){
            JOptionPane.showMessageDialog(this, "Không có lô có thể bán, vui lòng thêm rồi thử lại");
            return;
        }    
        if(x<0){
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lô cần hủy rồi thử lại");
            return;
        }
        String maLo = tbl.getValueAt(x, 2).toString();
        int check = JOptionPane.showConfirmDialog(this, "Xác nhận hủy lô "+maLo, "Xác nhận", JOptionPane.YES_NO_OPTION);
        if(check==JOptionPane.YES_OPTION){
            JTextArea noiDungXoaLo = new JTextArea();
            JScrollPane cuon = new JScrollPane(noiDungXoaLo);
            int nhap = JOptionPane.showConfirmDialog(null, cuon, "Nhập lý do hủy lô(Thông tin bắt buộc)", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if(nhap != JOptionPane.OK_OPTION) return; 
            String noiDungsXL = noiDungXoaLo.getText();
            LoSanPham loDuocTim =  LoSanPhamDAO.timLoSanPham(maLo);
            if(!LoSanPhamDAO.huyLoSanPham(loDuocTim)){
                JOptionPane.showMessageDialog(this, "Hủy lô"+loDuocTim.getMaLoSanPham()+"thất bại");
                return;
            }
            JOptionPane.showMessageDialog(this, "Hủy lô "+loDuocTim.getMaLoSanPham()+" thành công");
            tbl.setRowCount(0);
            loadLaiDanhSachLo();
            LichSuLoDAO.addLichSuLo(loDuocTim, tk.getNhanVien() , "HUY_LO", 0, noiDungsXL);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHuyLoActionPerformed

    private void txtNhaCungCapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNhaCungCapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNhaCungCapActionPerformed

    private void txtLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLamMoiActionPerformed
        DefaultTableModel tbl = (DefaultTableModel) tblKetQua.getModel();
        tbl.setRowCount(0);
        cmbTrangThai.setSelectedIndex(0);
        cmbTimKiemTheo.setSelectedIndex(0);
        txtMaLoSP.setText("");

        // TODO add your handling code here:
    }//GEN-LAST:event_txtLamMoiActionPerformed

    private void btnThemSanPhamTuExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSanPhamTuExcelActionPerformed
        DefaultTableModel tbl = (DefaultTableModel) tblThemSanPham.getModel();
        if(tbl.getRowCount()>0){
                JOptionPane.showMessageDialog(this, "Vui lòng xóa các sản phẩn hiện có rồi thử lại."); 
                return;
            }
        JFileChooser file = new JFileChooser();
        file.setFileFilter(new FileNameExtensionFilter("Excel Files", "xlsx"));
        int result = file.showOpenDialog(this);
        if(result!=JFileChooser.APPROVE_OPTION) return;
        File filee = file.getSelectedFile();
        try(FileInputStream test = new FileInputStream(filee)){
        }catch(Exception e){
            return;
        }
        List<MaVachSanPham> dsMaVach = MaVachSanPhamDAO.timMaSPTheoMaVach();
        Map<String, MaVachSanPham> mapMaVach = new HashMap<>();
        for(MaVachSanPham mv : dsMaVach){
            mapMaVach.put(mv.getMaVach(), mv);
        }
        try(FileInputStream fis = new FileInputStream(filee);
                XSSFWorkbook work = new XSSFWorkbook(fis)){
            XSSFSheet sheet = work.getSheetAt(0);
            
            //boolean head = true;
            int soSP = 0;
            int skip=0;
            final int colMaVach = 0;
            final int colSoLuongDat = 7;
            final int colSoLuongGiao = 8;
            final int colGiaNhap=9;
            
            for(Row is:sheet){
                if(skip<3){
                    skip++;
                    continue;
                }
                int lastCell = is.getLastCellNum();
                if(lastCell<=0) continue;
                Vector<Object> rowData = new Vector<>();
                for(int i=0;i<lastCell;i++){
                    Cell c = is.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    Object value;
                    switch(c.getCellType()){
                        case STRING -> {
                            String clear = c.getStringCellValue().trim();
                            value = clear;
                        }case NUMERIC ->{
                            if (DateUtil.isCellDateFormatted(c))
                                value = c.getDateCellValue();
                            else{
                                value = c.getNumericCellValue();
                            }
                        }case BOOLEAN -> value=c.getBooleanCellValue();
                        case FORMULA -> value = c.getCellFormula();
                        default -> value = "";
                             
                    }rowData.add(value);
                }
                if(colMaVach<rowData.size()){
                    Object as = rowData.get(colMaVach);
                    String maVachh = as.toString().trim();
                    MaVachSanPham maV = mapMaVach.get(maVachh);
                    if(maV==null){
                        JOptionPane.showMessageDialog(this, "Mã sản phẩm "+maVachh+" không tìm thấy trong hệ thống. Vui lòng kiểm tra rồi thử lại.",
                                "Lỗi dữ liệu", JOptionPane.ERROR_MESSAGE); continue;
                    }rowData.set(colMaVach, maV.getSanPham().getMaSP());
                    
                }
                if(colSoLuongDat<rowData.size()){
                    Object as = rowData.get(colSoLuongDat);
                    double soLuongDat =-1;
                    if(as instanceof Double i) soLuongDat =i;
                    else if (as instanceof String s && !s.isBlank()) 
                            soLuongDat = Double.parseDouble(s.trim());
                    if(soLuongDat <0){
                        JOptionPane.showMessageDialog(this, "Lỗi tại dòng "+(soSP+2)+
                                " : Số lượng phải lớn hơn 0.", "Dữ liệu không hợp lệ", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    rowData.set(colSoLuongDat, (int)soLuongDat);
                }
                if(colSoLuongGiao<rowData.size()){
                    Object as = rowData.get(colSoLuongGiao);
                    double soLuongGiao =-1;
                    if(as instanceof Double i) soLuongGiao =i;
                    else if (as instanceof String s && !s.isBlank()) 
                            soLuongGiao = Double.parseDouble(s.trim());
                    if(soLuongGiao <0){
                        JOptionPane.showMessageDialog(this, "Lỗi tại dòng "+(soSP+2)+
                                " : Số lượng phải lớn hơn 0.", "Dữ liệu không hợp lệ", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    rowData.set(colSoLuongGiao, (int)soLuongGiao);
                }
                if (colGiaNhap<rowData.size()){
                    Object as = rowData.get(colGiaNhap);
                    double giaNhap = -1;
                    if(as instanceof Double d) giaNhap = d;
                    else if (as instanceof String s && !s.isBlank())
                        giaNhap = Double.parseDouble(s.trim());
                    if(giaNhap<0){
                        JOptionPane.showMessageDialog(this, "Lỗi tại dòng "+(soSP+2)+": Giá nhập phải lớn hơn 0", "Dữ liệu không hợp lệ"
                                , JOptionPane.ERROR_MESSAGE); return;
                    }
                    rowData.set(colGiaNhap, giaNhap);
                }
                int colCount = tbl.getColumnCount();
                while(rowData.size()<colCount){
                    if(rowData.size()==colCount-2 && Boolean.class.equals(tbl.getColumnClass(colCount-2))){
                        rowData.add(Boolean.FALSE);
                    }else{
                        rowData.add("");
                    }
                }
                tbl.addRow(rowData);
                soSP++;
            }
            Row date = sheet.getRow(2);
            Cell c = date.getCell(0);
            String layNgay = c.getStringCellValue();
            String ngayTaoExcel = layNgay.split(":")[1].trim();
            ngayLap = QuanLyLoBUS.chuyenDinhDang(ngayTaoExcel);
            JOptionPane.showMessageDialog(this, "Thêm thành công "+soSP+" từ file excel!");
        } catch(Exception e){
            JOptionPane.showMessageDialog(this, "Lỗi đọc file: "+e.getMessage());
            //e.printStackTrace();
        }        
        // TODO add your handling code here:
        //System.out.println("ngaytao: " + ngayLap);
    }//GEN-LAST:event_btnThemSanPhamTuExcelActionPerformed

    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanActionPerformed
        DefaultTableModel tbl = (DefaultTableModel) tblThemSanPham.getModel();
        int kiemTra = 0;
        for (int i=0; i<tbl.getRowCount(); i++){
            Boolean chon = (Boolean) tbl.getValueAt(i, 10);
            if(chon != null && chon == true) kiemTra++;
        }
        if (tbl.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Chưa có sản phẩm! Vui lòng thêm từ Excel.");
            return;
        }
        if(kiemTra == 0){
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn lô cần thêm, vui lòng chọn lô cần thêm rồi thử lại sau");
            return;
        }
        double tongTien = 0;
        for (int i = 0; i < tbl.getRowCount(); i++) {
            boolean check = (Boolean) tbl.getValueAt(i, 10);
            if (check) {
                int slGiao = Integer.parseInt(tbl.getValueAt(i, 8).toString());
                double gia = Double.parseDouble(tbl.getValueAt(i, 9).toString());
                tongTien += (double) slGiao * gia;
            }
        }
        JTextArea ghiChuPhieuNhap = new JTextArea();
        JScrollPane cuon = new JScrollPane(ghiChuPhieuNhap);
        int nhap = JOptionPane.showConfirmDialog(null, cuon, "Nhập ghi chú nếu có", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(nhap != JOptionPane.OK_OPTION) return; 
        String ghiChuPN = ghiChuPhieuNhap.getText();
        LocalDate ngay = LocalDate.parse(ngayLap); 
        if(!PhieuNhapDAO.themPhieuNhap(ngay, tongTien, ghiChuPN)) {
            JOptionPane.showMessageDialog(this, "Thêm phiếu nhập thất bại!");
            return;
        }
        PhieuNhap pn = PhieuNhapDAO.getPhieuNhapMoiNhat();
        boolean coLoiBoSung = false;
        for (int i=0; i<tbl.getRowCount();i++) {
            boolean check = (Boolean) tbl.getValueAt(i, 10);
            if (!check) continue;
            String maLo = tbl.getValueAt(i, 2).toString();
            int slGiao = Integer.parseInt(tbl.getValueAt(i, 8).toString());
            LoSanPham loCu = LoSanPhamDAO.timLoSanPham(maLo);
            if (loCu != null){
                
                ChiTietPhieuNhap ctpn = ChiTietPhieuNhapDAO.getChiTietPhieuNhap(loCu.getMaLoSanPham());
                int soLuongSauBoSung = loCu.getSoLuong() + slGiao;                
                if(soLuongSauBoSung > ctpn.getSoLuongYeuCau()){ 
                    
                    JOptionPane.showMessageDialog(this, 
                        "Lô "+ loCu.getMaLoSanPham() + 
                        " bị từ chối vì tổng số lượng (" + soLuongSauBoSung + 
                        ") đã vượt quá số lượng đặt ban đầu (" + ctpn.getSoLuongYeuCau() + ")",
                        "Lỗi bổ sung lô", JOptionPane.ERROR_MESSAGE);
                    coLoiBoSung = true; 
                    break;
                }
            }
        }
        if (coLoiBoSung) {
            PhieuNhapDAO.xoaPhieuNhap(pn);
            return; 
        }
        ArrayList<Integer> dsIndex = new ArrayList<>();
        for (int i=0; i<tbl.getRowCount();i++) {
            boolean check = (Boolean) tbl.getValueAt(i, 10);
            if (!check) continue;
            String maSP = tbl.getValueAt(i, 0).toString();
            String maLo = tbl.getValueAt(i, 2).toString();
            String tenNcc = tbl.getValueAt(i, 3).toString();
            LocalDate sx = LocalDate.parse(QuanLyLoBUS.chuyenDinhDang(tbl.getValueAt(i, 5).toString()));
            LocalDate hh = LocalDate.parse(QuanLyLoBUS.chuyenDinhDang(tbl.getValueAt(i, 6).toString()));
            int slDat = Integer.parseInt(tbl.getValueAt(i, 7).toString());
            int slGiao = Integer.parseInt(tbl.getValueAt(i, 8).toString());
            double giaNhap = Double.parseDouble(tbl.getValueAt(i, 9).toString());
            String ghiChu = tbl.getValueAt(i, 11) == null ? "" : tbl.getValueAt(i, 11).toString();
            LoSanPham loMoi = new LoSanPham(maLo, new SanPham(maSP), slGiao, sx, hh, false);
            LoSanPham loCu = LoSanPhamDAO.timLoSanPham(maLo);
            NhaCungCap ncc = NhaCungCapDAO.getNhaCungCapTheoTen(tenNcc);
            if (loCu != null){
                LoSanPhamDAO.capNhatSoLuongLo(loCu, slGiao); 
                LichSuLoDAO.addLichSuLo(loCu, tk.getNhanVien(), "BO_SUNG_SO_LUONG", slGiao, ghiChu);
            } else {
                SanPham sp = SanPhamDAO.timSPTheoMa(loMoi.getSanPham().getMaSP());
                if(QuanLyLoBUS.tongSoLuongTheoSanPham(sp.getMaSP(), loMoi)){
                    JOptionPane.showMessageDialog(this, "Lô có sô lượng vượt quá số lượng tối đa là "
                            +sp.getTonToiDa() +" của sản phẩm "+sp.getMaSP(), "cảnh báo", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                LoSanPhamDAO.themLoSanPham(loMoi); 
                LichSuLoDAO.addLichSuLo(loMoi, tk.getNhanVien(), "NHAP_LO", slGiao, ghiChu);
            }
            ChiTietPhieuNhapDAO.themChiTietPhieuNhap(loMoi, pn, ncc, giaNhap, tongTien, slDat, ghiChu);
            dsIndex.add(i);
    }
    JOptionPane.showMessageDialog(this, "Thêm lô sản phẩm thành công!");
    ((DefaultTableModel) tblLoSanPham.getModel()).setRowCount(0);
    loadLaiDanhSachLo();
    xoaLoVuaThem(dsIndex);
    }//GEN-LAST:event_btnXacNhanActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        String maLo = txtTimKiem.getText().trim();
        if(maLo.isEmpty() || maLo.equals("Nhập mã lô...")){
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã lô !");
            return;
        }
        if (LoSanPhamDAO.timLoSanPham(maLo)==null){
            JOptionPane.showMessageDialog(this, "Không tìm thấy lô sản phẩm có mã: "+ maLo); 
            return;
        }
        LoSanPham lo = LoSanPhamDAO.timLoSanPham(maLo);
        if(lo.isDaHuy()){
            JOptionPane.showMessageDialog(this, "Lô "+maLo+" đã bị hủy.");
            return;
        }
        DefaultTableModel tbl = (DefaultTableModel) tblLoSanPham.getModel();
        tbl.setRowCount(0);       
        DonViTinh donVi = DonViTinhDAO.getMotDonViTinhTheoMaSP(lo.getSanPham().getMaSP());
        NhaCungCap ncc = NhaCungCapDAO.timNCCTheoMa(SanPhamCungCapDAO.getSanPhamCungCap(lo.getSanPham().getMaSP()).getNhaCungCap().getMaNCC());
        tbl.addRow(new Object[] {lo.getSanPham().getMaSP(), 
                                    SanPhamDAO.timSPTheoMa(lo.getSanPham().getMaSP()).getTen(),
                                    lo.getMaLoSanPham(),  
                                    donVi.getTenDonVi(),  
                                    lo.getSoLuong()});
        txtMaLo.setText(maLo);
        txtTenSanPham.setText(SanPhamDAO.timSPTheoMa(lo.getSanPham().getMaSP()).getTen());
        txtMaSanPham.setText(lo.getSanPham().getMaSP());
        txtNhaCungCap.setText(ncc.getTenNCC());
        txtDonViTinh.setText(donVi.getTenDonVi());
        txtSoLuong.setText(lo.getSoLuong()+"");
        txtNgaySanXuat.setText(lo.getNgaySanXuat()+"");
        txtNgayHetHan.setText(lo.getNgayHetHan()+"");
        txtGiaNhap.setText(SanPhamCungCapDAO.getSanPhamCungCap(lo.getSanPham().getMaSP()).getGiaNhap()+"");
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void btnXoaSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSanPhamActionPerformed
        int a=0;
        DefaultTableModel tbl = (DefaultTableModel) tblThemSanPham.getModel(); 
        if(tbl.getRowCount()==0){
            JOptionPane.showMessageDialog(this, "Vui lòng thêm sản phầm rồi thử lại");
            return;
        }
        for (int i=0; i< tbl.getRowCount();i++){
            Boolean sel = (Boolean) tbl.getValueAt(i, 10);
            if(sel!=null && sel){
                a++;
            }
        }
        if (a==0){
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xóa.");
            return;
        }
        int x = JOptionPane.showConfirmDialog(this, "Vui lòng xác nhận xóa "+a+" sản phẩm đã chọn?", "Xác nhận?", JOptionPane.YES_NO_OPTION);
        if(x==JOptionPane.YES_OPTION){
            xoaSanPhamDaChon();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaSanPhamActionPerformed

    private void btnXoaTrangLoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaTrangLoActionPerformed
        txtTimKiem.setText("");
        txtMaSanPham.setText("");
        txtTenSanPham.setText("");
        txtMaLo.setText("");
        txtNhaCungCap.setText("");
        txtNgaySanXuat.setText("");
        txtNgayHetHan.setText("");
        txtDonViTinh.setText("");
        txtSoLuong.setText("");
        txtGiaNhap.setText("");
        txtTimKiem.requestFocus();
        ArrayList<LoSanPham> ds = LoSanPhamDAO.dsLoSanPham();
        DefaultTableModel tbl = (DefaultTableModel) tblLoSanPham.getModel();
        if(ds.size()>tbl.getRowCount()){
            tbl.setRowCount(0);
            loadLaiDanhSachLo();
        }
            
    }//GEN-LAST:event_btnXoaTrangLoActionPerformed

    private void txtNgaySanXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgaySanXuatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgaySanXuatActionPerformed

    private void txtNgayHetHanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayHetHanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayHetHanActionPerformed

    private void txtDonViTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDonViTinhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDonViTinhActionPerformed

    private void txtGiaNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaNhapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaNhapActionPerformed

    private void btnChonTatCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonTatCaActionPerformed
        DefaultTableModel tbl = (DefaultTableModel) tblThemSanPham.getModel();
        if(tbl.getRowCount()==0){
            JOptionPane.showMessageDialog(this, "Chưa có sản phẩm để chọn, Vui lòng thêm sản phẩm rồi thử lại");
            return;
        }
        chonTatCa();
    }//GEN-LAST:event_btnChonTatCaActionPerformed

    private void txtMaLoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaLoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaLoActionPerformed

    private void txtTenSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenSanPhamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenSanPhamActionPerformed

    private void txtMaLoSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaLoSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaLoSPActionPerformed

    private void btnTimTheoThongTinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimTheoThongTinActionPerformed
        String noiDung = txtMaLoSP.getText().isBlank() ? null : txtMaLoSP.getText().trim(); 
        String trangThai = cmbTrangThai.getSelectedItem().toString().trim();
        String tieuChi = cmbTimKiemTheo.getSelectedItem().toString().trim();
        DefaultTableModel tbl = (DefaultTableModel) tblKetQua.getModel();
        QuanLyLoBUS busLo = new QuanLyLoBUS();
        ArrayList<LoSanPham> dsKetQua = busLo.timKiemLoVoiNhieuDieuKien(tieuChi, noiDung, trangThai);
        if (dsKetQua==null || dsKetQua.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy lô sản phẩm nào khớp với tiêu chí.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        tbl.setRowCount(0);
        SwingWorker<Void, Object[]>worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                QuanLyLoBUS busLo = new QuanLyLoBUS();
                for(LoSanPham lo : dsKetQua){
                    NhaCungCap ncc = NhaCungCapDAO.timNCCTheoMa(SanPhamCungCapDAO.getSanPhamCungCap(lo.getSanPham().getMaSP()).getNhaCungCap().getMaNCC());
                    String trangThaiHienTai = busLo.tinhTrangThaiLo(lo);
                    Object[] as = new Object[]{
                        lo.getSanPham().getMaSP(), 
                        SanPhamDAO.timSPTheoMa(lo.getSanPham().getMaSP()).getTen(), 
                        lo.getMaLoSanPham(), 
                        ncc.getTenNCC(), 
                        lo.getSoLuong(),
                        lo.getNgaySanXuat(), 
                        lo.getNgayHetHan(), 
                        trangThaiHienTai 
                    };
                    publish(as);
                }
                return  null;
            }

            @Override
            protected void process(List<Object[]> chunks) {
                DefaultTableModel tblMoi = (DefaultTableModel) tblKetQua.getModel();
                for(Object[] i:chunks){
                    tblMoi.addRow(i);
                }
            }
            
            @Override
            protected void done() {
                tblKetQua.revalidate();
                tblKetQua.repaint();
            }
        };
        worker.execute();
        
    }//GEN-LAST:event_btnTimTheoThongTinActionPerformed

    private void cmbTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTrangThaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbTrangThaiActionPerformed

    private void cmbTimKiemTheoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTimKiemTheoActionPerformed
        if(cmbTimKiemTheo.getSelectedIndex() == 0){
            txtMaLoSP.setText("");
            txtMaLoSP.setEnabled(false);
        }
        else{
            txtMaLoSP.setEnabled(true);
        }
        if(cmbTimKiemTheo.getSelectedIndex()!=0){
            txtMaLoSP.setText("");
            txtMaLoSP.requestFocus();
        }
            
        
        
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbTimKiemTheoActionPerformed

    private void btnTimLoHetHanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimLoHetHanActionPerformed
        QuanLyLoBUS bus = new QuanLyLoBUS();
        ArrayList<LoSanPham> dsLo = LoSanPhamDAO.dsLoSanPham();
        DefaultTableModel tbl = (DefaultTableModel) tblLoSanPham.getModel();
        Map<String, Object> dsLoHetHan = bus.thongKe(dsLo);
        List<LoSanPham> dslo =(ArrayList<LoSanPham>) dsLoHetHan.get("dsLoHetHan");
        if(dslo.isEmpty()){
            JOptionPane.showMessageDialog(this, "Không có lô hết hạn");
        }else{
            tbl.setRowCount(0);
            for (LoSanPham lo: dslo){
            DonViTinh donVi = DonViTinhDAO.getMotDonViTinhTheoMaSP(lo.getSanPham().getMaSP());
            Object[] row = new Object[] {lo.getSanPham().getMaSP(),
                            SanPhamDAO.timSPTheoMa(lo.getSanPham().getMaSP()).getTen(),
                            lo.getMaLoSanPham(), 
                            donVi.getTenDonVi(),  
                            lo.getSoLuong()};
                            tbl.addRow(row);
            }
        }
        
    }//GEN-LAST:event_btnTimLoHetHanActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel CanhBao;
    private javax.swing.JPanel QuanLyLo;
    private javax.swing.JButton btnChonTatCa;
    private javax.swing.JButton btnHuyLo;
    private javax.swing.JButton btnThemSanPhamTuExcel;
    private javax.swing.JButton btnTimLoHetHan;
    private javax.swing.JButton btnTimTheoThongTin;
    private javax.swing.JButton btnXacNhan;
    private javax.swing.JButton btnXoaSanPham;
    private javax.swing.JButton btnXoaTrangLo;
    private javax.swing.JComboBox<String> cmbTimKiemTheo;
    private javax.swing.JComboBox<String> cmbTrangThai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tblKetQua;
    private javax.swing.JTable tblLichSuHoatDong;
    private javax.swing.JTable tblLoSanPham;
    private javax.swing.JTabbedPane tblTab;
    private javax.swing.JTable tblThemSanPham;
    private javax.swing.JLabel txtConHan;
    private javax.swing.JLabel txtDaHuy;
    private javax.swing.JTextField txtDonViTinh;
    private javax.swing.JTextField txtGiaNhap;
    private javax.swing.JLabel txtHetHan;
    private javax.swing.JButton txtLamMoi;
    private javax.swing.JTextField txtMaLo;
    private javax.swing.JTextField txtMaLoSP;
    private javax.swing.JTextField txtMaSanPham;
    private javax.swing.JTextField txtNgayHetHan;
    private javax.swing.JTextField txtNgaySanXuat;
    private javax.swing.JTextField txtNhaCungCap;
    private javax.swing.JLabel txtSapHetHan;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenSanPham;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables

    @SuppressWarnings("unchecked")
    private void loadDanhSachLoSanPham() {
        ArrayList<LoSanPham> dsLo = new QuanLyLoBUS().getLoKhongHuy();
        if(dsLo==null || dsLo.isEmpty()){
            JOptionPane.showMessageDialog(this, "Không tồn tại lô sản phẩm");
            return;
        }
        SwingWorker<Void, Object[]> worker = new SwingWorker<Void, Object[]>() {
            @Override
            protected Void doInBackground() throws Exception {
                QuanLyLoBUS bus = new QuanLyLoBUS();
                for(LoSanPham lo:dsLo){
                    publish(bus.toTableRow(lo));
                }
                return null;
            }
            @Override
            protected void process(List<Object[]> chunks) {
                DefaultTableModel tblMoi = (DefaultTableModel) tblLoSanPham.getModel();
                for(Object[] i:chunks){
                    tblMoi.addRow(i);
                }
            }
            @Override
            protected void done() {
                tblLoSanPham.revalidate();
                tblLoSanPham.repaint();
            }
        };
        worker.execute();
    }
    @SuppressWarnings("unchecked")
    private void loadLaiDanhSachLo(){
        DefaultTableModel tbl = (DefaultTableModel) tblLoSanPham.getModel();
        ArrayList<LoSanPham> dsLo = new QuanLyLoBUS().getLoKhongHuy();
        QuanLyLoBUS bus = new QuanLyLoBUS();
        for (LoSanPham lo: dsLo){
            tbl.addRow(bus.toTableRow(lo));
        }
    }
        
    @SuppressWarnings("unchecked")
    private void chonTatCa(){
        DefaultTableModel tbl = (DefaultTableModel) tblThemSanPham.getModel();
        int row = tbl.getRowCount();
        boolean duocChon = true;
        for (int i=0;i<row;i++){
            Boolean value = (Boolean) tbl.getValueAt(i, 10);
            if(value ==null || !value){
                duocChon=false;
                break;
            }
        }
        boolean newValue = !duocChon;
        for(int i=0;i<row;i++){
            tbl.setValueAt(newValue, i, 10);
        }
    }
    public void capNhatSoLo(){
        ArrayList<LoSanPham> ds = LoSanPhamDAO.dsLoSanPham();
        QuanLyLoBUS bs = new QuanLyLoBUS();
        Map<String, Object> as = bs.thongKe(ds);
        txtDaHuy.setText(as.get("SoLoDaHuy")+ " lô");
        txtHetHan.setText(as.get("SoLoHetHan")+ " lô");
        txtSapHetHan.setText(as.get("SoLoSapHetHan")+" lô");
        txtConHan.setText(as.get("SoLoConHan")+ " lô");
    }
    @SuppressWarnings("unchecked")
    private void xoaSanPhamDaChon() {
        DefaultTableModel tbl = (DefaultTableModel) tblThemSanPham.getModel();
        for(int i=tbl.getRowCount()-1;i>=0;i--){
            Boolean sel = (Boolean) tbl.getValueAt(i, 10);
            if(sel!=null && sel){
                tbl.removeRow(i);
            }
        }
    }

    private void reLoadQuanLyLo() {
        loadDanhSachLoSanPham();
    }

    private void reLoadTheoDoiVaCanhBao() throws SQLException {
        capNhatSoLo();
        loadLichSuLo();
    }
    private void xoaLoVuaThem(ArrayList<Integer> ds){
        DefaultTableModel tbl = (DefaultTableModel) tblThemSanPham.getModel();
        ds.sort(Comparator.reverseOrder());
        for(Integer i:ds){
            tbl.removeRow(i);
        }
    }
    private void focusTxt(javax.swing.JTextField txtChon, String noiDung){
        txtChon.setText(noiDung);
        txtChon.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(txtChon.getText().equals(noiDung)) txtChon.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(txtChon.getText().isEmpty()) txtChon.setText(noiDung);
            }
        });
    }
    @SuppressWarnings("unchecked")
    private void loadLichSuLo() throws SQLException{
        DefaultTableModel tbl = (DefaultTableModel) tblLichSuHoatDong.getModel();
        tbl.setRowCount(0);
        ArrayList<LichSuLo> ds = LichSuLoDAO.getAllLichSuLo();
        if(ds==null){
            JOptionPane.showMessageDialog(this, "Danh sách lịch sử không tồn tại");
            return;
        }
        for(LichSuLo i:ds){
            NhanVien nv = NhanVienDAO.getNhanVienTheoMaNV(i.getNv().getMaNV());
            tbl.addRow(new Object[]{i.getLo().getMaLoSanPham(), 
                                    i.getThoiGian(), 
                                    i.getHanhDong(), 
                                    i.getSoLuongSau(), 
                                    i.getGhiChu(), 
                                    nv.getHoTenDem()+" "+ nv.getTen()});
        }
        
    }
    private void mapKeyToFocus(String key, JComponent target, JComponent tabPanel) {
        InputMap im = tabPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        ActionMap am = tabPanel.getActionMap();
        String actionKey = "focus_" + key + "_" + target.hashCode();
        im.put(KeyStroke.getKeyStroke(key), actionKey);
        am.put(actionKey, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                target.requestFocusInWindow();
                if (target instanceof JTextField jtf) {
                    jtf.selectAll();
                }
            }
        });
    }
    private void mapKeyToClickButton(String key, AbstractButton button, JComponent tabPanel) {
        InputMap im = tabPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        ActionMap am = tabPanel.getActionMap();
        String actionKey = "click_" + key + "_" + button.hashCode();
        im.put(KeyStroke.getKeyStroke(key), actionKey);
        am.put(actionKey, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (button.isEnabled() && button.isShowing()) {
                    button.doClick();
                }
            }
        });
    }
}
