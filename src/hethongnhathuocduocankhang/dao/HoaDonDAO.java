/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.DoanhThu;
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
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HoaDonDAO {

    private static HoaDon taoDoiTuongHoaDon(ResultSet rs) throws SQLException {
        String maHoaDon = rs.getString("maHoaDon");
        String maNV = rs.getString("maNV");
        LocalDateTime ngayLapHD = rs.getTimestamp("ngayLapHoaDon").toLocalDateTime();
        String maKH = rs.getString("maKH");
        boolean chuyenKhoan = rs.getBoolean("chuyenKhoan");
        double tongTien = rs.getDouble("tongTien");

        NhanVien nv = NhanVienDAO.getNhanVienTheoMaNV(maNV);
        KhachHang kh = KhachHangDAO.timKHTheoMa(maKH);

        if (nv == null) {
            nv = new NhanVien(maNV);
        }
        if (kh == null) {
            kh = new KhachHang(maKH);
        }

        return new HoaDon(maHoaDon, nv, ngayLapHD, kh, chuyenKhoan, tongTien);
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
                    "INSERT INTO HoaDon (maHoaDon, maNV, ngayLapHoaDon, maKH, chuyenKhoan, tongTien) VALUES (?, ?, ?, ?, ?, ?)"
            );

            ps.setString(1, hd.getMaHoaDon());
            ps.setString(2, hd.getNhanVien().getMaNV());
            ps.setTimestamp(3, Timestamp.valueOf(hd.getNgayLapHoaDon()));
            ps.setString(4, hd.getKhachHang().getMaKH());
            ps.setBoolean(5, hd.isChuyenKhoan());
            ps.setDouble(6, hd.getTongTien());

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

    public static int getSoPTH(String maHoaDon) {
        int soPTH = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "select hd.maHoaDon, count(pth.maPhieuTraHang) as tongPhieuTra from HoaDon hd join PhieuTraHang pth on hd.maHoaDon = pth.maHoaDon where hd.maHoaDon = ? group by hd.maHoaDon";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maHoaDon);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                soPTH = rs.getInt(2);
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
        return soPTH;
    }
    
    public static List<DoanhThu> getDoanhThuTungNgayTrongKhoangThoiGian(LocalDate begin, LocalDate end) {
        List<DoanhThu> list = new ArrayList<>();

        if (begin == null || end == null || begin.isAfter(end)) {
            return list;
        }
        
        String sql = """
            SELECT
                CAST(ngayLapHoaDon AS DATE) AS Ngay,
                COUNT(DISTINCT MaHoaDon) AS TongHoaDon,
                SUM(TongTien) AS TongDoanhThu
            FROM HoaDon
            WHERE ngayLapHoaDon >= ?
              AND ngayLapHoaDon <  ?
            GROUP BY CAST(ngayLapHoaDon AS DATE)
            ORDER BY Ngay;
        """;
        
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setDate(1, Date.valueOf(begin));
            ps.setDate(2, Date.valueOf(end));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String ngay = rs.getDate("Ngay").toString();
                int tongHoaDon = rs.getInt("TongHoaDon");
                double tongDoanhThu = rs.getDouble("TongDoanhThu");
                DoanhThu doanhThuTheoNgay = new DoanhThu(ngay, tongHoaDon, tongDoanhThu);
                list.add(doanhThuTheoNgay);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;   
    }
    
    public static List<DoanhThu> getDoanhThuTungThangTrongNam(int nam) {
        List<DoanhThu> list = new ArrayList<>();
        
        String sql = """
            WITH Thang AS (
                SELECT 1 AS Thang UNION ALL SELECT 2 UNION ALL SELECT 3
                UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6
                UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9
                UNION ALL SELECT 10 UNION ALL SELECT 11 UNION ALL SELECT 12
            )
            SELECT
                t.Thang,
                ISNULL(COUNT(DISTINCT h.MaHoaDon), 0) AS TongHoaDon,
                ISNULL(SUM(h.TongTien), 0) AS TongDoanhThu
            FROM Thang t
            LEFT JOIN HoaDon h
                ON MONTH(h.ngayLapHoaDon) = t.Thang
               AND YEAR(h.ngayLapHoaDon) = ?
            GROUP BY t.Thang
            ORDER BY t.Thang;
        """;
        
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, nam);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String thang = String.valueOf(rs.getInt("Thang"));
                int tongHoaDon = rs.getInt("TongHoaDon");
                double tongDoanhThu = rs.getDouble("TongDoanhThu");
                DoanhThu doanhThuTheoThang = new DoanhThu(thang + "/" + nam, tongHoaDon, tongDoanhThu);
                list.add(doanhThuTheoThang);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;   
    }
    
    public static List<DoanhThu> getDoanhThuTungQuyTrongNam(int nam) {
        List<DoanhThu> list = new ArrayList<>();
        
        String sql = """
            WITH Quy AS (
                SELECT 1 AS Quy
                UNION ALL SELECT 2
                UNION ALL SELECT 3
                UNION ALL SELECT 4
            )
            SELECT
                q.Quy,
                ISNULL(COUNT(DISTINCT h.MaHoaDon), 0) AS TongHoaDon,
                ISNULL(SUM(h.TongTien), 0) AS TongDoanhThu
            FROM Quy q
            LEFT JOIN HoaDon h
                ON DATEPART(QUARTER, h.ngayLapHoaDon) = q.Quy
               AND YEAR(h.ngayLapHoaDon) = ?
            GROUP BY q.Quy
            ORDER BY q.Quy;
        """;
        
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, nam);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String quy = String.valueOf(rs.getInt("Quy"));
                int tongHoaDon = rs.getInt("TongHoaDon");
                double tongDoanhThu = rs.getDouble("TongDoanhThu");
                DoanhThu doanhThuTheoThang = new DoanhThu("Quý " + quy + "/" + nam, tongHoaDon, tongDoanhThu);
                list.add(doanhThuTheoThang);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;   
    }
    
    public static List<DoanhThu> getDoanhThuTungNamTheoKhoang(int namBatDau, int namKetThuc) {
        List<DoanhThu> list = new ArrayList<>();
        
        String sql = """
            SELECT
                YEAR(ngayLapHoaDon) AS Nam,
                COUNT(DISTINCT MaHoaDon) AS TongHoaDon,
                SUM(TongTien) AS TongDoanhThu
            FROM HoaDon
            WHERE YEAR(ngayLapHoaDon) >= ? AND YEAR(ngayLapHoaDon) <= ?
            GROUP BY YEAR(ngayLapHoaDon)
            ORDER BY Nam
        """;
        
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, namBatDau);
            ps.setInt(2, namKetThuc);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String nam = String.valueOf(rs.getInt("Nam"));
                int tongHoaDon = rs.getInt("TongHoaDon");
                double tongDoanhThu = rs.getDouble("TongDoanhThu");
                DoanhThu doanhThuTheoThang = new DoanhThu(nam, tongHoaDon, tongDoanhThu);
                list.add(doanhThuTheoThang);
            }    
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return list;
    } 
    
    public static ArrayList<HoaDon> timHDTheoKhoangNgay(LocalDate startDate, LocalDate endDate) {
        ArrayList<HoaDon> dsHD = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT * FROM HoaDon WHERE CONVERT(DATE, ngayLapHoaDon) BETWEEN ? AND ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setDate(1, Date.valueOf(startDate));
            stmt.setDate(2, Date.valueOf(endDate));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsHD.add(taoDoiTuongHoaDon(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsHD;   
    }
}
