package hethongnhathuocduocankhang.gui;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.util.UIScale;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import hethongnhathuocduocankhang.menu.Menu;
import hethongnhathuocduocankhang.menu.MenuAction;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Raven
 */
public class MainForm extends JLayeredPane {

    public MainForm() {
        init();
    }

    private void init() {
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new MainFormLayout());
        menu = new Menu();
        panelBody = new JPanel(new BorderLayout());
        initMenuArrowIcon();
        menuButton.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Menu.button.background;"
                + "arc:999;"
                + "focusWidth:0;"
                + "borderWidth:0");
        menuButton.addActionListener((ActionEvent e) -> {
            setMenuFull(!menu.isMenuFull());
        });
        initMenuEvent();
        setLayer(menuButton, JLayeredPane.POPUP_LAYER);
        add(menuButton);
        add(menu);
        add(panelBody);
    }

    @Override
    public void applyComponentOrientation(ComponentOrientation o) {
        super.applyComponentOrientation(o);
        initMenuArrowIcon();
    }

    private void initMenuArrowIcon() {
        if (menuButton == null) {
            menuButton = new JButton();
        }
        String icon = (getComponentOrientation().isLeftToRight()) ? "menu_left.svg" : "menu_right.svg";
        menuButton.setIcon(new FlatSVGIcon("hethongnhathuocduocankhang/icon/svg/" + icon, 0.8f));
    }

    private void initMenuEvent() {
        menu.addMenuEvent((int index, int subIndex, MenuAction action) -> {
            // Application.mainForm.showForm(new DefaultForm("Form : " + index + " " + subIndex));
            if (index == 0) {
                if(GiaoDienChinhGUI.getTk().isQuanLy()) {
                    GiaoDienChinhGUI.showForm(new DashBoardQuanLi());
                } else {
                    GiaoDienChinhGUI.showForm(new DashBoardNhanVien());
                }               
            } else if (index == 1) {
                GiaoDienChinhGUI.showForm(new BanHangGUI());
            } else if (index == 2) {
                GiaoDienChinhGUI.showForm(new TraHangGUI());
            } else if (index == 3) {
                GiaoDienChinhGUI.showForm(new TraCuuChungGUI());
            } else if (index == 4) {
                switch(subIndex) {
                    case 1 ->
                        showForm(new QuanLiKhachHangGUI());
                    case 2 ->
                        showForm(new QuanLiSanPhamGUI());
                    case 3 ->
                        showForm(new QuanLiNhanVienGUI());
                    case 4 ->
                        showForm(new QuanLiHoaDonGUI());
                    case 5 ->
                        showForm(new QuanLiKhuyenMaiGUI());
                    case 6 ->
                        showForm(new QuanLiNhaCungCapGUI());
                    case 7 ->
                        showForm(new QuanLiPhieuDatHangGUI());
                    case 8 ->
                        showForm(new QuanLiLichSuCaLamGUI());
                    case 9 ->
                        showForm(new QuanLiPhieuTraHangGUI());
                    case 10 ->
                        showForm(new QuanLiPhieuNhapHangGUI());
                    default -> {
                        action.cancel();
                    }
                }
            } else if (index == 5) {
                try {
                    showForm(new LoSanPhamGUI());
                } catch (SQLException ex) {
                    Logger.getLogger(GiaoDienChinhGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (index == 6) {
                showForm(new ThongKeHoaDonGUI());
            } else if (index == 7) {
                showForm(new BaoCaoGUI());
            } else if (index == 8) {
                switch(subIndex) {
                    case 1 -> {
                        GiaoDienChinhGUI.showAboutGUI();
                    }
                    case 2 -> {
                        // Hướng dẫn sử dụng
                    }
                    default -> {
                        action.cancel();
                    }
                }
            } else if (index == 9) {
                GiaoDienChinhGUI.logout();
            } else {
                action.cancel();
            }
        });
        //action.cancel();
    }

    private void setMenuFull(boolean full) {
        String icon;
        if (getComponentOrientation().isLeftToRight()) {
            icon = (full) ? "menu_left.svg" : "menu_right.svg";
        } else {
            icon = (full) ? "menu_right.svg" : "menu_left.svg";
        }
        menuButton.setIcon(new FlatSVGIcon("hethongnhathuocduocankhang/icon/svg/" + icon, 0.8f));
        menu.setMenuFull(full);
        revalidate();
    }

    public void hideMenu() {
        menu.hideMenuItem();
    }

    public void showForm(Component component) {
        panelBody.removeAll();
        panelBody.add(component);
        panelBody.repaint();
        panelBody.revalidate();
    }

    public void setSelectedMenu(int index, int subIndex) {
        menu.setSelectedMenu(index, subIndex);
    }

    private Menu menu;
    private JPanel panelBody;
    private JButton menuButton;

    private class MainFormLayout implements LayoutManager {

        @Override
        public void addLayoutComponent(String name, Component comp) {
        }

        @Override
        public void removeLayoutComponent(Component comp) {
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                return new Dimension(5, 5);
            }
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                return new Dimension(0, 0);
            }
        }

        @Override
        public void layoutContainer(Container parent) {
            synchronized (parent.getTreeLock()) {
                boolean ltr = parent.getComponentOrientation().isLeftToRight();
                Insets insets = UIScale.scale(parent.getInsets());
                int x = insets.left;
                int y = insets.top;
                int width = parent.getWidth() - (insets.left + insets.right);
                int height = parent.getHeight() - (insets.top + insets.bottom);
                int menuWidth = UIScale.scale(menu.isMenuFull() ? menu.getMenuMaxWidth() : menu.getMenuMinWidth());
                int menuX = ltr ? x : x + width - menuWidth;
                menu.setBounds(menuX, y, menuWidth, height);
                int menuButtonWidth = menuButton.getPreferredSize().width;
                int menuButtonHeight = menuButton.getPreferredSize().height;
                int menubX;
                if (ltr) {
                    menubX = (int) (x + menuWidth - (menuButtonWidth * (menu.isMenuFull() ? 0.5f : 0.3f)));
                } else {
                    menubX = (int) (menuX - (menuButtonWidth * (menu.isMenuFull() ? 0.5f : 0.7f)));
                }
                menuButton.setBounds(menubX, UIScale.scale(30), menuButtonWidth, menuButtonHeight);
                int gap = UIScale.scale(5);
                int bodyWidth = width - menuWidth - gap;
                int bodyHeight = height;
                int bodyx = ltr ? (x + menuWidth + gap) : x;
                int bodyy = y;
                panelBody.setBounds(bodyx, bodyy, bodyWidth, bodyHeight);
            }
        }
    }
}
