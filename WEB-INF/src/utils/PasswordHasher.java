package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHasher {
    
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur de hachage du mot de passe", e);
        }
    }
    
    public static void main(String[] args) {
        String[] passwords = {"elias", "anis", "ilyes", "benjamin", "imad", "hugo"};
        for (String password : passwords) {
            System.out.println("Original: " + password + " -> Hashed: " + hashPassword(password));
        }
    }

    public static boolean verifyPassword(String inputPassword, String storedPassword) {
        String hashedInputPassword = hashPassword(inputPassword);
        return hashedInputPassword.equals(storedPassword);
    }
}