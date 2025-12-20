/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.LichSuLo;
import hethongnhathuocduocankhang.entity.LoSanPham;
import hethongnhathuocduocankhang.entity.NhanVien;
import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDate;

/**
 *
 * @author admin
 */
public class LichSuLoDAO {
    public static ArrayList<LichSuLo> getAllLichSuLo() throws Exception{
        ArrayList<LichSuLo> dsLichSu = new ArrayList<>();
        String sql = "Select * from LichSuLo order by maLichSuLo DESC";
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            try(ResultSet rs = st.executeQuery()){
                while(rs.next()){
                    String maLichSu = rs.getString(1);
                    String maLo = rs.getString(2);
                    LocalDate thoiGian = rs.getDate(3).toLocalDate();
                    String hanhDong = rs.getString(4);
                    int soLuongSau = rs.getInt(5);
                    String ghiChu = rs.getString(6);
                    String maNV = rs.getString(7);
                    LichSuLo ls = new LichSuLo(maLichSu, new LoSanPham(maLo), thoiGian, hanhDong, soLuongSau, ghiChu, new NhanVien(maNV));
                    dsLichSu.add(ls);
                }
            }
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return dsLichSu;
    }
    private static int getMaxLichSuLo(){
        int ma =0;
        String sql = "Select Max(maLichSuLo) from LichSuLo";
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            try(ResultSet rs = st.executeQuery()){
                if(rs.next()){
                    String maLichSu = rs.getString(1);
                    ma = Integer.parseInt(maLichSu.substring(4));
                }
            }
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return ma;
    } 
            
    public static boolean addLichSuLo(LoSanPham lo, NhanVien nv, String loaiThaoTac, int sl, String ghiChu){
        int n =0;
        String sql = "Insert LichSuLo(maLichSuLo, maLoSanPham, thoiGian, hanhDong, soLuongSau, ghiChu, maNV)"
                + "values(?, ?, ?, ?, ?, ?, ?)";
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            java.sql.Date thoiGian = java.sql.Date.valueOf(LocalDate.now());
            st.setString(1, String.format("LSL-%04d", LichSuLoDAO.getMaxLichSuLo()+1));
            st.setString(2, lo.getMaLoSanPham());
            st.setDate(3, thoiGian);
            st.setString(4, loaiThaoTac);
            st.setInt(5, sl);
            st.setString(6, ghiChu);
            st.setString(7, nv.getMaNV());
            n = st.executeUpdate();
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return n>0;
    }
}
