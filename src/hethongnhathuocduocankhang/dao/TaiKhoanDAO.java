/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.NhanVien;
import hethongnhathuocduocankhang.entity.TaiKhoan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 *
 * @author trand
 */
public class TaiKhoanDAO {
    public static TaiKhoan getTaiKhoanTheoTenDangNhapVaMatKhau(String tenDangNhap, String matKhau) {       
        TaiKhoan tk = null;
        
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            
            String sql = "SELECT * FROM TaiKhoan WHERE tenDangNhap = ? AND matKhau = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tenDangNhap);
            ps.setString(2, matKhau);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                boolean quanLy = rs.getInt(3) == 1;
                boolean biKhoa = rs.getInt(4) == 1;
                String email = rs.getString(5);
                LocalDateTime ngayTao = rs.getTimestamp(6).toLocalDateTime();
                NhanVien nv = NhanVienDAO.getNhanVienTheoMaNV(tenDangNhap);
                tk = new TaiKhoan(tenDangNhap, nv, matKhau, quanLy, biKhoa, email, ngayTao);
            }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return tk;
    } 
}
