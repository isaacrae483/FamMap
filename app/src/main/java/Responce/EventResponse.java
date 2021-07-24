package Responce;

import java.util.Set;

import Model.Event;

public class EventResponse extends Response{
    Event[] data;
    public EventResponse(Set<Event> events){
        //loads elements from set into array
        data = new Event[events.size()];
        int i = 0;
        for(Event x : events){
            data[i] = x;
            i++;
        }
    }

    public Event[] getData() {
        return data;
    }
}
