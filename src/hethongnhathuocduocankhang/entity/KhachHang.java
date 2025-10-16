/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.entity;

/**
 *
 * @author admin
 */
public class KhachHang {
    private String maKH;
    private String hoTenDem;
    private String ten;
    private String sdt;
    private int diemTichLuy;

    public KhachHang() {
    }
    public KhachHang(String maKH, String hoTenDem, String ten, String sdt, int diemTichLuy) {
        this.maKH = maKH;
        this.hoTenDem = hoTenDem;
        this.ten = ten;
        this.sdt = sdt;
        this.diemTichLuy = diemTichLuy;
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

    public void setMaKH(String maKH) throws Exception {
        if(maKH == null || maKH.trim().isEmpty())
            throw new Exception("Mã khách hàng không được rỗng");
        if(maKH.matches("KH-\\d{5}"))
            throw new Exception("Mã khách hàng phải theo định dạng KH-XXXXX (X là số từ 0-9)");
        this.maKH = maKH;
    }

    public void setHoTenDem(String hoTenDem) throws Exception {
        if(hoTenDem.trim().isEmpty())
            throw new Exception("Họ tên đệm không được rỗng");
        this.hoTenDem = hoTenDem;
    }

    public void setTen(String ten) throws Exception {
        if(ten.trim().isBlank()){
            throw new Exception("Tên không được rỗng");
        }
        if(ten.matches("[A-Z][a-z]+"))
            throw new Exception("Tên phải theo bắt đầu bằng ký tự viết hoa và không chứa ký tự số hay ký tự đặc biệt");
        this.ten = ten;
    }

    public void setSdt(String sdt) throws Exception {
        if(!sdt.matches("0\\d{9}"))
            throw new Exception("số điện thoại phải đủ 10 số và bắt đầu bằng số 0");
        this.sdt = sdt;
    }

    public int setDiemTichLuy(int diemTichLuy) {
        return this.diemTichLuy = diemTichLuy; // chưa rõ cách tính !
    }

    @Override
    public String toString() {
        return "KhachHang{" + "maKH=" + maKH + ", hoTenDem=" + hoTenDem + ", ten=" + ten + ", sdt=" + sdt + ", diemTichLuy=" + diemTichLuy + '}';
    }
}
