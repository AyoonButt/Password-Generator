import java.util.Scanner;
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
                    break;
              case 2:
                    System.out.println("Enter your password");
                    String passwordComplex = option.next();
                    ComplexStrengthChecker Complex = new ComplexStrengthChecker();
                    String assessment = Complex.assessPassword(passwordComplex);
                    System.out.println(assessment);
                    break;
              default:
                    System.out.println("Invalid option, please type in 1 or 2");
                    break;
            }
        break;
        case 2:
            generatePassword();
            break;
        case 3:
            System.out.println("Exiting program...");
            System.exit(0);
        default:
            System.out.println("Invalid option, please type in 1, 2, or 3");
            break;
      }
  }
}
