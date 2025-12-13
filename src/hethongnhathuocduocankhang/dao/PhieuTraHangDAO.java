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
    
    public static boolean themPhieuTra(PhieuTraHang pth){
        int n = 0;
        System.out.println(pth.getMaPhieuTraHang());
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "INSERT INTO PhieuTraHang VALUES (?, ?, ?, ?, ?)";
            
            PreparedStatement statement = con.prepareStatement(sql);
            
            statement.setString(1, pth.getMaPhieuTraHang());
            statement.setTimestamp(2, Timestamp.valueOf(pth.getNgayLapPhieuTraHang()) );
            statement.setString(3, pth.getNhanVien().getMaNV());
            statement.setString(4, pth.getHoaDon().getMaHoaDon());
            statement.setDouble(5, pth.getTongTienHoanTra());
            
            n = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n>0;
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
    public static int phieuTraHangMoiNhatHomNay(){
        String ma = null;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT top 1 * FROM PhieuTraHang pth WHERE CAST(pth.ngayLapPhieuTraHang AS DATE) = CAST(GETDATE() AS DATE) order by pth.ngayLapPhieuTraHang desc";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            if (rs.next()){
                ma = rs.getString(1);
                ma = ma.substring(ma.length() - 4);
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
        
        if(ma == null) return 0;
        return Integer.parseInt(ma);
    }
    
    public static ArrayList<PhieuTraHang> getAllPhieuTraHangTraCuu() {
        ArrayList<PhieuTraHang> dsPTH = new ArrayList<>();
        String sql = "SELECT pth.maPhieuTraHang, pth.ngayLapPhieuTraHang, nv.maNV, nv.hoTenDem, nv.ten, hd.maHoaDon, pth.tongTienHoanTra " +
                     "FROM PhieuTraHang pth JOIN NhanVien nv ON pth.maNV = nv.maNV " +
                     "JOIN HoaDon hd ON pth.maHoaDon = hd.maHoaDon " +
                     "ORDER BY pth.ngayLapPhieuTraHang DESC";
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    String maPTH = rs.getString("maPhieuTraHang");
                    LocalDateTime ngayLap = rs.getTimestamp("ngayLapPhieuTraHang").toLocalDateTime();
                    String maNV = rs.getString("maNV");
                    String hoTenDem = rs.getString("hoTenDem");
                    String ten = rs.getString("ten");
                    String maHD = rs.getString("maHoaDon");
                    double tongTienHoanTra = rs.getDouble("tongTienHoanTra");
                    
                    NhanVien nv = new NhanVien(maNV, hoTenDem, ten);
                    HoaDon hd = new HoaDon(maHD);
                    PhieuTraHang pth = new PhieuTraHang(maPTH, ngayLap, tongTienHoanTra, nv, hd);
                    dsPTH.add(pth);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsPTH;
    }
    
    public static PhieuTraHang getPhieuTraHangTheoMa(String ma) {
        PhieuTraHang pth = null;
        String sql = "SELECT pth.maPhieuTraHang, pth.ngayLapPhieuTraHang, nv.maNV, nv.hoTenDem, nv.ten, hd.maHoaDon, pth.tongTienHoanTra " +
                     "FROM PhieuTraHang pth JOIN NhanVien nv ON pth.maNV = nv.maNV " +
                     "JOIN HoaDon hd ON pth.maHoaDon = hd.maHoaDon " +
                     "WHERE pth.maPhieuTraHang = ?";
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, ma);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    String maPTH = rs.getString("maPhieuTraHang");
                    LocalDateTime ngayLap = rs.getTimestamp("ngayLapPhieuTraHang").toLocalDateTime();
                    String maNV = rs.getString("maNV");
                    String hoTenDem = rs.getString("hoTenDem");
                    String ten = rs.getString("ten");
                    String maHD = rs.getString("maHoaDon");
                    double tongTienHoanTra = rs.getDouble("tongTienHoanTra");
                    
                    NhanVien nv = new NhanVien(maNV, hoTenDem, ten);
                    HoaDon hd = new HoaDon(maHD);
                    pth = new PhieuTraHang(maPTH, ngayLap, tongTienHoanTra, nv, hd);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pth;
    }
    
    public static ArrayList<PhieuTraHang> timPTTheoNgayLap(LocalDate ngay) {
        ArrayList<PhieuTraHang> dsPTH = new ArrayList<>();
        String sql = "SELECT pth.maPhieuTraHang, pth.ngayLapPhieuTraHang, nv.maNV, nv.hoTenDem, nv.ten, hd.maHoaDon, pth.tongTienHoanTra " +
                     "FROM PhieuTraHang pth JOIN NhanVien nv ON pth.maNV = nv.maNV " +
                     "JOIN HoaDon hd ON pth.maHoaDon = hd.maHoaDon " +
                     "WHERE CAST(pth.ngayLapPhieuTraHang AS DATE) = ? " +
                     "ORDER BY pth.ngayLapPhieuTraHang DESC";
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setDate(1, java.sql.Date.valueOf(ngay));
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    String maPTH = rs.getString("maPhieuTraHang");
                    LocalDateTime ngayLap = rs.getTimestamp("ngayLapPhieuTraHang").toLocalDateTime();
                    String maNV = rs.getString("maNV");
                    String hoTenDem = rs.getString("hoTenDem");
                    String ten = rs.getString("ten");
                    String maHD = rs.getString("maHoaDon");
                    double tongTienHoanTra = rs.getDouble("tongTienHoanTra");
                    
                    NhanVien nv = new NhanVien(maNV, hoTenDem, ten);
                    HoaDon hd = new HoaDon(maHD);
                    PhieuTraHang pth = new PhieuTraHang(maPTH, ngayLap, tongTienHoanTra, nv, hd);
                    dsPTH.add(pth);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsPTH;
    }
    
    public static ArrayList<PhieuTraHang> timPTTheoKhoangNgay(LocalDate start, LocalDate end) {
        ArrayList<PhieuTraHang> dsPTH = new ArrayList<>();
        String sql = "SELECT pth.maPhieuTraHang, pth.ngayLapPhieuTraHang, nv.maNV, nv.hoTenDem, nv.ten, hd.maHoaDon, pth.tongTienHoanTra " +
                     "FROM PhieuTraHang pth JOIN NhanVien nv ON pth.maNV = nv.maNV " +
                     "JOIN HoaDon hd ON pth.maHoaDon = hd.maHoaDon " +
                     "WHERE CAST(pth.ngayLapPhieuTraHang AS DATE) BETWEEN ? AND ? " +
                     "ORDER BY pth.ngayLapPhieuTraHang DESC";
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setDate(1, java.sql.Date.valueOf(start));
            st.setDate(2, java.sql.Date.valueOf(end));
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    String maPTH = rs.getString("maPhieuTraHang");
                    LocalDateTime ngayLap = rs.getTimestamp("ngayLapPhieuTraHang").toLocalDateTime();
                    String maNV = rs.getString("maNV");
                    String hoTenDem = rs.getString("hoTenDem");
                    String ten = rs.getString("ten");
                    String maHD = rs.getString("maHoaDon");
                    double tongTienHoanTra = rs.getDouble("tongTienHoanTra");
                    
                    NhanVien nv = new NhanVien(maNV, hoTenDem, ten);
                    HoaDon hd = new HoaDon(maHD);
                    PhieuTraHang pth = new PhieuTraHang(maPTH, ngayLap, tongTienHoanTra, nv, hd);
                    dsPTH.add(pth);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsPTH;
    }
}    
