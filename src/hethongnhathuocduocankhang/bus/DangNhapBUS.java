/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.bus;

import hethongnhathuocduocankhang.dao.TaiKhoanDAO;
import hethongnhathuocduocankhang.entity.TaiKhoan;
import hethongnhathuocduocankhang.mail.EmailUtil;
import hethongnhathuocduocankhang.password.PasswordUtil;

/**
 *
 * @author trand
 */
public class DangNhapBUS {
    public static TaiKhoan dangNhap(String userName, String plainPassword) {               
        TaiKhoan tk = TaiKhoanDAO.getTaiKhoanTheoTenDangNhap(userName);
        String hashed = tk.getMatKhau();
        if(PasswordUtil.checkPassword(plainPassword, hashed) && !tk.isBiKhoa()) {
            return tk;
        } else {
            return null;
        }      
    }
    
    public static boolean quenMatKhau(String taiKhoan, String email) {
        // Sinh mật khẩu mới
        String newPassword = PasswordUtil.generateTempPassword();
        // Hash
        String hashed = PasswordUtil.hashPassword(newPassword);
        // Cập nhật
        Boolean check = TaiKhoanDAO.updateMatKhau(taiKhoan, email, hashed);
        // Gửi mail
        String subject = "Yêu cầu đặt lại mật khẩu";
        String body =
            "<div style='font-family:Segoe UI, sans-serif; font-size:14px;'>"
          + "<h2 style='color:#1976D2;'>Đặt lại mật khẩu</h2>"
          + "<p>Xin chào,</p>"
          + "<p>Mật khẩu mới của bạn là:</p>"
          + "<div style='padding:10px 15px; "
                + "background-color:#E3F2FD; color:#0D47A1; "
                + "border-radius:5px; display:inline-block; "
                + "font-size:18px; font-weight:bold;'>"
          + newPassword
          + "</div>"
          + "<p>Vui lòng đăng nhập và đổi mật khẩu ngay.</p>"
          + "<p style='color:#555;'>Trân trọng,<br>Hệ thống nhà thuốc Dược An Khang</p>"
          + "</div>";

        EmailUtil.sendEmail(email, subject, body);

        return check;
    }
}
