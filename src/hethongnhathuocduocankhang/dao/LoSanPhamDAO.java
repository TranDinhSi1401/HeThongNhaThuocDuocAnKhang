package hethongnhathuocduocankhang.dao;


import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.ChiTietHoaDon;
import hethongnhathuocduocankhang.entity.DonViTinh;
import hethongnhathuocduocankhang.entity.KhachHang;
import hethongnhathuocduocankhang.entity.LoSanPham;
import hethongnhathuocduocankhang.entity.NhaCungCap;
import hethongnhathuocduocankhang.entity.SanPham;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import net.miginfocom.layout.AC;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author trand
 */
public class LoSanPhamDAO {
    public static ArrayList<LoSanPham> getLoSanPhamTheoMaSP(String maSP) {
        ArrayList<LoSanPham> dsLSP = new ArrayList<>();
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM LoSanPham WHERE maSP = ? AND ngayHetHan > GETDATE() ORDER BY NgayHetHan ASC";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maSP);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String maLSP = rs.getString(1);
                int soLuong = rs.getInt(3);
                LocalDate ngaySanXuat = rs.getDate(4).toLocalDate();
                LocalDate ngayHetHan = rs.getDate(5).toLocalDate();
                LoSanPham lsp = new LoSanPham(maLSP, new SanPham(maSP), soLuong, ngaySanXuat, ngayHetHan);
                dsLSP.add(lsp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsLSP;
    }
    
    public static boolean truSoLuong(String maLo, int soLuong) {
        int rows = 0;
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "UPDATE LoSanPham SET soLuong = soLuong - ? WHERE maLoSanPham = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, soLuong);
            ps.setString(2, maLo);
            rows = ps.executeUpdate(); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows > 0;
    }
    public static ArrayList<LoSanPham> dsLoSanPham(){
        ArrayList<LoSanPham> ds = new ArrayList<>();
        String sql = "Select s.maSP, s.ten, l.maLoSanPham, maNCC, cd.maDonViTinh, ngaySanXuat, ngayHetHan, cd.soLuong, donGia \n" +
                    "from LoSanPham l join SanPham s on l.maSP=s.maSP \n" +
                    "join ChiTietXuatLo cl on cl.maLoSanPham=l.maLoSanPham\n" +
                    "join ChiTietHoaDon cd on cd.maChiTietHoaDon=cl.maChiTietHoaDon\n" +
                    "join SanPhamCungCap sc on sc.maSP = s.maSP";
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                    String maSP = rs.getString(1);
                    String ten = rs.getString(2);
                    String maLoSP = rs.getString(3);
                    String nhaCC = rs.getString(4);
                    String maDonVi = rs.getString(5);                    
                    LocalDate ngaySX = rs.getDate(6).toLocalDate();
                    LocalDate ngayHH = rs.getDate(7).toLocalDate();
                    int sl = rs.getInt(8);
                    double gia = Double.parseDouble(rs.getString(9));
                    NhaCungCap ncc = new NhaCungCap(nhaCC);
                    ChiTietHoaDon cthd = new ChiTietHoaDon(gia);
                    DonViTinh donVi = new DonViTinh(maDonVi);
                    LoSanPham lo = new LoSanPham(maLoSP, new SanPham(maSP, ten), sl, ngaySX, ngayHH, ncc, cthd, donVi);    
                ds.add(lo);
            }
        } catch (SQLException sQLException) {
        }
        return ds;
    }
    public static LoSanPham timLoSanPham(String maLo){
        LoSanPham lo = new LoSanPham();
        String sql = "Select s.maSP, s.ten, l.maLoSanPham, maNCC, cd.maDonViTinh, ngaySanXuat, ngayHetHan, cd.soLuong, donGia \n" +
                    "from LoSanPham l join SanPham s on l.maSP=s.maSP \n" +
                    "join ChiTietXuatLo cl on cl.maLoSanPham=l.maLoSanPham\n" +
                    "join ChiTietHoaDon cd on cd.maChiTietHoaDon=cl.maChiTietHoaDon\n" +
                    "join SanPhamCungCap sc on sc.maSP = s.maSP\n" +
                    "where l.maLoSanPham = ?";
        lo = null;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, maLo);
            try(ResultSet rs = st.executeQuery()){
                while (rs.next()){
                    String maSP = rs.getString(1);
                    String ten = rs.getString(2);
                    String maLoSP = rs.getString(3);
                    String nhaCC = rs.getString(4);
                    String maDonVi = rs.getString(5);                    
                    LocalDate ngaySX = rs.getDate(6).toLocalDate();
                    LocalDate ngayHH = rs.getDate(7).toLocalDate();
                    int sl = rs.getInt(8);
                    double gia = Double.parseDouble(rs.getString(9));
                    NhaCungCap ncc = new NhaCungCap(nhaCC);
                    ChiTietHoaDon cthd = new ChiTietHoaDon(gia);
                    DonViTinh donVi = new DonViTinh(maDonVi);
                    lo = new LoSanPham(maLoSP, new SanPham(maSP, ten), sl, ngaySX, ngayHH, ncc, cthd, donVi);
                }
            }
        } catch (SQLException sQLException) {
        }
        return lo;
    }
    
    public ArrayList<LoSanPham> dsSanPhamCoNgay(){
        ArrayList<LoSanPham> loSP = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "Select * from LoSanPham";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                String ma = rs.getString(1);
                String maSP = rs.getString(2);
                int soLuong = rs.getInt(3);
                LocalDate ngaySX = rs.getDate(4).toLocalDate();
                LocalDate ngayHH = rs.getDate(5).toLocalDate();
                LoSanPham lo = new LoSanPham(ma, new SanPham(maSP), soLuong, ngaySX, ngayHH);
                loSP.add(lo);
            }
        } catch (SQLException sQLException) {
        }
        return loSP;
    }
    
}
