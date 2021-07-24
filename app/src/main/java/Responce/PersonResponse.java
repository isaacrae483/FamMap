package Responce;

import java.util.Set;

import Model.Person;

public class PersonResponse extends Response {
    Person[] data;
    public PersonResponse(Set<Person> persons){
        //load people from set into array
        data = new Person[persons.size()];
        int i = 0;
        for(Person x : persons){
            data[i] = x;
            i++;
        }
    }

    public Person[] getData() {
        return data;
    }
}
