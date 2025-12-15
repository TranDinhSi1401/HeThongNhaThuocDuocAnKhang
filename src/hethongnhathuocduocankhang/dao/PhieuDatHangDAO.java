/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import java.sql.PreparedStatement;
import java.sql.Connection;
import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.DonViTinh;
import hethongnhathuocduocankhang.entity.NhaCungCap;
import hethongnhathuocduocankhang.entity.SanPham;
import hethongnhathuocduocankhang.entity.NhanVien;
import hethongnhathuocduocankhang.entity.PhieuDatHang;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PhieuDatHangDAO {

    public ArrayList<SanPham> dsSanPham() {
        return SanPhamDAO.getAllTableSanPham();
    }

    public SanPham timSanPham(String ma) {
        return SanPhamDAO.timSPTheoMa(ma);
    }

    public NhaCungCap timNhaCungCap(String ma) {
        return NhaCungCapDAO.timNCCTheoMa(ma);
    }

    public DonViTinh giaSanPham(String ma) {
        DonViTinh dv = null;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "Select maDonViTinh, maSP, tenDonVi, giaBanTheoDonVi from DonViTinh where maSP=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, ma);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    String maDVT = rs.getString(1);
                    SanPham sanPham = new SanPham(rs.getString(2));
                    String tenDV = rs.getString(3);
                    double gia = Double.parseDouble(rs.getString(4));
                    dv = new DonViTinh(maDVT, sanPham, gia, tenDV);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
        }

        return dv;

    }

    public boolean kiemTraPhieuHomNay() {
        boolean tonTai = false;
        String sql = "SELECT COUNT(*) FROM PhieuDatHang WHERE CAST(ngayLap AS DATE) = CAST(GETDATE() AS DATE)";
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    tonTai = rs.getInt(1) > 0;
                }
            }

        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
        }
        return tonTai;
    }

    private static PhieuDatHang taoDoiTuongPhieuDatHang(ResultSet rs) throws SQLException {
        String maPDH = rs.getString("maPhieuDatHang");

        LocalDate ngayLapDate = rs.getDate("ngayLap").toLocalDate();
        LocalDateTime ngayLapDateTime = ngayLapDate.atStartOfDay(); 

        String maNCC = rs.getString("maNCC");
        String maNV = rs.getString("maNV");
        double tongTien = rs.getDouble("tongTien");

        NhaCungCap ncc = NhaCungCapDAO.timNCCTheoMa(maNCC);
        NhanVien nv = NhanVienDAO.timNVTheoMa(maNV);

        if (ncc == null) {
            ncc = new NhaCungCap(maNCC, "Không rõ", null, null, null);
        }
        if (nv == null) {
            nv = new NhanVien(maNV);
        }

        return new PhieuDatHang(maPDH, ncc, ngayLapDateTime, nv, tongTien);
    }

    public static ArrayList<PhieuDatHang> getAllPhieuDatHang() {
        ArrayList<PhieuDatHang> dsPDH = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM PhieuDatHang";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                dsPDH.add(taoDoiTuongPhieuDatHang(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsPDH;
    }

    public static PhieuDatHang timPDHTheoMa(String ma) {
        PhieuDatHang pdh = null;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT * FROM PhieuDatHang WHERE maPhieuDatHang = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, ma);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                pdh = taoDoiTuongPhieuDatHang(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pdh;
    }

    public static ArrayList<PhieuDatHang> timPDHTheoMaNCC(String maNCC) {
        ArrayList<PhieuDatHang> dsPDH = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT * FROM PhieuDatHang WHERE maNCC = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, maNCC);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsPDH.add(taoDoiTuongPhieuDatHang(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsPDH;
    }

    public static ArrayList<PhieuDatHang> timPDHTheoMaNV(String maNV) {
        ArrayList<PhieuDatHang> dsPDH = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT * FROM PhieuDatHang WHERE maNV = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, maNV);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsPDH.add(taoDoiTuongPhieuDatHang(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsPDH;
    }

    public static ArrayList<PhieuDatHang> timPDHTheoNgayLap(LocalDate date) {
        ArrayList<PhieuDatHang> dsPDH = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String querry = "SELECT * FROM PhieuDatHang WHERE ngayLap = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setDate(1, Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsPDH.add(taoDoiTuongPhieuDatHang(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsPDH;
    }

    public static int getMaPDHCuoiCungTrongNgay(String ngay) { 
        int maCuoiCung = 0;
        String maPDHFormat = "PDH-" + ngay + "-"; // Ví dụ: "PDH-071125-"
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT top 1 * FROM PhieuTraHang pth WHERE CAST(pth.ngayLapPhieuTraHang AS DATE) = CAST(GETDATE() AS DATE) order by pth.ngayLapPhieuTraHang desc";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            String ma = null;
            if (rs.next()) {
                ma = rs.getString(1);
            }

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
        return maCuoiCung;
    }
}
