/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.entity;

/**
 *
 * @author admin
 */
public class SanPhamCungCap {
    private SanPham sanPham;
    private NhaCungCap nhaCungCap;
    private boolean trangThaiHopTac;
    private double giaNhap;

    public SanPhamCungCap() {
    }

    public SanPhamCungCap(SanPham sanPham, NhaCungCap nhaCungCap, boolean trangThaiHopTac, double giaNhap) {
        this.sanPham = sanPham;
        this.nhaCungCap = nhaCungCap;
        this.trangThaiHopTac = trangThaiHopTac;
        this.giaNhap = giaNhap;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public NhaCungCap getNhaCungCap() {
        return nhaCungCap;
    }

    public boolean isTrangThaiHopTac() {
        return trangThaiHopTac;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public void setSanPham(SanPham sanPham) throws Exception {
        if(sanPham==null) throw new Exception("Sản phẩm không được rỗng");
        this.sanPham = sanPham;
    }

    public void setNhaCungCap(NhaCungCap nhaCungCap) throws Exception {
        if(nhaCungCap==null) throw new Exception("Nhà cung cấp không được rỗng");
        this.nhaCungCap = nhaCungCap;
    }

    public void setTrangThaiHopTac(boolean trangThaiHopTac) {
        this.trangThaiHopTac = trangThaiHopTac;
    }

    public void setGiaNhap(double giaNhap) throws Exception {
        if(giaNhap<0) throw new Exception("Giá nhập không được nhỏ hơn 0");
        this.giaNhap = giaNhap;
    }

    @Override
    public String toString() {
        return "SanPhamCungCap{" + "sanPham=" + sanPham + ", nhaCungCap=" + nhaCungCap + ", trangThaiHopTac=" + trangThaiHopTac + ", giaNhap=" + giaNhap + '}';
    }
    
    
}
