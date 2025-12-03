/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import java.sql.*;
import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.NhaCungCap;
import java.util.ArrayList;

/**
 *
 * @author trand
 */
public class NhaCungCapDAO {

    public static ArrayList<NhaCungCap> getAllNhaCungCap() {
        ArrayList<NhaCungCap> dsNCC = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM NhaCungCap";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String maNCC = rs.getString("maNCC");
                String tenNCC = rs.getString("tenNCC");
                String diaChi = rs.getString("diaChi");
                String sdt = rs.getString("sdt");
                String email = rs.getString("email");

                NhaCungCap ncc = new NhaCungCap(maNCC, tenNCC, diaChi, sdt, email);
                dsNCC.add(ncc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsNCC;
    }

    public static boolean themNhaCungCap(NhaCungCap ncc) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "INSERT NhaCungCap (maNCC, tenNCC, diaChi, sdt, email) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, ncc.getMaNCC());
            stmt.setString(2, ncc.getTenNCC());
            stmt.setString(3, ncc.getDiaChi());
            stmt.setString(4, ncc.getSdt());
            stmt.setString(5, ncc.getEmail());
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE KEY constraint")) {
                System.err.println("Lỗi: Số điện thoại hoặc Email đã tồn tại.");
            } else {
                e.printStackTrace();
            }
        }
        return n > 0;
    }

    public static boolean xoaNhaCungCap(String maNCC) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "DELETE FROM NhaCungCap WHERE maNCC = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, maNCC);
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            if (e.getMessage().contains("The DELETE statement conflicted with the REFERENCE constraint")) {
                System.err.println("Lỗi: Không thể xóa nhà cung cấp đang cung cấp sản phẩm.");
            } else {
                e.printStackTrace();
            }
        }
        return n > 0;
    }

    public static boolean suaNhaCungCap(String maNCC, NhaCungCap nccNew) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "UPDATE NhaCungCap SET tenNCC = ?, diaChi = ?, sdt = ?, email = ? WHERE maNCC = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, nccNew.getTenNCC());
            stmt.setString(2, nccNew.getDiaChi());
            stmt.setString(3, nccNew.getSdt());
            stmt.setString(4, nccNew.getEmail());
            stmt.setString(5, maNCC);

            n = stmt.executeUpdate();
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE KEY constraint")) {
                System.err.println("Lỗi: Số điện thoại hoặc Email đã tồn tại.");
            } else {
                e.printStackTrace();
            }
        }
        return n > 0;
    }

    public static NhaCungCap timNCCTheoMa(String ma) {
        NhaCungCap ncc = null;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT * FROM NhaCungCap WHERE maNCC = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, ma);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String maNCC = rs.getString("maNCC");
                String tenNCC = rs.getString("tenNCC");
                String diaChi = rs.getString("diaChi");
                String sdt = rs.getString("sdt");
                String email = rs.getString("email");

                ncc = new NhaCungCap(maNCC, tenNCC, diaChi, sdt, email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ncc;
    }

    public static ArrayList<NhaCungCap> timNCCTheoTen(String tenNCCInput) {
        ArrayList<NhaCungCap> dsNCC = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT * FROM NhaCungCap WHERE tenNCC LIKE ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, "%" + tenNCCInput + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String maNCC = rs.getString("maNCC");
                String tenNCC = rs.getString("tenNCC");
                String diaChi = rs.getString("diaChi");
                String sdt = rs.getString("sdt");
                String email = rs.getString("email");

                NhaCungCap ncc = new NhaCungCap(maNCC, tenNCC, diaChi, sdt, email);
                dsNCC.add(ncc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsNCC;
    }

    public static ArrayList<NhaCungCap> timNCCTheoSDT(String sdtInput) {
        ArrayList<NhaCungCap> dsNCC = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT * FROM NhaCungCap WHERE sdt = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, sdtInput);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String maNCC = rs.getString("maNCC");
                String tenNCC = rs.getString("tenNCC");
                String diaChi = rs.getString("diaChi");
                String sdt = rs.getString("sdt");
                String email = rs.getString("email");

                NhaCungCap ncc = new NhaCungCap(maNCC, tenNCC, diaChi, sdt, email);
                dsNCC.add(ncc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsNCC;
    }

    public static ArrayList<NhaCungCap> timNCCTheoEmail(String emailInput) {
        ArrayList<NhaCungCap> dsNCC = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT * FROM NhaCungCap WHERE email LIKE ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, "%" + emailInput + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String maNCC = rs.getString("maNCC");
                String tenNCC = rs.getString("tenNCC");
                String diaChi = rs.getString("diaChi");
                String sdt = rs.getString("sdt");
                String email = rs.getString("email");

                NhaCungCap ncc = new NhaCungCap(maNCC, tenNCC, diaChi, sdt, email);
                dsNCC.add(ncc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsNCC;
    }

    public static int getMaNCCCUoiCung() {
        int maCuoiCung = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT MAX(maNCC) FROM NhaCungCap";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                String maNCCMax = rs.getString(1);
                if (maNCCMax != null && maNCCMax.matches("NCC-\\d{4}")) {
                    String maSo = maNCCMax.substring(4);
                    maCuoiCung = Integer.parseInt(maSo);
                }
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
        return maCuoiCung;
    }
}
