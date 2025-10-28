/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.entity;

import java.time.LocalDate;

/**
 *
 * @author MINH KHANG
 */
public class LoSanPham {
     private String maLoSanPham;
    private SanPham sanPham;
    private int soLuong;
    private LocalDate ngaySanXuat;
    private LocalDate ngayHetHan;

    // --- Constructors ---
    public LoSanPham() {
    }

    public LoSanPham(String maLoSanPham, SanPham sanPham, int soLuong,
                     LocalDate ngaySanXuat, LocalDate ngayHetHan) {
        setMaLoSanPham(maLoSanPham);
        setSanPham(sanPham);
        setSoLuong(soLuong);
        setNgaySanXuat(ngaySanXuat);
        setNgayHetHan(ngayHetHan);
    }

    public LoSanPham(String maLo) {
        this.maLoSanPham = maLo;
    }
    
    public LoSanPham(LoSanPham lo) {
        this(lo.maLoSanPham, lo.sanPham, lo.soLuong, lo.ngaySanXuat, lo.ngayHetHan);
    }

    // --- Getter & Setter ---
    public String getMaLoSanPham() {
        return maLoSanPham;
    }

    public void setMaLoSanPham(String maLoSanPham) {
        if (maLoSanPham == null || maLoSanPham.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã lô sản phẩm không được rỗng và phải duy nhất.");
        }
        this.maLoSanPham = maLoSanPham.trim();
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        if (sanPham == null) {
            throw new IllegalArgumentException("Sản phẩm thuộc lô không được rỗng.");
        }
        this.sanPham = sanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        if (soLuong < 0) {
            throw new IllegalArgumentException("Số lượng trong lô phải lớn hơn hoặc bằng 0.");
        }
        this.soLuong = soLuong;
    }

    public LocalDate getNgaySanXuat() {
        return ngaySanXuat;
    }

    public void setNgaySanXuat(LocalDate ngaySanXuat) {
        if (ngaySanXuat == null) {
            throw new IllegalArgumentException("Ngày sản xuất không được rỗng.");
        }
        this.ngaySanXuat = ngaySanXuat;
    }

    public LocalDate getNgayHetHan() {
        return ngayHetHan;
    }

    public void setNgayHetHan(LocalDate ngayHetHan) {
        if (ngayHetHan == null || (ngaySanXuat != null && ngayHetHan.isBefore(ngaySanXuat))) {
            throw new IllegalArgumentException("Ngày hết hạn phải sau hoặc bằng ngày sản xuất.");
        }
        this.ngayHetHan = ngayHetHan;
    }

    @Override
    public String toString() {
        return "LoSanPham{" +
                "maLoSanPham='" + maLoSanPham + '\'' +
                ", sanPham=" + (sanPham != null ? sanPham.getMaSP() : "null") +
                ", soLuong=" + soLuong +
                ", ngaySanXuat=" + ngaySanXuat +
                ", ngayHetHan=" + ngayHetHan +
                '}';
    }
}
