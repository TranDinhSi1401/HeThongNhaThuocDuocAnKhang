/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.entity;

/**
 *
 * @author admin
 */
public class ChiTietPhieuNhap {
    private PhieuNhap maPhieuNhap;
    private LoSanPham maLoSanPham;
    private int soLuong;
    private double donGia;
    private double thanhTien;
    private String ghiChu;

    public ChiTietPhieuNhap() {
    }

    public ChiTietPhieuNhap(PhieuNhap maPhieuNhap, LoSanPham maLoSanPham) {
        this.maPhieuNhap = maPhieuNhap;
        this.maLoSanPham = maLoSanPham;
    }

    
    
    public ChiTietPhieuNhap(PhieuNhap maPhieuNhap, LoSanPham maLoSanPham, int soLuong, double donGia, double thanhTien, String ghiChu) {
        this.maPhieuNhap = maPhieuNhap;
        this.maLoSanPham = maLoSanPham;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
        this.ghiChu = ghiChu;
    }

    public PhieuNhap getMaPhieuNhap() {
        return maPhieuNhap;
    }

    public LoSanPham getMaLoSanPham() {
        return maLoSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setMaPhieuNhap(PhieuNhap maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }

    public void setMaLoSanPham(LoSanPham maLoSanPham) {
        this.maLoSanPham = maLoSanPham;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    @Override
    public String toString() {
        return "ChiTietPhieuNhap{" + "maPhieuNhap=" + maPhieuNhap + ", maLoSanPham=" + maLoSanPham + ", soLuong=" + soLuong + ", donGia=" + donGia + ", thanhTien=" + thanhTien + ", ghiChu=" + ghiChu + '}';
    }
    
    
}
