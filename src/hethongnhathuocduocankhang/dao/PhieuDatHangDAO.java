/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import java.sql.PreparedStatement;
import java.sql.Connection;
import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.PhieuDatHang;
import hethongnhathuocduocankhang.entity.SanPham;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class PhieuDatHangDAO {

    public ArrayList<SanPham> dsSanPham(){
        ArrayList<SanPham> dsSanPham = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "Select maSP, ten from SanPham";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                String maSP = rs.getString(1);
                String ten = rs.getString(2);
                SanPham sp = new SanPham(maSP,ten);
                dsSanPham.add(sp);
            }
            
        } catch (SQLException e) {
        }
        return dsSanPham;
    }
    
    public SanPham timSanPham(String ma){
        SanPham s = null;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "select maSP, ten from SanPham where maSP = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, ma);
            try(ResultSet rs = st.executeQuery()){
                while(rs.next()){
                    String maSP = rs.getString(1);
                    String tenSP = rs.getString(2);
                    SanPham sp = new SanPham(maSP, tenSP);
                    s = new SanPham(maSP, tenSP);
                }
            }
        } catch (SQLException sQLException) {
        }
        return s;
    }
    
    
    
    
    
    
    
    
    
    
    public boolean themPhieuDat(PhieuDatHang pd){
        int n =0;
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        String sql ="";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, pd.getMaPhieuDat());
            
            
            
            
        } catch (SQLException sQLException) {
        }
        return n>0;
    }
  
}
