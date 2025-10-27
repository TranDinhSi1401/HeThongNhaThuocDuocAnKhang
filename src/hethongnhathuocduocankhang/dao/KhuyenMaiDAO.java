/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.KhuyenMai;
import hethongnhathuocduocankhang.entity.LoaiSanPhamEnum;
import hethongnhathuocduocankhang.entity.SanPham;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author trand
 */
public class KhuyenMaiDAO {
    public static ArrayList<KhuyenMai> getKhuyenMaiTheoMaSP(String maSp) {
        ArrayList<KhuyenMai> dsKM = new ArrayList<>();
        try {
            Connection con = ConnectDB.getConnection();
            String sql = """
                         SELECT km.maKhuyenMai, km.phanTram, km.soLuongToiThieu, km.soLuongToiDa 
                         FROM SanPham sp
                         JOIN KhuyenMai_SanPham kmsp
                         ON sp.maSP = kmsp.maSP
                         JOIN KhuyenMai km
                         ON km.maKhuyenMai = kmsp.maKhuyenMai
                         WHERE sp.maSP = ? AND NgayBatDau <= GETDATE() AND ngayKetThuc >= GETDATE()
                         """;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maSp);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String maKM = rs.getString(1);
                double phanTram = rs.getDouble(2);
                int soLuongToiThieu = rs.getInt(3);
                int soLuongToiDa = rs.getInt(4);
                KhuyenMai km = new KhuyenMai(maKM, phanTram, soLuongToiThieu, soLuongToiDa);
                dsKM.add(km);
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        return dsKM;
    }
}
