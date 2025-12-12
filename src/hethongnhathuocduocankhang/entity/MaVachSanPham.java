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
public class MaVachSanPham {

    private SanPham sanPham;
    private String maVach;

    public MaVachSanPham() {
    }
    
    public MaVachSanPham(SanPham sanPham, String maVach) {
        this.sanPham = sanPham;
        this.maVach = maVach;
    }

    public MaVachSanPham(MaVachSanPham other) {
        this.sanPham = other.sanPham;
        this.maVach = other.maVach;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public String getMaVach() {
        return maVach;
    }

    public void setMaVach(String maVach) {
        this.maVach = maVach;
    }

    @Override
    public String toString() {
        return "MaVachSanPham{" + "maSP=" + sanPham.getMaSP() + ", maVachSanPham=" + maVach + '}';
    }
}
