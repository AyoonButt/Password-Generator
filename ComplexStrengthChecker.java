import java.util.ArrayList;
import java.util.List;


public class ComplexStrengthChecker implements PasswordStrengthEvaluator {
   
    @Override
    public String checkStrength(String password) {
        return assessPassword(password);
    }
    // Method to calculate the strength of a password and provide feedback
    
    private String assessPassword(String password) {
        List<String> feedback = new ArrayList<>();
        int strengthScore = 0;

        strengthScore += calculateLengthScore(password, feedback);
        strengthScore += calculateCharacterDiversityScore(password, feedback);
        strengthScore += calculatePatternRecognitionScore(password, feedback);
        strengthScore += calculateEntropyScore(password, feedback);

        String overallFeedback = "Password strength (1-10): " + Math.min(10, strengthScore) + ". ";
        overallFeedback += String.join(" ", feedback);

        return overallFeedback;
    }

    // Length check with feedback
    private int calculateLengthScore(String password, List<String> feedback) {
        int length = password.length();
        int score = Math.min(5, length / 4);
        if (length < 8) {
            feedback.add("Password is too short. Consider using more characters.");
        }
        return score;
    }

    // Character diversity check with feedback
    private int calculateCharacterDiversityScore(String password, List<String> feedback) {
        int score = 0;
        if (!password.matches(".*[a-z].*")) {
            feedback.add("Add lowercase letters for more security.");
        } else {
            score += 1;
        }
        if (!password.matches(".*[A-Z].*")) {
            feedback.add("Include uppercase letters to strengthen the password.");
        } else {
            score += 2;
        }
        if (!password.matches(".*\\d.*")) {
            feedback.add("Incorporate numbers to improve strength.");
        } else {
            score += 2;
        }
        if (!password.matches(".*[!@#$%^&*()].*")) {
            feedback.add("Use special characters like !,@,#, etc., for better protection.");
        } else {
            score += 3;
        }
        return score;
    }

    // Pattern recognition with feedback
    private int calculatePatternRecognitionScore(String password, List<String> feedback) {
        int penalty = 0;
        String[] commonPatterns = {"123", "abc", "password", "qwerty"};
        for (String pattern : commonPatterns) {
            if (password.toLowerCase().contains(pattern)) {
                penalty += 2;
                feedback.add("Avoid common patterns like '" + pattern + "'.");
            }
        }
        return -penalty;
    }

    // Entropy calculation with feedback
    private int calculateEntropyScore(String password, List<String> feedback) {
        double entropy = 0.0;
        int[] charFrequencies = new int[256];
        for (char c : password.toCharArray()) {
            charFrequencies[c]++;
        }
        for (int frequency : charFrequencies) {
            if (frequency > 0) {
                double probability = (double) frequency / password.length();
                entropy -= probability * (Math.log(probability) / Math.log(2));
            }
        }
        int entropyScore = (int) (entropy / 2);
        if (entropyScore < 3) {
            feedback.add("Your password is too predictable. Try adding more random characters.");
        }
        return entropyScore;
    }

 
}

