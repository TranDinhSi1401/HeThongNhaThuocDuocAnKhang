package hethongnhathuocduocankhang.dao;


import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.DonViTinh;
import hethongnhathuocduocankhang.entity.LoSanPham;
import hethongnhathuocduocankhang.entity.NhaCungCap;
import hethongnhathuocduocankhang.entity.PhieuNhap;
import hethongnhathuocduocankhang.entity.SanPham;
import hethongnhathuocduocankhang.entity.TaiKhoan;
import hethongnhathuocduocankhang.gui.GiaoDienChinhGUI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

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
        }
        return rows > 0;
    }
    public static ArrayList<LoSanPham> dsLoSanPham(){
        ArrayList<LoSanPham> ds = new ArrayList<>();
        String sql = "Select * from LoSanPham";
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            try(ResultSet rs = st.executeQuery()){
                while(rs.next()){
                    String malo = rs.getString(1);
                    String sanPham = rs.getString(2);
                    int sl = rs.getInt(3);
                    LocalDate ngaySX = rs.getDate(4).toLocalDate();
                    LocalDate ngayHH = rs.getDate(5).toLocalDate();
                    boolean daHuy = rs.getBoolean(6);
                    LoSanPham lo = new LoSanPham(malo, new SanPham(sanPham), sl, ngaySX, ngayHH, daHuy);
                    ds.add(lo);
                }
            }
        } catch (SQLException sQLException) {
        }
        return ds;
    }
    public static LoSanPham timLoSanPham(String maLo){
        LoSanPham lo=null;
        String sql = "Select * from LoSanPham where maLoSanPham = ?";
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, maLo);
            try(ResultSet rs = st.executeQuery()){
                while (rs.next()){
                    String maLoSP = rs.getString(1);
                    String maSP = rs.getString(2);
                    int sl = rs.getInt(3);
                    LocalDate ngaySX = rs.getDate(4).toLocalDate();
                    LocalDate ngayHH = rs.getDate(5).toLocalDate();
                    boolean daHuy = rs.getBoolean(6);
                    lo = new LoSanPham(maLoSP, new SanPham(maSP), sl, ngaySX, ngayHH, daHuy);
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
    public static boolean themLoSanPham(LoSanPham lo){
        int n=0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "Insert LoSanPham (maLoSanPham, maSP, soLuong, ngaySanXuat, ngayHetHan, daHuy) "
                    + "values(?, ?, ?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, lo.getMaLoSanPham());
            st.setString(2, lo.getSanPham().getMaSP());
            st.setInt(3, lo.getSoLuong());
            LocalDate ngaySX = lo.getNgaySanXuat();
            LocalDate ngayHH = lo.getNgayHetHan();
            java.sql.Date sx = java.sql.Date.valueOf(ngaySX);
            java.sql.Date hh = java.sql.Date.valueOf(ngayHH);
            st.setDate(4, sx);
            st.setDate(5, hh);
            st.setBoolean(6, lo.isDaHuy());
            n = st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n>0;
    }
    public static boolean themChiTietPhieuNhap(LoSanPham lo, PhieuNhap pn, double gia, double thanhTien, String ghiChu){
        int n=0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "Insert ChiTietPhieuNhap (maPhieuNhap, maLoSanPham, soLuong, donGia, thanhTien, ghiChu) "
                    + "values(?, ?, ?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, pn.getMaPhieuNhap());
            st.setString(2, lo.getMaLoSanPham());
            st.setInt(3, lo.getSoLuong());
            st.setDouble(4, gia);
            st.setDouble(5, thanhTien);
            st.setString(6, ghiChu);
            n = st.executeUpdate();
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return n>0;
    }                                                               /// mã nhà cung cấp ứng với mỗi sản phẩm thì nó phải nằm trong phần
                                                                            /// chi tiết phiếu nhập 

    public static boolean themPhieuNhap(NhaCungCap ncc, double tongTien, String ghiChu){
        int n=0;
        try {
            TaiKhoan tk = GiaoDienChinhGUI.getTk();
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "Insert PhieuNhap (maPhieuNhap, ngayTao, maNV, maNCC, tongTien, ghiChu) " // Tạo khóa chính tự tăng
                    + "values(?, ?, ?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            LocalDate ngayTao = LocalDate.now();
            java.sql.Date nt = java.sql.Date.valueOf(ngayTao);
            st.setString(1, String.format("PN-%04d", PhieuNhapDAO.getPhieuCuoiCung() +1));//mã Phiếu nhập
            st.setDate(2, nt);
            st.setString(3, tk.getNhanVien().getMaNV());
            st.setString(4, ncc.getMaNCC());
            st.setDouble(5, tongTien);
            st.setString(6, ghiChu);
            n = st.executeUpdate();
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return n>0;
    }
}
