/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.entity;

/**
 *
 * @author admin
 */
public class MaVachSanPham {
    private String maSP;
    private String maVachSanPham;

    public MaVachSanPham() {
    }

    public MaVachSanPham(String maSP, String maVachSanPham) {
        this.maSP = maSP;
        this.maVachSanPham = maVachSanPham;
    }

    public String getMaSP() {
        return maSP;
    }

    public String getMaVachSanPham() {
        return maVachSanPham;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public void setMaVachSanPham(String maVachSanPham) {
        this.maVachSanPham = maVachSanPham;
    }

    @Override
    public String toString() {
        return "MaVachSanPham{" + "maSP=" + maSP + ", maVachSanPham=" + maVachSanPham + '}';
    }
}
