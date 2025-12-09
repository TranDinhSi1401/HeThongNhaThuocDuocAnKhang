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

    // 1. Khai báo thuộc tính (Bao gồm cả mới và cũ)
    private String maLoSanPham;
    private SanPham sanPham;
    private int soLuong;
    private LocalDate ngaySanXuat;
    private LocalDate ngayHetHan;
    private boolean daHuy;
    

    // --- Constructors ---

    public LoSanPham(String maLoSanPham, SanPham sanPham, int soLuong, LocalDate ngaySanXuat, LocalDate ngayHetHan, boolean daHuy) {
        this.maLoSanPham = maLoSanPham;
        this.sanPham = sanPham;
        this.soLuong = soLuong;
        this.ngaySanXuat = ngaySanXuat;
        this.ngayHetHan = ngayHetHan;
        this.daHuy = daHuy;
    }

    // 3. Constructor cũ (Chỉ có thông tin cơ bản) - GIỮ NGUYÊN
    public LoSanPham(String maLoSanPham, SanPham sanPham, int soLuong, LocalDate ngaySanXuat, LocalDate ngayHetHan) {
        setMaLoSanPham(maLoSanPham);
        setSanPham(sanPham);
        setSoLuong(soLuong);
        setNgaySanXuat(ngaySanXuat);
        setNgayHetHan(ngayHetHan);
        this.daHuy = false; // Mặc định false
    }

    // 4. Constructor cũ (Chỉ có mã lô) - GIỮ NGUYÊN
    public LoSanPham(String maLo) {
        setMaLoSanPham(maLo);
    }

    public LoSanPham() {
    }
    
    
   
    // 6. Copy constructor - CẬP NHẬT THÊM isDaHuy
    public LoSanPham(LoSanPham lo) {
        this.maLoSanPham = lo.maLoSanPham;
        this.sanPham = lo.sanPham;
        this.soLuong = lo.soLuong;
        this.ngaySanXuat = lo.ngaySanXuat;
        this.ngayHetHan = lo.ngayHetHan;
        this.daHuy = lo.daHuy;
    }

    // --- Getter & Setter (Đã cập nhật Validation theo đặc tả) ---
    
    public String getMaLoSanPham() {
        return maLoSanPham;
    }

    public void setMaLoSanPham(String maLoSanPham) {
        if (maLoSanPham == null || maLoSanPham.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã lô sản phẩm không được rỗng.");
        }
        this.maLoSanPham = maLoSanPham.trim();
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        if (sanPham == null) {
            throw new IllegalArgumentException("Sản phẩm thuộc lô này không được rỗng.");
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
        if (this.ngayHetHan != null && ngaySanXuat.isAfter(this.ngayHetHan)) {
             throw new IllegalArgumentException("Ngày sản xuất không được sau ngày hết hạn.");
        }
        this.ngaySanXuat = ngaySanXuat;
    }

    public LocalDate getNgayHetHan() {
        return ngayHetHan;
    }
    
    public void setNgayHetHan(LocalDate ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }
    
    public boolean isDaHuy() {
        return daHuy;
    }

    public void setDaHuy(boolean daHuy) {
        this.daHuy = daHuy;
    }
    
    // --- toString ---
    @Override
    public String toString() {
        return "LoSanPham{" + "maLoSanPham=" + maLoSanPham + ", sanPham=" + sanPham + ", soLuong=" + soLuong + ", ngaySanXuat=" + ngaySanXuat + ", ngayHetHan=" + ngayHetHan + ", trangThai=" + daHuy + '}';
    }

    
}
