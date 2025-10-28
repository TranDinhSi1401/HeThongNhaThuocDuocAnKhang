/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.HoaDon;
import hethongnhathuocduocankhang.entity.KhachHang;
import hethongnhathuocduocankhang.entity.NhanVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author trand
 */
public class HoaDonDAO {
    public static HoaDon getHoaDonMoiNhat() {
        HoaDon hd = null;
        
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            
            String sql = "SELECT TOP 1 * FROM HoaDon ORDER BY ngayLapHoaDon DESC";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next()) {
                String maHoaDon = rs.getString(1);
                String maNV = rs.getString(2);
                LocalDateTime ngayLapHD= rs.getTimestamp(3).toLocalDateTime();
                String maKH = rs.getString(4);
                boolean chuyenKhoan = rs.getInt(5) == 1;
                boolean trangThai = rs.getInt(6) == 1;
                double tongTien = rs.getDouble(7);
                hd = new HoaDon(maHoaDon, new NhanVien(maNV), ngayLapHD, new KhachHang(maKH), chuyenKhoan, trangThai, tongTien);
            }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return hd;
    }
    
    public static HoaDon getHoaDonTheoMaHD(String maHD) {
        HoaDon hd = null;

        try {
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM HoaDon WHERE MaHoaDon = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maHD);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String maNV = rs.getString(2);
                LocalDateTime ngayLapHD= rs.getTimestamp(3).toLocalDateTime();
                String maKH = rs.getString(4);
                boolean chuyenKhoan = rs.getInt(5) == 1;
                boolean trangThai = rs.getInt(6) == 1;
                double tongTien = rs.getDouble(7);
                hd = new HoaDon(maHD, new NhanVien(maNV), ngayLapHD, new KhachHang(maKH), chuyenKhoan, trangThai, tongTien);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hd;
    }
    
    public static boolean insertHoaDon(HoaDon hd) {
        int n = 0;
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO HoaDon VALUES (?, ?, ?, ?, ?, ?, ?)"
            );

            ps.setString(1, hd.getMaHoaDon());
            ps.setString(2, hd.getNhanVien().getMaNV());
            ps.setTimestamp(3, Timestamp.valueOf(hd.getNgayLapHoaDon()));
            ps.setString(4, hd.getKhachHang().getMaKH());
            ps.setInt(5, hd.isChuyenKhoan() ? 1 : 0);
            ps.setInt(6, hd.isTrangThai() ? 1 : 0);
            ps.setDouble(7, hd.getTongTien());
            
            n = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }
}
