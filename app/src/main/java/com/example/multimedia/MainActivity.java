package com.example.multimedia;
//
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;
import android.os.Handler;
import android.view.View;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    //casdsad

    public void AddFragment(View view)
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        Fragment fragment=null;
        switch (view.getId())
        {

            case R.id.btnMusic: fragment = new FragmentMusic();
                break;
            case R.id.btnYoutube: fragment = new FragmentYoutube();
                break;
            case R.id.btnTV: fragment = new FragmentTiVi();
            break;

        }
        fragmentTransaction.replace(R.id.frameContent,fragment);
        fragmentTransaction.commit();
    }


}


