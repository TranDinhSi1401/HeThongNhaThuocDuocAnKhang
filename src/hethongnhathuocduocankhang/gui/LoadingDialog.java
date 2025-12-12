/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 *
 * @author trand
 */
public class LoadingDialog extends JDialog{
    public LoadingDialog(JFrame parent) {
        super(parent, "Đang xử lý...", true);

        JLabel lbl = new JLabel("Đang gửi mật khẩu mới...");
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JProgressBar bar = new JProgressBar();
        bar.setIndeterminate(true);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.add(lbl, BorderLayout.NORTH);
        panel.add(bar, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(panel);
        setSize(300, 120);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    }
}
