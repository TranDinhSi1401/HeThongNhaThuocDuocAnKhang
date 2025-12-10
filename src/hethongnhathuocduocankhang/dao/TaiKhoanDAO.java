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
    public static TaiKhoan getTaiKhoanTheoTenDangNhap(String tenDangNhap) {       
        TaiKhoan tk = null;
        
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            
            String sql = "SELECT * FROM TaiKhoan WHERE tenDangNhap = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tenDangNhap);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                String hashedPassword = rs.getString(2);
                boolean quanLy = rs.getInt(3) == 1;
                boolean biKhoa = rs.getInt(4) == 1;
                String email = rs.getString(5);
                LocalDateTime ngayTao = rs.getTimestamp(6).toLocalDateTime();
                NhanVien nv = NhanVienDAO.getNhanVienTheoMaNV(tenDangNhap);
                tk = new TaiKhoan(tenDangNhap, nv, hashedPassword, quanLy, biKhoa, email, ngayTao);
            }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return tk;
    } 
    
    public static boolean kiemTraEmailTonTai(String email) {               
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            
            String sql = "SELECT * FROM TaiKhoan WHERE email = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return true;
            }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return false;
    }
    
    public static boolean kiemTraEmailThuocTaiKhoan(String userName, String email) {
        String sql = "SELECT 1 FROM TaiKhoan WHERE tenDangNhap = ? AND email = ?";
        try {
            PreparedStatement ps = ConnectDB.getConnection().prepareStatement(sql);
            ps.setString(1, userName);
            ps.setString(2, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean updateMatKhau(String taiKhoan, String email, String matKhauHash) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = ConnectDB.getInstance().getConnection();

            String sql = "UPDATE TaiKhoan SET matKhau = ? WHERE email = ? AND tenDangNhap = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, matKhauHash);
            ps.setString(2, email);
            ps.setString(3, taiKhoan);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // cập nhật thành công nếu > 0
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
