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
public class LichSuLo {

    private String maLichSuLo;
    private LoSanPham lo;
    private LocalDate thoiGian;
    private String hanhDong;
    private int soLuongSau;
    private String ghiChu;
    private NhanVien nv;

    public LichSuLo() {
    }

    public LichSuLo(String maLichSuLo){
        setMaLichSuLo(maLichSuLo);
    }

    public LichSuLo(String maLichSuLo, LoSanPham lo, LocalDate thoiGian, String hanhDong, int soLuongSau, String ghiChu, NhanVien nv){
        setMaLichSuLo(maLichSuLo);
        this.lo = lo;
        this.thoiGian = thoiGian;
        this.hanhDong = hanhDong;
        this.soLuongSau = soLuongSau;
        this.ghiChu = ghiChu;
        this.nv = nv;
    }

    public String getMaLichSuLo() {
        return maLichSuLo;
    }

    public LoSanPham getLo() {
        return lo;
    }

    public LocalDate getThoiGian() {
        return thoiGian;
    }

    public String getHanhDong() {
        return hanhDong;
    }

    public int getSoLuongSau() {
        return soLuongSau;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public NhanVien getNv() {
        return nv;
    }

    public void setMaLichSuLo(String maLichSuLo){
        if (maLichSuLo == null || maLichSuLo.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã lịch sử lô không được rỗng");
        }
        if (!maLichSuLo.matches("^LSL-\\d{4}$")) {
            throw new IllegalArgumentException("Mã lịch sử lô không hợp lệ. Phải theo định dạng LSL-XXXX với X là số nguyên từ 0-9.");
        }
        this.maLichSuLo = maLichSuLo;
    }

    public void setLo(LoSanPham lo) {
        this.lo = lo;
    }

    public void setThoiGian(LocalDate thoiGian) {
        this.thoiGian = thoiGian;
    }

    public void setHanhDong(String hanhDong) {
        this.hanhDong = hanhDong;
    }

    public void setSoLuongSau(int soLuongSau) {
        this.soLuongSau = soLuongSau;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public void setNv(NhanVien nv) {
        this.nv = nv;
    }

    @Override
    public String toString() {
        return "LichSuLo{" + "maLichSuLo=" + maLichSuLo + ", lo=" + lo + ", thoiGian=" + thoiGian + ", hanhDong=" + hanhDong + ", soLuongSau=" + soLuongSau + ", ghiChu=" + ghiChu + ", nv=" + nv + '}';
    }
}
