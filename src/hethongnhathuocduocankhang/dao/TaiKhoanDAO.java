/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.NhanVien;
import hethongnhathuocduocankhang.entity.TaiKhoan;
import java.security.Timestamp;
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
            if (rs.next()) {
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

    public static TaiKhoan getTaiKhoanTheoTenDangNhap(String tenDangNhap) {
        TaiKhoan tk = null;

        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT * FROM TaiKhoan WHERE tenDangNhap = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tenDangNhap);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String matKhau = rs.getString("matKhau");
                boolean quanLy = rs.getBoolean("quanLy");
                boolean biKhoa = rs.getBoolean("biKhoa");
                String email = rs.getString("email");
                LocalDateTime ngayTao = rs.getTimestamp("ngayTao").toLocalDateTime();

                NhanVien nv = NhanVienDAO.getNhanVienTheoMaNV(tenDangNhap);

                tk = new TaiKhoan(tenDangNhap, nv, matKhau, quanLy, biKhoa, email, ngayTao);
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

            String sql = "INSERT INTO TaiKhoan (tenDangNhap, matKhau, quanLy, biKhoa, email, ngayTao) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, tk.getTenDangNhap());
            ps.setString(2, tk.getMatKhau());
            ps.setBoolean(3, tk.isQuanLy());
            ps.setBoolean(4, tk.isBiKhoa());
            ps.setString(5, tk.getEmail());
            ps.setTimestamp(6, java.sql.Timestamp.valueOf(tk.getNgayTao()));

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
                    + "matKhau = ?, "
                    + "quanLy = ?, "
                    + "biKhoa = ?, "
                    + "email = ? "
                    + "WHERE tenDangNhap = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, tk.getMatKhau());
            ps.setBoolean(2, tk.isQuanLy());
            ps.setBoolean(3, tk.isBiKhoa());
            ps.setString(4, tk.getEmail());
            ps.setString(5, tk.getTenDangNhap());

            n = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Trả về true nếu có ít nhất 1 dòng được cập nhật
        return n > 0;
    }

    public static boolean kiemTraEmailTonTai(String email) {

        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT * FROM TaiKhoan WHERE email = ?";
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
}
