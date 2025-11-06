/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import hethongnhathuocduocankhang.entity.NhanVien;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author GIGABYTE
 */
public class ThemNhanVienGUI extends javax.swing.JPanel {

    private NhanVien nhanVienMoi = null;
    
    // Thêm các component mới
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jTextField8; // Cho Địa chỉ
    private javax.swing.JCheckBox jCheckBox1; // Cho Nghỉ việc


    public ThemNhanVienGUI() {
        initComponents();

        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyXacNhan();
            }
        });

        // Họ tên đệm
        jTextField2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateHoTenDem();
            }
        });

        // Tên
        jTextField3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateTen();
            }
        });

        // SĐT
        jTextField4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateSDT();
            }
        });

        // Giới tính
        jComboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateGioiTinh();
            }
        });

        // CCCD
        jTextField6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateCCCD();
            }
        });

        // Ngày sinh
        jTextField7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateNgaySinh();
            }
        });
        
        // Địa chỉ
        jTextField8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateDiaChi();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public javax.swing.JButton getBtnHuy() {
        return jButton1;
    }

    public javax.swing.JButton getBtnXacNhan() {
        return jButton2;
    }

    public javax.swing.JLabel getLblTieuDe() {
        return jLabel1;
    }

    // ... (Các getter cho JLabel và JPanel có thể giữ nguyên hoặc đổi tên) ...

    public javax.swing.JTextField getTxtMaNhanVien() {
        return jTextField1;
    }

    public javax.swing.JTextField getTxtHoTenDem() {
        return jTextField2;
    }

    public javax.swing.JTextField getTxtTen() {
        return jTextField3;
    }

    public javax.swing.JTextField getTxtSDT() {
        return jTextField4;
    }

    public javax.swing.JComboBox<String> getCmbGioiTinh() {
        return jComboBox1;
    }

    public javax.swing.JTextField getTxtCCCD() {
        return jTextField6;
    }

    public javax.swing.JTextField getTxtNgaySinh() {
        return jTextField7;
    }
    
    public javax.swing.JTextField getTxtDiaChi() {
        return jTextField8;
    }
    
    public javax.swing.JCheckBox getChkNghiViec() {
        return jCheckBox1;
    }
    

    public void setTxtMaNhanVien(String maNV) {
        jTextField1.setText(maNV);
    }

    public void setTxtHoTenDem(String hoTenDem) {
        jTextField2.setText(hoTenDem);
    }

    public void setTxtTen(String ten) {
        jTextField3.setText(ten);
    }

    public void setTxtSDT(String sdt) {
        jTextField4.setText(sdt);
    }
    
    public void setTxtCCCD(String cccd) {
        jTextField6.setText(cccd);
    }

    public void setCmbGioiTinh(boolean gioiTinh) {
        // true: Nam, false: Nữ
        jComboBox1.setSelectedItem(gioiTinh ? "Nam" : "Nữ");
    }
    
    public void setTxtNgaySinh(LocalDate ngaySinh) {
        if (ngaySinh != null) {
            jTextField7.setText(ngaySinh.toString());
        } else {
            jTextField7.setText("");
        }
    }
    
    public void setTxtDiaChi(String diaChi) {
        jTextField8.setText(diaChi);
    }
    
    public void setChkNghiViec(boolean nghiViec) {
        jCheckBox1.setSelected(nghiViec);
    }

    // </editor-fold>

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
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
        
        // Thêm component mới
        jLabel9 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();


        setLayout(new java.awt.BorderLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Thông tin nhân viên"); // THAY ĐỔI

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(233, Short.MAX_VALUE))
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

        jLabel2.setText("Mã nhân viên:"); // THAY ĐỔI

        jLabel3.setText("Họ tên đệm:"); // THAY ĐỔI

        jLabel4.setText("Tên:"); // THAY ĐỔI

        jLabel5.setText("SĐT:"); // THAY ĐỔI

        jLabel6.setText("Giới tính:"); // THAY ĐỔI

        jLabel7.setText("CCCD:"); // THAY ĐỔI

        jLabel8.setText("Ngày sinh (yyyy-MM-dd):"); // THAY ĐỔI
        
        jLabel9.setText("Địa chỉ:"); // THÊM MỚI
        
        jCheckBox1.setText("Đã nghỉ việc"); // THÊM MỚI


        jTextField1.setEnabled(false);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });

        jButton1.setText("Hủy");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Xác nhận");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" })); // THAY ĐỔI

        // CẬP NHẬT GROUP LAYOUT
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
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9) // THÊM
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1)
                            .addComponent(jTextField2)
                            .addComponent(jTextField3)
                            .addComponent(jTextField4)
                            .addComponent(jComboBox1, 0, 268, Short.MAX_VALUE) // Điều chỉnh kích thước
                            .addComponent(jTextField6)
                            .addComponent(jTextField7)
                            .addComponent(jTextField8) // THÊM
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jCheckBox1) // THÊM
                                .addGap(0, 0, Short.MAX_VALUE)))))
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
                .addGap(18, 18, 18) // THÊM
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE) // THÊM
                    .addComponent(jLabel9) // THÊM
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)) // THÊM
                .addGap(18, 18, 18) // THÊM
                .addComponent(jCheckBox1) // THÊM
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE) // Điều chỉnh
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        xuLyHuy();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed

    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed

    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed

    }//GEN-LAST:event_jTextField6ActionPerformed

    private void showError(String message, JComponent component) {
        JOptionPane.showMessageDialog(this, message, "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
        if (component != null) {
            component.requestFocusInWindow(); // Đặt con trỏ vào ô bị sai
        }
    }

    private void xuLyHuy() {
        this.nhanVienMoi = null;
        closeDialog();
    }

    private void closeDialog() {
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.dispose();
        }
    }

    public NhanVien getNhanVienMoi() {
        return this.nhanVienMoi;
    }

    // CÁC HÀM VALIDATE MỚI
    private boolean validateHoTenDem() {
        String hoTenDem = jTextField2.getText().trim();
        if (hoTenDem.isEmpty()) {
            showError("Họ tên đệm không được rỗng.", jTextField2);
            return false;
        }
        if (hoTenDem.length() > 50) {
            showError("Họ tên đệm không quá 50 ký tự.", jTextField2);
            return false;
        }
        return true;
    }

    private boolean validateTen() {
        String ten = jTextField3.getText().trim();
        if (ten.isEmpty()) {
            showError("Tên không được rỗng.", jTextField3);
            return false;
        }
         if (ten.length() > 20) {
            showError("Tên không quá 20 ký tự.", jTextField3);
            return false;
        }
        return true;
    }

    private boolean validateSDT() {
        String sdt = jTextField4.getText().trim();
        if (!sdt.matches("^0\\d{9}$")) {
            showError("Số điện thoại không hợp lệ (gồm 10 số, bắt đầu bằng 0).", jTextField4);
            return false;
        }
        return true;
    }
    
    private boolean validateCCCD() {
        String cccd = jTextField6.getText().trim();
        if (!cccd.matches("^\\d{12}$")) {
            showError("Số CCCD không hợp lệ (gồm 12 chữ số).", jTextField6);
            return false;
        }
        return true;
    }

    private boolean validateGioiTinh() {
        if (jComboBox1.getSelectedItem() == null) {
            showError("Bạn phải chọn giới tính.", jComboBox1);
            return false;
        }
        return true;
    }

    private boolean validateNgaySinh() {
        String ngaySinhStr = jTextField7.getText().trim();
        if (ngaySinhStr.isEmpty()) {
            showError("Ngày sinh không được rỗng.", jTextField7);
            return false;
        }
        try {
            LocalDate ngaySinh = LocalDate.parse(ngaySinhStr); // YYYY-MM-DD
            if (ngaySinh.isAfter(LocalDate.now())) {
                 showError("Ngày sinh phải trước ngày hiện tại.", jTextField7);
                 return false;
            }
             int tuoi = LocalDate.now().getYear() - ngaySinh.getYear();
             if (LocalDate.now().getDayOfYear() < ngaySinh.getDayOfYear()) {
                 tuoi--;
             }
             if (tuoi < 18) {
                 showError("Nhân viên phải đủ 18 tuổi.", jTextField7);
                 return false;
             }
        } catch (DateTimeParseException e) {
            showError("Ngày sinh phải đúng định dạng YYYY-MM-DD.", jTextField7);
            return false;
        }
        return true;
    }

    private boolean validateDiaChi() {
         String diaChi = jTextField8.getText().trim();
         if (diaChi.length() > 255) {
            showError("Địa chỉ không được vượt quá 255 ký tự.", jTextField8);
            return false;
        }
        return true;
    }


    private void xuLyXacNhan() {
        if (!validateHoTenDem() || !validateTen() || !validateSDT() || !validateGioiTinh() 
            || !validateCCCD() || !validateNgaySinh() || !validateDiaChi()) {
            return;
        }

        try {
            // 1. Lấy dữ liệu
            String maNV = jTextField1.getText().trim();
            String hoTenDem = jTextField2.getText().trim();
            String ten = jTextField3.getText().trim();
            String sdt = jTextField4.getText().trim();
            // true: Nam, false: Nữ
            boolean gioiTinh = jComboBox1.getSelectedItem().toString().equals("Nam"); 
            String cccd = jTextField6.getText().trim();
            LocalDate ngaySinh = LocalDate.parse(jTextField7.getText().trim());
            String diaChi = jTextField8.getText().trim();
            boolean nghiViec = jCheckBox1.isSelected();

            // 2. Tạo đối tượng và lưu vào biến của lớp
            this.nhanVienMoi = new NhanVien();
            
            // Sử dụng setter để validate lần cuối (nếu cần)
            nhanVienMoi.setMaNV(maNV);
            nhanVienMoi.setHoTenDem(hoTenDem);
            nhanVienMoi.setTen(ten);
            nhanVienMoi.setSdt(sdt);
            nhanVienMoi.setCccd(cccd);
            nhanVienMoi.setGioiTinh(gioiTinh);
            nhanVienMoi.setNgaySinh(ngaySinh);
            nhanVienMoi.setDiaChi(diaChi);
            nhanVienMoi.setNghiViec(nghiViec);


            closeDialog();

        } catch (Exception ex) {
            this.nhanVienMoi = null;
            // Hiển thị lỗi từ setter của Entity (nếu có)
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống hoặc dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    // End of variables declaration//GEN-END:variables
}