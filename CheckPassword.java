import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CheckPassword {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        String storedHash = "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy";
        
        // Probar contraseñas comunes
        String[] passwords = {"admin123", "mozo123", "cajero123", "cocinero123", "password", "123456"};
        
        System.out.println("Verificando contraseñas para el hash: " + storedHash);
        System.out.println("============================================");
        
        for (String password : passwords) {
            boolean matches = encoder.matches(password, storedHash);
            System.out.println(password + " -> " + (matches ? "✓ COINCIDE" : "✗ NO coincide"));
        }
    }
}