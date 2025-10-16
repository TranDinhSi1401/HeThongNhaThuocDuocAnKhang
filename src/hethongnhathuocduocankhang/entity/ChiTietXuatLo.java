/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.entity;

import java.util.regex.Pattern;

/**
 *
 * @author MINH KHANG
 */
public class ChiTietXuatLo {
    private LoSanPham loSanPham;
    private ChiTietHoaDon chiTietHoaDon;
    private int soLuong;

    // --- Constructors ---
    public ChiTietXuatLo() {
    }

    public ChiTietXuatLo(LoSanPham loSanPham, ChiTietHoaDon chiTietHoaDon, int soLuong) {
        setLoSanPham(loSanPham);
        setChiTietHoaDon(chiTietHoaDon);
        setSoLuong(soLuong);
    }

    public ChiTietXuatLo(ChiTietXuatLo ct) {
        this(ct.loSanPham, ct.chiTietHoaDon, ct.soLuong);
    }

    // --- Getter & Setter ---
    public LoSanPham getLoSanPham() {
        return loSanPham;
    }

    public void setLoSanPham(LoSanPham loSanPham) {
        if (loSanPham == null) {
            throw new IllegalArgumentException("Lô sản phẩm không được rỗng.");
        }
        this.loSanPham = loSanPham;
    }

    public ChiTietHoaDon getChiTietHoaDon() {
        return chiTietHoaDon;
    }

    public void setChiTietHoaDon(ChiTietHoaDon chiTietHoaDon) {
        if (chiTietHoaDon == null) {
            throw new IllegalArgumentException("Chi tiết hóa đơn không được rỗng.");
        }
        this.chiTietHoaDon = chiTietHoaDon;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        if (soLuong <= 0) {
            throw new IllegalArgumentException("Số lượng xuất phải lớn hơn 0.");
        }
        this.soLuong = soLuong;
    }

    @Override
    public String toString() {
        return "ChiTietXuatLo{" +
                "loSanPham=" + (loSanPham != null ? loSanPham.getMaLoSanPham() : "null") +
                ", chiTietHoaDon=" + (chiTietHoaDon != null ? chiTietHoaDon.getMaChiTietHoaDon() : "null") +
                ", soLuong=" + soLuong +
                '}';
    }
}
//⚠️ Ghi chú:
//Lớp ChiTietHoaDon bạn cần định nghĩa sẵn, ít nhất có phương thức getMaChiTiet() để tránh lỗi khi gọi toString().
//
//🧩 3️⃣ Lớp DonViTinh.java
//java
//Copy code
//package entity;
//
//import java.util.regex.Pattern;

