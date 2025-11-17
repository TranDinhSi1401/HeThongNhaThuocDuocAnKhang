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
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM SanPham";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String maSP = rs.getString("maSP");
                String tenSP = rs.getString("ten");
                String moTa = rs.getString("moTa");
                String thanhPhan = rs.getString("thanhPhan");
                LoaiSanPhamEnum loaiSanPham = LoaiSanPhamEnum.valueOf(rs.getString("loaiSanPham"));
                int tonToiThieu = rs.getInt("tonToiThieu");
                int tonToiDa = rs.getInt("tonToiDa");
                SanPham sp = new SanPham(maSP, tenSP, moTa, thanhPhan, loaiSanPham, tonToiThieu, tonToiDa);
                dsSP.add(sp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsSP;
    }

    public static boolean themSanPham(SanPham sp) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "INSERT INTO SanPham VALUES (?, ?, ?, ?, ?, ?, ?)";
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
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String query = "DELETE FROM SanPham WHERE maSP = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, maSP);
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public static boolean suaSanPham(String maSP, SanPham spNew) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String query = "UPDATE SanPham SET ten = ?, moTa = ?, thanhPhan = ?, loaiSanPham = ?, tonToiThieu = ?, tonToiDa = ? WHERE maSP = ?";
            PreparedStatement stmt = con.prepareStatement(query);
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
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String query = "SELECT * FROM SanPham WHERE maSP = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, ma);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
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
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String query = "SELECT * FROM SanPham WHERE ten LIKE ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, "%" + tenSP + "%");
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

    public static ArrayList<SanPham> timSPTheoMaNCC(String maNhaCC) {
        ArrayList<SanPham> dsSP = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String query = "SELECT SP.* FROM SanPham SP JOIN SanPhamCungCap SPCC "
                    + "ON SP.maSP = SPCC.maSP JOIN NhaCungCap NCC "
                    + "ON SPCC.maNCC = NCC.maNCC WHERE NCC.maNCC = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, maNhaCC);
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

    public static ArrayList<SanPham> timSPTheoLoai(String loaiSP) {
        ArrayList<SanPham> dsSP = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String query = "SELECT * FROM SanPham WHERE loaiSanPham = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, loaiSP);
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

    public static int getMaSPCuoiCung() {
        int maCuoiCung = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT MAX(maSP) FROM SanPham";

            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                String maSPMax = rs.getString(1);

                if (maSPMax != null && maSPMax.matches("^SP-\\d{4}$")) {
                    try {
                        String maSo = maSPMax.substring(3);
                        maCuoiCung = Integer.parseInt(maSo);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maCuoiCung;
    }
    public static SanPham timMotSPTheoMaNCC(String maNhaCC) {
        SanPham sp = new SanPham();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String query = "SELECT SP.* FROM SanPham SP JOIN SanPhamCungCap SPCC "
                    + "ON SP.maSP = SPCC.maSP JOIN NhaCungCap NCC "
                    + "ON SPCC.maNCC = NCC.maNCC WHERE NCC.maNCC = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, maNhaCC);
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

}
