/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import java.sql.*;
import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.LichSuCaLam;
import hethongnhathuocduocankhang.entity.CaLam;
import hethongnhathuocduocankhang.entity.NhanVien;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class LichSuCaLamDAO {

    private static LichSuCaLam taoDoiTuongLichSuCaLam(ResultSet rs) throws SQLException {
        String maNV = rs.getString("maNV");
        String maCa = rs.getString("maCa");
        LocalDate ngayLamViec = rs.getDate("ngayLamViec").toLocalDate();
        LocalTime thoiGianVaoCa = rs.getTime("thoiGianVaoCa").toLocalTime();

        Time timeRaCa = rs.getTime("thoiGianRaCa");
        LocalTime thoiGianRaCa = (timeRaCa != null) ? timeRaCa.toLocalTime() : null;
        
        String ghiChu = rs.getString("ghiChu");

        NhanVien nhanVien = NhanVienDAO.timNVTheoMa(maNV); 
        CaLam caLam = CaLamDAO.timCaLamTheoMa(maCa);

        if (nhanVien == null) {
            nhanVien = new NhanVien(maNV);
        }
        if (caLam == null) {
            caLam = new CaLam(maCa, maCa, thoiGianRaCa, thoiGianRaCa);
        }

        return new LichSuCaLam(nhanVien, ngayLamViec, caLam, thoiGianVaoCa, thoiGianRaCa, ghiChu);
    }

    public static ArrayList<LichSuCaLam> getAllLichSuCaLam() {
        ArrayList<LichSuCaLam> ds = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM LichSuCaLam";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                ds.add(taoDoiTuongLichSuCaLam(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public static ArrayList<LichSuCaLam> timTheoMaNV(String maNV) {
        ArrayList<LichSuCaLam> ds = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT * FROM LichSuCaLam WHERE maNV = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, maNV);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ds.add(taoDoiTuongLichSuCaLam(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
    
    public static ArrayList<LichSuCaLam> timTheoTenNV(String tenNV) {
        ArrayList<LichSuCaLam> ds = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT ls.* FROM LichSuCaLam ls " +
                            "JOIN NhanVien nv ON ls.maNV = nv.maNV " +
                            "WHERE (nv.hoTenDem + ' ' + nv.ten) LIKE ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, "%" + tenNV + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ds.add(taoDoiTuongLichSuCaLam(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
    
    public static ArrayList<LichSuCaLam> timTheoMaCa(String maCa) {
        ArrayList<LichSuCaLam> ds = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT * FROM LichSuCaLam WHERE maCa = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, maCa);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ds.add(taoDoiTuongLichSuCaLam(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
    
    public static ArrayList<LichSuCaLam> timTheoNgayLam(LocalDate date) {
        ArrayList<LichSuCaLam> ds = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT * FROM LichSuCaLam WHERE ngayLamViec = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setDate(1, Date.valueOf(date)); // CSDL dùng DATE
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ds.add(taoDoiTuongLichSuCaLam(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
}