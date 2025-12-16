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
            String querry = "SELECT ls.* FROM LichSuCaLam ls "
                    + "JOIN NhanVien nv ON ls.maNV = nv.maNV "
                    + "WHERE (nv.hoTenDem + ' ' + nv.ten) LIKE ?";
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
            stmt.setDate(1, Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ds.add(taoDoiTuongLichSuCaLam(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public boolean themLichSuCaLam(LichSuCaLam ls) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "INSERT INTO LichSuCaLam (maNV, maCa, ngayLamViec, thoiGianVaoCa, thoiGianRaCa, ghiChu) VALUES (?, ?, ?, ?, NULL, NULL)";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, ls.getNhanVien().getMaNV());
            stmt.setString(2, ls.getCaLam().getMaCa());
            stmt.setDate(3, Date.valueOf(ls.getNgayLamViec()));
            stmt.setTime(4, Time.valueOf(ls.getThoiGianVaoCa()));

            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public boolean capNhatRaCa(String maNV, String maCa, LocalDate ngayLamViec, LocalTime thoiGianRaCa, String ghiChu) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "UPDATE LichSuCaLam SET thoiGianRaCa = ?, ghiChu = ? WHERE maNV = ? AND maCa = ? AND ngayLamViec = ? AND thoiGianRaCa IS NULL";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setTime(1, Time.valueOf(thoiGianRaCa));
            stmt.setString(2, ghiChu);
            stmt.setString(3, maNV);
            stmt.setString(4, maCa);
            stmt.setDate(5, Date.valueOf(ngayLamViec));

            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    // Hàm kiểm tra xem nhân viên đã vào ca hôm nay chưa (để set trạng thái nút khi mở app)
    public boolean kiemTraNhanVienDangLamViec(String maNV, LocalDate ngayLamViec) {
        boolean dangLam = false;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            // Tìm dòng nào có vào ca mà chưa có ra ca
            String sql = "SELECT * FROM LichSuCaLam WHERE maNV = ? AND ngayLamViec = ? AND thoiGianRaCa IS NULL";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maNV);
            stmt.setDate(2, Date.valueOf(ngayLamViec));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                dangLam = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dangLam;
    }
}
