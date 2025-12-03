/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.NhaCungCap;
import hethongnhathuocduocankhang.entity.SanPham;
import hethongnhathuocduocankhang.entity.SanPhamCungCap;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;

/**
 *
 * @author admin
 */
public class SanPhamCungCapDAO {
    public static SanPhamCungCap getSanPhamCungCap (String masp){
        SanPhamCungCap sp = new SanPhamCungCap();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "Select * from SanPhamCungCap where maSP =?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, masp);
            try(ResultSet rs = st.executeQuery()){
                while(rs.next()){
                    String maSP = rs.getString(1);
                    String maNCC = rs.getString(2);
                    boolean trangThai = rs.getBoolean(3);
                    double giaNhap = rs.getDouble(4);
                    sp = new SanPhamCungCap(new SanPham(maSP),new NhaCungCap(maNCC), trangThai, giaNhap);
                }
            }
            
        } catch (SQLException sQLException) {
        }
        return sp;
    }

    // Thêm vào class SanPhamCungCapDAO
    public static ArrayList<SanPhamCungCap> getSanPhamCungCapTheoMaSP(String maSP) {
        ArrayList<SanPhamCungCap> dsSPCC = new ArrayList<>();
        try {
            Connection con = ConnectDB.getConnection();
            // Join bảng SanPhamCungCap với NhaCungCap để lấy tên NCC
            String sql = "SELECT spcc.maNCC, ncc.tenNCC, spcc.giaNhap, spcc.trangThaiHopTac "
                    + "FROM SanPhamCungCap spcc "
                    + "JOIN NhaCungCap ncc ON spcc.maNCC = ncc.maNCC "
                    + "WHERE spcc.maSP = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maSP);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String maNCC = rs.getString("maNCC");
                String tenNCC = rs.getString("tenNCC");
                double giaNhap = rs.getDouble("giaNhap");
                boolean trangThai = rs.getBoolean("trangThaiHopTac");

                // Tạo đối tượng NhaCungCap giả (chỉ cần mã và tên để hiển thị)
                NhaCungCap ncc = new NhaCungCap(maNCC, tenNCC, "", "", "");

                // Tạo đối tượng SanPham giả (chỉ cần mã)
                SanPham sp = new SanPham(maSP);

                SanPhamCungCap spcc = new SanPhamCungCap(sp, ncc, trangThai, giaNhap);
                dsSPCC.add(spcc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsSPCC;
    }

    // 1. HÀM THÊM SẢN PHẨM CUNG CẤP
    public static boolean themSanPhamCungCap(SanPhamCungCap spcc) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "INSERT INTO SanPhamCungCap (maSP, maNCC, trangThaiHopTac, giaNhap) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, spcc.getSanPham().getMaSP());
            stmt.setString(2, spcc.getNhaCungCap().getMaNCC());
            stmt.setBoolean(3, spcc.isTrangThaiHopTac());
            stmt.setDouble(4, spcc.getGiaNhap());

            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    // 2. HÀM XÓA SẢN PHẨM CUNG CẤP
    // Cần cả maSP và maNCC để xác định đúng record cần xóa
    public static boolean xoaSanPhamCungCap(String maSP, String maNCC) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();

            String query = "DELETE FROM SanPhamCungCap WHERE maSP = ? AND maNCC = ?";
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setString(1, maSP);
            stmt.setString(2, maNCC);

            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }
    
    public static boolean xoaHetNCCuaSP(String maSP) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();

            String query = "DELETE FROM SanPhamCungCap WHERE maSP = ?";
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setString(1, maSP);

            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    // 3. HÀM SỬA SẢN PHẨM CUNG CẤP
    // Cập nhật Giá nhập hoặc Trạng thái dựa trên cặp khóa chính (maSP, maNCC)
    public static boolean suaSanPhamCungCap(SanPhamCungCap spccNew) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String query = "UPDATE SanPhamCungCap SET trangThaiHopTac = ?, giaNhap = ? WHERE maSP = ? AND maNCC = ?";
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setBoolean(1, spccNew.isTrangThaiHopTac());
            stmt.setDouble(2, spccNew.getGiaNhap());

            stmt.setString(3, spccNew.getSanPham().getMaSP());
            stmt.setString(4, spccNew.getNhaCungCap().getMaNCC());

            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }
    
    
}
