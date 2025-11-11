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

/**
 * @author GIGABYTE
 */
public class ChiTietHoaDonDAO {

    public static ChiTietHoaDon getChiTietHoaDonMoiNhat() {
    ChiTietHoaDon cthd = null;
    try {
        ConnectDB.getInstance().connect();
        Connection con = ConnectDB.getConnection();
        String sql = "SELECT * FROM ChiTietHoaDon " +
                     "WHERE maChiTietHoaDon = (SELECT MAX(maChiTietHoaDon) FROM ChiTietHoaDon)";
        
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        
        if(rs.next()) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsCTHD;
    }
}