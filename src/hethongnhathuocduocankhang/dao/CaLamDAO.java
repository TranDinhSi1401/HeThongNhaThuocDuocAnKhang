/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import java.sql.*;
import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.CaLam;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * @author GIGABYTE
 */
public class CaLamDAO {

    private static CaLam taoDoiTuongCaLam(ResultSet rs) throws SQLException {
        String maCa = rs.getString("maCa");
        String tenCa = rs.getString("tenCa");
        LocalTime thoiGianBatDau = rs.getTime("thoiGianBatDau").toLocalTime();
        LocalTime thoiGianKetThuc = rs.getTime("thoiGianKetThuc").toLocalTime();

        return new CaLam(maCa, tenCa, thoiGianBatDau, thoiGianKetThuc);
    }

    public static ArrayList<CaLam> getAllCaLam() {
        ArrayList<CaLam> dsCaLam = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM CaLam";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                dsCaLam.add(taoDoiTuongCaLam(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsCaLam;
    }

    public static boolean themCaLam(CaLam caLam) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "INSERT INTO CaLam (maCa, tenCa, thoiGianBatDau, thoiGianKetThuc) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, caLam.getMaCa());
            stmt.setString(2, caLam.getTenCa());
            stmt.setTime(3, Time.valueOf(caLam.getThoiGianBatDau()));
            stmt.setTime(4, Time.valueOf(caLam.getThoiGianKetThuc()));
            
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            // Lỗi trùng mã khóa chính
            if (e.getMessage().contains("PRIMARY KEY constraint")) {
                JOptionPane.showMessageDialog(null, "Lỗi: Mã ca '" + caLam.getMaCa() + "' đã tồn tại.");
            } else {
                e.printStackTrace();
            }
        }
        return n > 0;
    }

    public static boolean xoaCaLam(String maCa) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "DELETE FROM CaLam WHERE maCa = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, maCa);
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            // Lỗi khóa ngoại (không thể xóa ca làm đã được tham chiếu)
             if (e.getMessage().contains("REFERENCE constraint")) {
                JOptionPane.showMessageDialog(null, "Lỗi: Không thể xóa ca làm này vì đã có nhân viên làm việc trong ca.");
            } else {
                e.printStackTrace();
            }
        }
        return n > 0;
    }

    public static boolean suaCaLam(String maCa, CaLam caLamNew) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "UPDATE CaLam SET tenCa = ?, thoiGianBatDau = ?, thoiGianKetThuc = ? WHERE maCa = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, caLamNew.getTenCa());
            stmt.setTime(2, Time.valueOf(caLamNew.getThoiGianBatDau()));
            stmt.setTime(3, Time.valueOf(caLamNew.getThoiGianKetThuc()));
            stmt.setString(4, maCa); 
            
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public static CaLam timCaLamTheoMa(String ma) {
        CaLam caLam = null;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT * FROM CaLam WHERE maCa = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, ma);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                caLam = taoDoiTuongCaLam(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return caLam;
    }

    public static ArrayList<CaLam> timCaLamTheoTen(String ten) {
        ArrayList<CaLam> dsCaLam = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT * FROM CaLam WHERE tenCa LIKE ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, "%" + ten + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsCaLam.add(taoDoiTuongCaLam(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsCaLam;
    }
}