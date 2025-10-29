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
	public TraHangBUS(TraHangGUI thg) {
		// TODO Auto-generated constructor stub
		this.traHangGui = thg;
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////// action
	@Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        JButton btn = (JButton) e.getSource();
        List<ChiTietHoaDon> listCTHD;
        try {
            if (btn.getText().equals("Tìm theo mã")) {
                ConnectDB.getInstance().connect(); // <--- 1. PHẢI GỌI TRƯỚC
                
                TraHangDAO thd = new TraHangDAO();
                listCTHD = thd.getAllCTHD(traHangGui.getMaHoaDon()); // Lỗi xảy ra bên trong hàm này
                traHangGui.initBan(listCTHD);
                
                ConnectDB.getInstance().disconnect(); // <--- XÓA DÒNG NÀY (đã chuyển xuống finally)
                //ConnectDB.getInstance().disconnect();
            } else {
                
                ConnectDB.getInstance().connect(); // <--- 1. PHẢI GỌI TRƯỚC
                
                TraHangDAO thd = new TraHangDAO();
                List<HoaDon> listHD;
                listHD = thd.getAllHoaDonTheoSoDienThoai(traHangGui.getSoDienThoai()); 
                traHangGui.initBangHoaDon(listHD);

                ConnectDB.getInstance().disconnect(); // <--- XÓA DÒNG NÀY (đã chuyển xuống finally)
            }
        } catch (SQLException e2) {
            e2.printStackTrace();
        } //finally {
//            try {
//                 // 2. LUÔN LUÔN đóng kết nối ở đây
//                
//            } catch (SQLException e3) {
//                e3.printStackTrace();
//            }
//        }
    }
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////keyboard
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
            
                
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////mouse
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
                Object source = e.getSource();  // Lấy đối tượng bị click
                traHangGui.clearCTHD();
		if(source == traHangGui.getBangHD() ){ 
                    JTable tblHoaDon = (JTable) source;
                    for(int i = 0 ; i<tblHoaDon.getRowCount(); i++){
                        if(Boolean.TRUE.equals(tblHoaDon.getValueAt(i, 3))){
                            String maHoaDon = tblHoaDon.getValueAt(i, 0).toString();

                            try{
                                ConnectDB.getInstance().connect();

                                TraHangDAO traHangDAO = new TraHangDAO();
                                List<ChiTietHoaDon> listCTHD = traHangDAO.getAllCTHD(maHoaDon);
                                traHangGui.initBan(listCTHD);

                                ConnectDB.getInstance().disconnect();
                            }catch(SQLException e2){
                                e2.printStackTrace();
                            }

                        }
                        else if(Boolean.FALSE.equals(tblHoaDon.getValueAt(i, 3))){

                            traHangGui.clearCTHD();
                            String maHoaDon = tblHoaDon.getValueAt(i, 0).toString();

                            try{
                                ConnectDB.getInstance().connect();

                                //TraHangDAO traHangDAO = new TraHangDAO();
                                //List<ChiTietHoaDon> listCTHD = traHangDAO.getAllCTHD(maHoaDon);
                                //traHangGui.initBan(listCTHD);

                                ConnectDB.getInstance().disconnect();
                            }catch(SQLException e2){
                                e2.printStackTrace();
                            }
                        }
                    }
                }
                else if(source == traHangGui.getBangCTHD()){
                    JTable tblCTHD = (JTable) source;
                    for(int i = 0 ; i<tblCTHD.getRowCount(); i++){
                        if(Boolean.TRUE.equals(tblCTHD.getValueAt(i, 4))){
                            String maCTHD = tblCTHD.getValueAt(i, 0).toString();

                            try{
                                ConnectDB.getInstance().connect();

                                TraHangDAO traHangDAO = new TraHangDAO();
                                List<ChiTietHoaDon> listCTHD = traHangDAO.getAllCTHD(maCTHD);
                                traHangGui.initBangPhieuTraHang(listCTHD);

                                ConnectDB.getInstance().disconnect();
                            }catch(SQLException e2){
                                e2.printStackTrace();
                            }

                        }
                        else if(Boolean.FALSE.equals(tblCTHD.getValueAt(i, 3))){

                            traHangGui.clearCTHD();
                            String maHoaDon = tblCTHD.getValueAt(i, 0).toString();

                            try{
                                ConnectDB.getInstance().connect();

                                //TraHangDAO traHangDAO = new TraHangDAO();
                                //List<ChiTietHoaDon> listCTHD = traHangDAO.getAllCTHD(maHoaDon);
                                //traHangGui.initBan(listCTHD);

                                ConnectDB.getInstance().disconnect();
                            }catch(SQLException e2){
                                e2.printStackTrace();
                            }
                        }
                    }
                }
            }

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();  // Lấy đối tượng bị click

	    if (source instanceof JTextField) {
	        JTextField clickedField = (JTextField) source;
	        String text = clickedField.getText();

	        if ("Nhập mã hóa đơn".equalsIgnoreCase(text)) {
	            System.out.println("Clicked mã hóa đơn");
	            clickedField.setText("");
	        } else if ("Nhập số điện thoại".equalsIgnoreCase(text)) {
	            System.out.println("Clicked số điện thoại");
	            clickedField.setText("");
	        }
	    }
            
            
	}
 
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}


	


}
