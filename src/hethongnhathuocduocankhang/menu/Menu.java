/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.menu;

import hethongnhathuocduocankhang.menu.MenuAnimation;
import hethongnhathuocduocankhang.menu.MenuItem;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
/**
 *
 * @author trand
 */
public class Menu extends JComponent{
    private MenuEvent event;
    private MigLayout layout;
    private final String [][]menuItems= new String[][]{
        {"Tổng quan"},
        {"Bán hàng"},
        {"Quản lý", "Quản lý khách hàng", "Quản lý sản phẩm", 
            "Quản lý nhân viên", "Quản lý hóa đơn", "Quản lý khuyến mãi", 
            "Quản lý nhà cung cấp", "Quản lý phiếu đặt hàng", "Quản lý lịch sử ca làm", 
            "Quản lý phiếu trả hàng"},
        {"Trả hàng"},
        {"Quản lý lô"},
        {"Tra cứu"},
        {"Thống kê"},
        {"Đổi quà"},
        {"Đăng xuất"}
    };
    
    public Menu() {
        init();
    }
    
    private void init() {
        layout = new MigLayout("wrap 1, fillx, gapy 0, inset 2", "fill");
        setLayout(layout);
        setOpaque(true);
        //Menu Items
        for(int i = 0; i < menuItems.length; i++){
            addMenu(menuItems[i][0], i);
        }
    }
    
    private Icon getIcon(int index) {
        URL url = getClass().getResource("/resources/images/icon/"+index+".png");
        if(url != null) {
            return new ImageIcon(url);
        }else {
            return null;
        }        
    }
    
    private void addMenu(String menuName, int index){
        int length = menuItems[index].length;
        MenuItem item = new MenuItem(menuName, index, length > 1);
        Icon icon = getIcon(index);
        if(icon != null) {
            item.setIcon(icon);
        }
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(length > 1) {
                    if(!item.isSelected()) {
                        item.setSelected(true);
                        addSubMenu(item, index, length, getComponentZOrder(item));
                    }else {
                        hideMenu(item, index);
                        item.setSelected(false);
                    }
                }else {
                    if(event != null) {
                        event.selected(index, 0);
                    }
                }
            }
        });
        add(item);
        revalidate();
        repaint();
    }
    
    private void addSubMenu(MenuItem item, int index, int length, int indexZorder) {
        JPanel panel = new JPanel(new MigLayout("wrap 1, fillx, inset 0, gapy 0", "fill"));
        panel.setName(index + "");
        panel.setOpaque(false);
        for(int i = 1; i < length; i++) {
            MenuItem subItem = new MenuItem(menuItems[index][i], i, false);
            subItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(event != null) {
                        event.selected(index, subItem.getIndex());
                    }
                }
                
            });
            subItem.initSubMenu(i, length);
            panel.add(subItem);
        }
        add(panel, "h 0!", indexZorder + 1);
        revalidate();
        repaint();
        MenuAnimation.showMenu(panel, item, layout, true);
    }
    
    private void hideMenu(MenuItem item, int index) {
        for(Component com: getComponents()) {
            if(com instanceof JPanel && com.getName().equals(index+"")) {
                com.setName(null);
                MenuAnimation.showMenu(com, item, layout, false);
                break;
            }
        }
    }
    
    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D)grphcs.create();
        g2.setColor(new Color(25, 118, 210));
        g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
        super.paintComponent(grphcs);
    }   

    public MenuEvent getEvent() {
        return event;
    }

    public void setEvent(MenuEvent event) {
        this.event = event;
    }
    
    
}
