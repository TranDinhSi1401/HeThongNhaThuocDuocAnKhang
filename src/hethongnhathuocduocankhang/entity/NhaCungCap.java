/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.entity;

/**
 *
 * @author admin
 */
public class NhaCungCap {

    private String maNCC;
    private String tenNCC;
    private String diaChi;
    private String sdt;
    private String email;

    public NhaCungCap() {
    }

    public NhaCungCap(String maNCC, String tenNCC, String diaChi, String sdt, String email) {
        this.maNCC = maNCC;
        this.tenNCC = tenNCC;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.email = email;
    }

    public NhaCungCap(String maNCC) {
        this.maNCC = maNCC;
    }

    public String getMaNCC() {
        return maNCC;
    }

    public String getTenNCC() {
        return tenNCC;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setMaNCC(String maNCC) {
        if (maNCC == null || !maNCC.matches("^NCC-\\d{4}$")) {
            throw new IllegalArgumentException("Mã NCC không hợp lệ! Định dạng đúng: NCC-0001");
        }
        this.maNCC = maNCC;
    }

    public void setTenNCC(String tenNCC) throws Exception {
        if (tenNCC == null || tenNCC.trim().isEmpty() || tenNCC.length() > 100) {
            throw new Exception("Tên nhà cung cấp không hợp lệ!");
        }
        this.tenNCC = tenNCC;
    }

    public void setDiaChi(String diaChi) throws Exception {
        if (diaChi != null && diaChi.length() > 225) {
            throw new Exception("Địa chỉ không vượt quá 225 ký tự");
        }
        this.diaChi = diaChi;
    }

    public void setSdt(String sdt) throws Exception {
        if (sdt == null || !sdt.matches("0\\d{9}")) {
            throw new Exception("Số điện thoại phải có gồm 10 số và bắt đầu bằng số 0");
        }
        this.sdt = sdt;
    }

    public void setEmail(String email) throws Exception {
        if (email == null || !email.matches("[a-zA-Z0-9._%+-]+@gmail\\.com")) {
            throw new Exception("Email phải đúng định dạng @gmail.com");
        }
        this.email = email;
    }

    @Override
    public String toString() {
        return "NhaCungCap{" + "maNCC=" + maNCC + ", tenNCC=" + tenNCC + ", diaChi=" + diaChi + ", sdt=" + sdt + ", email=" + email + '}';
    }

}
