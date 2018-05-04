package com.eyo.bethel.gitnaija;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NaijaDevs extends AppCompatActivity {

    View mFragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.naija_devs);

        mFragmentContainer = findViewById(R.id.fragment);

        String DEV_FRAGMENT_TAG = "DeveloperFragment";

        Fragment mDeveloperFragment = developerFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, mDeveloperFragment
                , DEV_FRAGMENT_TAG).commit();
    }
}
