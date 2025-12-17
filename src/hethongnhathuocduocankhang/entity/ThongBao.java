package hethongnhathuocduocankhang.entity;

import java.time.LocalDate;

/**
 * Notification entity for expired/expiring batches
 * Loại: "HẾT HẠN" (urgent), "CẢNH BÁO" (warning)
 */
public class ThongBao {
    public static final String HET_HAN = "HẾT HẠN";
    public static final String CANH_BAO = "CẢNH BÁO";

    private String maLoSanPham;
    private String maSanPham;
    private String tenSanPham;
    private LocalDate ngayHetHan;
    private int soLuong;
    private String loaiThongBao; // "HẾT HẠN" or "CẢNH BÁO"
    private int soNgayConLai; // Days until expiry (negative if expired)

    public ThongBao(String maLoSanPham, String maSanPham, String tenSanPham,
                    LocalDate ngayHetHan, int soLuong, String loaiThongBao, int soNgayConLai) {
        this.maLoSanPham = maLoSanPham;
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.ngayHetHan = ngayHetHan;
        this.soLuong = soLuong;
        this.loaiThongBao = loaiThongBao;
        this.soNgayConLai = soNgayConLai;
    }

    public String getMaLoSanPham() {
        return maLoSanPham;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public LocalDate getNgayHetHan() {
        return ngayHetHan;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public String getLoaiThongBao() {
        return loaiThongBao;
    }

    public int getSoNgayConLai() {
        return soNgayConLai;
    }

    public String getMoTa() {
        if (loaiThongBao.equals(HET_HAN)) {
            return String.format("%s (%s) - HẾT HẠN %d ngày", tenSanPham, maSanPham, Math.abs(soNgayConLai));
        } else {
            return String.format("%s (%s) - Hết hạn trong %d ngày", tenSanPham, maSanPham, soNgayConLai);
        }
    }

    @Override
    public String toString() {
        return "ThongBao{" +
                "maLoSanPham='" + maLoSanPham + '\'' +
                ", maSanPham='" + maSanPham + '\'' +
                ", tenSanPham='" + tenSanPham + '\'' +
                ", ngayHetHan=" + ngayHetHan +
                ", soLuong=" + soLuong +
                ", loaiThongBao='" + loaiThongBao + '\'' +
                ", soNgayConLai=" + soNgayConLai +
                '}';
    }
}
