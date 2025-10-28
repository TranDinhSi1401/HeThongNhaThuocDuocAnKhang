/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.KhachHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author trand
 */
public class KhachHangDAO {
    public static KhachHang getKhachHangTheoSdt(String sdt) {
        KhachHang kh = null;
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM KhachHang WHERE sdt = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, sdt);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String maKH = rs.getString(1);
                String hoDem = rs.getString(2);
                String ten = rs.getString(3);
                int diemTichLuy = rs.getInt(5);

                kh = new KhachHang(maKH, hoDem, ten, sdt, diemTichLuy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kh;
    }
    
    public static boolean updateDiemTichLuy(int diemTichLuy, String maKH) {
        int rows = 0;
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "UPDATE KhachHang SET diemTichLuy = diemTichLuy + ? WHERE maKH = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, diemTichLuy);
            ps.setString(2, maKH);
            rows = ps.executeUpdate(); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows > 0;
    }
}
