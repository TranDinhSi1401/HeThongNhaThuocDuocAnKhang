/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 *
 * @author GIGABYTE
 */
public class TaiKhoan {

    private String tenDangNhap;
    private NhanVien nhanVien;
    private String matKhau;
    private boolean quanLy;
    private boolean biKhoa;
    private String email;
    private LocalDateTime ngayTao;

    public TaiKhoan(String tenDangNhap, NhanVien nhanVien, String matKhau, boolean quanLy, boolean biKhoa, String email, LocalDateTime ngayTao) {
        this.tenDangNhap = tenDangNhap;
        this.nhanVien = nhanVien;
        this.matKhau = matKhau;
        this.quanLy = quanLy;
        this.biKhoa = biKhoa;
        this.email = email;
        this.ngayTao = ngayTao;
    }

    public TaiKhoan(TaiKhoan taiKhoan) {
        this.tenDangNhap = taiKhoan.tenDangNhap;
        this.nhanVien = taiKhoan.nhanVien;
        this.matKhau = taiKhoan.matKhau;
        this.quanLy = taiKhoan.quanLy;
        this.biKhoa = taiKhoan.biKhoa;
        this.email = taiKhoan.email;
        this.ngayTao = taiKhoan.ngayTao;
    }

    public TaiKhoan() {

    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        if (nhanVien != null) {
            this.nhanVien = nhanVien;
            this.tenDangNhap = nhanVien.getMaNV();
        } else {
            throw new IllegalArgumentException("Nhân viên không được trống");
        }
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        if (matKhau == null || matKhau.isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu không được để trống");
        }
        if (!matKhau.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\W).{8,}$")) {
            throw new IllegalArgumentException("Mật khẩu phải có tối thiểu 8 ký tự, gồm chữ hoa, chữ thường, chữ số và ký tự đặc biệt");
        }
        this.matKhau = matKhau;
    }

    public boolean isQuanLy() {
        return quanLy;
    }

    public void setQuanLy(boolean quanLy) {
        this.quanLy = quanLy;
    }

    public boolean isBiKhoa() {
        return biKhoa;
    }

    public void setBiKhoa(boolean biKhoa) {
        this.biKhoa = biKhoa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email không được để trống");
        }
        if (!email.matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$")) {
            throw new IllegalArgumentException("Email không đúng định dạng");
        }

        this.email = email;
    }

    public LocalDateTime getNgayTao() {
        return ngayTao;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.tenDangNhap);
        hash = 37 * hash + Objects.hashCode(this.nhanVien);
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
        final TaiKhoan other = (TaiKhoan) obj;
        if (!Objects.equals(this.tenDangNhap, other.tenDangNhap)) {
            return false;
        }
        return Objects.equals(this.nhanVien, other.nhanVien);
    }

    @Override
    public String toString() {
        return "TaiKhoan{" + "tenDangNhap=" + tenDangNhap + ", nhanVien=" + nhanVien + ", matKhau=" + matKhau + ", quanLy=" + quanLy + ", biKhoa=" + biKhoa + ", email=" + email + ", ngayTao=" + ngayTao + '}';
    }
}
