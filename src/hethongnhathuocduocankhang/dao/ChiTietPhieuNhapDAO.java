/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.ChiTietPhieuNhap;
import hethongnhathuocduocankhang.entity.LoSanPham;
import hethongnhathuocduocankhang.entity.NhaCungCap;
import hethongnhathuocduocankhang.entity.PhieuNhap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;


/**
 *
 * @author admin
 */
public class ChiTietPhieuNhapDAO {
    public static boolean themChiTietPhieuNhap(LoSanPham lo, PhieuNhap pn, NhaCungCap ncc, double gia, double thanhTien, int slDat, String ghiChu){
        int n=0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "Insert ChiTietPhieuNhap (maPhieuNhap, maLoSanPham, maNCC, soLuong, donGia, thanhTien, soLuongYeuCau, ghiChu) "
                    + "values(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, pn.getMaPhieuNhap());
            st.setString(2, lo.getMaLoSanPham());
            st.setString(3, ncc.getMaNCC());
            st.setInt(4, lo.getSoLuong());
            st.setDouble(5, gia);
            st.setDouble(6, thanhTien);
            st.setInt(7, slDat);
            st.setString(8, ghiChu);
            n = st.executeUpdate();
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return n>0;
    }      
    public static ChiTietPhieuNhap getChiTietPhieuNhap (String maLo){
        String sql = "Select * from ChiTietPhieuNhap where maLoSanPham = ?";
        ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, maLo);
            try(ResultSet rs = st.executeQuery()){
                if(rs.next()){
                    String maPNhap = rs.getString(1);
                    String maLoSP = rs.getString(2);
                    String ncc = rs.getString(3);
                    int sl = rs.getInt(4);
                    int slDat = rs.getInt(5);
                    double gia = rs.getDouble(6);
                    double thanhTien = rs.getDouble(7);
                    String ghiChu = rs.getString(8);
                    ctpn = new ChiTietPhieuNhap(new PhieuNhap(maPNhap), new LoSanPham(maLoSP), new NhaCungCap(ncc), sl, gia, thanhTien, slDat, ghiChu);
                }
            }
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return ctpn;
    }
        
    public static ArrayList<ChiTietPhieuNhap> getChiTietByMaPhieuNhap(String maPhieuNhap) {
        ArrayList<ChiTietPhieuNhap> dsCT = new ArrayList<>();
        String sql = "SELECT ct.maPhieuNhap, ct.maLoSanPham, ct.maNCC, ct.soLuong, ct.soLuongYeuCau, ct.donGia, ct.thanhTien, ct.ghiChu, " +
                     "lo.maLo, sp.ten, ncc.tenNCC " +
                     "FROM ChiTietPhieuNhap ct " +
                     "JOIN LoSanPham lo ON ct.maLoSanPham = lo.maLoSanPham " +
                     "JOIN SanPham sp ON lo.maSP = sp.maSP " +
                     "JOIN NhaCungCap ncc ON ct.maNCC = ncc.maNCC " +
                     "WHERE ct.maPhieuNhap = ?";
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, maPhieuNhap);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    String maPN = rs.getString("maPhieuNhap");
                    String maLoSP = rs.getString("maLoSanPham");
                    String maNCC = rs.getString("maNCC");
                    int soLuong = rs.getInt("soLuong");
                    int soLuongYeuCau = rs.getInt("soLuongYeuCau");
                    double donGia = rs.getDouble("donGia");
                    double thanhTien = rs.getDouble("thanhTien");
                    String ghiChu = rs.getString("ghiChu");
                    
                    PhieuNhap pn = new PhieuNhap(maPN);
                    LoSanPham lo = new LoSanPham(maLoSP);
                    lo.setSanPham(new hethongnhathuocduocankhang.entity.SanPham(rs.getString("maLo"), rs.getString("ten"))); // Assuming SanPham constructor
                    NhaCungCap ncc = new NhaCungCap(maNCC, rs.getString("tenNCC"));
                    
                    ChiTietPhieuNhap ct = new ChiTietPhieuNhap(pn, lo, ncc, soLuong, donGia, thanhTien, soLuongYeuCau, ghiChu);
                    dsCT.add(ct);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsCT;
    }
}

