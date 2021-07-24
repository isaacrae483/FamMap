package Model;

public class AuthToken {
    String userId;
    String token;

    /**
     * constructor for AuthToken
     * @param u     userId
     * @param t     token
     */
    public AuthToken(String u, String t){
        userId = u;
        token = t;
    }
    public String getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }




}
