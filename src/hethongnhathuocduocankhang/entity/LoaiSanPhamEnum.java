/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package hethongnhathuocduocankhang.entity;

/**
 *
 * @author MINH KHANG
 */
public enum LoaiSanPhamEnum {
    THUOC("Thuốc"), THUC_PHAM_CHUC_NANG("Thực phẩm chức năng");
    private final String loaiSanPham;
    private LoaiSanPhamEnum(String loaiSanPham) {
        this.loaiSanPham = loaiSanPham; 
    }
    
}
