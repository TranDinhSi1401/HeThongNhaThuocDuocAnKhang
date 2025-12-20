/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.util;
import hethongnhathuocduocankhang.dao.TaiKhoanDAO;
import hethongnhathuocduocankhang.entity.TaiKhoan;
import java.security.SecureRandom;
import java.util.UUID;
import org.mindrot.jbcrypt.BCrypt;
/**
 *
 * @author trand
 */
public class PasswordUtil {
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "@#$%^&*-_";

    private static final String ALL = LOWER + UPPER + DIGITS + SPECIAL;
    private static final SecureRandom random = new SecureRandom();

    public static String generateTempPassword() {
        StringBuilder password = new StringBuilder();

        // đảm bảo có ít nhất 1 ký tự của mỗi loại
        password.append(LOWER.charAt(random.nextInt(LOWER.length())));
        password.append(UPPER.charAt(random.nextInt(UPPER.length())));
        password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        password.append(SPECIAL.charAt(random.nextInt(SPECIAL.length())));

        // sinh thêm cho đủ độ dài 8
        for (int i = 4; i < 8; i++) {
            password.append(ALL.charAt(random.nextInt(ALL.length())));
        }

        // xáo trộn vị trí các ký tự để tránh dự đoán
        return shuffleString(password.toString());
    }

    private static String shuffleString(String input) {
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int j = random.nextInt(chars.length);
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }
        return new String(chars);
    }
    
    public static String hashPassword(String plainPassword) {
        String salt = BCrypt.gensalt(12); // 12 là cost factor (độ mạnh)
        return BCrypt.hashpw(plainPassword, salt);
    }
    
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
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
