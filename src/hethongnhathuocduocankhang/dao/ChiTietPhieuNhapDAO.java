/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.LoSanPham;
import hethongnhathuocduocankhang.entity.NhaCungCap;
import hethongnhathuocduocankhang.entity.PhieuNhap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class ChiTietPhieuNhapDAO {
    public static boolean themChiTietPhieuNhap(LoSanPham lo, PhieuNhap pn, NhaCungCap ncc, double gia, double thanhTien, String ghiChu){
        int n=0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "Insert ChiTietPhieuNhap (maPhieuNhap, maLoSanPham, maNCC soLuong, donGia, thanhTien, ghiChu) "
                    + "values(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, pn.getMaPhieuNhap());
            st.setString(2, lo.getMaLoSanPham());
            st.setString(3, ncc.getMaNCC());
            st.setInt(3, lo.getSoLuong());
            st.setDouble(4, gia);
            st.setDouble(5, thanhTien);
            st.setString(6, ghiChu);
            n = st.executeUpdate();
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return n>0;
    }      
}
