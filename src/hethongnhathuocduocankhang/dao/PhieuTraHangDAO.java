/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.ChiTietHoaDon;
import hethongnhathuocduocankhang.entity.ChiTietPhieuTraHang;
import hethongnhathuocduocankhang.entity.DonViTinh;
import hethongnhathuocduocankhang.entity.HoaDon;
import hethongnhathuocduocankhang.entity.NhanVien;
import hethongnhathuocduocankhang.entity.PhieuTraHang;
import hethongnhathuocduocankhang.entity.SanPham;
import hethongnhathuocduocankhang.entity.TinhTrangSanPhamEnum;
import hethongnhathuocduocankhang.entity.TruongHopDoiTraEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PhieuTraHangDAO {

    private static PhieuTraHang taoDoiTuongPhieuTraHang(ResultSet rs) throws SQLException {
        String maPTH = rs.getString("maPhieuTraHang");
        LocalDateTime ngayLap = rs.getTimestamp("ngayLapPhieuTraHang").toLocalDateTime();
        String maNV = rs.getString("maNV");
        String maHD = rs.getString("maHoaDon");
        double tongTienHoanTra = rs.getDouble("tongTienHoanTra");

        NhanVien nv = NhanVienDAO.timNVTheoMa(maNV);
        HoaDon hd = HoaDonDAO.getHoaDonTheoMaHD(maHD);

        if (nv == null) nv = new NhanVien(maNV);
        if (hd == null) hd = new HoaDon(maHD);

        return new PhieuTraHang(maPTH, ngayLap, tongTienHoanTra, nv, hd);
    }

    public static ArrayList<PhieuTraHang> getAllPhieuTraHang() {
        ArrayList<PhieuTraHang> dsPTH = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM PhieuTraHang";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                dsPTH.add(taoDoiTuongPhieuTraHang(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsPTH;
    }
    
    public static PhieuTraHang timPTHTheoMa(String ma) {
        PhieuTraHang pth = null;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT * FROM PhieuTraHang WHERE maPhieuTraHang = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, ma);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                pth = taoDoiTuongPhieuTraHang(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pth;
    }

    public static ArrayList<PhieuTraHang> timPTHTheoMaNV(String maNV) {
        ArrayList<PhieuTraHang> dsPTH = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT * FROM PhieuTraHang WHERE maNV = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, maNV);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsPTH.add(taoDoiTuongPhieuTraHang(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsPTH;
    }

    public static ArrayList<PhieuTraHang> timPTHTheoMaHD(String maHD) {
        ArrayList<PhieuTraHang> dsPTH = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT * FROM PhieuTraHang WHERE maHoaDon = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, maHD);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsPTH.add(taoDoiTuongPhieuTraHang(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsPTH;
    }
    
    public static ArrayList<PhieuTraHang> timPTHTheoNgayLap(LocalDate date) {
        ArrayList<PhieuTraHang> dsPTH = new ArrayList<>();
        
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT * FROM PhieuTraHang WHERE CONVERT(DATE, ngayLapPhieuTraHang) = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setDate(1, Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsPTH.add(taoDoiTuongPhieuTraHang(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsPTH;
    }
    
    public static ArrayList<ChiTietPhieuTraHang> getChiTietTheoMaPTH(PhieuTraHang phieuTraHang) {
        ArrayList<ChiTietPhieuTraHang> dsChiTiet = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            
            String sql = "SELECT ct_pth.*, sp.maSP, sp.ten, dvt.tenDonVi, dvt.maDonViTinh "
                       + "FROM ChiTietPhieuTraHang ct_pth "
                       + "JOIN ChiTietHoaDon cthd ON ct_pth.maChiTietHoaDon = cthd.maChiTietHoaDon "
                       + "JOIN DonViTinh dvt ON cthd.maDonViTinh = dvt.maDonViTinh "
                       + "JOIN SanPham sp ON dvt.maSP = sp.maSP "
                       + "WHERE ct_pth.maPhieuTraHang = ?";
                       
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, phieuTraHang.getMaPhieuTraHang());
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                SanPham sp = new SanPham();
                sp.setMaSP(rs.getString("maSP"));
                sp.setTen(rs.getString("ten"));

                DonViTinh dvt = new DonViTinh();
                dvt.setMaDonViTinh(rs.getString("maDonViTinh"));
                dvt.setTenDonVi(rs.getString("tenDonVi"));
                dvt.setSanPham(sp);
                
                ChiTietHoaDon cthd = new ChiTietHoaDon(rs.getString("maChiTietHoaDon"));
                cthd.setDonViTinh(dvt);

                int soLuong = rs.getInt("soLuong");
                TruongHopDoiTraEnum truongHop = TruongHopDoiTraEnum.valueOf(rs.getString("truongHopDoiTra"));
                TinhTrangSanPhamEnum tinhTrang = TinhTrangSanPhamEnum.valueOf(rs.getString("tinhTrangSanPham"));
                String giaTriHoanTra = rs.getString("giaTriHoanTra");
                double thanhTienHoanTra = rs.getDouble("thanhTienHoanTra");

                ChiTietPhieuTraHang ct_pth = new ChiTietPhieuTraHang(
                    phieuTraHang,
                    cthd,
                    soLuong,
                    truongHop,
                    tinhTrang,
                    giaTriHoanTra,
                    thanhTienHoanTra
                );
                
                dsChiTiet.add(ct_pth);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsChiTiet;
    }
}