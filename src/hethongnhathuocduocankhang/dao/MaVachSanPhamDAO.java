/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.MaVachSanPham;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class MaVachSanPhamDAO {
    public static ArrayList<MaVachSanPham> timMaSPTheoMaVach(){
         ArrayList<MaVachSanPham> ds = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "Select * from MaVachSanPham";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next()){        
                String maVachSanPham = rs.getString(1);
                String maSP = rs.getString(2);
                MaVachSanPham sanPhamTheoMa = new MaVachSanPham(maSP, maVachSanPham);
                ds.add(sanPhamTheoMa);
            }
        } catch (SQLException sQLException) {
        }       
        return ds;
    }
}
