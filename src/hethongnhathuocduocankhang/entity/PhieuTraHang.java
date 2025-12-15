/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class PhieuTraHang {
    private String maPhieuTraHang;
    private LocalDateTime ngayLapPhieuTraHang;
    private double tongTienHoanTra;
    private NhanVien nhanVien;
    private HoaDon hoaDon;

    public PhieuTraHang() {
    }

    public PhieuTraHang(String maPhieuTraHang, LocalDateTime ngayLapPhieuTraHang, double tongTienHoanTra, NhanVien nhanVien, HoaDon hoaDon) {
        this.maPhieuTraHang = maPhieuTraHang;
        this.ngayLapPhieuTraHang = ngayLapPhieuTraHang;
        this.tongTienHoanTra = tongTienHoanTra;
        this.nhanVien = nhanVien;
        this.hoaDon = hoaDon;
    }
    
    public PhieuTraHang(PhieuTraHang other) {
        this.maPhieuTraHang = other.maPhieuTraHang;
        this.ngayLapPhieuTraHang = other.ngayLapPhieuTraHang;
        this.tongTienHoanTra = other.tongTienHoanTra;
        this.nhanVien = other.nhanVien;
        this.hoaDon = other.hoaDon;
    }

    public String getMaPhieuTraHang() {
        return maPhieuTraHang;
    }

    public void setMaPhieuTraHang(String maPhieuTraHang) {
        this.maPhieuTraHang = maPhieuTraHang;
    }

    public LocalDateTime getNgayLapPhieuTraHang() {
        return ngayLapPhieuTraHang;
    }

    public void setNgayLapPhieuTraHang(LocalDateTime ngayLapPhieuTraHang) {
        this.ngayLapPhieuTraHang = ngayLapPhieuTraHang;
    }

    public double getTongTienHoanTra() {
        return tongTienHoanTra;
    }

    public void setTongTienHoanTra(double tongTienHoanTra) {
        this.tongTienHoanTra = tongTienHoanTra;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.maPhieuTraHang);
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
        final PhieuTraHang other = (PhieuTraHang) obj;
        return Objects.equals(this.maPhieuTraHang, other.maPhieuTraHang);
    }

    @Override
    public String toString() {
        return "PhieuTraHang{" + "maPhieuTraHang=" + maPhieuTraHang + ", ngayLapPhieuTraHang=" + ngayLapPhieuTraHang + ", tongTienHoanTra=" + tongTienHoanTra + ", nhanVien=" + nhanVien + ", hoaDon=" + hoaDon + '}';
    }
}
