/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.entity;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author trand
 */
public class KhuyenMaiSanPham {

    private KhuyenMai khuyenMai;
    private SanPham sanPham;
    private LocalDate ngayChinhSua;

    public KhuyenMaiSanPham() {
    }

    public KhuyenMaiSanPham(KhuyenMai khuyenMai, SanPham sanPham, LocalDate ngayChinhSua) {
        this.khuyenMai = khuyenMai;
        this.sanPham = sanPham;
        this.ngayChinhSua = ngayChinhSua;
    }

    public KhuyenMaiSanPham(KhuyenMaiSanPham other) {
        this.khuyenMai = other.khuyenMai;
        this.sanPham = other.sanPham;
        this.ngayChinhSua = other.ngayChinhSua;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.khuyenMai);
        hash = 97 * hash + Objects.hashCode(this.sanPham);
        hash = 97 * hash + Objects.hashCode(this.ngayChinhSua);
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
        final KhuyenMaiSanPham other = (KhuyenMaiSanPham) obj;
        if (!Objects.equals(this.khuyenMai, other.khuyenMai)) {
            return false;
        }
        if (!Objects.equals(this.sanPham, other.sanPham)) {
            return false;
        }
        return Objects.equals(this.ngayChinhSua, other.ngayChinhSua);
    }

    @Override
    public String toString() {
        return "KhuyenMaiSanPham{" + "khuyenMai=" + khuyenMai + ", sanPham=" + sanPham + ", ngayChinhSua=" + ngayChinhSua + '}';
    }

    public KhuyenMai getKhuyenMai() {
        return khuyenMai;
    }

    public void setKhuyenMai(KhuyenMai khuyenMai) {
        this.khuyenMai = khuyenMai;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public LocalDate getNgayChinhSua() {
        return ngayChinhSua;
    }

    public void setNgayChinhSua(LocalDate ngayChinhSua) {
        this.ngayChinhSua = ngayChinhSua;
    }

}
