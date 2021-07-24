package Model;

/**
 * User
 */

public class User {

    private static User _instance;
    public static User instance() {
        if (_instance == null){
            _instance = new User();
        }
        return _instance;
    }

    private User(){//needed to keep track of user id saved here in singleton

    }



    String userName;
    String password;
    String email;
    String firstName;
    String lastName;
    String gender;
    String personID;

    /**
     * constructor
     * @param u userName
     * @param p password
     * @param e email
     * @param f first name
     * @param l last name
     * @param g gender
     * @param i personID
     */

    public User(String u, String p, String e, String f, String l, String g, String i){
        userName = u;
        password = p;
        email = e;
        firstName = f;
        lastName = l;
        gender = g;
        personID = i;
    }

    public String getUsername() {
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

    public String getId() {
        return personID;
    }

    public void setId(String personID) {
        this.personID = personID;
    }
}
