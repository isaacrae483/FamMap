package Responce;

public class LoginResponse extends Response{
    String authToken;
    String userName;
    String id;

    /**
     * loginResponse constructor
     * @param a auth token
     * @param u username
     * @param i id
     */
    public LoginResponse(String a, String u, String i){
        authToken = a;
        userName = u;
        id = i;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getUserName() {
        return userName;
    }

    public String getId() {
        return id;
    }
}