/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 *
 * @author GIGABYTE
 */
public class HoaDon {

    private String maHoaDon;
    private NhanVien nhanVien;
    private LocalDateTime ngayLapHoaDon;
    private KhachHang khachHang;
    private boolean chuyenKhoan;
    private double tongTien;

    public HoaDon(String maHoaDon, NhanVien nhanVien, LocalDateTime ngayLapHoaDon, KhachHang khachHang, boolean chuyenKhoan, double tongTien) {
        this.maHoaDon = maHoaDon;
        this.nhanVien = nhanVien;
        this.ngayLapHoaDon = ngayLapHoaDon;
        this.khachHang = khachHang;
        this.chuyenKhoan = chuyenKhoan;
        this.tongTien = tongTien;
    }

    public HoaDon(HoaDon hoaDon) {
        this.maHoaDon = hoaDon.maHoaDon;
        this.nhanVien = hoaDon.nhanVien;
        this.ngayLapHoaDon = hoaDon.ngayLapHoaDon;
        this.khachHang = hoaDon.khachHang;
        this.chuyenKhoan = hoaDon.chuyenKhoan;
        this.tongTien = hoaDon.tongTien;
    }

    public HoaDon() {

    }

    public HoaDon(String maHD) {
        this.maHoaDon = maHD;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public LocalDateTime getNgayLapHoaDon() {
        return ngayLapHoaDon;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public double getTongTien() {
        return tongTien;
    }

    public boolean isChuyenKhoan() {
        return chuyenKhoan;
    }

//    public void setMaHoaDon(String maHoaDon){
//       if(maHoaDon == null) 
//           throw new IllegalArgumentException("Hóa đơn không được rỗng");
//        DateTimeFormatter =
//    }
//    Không được rỗng, không trùng, phải theo định dạng HD-[DD][MM][YY]-[XXXX]. 
//    Với [DD][MM][YY] là ngày tháng năm hiện tại, [X] là số nguyên từ 0–9.
    public void setNhanVien(NhanVien nhanVien) {
        if (nhanVien == null) {
            throw new IllegalArgumentException("Nhân viên không được rỗng");
        }
        this.nhanVien = nhanVien;
    }

    public void setNgayLapHoaDon(LocalDateTime ngayLapHoaDon) {
        if (ngayLapHoaDon == null) {
            throw new IllegalArgumentException("Ngày lập hóa đơn không được rỗng");
        }
        this.ngayLapHoaDon = ngayLapHoaDon;
    }

    public void setKhachHang(KhachHang khachHang) {
        if (khachHang == null) {
            throw new IllegalArgumentException("Khách hàng không được rỗng");
        }
        this.khachHang = khachHang;
    }

    public void setHinhThucThanhToan(boolean hinhThucThanhToan) {
        this.chuyenKhoan = hinhThucThanhToan;
    }

    public void setTongTien(double tongTien) {
        if (tongTien <= 0) {
            throw new IllegalArgumentException("Tổng tiền phải là số thực lớn hơn 0");
        }
        this.tongTien = tongTien;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.maHoaDon);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HoaDon other = (HoaDon) obj;
        return Objects.equals(this.maHoaDon, other.maHoaDon);
    }

    @Override
    public String toString() {
        return "HoaDon{" + "maHoaDon=" + maHoaDon + ", nhanVien=" + nhanVien + ", ngayLapHoaDon=" + ngayLapHoaDon + ", khachHang=" + khachHang + ", chuyenKhoan=" + chuyenKhoan + ", tongTien=" + tongTien + '}';
    }

}
