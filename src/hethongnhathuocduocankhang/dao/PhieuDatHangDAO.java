/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import java.sql.PreparedStatement;
import java.sql.Connection;
import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.DonViTinh;
import hethongnhathuocduocankhang.entity.NhaCungCap;
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
    public NhaCungCap timNhaCungCap(String ma){
        NhaCungCap ncc = null;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "Select maNCC from SanPham s join SanPhamCungCap sc on s.maSP=sc.maSP where sc.maSP=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, ma);
            try (ResultSet rs = st.executeQuery()){
                while(rs.next()){
                    String maNCC = rs.getString(1);
                    ncc = new NhaCungCap(maNCC);
                }
            } catch (Exception e) {
            }
        } catch (SQLException sQLException) {
        }
        return ncc;
    }
    public DonViTinh giaSanPham(String ma){
        DonViTinh dv = null;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql= "Select maDonViTinh, maSP, tenDonVi, giaBanTheoDonVi from DonViTinh where maSP=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, ma);
            try (ResultSet rs = st.executeQuery()){
                while(rs.next()){
                    String maDVT = rs.getString(1);
                    SanPham sanPham = new SanPham(rs.getString(2));
                    String tenDV = rs.getString(3);
                    double gia =  Double.parseDouble(rs.getString(4));    
                    dv = new DonViTinh(maDVT, sanPham, gia, tenDV);
                }
            } catch (Exception e) {
            }
        } catch (SQLException sQLException) {}
        return dv;    
    }
  
}
