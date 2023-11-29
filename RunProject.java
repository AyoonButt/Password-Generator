import java.util.Scanner;

public class RunProject {
    public static void main(String[] args) {
        Scanner option = new Scanner(System.in);
        System.out.println("Hello There!");

        boolean runProgram = true;
        while (runProgram) {
            // Display main menu options
            System.out.println("Please type in one of the following options (1, 2, 3):");
            System.out.println("1. Strength Checker");
            System.out.println("2. Generate Password");
            System.out.println("3. Quit");

            int userChoice = option.nextInt();

            switch (userChoice) {
                case 1: // Password Strength Checker
                    System.out.println("Choose Strength Checker (1 or 2):");
                    System.out.println("1. Simple Strength Checker");
                    System.out.println("2. Complex Strength Checker");
                    int strengthChoice = option.nextInt();

                    switch (strengthChoice) {
                        case 1:
                            checkSimpleStrength(option);
                            break;
                        case 2:
                            checkComplexStrength(option);
                            break;
                        default:
                            System.out.println("Invalid option, please type in 1 or 2");
                            break;
                    }
                    break;

                case 2: // Password Generator
                    generatePasswords(option);
                    break;

                case 3: // Quit Program
                    System.out.println("Exiting program...");
                    runProgram = false;
                    break;

                default:
                    System.out.println("Invalid option, please type in 1, 2, or 3");
                    break;
            }

            if (runProgram) {
                // Ask the user if they want to rerun the program
                System.out.println("Would you like to rerun the program? (y/n)");
                String response = option.next();

                if (response.equals("n")) {
                    System.out.println("Exiting program...");
                    runProgram = false;
                } else if (!response.equals("y")) {
                    System.out.println("Invalid option, please type y or n");
                }
            }
        }

        option.close(); // Close the Scanner to prevent resource leak
    }

    // Method to check password strength using a simple checker
    private static void checkSimpleStrength(Scanner option) {
        System.out.println("Enter your password");
        String passwordSimple = option.next();
        CheckPassword passwordChecker = new CheckPassword();
        String ratingMessage = passwordChecker.checkStrength(passwordSimple);
        System.out.println(ratingMessage);
    }

    // Method to check password strength using a complex checker
    private static void checkComplexStrength(Scanner option) {
        System.out.println("Enter your password");
        String passwordComplex = option.next();
        ComplexStrengthChecker complex = new ComplexStrengthChecker();
        String assessment = complex.checkStrength(passwordComplex);
        System.out.println(assessment);
    }

    // Method to generate passwords
    private static void generatePasswords(Scanner option) {
        PasswordGenerator passwordGenerator = new PasswordGenerator();

        // Ask the user for password length
        System.out.println("How long would you like the password(s) to be? (Enter integer value between 1 and 10)");
        int length = option.nextInt();
        passwordGenerator.setLength(length);

        // Ask the user for password complexity
        System.out.println("How complex would you like the password(s) to be? (Enter integer between 1 and 10)");
        int complexity = option.nextInt();
        passwordGenerator.setComplexity(complexity);

        // Ask the user for the number of passwords to generate
        System.out.println("How many passwords would you like to generate?");
        int n = option.nextInt();

        // Generate and display the passwords
        for (int i = 0; i < n; i++) {
            String generatedPassword = passwordGenerator.generatePassword();
            System.out.println("Generated Password: " + generatedPassword);
        }
    }
}
