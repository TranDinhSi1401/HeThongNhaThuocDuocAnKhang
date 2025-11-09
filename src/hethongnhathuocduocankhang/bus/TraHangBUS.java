package hethongnhathuocduocankhang.bus;

import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.dao.TraHangDAO;
import hethongnhathuocduocankhang.entity.ChiTietHoaDon;
import hethongnhathuocduocankhang.entity.HoaDon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;

import hethongnhathuocduocankhang.gui.TraHangGUI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JTable;

public class TraHangBUS implements MouseListener, ActionListener, KeyListener  {
    private TraHangGUI traHangGui;
    private TraHangDAO traHangDao;
    public TraHangBUS(TraHangGUI thg) {
		// TODO Auto-generated constructor stub
        this.traHangGui = thg;
        this.traHangDao = new TraHangDAO();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String src = e.getActionCommand();
        switch (src) {
            case "Tìm theo mã":
                String maHoaDon = traHangGui.getMaHoaDon();
                //tra
                break;
            default:
                throw new AssertionError();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}