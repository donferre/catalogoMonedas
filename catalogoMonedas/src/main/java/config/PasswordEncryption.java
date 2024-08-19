package config;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncryption {
    
    // Método para hashear una contraseña
    public static String hashPassword(String rawPassword) {
        String salt = BCrypt.gensalt(12); // Puedes ajustar el costo según tus necesidades
        return BCrypt.hashpw(rawPassword, salt);
    }

    // Método para verificar una contraseña
    public static boolean checkPassword(String rawPassword, String hashedPassword) {
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}
