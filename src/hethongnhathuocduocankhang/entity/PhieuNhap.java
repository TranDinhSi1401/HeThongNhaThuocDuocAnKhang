/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.entity;

import java.time.LocalDate;

/**
 *
 * @author admin
 */
public class PhieuNhap {
    private String maPhieuNhap;
    private LocalDate ngayTao;
    private NhanVien nv;
    private NhaCungCap ncc;
    private double tongTien;
    private String ghiChu;

    public PhieuNhap() {
    }

    public PhieuNhap(String maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }

    public PhieuNhap(String maPhieuNhap, LocalDate ngayTao, NhanVien nv, NhaCungCap ncc, double tongTien, String ghiChu) {
        this.maPhieuNhap = maPhieuNhap;
        this.ngayTao = ngayTao;
        this.nv = nv;
        this.ncc = ncc;
        this.tongTien = tongTien;
        this.ghiChu = ghiChu;
    }

    public String getMaPhieuNhap() {
        return maPhieuNhap;
    }

    public LocalDate getNgayTao() {
        return ngayTao;
    }

    public NhanVien getNv() {
        return nv;
    }

    public NhaCungCap getNcc() {
        return ncc;
    }

    public double getTongTien() {
        return tongTien;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setMaPhieuNhap(String maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }

    public void setNgayTao(LocalDate ngayTao) {
        this.ngayTao = ngayTao;
    }

    public void setNv(NhanVien nv) {
        this.nv = nv;
    }

    public void setNcc(NhaCungCap ncc) {
        this.ncc = ncc;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    @Override
    public String toString() {
        return "PhieuNhap{" + "maPhieuNhap=" + maPhieuNhap + ", ngayTao=" + ngayTao + ", nv=" + nv + ", ncc=" + ncc + ", tongTien=" + tongTien + ", ghiChu=" + ghiChu + '}';
    }
    
    
}
