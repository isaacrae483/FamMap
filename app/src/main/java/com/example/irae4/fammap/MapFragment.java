package com.example.irae4.fammap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.List;
import java.util.Map;
import java.util.Set;

import Model.Event;
import Model.Person;
import Model.Model;

import static java.lang.Double.parseDouble;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    private TextView mMapNameText;
    private TextView mMapEventText;
    private TextView mMapLocationText;
    private TextView mMapYearText;

    private LinearLayout mPersonStuff;
    private ImageView mGenderIcon;

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;

    public static MapFragment newInstance(){
        return new MapFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//sets menu depending on where map being used
        if(Model.instance().getActiveEvent() == null){
            setHasOptionsMenu(true);
        }
        else{
            setHasOptionsMenu(false);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

//wires up the widgets
        mapFragment = (SupportMapFragment)getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        mPersonStuff = (LinearLayout) v.findViewById(R.id.person_stuff);//data for given marker
        mPersonStuff.setClickable(true);
        mPersonStuff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//on click starts person activity
                if(Model.instance().getActiveEvent() != null){
                    Intent intent = new Intent(getActivity(), PersonActivity.class);
                    startActivity(intent);
                }

            }
        });


        mGenderIcon = (ImageView) v.findViewById(R.id.gender_icon);
        Drawable genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).sizeDp(40);
        mGenderIcon.setImageDrawable(genderIcon);

        mMapNameText = (TextView) v.findViewById(R.id.map_name_text);
        mMapNameText.setText("Click");

        mMapEventText = (TextView) v.findViewById(R.id.map_event_text);
        mMapEventText.setText("an event");

        mMapLocationText = (TextView) v.findViewById(R.id.map_location_text);
        mMapLocationText.setText("for more");

        mMapYearText = (TextView) v.findViewById(R.id.map_year_text);
        mMapYearText.setText("information");

        return v;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {//loads actual map
        mMap = googleMap;

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {
            @Override
            public boolean onMarkerClick(Marker mark) {//fill out data when marker clicked
                Model.instance().setActiveEvent(Model.instance().getMarkerList().get(mark));
                Event curr = Model.instance().getActiveEvent();
                mMapNameText.setText(findName(curr.getpId()));
                mMapEventText.setText(curr.getType());
                mMapLocationText.setText(curr.getCity() + "," + curr.getCountry());
                mMapYearText.setText(Integer.toString(curr.getYear()));
                if(findGender(curr.getpId())){
                    Drawable genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).sizeDp(40);
                    mGenderIcon.setImageDrawable(genderIcon);
                }
                else{
                    Drawable genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_female).sizeDp(40);
                    mGenderIcon.setImageDrawable(genderIcon);
                }
                addPolyLines(curr);//draws the lines
                return true;
            }
        });

        addMarkers();//adds the markers

        if(Model.instance().getActiveEvent() != null){
            setUp();
            LatLng place = new LatLng(Double.parseDouble(Model.instance().getActiveEvent().getLatitude()),Double.parseDouble(Model.instance().getActiveEvent().getLongitude()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(place));
        }

    }

    private void setMapType(){//decides correct map display
        switch (Model.instance().getMapType()){
            case 0:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return;
            case 1:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return;
            case 2:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return;
            case 3:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return;
            default:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return;
        }


    }

    private void addMarkers(){//adds the markers that aren't filtered out

        Model.instance().getMarkerList().clear();

        for(Event event : Model.instance().getEvents().values()){

            if(Model.instance().approve(event.getpId(), event.getType())){
                LatLng latLng = new LatLng(parseDouble(event.getLatitude()), parseDouble(event.getLongitude()));
                //need to add colors
                Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).title(event.getType())
                        .icon(BitmapDescriptorFactory.defaultMarker(Model.instance().getMarkerColor(event.getType().toLowerCase()))));//color based on event type

                Model.instance().getMarkerList().put(marker, event);
            }

        }
    }
    private void addPolyLines(Event event){
        for(Polyline x : Model.instance().getLines()){
            x.remove();
        }
        Model.instance().getLines().clear();

        if(Model.instance().isStoryShow())//draws story lines if shown
            storyLines(event);
        if(Model.instance().isTreeShow())//draws tree lines if shown
            drawTreeLines(event);
        if(Model.instance().isSpouseShow())//draws spouse lines if shown
            spouseLines(event);


    }


    private void storyLines(Event event){
        Set<Event> allEvents = Model.instance().getPersonEvents(event.getpId());
        if(allEvents == null){
            return;
        }
        Event startEvent = null;
        Event endEvent = null;
        LatLng start;
        LatLng end;
        for(Event x : allEvents){
            if(startEvent == null){//gets first event
                startEvent = x;
            }
            else{//draws line to next event
                endEvent = x;
                start = new LatLng(parseDouble(startEvent.getLatitude()), parseDouble(startEvent.getLongitude()));
                end = new LatLng(parseDouble(endEvent.getLatitude()), parseDouble(endEvent.getLongitude()));
                PolylineOptions options = new PolylineOptions().add(start).add(end).color(Model.instance().getStoryColor());
                Polyline line = mMap.addPolyline(options);
                Model.instance().getLines().add(line);
                startEvent = endEvent;//makes end event the new start event
            }
        }
    }
    private void spouseLines(Event event){
        Set<Event> allEvents = Model.instance().getSpouseEvents(event.getpId());
        if(allEvents == null){
            return;
        }
        Event startEvent = event;
        Event endEvent = null;
        LatLng start;
        LatLng end;
        for(Event x : allEvents){//draws line to first filtered spouse event
            endEvent = x;
            start = new LatLng(parseDouble(startEvent.getLatitude()), parseDouble(startEvent.getLongitude()));
            end = new LatLng(parseDouble(endEvent.getLatitude()), parseDouble(endEvent.getLongitude()));
            PolylineOptions options = new PolylineOptions().add(start).add(end).color(Model.instance().getSpouseColor());
            Polyline line = mMap.addPolyline(options);
            Model.instance().getLines().add(line);
            break;
        }
    }
    private float lineW;
    private void drawTreeLines(Event event){//base of tree drawing
        lineW = 13f;//set initial line width
        Event rtnEvent;
        if(Model.instance().isFatherShow()){//father side if shown
            rtnEvent = fatherLines(event);
            if(rtnEvent != null){
                treeLines(rtnEvent);
            }
        }
        if(Model.instance().isMotherShow()){//mother side if shown
            rtnEvent = motherLines(event);
            if(rtnEvent != null){
                treeLines(rtnEvent);
            }
        }

    }

    private void treeLines(Event event){
        lineW -= 2f;//gradually decreases line width each call
        if(lineW <= 0)
            lineW = 1f;
        Event rtnEvent;
        rtnEvent = fatherLines(event);
        if(rtnEvent != null){
            treeLines(rtnEvent);
        }
        rtnEvent = motherLines(event);
        if(rtnEvent != null){
            treeLines(rtnEvent);
        }

    }
    private Event fatherLines(Event dEvent){
        Set<Event> fatherEvents = Model.instance().getFatherEvents(dEvent.getpId());
        if(fatherEvents == null){
            return null;
        }
        Event startEvent = dEvent;
        Event endEvent = null;
        LatLng start;
        LatLng end;
        for(Event x : fatherEvents){//draws line to fathers first event
            endEvent = x;
            start = new LatLng(parseDouble(startEvent.getLatitude()), parseDouble(startEvent.getLongitude()));
            end = new LatLng(parseDouble(endEvent.getLatitude()), parseDouble(endEvent.getLongitude()));
            PolylineOptions options = new PolylineOptions().add(start).add(end).color(Model.instance().getTreeColor()).width(lineW);
            Polyline line = mMap.addPolyline(options);
            Model.instance().getLines().add(line);
            return x;
        }
        return null;
    }
    private Event motherLines(Event dEvent){
        Set<Event> motherEvents = Model.instance().getMotherEvents(dEvent.getpId());
        if(motherEvents == null){
            return null;
        }
        Event startEvent = dEvent;
        Event endEvent = null;
        LatLng start;
        LatLng end;
        for(Event x : motherEvents){//draw line to mother first event
            endEvent = x;
            start = new LatLng(parseDouble(startEvent.getLatitude()), parseDouble(startEvent.getLongitude()));
            end = new LatLng(parseDouble(endEvent.getLatitude()), parseDouble(endEvent.getLongitude()));
            PolylineOptions options = new PolylineOptions().add(start).add(end).color(Model.instance().getTreeColor()).width(lineW);
            Polyline line = mMap.addPolyline(options);
            Model.instance().getLines().add(line);
            return x;
        }
        return null;
    }


    private String findName(String id){//gets name of person for given event id
       Person person = Model.instance().getPersons().get(id);
       return person.getFirstName() + " " + person.getLastName();
    }

    private Boolean findGender(String id){//finds gender of given person id
        Person person = Model.instance().getPersons().get(id);
        if(person.getGender().equals("m")){
            return true; //true means male
        }
        else return false; // false means female
    }


    private void setUp(){//resets up map if settings have changed
        Event curr = Model.instance().getActiveEvent();
        mMapNameText.setText(findName(curr.getpId()));
        mMapEventText.setText(curr.getType());
        mMapLocationText.setText(curr.getCity() + "," + curr.getCountry());
        mMapYearText.setText(Integer.toString(curr.getYear()));
        if(findGender(curr.getpId())){
            Drawable genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).sizeDp(40);
            mGenderIcon.setImageDrawable(genderIcon);
        }
        else{
            Drawable genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_female).sizeDp(40);
            mGenderIcon.setImageDrawable(genderIcon);
        }
        reDraw();
    }
    private void reDraw(){//redraws lines when settings change
        mMap.clear();
        Model.instance().getLines().clear();

        addMarkers();
        setMapType();
        if(Model.instance().getActiveEvent() != null){
            if(!Model.instance().approve(Model.instance().getActiveEvent().getpId(), Model.instance().getActiveEvent().getType())){
                Model.instance().setActiveEvent(null);

                mMapNameText.setText("Click");
                mMapEventText.setText("an event");
                mMapLocationText.setText("for more");
                mMapYearText.setText("information");

            }
        }

        if(Model.instance().getActiveEvent() != null)
            addPolyLines(Model.instance().getActiveEvent());
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
        return;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem mSearch = (MenuItem) menu.findItem(R.id.search);
        Drawable searchIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_search).actionBarSize();
        mSearch.setIcon(searchIcon);
        return;
    }
//gives functions to the options in the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.search:
                intent = new Intent(getActivity(), SearchActivity.class);
                startActivityForResult(intent, 1);
                return true;
            case R.id.filter:
                intent = new Intent(getActivity(), FilterActivity.class);
                startActivityForResult(intent, 1);
                return true;
            case R.id.settings:
                intent = new Intent(getActivity(), SettingsActivity.class);
                startActivityForResult(intent, 1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(Model.instance().getChanged()){//rebuilds map if settings have changed
            reDraw();
        }
        Model.instance().setChanged(false);
    }
}
