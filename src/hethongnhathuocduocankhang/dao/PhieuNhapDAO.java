/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import hethongnhathuocduocankhang.connectDB.ConnectDB;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author admin
 */
public class PhieuNhapDAO {
    public static int getPhieuCuoiCung(){
        String sql = "Select Max(maPhieuNhap) from PhieuNhap";
        int ma = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            try(ResultSet rs = st.executeQuery()){
                if (rs.next()){
                    String maPhieu = rs.getString(1);
                    if(maPhieu !=null && maPhieu.matches("^PN-\\d{4}$")){
                        String maSo = maPhieu.substring(3);
                        ma = Integer.parseInt(maSo);
                    }
                }
            }
        } catch (SQLException sQLException) {
        }
        return ma;
    }
}
