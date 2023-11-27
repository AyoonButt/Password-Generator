import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;



public class PasswordGenerator {

    private int length;
    private int complexity;

    private static final String LOWERCASE_CHARS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*()-_+=";

    private Set<String> usedPasswords;

    public PasswordGenerator() {
        this.usedPasswords = new HashSet<>();
        loadUsedPasswords();
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setComplexity(int complexity) {
        this.complexity = complexity;
    }
    
    public String generatePassword() {
        // Load used passwords from persistent storage
        loadUsedPasswords();
    
        // Calculate the maximum number of possible passwords with the given length and complexity
        long maxPossiblePasswords = calculateMaxPossiblePasswords(length, complexity);
    
        // Keep generating passwords until a unique one is found or all possibilities are exhausted
        Set<String> attemptedPasswords = new HashSet<>();
        for (long attempt = 1; attempt <= maxPossiblePasswords; attempt++) {
            try {
                String newPassword = generateRandomPassword(length, complexity);
    
                // Check if the hashed password is not in the set of used passwords
                if (!usedPasswords.contains(hashPassword(newPassword))) {
                    saveUsedPassword(newPassword);
                    return newPassword;
                } else {
                    // Track attempted passwords for clearing in case of exhaustion
                    attemptedPasswords.add(newPassword);
                }
            } catch (Exception e) {
                // Clear only the attempted passwords with the same length and complexity
                usedPasswords.removeAll(attemptedPasswords);
            }
        }
    
        // If all possibilities are exhausted, you might want to handle this situation (e.g., throw an exception)
        throw new RuntimeException("All possible passwords have been exhausted.");
    }
    
    
    

    private long calculateMaxPossiblePasswords(int length, int complexity) {
        long totalPossiblePasswords = 1;
        String allCharacterTypes = LOWERCASE_CHARS + UPPERCASE_CHARS + DIGITS + SPECIAL_CHARS;

        for (int i = 0; i < length; i++) {
            if (complexity <= 2) {
                totalPossiblePasswords *= LOWERCASE_CHARS.length();
            } else if (complexity <= 5) {
                totalPossiblePasswords *= allCharacterTypes.length();
            } else if (complexity <= 8) {
                totalPossiblePasswords *= (allCharacterTypes + DIGITS).length();
            } else {
                totalPossiblePasswords *= (allCharacterTypes + DIGITS + SPECIAL_CHARS).length();
            }
        }

        return totalPossiblePasswords;
    }

    private static String generateRandomPassword(int length, int complexity) {
        SecureRandom random = new SecureRandom();
        byte[] passwordBytes = new byte[length];
        random.nextBytes(passwordBytes);

        StringBuilder password = new StringBuilder();

        for (byte b : passwordBytes) {
            char selectedChar = chooseCharacterType(complexity, b);
            password.append(selectedChar);
        }

        return password.toString();
    }

    private static char chooseCharacterType(int strengthValue, byte randomByte) {
        int value = Math.abs(randomByte);
        if (strengthValue <= 2) {
            return LOWERCASE_CHARS.charAt(value % LOWERCASE_CHARS.length());
        } else if (strengthValue <= 5) {
            return (LOWERCASE_CHARS + UPPERCASE_CHARS).charAt(value % (LOWERCASE_CHARS + UPPERCASE_CHARS).length());
        } else if (strengthValue <= 8) {
            return (LOWERCASE_CHARS + UPPERCASE_CHARS + DIGITS).charAt(value % (LOWERCASE_CHARS + UPPERCASE_CHARS + DIGITS).length());
        } else {
            return (LOWERCASE_CHARS + UPPERCASE_CHARS + DIGITS + SPECIAL_CHARS).charAt(value % (LOWERCASE_CHARS + UPPERCASE_CHARS + DIGITS + SPECIAL_CHARS).length());
        }
    }

    private void saveUsedPassword(String password) {
        // Save the password to a persistent storage (e.g., a file or a database)
        // You can use hashing to store passwords securely
        try (PrintWriter writer = new PrintWriter(new FileWriter("used_passwords.txt", true))) {
            String hashedPassword = hashPassword(password);
            writer.println(hashedPassword);
            usedPasswords.add(hashedPassword);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    private void loadUsedPasswords() {
        // Load used passwords from persistent storage
        try (BufferedReader reader = new BufferedReader(new FileReader("used_passwords.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                usedPasswords.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String hashPassword(String password) {
        // Use a cryptographic hash function to securely store passwords
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder hexStringBuilder = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            hexStringBuilder.append(String.format("%02x", b));
        }
        return hexStringBuilder.toString();
    }

    @Override
    public String toString() {
        return "PasswordGenerator{" +
                "length=" + length +
                ", complexity=" + complexity +
                ", usedPasswords=" + usedPasswords +
                '}';
    }

  
    public static void main(String[] args) {
        // Create an instance of PasswordGenerator
        PasswordGenerator passwordGenerator = new PasswordGenerator();

        // Set length and complexity outside the class
        passwordGenerator.setLength(5);
        passwordGenerator.setComplexity(4);

        // Generate and print the password
        String generatedPassword = passwordGenerator.generatePassword();
        System.out.println("Generated Password: " + generatedPassword);
    }

}


