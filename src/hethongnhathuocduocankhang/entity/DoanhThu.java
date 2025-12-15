/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.entity;

/**
 *
 * @author trand
 */
public class DoanhThu {
    private String thoiGian;
    private int tongHoaDon;
    private double tongDoanhThu;

    public DoanhThu(String thoiGian, int tongHoaDon, double tongDoanhThu) {
        this.thoiGian = thoiGian;
        this.tongHoaDon = tongHoaDon;
        this.tongDoanhThu = tongDoanhThu;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public int getTongHoaDon() {
        return tongHoaDon;
    }

    public void setTongHoaDon(int tongHoaDon) {
        this.tongHoaDon = tongHoaDon;
    }

    public double getTongDoanhThu() {
        return tongDoanhThu;
    }

    public void setTongDoanhThu(double tongDoanhThu) {
        this.tongDoanhThu = tongDoanhThu;
    }

    @Override
    public String toString() {
        return "DoanhThu{" + "thoiGian=" + thoiGian + ", tongHoaDon=" + tongHoaDon + ", tongDoanhThu=" + tongDoanhThu + '}';
    }
}
