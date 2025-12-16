package hethongnhathuocduocankhang.dao;

import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.ThongBao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * DAO for notification queries - detects expired/expiring batches
 */
public class ThongBaoDAO {

    /**
     * Get all expired batches (ngayHetHan < today)
     */
    public static ArrayList<ThongBao> getDanhSachLoBiHetHan() {
        ArrayList<ThongBao> dsBao = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT l.maLoSanPham, l.maSP, sp.ten, l.ngayHetHan, l.soLuong " +
                    "FROM LoSanPham l " +
                    "JOIN SanPham sp ON l.maSP = sp.maSP " +
                    "WHERE l.ngayHetHan < CAST(GETDATE() AS DATE) AND l.daHuy = 0 " +
                    "ORDER BY l.ngayHetHan ASC";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String maLo = rs.getString(1);
                String maSP = rs.getString(2);
                String tenSP = rs.getString(3);
                LocalDate ngayHH = rs.getDate(4).toLocalDate();
                int soLuong = rs.getInt(5);
                int soNgayConLai = (int) ChronoUnit.DAYS.between(ngayHH, LocalDate.now());
                ThongBao bao = new ThongBao(maLo, maSP, tenSP, ngayHH, soLuong, ThongBao.HET_HAN, -soNgayConLai);
                dsBao.add(bao);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsBao;
    }

    /**
     * Get all warning batches (expiring within 30 days but not yet expired)
     */
    public static ArrayList<ThongBao> getDanhSachLoCanhBao(int soNgayCanhBao) {
        ArrayList<ThongBao> dsBao = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT l.maLoSanPham, l.maSP, sp.ten, l.ngayHetHan, l.soLuong " +
                    "FROM LoSanPham l " +
                    "JOIN SanPham sp ON l.maSP = sp.maSP " +
                    "WHERE DATEDIFF(DAY, CAST(GETDATE() AS DATE), l.ngayHetHan) BETWEEN 1 AND ? " +
                    "AND l.daHuy = 0 " +
                    "ORDER BY l.ngayHetHan ASC";
            java.sql.PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, soNgayCanhBao);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String maLo = rs.getString(1);
                String maSP = rs.getString(2);
                String tenSP = rs.getString(3);
                LocalDate ngayHH = rs.getDate(4).toLocalDate();
                int soLuong = rs.getInt(5);
                int soNgayConLai = (int) ChronoUnit.DAYS.between(LocalDate.now(), ngayHH);
                ThongBao bao = new ThongBao(maLo, maSP, tenSP, ngayHH, soLuong, ThongBao.CANH_BAO, soNgayConLai);
                dsBao.add(bao);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsBao;
    }

    /**
     * Get all notifications (combined expired + warning)
     */
    public static ArrayList<ThongBao> getDanhSachThongBao(int soNgayCanhBao) {
        ArrayList<ThongBao> dsBao = new ArrayList<>();
        dsBao.addAll(getDanhSachLoBiHetHan());
        dsBao.addAll(getDanhSachLoCanhBao(soNgayCanhBao));
        return dsBao;
    }
}
