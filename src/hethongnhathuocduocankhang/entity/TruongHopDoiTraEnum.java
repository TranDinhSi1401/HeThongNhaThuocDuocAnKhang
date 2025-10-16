/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package hethongnhathuocduocankhang.entity;

/**
 *
 * @author trand
 */
public enum TruongHopDoiTraEnum {
    HANG_LOI_DO_NHA_SAN_XUAT("Hàng lỗi do nhà sản xuất"), DI_UNG_MAN_CAM("Dị ứng mẫn cảm"), NHU_CAU_KHACH_HANG("Nhu cầu khách hàng");
    
    private final String truongHopDoiTra;
    
    TruongHopDoiTraEnum(String truongHopDoiTra) {
        this.truongHopDoiTra = truongHopDoiTra;
    }
    
    public String getTruongHopDoiTra() {
        return this.truongHopDoiTra;
    }
}
