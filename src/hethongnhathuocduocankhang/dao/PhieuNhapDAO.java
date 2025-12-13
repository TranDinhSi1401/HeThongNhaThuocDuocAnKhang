/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.NhaCungCap;
import hethongnhathuocduocankhang.entity.NhanVien;
import hethongnhathuocduocankhang.entity.PhieuNhap;
import hethongnhathuocduocankhang.entity.TaiKhoan;
import hethongnhathuocduocankhang.gui.GiaoDienChinhGUI;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class PhieuNhapDAO {
    public static int getPhieuCuoiCung(){
        String sql = "Select Max(maPhieuNhap) from PhieuNhap";
        int ma = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            try(ResultSet rs = st.executeQuery()){
                if (rs.next()){
                    String maPhieu = rs.getString(1);
                    if(maPhieu !=null && maPhieu.matches("^PN-\\d{4}$")){
                        String maSo = maPhieu.substring(3);
                        ma = Integer.parseInt(maSo);
                    }
                }
            }
        } catch (SQLException sQLException) {
        }
        return ma;
    }
        public static boolean themPhieuNhap(LocalDate ngayTao, double tongTien, String ghiChu){
            int n=0;
            try {
                TaiKhoan tk = GiaoDienChinhGUI.getTk();
                ConnectDB.getInstance().connect();
                Connection con = ConnectDB.getConnection();
                String sql = "Insert PhieuNhap (maPhieuNhap, ngayTao, maNV, tongTien, ghiChu) " // Tạo khóa chính tự tăng
                        + "values(?, ?, ?, ?, ?)";
                PreparedStatement st = con.prepareStatement(sql);
                ngayTao = LocalDate.now();
                java.sql.Date nt = java.sql.Date.valueOf(ngayTao);
                st.setString(1, String.format("PN-%04d", PhieuNhapDAO.getPhieuCuoiCung() +1));//mã Phiếu nhập
                st.setDate(2, nt);
                st.setString(3, tk.getNhanVien().getMaNV());
                st.setDouble(4, tongTien);
                st.setString(5, ghiChu);
                n = st.executeUpdate();
            } catch (SQLException s) {
                s.printStackTrace();
            }
            return n>0;
        }
        public static PhieuNhap getPhieuNhapMoiNhat(){
            PhieuNhap pn = new PhieuNhap();
            String sql = "Select top 1 * from PhieuNhap order by maPhieuNhap desc";
            try {
                ConnectDB.getInstance().connect();
                Connection con = ConnectDB.getConnection();
                PreparedStatement st = con.prepareStatement(sql);
                try(ResultSet rs = st.executeQuery()){
                    if(rs.next()){
                        String ma= rs.getString(1);
                        LocalDate ngayTao = rs.getDate(2).toLocalDate();
                        String maNV = rs.getString(3);
                        double tongTien = rs.getDouble(4);
                        String ghiChu = rs.getString(5);
                        
                        pn = new PhieuNhap(ma, ngayTao, new NhanVien(maNV), tongTien, ghiChu);
                    }
                }
            } catch (SQLException s) {
                s.printStackTrace();
            }
            return pn;
        }
    public static boolean xoaPhieuNhap(PhieuNhap pn){
        int n=0;
        String sql ="Delete PhieuNhap where maphieuNhap = ?";
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement st= con.prepareStatement(sql);
            st.setString(1, pn.getMaPhieuNhap());
            n=st.executeUpdate();
            
        } catch (SQLException s) {
            s.printStackTrace();
        }return n>0;
        
    }
    public static ArrayList<PhieuNhap> getAllPhieuNhap() {
        ArrayList<PhieuNhap> dsPN = new ArrayList<>();
        String sql = "SELECT pn.maPhieuNhap, pn.ngayTao, nv.maNV, nv.hoTenDem, nv.ten, pn.tongTien, pn.ghiChu " +
                     "FROM PhieuNhap pn JOIN NhanVien nv ON pn.maNV = nv.maNV " +
                     "ORDER BY pn.ngayTao DESC";
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    String maPN = rs.getString("maPhieuNhap");
                    LocalDate ngayTao = rs.getDate("ngayTao").toLocalDate();
                    String maNV = rs.getString("maNV");
                    String hoTenDem = rs.getString("hoTenDem");
                    String ten = rs.getString("ten");
                    double tongTien = rs.getDouble("tongTien");
                    String ghiChu = rs.getString("ghiChu");
                    
                    NhanVien nv = new NhanVien(maNV, hoTenDem, ten);
                    PhieuNhap pn = new PhieuNhap(maPN, ngayTao, nv, tongTien, ghiChu);
                    dsPN.add(pn);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsPN;
    }
    
    public static PhieuNhap getPhieuNhapTheoMa(String ma) {
        PhieuNhap pn = null;
        String sql = "SELECT pn.maPhieuNhap, pn.ngayTao, nv.maNV, nv.hoTenDem, nv.ten, pn.tongTien, pn.ghiChu " +
                     "FROM PhieuNhap pn JOIN NhanVien nv ON pn.maNV = nv.maNV " +
                     "WHERE pn.maPhieuNhap = ?";
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, ma);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    String maPN = rs.getString("maPhieuNhap");
                    LocalDate ngayTao = rs.getDate("ngayTao").toLocalDate();
                    String maNV = rs.getString("maNV");
                    String hoTenDem = rs.getString("hoTenDem");
                    String ten = rs.getString("ten");
                    double tongTien = rs.getDouble("tongTien");
                    String ghiChu = rs.getString("ghiChu");
                    
                    NhanVien nv = new NhanVien(maNV, hoTenDem, ten);
                    pn = new PhieuNhap(maPN, ngayTao, nv, tongTien, ghiChu);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pn;
    }
    
    public static ArrayList<PhieuNhap> timPNTheoNgayLap(LocalDate ngay) {
        ArrayList<PhieuNhap> dsPN = new ArrayList<>();
        String sql = "SELECT pn.maPhieuNhap, pn.ngayTao, nv.maNV, nv.hoTenDem, nv.ten, pn.tongTien, pn.ghiChu " +
                     "FROM PhieuNhap pn JOIN NhanVien nv ON pn.maNV = nv.maNV " +
                     "WHERE pn.ngayTao = ? " +
                     "ORDER BY pn.ngayTao DESC";
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setDate(1, java.sql.Date.valueOf(ngay));
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    String maPN = rs.getString("maPhieuNhap");
                    LocalDate ngayTao = rs.getDate("ngayTao").toLocalDate();
                    String maNV = rs.getString("maNV");
                    String hoTenDem = rs.getString("hoTenDem");
                    String ten = rs.getString("ten");
                    double tongTien = rs.getDouble("tongTien");
                    String ghiChu = rs.getString("ghiChu");
                    
                    NhanVien nv = new NhanVien(maNV, hoTenDem, ten);
                    PhieuNhap pn = new PhieuNhap(maPN, ngayTao, nv, tongTien, ghiChu);
                    dsPN.add(pn);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsPN;
    }
    
    public static ArrayList<PhieuNhap> timPNTheoKhoangNgay(LocalDate start, LocalDate end) {
        ArrayList<PhieuNhap> dsPN = new ArrayList<>();
        String sql = "SELECT pn.maPhieuNhap, pn.ngayTao, nv.maNV, nv.hoTenDem, nv.ten, pn.tongTien, pn.ghiChu " +
                     "FROM PhieuNhap pn JOIN NhanVien nv ON pn.maNV = nv.maNV " +
                     "WHERE pn.ngayTao BETWEEN ? AND ? " +
                     "ORDER BY pn.ngayTao DESC";
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setDate(1, java.sql.Date.valueOf(start));
            st.setDate(2, java.sql.Date.valueOf(end));
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    String maPN = rs.getString("maPhieuNhap");
                    LocalDate ngayTao = rs.getDate("ngayTao").toLocalDate();
                    String maNV = rs.getString("maNV");
                    String hoTenDem = rs.getString("hoTenDem");
                    String ten = rs.getString("ten");
                    double tongTien = rs.getDouble("tongTien");
                    String ghiChu = rs.getString("ghiChu");
                    
                    NhanVien nv = new NhanVien(maNV, hoTenDem, ten);
                    PhieuNhap pn = new PhieuNhap(maPN, ngayTao, nv, tongTien, ghiChu);
                    dsPN.add(pn);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsPN;
    }
}
    