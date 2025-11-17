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
}
