/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.LoaiSanPhamEnum;
import hethongnhathuocduocankhang.entity.NhanVien;
import hethongnhathuocduocankhang.entity.SanPham;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author trand
 */
public class NhanVienDAO {
     public static NhanVien getNhanVienTheoMaNV(String maNV) {
        NhanVien nv = null;

        try {
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM NhanVien WHERE MaNV = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maNV);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String hoDem = rs.getString(2);
                String ten = rs.getString(3);
                String sdt = rs.getString(4);
                String cccd = rs.getString(5);
                boolean gioiTinh = rs.getInt(6) == 1;
                LocalDate ngaySinh = rs.getDate(7).toLocalDate();
                String diaChi = rs.getString(8);
                boolean nghiViec = rs.getInt(9) == 1;

                nv = new NhanVien(maNV, hoDem, ten, sdt, cccd, gioiTinh, ngaySinh, diaChi, nghiViec);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nv;
    }
}
