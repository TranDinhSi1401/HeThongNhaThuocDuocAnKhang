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
    private boolean isDaHuy; // Thuộc tính mới theo đặc tả

    // Các thuộc tính cũ (Giữ lại để hỗ trợ constructor cũ)
    private NhaCungCap ncc;
    private ChiTietHoaDon ctthd;
    private DonViTinh dvTinh;

    // --- Constructors ---
    
    // 1. Constructor mặc định
    public LoSanPham() {
        this.isDaHuy = false; // Mặc định là false theo đặc tả
    }

    // 2. Constructor cũ (Đầy đủ các trường cũ) - GIỮ NGUYÊN
    public LoSanPham(String maLoSanPham, SanPham sanPham, int soLuong, LocalDate ngaySanXuat, LocalDate ngayHetHan, NhaCungCap ncc, ChiTietHoaDon ctthd, DonViTinh dvTinh) {
        setMaLoSanPham(maLoSanPham);
        setSanPham(sanPham);
        setSoLuong(soLuong);
        setNgaySanXuat(ngaySanXuat);
        setNgayHetHan(ngayHetHan);
        
        this.ncc = ncc;
        this.ctthd = ctthd;
        this.dvTinh = dvTinh;
        this.isDaHuy = false; // Mặc định false cho constructor cũ
    }

    // 3. Constructor cũ (Chỉ có thông tin cơ bản) - GIỮ NGUYÊN
    public LoSanPham(String maLoSanPham, SanPham sanPham, int soLuong, LocalDate ngaySanXuat, LocalDate ngayHetHan) {
        setMaLoSanPham(maLoSanPham);
        setSanPham(sanPham);
        setSoLuong(soLuong);
        setNgaySanXuat(ngaySanXuat);
        setNgayHetHan(ngayHetHan);
        this.isDaHuy = false; // Mặc định false
    }

    // 4. Constructor cũ (Chỉ có mã lô) - GIỮ NGUYÊN
    public LoSanPham(String maLo) {
        setMaLoSanPham(maLo);
    }
    
    // 5. Constructor mới (Theo bảng đặc tả 1.1 có isDaHuy)
    public LoSanPham(String maLoSanPham, SanPham sanPham, int soLuong, LocalDate ngaySanXuat, LocalDate ngayHetHan, boolean isDaHuy) {
        setMaLoSanPham(maLoSanPham);
        setSanPham(sanPham);
        setSoLuong(soLuong);
        setNgaySanXuat(ngaySanXuat);
        setNgayHetHan(ngayHetHan);
        setDaHuy(isDaHuy);
    }

    // 6. Copy constructor - CẬP NHẬT THÊM isDaHuy
    public LoSanPham(LoSanPham lo) {
        this.maLoSanPham = lo.maLoSanPham;
        this.sanPham = lo.sanPham;
        this.soLuong = lo.soLuong;
        this.ngaySanXuat = lo.ngaySanXuat;
        this.ngayHetHan = lo.ngayHetHan;
        this.isDaHuy = lo.isDaHuy;
        
        // Copy các trường cũ
        this.ncc = lo.ncc;
        this.ctthd = lo.ctthd;
        this.dvTinh = lo.dvTinh;
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
        if (ngayHetHan == null) {
             throw new IllegalArgumentException("Ngày hết hạn không được rỗng."); 
        }
        if (this.ngaySanXuat != null && ngayHetHan.isBefore(this.ngaySanXuat)) {
            throw new IllegalArgumentException("Ngày hết hạn phải sau hoặc bằng ngày sản xuất.");
        }
        this.ngayHetHan = ngayHetHan;
    }

    // Getter/Setter mới cho isDaHuy
    public boolean isDaHuy() {
        return isDaHuy;
    }

    public void setDaHuy(boolean isDaHuy) {
        this.isDaHuy = isDaHuy;
    }

    // --- Getter & Setter cho các trường cũ (Giữ lại để code cũ không lỗi) ---
    public NhaCungCap getNcc() {
        return ncc;
    }

    public void setNcc(NhaCungCap ncc) {
        this.ncc = ncc;
    }

    public ChiTietHoaDon getCtthd() {
        return ctthd;
    }

    public void setCtthd(ChiTietHoaDon ctthd) {
        this.ctthd = ctthd;
    }

    public DonViTinh getDvTinh() {
        return dvTinh;
    }

    public void setDvTinh(DonViTinh dvTinh) {
        this.dvTinh = dvTinh;
    }
    
    // --- toString ---
    @Override
    public String toString() {
        return "LoSanPham{" +
                "maLoSanPham='" + maLoSanPham + '\'' +
                ", sanPham=" + (sanPham != null ? sanPham.getMaSP() : "null") +
                ", soLuong=" + soLuong +
                ", ngaySanXuat=" + ngaySanXuat +
                ", ngayHetHan=" + ngayHetHan +
                ", isDaHuy=" + isDaHuy +
                '}';
    }
}