package hethongnhathuocduocankhang.dao;


import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.KhachHang;
import hethongnhathuocduocankhang.entity.LoSanPham;
import hethongnhathuocduocankhang.entity.SanPham;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author trand
 */
public class LoSanPhamDAO {
    public static ArrayList<LoSanPham> getLoSanPhamTheoMaSP(String maSP) {
        ArrayList<LoSanPham> dsLSP = new ArrayList<>();
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM LoSanPham WHERE maSP = ? AND ngayHetHan > GETDATE() ORDER BY NgayHetHan ASC";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maSP);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String maLSP = rs.getString(1);
                int soLuong = rs.getInt(3);
                LocalDate ngaySanXuat = rs.getDate(4).toLocalDate();
                LocalDate ngayHetHan = rs.getDate(5).toLocalDate();
                

                LoSanPham lsp = new LoSanPham(maLSP, new SanPham(maSP), soLuong, ngaySanXuat, ngayHetHan);
                dsLSP.add(lsp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsLSP;
    }
    
    public static boolean truSoLuong(String maLo, int soLuong) {
        int rows = 0;
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "UPDATE LoSanPham SET soLuong = soLuong - ? WHERE maLoSanPham = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, soLuong);
            ps.setString(2, maLo);
            rows = ps.executeUpdate(); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows > 0;
    }
}
