package Model;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import Model.Person;
import Model.User;
import Model.Event;

public class Model {
    private static Model _instance;
    public static Model instance() {
        if (_instance == null){
            _instance = new Model();
        }
        return _instance;
    }


    private Model(){
        activeEvent = null;
//variable of colors and ifShown initiated
        storyColor = Color.BLACK;
        treeColor = Color.RED;
        spouseColor = Color.BLUE;
        storyShow = true;
        treeShow = true;
        spouseShow = true;
        fatherShow = true;
        motherShow = true;
        maleShow = true;
        femaleShow = true;
        mapType = 0;
    }
    //variable of colors and ifShown
    private int storyColor;
    private int treeColor;
    private int spouseColor;
    private boolean storyShow;
    private boolean treeShow;
    private boolean spouseShow;

    private boolean fatherShow;
    private boolean motherShow;
    private boolean maleShow;
    private boolean femaleShow;

    private int mapType;

    private boolean changed;
//sets and maps of everything ill need
    Map<String, Person> persons = new HashMap<>();
    Map<String, Event> events = new HashMap<>();


    Event activeEvent;

    Map<Marker, Event> markerList = new HashMap<>();
    Map<String, Float> markerColors = new HashMap<>();
    List<Polyline> lines = new ArrayList<>();


    Set<String> eventTypes = new TreeSet<>();

    Set<String> fatherSide = new HashSet<>();
    Set<String> motherSide = new HashSet<>();

    Map<String, Boolean> eventTypeShow = new HashMap<>();


    public void addPersons(Person[] personList){//initiate person list
        persons.clear();
        for (int i = 0; i < personList.length; i++){
            persons.put(personList[i].getId(), personList[i]);
        }
    }
    public void addEvents(Event[] eventList){//initiate event list
        events.clear();
        for (int i = 0; i < eventList.length; i++){
            events.put(eventList[i].geteId(), eventList[i]);
        }
    }
    public void create(){
        for(Event x : events.values()){//gathers event types
            eventTypes.add(x.getType().toLowerCase());
        }
        for(String x : eventTypes) {//sets each event type to show
            x = x.toLowerCase();
            eventTypeShow.put(x,true);
        }
        String id = User.instance().getId();//initiates the rest of the data structures
        Person user = persons.get(id);
        String fatherId = user.getfId();
        makeFatherSide(fatherId);
        String motherId = user.getmId();
        makeMotherSide (motherId);
        makeMarkerColors();
    }

    private void makeMarkerColors(){//sets the color for each event type
        float color = 00.0f;
        for(String x : eventTypes){
            markerColors.put(x, color);
            color += 10.0f;
        }
    }

    private void makeFatherSide(String id){//father side events
        if(id == null)
            return;
        fatherSide.add(id);
        Person father = persons.get(id);
        makeFatherSide(father.getfId());
        makeFatherSide(father.getmId());
    }
    private void makeMotherSide(String id){//mother side events
        if(id == null)
            return;
        motherSide.add(id);
        Person mother = persons.get(id);
        makeMotherSide(mother.getfId());
        makeMotherSide(mother.getmId());
    }



    public boolean approve(String ID, String eventType){//checks if event is actually shown on the map
        if(ID != null){
            Person person = persons.get(ID);
            if(person.getGender().equals("m") && !isMaleShow())
                return false;
            if(person.getGender().equals("f") && !isFemaleShow())
                return false;
            if(fatherSide.contains(person.getId()) && !isFatherShow())
                return false;
            if(motherSide.contains(person.getId()) && !isMotherShow())
                return false;
        }
        if(eventType != null){
            if(!eventTypeShow.get(eventType.toLowerCase()))
                return false;
        }
        return true;
    }

    public Set<Event> getPersonEvents(String ID){//gets all events related to a person
        Set<Event> pEvents = new TreeSet<>();
        for(Event x : events.values()){
            if(x.getpId().equals(ID)){
                if(approve(ID, x.getType()))
                    pEvents.add(x);
            }
        }
        return pEvents;
    }
    public Set<Event> getSpouseEvents(String ID){//gets spouses events
        String spouseID = persons.get(ID).getsId();
        if(spouseID == null){
            return null;
        }
        return getPersonEvents(spouseID);
    }
    public Set<Event> getFatherEvents(String ID){//gets the events
        String fatherID = persons.get(ID).getfId();
        if(fatherID == null){
            return null;
        }
        return getPersonEvents(fatherID);
    }
    public Set<Event> getMotherEvents(String ID){//gets mothers events
        String motherID = persons.get(ID).getmId();
        if(motherID == null){
            return null;
        }
        return getPersonEvents(motherID);
    }
    //all other getters and setters
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setEventTypeShow(String eventType, boolean show){
        eventTypeShow.replace(eventType, show);
    }
    public boolean getEventTypeShow(String eventType){
        return eventTypeShow.get(eventType.toLowerCase());
    }


