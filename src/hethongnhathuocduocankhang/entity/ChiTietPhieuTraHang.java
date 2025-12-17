/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.entity;

import java.util.Objects;

public class ChiTietPhieuTraHang {
    private PhieuTraHang phieuTraHang;
    private ChiTietHoaDon chiTietHoaDon;
    private int soLuong;
    private TruongHopDoiTraEnum truongHopDoiTra;
    private TinhTrangSanPhamEnum tinhTrangSanPham;
    private String giaTriHoanTra;
    private double thanhTienHoanTra;

    public ChiTietPhieuTraHang() {
    }

    public ChiTietPhieuTraHang(PhieuTraHang phieuTraHang, ChiTietHoaDon chiTietHoaDon, int soLuong, TruongHopDoiTraEnum truongHopDoiTra, TinhTrangSanPhamEnum tinhTrangSanPham, String giaTriHoanTra, double thanhTienHoanTra) {
        this.phieuTraHang = phieuTraHang;
        this.chiTietHoaDon = chiTietHoaDon;
        this.soLuong = soLuong;
        this.truongHopDoiTra = truongHopDoiTra;
        this.tinhTrangSanPham = tinhTrangSanPham;
        this.giaTriHoanTra = giaTriHoanTra;
        this.thanhTienHoanTra = thanhTienHoanTra;
    }
    
    public ChiTietPhieuTraHang(ChiTietPhieuTraHang other) {
        this.phieuTraHang = other.phieuTraHang;
        this.chiTietHoaDon = other.chiTietHoaDon;
        this.soLuong = other.soLuong;
        this.truongHopDoiTra = other.truongHopDoiTra;
        this.tinhTrangSanPham = other.tinhTrangSanPham;
        this.giaTriHoanTra = other.giaTriHoanTra;
        this.thanhTienHoanTra = other.thanhTienHoanTra;
    }

    public PhieuTraHang getPhieuTraHang() {
        return phieuTraHang;
    }

    public void setPhieuTraHang(PhieuTraHang phieuTraHang) {
        this.phieuTraHang = phieuTraHang;
    }

    public ChiTietHoaDon getChiTietHoaDon() {
        return chiTietHoaDon;
    }

    public void setChiTietHoaDon(ChiTietHoaDon chiTietHoaDon) {
        this.chiTietHoaDon = chiTietHoaDon;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public TruongHopDoiTraEnum getTruongHopDoiTra() {
        return truongHopDoiTra;
    }

    public void setTruongHopDoiTra(TruongHopDoiTraEnum truongHopDoiTra) {
        this.truongHopDoiTra = truongHopDoiTra;
    }

    public TinhTrangSanPhamEnum getTinhTrangSanPham() {
        return tinhTrangSanPham;
    }

    public void setTinhTrangSanPham(TinhTrangSanPhamEnum tinhTrangSanPham) {
        this.tinhTrangSanPham = tinhTrangSanPham;
    }

    public String getGiaTriHoanTra() {
        return giaTriHoanTra;
    }

    public void setGiaTriHoanTra(String giaTriHoanTra) {
        this.giaTriHoanTra = giaTriHoanTra;
    }

    public double getThanhTienHoanTra() {
        return thanhTienHoanTra;
    }

    public void setThanhTienHoanTra(double thanhTienHoanTra) {
        this.thanhTienHoanTra = thanhTienHoanTra;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.phieuTraHang);
        hash = 71 * hash + Objects.hashCode(this.chiTietHoaDon);
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
        final ChiTietPhieuTraHang other = (ChiTietPhieuTraHang) obj;
        if (!Objects.equals(this.phieuTraHang, other.phieuTraHang)) {
            return false;
        }
        return Objects.equals(this.chiTietHoaDon, other.chiTietHoaDon);
    }

    @Override
    public String toString() {
        return "ChiTietPhieuTraHang{" + "phieuTraHang=" + phieuTraHang + ", chiTietHoaDon=" + chiTietHoaDon + ", soLuong=" + soLuong + ", truongHopDoiTra=" + truongHopDoiTra + ", tinhTrangSanPham=" + tinhTrangSanPham + ", giaTriHoanTra=" + giaTriHoanTra + ", thanhTienHoanTra=" + thanhTienHoanTra + '}';
    }
    
}
