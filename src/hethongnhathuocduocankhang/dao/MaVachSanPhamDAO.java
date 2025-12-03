/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.MaVachSanPham;
import hethongnhathuocduocankhang.entity.SanPham;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class MaVachSanPhamDAO {
    public static ArrayList<MaVachSanPham> timMaSPTheoMaVach(){
         ArrayList<MaVachSanPham> ds = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "Select * from MaVachSanPham";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next()){        
                String maVachSanPham = rs.getString(1);
                String maSP = rs.getString(2);
                MaVachSanPham sanPhamTheoMa = new MaVachSanPham(new SanPham(maSP), maVachSanPham);
                ds.add(sanPhamTheoMa);
            }
        } catch (SQLException sQLException) {
        }       
        return ds;
    }
    
        public static boolean themMaVach(MaVachSanPham mv) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "INSERT INTO MaVachSanPham (maVach, maSP) VALUES (?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, mv.getMaVach());
            stmt.setString(2, mv.getSanPham().getMaSP());
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public static boolean xoaMaVach(String maBarcode) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();

            String query = "DELETE FROM MaVachSanPham WHERE maVach = ?";

            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, maBarcode);

            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public static boolean suaMaVach(String maBarcode, MaVachSanPham mvNew) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();

            String query = "UPDATE MaVachSanPham SET maSP = ? WHERE maVach = ?";

            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setString(1, mvNew.getSanPham().getMaSP());
            stmt.setString(2, maBarcode);

            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }
    // Thêm vào class MaVachSanPhamDAO

    public static ArrayList<String> getDsMaVachTheoMaSP(String maSP) {
        ArrayList<String> list = new ArrayList<>();
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT maVach FROM MaVachSanPham WHERE maSP = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, maSP);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean xoaMaVachTheoMaSP(String maSP) {
        int n = 0;
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "DELETE FROM MaVachSanPham WHERE maSP = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, maSP);
            n = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }
}

