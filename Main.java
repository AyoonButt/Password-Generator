import java.util.Scanner;

public interface  checkPasswordStrength
  {
    public int checkStrength();
  }

public class Main 
{
  public static void main(String[] args) 
  {
      Scanner option = new Scanner(System.in);
      System.out.println("Hello There!");
      System.out.println("Please type in one of the following options (1, 2, 3):");
      System.out.println("1.Strength Checker");
      System.out.println("2.Generate Password");
      System.out.println("3.Quit");
      int userinput = option.nextInt();
      boolean loop = true;
      while (loop)
        {
          switch(userinput) 
          {
            case 1:
            System.out.println("Which of the following Strngth Checkers would you like to use (1 or 2)?");
            System.out.println("1.Simple Strength Checker");
            System.out.println("2.Complex Strength Checker");
            int strengthinput = option.nextInt();
            switch(strengthinput) 
            {
              case 1:
                    System.out.println("Enter your password");
                    String passwordSimple = option.next();
                    CheckPassword Simple = new CheckPassword();
                    int rating = Simple.calculateStrength(passwordSimple);
                    System.out.println("The password strength will be rated (1-10),your rating is : " + rating);
                    System.out.println("Would you like to rerun the program? (y/n)");
                    String responsesimple = option.next();
                    if (responsesimple.equals("y")) 
                    {
                        break;
                    }
                    else if (responsesimple.equals("n"))
                    {
                        userinput = 3;
                        break;
                    }
                    else 
                    {
                        System.out.println("Invalid option");
                        userinput = 3;
                        break;
                    }
              case 2:
                    System.out.println("Enter your password");
                    String passwordComplex = option.next();
                    ComplexStrengthChecker Complex = new ComplexStrengthChecker();
                    String assessment = Complex.assessPassword(passwordComplex);
                    System.out.println(assessment);
                    System.out.println("Would you like to rerun the program? (y/n)");
                    String responsecomplex = option.next();
                    if (responsecomplex.equals("y")) 
                    {
                        break;
                    }
                    else if (responsecomplex.equals("n"))
                    {
                        userinput = 3;
                        break;
                    }
                    else 
                    {
                        System.out.println("Invalid option");
                        userinput = 3;
                        break;
                    }
              default:
                    System.out.println("Invalid option, please type in 1 or 2");
                    break;
            }
            break;
            case 2:
            PasswordGenerator passwordgenerate = new PasswordGenerator();
            System.out.println("How long would you like the password(s) to be? (Enter integer)");
            int length = option.nextInt();
            passwordgenerate.setLength(length);
            System.out.println("How complex would you like the password(s) to be? (Enter integer)");
            int complexity = option.nextInt();
            passwordgenerate.setComplexity(complexity);
            System.out.println("How many passwords would you like to generate?");
            int n = option.nextInt();
            for (int i = 0; i < n; i++)
            {
            String generatedpassword = passwordgenerate.generatePassword();
            System.out.println("Generated Password: " + generatedpassword);
            }
            System.out.println("Would you like to rerun the program? (y/n)");
            String responsegenerate = option.next();
            if (responsegenerate.equals("y")) 
            {
                break;
            }
            else if (responsegenerate.equals("n"))
            {
                userinput = 3;
                break;
            }
            else 
            {
                System.out.println("Invalid option");
                userinput = 3;
                break;
            }
            case 3:
            System.out.println("Exiting program...");
            loop = false;
            break;
            default:
            System.out.println("Invalid option, please type in 1, 2, or 3");
            break;
          }
        }
  }
}
