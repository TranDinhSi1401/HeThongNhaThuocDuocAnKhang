/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package hethongnhathuocduocankhang.entity;

/**
 *
 * @author trand
 */
public enum LoaiKhuyenMaiEnum {
    SO_LUONG("Số lượng"), MUA("Mùa"), NHA_SAN_XUAT("Nhà sản xuất"), NGUNG_BAN("Ngừng bán");
    
    private final String loaiKhuyenMai;
    
    LoaiKhuyenMaiEnum(String loaiKhuyenMai) {
        this.loaiKhuyenMai = loaiKhuyenMai;
    }
    
    public String getLoaiKhuyenMai() {
        return loaiKhuyenMai;
    }
}
