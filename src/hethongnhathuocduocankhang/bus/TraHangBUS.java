/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.bus;

import hethongnhathuocduocankhang.dao.ChiTietPhieuTraDAO;
import hethongnhathuocduocankhang.dao.HoaDonDAO;
import hethongnhathuocduocankhang.dao.KhachHangDAO;
import hethongnhathuocduocankhang.dao.LoSanPhamDAO;
import hethongnhathuocduocankhang.dao.PhieuTraHangDAO;
import hethongnhathuocduocankhang.entity.ChiTietPhieuTraHang;
import hethongnhathuocduocankhang.entity.HoaDon;
import hethongnhathuocduocankhang.entity.PhieuTraHang;
import hethongnhathuocduocankhang.entity.TinhTrangSanPhamEnum;
import hethongnhathuocduocankhang.entity.TruongHopDoiTraEnum;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 *
 * @author MINH KHANG
 */
public class TraHangBUS {
    /**
     * Nghiệp vụ 1: Kiểm tra điều kiện trả hàng
     * Logic: Hóa đơn tồn tại VÀ ngày lập <= 30 ngày 
     */
    public HoaDon kiemTraDieuKienTraHang(String maHoaDon) throws Exception {
        if (maHoaDon == null || maHoaDon.trim().isEmpty()) {
            throw new Exception("Vui lòng nhập mã hóa đơn!");
        }

        HoaDon hoaDon = HoaDonDAO.getHoaDonTheoMaHD(maHoaDon);
        if (hoaDon == null) {
            throw new Exception("Không tìm thấy hóa đơn với mã: " + maHoaDon);
        }

        // Lấy ngày lập (Kiểu LocalDate)
        LocalDateTime ngayLapHoaDon;

        // Kiểm tra xem getNgayLapHoaDon trả về LocalDate hay LocalDateTime để xử lý
        // Giả sử getter trả về LocalDate (theo lỗi gợi ý), nếu getter trả về LocalDateTime thì dùng .toLocalDate()
        ngayLapHoaDon = hoaDon.getNgayLapHoaDon(); 

        // Tính khoảng cách ngày (Chắc chắn cả 2 đều là LocalDate)
        long ngayDaTroiQua = ChronoUnit.DAYS.between(ngayLapHoaDon, LocalDateTime.now());

        if (ngayDaTroiQua > 30) {
            throw new Exception("Hóa đơn đã lập " + ngayDaTroiQua + " ngày (quá 30 ngày), không thể trả hàng!");
        }

        return hoaDon;
    }

    /**
     * Nghiệp vụ 2: Lấy chuỗi hiển thị phần trăm hoàn trả
     */
    public static String layPhanTramHoanTra(TruongHopDoiTraEnum lyDo, boolean isNguyenVen) {
            if (isNguyenVen) {
                return "100%";
            }
            if (lyDo == TruongHopDoiTraEnum.HANG_LOI_DO_NHA_SAN_XUAT) return "100%";
            if (lyDo == TruongHopDoiTraEnum.DI_UNG_MAN_CAM) return "70%";
            return "Miễn trả hàng";
        }

    /**
     * Nghiệp vụ 3: Tính tiền hoàn trả cho từng sản phẩm
     */
public static double tinhTienHoanTraItem(double thanhTienGoc, TruongHopDoiTraEnum lyDo, boolean isNguyenVen) {
        // 1. Nếu hàng nguyên vẹn -> Luôn hoàn 100% tiền (theo logic code cũ của bạn)
        if (isNguyenVen) {
            return thanhTienGoc;
        }

        // 2. Nếu hàng KHÔNG nguyên vẹn -> Xét theo lý do
        if (lyDo == TruongHopDoiTraEnum.HANG_LOI_DO_NHA_SAN_XUAT) {
            return thanhTienGoc; // 100%
        } else if (lyDo == TruongHopDoiTraEnum.DI_UNG_MAN_CAM) {
            return thanhTienGoc * 0.7; // 70%
        } else {
            return 0; // Nhu cầu khách hàng -> 0%
        }
    }

    /**
     * Nghiệp vụ 4: Sinh mã phiếu trả hàng tự động
     */
    public static String phatSinhMaPhieuTraHang() {
        int ngay = LocalDate.now().getDayOfMonth();
        int thang = LocalDate.now().getMonthValue();
        int nam = LocalDate.now().getYear() % 1000; // Lấy 2 số cuối của năm
        String ngayThangNam = String.format("%02d%02d%02d", ngay, thang, nam);
        
        int soPhieuHomNay = PhieuTraHangDAO.phieuTraHangMoiNhatHomNay();
        return String.format("PTH-%s-%04d", ngayThangNam, soPhieuHomNay + 1);
    }

    /**
     * Nghiệp vụ 5 (Quan trọng nhất): Xử lý giao dịch trả hàng
     * Bao gồm: Lưu phiếu, Lưu chi tiết, Cộng kho, Trừ điểm
     */
    public static void xuLyTraHang(PhieuTraHang pth, List<ChiTietPhieuTraHang> listChiTiet) {
        // 1. Lưu phiếu trả hàng
        PhieuTraHangDAO.themPhieuTra(pth);

        // 2. Lưu chi tiết phiếu trả & Cập nhật kho
        for (ChiTietPhieuTraHang ctpth : listChiTiet) {
            // Lưu chi tiết
            ChiTietPhieuTraDAO.insertChiTietPhieuTra(ctpth);
            
            // Logic cập nhật kho (Đưa logic từ GUI sang đây)
            TinhTrangSanPhamEnum tinhTrang = ctpth.getTinhTrangSanPham();
            TruongHopDoiTraEnum truongHop = ctpth.getTruongHopDoiTra();
            
            // Điều kiện cộng lại kho: Hàng nguyên vẹn VÀ (Do khách đổi ý HOẶC Dị ứng)
            boolean hangTraVeKho = tinhTrang == TinhTrangSanPhamEnum.HANG_NGUYEN_VEN 
                    && (truongHop == TruongHopDoiTraEnum.NHU_CAU_KHACH_HANG || truongHop == TruongHopDoiTraEnum.DI_UNG_MAN_CAM);

            if (hangTraVeKho) {
                 String maLo = LoSanPhamDAO.getLoSanPhamTheoMaCTHD(ctpth.getChiTietHoaDon().getMaChiTietHoaDon()).getMaLoSanPham();
                 int soLuong = ctpth.getSoLuong();
                 int heSoQuyDoi = ctpth.getChiTietHoaDon().getDonViTinh().getHeSoQuyDoi();
                 
                 LoSanPhamDAO.congSoLuong(maLo, soLuong, heSoQuyDoi);
            }
        }

        // 3. Trừ điểm tích lũy khách hàng (Nếu có khách hàng thành viên)
        // Logic: Điểm trừ = Tổng tiền hoàn / 1000
        if (pth.getHoaDon().getKhachHang() != null && 
            !pth.getHoaDon().getKhachHang().getMaKH().equalsIgnoreCase("KH-00000")) {
            
            String maKhachHang = pth.getHoaDon().getKhachHang().getMaKH();
            int diemTru = (int) (pth.getTongTienHoanTra() / 1000);
            
            if (diemTru > 0) {
                KhachHangDAO.truDiemTichLuy(diemTru, maKhachHang);
            }
        }
    }

    public static double tinhThanhTienGoc(int soLuong, double donGia, double phanTramGiamGia) {
        return (soLuong * donGia) - (soLuong * donGia * (phanTramGiamGia / 100));
    }
}