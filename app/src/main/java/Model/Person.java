package Model;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Objects;

/**
 * Person
 */

public class Person {
    String personID;
    String descendant;
    String firstName;
    String lastName;
    String gender;
    String father = null;
    String mother = null;
    String spouse = null;

    /**
     * constructor
     * @param i     personID
     * @param d     descendant
     * @param f     first Name
     * @param l     last Name
     * @param g     gender
     */

    public Person(String i, String d, String f, String l, String g){
        personID = i;
        descendant = d;
        firstName = f;
        lastName = l;
        gender = g;
    }

    /**
     * constructor
     * @param i     personID
     * @param d     descendant
     * @param f     first Name
     * @param l     last Name
     * @param g     gender
     * @param s     Spouse
     */
    public Person(String i, String d, String f, String l, String g, String s){
        personID = i;
        descendant = d;
        firstName = f;
        lastName = l;
        gender = g;
        spouse = s;
    }

    /**
     * constructor
     * @param i     personID
     * @param d     descendant
     * @param f     first name
     * @param l     last name
     * @param g     gender
     * @param s     spouse
     * @param mi    mother ID
     * @param fi    father ID
     */
    public Person(String i, String d, String f, String l, String g, String mi, String fi, String s){
        personID = i;
        descendant = d;
        firstName = f;
        lastName = l;
        gender = g;
        father = fi;
        mother = mi;
        spouse = s;
    }

    public String getId() {
        return personID;
    }

    public void setId(String personID) {
        this.personID = personID;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getfId() {
        return father;
    }

    public void setfId(String father) {
        this.father = father;
    }

    public String getmId() {
        return mother;
    }

    public void setmId(String mother) {
        this.mother = mother;
    }

    public String getsId() {
        return spouse;
    }

    public void setsId(String spouse) {
        this.spouse = spouse;
    }





    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(personID, person.personID) &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {

        return Objects.hash(personID, firstName, lastName);
    }
}