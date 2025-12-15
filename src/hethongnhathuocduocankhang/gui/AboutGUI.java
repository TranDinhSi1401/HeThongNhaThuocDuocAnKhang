/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author Nguyễn Khánh Quân
 */
public class AboutGUI extends JDialog {

    /**
     * Creates new form AboutGUI
     */
    public AboutGUI(JFrame parent) {
        super(parent, "About - Dược An Khang", true);
        initComponents();
        setLocationRelativeTo(parent);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblIntro = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        lblFeatureTitle = new javax.swing.JLabel();
        lblFeature1 = new javax.swing.JLabel();
        lblFeature2 = new javax.swing.JLabel();
        lblFeature3 = new javax.swing.JLabel();
        lblFeature4 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        lblTech = new javax.swing.JLabel();
        lblVersion = new javax.swing.JLabel();
        lblDeveloper = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        lblCopyright = new javax.swing.JLabel();
        btnClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        try {
            lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/logo.png")));
        } catch (Exception ex) {
            lblLogo.setText("Logo");
        }
        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(0, 102, 204));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("HỆ THỐNG QUẢN LÝ NHÀ THUỐC – DƯỢC AN KHANG");

        lblIntro.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblIntro.setForeground(new java.awt.Color(102, 102, 102));
        lblIntro.setText("<html>Ứng dụng hỗ trợ quản lý hoạt động kinh doanh nhà thuốc, "
                + "giúp quản lý thuốc và lô sản phẩm, nhập kho, bán hàng – hóa đơn, khách hàng, "
                + "nhân viên và cung cấp các thống kê cơ bản phục vụ công tác quản lý.</html>");

        lblFeatureTitle.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblFeatureTitle.setForeground(new java.awt.Color(51, 51, 51));
        lblFeatureTitle.setText("<html><b>Chức năng chính:</b></html>");

        lblFeature1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblFeature1.setForeground(new java.awt.Color(102, 102, 102));
        lblFeature1.setText("• Quản lý thuốc và lô sản phẩm");

        lblFeature2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblFeature2.setForeground(new java.awt.Color(102, 102, 102));
        lblFeature2.setText("• Nhập kho, bán hàng và hóa đơn");

        lblFeature3.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblFeature3.setForeground(new java.awt.Color(102, 102, 102));
        lblFeature3.setText("• Quản lý khách hàng và nhân viên");

        lblFeature4.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblFeature4.setForeground(new java.awt.Color(102, 102, 102));
        lblFeature4.setText("• Thống kê cơ bản");

        lblTech.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblTech.setForeground(new java.awt.Color(102, 102, 102));
        lblTech.setText("<html><b>Công nghệ:</b> Java, Java Swing – SQL Server</html>");

        lblVersion.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblVersion.setForeground(new java.awt.Color(102, 102, 102));
        lblVersion.setText("<html><b>Phiên bản:</b> 1.0.0 (2025)</html>");

        lblDeveloper.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblDeveloper.setForeground(new java.awt.Color(102, 102, 102));
        lblDeveloper.setText("<html><b>Thực hiện:</b><br>"
                        + "• Hồ Minh Khang - Nhóm trưởng - Đại học Công nghiệp thành phố Hồ Chí Minh (IUH)<br>"
                        + "• Nguyễn Khánh Quân - Thư ký - Đại học Công nghiệp thành phố Hồ Chí Minh (IUH)<br>"                       
                        + "• Võ Tiến Khoa - Quản lý thời gian - Đại học Công nghiệp thành phố Hồ Chí Minh (IUH)<br>"
                        + "• Trần Đình Sĩ - Thuyết trình viên - Đại học Công nghiệp thành phố Hồ Chí Minh (IUH)"
                        + "</html>");

        lblCopyright.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        lblCopyright.setForeground(new java.awt.Color(153, 153, 153));
        lblCopyright.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCopyright.setText("© 2025 – Dược An Khang");

        btnClose.setBackground(new java.awt.Color(0, 102, 204));
        btnClose.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnClose.setForeground(new java.awt.Color(255, 255, 255));
        btnClose.setText("Đóng");
        btnClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblLogo, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                    .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                    .addComponent(jSeparator1)
                    .addComponent(lblIntro, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jSeparator2)
                    .addComponent(lblFeatureTitle)
                    .addComponent(lblFeature1)
                    .addComponent(lblFeature2)
                    .addComponent(lblFeature3)
                    .addComponent(lblFeature4)
                    .addComponent(jSeparator3)
                    .addComponent(lblTech)
                    .addComponent(lblVersion)
                    .addComponent(lblDeveloper)
                    .addComponent(jSeparator4)
                    .addComponent(lblCopyright, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(40, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(280, 280, 280))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblLogo)
                .addGap(10, 10, 10)
                .addComponent(lblTitle)
                .addGap(15, 15, 15)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(lblIntro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(lblFeatureTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblFeature1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblFeature2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblFeature3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblFeature4)
                .addGap(15, 15, 15)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(lblTech, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(lblVersion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(lblDeveloper, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(lblCopyright)
                .addGap(20, 20, 20)
                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    // Variables declaration
    private javax.swing.JButton btnClose;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblIntro;
    private javax.swing.JLabel lblFeatureTitle;
    private javax.swing.JLabel lblFeature1;
    private javax.swing.JLabel lblFeature2;
    private javax.swing.JLabel lblFeature3;
    private javax.swing.JLabel lblFeature4;
    private javax.swing.JLabel lblTech;
    private javax.swing.JLabel lblVersion;
    private javax.swing.JLabel lblDeveloper;
    private javax.swing.JLabel lblCopyright;
    // End of variables declaration
}
