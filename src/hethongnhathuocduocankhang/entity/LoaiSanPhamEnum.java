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
    THUOC_KE_DON("Thuốc kê đơn"), THUC_PHAM_CHUC_NANG("Thực phẩm chức năng"), THUOC_KHONG_KE_DON("Thuốc không kê đơn");
    private final String loaiSanPham;
    private LoaiSanPhamEnum(String loaiSanPham) {
        this.loaiSanPham = loaiSanPham; 
    }
    
}
