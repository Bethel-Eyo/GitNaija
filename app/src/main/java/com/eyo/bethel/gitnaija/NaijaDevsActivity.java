package com.eyo.bethel.gitnaija;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.eyo.bethel.gitnaija.data.NaijaDevelopers;

public class NaijaDevsActivity extends AppCompatActivity implements developerFragment.onDeveloperSelected {

    View mFragmentContainer;
    NaijaDevelopers selectedDeveloper;

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

    @Override
    public void onDeveloperClicked(int developerPosition, NaijaDevelopers developer) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("naijaDeveloper", developer);
        selectedDeveloper = developer;

        Intent intent = new Intent(this, developerProfileDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}
