package com.example.irae4.fammap;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import Model.Model;
import Model.Event;
import Model.Person;

public class PersonActivity extends AppCompatActivity {

    private RecyclerView mEventRecyclerView;
    private RecyclerView mPersonRecyclerView;
    private EventAdapter mEventAdapter;
    private PersonAdapter mPersonAdapter;

    private ImageView mShowEvents;
    private ImageView mShowPeople;

    TextView mPersonFirst;
    TextView mPersonLast;
    TextView mPersonGender;

    boolean eventShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
//wire up the widgets
        mEventRecyclerView = (RecyclerView)findViewById(R.id.event_recycler_view);
        mEventRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPersonRecyclerView = (RecyclerView)findViewById(R.id.person_recycler_view);
        mPersonRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        mPersonFirst = (TextView)findViewById(R.id.person_first);
        mPersonFirst.setText(Model.instance().getPersons().get(Model.instance().getActiveEvent().getpId()).getFirstName().toUpperCase());
        mPersonLast = (TextView)findViewById(R.id.person_last);
        mPersonLast.setText(Model.instance().getPersons().get(Model.instance().getActiveEvent().getpId()).getLastName().toUpperCase());
        mPersonGender = (TextView)findViewById(R.id.person_gender);
        if(Model.instance().getPersons().get(Model.instance().getActiveEvent().getpId()).getGender().equals("m"))
            mPersonGender.setText("MALE");
        if(Model.instance().getPersons().get(Model.instance().getActiveEvent().getpId()).getGender().equals("f"))
            mPersonGender.setText("FEMALE");

        updateEventUI();//sets up the event recycler view
        updatePersonUI();//sets up teh person recycler view

    }

    private void updateEventUI(){
        mEventAdapter = new EventAdapter(Model.instance().getPersonEvents(Model.instance().getActiveEvent().getpId()));
        mEventRecyclerView.setAdapter(mEventAdapter);
    }
    private void updatePersonUI(){

        mPersonAdapter = new PersonAdapter(Model.instance().getRelationPersons());
        mPersonRecyclerView.setAdapter(mPersonAdapter);
    }

    private class EventAdapter extends RecyclerView.Adapter<EventHolder>{
        private List<Event> mEvents = new ArrayList<>();
        public EventAdapter(Set<Event> events){//gets list of needed events
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
            Event event = mEvents.get(position);//gets wanted Event
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

        public EventHolder(LayoutInflater inflater, ViewGroup parent){//wires up the widgets
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

        public void bind(Event event){//sets view to display correct data
            mEvent = event;
            mEventType.setText(mEvent.getType());
            mEventPlace.setText(mEvent.getCity() + ", " + mEvent.getCountry());
            mEventYear.setText(Integer.toString(mEvent.getYear()));
        }
    }

    private class PersonAdapter extends RecyclerView.Adapter<PersonHolder>{
        private List<Person> mPersons = new ArrayList<>();
        public PersonAdapter(Set<Person> persons){//gets list of wanted people
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
            Person person = mPersons.get(position);//gets the wanted person
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

        public void bind(Person person){//sets view to display correct data
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
            mPersonRelation.setText(Model.instance().findRelation(mPerson));
        }
    }

    private Boolean findGender(String id){//determines gender for given Person id
        Person person = Model.instance().getPersons().get(id);
        if(person.getGender().equals("m")){
            return true;//true for male
        }
        else return false;//false for female
    }



//override function of the up button
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
