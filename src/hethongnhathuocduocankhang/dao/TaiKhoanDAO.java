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
                String maNV = rs.getString("tenDangNhap");
                String hashedPassword = rs.getString("matKhau");
                boolean quanLy = rs.getBoolean("quanLy");
                boolean quanLyLo = rs.getBoolean("quanLyLo"); 
                boolean biKhoa = rs.getBoolean("biKhoa");
                String email = rs.getString("email");

                java.sql.Timestamp ts = rs.getTimestamp("ngayTao");
                LocalDateTime ngayTao = (ts != null) ? ts.toLocalDateTime() : null;
                
                NhanVien nv = NhanVienDAO.getNhanVienTheoMaNV(maNV);

                tk = new TaiKhoan(maNV, nv, hashedPassword, quanLy, quanLyLo, biKhoa, email, ngayTao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tk;
    }

    public static boolean themTaiKhoan(TaiKhoan tk) {
        int n = 0;
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            String sql = "INSERT INTO TaiKhoan (tenDangNhap, matKhau, quanLy, quanLyLo, biKhoa, email, ngayTao, daXoa) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, 0)"; // Mặc định daXoa là 0

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, tk.getTenDangNhap());
            ps.setString(2, tk.getMatKhau());
            ps.setBoolean(3, tk.isQuanLy());
            ps.setBoolean(4, tk.isQuanLyLo());
            ps.setBoolean(5, tk.isBiKhoa());
            ps.setString(6, tk.getEmail());
            ps.setTimestamp(7, java.sql.Timestamp.valueOf(tk.getNgayTao()));

            n = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public static boolean xoaTaiKhoan(String tenDangNhap) {
        int n = 0;
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            String sql = "UPDATE TaiKhoan SET biKhoa = 1 WHERE tenDangNhap = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tenDangNhap);

            n = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public static boolean capNhatTaiKhoan(TaiKhoan tk) {
        int n = 0;
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            String sql = "UPDATE TaiKhoan SET "
                    + "quanLy = ?, "
                    + "quanLyLo = ?, " 
                    + "biKhoa = ?, "
                    + "email = ? "
                    + "WHERE tenDangNhap = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setBoolean(1, tk.isQuanLy());
            ps.setBoolean(2, tk.isQuanLyLo());
            ps.setBoolean(3, tk.isBiKhoa());
            ps.setString(4, tk.getEmail());
            ps.setString(5, tk.getTenDangNhap());

            n = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n > 0;
    }

    public static boolean kiemTraEmailTonTai(String email) {
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT tenDangNhap FROM TaiKhoan WHERE email = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
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
            return rowsAffected > 0; 
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