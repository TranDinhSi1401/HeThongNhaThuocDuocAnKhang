/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import hethongnhathuocduocankhang.entity.KhuyenMaiSanPham;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import hethongnhathuocduocankhang.connectDB.ConnectDB;

public class KhuyenMaiSanPhamDAO {

    // 1. HÀM THÊM (Gán sản phẩm vào khuyến mãi)
    public static boolean themKhuyenMaiSanPham(KhuyenMaiSanPham kmsp) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            
            // INSERT 3 trường: 2 khóa chính và ngày chỉnh sửa
            String sql = "INSERT INTO KhuyenMai_SanPham (maKhuyenMai, maSP, ngayChinhSua) VALUES (?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            // Lấy mã từ đối tượng KhuyenMai
            stmt.setString(1, kmsp.getKhuyenMai().getMaKhuyenMai());
            
            // Lấy mã từ đối tượng SanPham
            stmt.setString(2, kmsp.getSanPham().getMaSP());
            
            // Lấy thời gian hiện tại của hệ thống để lưu vào DB
            // Cột ngayChinhSua là DATETIME2 -> Java dùng Timestamp
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    // 2. HÀM XÓA (Gỡ sản phẩm khỏi khuyến mãi)
    public static boolean xoaKhuyenMaiSanPham(String maKhuyenMai, String maSP) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            
            String query = "DELETE FROM KhuyenMai_SanPham WHERE maKhuyenMai = ? AND maSP = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            
            stmt.setString(1, maKhuyenMai);
            stmt.setString(2, maSP);
            
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }
    
     public static boolean xoaHetKMCuaSP(String maSP) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            
            String query = "DELETE FROM KhuyenMai_SanPham WHERE maSP = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            
            stmt.setString(1, maSP);
            
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    // 3. HÀM SỬA
    // Với bảng trung gian (Link Table) chỉ có Khóa chính và Ngày giờ, 
    // "Sửa" thường có nghĩa là cập nhật lại 'ngayChinhSua' để đánh dấu lần cập nhật mới nhất.
    // Không thể sửa maKhuyenMai hay maSP vì nó là khóa chính (muốn đổi thì Xóa đi Thêm lại).
    public static boolean suaKhuyenMaiSanPham(KhuyenMaiSanPham kmsp) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            
            // Chỉ update được ngày chỉnh sửa dựa trên 2 khóa chính
            String query = "UPDATE KhuyenMai_SanPham SET ngayChinhSua = ? WHERE maKhuyenMai = ? AND maSP = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            
            // Set thời gian mới nhất
            stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            
            // Điều kiện WHERE
            stmt.setString(2, kmsp.getKhuyenMai().getMaKhuyenMai());
            stmt.setString(3, kmsp.getSanPham().getMaSP());
            
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }
}