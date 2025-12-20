/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import com.formdev.flatlaf.FlatClientProperties;
import hethongnhathuocduocankhang.entity.TaiKhoan;
import hethongnhathuocduocankhang.util.PasswordUtil;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author trand
 */
public class GiaoDienChinhGUI extends JFrame{
    private static TaiKhoan tk = null;
    private static GiaoDienChinhGUI app;
    private final MainForm mainForm;
    private static final Map<String, JPanel> cachedPanels = new HashMap<>();
    private static boolean canDoiMatKhau = false;

    public static boolean isCanDoiMatKhau() {
        return canDoiMatKhau;
    }

    public static void setCanDoiMatKhau(boolean canDoiMatKhau) {
        GiaoDienChinhGUI.canDoiMatKhau = canDoiMatKhau;
    }
        
    public GiaoDienChinhGUI(TaiKhoan tk) {
        if (tk != null) {
            GiaoDienChinhGUI.tk = tk;
        }
        app = this;
        initComponents();

        URL url = GiaoDienChinhGUI.class.getResource("/resources/images/logo.png");
        Image icon = Toolkit.getDefaultToolkit().createImage(url);
        this.setIconImage(icon);
        setTitle("Hệ thống nhà thuốc Dược An Khang");

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        mainForm = new MainForm();
        setContentPane(mainForm);
        setResizable(false);
        getRootPane().putClientProperty(FlatClientProperties.FULL_WINDOW_CONTENT, true);
        //Notifications.getInstance().setJFrame(this);
        if(GiaoDienChinhGUI.getTk().isQuanLy()) {
            GiaoDienChinhGUI.showForm(new DashBoardQuanLi());
        } else {
            GiaoDienChinhGUI.showForm(new DashBoardNhanVien());
        } 
    }
    
    public static void showAboutGUI() {
        AboutGUI aboutDialog = new AboutGUI(app);
        aboutDialog.setVisible(true);
    }
    
    public static void showForm(Component component) {
        component.applyComponentOrientation(app.getComponentOrientation());
        app.mainForm.showForm(component);
    }
    
    public static JPanel getOrCreatePanel(String key, Supplier<JPanel> creator) {
        return cachedPanels.computeIfAbsent(key, k -> creator.get());
    }
    
    public static void showFormByKey(String key, Supplier<JPanel> creator) {
        JPanel panel = getOrCreatePanel(key, creator);
        showForm(panel);
    }
    
    public static void logout() {
        int confirm = JOptionPane.showConfirmDialog(app,
                "Bạn có chắc chắn muốn đăng xuất không?",
                "Xác nhận đăng xuất",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
                app.dispose(); // đóng cửa sổ hiện tại
                cachedPanels.clear();
                GiaoDienChinhGUI.setCanDoiMatKhau(false);
                new DangNhapGUI().setVisible(true); 
        }
    }

    public static void setSelectedMenu(int index, int subIndex) {
        app.mainForm.setSelectedMenu(index, subIndex);
    }
    
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 719, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 521, Short.MAX_VALUE)
        );

        pack();
    }

    public static TaiKhoan getTk() {
        return tk;
    }

    public static void setTk(TaiKhoan tk) {
        GiaoDienChinhGUI.tk = tk;
    }
    
    public static void taoPanelDoiMatKhau() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Label và PasswordField cho Mật khẩu cũ
        JLabel lblMatKhauCu = new JLabel("Mật khẩu cũ:");
        JTextField txtMatKhauCu = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblMatKhauCu, gbc);
        gbc.gridx = 1;
        panel.add(txtMatKhauCu, gbc);

        // Label và PasswordField cho Mật khẩu mới
        JLabel lblMatKhauMoi = new JLabel("Mật khẩu mới:");
        JTextField txtMatKhauMoi = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblMatKhauMoi, gbc);
        gbc.gridx = 1;
        panel.add(txtMatKhauMoi, gbc);

        // Label và PasswordField cho Xác nhận mật khẩu mới
        JLabel lblXacNhan = new JLabel("Xác nhận mật khẩu mới:");
        JTextField txtXacNhan = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblXacNhan, gbc);
        gbc.gridx = 1;
        panel.add(txtXacNhan, gbc);

        int result = JOptionPane.showConfirmDialog(
                app,
                panel,
                "Đổi mật khẩu",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
        
        if (result == JOptionPane.OK_OPTION) {
            String oldPass = txtMatKhauCu.getText().trim();
            String newPass = txtMatKhauMoi.getText().trim();
            String confirmPass = txtXacNhan.getText().trim();
            if (oldPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
                JOptionPane.showMessageDialog(app, "Vui lòng nhập đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            } else if (!newPass.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\W).{8,}$")) {
                JOptionPane.showMessageDialog(app, "Mật khẩu phải có tối thiểu 8 ký tự, gồm chữ hoa, chữ thường, chữ số và ký tự đặc biệt!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            } else if (!newPass.equals(confirmPass)) {
                JOptionPane.showMessageDialog(app, "Mật khẩu xác nhận không khớp!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            } else if (oldPass.equalsIgnoreCase(newPass)) {
                JOptionPane.showMessageDialog(app, "Mật khẩu mới phải khác mật khẩu cũ!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            } else {
                // Gọi business logic xử lý đổi mật khẩu
                if(PasswordUtil.doiMatKhau(GiaoDienChinhGUI.getTk(), oldPass, newPass)) {
                    JOptionPane.showMessageDialog(app, "Đổi mật khẩu thành công!");
                } else {
                    JOptionPane.showMessageDialog(app, "Mật khẩu cũ không đúng!");
                }             
            }
        }
    }
    
    public static void showNhacNhoDoiMatKhau(boolean canDoiMatKhau) {
        if(canDoiMatKhau) {
            JOptionPane.showMessageDialog(app, "Bạn vừa đặt lại mật khẩu\nHãy đổi lại mật khẩu mới trong phần tiện ích -> đổi mật khẩu", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        } else {
        
        }
    }
}
