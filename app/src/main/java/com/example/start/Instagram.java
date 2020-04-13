package com.example.start;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Instagram extends Fragment {
    boolean sign_out=false;
    DBHandler db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.instagram, container, false);
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
        else{   if(i==R.id.deleteFromFacebook){
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
            if(R.id.help==i) {
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
}
