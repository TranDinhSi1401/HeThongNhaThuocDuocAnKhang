/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.ChiTietHoaDon;
import hethongnhathuocduocankhang.entity.DonViTinh;
import hethongnhathuocduocankhang.entity.HoaDon;
import hethongnhathuocduocankhang.entity.SanPham;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ChiTietHoaDonDAO {

    public static ChiTietHoaDon getChiTietHoaDonMoiNhat() {
        ChiTietHoaDon cthd = null;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM ChiTietHoaDon "
                    + "WHERE maChiTietHoaDon = (SELECT MAX(maChiTietHoaDon) FROM ChiTietHoaDon)";

            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                String maCTHD = rs.getString("maChiTietHoaDon");
                String maHD = rs.getString("maHoaDon");
                String maDVT = rs.getString("maDonViTinh");
                int soLuong = rs.getInt("soLuong");
                double donGia = rs.getDouble("donGia");
                double giamGia = rs.getDouble("giamGia");
                double thanhTien = rs.getDouble("thanhTien");

                HoaDon hd = HoaDonDAO.getHoaDonTheoMaHD(maHD);
                DonViTinh dvt = DonViTinhDAO.getDonViTinhTheoMaDVT(maDVT);

                cthd = new ChiTietHoaDon(maCTHD, hd, dvt, soLuong, donGia, giamGia, thanhTien);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cthd;
    }
    
    public static ChiTietHoaDon getChiTietHoaDonMoiNhatTrongNgay() {
        ChiTietHoaDon cthd = null;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT TOP 1 *\n" +
                            "FROM ChiTietHoaDon cthd \n" +
                            "JOIN HoaDon hd ON hd.maHoaDon = cthd.maHoaDon\n" +
                            "WHERE CAST(hd.ngayLapHoaDon AS DATE) = CAST(GETDATE() AS DATE)\n" +
                            "ORDER BY maChiTietHoaDon DESC";

            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                String maCTHD = rs.getString("maChiTietHoaDon");
                String maHD = rs.getString("maHoaDon");
                String maDVT = rs.getString("maDonViTinh");
                int soLuong = rs.getInt("soLuong");
                double donGia = rs.getDouble("donGia");
                double giamGia = rs.getDouble("giamGia");
                double thanhTien = rs.getDouble("thanhTien");

                HoaDon hd = HoaDonDAO.getHoaDonTheoMaHD(maHD);
                DonViTinh dvt = DonViTinhDAO.getDonViTinhTheoMaDVT(maDVT);

                cthd = new ChiTietHoaDon(maCTHD, hd, dvt, soLuong, donGia, giamGia, thanhTien);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cthd;
    }
    
    public static boolean insertChiTietHoaDon(ChiTietHoaDon cthd) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();

            String sql = "INSERT INTO ChiTietHoaDon (maChiTietHoaDon, maHoaDon, maDonViTinh, soLuong, donGia, giamGia) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, cthd.getMaChiTietHoaDon());
            ps.setString(2, cthd.getHoaDon().getMaHoaDon());
            ps.setString(3, cthd.getDonViTinh().getMaDonViTinh());
            ps.setInt(4, cthd.getSoLuong());
            ps.setDouble(5, cthd.getDonGia());

            ps.setDouble(6, cthd.getGiamGia() / 100);

            n = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    private static ChiTietHoaDon taoDoiTuongChiTietHoaDon(ResultSet rs, HoaDon hoaDon) throws SQLException {
        String maCTHD = rs.getString("maChiTietHoaDon");
        int soLuong = rs.getInt("soLuong");
        double donGia = rs.getDouble("donGia");
        double giamGia = rs.getDouble("giamGia");
        double thanhTien = rs.getDouble("thanhTien");

        SanPham sp = SanPhamDAO.timSPTheoMa(rs.getString("maSP"));

        DonViTinh dvt = DonViTinhDAO.getDonViTinhTheoMaDVT(rs.getString("maDonViTinh"));

        dvt.setSanPham(sp);

        return new ChiTietHoaDon(maCTHD, hoaDon, dvt, soLuong, donGia, giamGia, thanhTien);
    }

    public static ArrayList<ChiTietHoaDon> getChiTietHoaDonTheoMaHD(HoaDon hoaDon) {
        ArrayList<ChiTietHoaDon> dsCTHD = new ArrayList<>();
        
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT cthd.*, dvt.maSP, dvt.tenDonVi, sp.ten "
                    + "FROM ChiTietHoaDon cthd "
                    + "JOIN DonViTinh dvt ON cthd.maDonViTinh = dvt.maDonViTinh "
                    + "JOIN SanPham sp ON dvt.maSP = sp.maSP "
                    + "WHERE cthd.maHoaDon = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, hoaDon.getMaHoaDon());

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsCTHD.add(taoDoiTuongChiTietHoaDon(rs, hoaDon));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return dsCTHD;
    }

    
    public static ChiTietHoaDon getChiTietHoaDonTheoMaCTHD(String maCTHD) {
        HoaDon hd = null;

        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT maHoaDon FROM ChiTietHoaDon WHERE maChiTietHoaDon = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maCTHD);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                hd = HoaDonDAO.getHoaDonTheoMaHD(rs.getString("maHoaDon"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<ChiTietHoaDon> dsCTHD = getChiTietHoaDonTheoMaHD(hd);

        ChiTietHoaDon cthd = null;

        for (ChiTietHoaDon chiTietHoaDon : dsCTHD) {
            if (chiTietHoaDon.getMaChiTietHoaDon().equals(maCTHD)) {
                cthd = chiTietHoaDon;
                break;
            }
        }

        return cthd;
    }

    public static ArrayList<ChiTietHoaDon> getChiTietHoaDonDaTruPTHTheoMaHD(HoaDon hoaDon) {
        ArrayList<ChiTietHoaDon> dsCTHD = new ArrayList<>();
        
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();

            String sql = "select	hd.maHoaDon, cthd.maChiTietHoaDon, cthd.soLuong as soLuongGoc, sum(ctpth.soLuong) as soLuongDaTraCuaCacPhieuTraHang, cthd.soLuong - isnull(sum(ctpth.soLuong),0) as soLuongDaTru, cthd.donGia, cthd.giamGia, (cthd.donGia-cthd.giamGia) * (cthd.soLuong - isnull(sum(ctpth.soLuong),0)) as thanhTienDaTru, dvt.maSP, dvt.maDonViTinh from HoaDon hd join ChiTietHoaDon cthd on hd.maHoaDon = cthd.maHoaDon full outer join ChiTietPhieuTraHang ctpth on cthd.maChiTietHoaDon = ctpth.maChiTietHoaDon join DonViTinh dvt on dvt.maDonViTinh = Cthd.maDonViTinh where  hd.maHoaDon = ? group by cthd.maChiTietHoaDon,cthd.soLuong ,cthd.donGia,cthd.giamGia,dvt.maSP,dvt.maDonViTinh, hd.maHoaDon order by hd.maHoaDon";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, hoaDon.getMaHoaDon());

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsCTHD.add(taoDoiTuongChiTietHoaDonDaTruPTH(rs, hoaDon));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return dsCTHD;
    }

    private static ChiTietHoaDon taoDoiTuongChiTietHoaDonDaTruPTH(ResultSet rs, HoaDon hoaDon) throws SQLException {
        String maCTHD = rs.getString("maChiTietHoaDon");
        int soLuong = rs.getInt("soLuongDaTru");
        double donGia = rs.getDouble("donGia");
        double giamGia = rs.getDouble("giamGia");
        double thanhTien = rs.getDouble("thanhTienDaTru");

        SanPham sp = SanPhamDAO.timSPTheoMa(rs.getString("maSP"));
        DonViTinh dvt = DonViTinhDAO.getDonViTinhTheoMaDVT(rs.getString("maDonViTinh"));
        dvt.setSanPham(sp);

        return new ChiTietHoaDon(maCTHD, hoaDon, dvt, soLuong, donGia, giamGia, thanhTien);    
    }

    public static ChiTietHoaDon getChiTietHoaDonDaTungTraRoiTheoMaCTHD(String maCTHD) {
        HoaDon hd = null;

        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT maHoaDon FROM ChiTietHoaDon WHERE maChiTietHoaDon = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maCTHD);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                hd = HoaDonDAO.getHoaDonTheoMaHD(rs.getString("maHoaDon"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<ChiTietHoaDon> dsCTHD = getChiTietHoaDonDaTruPTHTheoMaHD(hd);

        ChiTietHoaDon cthd = null;

        for (ChiTietHoaDon chiTietHoaDon : dsCTHD) {
            if (chiTietHoaDon.getMaChiTietHoaDon().equals(maCTHD)) {
                cthd = chiTietHoaDon;
                break;
            }
        }

        return cthd;
    }
}
