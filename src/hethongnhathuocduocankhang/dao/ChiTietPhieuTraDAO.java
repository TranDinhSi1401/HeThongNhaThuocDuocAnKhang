/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.ChiTietHoaDon;
import hethongnhathuocduocankhang.entity.ChiTietPhieuTraHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author GIGABYTE
 */
public class ChiTietPhieuTraDAO {

    public static boolean insertChiTietPhieuTra(ChiTietPhieuTraHang ctpth) {
        int n = 0;
        try {

            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();

            String sql = "INSERT INTO ChiTietPhieuTraHang "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, ctpth.getPhieuTraHang().getMaPhieuTraHang());
            ps.setString(2, ctpth.getChiTietHoaDon().getMaChiTietHoaDon());
            ps.setInt(3, ctpth.getSoLuong());
            ps.setString(4, ctpth.getTruongHopDoiTra().toString());
            ps.setString(5, ctpth.getTinhTrangSanPham().toString());
            ps.setString(6, ctpth.getGiaTriHoanTra());
            ps.setDouble(7, ctpth.getThanhTienHoanTra());

            n = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }
}
