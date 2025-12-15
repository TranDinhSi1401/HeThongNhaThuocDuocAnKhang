package hethongnhathuocduocankhang.dao;

import java.sql.*;
import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.DonViTinh;
import hethongnhathuocduocankhang.entity.SanPham;
import hethongnhathuocduocankhang.entity.LoaiSanPhamEnum;
import hethongnhathuocduocankhang.entity.MaVachSanPham;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SanPhamDAO {

    public static ArrayList<SanPham> getAllTableSanPham() {
        ArrayList<SanPham> dsSP = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT * FROM SanPham WHERE daXoa = 0";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String maSP = rs.getString("maSP");
                String tenSP = rs.getString("ten");
                String moTa = rs.getString("moTa");
                String thanhPhan = rs.getString("thanhPhan");
                LoaiSanPhamEnum loaiSanPham = LoaiSanPhamEnum.valueOf(rs.getString("loaiSanPham"));
                int tonToiThieu = rs.getInt("tonToiThieu");
                int tonToiDa = rs.getInt("tonToiDa");
                boolean daXoa = rs.getBoolean("daXoa");

                //SanPham sp = new SanPham(maSP, tenSP, moTa, thanhPhan, loaiSanPham, tonToiThieu, tonToiDa);
                SanPham sp = new SanPham(maSP, tenSP, moTa, thanhPhan, loaiSanPham, tonToiThieu, tonToiDa, daXoa);
                dsSP.add(sp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsSP;
    }

    public static boolean themSanPham(SanPham sp) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String sql = "INSERT INTO SanPham VALUES (?, ?, ?, ?, ?, ?, ?, 0)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, sp.getMaSP());
            stmt.setString(2, sp.getTen());
            stmt.setString(3, sp.getMoTa());
            stmt.setString(4, sp.getThanhPhan());
            stmt.setString(5, sp.getLoaiSanPham().toString());
            stmt.setInt(6, sp.getTonToiThieu());
            stmt.setInt(7, sp.getTonToiDa());
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public static LinkedHashMap<SanPham, Number[]> getSPBanChayTrongThang(LocalDate tg) {
        LinkedHashMap<SanPham, Number[]> dsSP = new LinkedHashMap<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            // 1. Thêm DONGIA_TB = AVG(CTHD.donGia) vào SQL
            String query = "SELECT SP.[maSP], [ten], [moTa], [thanhPhan], [loaiSanPham], [tonToiThieu], [tonToiDa], SP.[daXoa], "
                    + "SLBAN = SUM(CTHD.soLuong * DVT.heSoQuyDoi), "
                    + "TONGTIEN = SUM(CTHD.soLuong * CTHD.donGia), "
                    + "DONGIA_TB = AVG(CTHD.donGia) " // Thêm dòng này
                    + "FROM ChiTietHoaDon CTHD JOIN DonViTinh DVT "
                    + "ON CTHD.maDonViTinh = DVT.maDonViTinh JOIN SanPham SP "
                    + "ON SP.maSP = DVT.maSP JOIN HoaDon HD "
                    + "ON CTHD.maHoaDon = HD.maHoaDon "
                    + "WHERE MONTH(HD.ngayLapHoaDon) = ? AND YEAR(HD.ngayLapHoaDon) = ? AND SP.daXoa = 0"
                    + "GROUP BY SP.[maSP], [ten], [moTa], [thanhPhan], [loaiSanPham], [tonToiThieu], [tonToiDa], SP.[daXoa] "
                    + "ORDER BY SLBAN DESC";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, tg.getMonthValue());
            stmt.setInt(2, tg.getYear());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String maSP = rs.getString("maSP");
                String ten = rs.getString("ten");
                String moTa = rs.getString("moTa");
                String thanhPhan = rs.getString("thanhPhan");
                LoaiSanPhamEnum loaiSanPham = LoaiSanPhamEnum.valueOf(rs.getString("loaiSanPham"));
                int tonToiThieu = rs.getInt("tonToiThieu");
                int tonToiDa = rs.getInt("tonToiDa");
                boolean daXoa = rs.getBoolean("daXoa");
                SanPham sp = new SanPham(maSP, ten, moTa, thanhPhan, loaiSanPham, tonToiThieu, tonToiDa, daXoa);
                
                // 2. Sửa Number[2] thành Number[3]
                Number[] thongSo = new Number[3];
                thongSo[0] = rs.getInt("SLBAN");
                thongSo[1] = rs.getDouble("TONGTIEN");
                thongSo[2] = rs.getDouble("DONGIA_TB"); // 3. Lấy dữ liệu vào cột thứ 3
                dsSP.put(sp, thongSo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsSP;
    }

    public static LinkedHashMap<SanPham, Number[]> getSPBanChayTrongNam(LocalDate tg) {
        LinkedHashMap<SanPham, Number[]> dsSP = new LinkedHashMap<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            // 1. Thêm DONGIA_TB = AVG(CTHD.donGia) vào SQL
            String query = "SELECT SP.[maSP], [ten], [moTa], [thanhPhan], [loaiSanPham], [tonToiThieu], [tonToiDa], SP.[daXoa], "
                    + "SLBAN = SUM(CTHD.soLuong * DVT.heSoQuyDoi), "
                    + "TONGTIEN = SUM(CTHD.soLuong * CTHD.donGia), "
                    + "DONGIA_TB = AVG(CTHD.donGia) "
                    + "FROM ChiTietHoaDon CTHD JOIN DonViTinh DVT "
                    + "ON CTHD.maDonViTinh = DVT.maDonViTinh JOIN SanPham SP "
                    + "ON SP.maSP = DVT.maSP JOIN HoaDon HD "
                    + "ON CTHD.maHoaDon = HD.maHoaDon "
                    + "WHERE YEAR(HD.ngayLapHoaDon) = ? AND SP.daXoa = 0"
                    + "GROUP BY SP.[maSP], [ten], [moTa], [thanhPhan], [loaiSanPham], [tonToiThieu], [tonToiDa], SP.[daXoa] "
                    + "ORDER BY SLBAN DESC";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, tg.getYear());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String maSP = rs.getString("maSP");
                String ten = rs.getString("ten");
                String moTa = rs.getString("moTa");
                String thanhPhan = rs.getString("thanhPhan");
                LoaiSanPhamEnum loaiSanPham = LoaiSanPhamEnum.valueOf(rs.getString("loaiSanPham"));
                int tonToiThieu = rs.getInt("tonToiThieu");
                int tonToiDa = rs.getInt("tonToiDa");
                boolean daXoa = rs.getBoolean("daXoa");
                SanPham sp = new SanPham(maSP, ten, moTa, thanhPhan, loaiSanPham, tonToiThieu, tonToiDa, daXoa);
                
                // 2. Sửa Number[2] thành Number[3]
                Number[] thongSo = new Number[3];
                thongSo[0] = rs.getInt("SLBAN");
                thongSo[1] = rs.getDouble("TONGTIEN");
                thongSo[2] = rs.getDouble("DONGIA_TB"); // 3. Lấy dữ liệu vào cột thứ 3
                dsSP.put(sp, thongSo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsSP;
    }

    // [SOFT DELETE]
    public static boolean xoaSanPham(String maSP) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String query = "UPDATE SanPham SET daXoa = 1 WHERE maSP = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, maSP);
            n = stmt.executeUpdate();

            DonViTinhDAO.xoaDonViTinhTheoMaSP(maSP);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public static boolean suaSanPham(String maSP, SanPham spNew) {
        int n = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String query = "UPDATE SanPham SET ten = ?, moTa = ?, thanhPhan = ?, loaiSanPham = ?, tonToiThieu = ?, tonToiDa = ? WHERE maSP = ? AND daXoa = 0";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, spNew.getTen());
            stmt.setString(2, spNew.getMoTa());
            stmt.setString(3, spNew.getThanhPhan());
            stmt.setString(4, spNew.getLoaiSanPham().toString());
            stmt.setInt(5, spNew.getTonToiThieu());
            stmt.setInt(6, spNew.getTonToiDa());
            stmt.setString(7, maSP);
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public static SanPham timSPTheoMa(String ma) {
        SanPham sp = null;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String query = "SELECT * FROM SanPham WHERE maSP = ? AND daXoa = 0";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, ma);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String maSP = rs.getString("maSP");
                String ten = rs.getString("ten");
                String moTa = rs.getString("moTa");
                String thanhPhan = rs.getString("thanhPhan");
                LoaiSanPhamEnum loaiSanPham = LoaiSanPhamEnum.valueOf(rs.getString("loaiSanPham"));
                int tonToiThieu = rs.getInt("tonToiThieu");
                int tonToiDa = rs.getInt("tonToiDa");
                boolean daXoa = rs.getBoolean("daXoa");
                sp = new SanPham(maSP, ten, moTa, thanhPhan, loaiSanPham, tonToiThieu, tonToiDa, daXoa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sp;
    }

    public static ArrayList<SanPham> timSPTheoTen(String tenSP) {
        ArrayList<SanPham> dsSP = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String query = "SELECT * FROM SanPham WHERE ten LIKE ? AND daXoa = 0";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, "%" + tenSP + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String maSP = rs.getString("maSP");
                String ten = rs.getString("ten");
                String moTa = rs.getString("moTa");
                String thanhPhan = rs.getString("thanhPhan");
                LoaiSanPhamEnum loaiSanPham = LoaiSanPhamEnum.valueOf(rs.getString("loaiSanPham"));
                int tonToiThieu = rs.getInt("tonToiThieu");
                int tonToiDa = rs.getInt("tonToiDa");
                boolean daXoa = rs.getBoolean("daXoa");
                SanPham sp = new SanPham(maSP, ten, moTa, thanhPhan, loaiSanPham, tonToiThieu, tonToiDa, daXoa);
                dsSP.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsSP;
    }

    public static ArrayList<SanPham> timSPTheoMaNCC(String maNhaCC) {
        ArrayList<SanPham> dsSP = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            // Cần check daXoa của bảng SanPham (SP.daXoa = 0)
            String query = "SELECT SP.* FROM SanPham SP JOIN SanPhamCungCap SPCC "
                    + "ON SP.maSP = SPCC.maSP JOIN NhaCungCap NCC "
                    + "ON SPCC.maNCC = NCC.maNCC WHERE NCC.maNCC = ? AND SP.daXoa = 0";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, maNhaCC);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String maSP = rs.getString("maSP");
                String ten = rs.getString("ten");
                String moTa = rs.getString("moTa");
                String thanhPhan = rs.getString("thanhPhan");
                LoaiSanPhamEnum loaiSanPham = LoaiSanPhamEnum.valueOf(rs.getString("loaiSanPham"));
                int tonToiThieu = rs.getInt("tonToiThieu");
                int tonToiDa = rs.getInt("tonToiDa");
                boolean daXoa = rs.getBoolean("daXoa");
                SanPham sp = new SanPham(maSP, ten, moTa, thanhPhan, loaiSanPham, tonToiThieu, tonToiDa, daXoa);
                dsSP.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsSP;
    }

    public static ArrayList<SanPham> timSPTheoLoai(String loaiSP) {
        ArrayList<SanPham> dsSP = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String query = "SELECT * FROM SanPham WHERE loaiSanPham = ? AND daXoa = 0";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, loaiSP);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String maSP = rs.getString("maSP");
                String ten = rs.getString("ten");
                String moTa = rs.getString("moTa");
                String thanhPhan = rs.getString("thanhPhan");
                LoaiSanPhamEnum loaiSanPham = LoaiSanPhamEnum.valueOf(rs.getString("loaiSanPham"));
                int tonToiThieu = rs.getInt("tonToiThieu");
                int tonToiDa = rs.getInt("tonToiDa");
                boolean daXoa = rs.getBoolean("daXoa");
                SanPham sp = new SanPham(maSP, ten, moTa, thanhPhan, loaiSanPham, tonToiThieu, tonToiDa, daXoa);
                dsSP.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsSP;
    }

    public static int getMaSPCuoiCung() {
        int maCuoiCung = 0;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT MAX(maSP) FROM SanPham";

            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                String maSPMax = rs.getString(1);

                if (maSPMax != null && maSPMax.matches("^SP-\\d{4}$")) {
                    try {
                        String maSo = maSPMax.substring(3);
                        maCuoiCung = Integer.parseInt(maSo);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maCuoiCung;
    }

    public static SanPham timMotSPTheoMaNCC(String maNhaCC) {
        SanPham sp = new SanPham();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String query = "SELECT SP.* FROM SanPham SP JOIN SanPhamCungCap SPCC "
                    + "ON SP.maSP = SPCC.maSP JOIN NhaCungCap NCC "
                    + "ON SPCC.maNCC = NCC.maNCC WHERE NCC.maNCC = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, maNhaCC);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String maSP = rs.getString("maSP");
                String ten = rs.getString("ten");
                String moTa = rs.getString("moTa");
                String thanhPhan = rs.getString("thanhPhan");
                LoaiSanPhamEnum loaiSanPham = LoaiSanPhamEnum.valueOf(rs.getString("loaiSanPham"));
                int tonToiThieu = rs.getInt("tonToiThieu");
                int tonToiDa = rs.getInt("tonToiDa");
                sp = new SanPham(maSP, ten, moTa, thanhPhan, loaiSanPham, tonToiThieu, tonToiDa);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sp;
    }

    private static ArrayList<MaVachSanPham> timMaBarcodeTheoMaSP(String masp) {
        ArrayList<MaVachSanPham> dsMaVachSP = new ArrayList<>(); // Fixed: Khởi tạo ArrayList
        SanPham sp = SanPhamDAO.timSPTheoMa(masp);
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String query = "SELECT * FROM [dbo].[MaVachSanPham] WHERE [maSP] = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, masp);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String maBarcode = rs.getString("maBarcode");
                String maSP = rs.getString("maSP");

                MaVachSanPham mvsp = new MaVachSanPham(sp, maBarcode);

                dsMaVachSP.add(mvsp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsMaVachSP;
    }

    public static ArrayList<SanPham> getSPDaHetHang() {
        ArrayList<SanPham> dsSP = new ArrayList<>();

        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String query = "select sp.maSP, sp.ten, sp.moTa, sp.thanhPhan, sp.loaiSanPham, sp.tonToiThieu, sp.tonToiDa, sp.daXoa, soLuong = sum(soLuong)\n"
                    + "from LoSanPham lsp join SanPham sp\n"
                    + "on lsp.maSP = sp.maSP\n"
                    + "group by sp.maSP, sp.ten, sp.moTa, sp.thanhPhan, sp.loaiSanPham, sp.tonToiThieu, sp.tonToiDa, sp.daXoa\n"
                    + "having sum(soLuong) = 0";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String maSP = rs.getString("maSP");
                String ten = rs.getString("ten");
                String moTa = rs.getString("moTa");
                String thanhPhan = rs.getString("thanhPhan");
                LoaiSanPhamEnum loaiSanPham = LoaiSanPhamEnum.valueOf(rs.getString("loaiSanPham"));
                int tonToiThieu = rs.getInt("tonToiThieu");
                int tonToiDa = rs.getInt("tonToiDa");
                boolean daXoa = rs.getBoolean("daXoa");
                SanPham sp = new SanPham(maSP, ten, moTa, thanhPhan, loaiSanPham, tonToiThieu, tonToiDa, daXoa);
                //int soLuong = rs.getInt("soLuong");
                dsSP.add(sp);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối csdl");
        }

        return dsSP;
    }

    public static Map<SanPham, Integer> getSPSapHetHang() {
        Map<SanPham, Integer> dsSP = new HashMap<>();

        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String query = "select sp.maSP, sp.ten, sp.moTa, sp.thanhPhan, sp.loaiSanPham, sp.tonToiThieu, sp.tonToiDa, sp.daXoa, soLuong = sum(soLuong)\n"
                    + "from LoSanPham lsp join SanPham sp\n"
                    + "on lsp.maSP = sp.maSP\n"
                    + "group by sp.maSP, sp.ten, sp.moTa, sp.thanhPhan, sp.loaiSanPham, sp.tonToiThieu, sp.tonToiDa, sp.daXoa\n"
                    + "having sum(soLuong) <= tonToiDa";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String maSP = rs.getString("maSP");
                String ten = rs.getString("ten");
                String moTa = rs.getString("moTa");
                String thanhPhan = rs.getString("thanhPhan");
                LoaiSanPhamEnum loaiSanPham = LoaiSanPhamEnum.valueOf(rs.getString("loaiSanPham"));
                int tonToiThieu = rs.getInt("tonToiThieu");
                int tonToiDa = rs.getInt("tonToiDa");
                boolean daXoa = rs.getBoolean("daXoa");
                SanPham sp = new SanPham(maSP, ten, moTa, thanhPhan, loaiSanPham, tonToiThieu, tonToiDa, daXoa);
                int soLuong = rs.getInt("soLuong");
                dsSP.put(sp, soLuong);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối csdl");
        }
        return dsSP;
    }

    public static String getMaSpTheoMaVach(String maVach) {
        String maSP = null;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            String query = "SELECT maSP FROM MaVachSanPham WHERE maVach = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, maVach);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                maSP = rs.getString("maSP");
                return maSP;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối csdl");
        }
        return null;
    }
    
    //Tra cứu chung
    public static ArrayList<SanPham> getAllTableSanPham(String sql) {
        ArrayList<SanPham> dsSP = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String maSP = rs.getString("maSP");
                String tenSP = rs.getString("ten");
                String moTa = rs.getString("moTa");
                String thanhPhan = rs.getString("thanhPhan");
                LoaiSanPhamEnum loaiSanPham = LoaiSanPhamEnum.valueOf(rs.getString("loaiSanPham"));
                int tonToiThieu = rs.getInt("tonToiThieu");
                int tonToiDa = rs.getInt("tonToiDa");
                boolean daXoa = rs.getBoolean("daXoa");
                
                //SanPham sp = new SanPham(maSP, tenSP, moTa, thanhPhan, loaiSanPham, tonToiThieu, tonToiDa);
                SanPham sp = new SanPham(maSP, tenSP, moTa, thanhPhan, loaiSanPham, tonToiThieu, tonToiDa, daXoa);
                dsSP.add(sp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsSP;
    }
}
