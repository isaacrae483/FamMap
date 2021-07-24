package Requests;

public class LoginRequest {
    String userName;
    String password;

    public LoginRequest(String u, String p){
        userName = u;
        password = p;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
