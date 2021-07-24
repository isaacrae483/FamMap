package com.example.irae4.fammap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import Model.Model;
import Model.LoginData;
import Responce.*;
import Responce.Error;
import Model.Person;
import Model.Event;
import ServerAccess.ServerProxy;

public class SettingsActivity extends AppCompatActivity {

    private Spinner mStorySpinner;
    private Switch mStorySwitch;
    private Spinner mTreeSpinner;
    private Switch mTreeSwitch;
    private Spinner mSpouseSpinner;
    private Switch mSpouseSwitch;

    private Spinner mMapSpinner;

    private TextView mReSync;
    private TextView mLogOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
//wire up the widgets
        mStorySpinner = (Spinner)findViewById(R.id.story_spinner);
        switch(Model.instance().getStoryColor()){//start with correct color selected
            case Color.BLACK:
                mStorySpinner.setSelection(0);
                break;
            case Color.RED:
                mStorySpinner.setSelection(1);
                break;
            case Color.GREEN:
                mStorySpinner.setSelection(2);
                break;
            case Color.BLUE:
                mStorySpinner.setSelection(3);
                break;
            default:
                mStorySpinner.setSelection(0);
                break;
        }

        mStorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {//change story color
                switch (i) {
                    case 0:
                        Model.instance().setStoryColor(Color.BLACK);
                        Model.instance().setChanged(true);
                        return;
                    case 1:
                        Model.instance().setStoryColor(Color.RED);
                        Model.instance().setChanged(true);
                        return;
                    case 2:
                        Model.instance().setStoryColor(Color.GREEN);
                        Model.instance().setChanged(true);
                        return;
                    case 3:
                        Model.instance().setStoryColor(Color.BLUE);
                        Model.instance().setChanged(true);
                        return;
                    default:
                        Model.instance().setStoryColor(Color.BLACK);
                        Model.instance().setChanged(true);
                        return;
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        mStorySwitch = (Switch)findViewById(R.id.story_switch);
        mStorySwitch.setChecked(Model.instance().isStoryShow());
        mStorySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//show story lines on/off
                Model.instance().setStoryShow(isChecked);
                Model.instance().setChanged(true);
            }
        });
        mTreeSpinner = (Spinner)findViewById(R.id.tree_spinner);//start with correct color selected
        switch(Model.instance().getTreeColor()){
            case Color.BLACK:
                mTreeSpinner.setSelection(0);
                break;
            case Color.RED:
                mTreeSpinner.setSelection(1);
                break;
            case Color.GREEN:
                mTreeSpinner.setSelection(2);
                break;
            case Color.BLUE:
                mTreeSpinner.setSelection(3);
                break;
            default:
                mTreeSpinner.setSelection(0);
                break;
        }
        mTreeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {//update new Tree color
                switch (i) {
                    case 0:
                        Model.instance().setTreeColor(Color.BLACK);
                        Model.instance().setChanged(true);
                        return;
                    case 1:
                        Model.instance().setTreeColor(Color.RED);
                        Model.instance().setChanged(true);
                        return;
                    case 2:
                        Model.instance().setTreeColor(Color.GREEN);
                        Model.instance().setChanged(true);
                        return;
                    case 3:
                        Model.instance().setTreeColor(Color.BLUE);
                        Model.instance().setChanged(true);
                        return;
                    default:
                        Model.instance().setTreeColor(Color.BLACK);
                        Model.instance().setChanged(true);
                        return;
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        mTreeSwitch = (Switch)findViewById(R.id.tree_switch);
        mTreeSwitch.setChecked(Model.instance().isTreeShow());
        mTreeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//show tree lines on/off
                Model.instance().setTreeShow(isChecked);
                Model.instance().setChanged(true);
            }
        });
        mSpouseSpinner = (Spinner)findViewById(R.id.spouse_spinner);//start with correct color selected
        switch(Model.instance().getSpouseColor()){
            case Color.BLACK:
                mSpouseSpinner.setSelection(0);
                break;
            case Color.RED:
                mSpouseSpinner.setSelection(1);
                break;
            case Color.GREEN:
                mSpouseSpinner.setSelection(2);
                break;
            case Color.BLUE:
                mSpouseSpinner.setSelection(3);
                break;
            default:
                mSpouseSpinner.setSelection(0);
                break;
        }
        mSpouseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {//update new Spouse color
                switch (i) {
                    case 0:
                        Model.instance().setSpouseColor(Color.BLACK);
                        Model.instance().setChanged(true);
                        return;
                    case 1:
                        Model.instance().setSpouseColor(Color.RED);
                        Model.instance().setChanged(true);
                        return;
                    case 2:
                        Model.instance().setSpouseColor(Color.GREEN);
                        Model.instance().setChanged(true);
                        return;
                    case 3:
                        Model.instance().setSpouseColor(Color.BLUE);
                        Model.instance().setChanged(true);
                        return;
                    default:
                        Model.instance().setSpouseColor(Color.BLACK);
                        Model.instance().setChanged(true);
                        return;
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        mSpouseSwitch = (Switch)findViewById(R.id.spouse_switch);
        mSpouseSwitch.setChecked(Model.instance().isSpouseShow());
        mSpouseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//show spouse lines on/off
                Model.instance().setSpouseShow(isChecked);
                Model.instance().setChanged(true);
            }
        });
        mMapSpinner = (Spinner)findViewById(R.id.map_spinner);
        mMapSpinner.setSelection(Model.instance().getMapType());//start with correct color selected
        mMapSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {//update map type
                Model.instance().setMapType(i);
                Model.instance().setChanged(true);
                return;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        mReSync = (TextView)findViewById(R.id.resync_label);
        mReSync.setOnClickListener(new View.OnClickListener() {//button does nothing -3pts
            @Override
            public void onClick(View v) {

            }
        });
        mLogOut = (TextView)findViewById(R.id.log_out_label);//logs out of everything starts up at login screen
        final Context activityContext = this;
        mLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTopActivity(activityContext, true);
            }
        });
    }
//override up button
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
