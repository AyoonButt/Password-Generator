

 public class CheckPassword implements PasswordStrengthEvaluator {

    @Override
    public String checkStrength(String input) {
        int rating = calculateStrength(input);
        return "The password strength will be rated (1-10), your rating is: " + rating;
    }

    private int calculateStrength(String input) {
        int length = input.length();
        int strength = 1;
        strength += Math.min(5, length / 4);

        if (input.matches(".*[a-z].*")) {
            strength += 1;
        }
        if (input.matches(".*[A-Z].*")) {
            strength += 2;
        }

        if (input.matches(".*\\d.*")) {
            strength += 2;
        }
        if (input.matches(".*[a-zA-Z].*\\d.*")) {
            strength += 3;
        }

        // Limit strength to a maximum of 10
        return Math.min(10, strength);
    }

}
