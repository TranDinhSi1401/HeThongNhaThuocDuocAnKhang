/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.entity;

import java.time.LocalTime;
import java.util.Objects;

public class CaLam {

    private String maCa;
    private String tenCa;
    private LocalTime thoiGianBatDau;
    private LocalTime thoiGianKetThuc;

    public CaLam(String maCa, String tenCa, LocalTime thoiGianBatDau, LocalTime thoiGianKetThuc) {
        super();
        this.maCa = maCa;
        this.tenCa = tenCa;
        this.thoiGianBatDau = thoiGianBatDau;
        this.thoiGianKetThuc = thoiGianKetThuc;
    }

    public CaLam() {

    }

    public CaLam(CaLam other) {
        this.maCa = other.maCa;
        this.tenCa = other.tenCa;
        this.thoiGianBatDau = other.thoiGianBatDau;
        this.thoiGianKetThuc = other.thoiGianKetThuc;
    }

    public String getMaCa() {
        return maCa;
    }

    public void setMaCa(String maCa) {
        if (maCa.equals("SANG") || maCa.equals("TOI")) {
            this.maCa = maCa;
        } else {
            throw new IllegalArgumentException("Mã ca không phù hợp, mã ca chỉ được 'SANG', 'TOI'");
        }
    }

    public String getTenCa() {
        return tenCa;
    }

    public void setTenCa(String tenCa) {
        if (tenCa != null && !tenCa.isEmpty() && tenCa.length() <= 50) {
            this.tenCa = tenCa;
        } else {
            throw new IllegalArgumentException("Tên ca không được rỗng và không quá 50 ký tự");
        }
    }

    public LocalTime getThoiGianBatDau() {
        return thoiGianBatDau;
    }

    public void setThoiGianBatDau(LocalTime thoiGianBatDau) {
        if (thoiGianBatDau == null) {
            throw new IllegalArgumentException("Thời gian bắt đầu không được để trống");
        }
        if (thoiGianKetThuc != null) {
            if (thoiGianBatDau.isBefore(thoiGianKetThuc)) {
                this.thoiGianBatDau = thoiGianBatDau;
            } else {
                throw new IllegalArgumentException("Thời gian băt đầu phải trước thời gian kết thúc");
            }
        } else {
            this.thoiGianBatDau = thoiGianBatDau;
        }
    }

    public LocalTime getThoiGianKetThuc() {
        return thoiGianKetThuc;
    }

    public void setThoiGianKetThuc(LocalTime thoiGianKetThuc) {
        if (thoiGianKetThuc == null) {
            throw new IllegalArgumentException("Thời gian kết thúc không được để trống");
        }
        if (thoiGianBatDau != null) {
            if (thoiGianKetThuc.isAfter(thoiGianBatDau)) {
                this.thoiGianKetThuc = thoiGianKetThuc;
            } else {
                throw new IllegalArgumentException("Thời gian kêt thúc phải sau thời gian bắt đầu");
            }
        } else {
            this.thoiGianKetThuc = thoiGianKetThuc;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.maCa);
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
        final CaLam other = (CaLam) obj;
        return Objects.equals(this.maCa, other.maCa);
    }

    @Override
    public String toString() {
        return "CaLam{" + "maCa=" + maCa + ", tenCa=" + tenCa + ", thoiGianBatDau=" + thoiGianBatDau + ", thoiGianKetThuc=" + thoiGianKetThuc + '}';
    }

}
