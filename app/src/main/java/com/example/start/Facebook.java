package com.example.start;

import android.app.ActivityOptions;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;

public class Facebook extends Fragment {
    boolean sign_out=false;
    ArrayList<String> arrayList=new ArrayList<>();
    ArrayAdapter<String> adapter;
  DBHandler  db;
    HashMap<String,String> names;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.facebook, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        super.onCreate(savedInstanceState);



    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_main,menu);
        super.onCreateOptionsMenu(menu, inflater);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i=item.getItemId();


        if(i==R.id.sign_out){
            StayLoggedIn.clearUserName(getActivity());
            sign_out=true;
            ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(getActivity());
            Intent login=new Intent(getActivity(),Login.class);
            startActivity(login,options.toBundle());
            // Toast.makeText(getActivity(),"Signed Out",Toast.LENGTH_SHORT).show();
        }
        else{

            if(i==R.id.deleteFromFacebook){
                db=new DBHandler(getActivity(),null,null,10);
                db.deleteFacebookUsers(StayLoggedIn.getUserName(getActivity()));
                Toast.makeText(getActivity(),"Facebook users have been deleted.",Toast.LENGTH_SHORT).show();
            }

            if(i==R.id.deleteFromInstagram){
                db=new DBHandler(getActivity(),null,null,10);
                db.deleteInstagramUsers(StayLoggedIn.getUserName(getActivity()));
                Toast.makeText(getActivity(),"Instagram users have been deleted.",Toast.LENGTH_SHORT).show();
            }

            if(i==R.id.deleteFromWhatsapp){
                db=new DBHandler(getActivity(),null,null,10);
                db.deleteWhatsappContacts(StayLoggedIn.getUserName(getActivity()));
                Toast.makeText(getActivity(),"Whatsapp contacts have been deleted.",Toast.LENGTH_SHORT).show();
            }


            if(i==R.id.help) {
                Intent help = new Intent(getActivity(), Help.class);
                startActivity(help);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(sign_out)
            getActivity().finish();
    }

    public static Intent newFacebookIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                // http://stackoverflow.com/a/24547437/1048340
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }


}

