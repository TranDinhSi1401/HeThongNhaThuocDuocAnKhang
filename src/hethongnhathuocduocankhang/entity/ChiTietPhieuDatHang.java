/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.entity;

public class ChiTietPhieuDatHang {
    private SanPham sanPham;
    private int soLuong;
    private PhieuDatHang phieuDatHang;
    private double donGia;
    private double thanhTien;

    public ChiTietPhieuDatHang() {
    }

    public ChiTietPhieuDatHang(SanPham sanPham, int soLuong, PhieuDatHang phieuDatHang, double donGia, double thanhTien) {
        this.sanPham = sanPham;
        this.soLuong = soLuong;
        this.phieuDatHang = phieuDatHang;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public PhieuDatHang getPhieuDatHang() {
        return phieuDatHang;
    }

    public double getDonGia() {
        return donGia;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public void setPhieuDatHang(PhieuDatHang phieuDatHang) {
        this.phieuDatHang = phieuDatHang;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public double setThanhTien(double thanhTien) {
        return this.thanhTien = thanhTien;
    }

    @Override
    public String toString() {
        return "ChiTietPhieuDatHang{" + "sanPham=" + sanPham + ", soLuong=" + soLuong + ", phieuDatHang=" + phieuDatHang + ", donGia=" + donGia + ", thanhTien=" + thanhTien + '}';
    }
}
