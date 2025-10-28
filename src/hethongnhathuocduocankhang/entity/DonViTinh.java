/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.entity;

import java.util.regex.Pattern;

/**
 *
 * @author MINH KHANG
 */
    public class DonViTinh {
        private String maDonViTinh;
        private SanPham sanPham;
        private int heSoQuyDoi;
        private double giaBanTheoDonVi;
        private String tenDonVi;
        private boolean donViTinhCoBan;

    // --- Constructors ---
    public DonViTinh() {
    }

    public DonViTinh(String maDonViTinh, SanPham sanPham, int heSoQuyDoi,
                     double giaBanTheoDonVi, String tenDonVi, boolean donViTinhCoBan) {
        setMaDonViTinh(maDonViTinh);
        setSanPham(sanPham);
        setHeSoQuyDoi(heSoQuyDoi);
        setGiaBanTheoDonVi(giaBanTheoDonVi);
        setTenDonVi(tenDonVi);
        this.donViTinhCoBan = donViTinhCoBan;
    }

    public DonViTinh(DonViTinh dvt) {
        this(dvt.maDonViTinh, dvt.sanPham, dvt.heSoQuyDoi,
             dvt.giaBanTheoDonVi, dvt.tenDonVi, dvt.donViTinhCoBan);
    }

    // --- Getter & Setter ---
    public String getMaDonViTinh() {
        return maDonViTinh;
    }

    public DonViTinh(String maDonViTinh, SanPham sanPham, double giaBanTheoDonVi, String tenDonVi) {
        this.maDonViTinh = maDonViTinh;
        this.sanPham = sanPham;
        this.giaBanTheoDonVi = giaBanTheoDonVi;
        this.tenDonVi = tenDonVi;
    }
    

    public void setMaDonViTinh(String maDonViTinh) {
        if (maDonViTinh == null || !Pattern.matches("^DVT-\\d{4}-[A-Z]+$", maDonViTinh)) {
            throw new IllegalArgumentException("Mã đơn vị tính phải theo định dạng DVT-[xxxx]-[ĐƠN_VỊ]. Ví dụ: DVT-0001-HOP");
        }
        this.maDonViTinh = maDonViTinh;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        if (sanPham == null) {
            throw new IllegalArgumentException("Sản phẩm trong đơn vị tính không được rỗng.");
        }
        this.sanPham = sanPham;
    }

    public int getHeSoQuyDoi() {
        return heSoQuyDoi;
    }

    public void setHeSoQuyDoi(int heSoQuyDoi) {
        if (heSoQuyDoi <= 0) {
            throw new IllegalArgumentException("Hệ số quy đổi phải lớn hơn 0.");
        }
        this.heSoQuyDoi = heSoQuyDoi;
    }

    public double getGiaBanTheoDonVi() {
        return giaBanTheoDonVi;
    }

    public void setGiaBanTheoDonVi(double giaBanTheoDonVi) {
        if (giaBanTheoDonVi < 0 || giaBanTheoDonVi > 20_000_000) {
            throw new IllegalArgumentException("Giá bán theo đơn vị phải nằm trong khoảng 0 - 20,000,000 VND.");
        }
        this.giaBanTheoDonVi = giaBanTheoDonVi;
    }

    public String getTenDonVi() {
        return tenDonVi;
    }

    public void setTenDonVi(String tenDonVi) {
        if (tenDonVi == null || tenDonVi.trim().isEmpty() || tenDonVi.length() > 30) {
            throw new IllegalArgumentException("Tên đơn vị không được rỗng và tối đa 30 ký tự.");
        }
        this.tenDonVi = tenDonVi.trim();
    }

    public boolean isDonViTinhCoBan() {
        return donViTinhCoBan;
    }

    public void setDonViTinhCoBan(boolean donViTinhCoBan) {
        this.donViTinhCoBan = donViTinhCoBan;
    }

    @Override
    public String toString() {
        return "DonViTinh{" +
                "maDonViTinh='" + maDonViTinh + '\'' +
                ", sanPham=" + (sanPham != null ? sanPham.getMaSP() : "null") +
                ", heSoQuyDoi=" + heSoQuyDoi +
                ", giaBanTheoDonVi=" + giaBanTheoDonVi +
                ", tenDonVi='" + tenDonVi + '\'' +
                ", donViTinhCoBan=" + donViTinhCoBan +
                '}';
    }
}
