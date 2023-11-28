import java.util.Scanner;


public class RunProject 
{
    public static void main(String[] args) 
    {
        Scanner option = new Scanner(System.in);                                                                            // Create Scanner variable
        System.out.println("Hello There!");                                                                                 // Greetings
        boolean loop1 = true;                                                                                               // Create boolean loop1, set to true
        boolean loop2 = true;                                                                                               // Create boolean loop1, set to true 
        while (loop1)                                                                                                       // While loop based on user input
        {
            System.out.println("Please type in one of the following options (1, 2, 3):");                                   // Asks user to choose option 1, option 2, or option 3
            System.out.println("1.Strength Checker");                                                                       // Option 1: Password Strength Checker
            System.out.println("2.Generate Password");                                                                      // Option 2: Password Generator  
            System.out.println("3.Quit");                                                                                   // Option 3: Exit Program
            int userinput = option.nextInt();                                                                               // Create int variable "userinput" that takes in user input 
            switch (userinput)                                                                                              // Switch case where case is based on "userinput"
            {
                case 1:                                                                                                     // Case 1 (Password Strength Checker)
                    System.out.println("Which of the following Strength Checkers would you like to use (1 or 2)?");         // Asks user to choose option 1 or option 2
                    System.out.println("1.Simple Strength Checker");                                                        // Option 1: Simple Strength Checker
                    System.out.println("2.Complex Strength Checker");                                                       // Option 2: Complex Strength Checker
                    int strengthinput = option.nextInt();                                                                   // Create int variable "strengthinput" that takes in user input
                    switch (strengthinput) {                                                                                // Switch case where case is based on "strengthinput"
                        case 1:
                            System.out.println("Enter your password");                                                      // Ask for user's password
                            String passwordSimple = option.next();                                                          // Create String variable "passwordSimple" that takes in user input
                            CheckPassword passwordChecker = new CheckPassword();                                            // Create CheckPassword variable "passwordChecker" (from CheckPassword.java)
                            String ratingMessage = passwordChecker.checkStrength(passwordSimple);                           // Create String variable "ratingMessage" that runs checkStrength() from "passwordChecker"
                            System.out.println(ratingMessage);                                                              // Prints out value from "ratingMessage" variable  
                            break;                                                                                          // break case

                        case 2:
                            System.out.println("Enter your password");                                                      // Ask for user's password
                            String passwordComplex = option.next();                                                         // Create String variable "passwordComplex" that takes in user input
                            ComplexStrengthChecker complex = new ComplexStrengthChecker();                                  // Create CheckPassword variable "complex" (from ComplexStrengthChecker.java)
                            String assessment = complex.checkStrength(passwordComplex);                                     // Create String variable "assessment" that runs checkStrength() from "ComplexStrengthChecker"
                            System.out.println(assessment);                                                                 // Prints out value from "ratingMessage" variable 
                            break;                                                                                          // break case

                        default:
                            System.out.println("Invalid option, please type in 1 or 2");                                    // Error message
                            break;                                                                                          // break case
                    }
                    break;                                                                                                  // break case

                case 2:                                                                                                     // Case 2 (Password Generator)      
                    PasswordGenerator passwordgenerate = new PasswordGenerator();                                           // Create PasswordGenerator variable "passwordgenerate" (from PasswordGenerator.java)
                    System.out.println("How long would you like the password(s) to be? (Enter integer)");                   // Asks user for password(s) length
                    int length = option.nextInt();                                                                          // Create int variable "length" that takes in user input  
                    passwordgenerate.setLength(length);                                                                     // Runs setLength() from passwordgenerate
                    System.out.println("How complex would you like the password(s) to be? (Enter integer)");                // Asks user for password(s) complexity
                    int complexity = option.nextInt();                                                                      // Create int variable "complexity" that takes in user input  
                    passwordgenerate.setComplexity(complexity);                                                             // Runs setComplexity() from passwordgenerate
                    System.out.println("How many passwords would you like to generate?");                                   // Asks user for number of passwords
                    int n = option.nextInt();                                                                               // Create int variable "n" that takes in user input
                    for (int i = 0; i < n; i++) {                                                                           // For loop
                        String generatedpassword = passwordgenerate.generatePassword();                                     // Create String variable "generatedPassword" that runs generatePassword() from "passwordgenerate"
                        System.out.println("Generated Password: " + generatedpassword);                                     // Prints out generated password
                    }
                    break;                                                                                                  // break case

                case 3:                                                                                                     // Case 3 (Exit Program)
                    System.out.println("Exiting program...");                                                               // Exit Message
                    loop1 = false;                                                                                          // Sets loop1 to false to exit while loop
                    break;                                                                                                  // break case 

                default:                                                                                                    // default case
                    System.out.println("Invalid option, please type in 1, 2, or 3");                                        // Tells user to input valid options
                    loop2 = false;                                                                                          // Sets loop2 to false to not run rerun request while loop
                    break;                                                                                                  // break case
            }
            
            while (loop2)                                                                                                   // While loop to request to rerun program
                {
                    System.out.println("Would you like to rerun the program? (y/n)");                                       // Asks user to rerun program with y or n
                    String response = option.next();                                                                        // Create String variable "response" that takes in user input
                    if (response.equals("y"))                                                                               // If statement: If "y" is inputted
                    {
                        System.out.println("Restarting program...");                                                        // Resetting program message
                    }
                    else if (response.equals("n"))                                                                          // If else statement: If "n" is inputted
                    {
                        System.out.println("Exiting program...");                                                           // Exitting program message
                        loop1 = false;                                                                                      // Sets loop1 to false to exit while loop
                    } 
                    else                                                                                                    // Else Statement
                    {
                    System.out.println("Invalid option, please type y or n");                                               // Tells user to input valid options
                    }
                }
            
        }
        option.close();                                                                                                     // Close the Scanner outside the loop when you're done with it
    }
}
