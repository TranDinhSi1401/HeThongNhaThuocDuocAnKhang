/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.ChiTietPhieuDatHang;
import hethongnhathuocduocankhang.entity.PhieuDatHang;
import hethongnhathuocduocankhang.entity.SanPham;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author GIGABYTE
 * DAO này dùng để lấy thông tin ChiTietPhieuDatHang (danh sách SP)
 * từ một PhieuDatHang.
 */
public class ChiTietPhieuDatDAO {

    /**
     * Lấy tất cả các dòng ChiTietPhieuDatHang của một PhieuDatHang cụ thể.
     * Hàm này thực hiện JOIN với SanPham để lấy Tên Sản Phẩm.
     * @param maPhieuDatHang Mã của phiếu đặt hàng cha.
     * @return ArrayList<ChiTietPhieuDatHang>
     */
    public static ArrayList<ChiTietPhieuDatHang> getChiTietTheoMaPDH(String maPhieuDatHang) {
        ArrayList<ChiTietPhieuDatHang> dsChiTiet = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            
            // Câu SQL JOIN 2 bảng để lấy Tên Sản Phẩm
            String sql = "SELECT ctpd.*, sp.ten "
                       + "FROM ChiTietPhieuDat ctpd "
                       + "JOIN SanPham sp ON ctpd.maSP = sp.maSP "
                       + "WHERE ctpd.maPhieuDatHang = ?";
                       
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maPhieuDatHang);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // 1. Lấy dữ liệu từ CSDL
                int soLuong = rs.getInt("soLuong");
                double donGia = rs.getDouble("donGia");
                double thanhTien = rs.getDouble("thanhTien");
                
                // 2. Tái tạo đối tượng SanPham (chứa mã và tên)
                SanPham sp = new SanPham();
                sp.setMaSP(rs.getString("maSP"));
                sp.setTen(rs.getString("ten")); // Lấy từ JOIN

                // 3. Tái tạo PhieuDatHang (stub, chỉ cần mã)
                PhieuDatHang pdh = new PhieuDatHang();
                pdh.setMaPhieuDat(maPhieuDatHang);
                
                // 4. Tái tạo ChiTietPhieuDatHang
                ChiTietPhieuDatHang ctpd = new ChiTietPhieuDatHang(
                    sp,
                    soLuong,
                    pdh,
                    donGia,
                    thanhTien
                );
                
                dsChiTiet.add(ctpd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsChiTiet;
    }
}