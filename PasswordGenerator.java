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

    // allows for main to set length
    public void setLength(int length) {
        this.length = length;
    }
    // allows for main to set complexity
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
        throw new RuntimeException("All possible passwords have been exhausted. Please choose a different complexity or length");
    }

    // find the number of possible outcomes so that you can find out when all possibilities are exhausted
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

    // create the password and convert it to a string to be displayed to the user
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
   
    // create the password based on the length and complexity requirements
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

    // Save the password to a persistent storage
    // You can use hashing to store passwords securely

    private void saveUsedPassword(String password) {
        
        try (PrintWriter writer = new PrintWriter(new FileWriter("used_passwords.txt", true))) {
            String hashedPassword = hashPassword(password);
            writer.println(hashedPassword);
            usedPasswords.add(hashedPassword);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load used passwords from persistent storage so that they can be compared with current passwords to avoid
    // the same password being generated twice

    private void loadUsedPasswords() {
        
        try (BufferedReader reader = new BufferedReader(new FileReader("used_passwords.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                usedPasswords.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Use a cryptographic hash function to securely store passwords in a way that is difficult to decode
    // SHA-256 is used in this case

    private String hashPassword(String password) {
        
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Convert a byte array to a hexadecimal string representation

    private String bytesToHex(byte[] bytes) {
        
        StringBuilder hexStringBuilder = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            hexStringBuilder.append(String.format("%02x", b));
        }
        return hexStringBuilder.toString();
    }

    // Provide a string representation of the PasswordGenerator object

    @Override
    public String toString() {
        
        return "PasswordGenerator{" +
                "length=" + length +
                ", complexity=" + complexity +
                ", usedPasswords=" + usedPasswords +
                '}';
    }

   
}

