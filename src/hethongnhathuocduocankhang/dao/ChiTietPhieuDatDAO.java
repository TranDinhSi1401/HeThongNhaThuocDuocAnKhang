/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.ChiTietPhieuDatHang;
import hethongnhathuocduocankhang.entity.PhieuDatHang;
import hethongnhathuocduocankhang.entity.SanPham;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChiTietPhieuDatDAO {

    public static ArrayList<ChiTietPhieuDatHang> getChiTietTheoMaPDH(String maPhieuDatHang) {
        ArrayList<ChiTietPhieuDatHang> dsChiTiet = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            
            String sql = "SELECT ctpd.*, sp.ten "
                       + "FROM ChiTietPhieuDat ctpd "
                       + "JOIN SanPham sp ON ctpd.maSP = sp.maSP "
                       + "WHERE ctpd.maPhieuDatHang = ?";
                       
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maPhieuDatHang);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int soLuong = rs.getInt("soLuong");
                double donGia = rs.getDouble("donGia");
                double thanhTien = rs.getDouble("thanhTien");

                SanPham sp = new SanPham();
                sp.setMaSP(rs.getString("maSP"));
                sp.setTen(rs.getString("ten")); 

                PhieuDatHang pdh = new PhieuDatHang();
                pdh.setMaPhieuDat(maPhieuDatHang);

                ChiTietPhieuDatHang ctpd = new ChiTietPhieuDatHang(
                    sp,
                    soLuong,
                    pdh,
                    donGia,
                    thanhTien
                );
                
                dsChiTiet.add(ctpd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsChiTiet;
    }
}