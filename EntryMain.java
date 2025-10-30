import java.util.InputMismatchException; 
public class EntryMain{
public static void main(String[] args) {
    boolean usernameOk = false;
    String username = "";
    while (!usernameOk) {
        try {
            username = Entry.isValidUsername();
            System.out.println("usernmane accepted as: " + username);
            usernameOk = true;
         }  
          /*exception if user give only integers */
        catch (InputMismatchException e) {
            System.out.println("Error " + e.getMessage());
            System.out.println("Please try again");
        }
    }    
       boolean passwordOk = false;
        String password = "";

        while (!passwordOk) {
            try {
                password = Entry.isValidPassword();
                System.out.println("Password accepted as: " + password);
                passwordOk = true; // βγαίνει από το loop
            } 
            catch (NotCorrectPassword e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Please try again.\n");
            }
        }
}
}
