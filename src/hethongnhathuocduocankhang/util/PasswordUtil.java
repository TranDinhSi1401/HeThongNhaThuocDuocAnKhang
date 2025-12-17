/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.util;
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
}
