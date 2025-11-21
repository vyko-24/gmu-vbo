package sgu.vbo.server.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

public class PasswordUtils {
    private static String encondePassword(String raw) {
        return new BCryptPasswordEncoder().encode(raw);
    }

    public static boolean matchPassword(String raw, String encoded) {
        return new BCryptPasswordEncoder().matches(raw, encoded);
    }

    public static String generateEncodedpassword(String username, String fullname) {
        // Inicial del nombre + nombre de usuario + año
        String raw = fullname.charAt(0) + username + LocalDateTime.now().getYear();
        //System.out.println("Generated raw password: " + raw);
        return encondePassword(raw);
    }
/*
    public static void main(String[] args) {
        System.out.println(generateEncodedpassword("Vyko", "Víctor Barrera"));
    }

 */
}


