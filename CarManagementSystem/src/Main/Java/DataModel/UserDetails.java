package Main.Java.DataModel;

public class UserDetails {
    private String userID;

    public String GetUserId() {
        return this.userID;
    }

    public String UserName;
    public String Password;
    public String Role;
    public String MobileNumber;
    public String Email;

    public UserDetails(String Userid, String username, String password, String role, String mobilenumber,
            String email) {
        this.userID = Userid;
        this.UserName = username;
        this.Password = password;
        this.Role = role;
        this.MobileNumber = mobilenumber;
        this.Email = email;
    }

    public UserDetails() {

    }
}