    public Map<String, Event> getEvents() {
        return events;
    }

    public Map<String, Person> getPersons() {
        return persons;
    }

    public Event getActiveEvent() {
        return activeEvent;
    }
    public Event setActiveEvent(Event newEvent) {
        return activeEvent = newEvent;
    }

    public Set<Person> getRelationPersons(){//get all people related to the selected person that aren't filtered out
        Set<Person> relatedPersons = new HashSet<>();
        String pID = activeEvent.getpId();
        String pfID = persons.get(pID).getfId();
        String pmID = persons.get(pID).getmId();
        String psID = persons.get(pID).getsId();

        for(Person x : persons.values()){
            if(approve(x.getId(), null)){
                if(pfID != null){
                    if(pfID.equals(x.getId())){
                        relatedPersons.add(x);
                    }
                }
                //curr mother of rel
                if(pmID != null){
                    if(pmID.equals(x.getId())){
                        relatedPersons.add(x);
                    }
                }
                //curr spouse of rel
                if(psID != null){
                    if(psID.equals(x.getId())){
                        relatedPersons.add(x);
                    }
                }
                //rel father curr
                if(x.getfId() != null){
                    if(pID.equals(x.getfId())){
                        relatedPersons.add(x);
                    }
                }
                //rel mother curr
                if(x.getmId() != null){
                    if(pID.equals(x.getmId())){
                        relatedPersons.add(x);
                    }
                }
            }
        }

        return relatedPersons;
    }

    public String findRelation(Person relPerson){//determines the relationship between tha active person and the loaded person
        String relId = relPerson.getId();
        String relfId = relPerson.getfId();
        String relmId = relPerson.getmId();
        Person currPerson = Model.instance().getPersons().get(Model.instance().getActiveEvent().getpId());
        String currId = currPerson.getId();
        String currfId = currPerson.getfId();
        String currmId = currPerson.getmId();
        String currsId = currPerson.getsId();
        //curr father of rel
        if(currfId != null){
            if(currfId.equals(relId)){
                return "Father";
            }
        }
        //curr mother of rel
        if(currfId != null){
            if(currmId.equals(relId)){
                return "Mother";
            }
        }
        //curr spouse of rel
        if(currsId != null){
            if(currsId.equals(relId)){
                return "Spouse";
            }
        }
        //rel father curr
        if(relfId != null){
            if(currId.equals(relfId)){
                return "Child";
            }
        }
        //rel mother curr
        if(relmId != null){
            if(currId.equals(relmId)){
                return "Child";
            }
        }
        return "No Relation";
    }

    public Map<Marker, Event> getMarkerList() {
        return markerList;
    }

    public List<Polyline> getLines() {
        return lines;
    }

    public Float getMarkerColor(String type){
        return markerColors.get(type);
    }

    public int getStoryColor() {
        return storyColor;
    }

    public void setStoryColor(int storyColor) {
        this.storyColor = storyColor;
    }

    public int getTreeColor() {
        return treeColor;
    }

    public void setTreeColor(int treeColor) {
        this.treeColor = treeColor;
    }

    public int getSpouseColor() {
        return spouseColor;
    }

    public void setSpouseColor(int spouseColor) {
        this.spouseColor = spouseColor;
    }

    public boolean isStoryShow() {
        return storyShow;
    }

    public void setStoryShow(boolean storyShow) {
        this.storyShow = storyShow;
    }

    public boolean isTreeShow() {
        return treeShow;
    }

    public void setTreeShow(boolean treeShow) {
        this.treeShow = treeShow;
    }

    public boolean isSpouseShow() {
        return spouseShow;
    }

    public void setSpouseShow(boolean spouseShow) {
        this.spouseShow = spouseShow;
    }

    public boolean isFatherShow() {
        return fatherShow;
    }

    public void setFatherShow(boolean fatherShow) {
        this.fatherShow = fatherShow;
    }

    public boolean isMotherShow() {
        return motherShow;
    }

    public void setMotherShow(boolean motherShow) {
        this.motherShow = motherShow;
    }

    public boolean isMaleShow() {
        return maleShow;
    }

    public void setMaleShow(boolean maleShow) {
        this.maleShow = maleShow;
    }

    public boolean isFemaleShow() {
        return femaleShow;
    }

    public void setFemaleShow(boolean femaleShow) {
        this.femaleShow = femaleShow;
    }

    public Set<String> getEventTypes() {
        return eventTypes;
    }

    public int getMapType() {
        return mapType;
    }

    public void setMapType(int mapType) {
        this.mapType = mapType;
    }

    public boolean getChanged(){
        return changed;
    }

    public void setChanged(boolean changed){
        this.changed = changed;
    }
}
