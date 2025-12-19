/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import com.formdev.flatlaf.FlatClientProperties;
import hethongnhathuocduocankhang.entity.TaiKhoan;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author trand
 */
public class GiaoDienChinhGUI extends JFrame{
    private static TaiKhoan tk = null;
    private static GiaoDienChinhGUI app;
    private final MainForm mainForm;
    private static final Map<String, JPanel> cachedPanels = new HashMap<>();
        
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
    
    
}
