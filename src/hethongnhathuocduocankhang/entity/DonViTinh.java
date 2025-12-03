package hethongnhathuocduocankhang.entity;

import java.util.Objects;
import java.util.regex.Pattern;

public class DonViTinh {

    private String maDonViTinh;
    private SanPham sanPham;
    private int heSoQuyDoi;
    private double giaBanTheoDonVi;
    private String tenDonVi;
    private boolean donViTinhCoBan;
    private boolean daXoa;

    // --- Constructors ---
    public DonViTinh() {
    }

    // Constructor full
    public DonViTinh(String maDonViTinh, SanPham sanPham, int heSoQuyDoi,
            double giaBanTheoDonVi, String tenDonVi, boolean donViTinhCoBan, boolean daXoa) {
        setMaDonViTinh(maDonViTinh);
        setSanPham(sanPham);
        setHeSoQuyDoi(heSoQuyDoi);
        setGiaBanTheoDonVi(giaBanTheoDonVi);
        setTenDonVi(tenDonVi);
        this.donViTinhCoBan = donViTinhCoBan;
        this.daXoa = daXoa;
    }

    // Constructor cũ
    public DonViTinh(String maDonViTinh, SanPham sanPham, int heSoQuyDoi,
            double giaBanTheoDonVi, String tenDonVi, boolean donViTinhCoBan) {
        this(maDonViTinh, sanPham, heSoQuyDoi, giaBanTheoDonVi, tenDonVi, donViTinhCoBan, false);
    }

    public DonViTinh(DonViTinh dvt) {
        this(dvt.maDonViTinh, dvt.sanPham, dvt.heSoQuyDoi,
                dvt.giaBanTheoDonVi, dvt.tenDonVi, dvt.donViTinhCoBan, dvt.daXoa);
    }

    public DonViTinh(String maDVT) {
        this.maDonViTinh = maDVT;
    }

    public DonViTinh(String maDonViTinh, SanPham sanPham, double giaBanTheoDonVi, String tenDonVi) {
        this.maDonViTinh = maDonViTinh;
        this.sanPham = sanPham;
        this.giaBanTheoDonVi = giaBanTheoDonVi;
        this.tenDonVi = tenDonVi;
    }

    public boolean isDaXoa() {
        return daXoa;
    }

    public void setDaXoa(boolean daXoa) {
        this.daXoa = daXoa;
    }

    // --- Getter & Setter ---
    public String getMaDonViTinh() {
        return maDonViTinh;
    }

    public void setMaDonViTinh(String maDonViTinh) {
        if (maDonViTinh == null || !Pattern.matches("^DVT-\\d{4}-[A-Z]+$", maDonViTinh)) {
            throw new IllegalArgumentException("Mã đơn vị tính phải theo định dạng DVT-[xxxx]-[ĐƠN_VỊ]. Ví dụ: DVT-0001-HOP");
        }
        this.maDonViTinh = maDonViTinh;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        if (sanPham == null) {
            throw new IllegalArgumentException("Sản phẩm trong đơn vị tính không được rỗng.");
        }
        this.sanPham = sanPham;
    }

    public int getHeSoQuyDoi() {
        return heSoQuyDoi;
    }

    public void setHeSoQuyDoi(int heSoQuyDoi) {
        if (heSoQuyDoi <= 0) {
            throw new IllegalArgumentException("Hệ số quy đổi phải lớn hơn 0.");
        }
        this.heSoQuyDoi = heSoQuyDoi;
    }

    public double getGiaBanTheoDonVi() {
        return giaBanTheoDonVi;
    }

    public void setGiaBanTheoDonVi(double giaBanTheoDonVi) {
        if (giaBanTheoDonVi < 0 || giaBanTheoDonVi > 20_000_000) {
            throw new IllegalArgumentException("Giá bán theo đơn vị phải nằm trong khoảng 0 - 20,000,000 VND.");
        }
        this.giaBanTheoDonVi = giaBanTheoDonVi;
    }

    public String getTenDonVi() {
        return tenDonVi;
    }

    public void setTenDonVi(String tenDonVi) {
        if (tenDonVi == null || tenDonVi.trim().isEmpty() || tenDonVi.length() > 30) {
            throw new IllegalArgumentException("Tên đơn vị không được rỗng và tối đa 30 ký tự.");
        }
        this.tenDonVi = tenDonVi.trim();
    }

    public boolean isDonViTinhCoBan() {
        return donViTinhCoBan;
    }

    public void setDonViTinhCoBan(boolean donViTinhCoBan) {
        this.donViTinhCoBan = donViTinhCoBan;
    }

    @Override
    public String toString() {
        return "DonViTinh{"
                + "maDonViTinh='" + maDonViTinh + '\''
                + ", sanPham=" + (sanPham != null ? sanPham.getMaSP() : "null")
                + ", heSoQuyDoi=" + heSoQuyDoi
                + ", giaBanTheoDonVi=" + giaBanTheoDonVi
                + ", tenDonVi='" + tenDonVi + '\''
                + ", donViTinhCoBan=" + donViTinhCoBan
                + ", daXoa=" + daXoa
                + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.maDonViTinh);
        hash = 37 * hash + Objects.hashCode(this.sanPham);
        hash = 37 * hash + this.heSoQuyDoi;
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.giaBanTheoDonVi) ^ (Double.doubleToLongBits(this.giaBanTheoDonVi) >>> 32));
        hash = 37 * hash + Objects.hashCode(this.tenDonVi);
        hash = 37 * hash + (this.donViTinhCoBan ? 1 : 0);
        hash = 37 * hash + (this.daXoa ? 1 : 0);
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
        final DonViTinh other = (DonViTinh) obj;
        if (this.heSoQuyDoi != other.heSoQuyDoi) {
            return false;
        }
        if (Double.doubleToLongBits(this.giaBanTheoDonVi) != Double.doubleToLongBits(other.giaBanTheoDonVi)) {
            return false;
        }
        if (this.donViTinhCoBan != other.donViTinhCoBan) {
            return false;
        }
        if (this.daXoa != other.daXoa) {
            return false;
        }
        if (!Objects.equals(this.maDonViTinh, other.maDonViTinh)) {
            return false;
        }
        if (!Objects.equals(this.tenDonVi, other.tenDonVi)) {
            return false;
        }
        return Objects.equals(this.sanPham, other.sanPham);
    }

}
