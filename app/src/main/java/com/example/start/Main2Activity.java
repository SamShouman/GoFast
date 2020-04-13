package com.example.start;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.transition.Explode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.start.ui.main.SectionsPagerAdapter;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;

public class Main2Activity extends AppCompatActivity {
    TabLayout accounts;
    ListView listview;
    DBHandler db;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> arrayListInsta = new ArrayList<>();

    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList1 = new ArrayList<>();
    ArrayList<String> arrayListWhatsapp = new ArrayList<>();
    ArrayAdapter adapter1, adapterInsta, adapterWhatsapp;
    boolean on_facebook = true, on_Instagram = false, on_Whatsapp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFE302));
        Explode animation=new Explode();
        animation.setDuration(750);
        getWindow().setAllowEnterTransitionOverlap(false);
        getWindow().setEnterTransition(animation);
        accounts = findViewById(R.id.accounts);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.accounts);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        // Toolbar toolbar=findViewById(R.id.toolbar3);
        // ActionBar bar = getActionBar();
        //bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f1c40f")));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.mycolor)));
        tabs.setSelectedTabIndicatorColor(Color.parseColor("#283747"));

        //setSupportActionBar(toolbar);
        int[] tabIcons = {
                R.drawable.facebook,
                R.drawable.whatsapp,
                R.drawable.instagram
        };

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //      .setAction("Action", null).show();
                ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(Main2Activity.this);
                Intent newFriend = new Intent(Main2Activity.this, NewFriend.class);
                startActivity(newFriend,options.toBundle());
            }
        });
        db = new DBHandler(Main2Activity.this, null, null, 3);
        accounts.getTabAt(0).setIcon(tabIcons[0]);
        accounts.getTabAt(1).setIcon(tabIcons[1]);
        accounts.getTabAt(2).setIcon(tabIcons[2]);
        TypefaceSpan typefaceSpan = new TypefaceSpan("@font/alfa_slab_one.xml");
        SpannableString str = new SpannableString("GoFast");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);

        listview = findViewById(R.id.listview);

        HashMap<String, String> names;
        names = db.getFacebookNames(StayLoggedIn.getUserName(Main2Activity.this));
        for (String key : names.keySet()) {
            arrayList.add(key);
        }
        adapter = new ArrayAdapter(Main2Activity.this, android.R.layout.simple_list_item_1, arrayList);
        listview.setAdapter(adapter);
        accounts.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 1) {
                    on_facebook = false;
                    on_Instagram = false;
                    on_Whatsapp = true;
                    HashMap<String, String> names;
                    names = db.getContactsNames(StayLoggedIn.getUserName(Main2Activity.this));
                    for (String key : names.keySet()) {
                        arrayListWhatsapp.add(key);
                    }
                    adapterWhatsapp = new ArrayAdapter(Main2Activity.this, android.R.layout.simple_list_item_1, arrayListWhatsapp);
                    listview.setAdapter(adapterWhatsapp);
                    //  adapter=adapter1;
                }

                if (position == 0) {
                    on_facebook = true;
                    on_Instagram = false;
                    on_Whatsapp = false;
                    //  String [] b={"ali","sam"};
                    //ArrayAdapter adapter=new ArrayAdapter(Main2Activity.this,android.R.layout.simple_list_item_1,b);
                    //listview.setAdapter(adapter);
                    //arrayList1=new ArrayList<>();
                    HashMap<String, String> names;
                    names = db.getFacebookNames(StayLoggedIn.getUserName(Main2Activity.this));
                    for (String key : names.keySet()) {
                        arrayList1.add(key);
                    }
                    adapter1 = new ArrayAdapter(Main2Activity.this, android.R.layout.simple_list_item_1, arrayList1);
                    listview.setAdapter(adapter1);
                    adapter = adapter1;


                }

                if (position == 2) {
                    on_Instagram = true;
                    on_facebook = false;
                    on_Whatsapp = false;

                    //     arrayListInsta=new ArrayList<>();
                    HashMap<String, String> names;
                    names = db.getInstagramNames(StayLoggedIn.getUserName(Main2Activity.this));
                    for (String key : names.keySet()) {
                        arrayListInsta.add(key);
                    }
                    adapterInsta = new ArrayAdapter(Main2Activity.this, android.R.layout.simple_list_item_1, arrayListInsta);
                    listview.setAdapter(adapterInsta);
                    //adapter=adapter1;

                    // adapterInsta.notifyDataSetChanged();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 2) {
                    arrayListInsta.clear();
                }

                if (tab.getPosition() == 0)
                    arrayList1.clear();
                if (tab.getPosition() == 1)
                    arrayListWhatsapp.clear();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (on_facebook) {

                    String selectedName = (String) listview.getItemAtPosition(position);

                    String url = db.getFacebookURL(selectedName);
                    String YourPageURL = "https://www.facebook.com/n/?" + url;
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YourPageURL));

                    startActivity(browserIntent);
                } else {
                    if (on_Instagram) {
                        String selectedName = (String) listview.getItemAtPosition(position);
                        String url = db.getInstagramURL(selectedName);
                        Uri uri = Uri.parse(url);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }

                    if (on_Whatsapp) {

                        String selectedName = (String) listview.getItemAtPosition(position);
                        String url = db.getContactNumber(selectedName);
                        Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + url);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);

                    }
                }

            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                if (on_facebook) {
                    String x = arrayList1.remove(position);

                    adapter1.notifyDataSetChanged();
                    //   String f=db.getFacebookURL(x);
                    db.deleteFacebookUser(x);

                } else {
                    if (on_Instagram) {
                        String x = arrayListInsta.remove(position);

                        adapterInsta.notifyDataSetChanged();
                        //   String f=db.getFacebookURL(x);
                        db.deleteInstagramUser(x);
                    }

                    if (on_Whatsapp) {

                        String x = arrayListWhatsapp.remove(position);

                        adapterWhatsapp.notifyDataSetChanged();
                        //   String f=db.getFacebookURL(x);
                        db.deleteContact(x);

                    }
                }
                return false;
            }
        });

      /*  AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        Calendar calendar= Calendar.getInstance();
        calendar.add(Calendar.SECOND,5);
        Intent intent=new Intent(this,MainService.class);
        PendingIntent pendingIntent=PendingIntent.getService(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),0,pendingIntent);*/
        Intent intent=new Intent(this,Receiver.class);
      Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,12);
      calendar.set(Calendar.MINUTE,15);
      calendar.set(Calendar.SECOND,0);
      PendingIntent pendingIntent=PendingIntent.getBroadcast(this,100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
      AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
      alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);



    }
}

