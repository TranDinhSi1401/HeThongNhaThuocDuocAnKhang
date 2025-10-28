/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.entity;

import java.util.regex.Pattern;

/**
 *
 * @author MINH KHANG
 */
public class SanPham {
    private String maSP;
    private String ten;
    private String moTa;
    private String thanhPhan;
    private LoaiSanPhamEnum loaiSanPhamEnum;
    private int tonToiThieu;
    private int tonToiDa;

    
    // --- Constructor ---
    public SanPham() {
    }

    public SanPham(String maSP) {
        setMaSP(maSP);
    }
    
    public SanPham(String maSP, String ten, String moTa, String thanhPhan,
                   LoaiSanPhamEnum loaiSanPhamEnum, int tonToiThieu, int tonToiDa) {
        setMaSP(maSP);
        setTen(ten);
        setMoTa(moTa);
        setThanhPhan(thanhPhan);
        setLoaiSanPham(loaiSanPhamEnum);
        setTonToiThieu(tonToiThieu);
        setTonToiDa(tonToiDa);

    }

    // Copy constructor
    public SanPham(SanPham sp) {
        this(sp.maSP, sp.ten, sp.moTa, sp.thanhPhan, sp.loaiSanPhamEnum,
             sp.tonToiThieu, sp.tonToiDa);
    }

    public SanPham(String maSP, String ten) {
        this.maSP = maSP;
        this.ten = ten;
        this(sp.maSP, sp.ten, sp.moTa, sp.thanhPhan, sp.loaiSanPhamEnum, sp.tonToiThieu, sp.tonToiDa);
    }

    


    // --- Getter & Setter ---
    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        if (maSP == null || !Pattern.matches("^SP-\\d{4}$", maSP)) {
            throw new IllegalArgumentException("Không được rỗng, không trùng, phải theo định dạng SP-[XXXX]. Với X là số nguyên từ 0–9.");
        }
        this.maSP = maSP;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        if (ten == null || ten.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên thuốc không được rỗng.");
        }
        this.ten = ten.trim();
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        if (moTa == null || moTa.trim().isEmpty()) {
            throw new IllegalArgumentException("Mô tả không được rỗng.");
        }
        this.moTa = moTa.trim();
    }

    public String getThanhPhan() {
        return thanhPhan;
    }

    public void setThanhPhan(String thanhPhan) {
        if (thanhPhan == null || thanhPhan.trim().isEmpty()) {
            throw new IllegalArgumentException("Thành phần không được rỗng.");
        }
        this.thanhPhan = thanhPhan.trim();
    }

    public LoaiSanPhamEnum getLoaiSanPham() {
        return loaiSanPhamEnum;
    }

    public void setLoaiSanPham(LoaiSanPhamEnum loaiSanPhamEnum) {
        if (loaiSanPhamEnum == null) {
            throw new IllegalArgumentException("Loại sản phẩm không được rỗng và phải thuộc THUOC hoặc THUC_PHAM_CHUC_NANG.");
        }
        this.loaiSanPhamEnum = loaiSanPhamEnum;
    }

    public int getTonToiThieu() {
        return tonToiThieu;
    }

    public void setTonToiThieu(int tonToiThieu) {
        if (tonToiThieu < 0 || (this.tonToiDa > 0 && tonToiThieu >= this.tonToiDa)) {
            throw new IllegalArgumentException("Số lượng tối thiểu là số nguyên dương và nhỏ hơn số lượng tối đa.");
        }
        this.tonToiThieu = tonToiThieu;
    }

    public int getTonToiDa() {
        return tonToiDa;
    }

    public void setTonToiDa(int tonToiDa) {
        if (tonToiDa < 0 || (this.tonToiThieu > 0 && tonToiDa <= this.tonToiThieu)) {
            throw new IllegalArgumentException("Số lượng tối đa là số nguyên dương và lớn hơn số lượng tối thiểu.");
        }
        this.tonToiDa = tonToiDa;
    }
    

    @Override
    public String toString() {
        return "SanPham{" +
                "maSP='" + maSP + '\'' +
                ", ten='" + ten + '\'' +
                ", moTa='" + moTa + '\'' +
                ", thanhPhan='" + thanhPhan + '\'' +
                ", loaiSanPhamEnum=" + loaiSanPhamEnum +
                ", tonToiThieu=" + tonToiThieu +
                ", tonToiDa=" + tonToiDa +
                '}';
    }
}
