package com.example.multimedia;
//

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentMusic()).commit();
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {

                        case R.id.nav_music:
                            selectedFragment = new FragmentMusic();
                            break;

                        case R.id.nav_video:
                            selectedFragment = new FragmentYoutube();
                            break;

                        case R.id.nav_youtube:
                            selectedFragment = new FragmentYoutube();
                            break;

                        case R.id.nav_tivi:
                            selectedFragment = new FragmentTivi1();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };


//
//    //casdsad
//
//    public void AddFragment(View view)
//    {
//        FragmentManager fragmentManager=getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
//        Fragment fragment=null;
//        switch (view.getId())
//        {
//
//            case R.id.btnMusic: fragment = new FragmentMusic();
//                break;
//            case R.id.btnYoutube: fragment = new FragmentYoutube();
//                break;
//            case R.id.btnTV: fragment = new FragmentTivi1();
//            break;
//
//        }
//        fragmentTransaction.replace(R.id.frameContent,fragment);
//        fragmentTransaction.commit();
//    }


}


