/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import hethongnhathuocduocankhang.entity.NhaCungCap; // THAY ĐỔI
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class ThemNhaCungCapGUI extends javax.swing.JPanel { 

    private NhaCungCap nhaCungCapMoi = null; 
    private javax.swing.JTextField txtEmail;
    private javax.swing.JLabel lblEmail;     

    public ThemNhaCungCapGUI() {
        initComponents();

        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyXacNhan();
            }
        });

        // Tên NCC
        jTextField2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateTenNCC();
            }
        });

        // Địa chỉ
        jTextField3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateDiaChi();
            }
        });

        // SĐT
        jTextField4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateSDT();
            }
        });

        // Email
        txtEmail.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 validateEmail();
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

    // Getters for TextFields
    public javax.swing.JTextField getTxtMaNCC() {
        return jTextField1;
    }

    public javax.swing.JTextField getTxtTenNCC() {
        return jTextField2;
    }

    public javax.swing.JTextField getTxtDiaChi() {
        return jTextField3;
    }

    public javax.swing.JTextField getTxtSDT() {
        return jTextField4;
    }

    public javax.swing.JTextField getTxtEmail() {
        return txtEmail;
    }

    public void setTxtMaNCC(String maNCC) {
        jTextField1.setText(maNCC);
    }

    public void setTxtTenNCC(String tenNCC) {
        jTextField2.setText(tenNCC);
    }

    public void setTxtDiaChi(String diaChi) {
        jTextField3.setText(diaChi);
    }

    public void setTxtSDT(String sdt) {
        jTextField4.setText(sdt);
    }

    public void setTxtEmail(String email) {
        txtEmail.setText(email);
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
        lblEmail = new javax.swing.JLabel(); // THÊM LABEL CHO EMAIL
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField(); // THÊM TEXTFIELD CHO EMAIL
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Thông tin Nhà Cung Cấp"); // THAY ĐỔI

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(195, Short.MAX_VALUE)) // Điều chỉnh
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

        jLabel2.setText("Mã NCC:"); // THAY ĐỔI

        jLabel3.setText("Tên NCC:"); // THAY ĐỔI

        jLabel4.setText("Địa chỉ:"); // THAY ĐỔI

        jLabel5.setText("Số điện thoại:"); // THAY ĐỔI

        lblEmail.setText("Email:"); // THAY ĐỔI

        jTextField1.setEnabled(false);

        jButton1.setText("Hủy");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Xác nhận");

        // TÁI CẤU TRÚC LAYOUT
        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 227, Short.MAX_VALUE) // Điều chỉnh
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(lblEmail)) // THÊM
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1)
                            .addComponent(jTextField2)
                            .addComponent(jTextField3)
                            .addComponent(jTextField4)
                            .addComponent(txtEmail)))) // THÊM
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                .addGap(18, 18, 18) // THÊM
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE) // THÊM
                    .addComponent(lblEmail) // THÊM
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)) // THÊM
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE) // Điều chỉnh
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
        this.nhaCungCapMoi = null;
        closeDialog();
    }

    private void closeDialog() {
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.dispose();
        }
    }

    public NhaCungCap getNhaCungCapMoi() {
        return this.nhaCungCapMoi;
    }

    private boolean validateTenNCC() {
        String ten = jTextField2.getText().trim();
        if (ten.isEmpty()) {
            showError("Tên nhà cung cấp không được rỗng.", jTextField2);
            return false;
        }
         if (ten.length() > 100) {
            showError("Tên nhà cung cấp không quá 100 ký tự.", jTextField2);
            return false;
        }
        return true;
    }

    private boolean validateDiaChi() {
        String diaChi = jTextField3.getText().trim();
         if (diaChi.length() > 255) { // Dựa theo DB schema (NVARCHAR 255)
            showError("Địa chỉ không quá 255 ký tự.", jTextField3);
            return false;
        }
        return true;
    }

    private boolean validateSDT() {
        String sdt = jTextField4.getText().trim();
        if (!sdt.matches("0\\d{9}")) {
            showError("Số điện thoại phải đủ 10 số và bắt đầu bằng 0.", jTextField4);
            return false;
        }
        return true;
    }

    private boolean validateEmail() {
        String email = txtEmail.getText().trim();
        if (email.isEmpty()) {
             showError("Email không được rỗng.", txtEmail);
            return false;
        }
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
             showError("Email không đúng định dạng.", txtEmail);
            return false;
        }
         if (email.length() > 255) {
            showError("Email không quá 255 ký tự.", txtEmail);
            return false;
        }
        return true;
    }


    private void xuLyXacNhan() {
        if (!validateTenNCC() || !validateDiaChi() || !validateSDT() || !validateEmail()) {
            return;
        }

        try {
            String maNCC = jTextField1.getText().trim();
            String tenNCC = jTextField2.getText().trim();
            String diaChi = jTextField3.getText().trim();
            String sdt = jTextField4.getText().trim();
            String email = txtEmail.getText().trim();
            
            this.nhaCungCapMoi = new NhaCungCap(maNCC, tenNCC, diaChi, sdt, email);

            closeDialog();

        } catch (Exception ex) {
            this.nhaCungCapMoi = null;
            showError("Lỗi hệ thống: " + ex.getMessage(), null);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    //private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    //private javax.swing.JTextField jTextField5;
   // End of variables declaration//GEN-END:variables
}