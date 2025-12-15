/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.entity;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author GIGABYTE
 */
public class NhanVien {

    private String maNV;
    private String hoTenDem;
    private String ten;
    private String sdt;
    private String cccd;
    private boolean gioiTinh;
    private LocalDate ngaySinh;
    private String diaChi;
    private boolean nghiViec;
    private boolean daXoa;

    public NhanVien(NhanVien other) {
        this.maNV = other.maNV;
        this.hoTenDem = other.hoTenDem;
        this.ten = other.ten;
        this.sdt = other.sdt;
        this.cccd = other.cccd;
        this.gioiTinh = other.gioiTinh;
        this.ngaySinh = other.ngaySinh;
        this.diaChi = other.diaChi;
        this.nghiViec = other.nghiViec;
        this.daXoa = other.daXoa;
    }

    public NhanVien(String maNV, String hoTenDem, String ten, String sdt, String cccd, boolean gioiTinh,
            LocalDate ngaySinh, String diaChi, boolean nghiViec) {
        this.maNV = maNV;
        this.hoTenDem = hoTenDem;
        this.ten = ten;
        this.sdt = sdt;
        this.cccd = cccd;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.nghiViec = nghiViec;
        this.daXoa = false;
    }
    
    public NhanVien(String maNV, String hoTenDem, String ten) {
        this.maNV = maNV;
        this.hoTenDem = hoTenDem;
        this.ten = ten;
    }

    public NhanVien(String maNV) {
        this.maNV = maNV;
    }

    public NhanVien() {

    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        if (maNV.matches("^NV-\\d{4}$")) {
            this.maNV = maNV;
        } else {
            throw new IllegalArgumentException("Mã nhân viên không hợp lệ! (Đúng dạng NVxxxx, với xxxx là 4 chữ số)");
        }
    }

    public String getHoTenDem() {
        return hoTenDem;
    }

    public void setHoTenDem(String hoTenDem) {
        if (hoTenDem.length() <= 50 && !hoTenDem.isEmpty()) {
            this.hoTenDem = hoTenDem;
        } else {
            throw new IllegalArgumentException("Họ tên đệm không được rỗng và không quá 50 ký tự");
        }
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        if (ten.length() <= 20 && !ten.isEmpty()) {
            this.ten = ten;
        } else {
            throw new IllegalArgumentException("Tên không được rỗng và không quá 20 ký tự");
        }
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        if (sdt.matches("^0\\d{9}$")) {
            this.sdt = sdt;
        } else {
            throw new IllegalArgumentException("Số điện thoại không hợp lệ");
        }
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        if (cccd.matches("^\\d{12}$")) {
            this.cccd = cccd;
        } else {
            throw new IllegalArgumentException("Số CCCD không hợp lệ");
        }
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public boolean isDaXoa() {
        return daXoa;
    }

    public void setDaXoa(boolean daXoa) {
        this.daXoa = daXoa;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        if (ngaySinh == null) {
            throw new IllegalArgumentException("Ngày sinh không được trống");
        }
        if (ngaySinh.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Ngày sinh phải trước ngày hiện tại");
        }
        int tuoi = LocalDate.now().getYear() - ngaySinh.getYear();
        if (LocalDate.now().getMonthValue() < ngaySinh.getMonthValue()) {
            tuoi--;
        }
        if (tuoi < 18) {
            throw new IllegalArgumentException("Chưa đủ 18 tuổi");
        }
        this.ngaySinh = ngaySinh;

    }

    public void setMatKhau(String matKhau) {

    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        if (diaChi.length() <= 255) {
            this.diaChi = diaChi;
        } else {
            throw new IllegalArgumentException("Địa chỉ không được vượt quá 255 ký tự");
        }
    }

    public boolean isNghiViec() {
        return nghiViec;
    }

    public void setNghiViec(boolean nghiViec) {
        this.nghiViec = nghiViec;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.maNV);
        hash = 29 * hash + Objects.hashCode(this.sdt);
        hash = 29 * hash + Objects.hashCode(this.cccd);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NhanVien other = (NhanVien) obj;
        if (!Objects.equals(this.maNV, other.maNV)) {
            return false;
        }
        if (!Objects.equals(this.sdt, other.sdt)) {
            return false;
        }
        return Objects.equals(this.cccd, other.cccd);
    }

    @Override
    public String toString() {
        return "NhanVien{" + "maNV=" + maNV + ", hoTenDem=" + hoTenDem + ", ten=" + ten + ", sdt=" + sdt + ", cccd=" + cccd + ", gioiTinh=" + gioiTinh + ", ngaySinh=" + ngaySinh + ", diaChi=" + diaChi + ", nghiViec=" + nghiViec + ", daXoa=" + daXoa + '}';
    }

}
