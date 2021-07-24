package com.example.irae4.fammap;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Model.Model;
import Model.Person;
import Model.Event;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView mEventRecyclerView;
    private RecyclerView mPersonRecyclerView;
    private EventAdapter mEventAdapter;
    private PersonAdapter mPersonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
//wire up widgets
        mEventRecyclerView = (RecyclerView)findViewById(R.id.event_search_recycler_view);
        mEventRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPersonRecyclerView = (RecyclerView)findViewById(R.id.person_search_recycler_view);
        mPersonRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    private void updateView(String str){//updates search results
        updateEventUI(filterEvents(str.toLowerCase()));
        updatePersonUI(filterPersons(str.toLowerCase()));

    }

    private Set filterEvents(String str){//checks to see if contains given search substring
        Set<Event> filteredEvents = new HashSet<>();
        for(Event x : Model.instance().getEvents().values()){
            if(Model.instance().approve(x.getpId(), x.getType())){
                if(x.getType().toLowerCase().contains(str)){//in type
                    filteredEvents.add(x);
                }
                if(x.getCity().toLowerCase().contains(str)){//in city
                    filteredEvents.add(x);
                }
                if(x.getCountry().toLowerCase().contains(str)){// in country
                    filteredEvents.add(x);
                }
                if(Integer.toString(x.getYear()).toLowerCase().contains(str)){//in year
                    filteredEvents.add(x);
                }
            }
        }

        return filteredEvents;
    }

    private Set filterPersons(String str){//checks if contains given substring
        Set<Person> filteredPersons = new HashSet<>();
        for(Person x : Model.instance().getPersons().values()){
            if(Model.instance().approve(x.getId(), null)){
                if(x.getFirstName().toLowerCase().contains(str)){//in first name
                    filteredPersons.add(x);
                }
                if(x.getLastName().toLowerCase().contains(str)){//in last name
                    filteredPersons.add(x);
                }
            }
        }
        return filteredPersons;
    }


    private void updateEventUI(Set<Event> set){
        mEventAdapter = new EventAdapter(set);
        mEventRecyclerView.setAdapter(mEventAdapter);
    }
    private void updatePersonUI(Set<Person> set){
        mPersonAdapter = new PersonAdapter(set);
        mPersonRecyclerView.setAdapter(mPersonAdapter);
    }

    private class EventAdapter extends RecyclerView.Adapter<EventHolder>{
        private List<Event> mEvents = new ArrayList<>();
        public EventAdapter(Set<Event> events){//given list of events containing the searched substring
            for(Event x : events){
                mEvents.add(x);
            }
        }
        @Override
        public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            return new EventHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(EventHolder eventHolder, int position) {
            Event event = mEvents.get(position);//gets correct event
            eventHolder.bind(event);
        }

        @Override
        public int getItemCount() {
            return mEvents.size();
        }
    }

    private class EventHolder extends RecyclerView.ViewHolder{
        ImageView mEventIcon;
        TextView mEventType;
        TextView mEventPlace;
        TextView mEventYear;
        LinearLayout mEventListLayout;

        private Event mEvent;

        ViewGroup mParent;

        public EventHolder(LayoutInflater inflater, ViewGroup parent){//wires up widgets
            super(inflater.inflate(R.layout.event_list, parent, false));
            mParent = parent;
            mEventIcon = (ImageView)itemView.findViewById(R.id.event_icon);
            Drawable eventIcon = new IconDrawable(parent.getContext(), FontAwesomeIcons.fa_map_marker).sizeDp(40);
            mEventIcon.setImageDrawable(eventIcon);
            mEventType = (TextView)itemView.findViewById(R.id.type_title);
            mEventPlace = (TextView)itemView.findViewById(R.id.place_title);
            mEventYear = (TextView)itemView.findViewById(R.id.year_title);
            mEventListLayout = (LinearLayout)itemView.findViewById(R.id.event_list_layout);
            mEventListLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Model.instance().setActiveEvent(mEvent);
                    if(Model.instance().getActiveEvent() != null){
                        Intent intent = new Intent(mParent.getContext(), EventActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }

        public void bind(Event event){//updates data
            mEvent = event;
            mEventType.setText(mEvent.getType());
            mEventPlace.setText(mEvent.getCity() + ", " + mEvent.getCountry());
            mEventYear.setText(Integer.toString(mEvent.getYear()));
        }
    }

    private class PersonAdapter extends RecyclerView.Adapter<PersonHolder>{
        private List<Person> mPersons = new ArrayList<>();
        public PersonAdapter(Set<Person> persons){//given list of Persons contaning the given substring
            for(Person x : persons){
                mPersons.add(x);
            }
        }
        @Override
        public PersonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            return new PersonHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(PersonHolder personHolder, int position) {
            Person person = mPersons.get(position);//gets correct person
            personHolder.bind(person);
        }

        @Override
        public int getItemCount() {
            return mPersons.size();
        }
    }

    private class PersonHolder extends RecyclerView.ViewHolder{
        ImageView mGenderIcon;
        TextView mPersonName;
        TextView mPersonRelation;
        LinearLayout mPersonListLayout;

        private Person mPerson;

        ViewGroup mParent;

        public PersonHolder(LayoutInflater inflater, ViewGroup parent){//wires up widgets
            super(inflater.inflate(R.layout.person_list, parent, false));
            mParent = parent;
            mGenderIcon = (ImageView)itemView.findViewById(R.id.gender_icon);
            mPersonName = (TextView)itemView.findViewById(R.id.name_title);
            mPersonRelation = (TextView)itemView.findViewById(R.id.relation_title);
            mPersonListLayout = (LinearLayout)itemView.findViewById(R.id.person_list_layout);
            mPersonListLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(Event x : Model.instance().getPersonEvents(mPerson.getId())){
                        Model.instance().setActiveEvent(x);
                        break;
                    }
                    if(Model.instance().getActiveEvent() != null){
                        Intent intent = new Intent(mParent.getContext(), PersonActivity.class);
                        startActivity(intent);
                    }
                }
            });

        }

        public void bind(Person person){//sets view to correct data
            mPerson = person;
            if(findGender(person.getId())){
                Drawable genderIcon = new IconDrawable(mParent.getContext(), FontAwesomeIcons.fa_male).sizeDp(40);
                mGenderIcon.setImageDrawable(genderIcon);
            }
            else{
                Drawable genderIcon = new IconDrawable(mParent.getContext(), FontAwesomeIcons.fa_female).sizeDp(40);
                mGenderIcon.setImageDrawable(genderIcon);
            }
            mPersonName.setText(mPerson.getFirstName() + " " + mPerson.getLastName());
            mPersonRelation.setText("");
        }
    }

    private Boolean findGender(String id){//returns gender
        Person person = Model.instance().getPersons().get(id);
        if(person.getGender().equals("m")){
            return true;//true male
        }
        else return false;//false female
    }



//adds search bar to tool bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);


        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView mSearch = (SearchView)searchItem.getActionView();
        mSearch.setIconified(false);

        mSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String s) {
                updateView(s);//updates list of people and events when search submitted
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return true;
    }

    //overrides up button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            startTopActivity(this, false);
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }

    public static void startTopActivity(Context context, boolean newInstance) {
        Intent intent = new Intent(context, MainActivity.class);
        if (newInstance) {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//starts new main activity
        }
        else {
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);//starts old main activity
        }
        context.startActivity(intent);
    }

}
