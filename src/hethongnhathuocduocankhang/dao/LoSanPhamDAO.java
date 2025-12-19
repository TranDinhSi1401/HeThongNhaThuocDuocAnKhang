package hethongnhathuocduocankhang.dao;


import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.LoSanPham;
import hethongnhathuocduocankhang.entity.SanPham;
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
    public static boolean congSoLuong(String maLo, int soLuong, int heSoQuyDoi) {
        int rows = 0;
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "UPDATE LoSanPham SET soLuong = soLuong + ? WHERE maLoSanPham = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, soLuong * heSoQuyDoi);
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

    public static LoSanPham getLoSanPhamTheoMaCTHD(String maChiTietHoaDon) {
                LoSanPham lSP = null;
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "select lsp.* from LoSanPham lsp join ChiTietXuatLo ctxl on lsp.maLoSanPham = ctxl.maLoSanPham join ChiTietHoaDon cthd on cthd.maChiTietHoaDon = ctxl.maChiTietHoaDon where cthd.maChiTietHoaDon = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maChiTietHoaDon);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                String maLSP = rs.getString(1);
                int soLuong = rs.getInt(3);
                LocalDate ngaySanXuat = rs.getDate(4).toLocalDate();
                LocalDate ngayHetHan = rs.getDate(5).toLocalDate();
                LoSanPham lsp = new LoSanPham(maLSP, new SanPham(), soLuong, ngaySanXuat, ngayHetHan);
                lSP = lsp;
            }
        } catch (SQLException e) {
        }
        return lSP;
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
                                                             /// mã nhà cung cấp ứng với mỗi sản phẩm thì nó phải nằm trong phần
                                                                            /// chi tiết phiếu nhập 


    public static ArrayList<LoSanPham> getLoSPTheoMaNhaCungCap (String ma){
        String sql ="""
                    select * from LoSanPham l join ChiTietPhieuNhap pn on l.maLoSanPham=pn.maLoSanPham 
                    where maNCC = ?""";
        ArrayList<LoSanPham> dsLo = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, ma);
            try(ResultSet rs = st.executeQuery()){
                if(rs.next()){
                    String maLo = rs.getString(1);
                    String maSP = rs.getString(2);
                    int soLuong = rs.getInt(3);
                    LocalDate ngaySX = rs.getDate(4).toLocalDate();
                    LocalDate ngayHH = rs.getDate(5).toLocalDate();
                    boolean tinhTrang = rs.getBoolean(6);
                    LoSanPham lo = new LoSanPham(maLo, new SanPham(maSP), soLuong, ngaySX, ngayHH, tinhTrang);
                    dsLo.add(lo);
                }
            }
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return dsLo;
    }
    public static boolean capNhatSoLuongLo(LoSanPham lo, int slDat){
        String sql = "Update LoSanPham Set soLuong = ? where maLoSanPham = ?";
        int n=0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            int soLuongMoi =  lo.getSoLuong()+slDat;
            st.setString(2, lo.getMaLoSanPham());
            st.setInt(1, soLuongMoi);
            n=st.executeUpdate();
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return n>0;
    }
    public static boolean huyLoSanPham(LoSanPham lo){
        int n =0;
        String sql = "Update LoSanPham Set daHuy = ? where maLoSanPham =?" ;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setBoolean(1, true);
            st.setString(2, lo.getMaLoSanPham());
            n =st.executeUpdate();
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return n>0;
    }
    public static ArrayList<LoSanPham> dsLoTheoMaSanPham(String maSP){
        ArrayList<LoSanPham> dsLo = new ArrayList<>();
        String sql = "Select * from LoSanPham where maSP = ?";
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, maSP);
            try(ResultSet rs = st.executeQuery()){
                while(rs.next()){
                    String maLo = rs.getString(1);
                    String maSPham = rs.getString(2);
                    int soLuong = rs.getInt(3);
                    LocalDate ngaySX = rs.getDate(4).toLocalDate();
                    LocalDate ngayHH = rs.getDate(5).toLocalDate();
                    boolean tinhTrang = rs.getBoolean(6);
                    LoSanPham lo = new LoSanPham(maLo, new SanPham(maSPham), soLuong, ngaySX, ngayHH, tinhTrang);
                    dsLo.add(lo);
                }
            }
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return dsLo;
    }
    
    /**
     * Đếm số lượng lô theo 4 trạng thái:
     * - Còn hạn (daHuy = 0 AND ngayHetHan > 30 ngày)
     * - Sắp hết hạn (daHuy = 0 AND ngayHetHan >= hôm nay AND <= 30 ngày)
     * - Hết hạn (daHuy = 0 AND ngayHetHan < hôm nay)
     * - Đã hủy (daHuy = 1)
     * @return int[4] - [0]=Còn hạn, [1]=Sắp hết hạn, [2]=Hết hạn, [3]=Đã hủy
     */
    public static int[] demLoTheoTrangThai() {
        int[] counts = new int[4];
        String sql = 
            "SELECT " +
            "  SUM(CASE WHEN daHuy = 1 THEN 1 ELSE 0 END) AS DaHuy, " +
            "  SUM(CASE WHEN daHuy = 0 AND ngayHetHan < CAST(GETDATE() AS DATE) THEN 1 ELSE 0 END) AS HetHan, " +
            "  SUM(CASE WHEN daHuy = 0 AND ngayHetHan >= CAST(GETDATE() AS DATE) AND ngayHetHan <= DATEADD(DAY, 30, CAST(GETDATE() AS DATE)) THEN 1 ELSE 0 END) AS SapHetHan, " +
            "  SUM(CASE WHEN daHuy = 0 AND ngayHetHan > DATEADD(DAY, 30, CAST(GETDATE() AS DATE)) THEN 1 ELSE 0 END) AS ConHan " +
            "FROM LoSanPham";
        
        try {
            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                counts[3] = rs.getInt("DaHuy");
                counts[2] = rs.getInt("HetHan");
                counts[1] = rs.getInt("SapHetHan");
                counts[0] = rs.getInt("ConHan");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counts;
    }
}
