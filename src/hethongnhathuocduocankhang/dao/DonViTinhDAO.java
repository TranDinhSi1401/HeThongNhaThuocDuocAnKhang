package hethongnhathuocduocankhang.dao;

import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.DonViTinh;
import hethongnhathuocduocankhang.entity.SanPham;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DonViTinhDAO {

    public static ArrayList<DonViTinh> getDonViTinhTheoMaSP(String maSp) {
        ArrayList<DonViTinh> dsDVT = new ArrayList<>();

        try {
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM DonViTinh WHERE MaSP = ? AND daXoa = 0";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maSp);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String maDVT = rs.getString(1);
                String maSP = rs.getString(2);
                String tenDVT = rs.getString(3);
                int heSoQuyDoi = rs.getInt(4);
                double giaBanTheoDonVi = rs.getDouble(5);
                boolean donViTinhCoBan = rs.getInt(6) == 1 ? true : false;
                boolean daXoa = rs.getBoolean("daXoa");
                
                SanPham sp = new SanPham(maSP);
                DonViTinh dvt = new DonViTinh(maDVT, sp, heSoQuyDoi, giaBanTheoDonVi, tenDVT, donViTinhCoBan, daXoa);
                dsDVT.add(dvt);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsDVT;
    }

    public static DonViTinh getDonViTinhTheoMaDVT(String maDVT) {
        DonViTinh dvt = null;

        try {
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM DonViTinh WHERE maDonViTinh = ? AND daXoa = 0";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maDVT);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String maSP = rs.getString(2);
                String tenDVT = rs.getString(3);
                int heSoQuyDoi = rs.getInt(4);
                double giaBanTheoDonVi = rs.getDouble(5);
                boolean donViTinhCoBan = rs.getInt(6) == 1 ? true : false;
                boolean daXoa = rs.getBoolean("daXoa");
                SanPham sp = new SanPham(maSP);
                dvt = new DonViTinh(maDVT, sp, heSoQuyDoi, giaBanTheoDonVi, tenDVT, donViTinhCoBan, daXoa);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dvt;
    }

    public static String getMaSanPhamTheoMaDVT(String maDVT) {
        String maSP = "";
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM DonViTinh WHERE maDonViTinh = ? AND daXoa = 0";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maDVT);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                maSP = rs.getString(2);
                return maSP;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maSP;
    }

    public static boolean themDonViTinh(DonViTinh dvt) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "INSERT INTO DonViTinh VALUES (?, ?, ?, ?, ?, ?, 0)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, dvt.getMaDonViTinh());
            stmt.setString(2, dvt.getSanPham().getMaSP());
            stmt.setString(3, dvt.getTenDonVi());
            stmt.setInt(4, dvt.getHeSoQuyDoi());
            stmt.setDouble(5, dvt.getGiaBanTheoDonVi());
            stmt.setBoolean(6, dvt.isDonViTinhCoBan());
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    // [SOFT DELETE]
    public static boolean xoaDonViTinh(String maDonViTinh) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String query = "UPDATE DonViTinh SET daXoa = 1 WHERE maDonViTinh = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, maDonViTinh);
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    // [SOFT DELETE]
    public static boolean xoaDonViTinhTheoMaSP(String maSP) {
        int n = 0;
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "UPDATE DonViTinh SET daXoa = 1 WHERE maSP = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, maSP);
            n = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public static boolean suaDonViTinh(String maDonViTinh, DonViTinh dvtNew) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String query = "UPDATE DonViTinh SET maSP = ?, tenDonVi = ?, heSoQuyDoi = ?, giaBanTheoDonVi = ?, donViTinhCoBan = ? WHERE maDonViTinh = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, dvtNew.getSanPham().getMaSP());
            stmt.setString(2, dvtNew.getTenDonVi());
            stmt.setInt(3, dvtNew.getHeSoQuyDoi());
            stmt.setDouble(4, dvtNew.getGiaBanTheoDonVi());
            stmt.setBoolean(5, dvtNew.isDonViTinhCoBan());
            stmt.setString(6, maDonViTinh);
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }
}