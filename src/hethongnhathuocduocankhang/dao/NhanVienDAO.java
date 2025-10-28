/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import java.sql.*;
import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.NhanVien;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author trand
 */
public class NhanVienDAO {

    public static ArrayList<NhanVien> getAllNhanVien() {
        ArrayList<NhanVien> dsNV = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM NhanVien";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String maNV = rs.getString("maNV");
                String hoTenDem = rs.getString("hoTenDem");
                String ten = rs.getString("ten");
                String sdt = rs.getString("sdt");
                String cccd = rs.getString("cccd");
                boolean gioiTinh = rs.getBoolean("gioiTinh");
                LocalDate ngaySinh = rs.getDate("ngaySinh").toLocalDate();
                String diaChi = rs.getString("diaChi");
                boolean nghiViec = rs.getBoolean("nghiViec");
                
                NhanVien nv = new NhanVien(maNV, hoTenDem, ten, sdt, cccd, gioiTinh, ngaySinh, diaChi, nghiViec);
                dsNV.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsNV;
    }

    public static boolean themNhanVien(NhanVien nv) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "INSERT NhanVien (maNV, hoTenDem, ten, sdt, cccd, gioiTinh, ngaySinh, diaChi, nghiViec) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nv.getMaNV());
            stmt.setString(2, nv.getHoTenDem());
            stmt.setString(3, nv.getTen());
            stmt.setString(4, nv.getSdt());
            stmt.setString(5, nv.getCccd());
            stmt.setBoolean(6, nv.isGioiTinh());
            stmt.setDate(7, Date.valueOf(nv.getNgaySinh()));
            stmt.setString(8, nv.getDiaChi());
            stmt.setBoolean(9, nv.isNghiViec());
            
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public static boolean xoaNhanVien(String maNV) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "DELETE FROM NhanVien WHERE maNV = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, maNV);
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public static boolean suaNhanVien(String maNV, NhanVien nvNew) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "UPDATE NhanVien SET hoTenDem = ?, ten= ?, sdt = ?, cccd = ?, gioiTinh = ?, ngaySinh = ?, diaChi = ?, nghiViec = ? WHERE maNV = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, nvNew.getHoTenDem());
            stmt.setString(2, nvNew.getTen());
            stmt.setString(3, nvNew.getSdt());
            stmt.setString(4, nvNew.getCccd());
            stmt.setBoolean(5, nvNew.isGioiTinh());
            stmt.setDate(6, Date.valueOf(nvNew.getNgaySinh()));
            stmt.setString(7, nvNew.getDiaChi());
            stmt.setBoolean(8, nvNew.isNghiViec());
            stmt.setString(9, maNV); // Điều kiện WHERE
            
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public static NhanVien timNVTheoMa(String ma) {
        NhanVien nv = null;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT * FROM NhanVien WHERE maNV = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, ma);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String maNV = rs.getString("maNV");
                String hoTenDem = rs.getString("hoTenDem");
                String ten = rs.getString("ten");
                String sdt = rs.getString("sdt");
                String cccd = rs.getString("cccd");
                boolean gioiTinh = rs.getBoolean("gioiTinh");
                LocalDate ngaySinh = rs.getDate("ngaySinh").toLocalDate();
                String diaChi = rs.getString("diaChi");
                boolean nghiViec = rs.getBoolean("nghiViec");
                
                nv = new NhanVien(maNV, hoTenDem, ten, sdt, cccd, gioiTinh, ngaySinh, diaChi, nghiViec);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nv;
    }

    public static ArrayList<NhanVien> timNVTheoTen(String tenNV) {
        ArrayList<NhanVien> dsNV = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            // Tìm kiếm tương đối theo cả họ và tên
            String querry = "SELECT * FROM NhanVien WHERE hoTenDem + ' ' + ten LIKE ?"; 
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, "%" + tenNV + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                 String maNV = rs.getString("maNV");
                String hoTenDem = rs.getString("hoTenDem");
                String ten = rs.getString("ten");
                String sdt = rs.getString("sdt");
                String cccd = rs.getString("cccd");
                boolean gioiTinh = rs.getBoolean("gioiTinh");
                LocalDate ngaySinh = rs.getDate("ngaySinh").toLocalDate();
                String diaChi = rs.getString("diaChi");
                boolean nghiViec = rs.getBoolean("nghiViec");
                
                NhanVien nv = new NhanVien(maNV, hoTenDem, ten, sdt, cccd, gioiTinh, ngaySinh, diaChi, nghiViec);
                dsNV.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsNV;
    }

    public static ArrayList<NhanVien> timNVTheoSDT(String sdtNV) {
         ArrayList<NhanVien> dsNV = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT * FROM NhanVien WHERE sdt = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, sdtNV);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String maNV = rs.getString("maNV");
                String hoTenDem = rs.getString("hoTenDem");
                String ten = rs.getString("ten");
                String sdt = rs.getString("sdt");
                String cccd = rs.getString("cccd");
                boolean gioiTinh = rs.getBoolean("gioiTinh");
                LocalDate ngaySinh = rs.getDate("ngaySinh").toLocalDate();
                String diaChi = rs.getString("diaChi");
                boolean nghiViec = rs.getBoolean("nghiViec");
                
                NhanVien nv = new NhanVien(maNV, hoTenDem, ten, sdt, cccd, gioiTinh, ngaySinh, diaChi, nghiViec);
                dsNV.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsNV;
    }
    
    public static ArrayList<NhanVien> timNVTheoCCCD(String cccdNV) {
         ArrayList<NhanVien> dsNV = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT * FROM NhanVien WHERE cccd = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, cccdNV);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String maNV = rs.getString("maNV");
                String hoTenDem = rs.getString("hoTenDem");
                String ten = rs.getString("ten");
                String sdt = rs.getString("sdt");
                String cccd = rs.getString("cccd");
                boolean gioiTinh = rs.getBoolean("gioiTinh");
                LocalDate ngaySinh = rs.getDate("ngaySinh").toLocalDate();
                String diaChi = rs.getString("diaChi");
                boolean nghiViec = rs.getBoolean("nghiViec");
                
                NhanVien nv = new NhanVien(maNV, hoTenDem, ten, sdt, cccd, gioiTinh, ngaySinh, diaChi, nghiViec);
                dsNV.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsNV;
    }


    public static ArrayList<NhanVien> timNVTheoTrangThai(boolean daNghiViec) {
        ArrayList<NhanVien> dsNV = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT * FROM NhanVien WHERE nghiViec = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setBoolean(1, daNghiViec);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String maNV = rs.getString("maNV");
                String hoTenDem = rs.getString("hoTenDem");
                String ten = rs.getString("ten");
                String sdt = rs.getString("sdt");
                String cccd = rs.getString("cccd");
                boolean gioiTinh = rs.getBoolean("gioiTinh");
                LocalDate ngaySinh = rs.getDate("ngaySinh").toLocalDate();
                String diaChi = rs.getString("diaChi");
                boolean nghiViec = rs.getBoolean("nghiViec");
                
                NhanVien nv = new NhanVien(maNV, hoTenDem, ten, sdt, cccd, gioiTinh, ngaySinh, diaChi, nghiViec);
                dsNV.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsNV;
    }

    public static int getMaNVCuoiCung() {
        int maCuoiCung = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT COUNT(*) FROM NhanVien";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                maCuoiCung = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maCuoiCung;
    }
}