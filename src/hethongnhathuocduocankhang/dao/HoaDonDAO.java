/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.HoaDon;
import hethongnhathuocduocankhang.entity.KhachHang;
import hethongnhathuocduocankhang.entity.NhanVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HoaDonDAO {

    private static HoaDon taoDoiTuongHoaDon(ResultSet rs) throws SQLException {
        String maHoaDon = rs.getString("maHoaDon");
        String maNV = rs.getString("maNV");
        LocalDateTime ngayLapHD = rs.getTimestamp("ngayLapHoaDon").toLocalDateTime();
        String maKH = rs.getString("maKH");
        boolean chuyenKhoan = rs.getBoolean("chuyenKhoan");
        boolean trangThai = rs.getBoolean("trangThai");
        double tongTien = rs.getDouble("tongTien");

        NhanVien nv = NhanVienDAO.getNhanVienTheoMaNV(maNV);
        KhachHang kh = KhachHangDAO.timKHTheoMa(maKH);

        if (nv == null) {
            nv = new NhanVien(maNV);
        }
        if (kh == null) {
            kh = new KhachHang(maKH);
        }

        return new HoaDon(maHoaDon, nv, ngayLapHD, kh, chuyenKhoan, trangThai, tongTien);
    }

    public static HoaDon getHoaDonMoiNhatTrongNgay() {
        HoaDon hd = null;
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT TOP 1 * FROM HoaDon WHERE CAST(ngayLapHoaDon AS DATE) = CAST(GETDATE() AS DATE) ORDER BY maHoaDon DESC";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                hd = taoDoiTuongHoaDon(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hd;
    }

    public static HoaDon getHoaDonTheoMaHD(String maHD) {
        HoaDon hd = null;
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM HoaDon WHERE MaHoaDon = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maHD);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                hd = taoDoiTuongHoaDon(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hd;
    }

    public static boolean insertHoaDon(HoaDon hd) {
        int n = 0;
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO HoaDon (maHoaDon, maNV, ngayLapHoaDon, maKH, chuyenKhoan, trangThai, tongTien) VALUES (?, ?, ?, ?, ?, ?, ?)"
            );

            ps.setString(1, hd.getMaHoaDon());
            ps.setString(2, hd.getNhanVien().getMaNV());
            ps.setTimestamp(3, Timestamp.valueOf(hd.getNgayLapHoaDon()));
            ps.setString(4, hd.getKhachHang().getMaKH());
            ps.setBoolean(5, hd.isChuyenKhoan());
            ps.setBoolean(6, hd.isTrangThai());
            ps.setDouble(7, hd.getTongTien());

            n = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public static ArrayList<HoaDon> getAllHoaDon() {
        ArrayList<HoaDon> dsHD = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM HoaDon";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                dsHD.add(taoDoiTuongHoaDon(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsHD;
    }

    public static ArrayList<HoaDon> timHDTheoMaNV(String maNV) {
        ArrayList<HoaDon> dsHD = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT * FROM HoaDon WHERE maNV = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, maNV);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsHD.add(taoDoiTuongHoaDon(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsHD;
    }

    public static ArrayList<HoaDon> timHDTheoMaKH(String maKH) {
        ArrayList<HoaDon> dsHD = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT * FROM HoaDon WHERE maKH = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, maKH);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsHD.add(taoDoiTuongHoaDon(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsHD;
    }

    public static ArrayList<HoaDon> timHDTheoNgayLap(LocalDate date) {
        ArrayList<HoaDon> dsHD = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT * FROM HoaDon WHERE CONVERT(DATE, ngayLapHoaDon) = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setDate(1, Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsHD.add(taoDoiTuongHoaDon(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsHD;
    }

    public static double getDoanhThuTheoNgay(LocalDate date) {
        double doanhThu = 0.0;
        ArrayList<HoaDon> dsHD = timHDTheoNgayLap(date);

        for (HoaDon hoaDon : dsHD) {
            doanhThu += hoaDon.getTongTien();
        }

        return doanhThu;
    }

    public static Map<Integer, Double> getDoanhThuTungNgayTrongThang(LocalDate date) {
        Map<Integer, Double> doanhthuTungNgay = new HashMap<>();

        int soNgayTrongThang = date.lengthOfMonth();
        int nam = date.getYear();
        int thang = date.getMonthValue();

        for (int i = 1; i <= soNgayTrongThang; i++) {
            LocalDate ngayHienTai = LocalDate.of(nam, thang, i);
            doanhthuTungNgay.put(i, getDoanhThuTheoNgay(ngayHienTai));
        }

        return doanhthuTungNgay;
    }

    public static double getDoanhThuTheoThang(LocalDate date) {
        double doanhThu = 0.0;

        int soNgayTrongThang = date.lengthOfMonth();
        int nam = date.getYear();
        int thang = date.getMonthValue();

        for (int i = 1; i <= soNgayTrongThang; i++) {
            LocalDate ngayHienTai = LocalDate.of(nam, thang, i);
            doanhThu += getDoanhThuTheoNgay(ngayHienTai);
        }

        return doanhThu;
    }

    public static Map<Integer, Double> getDoanhThuTungThangTrongNam(LocalDate date) {
        Map<Integer, Double> doanhthuTungThang = new HashMap<>();

        int nam = date.getYear();
        int thang = date.getMonthValue();

        for (int i = 1; i <= 12; i++) {
            LocalDate thangHienTai = LocalDate.of(nam, i, 1);
            doanhthuTungThang.put(i, getDoanhThuTheoThang(thangHienTai));
        }

        return doanhthuTungThang;
    }

    public static ArrayList<HoaDon> timHDTheoSDTKH(String sdt) {
        ArrayList<HoaDon> dsHD = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT hd.* FROM HoaDon hd "
                    + "JOIN KhachHang kh ON hd.maKH = kh.maKH "
                    + "WHERE kh.sdt = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, sdt);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsHD.add(taoDoiTuongHoaDon(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsHD;
    }

    public static ArrayList<HoaDon> timHDTheoTrangThai(boolean trangThai) {
        ArrayList<HoaDon> dsHD = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT * FROM HoaDon WHERE trangThai = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setBoolean(1, trangThai);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsHD.add(taoDoiTuongHoaDon(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsHD;
    }

    public static ArrayList<HoaDon> timHDTheoHinhThuc(boolean chuyenKhoan) {
        ArrayList<HoaDon> dsHD = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT * FROM HoaDon WHERE chuyenKhoan = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setBoolean(1, chuyenKhoan);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsHD.add(taoDoiTuongHoaDon(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsHD;
    }

    public static int getSoHDCuoiCungTrongNgay(String ngay) {
        int maCuoiCung = 0;
        String maHDFormat = "HD-" + ngay + "-";
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT MAX(maHoaDon) FROM HoaDon WHERE maHoaDon LIKE ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maHDFormat + "%");
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String maHDMax = rs.getString(1);
                if (maHDMax != null) {
                    String maSo = maHDMax.substring(maHDFormat.length());
                    maCuoiCung = Integer.parseInt(maSo);
                }
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
        return maCuoiCung;
    }
}
