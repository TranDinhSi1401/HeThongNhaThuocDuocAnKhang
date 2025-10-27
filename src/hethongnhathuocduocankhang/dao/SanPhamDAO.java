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

    public static ArrayList<SanPham> getAllSanPham() {
        ArrayList<SanPham> dsSP = new ArrayList<>();
        try (Connection con = ConnectDB.getConnection()) {
            String sql = "SELECT * FROM SanPham";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String maSP = rs.getString("maSP");
                String ten = rs.getString("ten");
                String moTa = rs.getString("moTa");
                String thanhPhan = rs.getString("thanhPhan");
                LoaiSanPhamEnum loaiSanPham = LoaiSanPhamEnum.valueOf(rs.getString("loaiSanPham"));
                int tonToiThieu = rs.getInt("tonToiThieu");
                int tonToiDa = rs.getInt("tonToiDa");
                SanPham sp = new SanPham(maSP, ten, moTa, thanhPhan, loaiSanPham, tonToiThieu, tonToiDa);
                dsSP.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsSP; 
    }

    public static boolean themSanPham(SanPham sp) {
        int n = 0;
        try (Connection con = ConnectDB.getConnection()) {
            String sql = "INSERT SanPham VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, sp.getMaSP());
            stmt.setString(2, sp.getTen());
            stmt.setString(3, sp.getMoTa());
            stmt.setString(4, sp.getThanhPhan());
            stmt.setString(5, sp.getLoaiSanPham().toString());
            stmt.setInt(6, sp.getTonToiThieu());
            stmt.setInt(7, sp.getTonToiDa());
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public static boolean xoaSanPham(String maSP) {
        int n = 0;
        try (Connection con = ConnectDB.getConnection()) {
            String querry = "DELETE FROM SanPham WHERE maSP = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, maSP);
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public static boolean suaSanPham(String maSP, SanPham spNew) {
        int n = 0;
        try (Connection con = ConnectDB.getConnection()) {
            String querry = "UPDATE SanPham SET ten = ?, thanhPhan = ?, loaiSanPham = ?, tonToiThieu = ?, tonToiDa = ? WHERE maSP = ?";
            PreparedStatement stmt = con.prepareStatement(querry);           
            stmt.setString(1, spNew.getTen());
            stmt.setString(2, spNew.getMoTa());
            stmt.setString(3, spNew.getThanhPhan());
            stmt.setString(4, spNew.getLoaiSanPham().toString());
            stmt.setInt(5, spNew.getTonToiThieu());
            stmt.setInt(6, spNew.getTonToiDa());
            stmt.setString(7, maSP);
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public static SanPham timSPTheoMa(String ma) {
        SanPham sp = null;
        try (Connection con = ConnectDB.getConnection()) {
            String querry = "SELECT * FROM SanPham WHERE maSP = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, ma);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String maSP = rs.getString("maSP");
                String ten = rs.getString("ten");
                String moTa = rs.getString("moTa");
                String thanhPhan = rs.getString("thanhPhan");
                LoaiSanPhamEnum loaiSanPham = LoaiSanPhamEnum.valueOf(rs.getString("loaiSanPham"));
                int tonToiThieu = rs.getInt("tonToiThieu");
                int tonToiDa = rs.getInt("tonToiDa");
                sp = new SanPham(maSP, ten, moTa, thanhPhan, loaiSanPham, tonToiThieu, tonToiDa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sp;
    }
    
    public static ArrayList<SanPham> timSPTheoTen(String tenSP) {
        ArrayList<SanPham> dsSP = new ArrayList<>();
        try (Connection con = ConnectDB.getConnection()) {
            String querry = "SELECT * FROM SanPham WHERE ten = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, tenSP);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String maSP = rs.getString("maSP");
                String ten = rs.getString("ten");
                String moTa = rs.getString("moTa");
                String thanhPhan = rs.getString("thanhPhan");
                LoaiSanPhamEnum loaiSanPham = LoaiSanPhamEnum.valueOf(rs.getString("loaiSanPham"));
                int tonToiThieu = rs.getInt("tonToiThieu");
                int tonToiDa = rs.getInt("tonToiDa");
                SanPham sp = new SanPham(maSP, ten, moTa, thanhPhan, loaiSanPham, tonToiThieu, tonToiDa);
                dsSP.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsSP;  
    }
    
    public static ArrayList<SanPham> timSPTheoNCC(String tenNhaCC) {
        ArrayList<SanPham> dsSP = new ArrayList<>();
        try (Connection con = ConnectDB.getConnection()) {
            String querry = "SELECT * FROM SanPham SP JOIN SanPhamCungCap SPCC "
                        + "ON SP.maSP = SPCC.maSP JOIN NhaCungCap NCC "
                        + "ON SPCC.maNCC = NCC.maNCC "
                        + "WHERE NCC.tenNCC = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, tenNhaCC);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String maSP = rs.getString("maSP");
                String ten = rs.getString("ten");
                String moTa = rs.getString("moTa");
                String thanhPhan = rs.getString("thanhPhan");
                LoaiSanPhamEnum loaiSanPham = LoaiSanPhamEnum.valueOf(rs.getString("loaiSanPham"));
                int tonToiThieu = rs.getInt("tonToiThieu");
                int tonToiDa = rs.getInt("tonToiDa");
                SanPham sp = new SanPham(maSP, ten, moTa, thanhPhan, loaiSanPham, tonToiThieu, tonToiDa);
                dsSP.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsSP;  
    }
}
