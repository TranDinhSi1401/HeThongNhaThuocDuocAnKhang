/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import java.sql.*;
import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.LoSanPham;
import hethongnhathuocduocankhang.entity.NhaCungCap;
import hethongnhathuocduocankhang.entity.SanPham;
import java.time.LocalDate;
import java.util.ArrayList;

public class NhaCungCapDAO {

    public static ArrayList<NhaCungCap> getAllNhaCungCap() {
        ArrayList<NhaCungCap> dsNCC = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM NhaCungCap WHERE daXoa = 0";
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
    
    public static ArrayList<NhaCungCap> getAllNhaCungCap(String sql) {
        ArrayList<NhaCungCap> dsNCC = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
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
            String querry = "UPDATE NhaCungCap SET daXoa = 1 WHERE maNCC = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, maNCC);
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
            String querry = "SELECT * FROM NhaCungCap WHERE maNCC = ? AND daXoa = 0";
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
            String querry = "SELECT * FROM NhaCungCap WHERE tenNCC LIKE ? AND daXoa = 0";
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
            String querry = "SELECT * FROM NhaCungCap WHERE sdt = ? AND daXoa = 0";
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
            String querry = "SELECT * FROM NhaCungCap WHERE email LIKE ? AND daXoa = 0";
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

    public static NhaCungCap getNhaCungCapTheoTen(String ten) {
        NhaCungCap ncc = new NhaCungCap();
        String sql = "Select * from NhaCungCap where tenNCC like ? AND daXoa = 0";
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, ten);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    String ma = rs.getString(1);
                    String tenNCC = rs.getString(2);
                    String diaChi = rs.getString(3);
                    String sdt = rs.getString(4);
                    String email = rs.getString(5);
                    ncc = new NhaCungCap(ma, tenNCC, diaChi, sdt, email);
                }
            }

        } catch (SQLException s) {
            s.printStackTrace();
        }
        return ncc;
    }

    public static ArrayList<LoSanPham> getDanhSachLoTheoMaNCC(String maNCC) {
        String sql = "Select l.maLoSanPham, maSP, l.soLuong, ngaySanXuat, ngayHetHan, daHuy  from LoSanPham l join ChiTietPhieuNhap ct on l.maLoSanPham=ct.maLoSanPham \n"
                + "join NhaCungCap c on c.maNCC=ct.maNCC\n"
                + "where c.maNCC like ? AND daXoa = 0";
        ArrayList<LoSanPham> dsLo = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, maNCC);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    String ma = rs.getString(1);
                    String maSP = rs.getString(2);
                    int sl = rs.getInt(3);
                    LocalDate ngaySX = rs.getDate(4).toLocalDate();
                    LocalDate ngayHH = rs.getDate(5).toLocalDate();
                    boolean daHuy = rs.getBoolean(6);
                    LoSanPham lo = new LoSanPham(ma, new SanPham(maSP), sl, ngaySX, ngayHH, daHuy);
                    dsLo.add(lo);
                }
            }

        } catch (SQLException a) {
            a.printStackTrace();
        }
        return dsLo;
    }

    public static NhaCungCap timMotNCCTheoTen(String tenNCCInput) {
        NhaCungCap ncc = new NhaCungCap();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT * FROM NhaCungCap WHERE tenNCC LIKE ? AND daXoa = 0";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, "%" + tenNCCInput + "%");
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
}
