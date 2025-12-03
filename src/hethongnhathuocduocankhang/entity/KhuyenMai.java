package hethongnhathuocduocankhang.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author trand
 */
public class KhuyenMai {

    private String maKhuyenMai;
    private String moTa;
    private double phanTram;
    private LoaiKhuyenMaiEnum loaiKhuyenMai;
    private LocalDateTime ngayBatDau;
    private LocalDateTime ngayKetThuc;
    private int soLuongToiThieu;
    private int soLuongToiDa;
    private LocalDateTime ngayChinhSua;

    public KhuyenMai() {
    }

    public KhuyenMai(String maKhuyenMai, double phanTram, int soLuongToiThieu, int soLuongToiDa) {
        this.maKhuyenMai = maKhuyenMai;
        this.phanTram = phanTram;
        this.soLuongToiThieu = soLuongToiThieu;
        this.soLuongToiDa = soLuongToiDa;
    }

    public KhuyenMai(String maKhuyenMai, String moTa, double phanTram, LoaiKhuyenMaiEnum loaiKhuyenMai, LocalDateTime ngayBatDau, LocalDateTime ngayKetThuc, int soLuongToiThieu, int soLuongToiDa, LocalDateTime ngayChinhSua) {
        this.maKhuyenMai = maKhuyenMai;
        this.moTa = moTa;
        this.phanTram = phanTram;
        this.loaiKhuyenMai = loaiKhuyenMai;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.soLuongToiThieu = soLuongToiThieu;
        this.soLuongToiDa = soLuongToiDa;
        this.ngayChinhSua = ngayChinhSua;
    }

    public KhuyenMai(KhuyenMai other) {
        this.maKhuyenMai = other.maKhuyenMai;
        this.moTa = other.moTa;
        this.phanTram = other.phanTram;
        this.loaiKhuyenMai = other.loaiKhuyenMai;
        this.ngayBatDau = other.ngayBatDau;
        this.ngayKetThuc = other.ngayKetThuc;
        this.soLuongToiThieu = other.soLuongToiThieu;
        this.soLuongToiDa = other.soLuongToiDa;
        this.ngayChinhSua = other.ngayChinhSua;
    }

    public String getMaKhuyenMai() {
        return maKhuyenMai;
    }

    public void setMaKhuyenMai(String maKhuyenMai) {

        this.maKhuyenMai = maKhuyenMai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public double getPhanTram() {
        return phanTram;
    }

    public void setPhanTram(double phanTram) {
        this.phanTram = phanTram;
    }

    public LoaiKhuyenMaiEnum getLoaiKhuyenMai() {
        return loaiKhuyenMai;
    }

    public void setLoaiKhuyenMai(LoaiKhuyenMaiEnum loaiKhuyenMai) {
        this.loaiKhuyenMai = loaiKhuyenMai;
    }

    public LocalDateTime getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(LocalDateTime ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public LocalDateTime getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(LocalDateTime ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public int getSoLuongToiThieu() {
        return soLuongToiThieu;
    }

    public void setSoLuongToiThieu(int soLuongToiThieu) {
        this.soLuongToiThieu = soLuongToiThieu;
    }

    public int getSoLuongToiDa() {
        return soLuongToiDa;
    }

    public void setSoLuongToiDa(int soLuongToiDa) {
        this.soLuongToiDa = soLuongToiDa;
    }

    public LocalDateTime getNgayChinhSua() {
        return ngayChinhSua;
    }

    public void setNgayChinhSua(LocalDateTime ngayChinhSua) {
        this.ngayChinhSua = ngayChinhSua;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.maKhuyenMai);
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
        final KhuyenMai other = (KhuyenMai) obj;
        return Objects.equals(this.maKhuyenMai, other.maKhuyenMai);
    }

    @Override
    public String toString() {
        return "KhuyenMai{" + "maKhuyenMai=" + maKhuyenMai + ", moTa=" + moTa + ", phanTram=" + phanTram + ", loaiKhuyenMai=" + loaiKhuyenMai + ", ngayBatDau=" + ngayBatDau + ", ngayKetThuc=" + ngayKetThuc + ", soLuongToiThieu=" + soLuongToiThieu + ", soLuongToiDa=" + soLuongToiDa + ", ngayChinhSua=" + ngayChinhSua + '}';
    }

}
