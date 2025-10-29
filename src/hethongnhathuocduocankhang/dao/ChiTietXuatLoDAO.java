/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.ChiTietXuatLo;
import hethongnhathuocduocankhang.entity.HoaDon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author trand
 */
public class ChiTietXuatLoDAO {
    public static boolean insertChiTietXuatLo(ChiTietXuatLo ctxl) {
        int n = 0;
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO ChiTietXuatLo VALUES (?, ?, ?)"
            );

            ps.setString(1, ctxl.getLoSanPham().getMaLoSanPham());
            ps.setString(2, ctxl.getChiTietHoaDon().getMaChiTietHoaDon());
            ps.setInt(3, ctxl.getSoLuong());
            
            n = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }
}
