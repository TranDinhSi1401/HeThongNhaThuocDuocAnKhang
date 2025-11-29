/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import hethongnhathuocduocankhang.dao.SanPhamDAO;
import hethongnhathuocduocankhang.entity.LoaiSanPhamEnum;
import hethongnhathuocduocankhang.entity.SanPham;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author GIGABYTE
 */
public class ThemSanPhamGUI extends javax.swing.JPanel {

    private SanPham sanPhamMoi = null;

    public ThemSanPhamGUI() {
        initComponents();

        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyXacNhan();
            }
        });

        // Tên sản phẩm
        jTextField2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateTen();
            }
        });

        // Mô tả
        jTextField3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateMoTa();
            }
        });

        // Thành phần
        jTextField4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateThanhPhan();
            }
        });

        // Loại sản phẩm
        jComboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateLoai();
            }
        });

        // Tồn tối thiểu
        jTextField6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateTonToiThieu();
            }
        });

        // Tồn tối đa
        jTextField7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateTonToiDa();
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

    public javax.swing.JLabel getLblMaSanPham() {
        return jLabel2;
    }

    public javax.swing.JLabel getLblTenSanPham() {
        return jLabel3;
    }

    public javax.swing.JLabel getLblMoTa() {
        return jLabel4;
    }

    public javax.swing.JLabel getLblThanhPhan() {
        return jLabel5;
    }

    public javax.swing.JLabel getLblLoaiSanPham() {
        return jLabel6;
    }

    public javax.swing.JLabel getLblSoLuongToiThieu() {
        return jLabel7;
    }

    public javax.swing.JLabel getLblSoLuongToiDa() {
        return jLabel8;
    }

    public javax.swing.JPanel getPanelTieuDe() {
        return jPanel1;
    }

    public javax.swing.JPanel getPanelNoiDung() {
        return jPanel2;
    }

    public javax.swing.JTextField getTxtMaSanPham() {
        return jTextField1;
    }

    public javax.swing.JTextField getTxtTenSanPham() {
        return jTextField2;
    }

    public javax.swing.JTextField getTxtMoTa() {
        return jTextField3;
    }

    public javax.swing.JTextField getTxtThanhPhan() {
        return jTextField4;
    }

    public JComboBox<String> getCmbLoaiSanPham() {
        return jComboBox1;
    }

    public javax.swing.JTextField getTxtSoLuongToiThieu() {
        return jTextField6;
    }

    public javax.swing.JTextField getTxtSoLuongToiDa() {
        return jTextField7;
    }

    public void setTxtMaSanPham(String maSP) {
        jTextField1.setText(maSP);
    }

    public void setTxtTenSanPham(String tenSP) {
        jTextField2.setText(tenSP);
    }

    public void setTxtMoTa(String moTa) {
        jTextField3.setText(moTa);
    }

    public void setTxtThanhPhan(String thanhPhan) {
        jTextField4.setText(thanhPhan);
    }

    public void setCmbLoaiSanPham(LoaiSanPhamEnum loai) {
        if (loai == LoaiSanPhamEnum.THUOC_KE_DON) {
            jComboBox1.setSelectedItem("Thuốc kê đơn");
        } else if (loai == LoaiSanPhamEnum.THUC_PHAM_CHUC_NANG) {
            jComboBox1.setSelectedItem("Thực phẩm chức năng");
        }else {
            jComboBox1.setSelectedItem("Thuốc không kê đơn");
        }
    }

    /**
     * Thiết lập giá trị cho ô số lượng tồn tối thiểu.
     *
     * @param tonToiThieu Giá trị int
     */
    public void setTxtSoLuongToiThieu(int tonToiThieu) {
        jTextField6.setText(String.valueOf(tonToiThieu));
    }

    /**
     * Thiết lập giá trị cho ô số lượng tồn tối đa.
     *
     * @param tonToiDa Giá trị int
     */
    public void setTxtSoLuongToiDa(int tonToiDa) {
        jTextField7.setText(String.valueOf(tonToiDa));
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

        setLayout(new java.awt.BorderLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Thông tin sản phẩm");

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

        jLabel2.setText("Mã sản phẩm:");

        jLabel3.setText("Tên sản phẩm:");

        jLabel4.setText("Mô tả:");

        jLabel5.setText("Thành phần:");

        jLabel6.setText("Loại sản phẩm:");

        jLabel7.setText("Số lượng tối thiểu:");

        jLabel8.setText("Số lượng tối đa:");

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

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Thuốc", "Thực phẩm chức năng"}));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel4)
                                                        .addComponent(jLabel5)
                                                        .addComponent(jLabel6)
                                                        .addComponent(jLabel3)
                                                        .addComponent(jLabel2))
                                                .addGap(31, 31, 31)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jTextField1)))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel7)
                                                        .addComponent(jLabel8))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jTextField7)
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 0, Short.MAX_VALUE))))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addComponent(jButton1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton2)))
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
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
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
        this.sanPhamMoi = null;
        closeDialog();
    }

    private void closeDialog() {
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.dispose();
        }
    }

    public SanPham getSanPhamMoi() {
        return this.sanPhamMoi;
    }

    private boolean validateTen() {
        String ten = jTextField2.getText().trim();
        if (ten.isEmpty()) {
            showError("Tên thuốc không được rỗng.", jTextField2);
            return false;
        }
        return true;
    }

    private boolean validateMoTa() {
        String moTa = jTextField3.getText().trim();
        if (moTa.isEmpty()) {
            showError("Mô tả không được rỗng.", jTextField3);
            return false;
        }
        return true;
    }

    private boolean validateThanhPhan() {
        String thanhPhan = jTextField4.getText().trim();
        if (thanhPhan.isEmpty()) {
            showError("Thành phần không được rỗng.", jTextField4);
            return false;
        }
        return true;
    }

    private boolean validateLoai() {
        if (jComboBox1.getSelectedItem() == null) {
            showError("Bạn phải chọn một loại sản phẩm.", jComboBox1);
            return false;
        }
        return true;
    }

    private boolean validateTonToiThieu() {
        String tonToiThieuStr = jTextField6.getText().trim();
        if (tonToiThieuStr.isEmpty()) {
            showError("Tồn tối thiểu không được rỗng.", jTextField6);
            return false;
        }
        try {
            int tonToiThieu = Integer.parseInt(tonToiThieuStr);
            if (tonToiThieu < 0) {
                showError("Tồn tối thiểu phải lớn hơn hoặc bằng 0.", jTextField6);
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Tồn tối thiểu phải là một số nguyên.", jTextField6);
            return false;
        }
        return true;
    }

    private boolean validateTonToiDa() {
        String tonToiDaStr = jTextField7.getText().trim();
        if (tonToiDaStr.isEmpty()) {
            showError("Tồn tối đa không được rỗng.", jTextField7);
            return false;
        }
        try {
            int tonToiDa = Integer.parseInt(tonToiDaStr);
            if (tonToiDa < 0) {
                showError("Tồn tối đa phải lớn hơn hoặc bằng 0.", jTextField7);
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Tồn tối đa phải là một số nguyên.", jTextField7);
            return false;
        }
        return true;
    }

    private void xuLyXacNhan() {
        if (!validateTen()) {
            return;
        }
        if (!validateMoTa()) {
            return;
        }
        if (!validateThanhPhan()) {
            return;
        }
        if (!validateLoai()) {
            return;
        }
        if (!validateTonToiThieu()) {
            return;
        }
        if (!validateTonToiDa()) {
            return;
        }

        try {
            int tonToiThieu = Integer.parseInt(jTextField6.getText().trim());
            int tonToiDa = Integer.parseInt(jTextField7.getText().trim());

            if (tonToiDa < tonToiThieu) {
                showError("Tồn tối đa (" + tonToiDa + ") phải lớn hơn hoặc bằng tồn tối thiểu (" + tonToiThieu + ").", jTextField7);
                return;
            }

            // 1. Lấy dữ liệu
            String maSP = jTextField1.getText().trim();
            String ten = jTextField2.getText().trim();
            String moTa = jTextField3.getText().trim();
            String thanhPhan = jTextField4.getText().trim();
            LoaiSanPhamEnum loai = jComboBox1.getSelectedItem().toString().equals("Thuốc")
                    ? LoaiSanPhamEnum.THUOC_KE_DON
                    : LoaiSanPhamEnum.THUC_PHAM_CHUC_NANG;

            // 2. Tạo đối tượng và lưu vào biến của lớp
            this.sanPhamMoi = new SanPham();

            sanPhamMoi.setMaSP(maSP);
            sanPhamMoi.setTen(ten);
            sanPhamMoi.setMoTa(moTa);
            sanPhamMoi.setThanhPhan(thanhPhan);
            sanPhamMoi.setLoaiSanPham(loai);
            sanPhamMoi.setTonToiThieu(tonToiThieu);
            sanPhamMoi.setTonToiDa(tonToiDa);

            closeDialog();

        } catch (Exception ex) {
            this.sanPhamMoi = null;
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
