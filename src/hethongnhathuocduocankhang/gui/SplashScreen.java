/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;   
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.Border;

/**
 *
 * @author admin
 */
public class SplashScreen extends JWindow {
    private int dur;

    public SplashScreen(int d) {
        dur = d;
    }

    public int getDuration() {
        return dur;
    }

    public void showSS() {
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(Color.white);
        setContentPane(content);

        int width = 500;
        int height = 300;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);

        ImageIcon icon = null;
        try {
            java.net.URL imgUrl = getClass().getResource("/resources/images/logo.png");
            if (imgUrl != null)
                icon = new ImageIcon(imgUrl);
        } catch (Exception ex) {
        }

        JLabel imageLabel = new JLabel();
        if (icon != null) {
            imageLabel.setIcon(icon);
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
        }

        JLabel title = new JLabel("Nhà thuốc Dược An Khang", JLabel.CENTER);
        title.setFont(new Font("Sans-Serif", Font.BOLD, 16));
        Color boder = new Color(0, 120, 215);
        title.setBorder(BorderFactory.createLineBorder(boder, 5));
        
        
        content.add(imageLabel, BorderLayout.CENTER);
        content.add(title, BorderLayout.SOUTH);

        setVisible(true);

        new Thread(() -> {
            try {
                Thread.sleep(dur);
            } catch (InterruptedException ex) {
                // ignore
            }
            java.awt.EventQueue.invokeLater(() -> {
                setVisible(false);
                dispose();
            });
        }, "Splash-Hider").start();
    }

    public void showDuring(SwingWorker<?, ?> worker) {
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(Color.white);
        setContentPane(content);

        int width = 500;
        int height = 300;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);

        ImageIcon icon = null;
        try {
            java.net.URL imgUrl = getClass().getResource("/resources/images/logo.png");
            if (imgUrl != null)
                icon = new ImageIcon(imgUrl);
        } catch (Exception ex) {
            // ignore
        }

        JLabel imageLabel = new JLabel();
        if (icon != null) {
            imageLabel.setIcon(icon);
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
        }
        
        Color blueColor = new Color(0, 120, 215);
        JLabel title = new JLabel("Nhà thuốc Dược An Khang", JLabel.CENTER);
        title.setForeground(blueColor);
        title.setFont(new Font("Sans-Serif", Font.BOLD, 23));
        
        //title.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(imageLabel, BorderLayout.CENTER);
        centerPanel.add(title, BorderLayout.SOUTH);

    JPanel bottomPanel = new JPanel(new BorderLayout());
    bottomPanel.setOpaque(false);
    bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 12, 20));

    JLabel messageLabel = new JLabel("Starting...", SwingConstants.LEFT);
    messageLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
    messageLabel.setForeground(new Color(80, 80, 80));
    messageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));

    JProgressBar progressBar = new JProgressBar(0, 100);
    progressBar.setStringPainted(true);
    progressBar.setForeground(new Color(0, 120, 215));
    progressBar.setValue(0);

    bottomPanel.add(messageLabel, BorderLayout.NORTH);
    bottomPanel.add(progressBar, BorderLayout.SOUTH);

    Border bd = BorderFactory.createLineBorder(blueColor, 4, true);
    content.setBorder(bd);
    content.add(centerPanel, BorderLayout.CENTER);
    content.add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);

        if (worker != null) {
            worker.addPropertyChangeListener(evt -> {
                String name = evt.getPropertyName();
                if ("progress".equals(name)) {
                    Object v = evt.getNewValue();
                    if (v instanceof Integer) {
                        int p = (Integer) v;
                        progressBar.setValue(p);
                    }
                } else if ("message".equals(name)) {
                    Object v = evt.getNewValue();
                    if (v != null) {
                        messageLabel.setText(v.toString());
                    }
                }
            });
            worker.execute();
        }
    }

}
