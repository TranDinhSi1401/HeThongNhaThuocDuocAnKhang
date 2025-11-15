/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.entity;

import java.util.Objects;

/**
 *
 * @author GIGABYTE
 */
public class ChiTietHoaDon {

    private String maChiTietHoaDon;
    private HoaDon hoaDon;
    private DonViTinh donViTinh;
    private int soLuong;
    private double donGia;
    private double giamGia;
    private double thanhTien;

    public ChiTietHoaDon(String maChiTietHoaDon, HoaDon hoaDon, DonViTinh donViTinh, int soLuong, double donGia, double giamGia, double thanhTien) {
        this.maChiTietHoaDon = maChiTietHoaDon;
        this.hoaDon = hoaDon;
        this.donViTinh = donViTinh;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.giamGia = giamGia;
        this.thanhTien = thanhTien;
    }

    public ChiTietHoaDon(double donGia) {
        this.donGia = donGia;
    }
    
    public ChiTietHoaDon(String maCTHD) {
        this.maChiTietHoaDon = maCTHD;
    }

    public String getMaChiTietHoaDon() {
        return maChiTietHoaDon;
    }

    public void setMaChiTietHoaDon(String maChiTietHoaDon) {
        this.maChiTietHoaDon = maChiTietHoaDon;
    }

    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }

    public DonViTinh getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(DonViTinh donViTinh) {
        this.donViTinh = donViTinh;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public double getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(double giamGia) {
        this.giamGia = giamGia;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.maChiTietHoaDon);
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
        final ChiTietHoaDon other = (ChiTietHoaDon) obj;
        return Objects.equals(this.maChiTietHoaDon, other.maChiTietHoaDon);
    }

    @Override
    public String toString() {
        return "ChiTietHoaDon{" + "maChiTietHoaDon=" + maChiTietHoaDon + ", hoaDon=" + hoaDon + ", donViTinh=" + donViTinh + ", soLuong=" + soLuong + ", donGia=" + donGia + ", giamGia=" + giamGia + ", thanhTien=" + thanhTien + '}';
    }

}
