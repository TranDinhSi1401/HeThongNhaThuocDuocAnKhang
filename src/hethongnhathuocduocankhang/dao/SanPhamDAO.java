/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import java.sql.*;
import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.SanPham;
import hethongnhathuocduocankhang.entity.LoaiSanPhamEnum;
import java.util.ArrayList;

/**
 *
 * @author trand
 */
public class SanPhamDAO {
    public static ArrayList<SanPham> getAllTableSanPham() {
        ArrayList<SanPham> dsSP = new ArrayList<>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            
            String sql = "SELECT * FROM SanPham";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()) {
                String maSP = rs.getString(1);
                String tenSP = rs.getString(2);
                String moTa = rs.getString(3);
                String thanhPhan = rs.getString(4);
                LoaiSanPhamEnum loaiSanPham = LoaiSanPhamEnum.valueOf(rs.getString(5));
                int tonToiThieu = rs.getInt(6);
                int tonToiDa = rs.getInt(7);
                SanPham sp = new SanPham(maSP, tenSP, moTa, thanhPhan, loaiSanPham, tonToiThieu, tonToiDa);
                dsSP.add(sp);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return dsSP;
    }
    
    public static SanPham getSanPhamTheoMaSP(String maSp) {
        SanPham sp = null;

        try {
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM SanPham WHERE MaSP = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maSp);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String maSP = rs.getString(1);
                String tenSP = rs.getString(2);
                String moTa = rs.getString(3);
                String thanhPhan = rs.getString(4);
                LoaiSanPhamEnum loaiSanPham = LoaiSanPhamEnum.valueOf(rs.getString(5));
                int tonToiThieu = rs.getInt(6);
                int tonToiDa = rs.getInt(7);

                sp = new SanPham(maSP, tenSP, moTa, thanhPhan, loaiSanPham, tonToiThieu, tonToiDa);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sp;
    }
}
