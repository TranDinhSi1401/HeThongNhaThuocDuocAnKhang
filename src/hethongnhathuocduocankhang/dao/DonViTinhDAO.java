/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.DonViTinh;
import hethongnhathuocduocankhang.entity.SanPham;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author trand
 */
public class DonViTinhDAO {
    public static ArrayList<DonViTinh> getDonViTinhTheoMaSP(String maSp) {
        ArrayList<DonViTinh> dsDVT = new ArrayList<>();

        try {
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM DonViTinh WHERE MaSP = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maSp);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String maDVT = rs.getString(1);
                String maSP = rs.getString(2);
                String tenDVT = rs.getString(3);
                int heSoQuyDoi = rs.getInt(4);
                double giaBanTheoDonVi = rs.getDouble(5);
                boolean donViTinhCoBan = rs.getInt(6) == 1 ? true : false;
                SanPham sp = new SanPham(maSP);
                DonViTinh dvt = new DonViTinh(maDVT, sp, heSoQuyDoi, giaBanTheoDonVi, tenDVT, donViTinhCoBan);
                dsDVT.add(dvt);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsDVT;
    }
    
    public static String getMaSanPhamTheoMaDVT(String maDVT) {
        String maSP = "";

        try {
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM DonViTinh WHERE maDonViTinh = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maDVT);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                maSP = rs.getString(1);
                return maSP;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maSP;
    }
}
