/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.entity.ChiTietHoaDon;
import hethongnhathuocduocankhang.entity.HoaDon;
import hethongnhathuocduocankhang.entity.DonViTinh;
import hethongnhathuocduocankhang.entity.LoaiSanPhamEnum;
import hethongnhathuocduocankhang.entity.NhanVien;
import hethongnhathuocduocankhang.entity.SanPham;
import java.util.List;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
/**
 *
 * @author MINH KHANG
 */
public class TraHangDAO {

    public List<ChiTietHoaDon> getAllCTHD(String maHoaDon) {
        List<ChiTietHoaDon> listCTHD = new ArrayList<>();
        
        // Câu query JOIN 4 bảng: ChiTietHoaDon, HoaDon, DonViTinh, SanPham
        String querry = "SELECT cthd.*, hd.*, dvt.*, sp.maSP, sp.ten, sp.moTa, sp.thanhPhan, sp.tonToiThieu, sp.tonToiDa " +
                      "FROM ChiTietHoaDon cthd " +
                      "JOIN HoaDon hd ON cthd.maHoaDon = hd.maHoaDon " +
                      "JOIN DonViTinh dvt ON cthd.maDonViTinh = dvt.maDonViTinh " +
                      "JOIN SanPham sp ON dvt.maSP = sp.maSP " + // <-- Chú ý: Đảm bảo tên cột `maSP` ở bảng SanPham là đúng
                      "WHERE cthd.maHoaDon = ?";

        // Dùng try-with-resources để kết nối tự động đóng khi xong
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement stmt = con.prepareStatement(querry)) {

            stmt.setString(1, maHoaDon);
            ResultSet rs = stmt.executeQuery();

            HoaDon hoaDon = null; // Chỉ cần tạo 1 đối tượng HoaDon vì nó giống nhau

            while (rs.next()) {
                
                // 1. Tạo đối tượng SanPham
                // Chú ý: Code gốc của bạn có vẻ không nhất quán giữa 'maSP' và 'maSP'
                // Tôi dùng 'maSP' như trong hàm getSanPhamTheoMa của bạn
                SanPham sp = new SanPham(
                        rs.getString("maSP"), 
                        rs.getString("ten"), 
                        rs.getString("moTa"),
                        rs.getString("thanhPhan"), 
                        LoaiSanPhamEnum.THUOC, // Code gốc của bạn đang hardcode
                        rs.getInt("tonToiThieu"), 
                        rs.getInt("tonToiDa")
                );

                // 2. Tạo đối tượng DonViTinh
                DonViTinh dvt = new DonViTinh(
                        rs.getString("maDonViTinh"), 
                        sp, // Dùng SanPham vừa tạo
                        rs.getInt("heSoQuyDOi"), 
                        rs.getDouble("giaBanTheoDonVi"),
                        rs.getString("tenDonVi"), 
                        rs.getBoolean("donViTinhCoBan")
                );

                // 3. Tạo đối tượng HoaDon (Chỉ tạo 1 lần)
                if (hoaDon == null) {
                    hoaDon = new HoaDon(
                            rs.getString("maHoaDon"), 
                            null, // Bạn cần JOIN thêm bảng NhanVien nếu muốn có
                            null, // Bạn cần JOIN thêm bảng KhachHang nếu muốn có
                            null, // Bạn cần lấy NgayLapTu rs.getTimestamp("ngayLap")
                            rs.getBoolean("chuyenKhoan"), 
                            rs.getBoolean("trangThai"),
                            rs.getDouble("tongTien")
                    );
                }

                // 4. Tạo đối tượng ChiTietHoaDon
                // Chú ý: Constructor gốc của bạn truyền giamGia 2 lần?
                // new ChiTietHoaDon(ma, hd, dvt, soLuong, giamGia, giamGia, thanhTien)
                ChiTietHoaDon cthd = new ChiTietHoaDon(
                        rs.getString("maChiTietHoaDon"), 
                        hoaDon, // Dùng HoaDon đã tạo
                        dvt,    // Dùng DonViTinh đã tạo
                        rs.getInt("soLuong"), 
                        rs.getDouble("giamGia"), 
                        rs.getDouble("giamGia"), // Giữ nguyên theo code gốc của bạn
                        rs.getDouble("thanhTien")
                );

                listCTHD.add(cthd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listCTHD;
    }

    public List<HoaDon> getAllHoaDon(String soDienThoai) {
        List<HoaDon> listHD = null;
        try(Connection con = ConnectDB.getConnection()){
            String querry = "select hd from HoaDon hd where maKH = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, soDienThoai);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return listHD;
    }

    public List<HoaDon> getAllHoaDonTheoSoDienThoai(String soDienThoai) {
        List<HoaDon> listHD = new ArrayList<>();
        try(Connection con = ConnectDB.getConnection()){
            String querry = "select hd.maHoaDon, hd.ngayLapHoaDon, hd.tongTien " +
                            "from HoaDon hd join KhachHang kh " +
                            "on hd.maKH = kh.maKH " +
                            "where kh.sdt = ?";
            PreparedStatement stmt = con.prepareStatement(querry);
            stmt.setString(1, soDienThoai);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String maHoaDon = rs.getString("maHoaDon");
                java.sql.Timestamp timestamp = rs.getTimestamp("ngayLapHoaDon");
                LocalDateTime ngayLapHoaDon = null;
                if (timestamp != null) {
                    ngayLapHoaDon = timestamp.toLocalDateTime();
                }
                double tongTien = rs.getDouble("tongTien");
                HoaDon hd = new HoaDon(maHoaDon, null,ngayLapHoaDon, null, true, true, tongTien);
                listHD.add(hd);
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return listHD;    
    }

}

