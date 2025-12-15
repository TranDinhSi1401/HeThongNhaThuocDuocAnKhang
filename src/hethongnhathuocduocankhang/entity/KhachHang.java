/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.entity;

public class KhachHang {

    private String maKH;
    private String hoTenDem;
    private String ten;
    private String sdt;
    private int diemTichLuy;
    private boolean daXoa; 

    public KhachHang() {
        this.daXoa = false;
    }

    public KhachHang(String maKH, String hoTenDem, String ten, String sdt, int diemTichLuy, boolean daXoa) {
        this.maKH = maKH;
        this.hoTenDem = hoTenDem;
        this.ten = ten;
        this.sdt = sdt;
        this.diemTichLuy = diemTichLuy;
        this.daXoa = daXoa;
    }

    public KhachHang(String maKH, String hoTenDem, String ten, String sdt, int diemTichLuy) {
        this(maKH, hoTenDem, ten, sdt, diemTichLuy, false);
    }

    public KhachHang(String maKH) {
        this.maKH = maKH;
    }

    public String getMaKH() {
        return maKH;
    }

    public String getHoTenDem() {
        return hoTenDem;
    }

    public String getTen() {
        return ten;
    }

    public String getSdt() {
        return sdt;
    }

    public int getDiemTichLuy() {
        return diemTichLuy;
    }
    
    public boolean isDaXoa() {
        return daXoa;
    }

    public void setDaXoa(boolean daXoa) {
        this.daXoa = daXoa;
    }

    public void setMaKH(String maKH) throws Exception {
        if (maKH == null || maKH.trim().isEmpty()) {
            throw new Exception("Mã khách hàng không được rỗng");
        }
        if (!maKH.matches("KH-\\d{5}")) {
            throw new Exception("Mã khách hàng phải theo định dạng KH-XXXXX (X là số từ 0-9)");
        }
        this.maKH = maKH;
    }

    public void setHoTenDem(String hoTenDem) throws Exception {
        if (hoTenDem.trim().isEmpty()) {
            throw new Exception("Họ tên đệm không được rỗng");
        }
        this.hoTenDem = hoTenDem;
    }

    public void setTen(String ten) throws Exception {
        if (ten.trim().isBlank()) {
            throw new Exception("Tên không được rỗng");
        }
        if (!ten.matches("[A-Z][a-z]+")) {
            throw new Exception("Tên phải bắt đầu bằng ký tự viết hoa và không chứa ký tự số hay ký tự đặc biệt");
        }
        this.ten = ten;
    }

    public void setSdt(String sdt) throws Exception {
        if (!sdt.matches("0\\d{9}")) {
            throw new Exception("Số điện thoại phải đủ 10 số và bắt đầu bằng số 0");
        }
        this.sdt = sdt;
    }

    public void setDiemTichLuy(int diemTichLuy) {
        if (diemTichLuy < 0) {
             this.diemTichLuy = 0;
        } else {
             this.diemTichLuy = diemTichLuy;
        }
    }

    @Override
    public String toString() {
        return "KhachHang{" + "maKH=" + maKH + ", hoTenDem=" + hoTenDem + ", ten=" + ten + ", sdt=" + sdt + ", diemTichLuy=" + diemTichLuy + ", daXoa=" + daXoa + '}';
    }
}