/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.ChiTietHoaDon;
import hethongnhathuocduocankhang.entity.DonViTinh;
import hethongnhathuocduocankhang.entity.HoaDon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



/**
 *
 * @author trand
 */
public class ChiTietHoaDonDAO {
    public static ChiTietHoaDon getChiTietHoaDonMoiNhat() {
        ChiTietHoaDon cthd = null;
        
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            
            String sql = "SELECT TOP 1 * FROM ChiTietHoaDon ORDER BY maChiTietHoaDon DESC";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next()) {
                String maCTHD = rs.getString(1);
                String maHD = rs.getString(2);
                String maDVT = rs.getString(3);
                int soLuong = rs.getInt(4);
                double donGia = rs.getDouble(5);
                double giamGia = rs.getDouble(6);
                double thanhTien = rs.getDouble(7);
                cthd = new ChiTietHoaDon(maCTHD, new HoaDon(maHD), new DonViTinh(maDVT), soLuong, donGia, giamGia, thanhTien);
            }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return cthd;
    }
    
    public static boolean insertChiTietHoaDon(ChiTietHoaDon cthd) {
        int n = 0;
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO ChiTietHoaDon VALUES (?, ?, ?, ?, ?, ?)"
            );

            ps.setString(1, cthd.getMaChiTietHoaDon());
            ps.setString(2, cthd.getHoaDon().getMaHoaDon());
            ps.setString(3, cthd.getDonViTinh().getMaDonViTinh());
            ps.setInt(4, cthd.getSoLuong());
            ps.setDouble(5, cthd.getDonGia());
            ps.setDouble(6, cthd.getGiamGia() / 100);
            
            n = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }
}
