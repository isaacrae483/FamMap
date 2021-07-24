package Requests;

public class RegisterRequest {
    String userName;
    String password;
    String email;
    String firstName;
    String lastName;
    String gender;

    public RegisterRequest(String u, String p, String e, String f, String l, String g){
        userName = u;
        password = p;
        email = e;
        firstName = f;
        lastName = l;
        gender = g;

    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }
}
