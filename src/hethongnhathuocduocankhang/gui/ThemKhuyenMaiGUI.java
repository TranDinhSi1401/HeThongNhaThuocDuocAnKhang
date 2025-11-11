/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import hethongnhathuocduocankhang.entity.KhuyenMai;
import hethongnhathuocduocankhang.entity.LoaiKhuyenMaiEnum;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.JComponent;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author GIGABYTE
 */
public class ThemKhuyenMaiGUI extends javax.swing.JPanel { 

    private KhuyenMai khuyenMaiMoi = null; 
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jTextField8; 

    public ThemKhuyenMaiGUI() {
        initComponents();

        jButton2.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyXacNhan();
            }
        });

        jTextField2.addActionListener(e -> validateMoTa());
        jTextField3.addActionListener(e -> validatePhanTram());
        jTextField4.addActionListener(e -> validateNgayBatDau());
        jComboBox1.addActionListener(e -> validateLoaiKM());
        jTextField6.addActionListener(e -> validateNgayKetThuc());
        jTextField7.addActionListener(e -> validateSLToiThieu());
        jTextField8.addActionListener(e -> validateSLToiDa());
    }

    // Getters cho các nút
    public javax.swing.JButton getBtnHuy() {
        return jButton1;
    }

    public javax.swing.JButton getBtnXacNhan() {
        return jButton2;
    }

    // Getters/Setters cho các trường dữ liệu
    
    public javax.swing.JTextField getTxtMaKhuyenMai() {
        return jTextField1;
    }
    
    public void setTxtMaKhuyenMai(String maKM) {
        jTextField1.setText(maKM);
    }
    
    public javax.swing.JTextField getTxtMoTa() {
        return jTextField2;
    }

    public void setTxtMoTa(String moTa) {
        jTextField2.setText(moTa);
    }
    
    public javax.swing.JTextField getTxtPhanTram() {
        return jTextField3;
    }

    public void setTxtPhanTram(double phanTram) {
        jTextField3.setText(String.valueOf(phanTram));
    }
    
    public javax.swing.JTextField getTxtNgayBatDau() {
        return jTextField4;
    }

    public void setTxtNgayBatDau(LocalDateTime ngayBatDau) {
        if (ngayBatDau != null) {
            jTextField4.setText(ngayBatDau.format(formatter));
        } else {
            jTextField4.setText("");
        }
    }
    
    public javax.swing.JComboBox<String> getCmbLoaiKhuyenMai() {
        return jComboBox1;
    }

    public void setCmbLoaiKhuyenMai(LoaiKhuyenMaiEnum loai) {
        if (loai != null) {
            jComboBox1.setSelectedItem(loai.toString());
        }
    }
    
    public javax.swing.JTextField getTxtNgayKetThuc() {
        return jTextField6;
    }

    public void setTxtNgayKetThuc(LocalDateTime ngayKetThuc) {
         if (ngayKetThuc != null) {
            jTextField6.setText(ngayKetThuc.format(formatter));
        } else {
            jTextField6.setText("");
        }
    }
    
    // *** BỔ SUNG GETTER BỊ THIẾU ***
    public javax.swing.JTextField getTxtSoLuongToiThieu() {
        return jTextField7;
    }

    public void setTxtSoLuongToiThieu(int sl) {
        jTextField7.setText(String.valueOf(sl));
    }
    
    // *** BỔ SUNG GETTER BỊ THIẾU ***
    public javax.swing.JTextField getTxtSoLuongToiDa() {
        return jTextField8;
    }

    public void setTxtSoLuongToiDa(int sl) {
        jTextField8.setText(String.valueOf(sl)); 
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        
        jLabel9 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Thông tin Khuyến mãi"); 

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(218, Short.MAX_VALUE)) 
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("Mã khuyến mãi:"); 
        jLabel3.setText("Mô tả:"); 
        jLabel4.setText("Phần trăm (%):"); 
        jLabel5.setText("Ngày bắt đầu (yyyy-MM-dd HH:mm:ss):"); 
        jLabel6.setText("Loại khuyến mãi:"); 
        jLabel7.setText("Ngày kết thúc (yyyy-MM-dd HH:mm:ss):"); 
        jLabel8.setText("Số lượng tối thiểu:"); 
        jLabel9.setText("Số lượng tối đa:"); 

        jTextField1.setEnabled(false);

        jButton1.setText("Hủy");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Xác nhận");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SO_LUONG", "MUA", "NHA_SAN_XUAT", "NGUNG_BAN" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)) 
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1)
                            .addComponent(jTextField2)
                            .addComponent(jTextField3)
                            .addComponent(jTextField4)
                            .addComponent(jComboBox1, 0, 168, Short.MAX_VALUE)
                            .addComponent(jTextField6)
                            .addComponent(jTextField7)
                            .addComponent(jTextField8)))) 
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18) 
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE) 
                    .addComponent(jLabel9) 
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)) 
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        xuLyHuy();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void showError(String message, JComponent component) {
        JOptionPane.showMessageDialog(this, message, "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
        if (component != null) {
            component.requestFocusInWindow();
        }
    }

    private void xuLyHuy() {
        this.khuyenMaiMoi = null;
        closeDialog();
    }

    private void closeDialog() {
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.dispose();
        }
    }

    public KhuyenMai getKhuyenMaiMoi() {
        return this.khuyenMaiMoi;
    }

    // CÁC HÀM VALIDATE MỚI
    private boolean validateMoTa() {
        String moTa = jTextField2.getText().trim();
        if (moTa.length() > 255) {
            showError("Mô tả không được quá 255 ký tự.", jTextField2);
            return false;
        }
        return true; 
    }

    private boolean validatePhanTram() {
        String phanTramStr = jTextField3.getText().trim();
        if (phanTramStr.isEmpty()) {
            showError("Phần trăm không được rỗng.", jTextField3);
            return false;
        }
        try {
            double phanTram = Double.parseDouble(phanTramStr);
            if (phanTram <= 0 || phanTram > 100) {
                showError("Phần trăm phải > 0 và <= 100.", jTextField3);
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Phần trăm phải là một con số.", jTextField3);
            return false;
        }
        return true;
    }

    private boolean validateNgayBatDau() {
        String ngayBatDauStr = jTextField4.getText().trim();
         if (ngayBatDauStr.isEmpty()) {
            showError("Ngày bắt đầu không được rỗng.", jTextField4);
            return false;
        }
        try {
            LocalDateTime.parse(ngayBatDauStr, formatter);
        } catch (DateTimeParseException e) {
            showError("Ngày bắt đầu phải đúng định dạng yyyy-MM-dd HH:mm:ss", jTextField4);
            return false;
        }
        return true;
    }

    private boolean validateLoaiKM() {
        if (jComboBox1.getSelectedItem() == null) {
            showError("Bạn phải chọn một loại khuyến mãi.", jComboBox1);
            return false;
        }
        return true;
    }

    private boolean validateNgayKetThuc() {
        String ngayKetThucStr = jTextField6.getText().trim();
         if (ngayKetThucStr.isEmpty()) {
            showError("Ngày kết thúc không được rỗng.", jTextField6);
            return false;
        }
        try {
            LocalDateTime.parse(ngayKetThucStr, formatter);
        } catch (DateTimeParseException e) {
            showError("Ngày kết thúc phải đúng định dạng yyyy-MM-dd HH:mm:ss", jTextField6);
            return false;
        }
        return true;
    }
    
    private boolean validateSLToiThieu() {
        String slStr = jTextField7.getText().trim();
        if (slStr.isEmpty()) {
            showError("Số lượng tối thiểu không được rỗng.", jTextField7);
            return false;
        }
        try {
            int sl = Integer.parseInt(slStr);
            if (sl <= 0) {
                showError("Số lượng tối thiểu phải lớn hơn 0.", jTextField7);
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Số lượng tối thiểu phải là một số nguyên.", jTextField7);
            return false;
        }
        return true;
    }
    
    private boolean validateSLToiDa() {
        String slStr = jTextField8.getText().trim();
        if (slStr.isEmpty()) {
            showError("Số lượng tối đa không được rỗng.", jTextField8);
            return false;
        }
        try {
            int sl = Integer.parseInt(slStr);
            if (sl < 0) { 
                showError("Số lượng tối đa phải lớn hơn hoặc bằng 0.", jTextField8);
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Số lượng tối đa phải là một số nguyên.", jTextField8);
            return false;
        }
        return true;
    }

    
    private void xuLyXacNhan() {
        if (!validateMoTa() || !validatePhanTram() || !validateNgayBatDau() ||
            !validateLoaiKM() || !validateNgayKetThuc() || !validateSLToiThieu() ||
            !validateSLToiDa()) {
            return;
        }

        try {
            String maKM = jTextField1.getText().trim();
            String moTa = jTextField2.getText().trim();
            double phanTram = Double.parseDouble(jTextField3.getText().trim());
            LocalDateTime ngayBatDau = LocalDateTime.parse(jTextField4.getText().trim(), formatter);
            LoaiKhuyenMaiEnum loaiKM = LoaiKhuyenMaiEnum.valueOf(jComboBox1.getSelectedItem().toString());
            LocalDateTime ngayKetThuc = LocalDateTime.parse(jTextField6.getText().trim(), formatter);
            int slToiThieu = Integer.parseInt(jTextField7.getText().trim());
            int slToiDa = Integer.parseInt(jTextField8.getText().trim());

            if (ngayKetThuc.isBefore(ngayBatDau)) {
                showError("Ngày kết thúc không được trước ngày bắt đầu.", jTextField6);
                return;
            }
            if (slToiDa < slToiThieu) {
                showError("Số lượng tối đa không được nhỏ hơn số lượng tối thiểu.", jTextField8);
                return;
            }
            
            this.khuyenMaiMoi = new KhuyenMai();
            khuyenMaiMoi.setMaKhuyenMai(maKM);
            khuyenMaiMoi.setMoTa(moTa);
            khuyenMaiMoi.setPhanTram(phanTram);
            khuyenMaiMoi.setLoaiKhuyenMai(loaiKM);
            khuyenMaiMoi.setNgayBatDau(ngayBatDau);
            khuyenMaiMoi.setNgayKetThuc(ngayKetThuc);
            khuyenMaiMoi.setSoLuongToiThieu(slToiThieu);
            khuyenMaiMoi.setSoLuongToiDa(slToiDa);

            closeDialog();

        } catch (Exception ex) {
            this.khuyenMaiMoi = null;
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + ex.getMessage(), "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    // Khai báo biến cho component mới
    //private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    // Khai báo biến cho component mới
    //private javax.swing.JTextField jTextField8;
    // End of variables declaration//GEN-END:variables
}