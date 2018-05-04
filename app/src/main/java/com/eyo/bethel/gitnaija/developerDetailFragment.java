package com.eyo.bethel.gitnaija;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eyo.bethel.gitnaija.data.NaijaDevelopers;


public class developerDetailFragment extends Fragment {
    private static final String PROFILE_KEY = "profile";
    private static final String PROFILE_SHARE_HASHTAG = " #GITHUb";
    private ShareActionProvider shareActionProvider;

    TextView profileUrl, username, tryAgain, errorMsg, errorDesc;
    View emptyLayout, detailLayout;
    Bundle mBundle;
    NaijaDevelopers mNaijaDevelopers;
    ImageView errorImage;

    public static developerDetailFragment newInstance(){
        return new developerDetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         mBundle = getArguments();

        if (mBundle != null){
            updateDeveloperDetails(mBundle);
        }
    }

    public void updateDeveloperDetails(Bundle bundle){
        mNaijaDevelopers = bundle.getParcelable(PROFILE_KEY);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null){
            mBundle = savedInstanceState;
        }
        setUpViews(view);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(PROFILE_KEY, mNaijaDevelopers);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setUpViews(View views){
        emptyLayout = views.findViewById(R.id.error_screen);
        errorImage = (ImageView) views.findViewById(R.id.error_image);
        errorMsg = (TextView) views.findViewById(R.id.err_msg);
        tryAgain = (TextView) views.findViewById(R.id.try_again);
        errorDesc = (TextView) views.findViewById(R.id.err_description);
        tryAgain.setVisibility(View.INVISIBLE);
        detailLayout = views.findViewById(R.id.card_view);
        username = (TextView) views.findViewById(R.id.user_name);
        profileUrl = (TextView) views.findViewById(R.id.profile_url);
    }

}
