package com.eyo.bethel.gitnaija;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eyo.bethel.gitnaija.R;
import com.eyo.bethel.gitnaija.data.NaijaDevelopers;
import com.eyo.bethel.gitnaija.developerDetailFragment;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class developerProfileDetailActivity extends AppCompatActivity {
    String FRAG_TAG;
    NaijaDevelopers mNaijaDevelopers;
    View layoutHeader;
    ProgressBar progressBar;
    Target target;
    ImageView headerImage;
    Fragment mFragment;
    TextView username, profileUrl, biography, publicRepos, workPlace;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developer_profile_detail);

        Bundle bundle = getIntent().getExtras();

        FRAG_TAG = "developerDetails";
        mNaijaDevelopers = bundle.getParcelable("naijaDeveloper");
        setUpToolbar();
        mFragment = developerDetailFragment.newInstance();
        mFragment.setArguments(bundle);
        setFragment(mFragment);
        username = (TextView) findViewById(R.id.user_name);
        profileUrl = (TextView) findViewById(R.id.profile_url);
        biography = (TextView) findViewById(R.id.biography);
        publicRepos = (TextView) findViewById(R.id.pub_rep);
        workPlace = (TextView) findViewById(R.id.workplace);

        username.setText(mNaijaDevelopers.getDeveloperUsername());
        if(mNaijaDevelopers.getDevBiography() != null){
            biography.setText(mNaijaDevelopers.getDevBiography());
        } else {
            biography.setText("Not available");
        }
        int pubRep = mNaijaDevelopers.getPublicRepos();
        String pubRepStr = Integer.toString(pubRep);
        publicRepos.setText(pubRepStr);
        if (mNaijaDevelopers.getDevWorkPlace() != null){
            workPlace.setText(mNaijaDevelopers.getDevWorkPlace());
        } else {
            workPlace.setText("Not available");
        }
        profileUrl.setText(mNaijaDevelopers.getProfileUrl());
        profileUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(profileUrl.getText().toString()));
                startActivity(intent);
            }
        });

    }

    public void setUpToolbar(){
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);

        collapsingToolbarLayout.setTitle(mNaijaDevelopers.getFullName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat
                    .getColor(this,R.color.icons));
            collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this,R.color.icons));
        }else {
            collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.icons));
            collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.icons));

        }

        //to setUp the Image Header
        layoutHeader = findViewById(R.id.layout_header);
        progressBar = (ProgressBar) findViewById(R.id.image_progress_bar);
        headerImage = (ImageView) findViewById(R.id.header_img);

        String BackImageUrl = mNaijaDevelopers.getImageUrl();

        target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                headerImage.setImageBitmap(bitmap);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                headerImage.setImageResource(R.color.primary_light);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                headerImage.setImageResource(R.color.primary_light);
                progressBar.setVisibility(View.VISIBLE);
            }
        };
        Picasso.with(getApplicationContext()).load(BackImageUrl).into(target);

    }


    public void setFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.details_container, fragment, FRAG_TAG).commit();
    }
}
