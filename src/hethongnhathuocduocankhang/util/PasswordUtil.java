/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.util;
import hethongnhathuocduocankhang.dao.TaiKhoanDAO;
import hethongnhathuocduocankhang.entity.TaiKhoan;
import java.util.UUID;
import org.mindrot.jbcrypt.BCrypt;
/**
 *
 * @author trand
 */
public class PasswordUtil {
    public static String hashPassword(String plainPassword) {
        String salt = BCrypt.gensalt(12); // 12 là cost factor (độ mạnh)
        return BCrypt.hashpw(plainPassword, salt);
    }
    
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
    
    public static String generateTempPassword() {
        return UUID.randomUUID().toString().substring(0, 8); 
    }
    
    public static boolean doiMatKhau(TaiKhoan tk, String mkCu, String mkMoi) {
        try {
            // Kiểm tra mật khẩu cũ
            if (!checkPassword(mkCu, tk.getMatKhau())) {
                return false;
            }

            // Hash mật khẩu mới
            String hashMkMoi = hashPassword(mkMoi);

            // Cập nhật trong CSDL
            boolean updated = TaiKhoanDAO.updateMatKhau(tk.getTenDangNhap(), tk.getEmail(), hashMkMoi);

            // Nếu update thành công, cập nhật lại đối tượng tk trong bộ nhớ
            if (updated) {
                tk.setMatKhau(hashMkMoi);
            }

            return updated;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
