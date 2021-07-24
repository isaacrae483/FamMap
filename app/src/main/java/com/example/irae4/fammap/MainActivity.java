package com.example.irae4.fammap;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.irae4.fammap.R;
import com.example.irae4.fammap.LoginFragment;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;


public class MainActivity extends AppCompatActivity {

    private LoginFragment logFrag;
    private MapFragment mapFrag;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Iconify.with(new FontAwesomeModule());

       fm = this.getSupportFragmentManager();//loads login fragment

        logFrag = (LoginFragment)fm.findFragmentById(R.id.fragment_container);
        if (logFrag == null) {
            logFrag = new LoginFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, logFrag)
                    .commit();
        }


    }

    public void afterLogin(){
        fm.beginTransaction()
                .remove(logFrag)
                .commit();

        mapFrag = new MapFragment();//switches login to map
        fm.beginTransaction()
                .add(R.id.fragment_container, mapFrag)
                .commit();

    }

}
