package com.example.irae4.fammap;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import Model.Model;

public class FilterActivity extends AppCompatActivity {

    private RecyclerView mFilterRecyclerView;
    private FilterAdapter mAdapter;

    private Switch mFatherSwitch;
    private Switch mMotherSwitch;
    private Switch mMaleSwitch;
    private Switch mFemaleSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
//inflate view and wire up the widgets
        mFilterRecyclerView = (RecyclerView)findViewById(R.id.filter_recycler_view);
        mFilterRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mFatherSwitch = (Switch)findViewById(R.id.father_show_switch);
        mFatherSwitch.setChecked(Model.instance().isFatherShow());
        mFatherSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//listener to turn off/on father side events
                Model.instance().setFatherShow(isChecked);
                Model.instance().setChanged(true);
            }
        });
        mMotherSwitch = (Switch)findViewById(R.id.mother_show_switch);
        mMotherSwitch.setChecked(Model.instance().isMotherShow());
        mMotherSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//listener to turn off/on mother side events
                Model.instance().setMotherShow(isChecked);
                Model.instance().setChanged(true);
            }
        });
        mMaleSwitch = (Switch)findViewById(R.id.male_show_switch);
        mMaleSwitch.setChecked(Model.instance().isMaleShow());
        mMaleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//listener to turn off/on male events
                Model.instance().setMaleShow(isChecked);
                Model.instance().setChanged(true);
            }
        });
        mFemaleSwitch = (Switch)findViewById(R.id.female_show_switch);
        mFemaleSwitch.setChecked(Model.instance().isFemaleShow());
        mFemaleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//listener to turn off/on female events
                Model.instance().setFemaleShow(isChecked);
                Model.instance().setChanged(true);
            }
        });


        updateUI();//works teh recycler view
    }

    private void updateUI(){
        mAdapter = new FilterAdapter(Model.instance().getEventTypes());
        mFilterRecyclerView.setAdapter(mAdapter);
    }


    private class FilterAdapter extends RecyclerView.Adapter<FilterHolder>{
        private List<String> mEventTypes = new ArrayList<>();
        public FilterAdapter(Set<String> eventTypes){//gets list of all event types that need to be shown
            for(String x : eventTypes){
                mEventTypes.add(x);
            }
        }

        @Override
        public FilterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            return new FilterHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(FilterHolder filterHolder, int position) {
            String eventType = mEventTypes.get(position);//select desired event type
            filterHolder.bind(eventType);
        }

        @Override
        public int getItemCount() {
            return mEventTypes.size();
        }
    }

    private class FilterHolder extends RecyclerView.ViewHolder{
        TextView mEventTitle;
        Switch mEventShow;

        private String mEventType;

        public FilterHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.filter_list, parent, false));//wires up widgets inside the recycler view
            mEventTitle = (TextView)itemView.findViewById(R.id.event_type_title);
            mEventShow = (Switch)itemView.findViewById(R.id.event_type_switch);
            mEventShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Model.instance().setEventTypeShow(mEventType, isChecked);
                    Model.instance().setChanged(true);
                }
            });
        }

        public void bind(String eventType){//sets the correct values to the given view
            mEventType = eventType;
            mEventTitle.setText(mEventType + " Events Display");
            mEventShow.setChecked(Model.instance().getEventTypeShow(eventType));
        }

    }



//overide up button function
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
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//start new main activity
        }
        else {
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);//start old main activity
        }
        context.startActivity(intent);
    }
}
