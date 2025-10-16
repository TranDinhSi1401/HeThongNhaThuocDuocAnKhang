/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package hethongnhathuocduocankhang.entity;

/**
 *
 * @author trand
 */
public enum TinhTrangSanPhamEnum {
    HANG_NGUYEN_VEN("Hàng nguyên vẹn"), HANG_KHONG_NGUYEN_VEN("Hàng không nguyên vẹn"), HANG_DA_SU_DUNG("Hàng đã sử dụng");
    
    private final String tinhTrangSanPham;
    
    TinhTrangSanPhamEnum(String tinhTrangSanPham) {
        this.tinhTrangSanPham = tinhTrangSanPham;
    }
    
    public String getTinhTrangSanPham() {
        return this.tinhTrangSanPham;
    }
}
