/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PhieuDatHang {
    private String maPhieuDat;
    private NhaCungCap nhaCungCap;
    private LocalDateTime ngayLap;
    private NhanVien nhanVien;
    private double tongTien;

    public PhieuDatHang() {
    }

    public PhieuDatHang(String maPhieuDat, NhaCungCap nhaCungCap, LocalDateTime ngayLap, NhanVien nhanVien, double tongTien) {
        this.maPhieuDat = maPhieuDat;
        this.nhaCungCap = nhaCungCap;
        this.ngayLap = ngayLap;
        this.nhanVien = nhanVien;
        this.tongTien = tongTien;
    }

    public String getMaPhieuDat() {
        return maPhieuDat;
    }

    public NhaCungCap getNhaCungCap() {
        return nhaCungCap;
    }

    public LocalDateTime getNgayLap() {
        return ngayLap;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setMaPhieuDat(String maPhieuDat) throws Exception {
        if (maPhieuDat == null || !maPhieuDat.matches("^PDH-\\d{6}-\\d{4}$")) {
            throw new Exception("Mã PDH không hợp lệ! Ví dụ: PDH-300925-0001");
        }
        this.maPhieuDat = maPhieuDat;
    }

    public void setNhaCungCap(NhaCungCap nhaCungCap) throws Exception {
        if(nhaCungCap==null)
            throw new Exception("Nhà cung cấp không được rỗng");
        this.nhaCungCap = nhaCungCap;
    }

    public void setNgayLap(LocalDateTime ngayLap) throws Exception {
        if(ngayLap==null || ngayLap.isAfter(LocalDateTime.now()))
            throw new Exception("Ngày lập phải sau ngày hiện tại");
        this.ngayLap = ngayLap;
    }

    public void setNhanVien(NhanVien nhanVien) throws Exception{
        if(nhanVien==null) throw new Exception("Nhân viên không được rỗng");
        this.nhanVien = nhanVien;
    }

    public double setTongTien(double tongTien) {
        return 0;
        //return tongTien=+ SanPham.getGiaBan();
    }

    @Override
    public String toString() {
        return "PhieuDatHang{" + "maPhieuDat=" + maPhieuDat + ", nhaCungCap=" + nhaCungCap + ", ngayLap=" + ngayLap + ", nhanVien=" + nhanVien + ", tongTien=" + tongTien + '}';
    }
}
