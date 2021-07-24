package Model;

import java.util.Objects;

/**
 * Event
 */

public class Event implements Comparable<Event>{

    String eventID;
    String descendant;
    String personID;
    String latitude;
    String longitude;
    String country;
    String city;
    String eventType;
    int year;

    /**
     * constructor
     * @param e     event ID
     * @param d     descendant
     * @param p     person ID
     * @param la    latitude
     * @param lo    logitude
     * @param co    country
     * @param ci    city
     * @param t     type
     * @param y     year
     */

    public Event(String e, String d, String p, String la, String lo, String co, String ci, String t, int y){
        eventID = e;
        descendant = d;
        personID = p;
        latitude = la;
        longitude = lo;
        country = co;
        city = ci;
        eventType = t;
        year = y;
    }

    public String geteId() {
        return eventID;
    }

    public String getDescendant() {
        return descendant;
    }

    public String getpId() {
        return personID;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getType() {
        return eventType;
    }

    public int getYear() {
        return year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return year == event.year &&
                Objects.equals(eventID, event.eventID) &&
                Objects.equals(descendant, event.descendant) &&
                Objects.equals(personID, event.personID);
    }

    @Override
    public int hashCode() {

        return Objects.hash(eventID, descendant, personID, year);
    }

    @Override
    public int compareTo(Event event) {
        if(this.getYear() != event.getYear())
            return this.getYear() - event.getYear();
return -1;
    }


}
