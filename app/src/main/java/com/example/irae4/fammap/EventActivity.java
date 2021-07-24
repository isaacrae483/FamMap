package com.example.irae4.fammap;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import Model.Model;

public class EventActivity extends AppCompatActivity {

    private MapFragment mapFrag;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Iconify.with(new FontAwesomeModule());

        fm = this.getSupportFragmentManager();
//add map fragment to activity
        mapFrag = (MapFragment)fm.findFragmentById(R.id.fragment_map_container);
        if (mapFrag == null) {
            mapFrag = new MapFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_map_container, mapFrag)
                    .commit();
        }

    }




//override function of up button

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
        Model.instance().setChanged(true);
        Intent intent = new Intent(context, MainActivity.class);
        if (newInstance) {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//new main activity
        }
        else {
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);//resume old main activity
        }
        context.startActivity(intent);
    }
}
