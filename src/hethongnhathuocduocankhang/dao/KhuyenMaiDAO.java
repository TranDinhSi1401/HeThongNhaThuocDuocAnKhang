/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import java.sql.*;
import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.KhuyenMai;
import hethongnhathuocduocankhang.entity.LoaiKhuyenMaiEnum;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class KhuyenMaiDAO {

    private static KhuyenMai taoDoiTuongKhuyenMai(ResultSet rs) throws SQLException {
        String maKM = rs.getString("maKhuyenMai");
        String moTa = rs.getString("moTa");
        double phanTram = rs.getDouble("phanTram");
        LoaiKhuyenMaiEnum loai = LoaiKhuyenMaiEnum.valueOf(rs.getString("loaiKhuyenMai"));
        LocalDateTime ngayBatDau = rs.getTimestamp("ngayBatDau").toLocalDateTime();
        LocalDateTime ngayKetThuc = rs.getTimestamp("ngayKetThuc").toLocalDateTime();
        int slToiThieu = rs.getInt("soLuongToiThieu");
        int slToiDa = rs.getInt("soLuongToiDa");
        LocalDateTime ngayChinhSua = rs.getTimestamp("ngayChinhSua").toLocalDateTime();

        return new KhuyenMai(maKM, moTa, phanTram, loai, ngayBatDau, ngayKetThuc, slToiThieu, slToiDa, ngayChinhSua);
    }

    public static ArrayList<KhuyenMai> getKhuyenMaiTheoMaSP(String maSp) {
        ArrayList<KhuyenMai> dsKM = new ArrayList<>();
        try {
            Connection con = ConnectDB.getConnection();
            String sql = """
                         SELECT km.maKhuyenMai, km.phanTram, km.soLuongToiThieu, km.soLuongToiDa 
                         FROM SanPham sp
                         JOIN KhuyenMai_SanPham kmsp
                         ON sp.maSP = kmsp.maSP
                         JOIN KhuyenMai km
                         ON km.maKhuyenMai = kmsp.maKhuyenMai
                         WHERE sp.maSP = ? AND NgayBatDau <= GETDATE() AND ngayKetThuc >= GETDATE() AND kmsp.daXoa = 0
                         """;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maSp);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String maKM = rs.getString(1);
                double phanTram = rs.getDouble(2);
                int soLuongToiThieu = rs.getInt(3);
                int soLuongToiDa = rs.getInt(4);
                KhuyenMai km = new KhuyenMai(maKM, phanTram, soLuongToiThieu, soLuongToiDa);
                dsKM.add(km);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsKM;
    }

    public static ArrayList<KhuyenMai> getAllKhuyenMai() {
        ArrayList<KhuyenMai> dsKM = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM KhuyenMai WHERE daXoa = 0";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                dsKM.add(taoDoiTuongKhuyenMai(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsKM;
    }

    public static boolean themKhuyenMai(KhuyenMai km) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "INSERT INTO KhuyenMai (maKhuyenMai, moTa, phanTram, loaiKhuyenMai, ngayBatDau, ngayKetThuc, soLuongToiThieu, soLuongToiDa) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, km.getMaKhuyenMai());
            stmt.setString(2, km.getMoTa());
            stmt.setDouble(3, km.getPhanTram());
            stmt.setString(4, km.getLoaiKhuyenMai().toString());
            stmt.setTimestamp(5, Timestamp.valueOf(km.getNgayBatDau()));
            stmt.setTimestamp(6, Timestamp.valueOf(km.getNgayKetThuc()));
            stmt.setInt(7, km.getSoLuongToiThieu());
            stmt.setInt(8, km.getSoLuongToiDa());

            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public static boolean xoaKhuyenMai(String maKM) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "UPDATE KhuyenMai SET daXoa = 1 WHERE maKhuyenMai = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, maKM);
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public static boolean suaKhuyenMai(String maKM, KhuyenMai kmNew) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "UPDATE KhuyenMai SET moTa = ?, phanTram = ?, loaiKhuyenMai = ?, ngayBatDau = ?, "
                    + "ngayKetThuc = ?, soLuongToiThieu = ?, soLuongToiDa = ?, ngayChinhSua = GETDATE() "
                    + "WHERE maKhuyenMai = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, kmNew.getMoTa());
            stmt.setDouble(2, kmNew.getPhanTram());
            stmt.setString(3, kmNew.getLoaiKhuyenMai().toString());
            stmt.setTimestamp(4, Timestamp.valueOf(kmNew.getNgayBatDau()));
            stmt.setTimestamp(5, Timestamp.valueOf(kmNew.getNgayKetThuc()));
            stmt.setInt(6, kmNew.getSoLuongToiThieu());
            stmt.setInt(7, kmNew.getSoLuongToiDa());
            stmt.setString(8, maKM);

            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public static KhuyenMai timKMTheoMa(String ma) {
        KhuyenMai km = null;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT * FROM KhuyenMai WHERE maKhuyenMai = ? AND daXoa = 0";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, ma);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                km = taoDoiTuongKhuyenMai(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return km;
    }

    public static ArrayList<KhuyenMai> timKMTheoMoTa(String moTa) {
        ArrayList<KhuyenMai> dsKM = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT * FROM KhuyenMai WHERE moTa LIKE ? AND daXoa = 0";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, "%" + moTa + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsKM.add(taoDoiTuongKhuyenMai(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsKM;
    }

    public static ArrayList<KhuyenMai> timKMTheoLoai(LoaiKhuyenMaiEnum loai) {
        ArrayList<KhuyenMai> dsKM = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT * FROM KhuyenMai WHERE loaiKhuyenMai = ? AND daXoa = 0";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, loai.toString());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsKM.add(taoDoiTuongKhuyenMai(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsKM;
    }

    public static int getMaKMCuoiCung() {
        int maCuoiCung = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT MAX(maKhuyenMai) FROM KhuyenMai";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                String maKMMax = rs.getString(1);
                if (maKMMax != null && maKMMax.matches("KM-\\d{4}")) {
                    String maSo = maKMMax.substring(3);
                    maCuoiCung = Integer.parseInt(maSo);
                }
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
        return maCuoiCung;
    }
}
