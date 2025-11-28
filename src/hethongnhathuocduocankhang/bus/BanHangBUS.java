/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.bus;

import hethongnhathuocduocankhang.dao.DonViTinhDAO;
import hethongnhathuocduocankhang.dao.KhachHangDAO;
import hethongnhathuocduocankhang.dao.KhuyenMaiDAO;
import hethongnhathuocduocankhang.dao.LoSanPhamDAO;
import hethongnhathuocduocankhang.dao.SanPhamDAO;
import hethongnhathuocduocankhang.entity.DonViTinh;
import hethongnhathuocduocankhang.entity.KhachHang;
import hethongnhathuocduocankhang.entity.KhuyenMai;
import hethongnhathuocduocankhang.entity.LoSanPham;
import hethongnhathuocduocankhang.entity.SanPham;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 *
 * @author trand
 */
public class BanHangBUS {
    
    public String chuanHoaMaSP(String input) {
        if (input == null) return "";
        input = input.trim().toUpperCase();
        // Nếu dạng chuẩn rồi thì giữ nguyên
        if (input.matches("^SP-\\d{4}$")) {
            return input;
        }
        // Nếu nhân viên nhập sp0001 
        if (input.matches("^SP\\d{4}$")) {
            return input.substring(0, 2) + "-" + input.substring(2);
        }
        // Nếu nhân viên chỉ nhập số
        if (input.matches("^\\d{4}$")) {
            return "SP-" + input;
        }
        // Nếu nhân viên quét mã sản phẩm
        if (input.matches("^d{10}$")) {
            return "";
        }
        return input;
    }
    
    public Object[] themChiTietHoaDon(String maSp) throws Exception{
        // Lấy các thông tin liên quan đến mã sp
        String maSP = chuanHoaMaSP(maSp);
        SanPham sp = SanPhamDAO.timSPTheoMa(maSP);
        ArrayList<DonViTinh> dsDVT = DonViTinhDAO.getDonViTinhTheoMaSP(maSP);
        ArrayList<KhuyenMai> dsKM = KhuyenMaiDAO.getKhuyenMaiTheoMaSP(maSP);
        ArrayList<LoSanPham> dsLSP = LoSanPhamDAO.getLoSanPhamTheoMaSP(maSP);
        int tongSoLuong = dsLSP.stream().mapToInt(LoSanPham :: getSoLuong).sum();
        
        // Bắt lỗi các trường hợp có thể xảy ra
        if (sp == null) {
            throw new Exception("Sản phẩm không tồn tại!");
        }        
        if (dsDVT == null || dsDVT.isEmpty()) {
            throw new Exception("Sản phẩm này chưa có đơn vị tính!");
        }
        if(tongSoLuong <= 0) {
            throw new Exception("Sản phẩm hiện tại hết hàng!");
        }
        
        // Tạo thông tin chi tiết hóa đơn       
        String tenSP = sp.getTen();
        
        dsDVT.sort((a, b) -> Double.compare(a.getHeSoQuyDoi(), b.getHeSoQuyDoi()));
        DonViTinh dvtMacDinh = dsDVT.get(0);
        String tenDVT = dvtMacDinh.getTenDonVi();
        
        double donGia = dvtMacDinh.getGiaBanTheoDonVi();
        
        int soLuong = 1;   
        
        double giamGia = 0;
        dsKM.sort((b, a) -> Double.compare(a.getPhanTram(), b.getPhanTram()));
        for(KhuyenMai km : dsKM) {
            if(soLuong >= km.getSoLuongToiThieu() && soLuong <= km.getSoLuongToiDa()) {
                giamGia = km.getPhanTram();
                break;
            }
        }
        double thanhTien = soLuong * donGia * (1 - giamGia / 100);
        String maDVT = dvtMacDinh.getMaDonViTinh();
        
        Object[] newRow = {0, tenSP, tenDVT, donGia, soLuong, giamGia, thanhTien, maDVT, maSP};
        
        return newRow;
    }
    
    public Object[] thayDoiChiTietHoaDon(String maSP, int soLuong, String tenDVT) throws Exception{
        // Lấy khuyến mãi đủ điều kiện
        ArrayList<KhuyenMai> dskm = KhuyenMaiDAO.getKhuyenMaiTheoMaSP(maSP);
        dskm.sort((b, a) -> Double.compare(a.getPhanTram(), b.getPhanTram()));
        double giamGia = 0;
        for(KhuyenMai km : dskm) {
            if(soLuong >= km.getSoLuongToiThieu() && soLuong <= km.getSoLuongToiDa()) {
                giamGia = km.getPhanTram();
                break;
            } 
        }                               
        // Lấy số lượng trong kho
        ArrayList<LoSanPham> dsLSP = LoSanPhamDAO.getLoSanPhamTheoMaSP(maSP);
        int tongSoLuong = 0;
        for(LoSanPham lsp : dsLSP) {
            tongSoLuong += lsp.getSoLuong();
        }

        ArrayList<DonViTinh> dsDVT = DonViTinhDAO.getDonViTinhTheoMaSP(maSP);
        for (DonViTinh dvt : dsDVT) {
            if (dvt.getTenDonVi().equals(tenDVT)) {
                int heSoQuyDoi = dvt.getHeSoQuyDoi();
                double donGia = dvt.getGiaBanTheoDonVi();
                double thanhTien = soLuong * donGia * (1 - giamGia / 100);
                String maDVT = dvt.getMaDonViTinh();
                if(soLuong * heSoQuyDoi > tongSoLuong) {
                    throw new Exception("Không đủ số lượng");
                }
                Object[] updatedInfo = {donGia, giamGia, thanhTien, maDVT};
                return updatedInfo;
            }
        }
        return null;
    }
    
    public KhachHang layThongTinKhachHang(String sdt) {        
        KhachHang kh = null;
        try {
            kh = KhachHangDAO.getKhachHangTheoSdt(sdt);
        } catch (SQLException ex) {
            return kh;
        }
        return kh;
    }
    
    public boolean thanhToan() {
        
        
        return true;
    }
}
