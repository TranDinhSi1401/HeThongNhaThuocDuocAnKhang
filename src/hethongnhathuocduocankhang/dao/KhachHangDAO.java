/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.KhachHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.KhachHang;
import java.util.ArrayList;

/**
 *
 * @author trand
 */
public class KhachHangDAO {

    public static KhachHang getKhachHangTheoSdt(String sdt) throws SQLException {
        KhachHang kh = null;
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM KhachHang WHERE sdt = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, sdt);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String maKH = rs.getString(1);
                String hoDem = rs.getString(2);
                String ten = rs.getString(3);
                int diemTichLuy = rs.getInt(5);
                kh = new KhachHang(maKH, hoDem, ten, sdt, diemTichLuy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kh;
    }

    public static ArrayList<KhachHang> getAllKhachHang() {
        ArrayList<KhachHang> dsKH = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM KhachHang";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String maKH = rs.getString("maKH");
                String hoTenDem = rs.getString("hoTenDem");
                String ten = rs.getString("ten");
                String sdt = rs.getString("sdt");
                int diemTichLuy = rs.getInt("diemTichLuy");

                KhachHang kh = new KhachHang(maKH, hoTenDem, ten, sdt, diemTichLuy);
                dsKH.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsKH;
    }

    public static boolean themKhachHang(KhachHang kh) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "INSERT KhachHang (maKH, hoTenDem, ten, sdt, diemTichLuy) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, kh.getMaKH());
            stmt.setString(2, kh.getHoTenDem());
            stmt.setString(3, kh.getTen());
            stmt.setString(4, kh.getSdt());
            stmt.setInt(5, kh.getDiemTichLuy());
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public static boolean xoaKhachHang(String maKH) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "DELETE FROM KhachHang WHERE maKH = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, maKH);
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public static boolean suaKhachHang(String maKH, KhachHang khNew) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "UPDATE KhachHang SET hoTenDem = ?, ten = ?, sdt = ?, diemTichLuy = ? WHERE maKH = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, khNew.getHoTenDem());
            stmt.setString(2, khNew.getTen());
            stmt.setString(3, khNew.getSdt());
            stmt.setInt(4, khNew.getDiemTichLuy());
            stmt.setString(5, maKH);

            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public static KhachHang timKHTheoMa(String ma) {
        KhachHang kh = null;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT * FROM KhachHang WHERE maKH = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, ma);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String maKH = rs.getString("maKH");
                String hoTenDem = rs.getString("hoTenDem");
                String ten = rs.getString("ten");
                String sdt = rs.getString("sdt");
                int diemTichLuy = rs.getInt("diemTichLuy");

                kh = new KhachHang(maKH, hoTenDem, ten, sdt, diemTichLuy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kh;
    }

    public static boolean updateDiemTichLuy(int diemTichLuy, String maKH) {
        int rows = 0;
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "UPDATE KhachHang SET diemTichLuy = diemTichLuy + ? WHERE maKH = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, diemTichLuy);
            ps.setString(2, maKH);
            rows = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows > 0;
    }

    public static ArrayList<KhachHang> timKHTheoTen(String tenKH) {
        ArrayList<KhachHang> dsKH = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT * FROM KhachHang WHERE hoTenDem + ' ' + ten LIKE ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, "%" + tenKH + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String maKH = rs.getString("maKH");
                String hoTenDem = rs.getString("hoTenDem");
                String ten = rs.getString("ten");
                String sdt = rs.getString("sdt");
                int diemTichLuy = rs.getInt("diemTichLuy");

                KhachHang kh = new KhachHang(maKH, hoTenDem, ten, sdt, diemTichLuy);
                dsKH.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsKH;
    }

    public static ArrayList<KhachHang> timKHTheoSDT(String sdtKH) {
        ArrayList<KhachHang> dsKH = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT * FROM KhachHang WHERE sdt = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, sdtKH);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String maKH = rs.getString("maKH");
                String hoTenDem = rs.getString("hoTenDem");
                String ten = rs.getString("ten");
                String sdt = rs.getString("sdt");
                int diemTichLuy = rs.getInt("diemTichLuy");

                KhachHang kh = new KhachHang(maKH, hoTenDem, ten, sdt, diemTichLuy);
                dsKH.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsKH;
    }

    public static int getMaKHCUoiCung() {
        int maCuoiCung = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT MAX(maKH) FROM KhachHang";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                String maKHMax = rs.getString(1);
                if (maKHMax != null && maKHMax.matches("KH-\\d{5}")) {
                    String maSo = maKHMax.substring(3);
                    maCuoiCung = Integer.parseInt(maSo);
                }
            }

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
        return maCuoiCung;
    }
    
    
    public static ArrayList<KhachHang> getAllKhachHang(String sql) {
        ArrayList<KhachHang> dsKH = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String maKH = rs.getString("maKH");
                String hoTenDem = rs.getString("hoTenDem");
                String ten = rs.getString("ten");
                String sdt = rs.getString("sdt");
                int diemTichLuy = rs.getInt("diemTichLuy");

                KhachHang kh = new KhachHang(maKH, hoTenDem, ten, sdt, diemTichLuy);
                dsKH.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsKH;
    }
}
