import java.util.Scanner;
import java.util.InputMismatchException;

public class Entry {
       
 public static String isValidUsername() throws InputMismatchException {
    String username="";
    Scanner s = new Scanner(System.in);

        /*user inserts his username until is valid */
    System.out.println("Give your username");
    username = s.nextLine();

            /* check that the input is not only numbers*/
        if (username.matches("\\d+")) {
            throw new InputMismatchException("Username cannot be a number");
            }

            /*check that username has some constrains */
        if (username.length() > 4 && username.length() < 15 && Character.isLetter(username.charAt(0)) &&
               username.matches("[A-Za-z0-9_]+")) {
                return username;     
            }
        else {
            throw new InputMismatchException("Username must start with a letter, be 5–14 characters long, and contain only letters, digits, or underscores.");
        }
    }

public static String isValidPassword() throws NotCorrectPassword{
    boolean isvalid=false;
    String password="";
    Scanner s = new Scanner(System.in);
    /*user inserts his password */
    do {
            System.out.println("Give me your Password");
            System.out.println("it must contain one UPPER letter, one lower letter, one number and it has to be over 5 digits");
            password=s.nextLine();
            /*check that password is strong */
                if (password.length()>=6 && password.matches(".*[A-Z].*") && password.matches(".*[a-z].*") && password.matches(".*[0-9].*")) {
                    isvalid=true;
                }
                else {
                    throw new NotCorrectPassword("Password does not meet security requirements.");
                }
            
        } while (!isvalid);
    
        return password;
   
}
    
}