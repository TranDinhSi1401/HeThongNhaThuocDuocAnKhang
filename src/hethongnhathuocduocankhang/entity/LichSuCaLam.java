/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 *
 * @author GIGABYTE
 */
public class LichSuCaLam {

    private NhanVien nhanVien;
    private LocalDate ngayLamViec;
    private CaLam caLam;
    private LocalTime thoiGianVaoCa;
    private LocalTime thoiGianRaCa;
    private String ghiChu;

    public LichSuCaLam(NhanVien nhanVien, LocalDate ngayLamViec, CaLam caLam, LocalTime thoiGianVaoCa,
            LocalTime thoiGianRaCa, String ghiChu) {
        this.nhanVien = nhanVien;
        this.ngayLamViec = ngayLamViec;
        this.caLam = caLam;
        this.thoiGianVaoCa = thoiGianVaoCa;
        this.thoiGianRaCa = thoiGianRaCa;
        this.ghiChu = ghiChu;
    }

    public LichSuCaLam() {

    }

    public LichSuCaLam(LichSuCaLam lichSuCaLam) {
        this.nhanVien = lichSuCaLam.nhanVien;
        this.ngayLamViec = lichSuCaLam.ngayLamViec;
        this.caLam = lichSuCaLam.caLam;
        this.thoiGianVaoCa = lichSuCaLam.thoiGianVaoCa;
        this.thoiGianRaCa = lichSuCaLam.thoiGianRaCa;
        this.ghiChu = lichSuCaLam.ghiChu;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        if (nhanVien != null) {
            this.nhanVien = nhanVien;
        } else {
            throw new IllegalArgumentException("Nhân viên không được để trống");
        }
    }

    public LocalDate getNgayLamViec() {
        return ngayLamViec;
    }

    public void setNgayLamViec(LocalDate ngayLamViec) {
        if (ngayLamViec != null) {
            this.ngayLamViec = ngayLamViec;
        } else {
            throw new IllegalArgumentException("Ngày làm việc không được để trống");
        }
    }

    public CaLam getCaLam() {
        return caLam;
    }

    public void setCaLam(CaLam caLam) {
        if (caLam != null) {
            this.caLam = caLam;
        } else {
            throw new IllegalArgumentException("Ca làm không được để trống");
        }
    }

    public LocalTime getThoiGianVaoCa() {
        return thoiGianVaoCa;
    }

    public void setThoiGianVaoCa(LocalTime thoiGianVaoCa) {
        if (thoiGianVaoCa == null) {
            throw new IllegalArgumentException("Thời gian vào ca không được trống");
        }
        if (thoiGianRaCa != null) {
            if (thoiGianVaoCa.isBefore(thoiGianRaCa)) {
                this.thoiGianVaoCa = thoiGianVaoCa;
            } else {
                throw new IllegalArgumentException("Thời gian vào ca phải trước thời gian ra ca");
            }
        } else {
            this.thoiGianVaoCa = thoiGianVaoCa;
        }
    }

    public LocalTime getThoiGianRaCa() {
        return thoiGianRaCa;
    }

    public void setThoiGianRaCa(LocalTime thoiGianRaCa) {
        if (thoiGianRaCa == null) {
            throw new IllegalArgumentException("Thời gian ra ca không được trống");
        }
        if (thoiGianVaoCa != null) {
            if (thoiGianRaCa.isAfter(thoiGianVaoCa)) {
                this.thoiGianRaCa = thoiGianRaCa;
            } else {
                throw new IllegalArgumentException("Thời gian ra ca phải sau thời gian vào ca");
            }
        } else {
            this.thoiGianRaCa = thoiGianRaCa;
        }
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        if (ghiChu.length() <= 500) {
            this.ghiChu = ghiChu;
        } else {
            throw new IllegalArgumentException("Ghi chú không được vượt quá 500 ký tự");
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.nhanVien);
        hash = 37 * hash + Objects.hashCode(this.ngayLamViec);
        hash = 37 * hash + Objects.hashCode(this.caLam);
        hash = 37 * hash + Objects.hashCode(this.thoiGianVaoCa);
        hash = 37 * hash + Objects.hashCode(this.thoiGianRaCa);
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
        final LichSuCaLam other = (LichSuCaLam) obj;
        if (!Objects.equals(this.nhanVien, other.nhanVien)) {
            return false;
        }
        if (!Objects.equals(this.ngayLamViec, other.ngayLamViec)) {
            return false;
        }
        if (!Objects.equals(this.caLam, other.caLam)) {
            return false;
        }
        if (!Objects.equals(this.thoiGianVaoCa, other.thoiGianVaoCa)) {
            return false;
        }
        return Objects.equals(this.thoiGianRaCa, other.thoiGianRaCa);
    }

    @Override
    public String toString() {
        return "LichSuCaLam{" + "nhanVien=" + nhanVien + ", ngayLamViec=" + ngayLamViec + ", caLam=" + caLam + ", thoiGianVaoCa=" + thoiGianVaoCa + ", thoiGianRaCa=" + thoiGianRaCa + ", ghiChu=" + ghiChu + '}';
    }

}
