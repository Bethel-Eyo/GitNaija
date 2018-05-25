package com.eyo.bethel.gitnaija;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eyo.bethel.gitnaija.Api.DevListService;
import com.eyo.bethel.gitnaija.Api.RestApiBuilder;
import com.eyo.bethel.gitnaija.data.DevList;
import com.eyo.bethel.gitnaija.data.NaijaDevelopers;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import static com.eyo.bethel.gitnaija.Utilities.Utils.isDeviceOnline;


public class developerFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    ProgressBar progressBar;
    View emptyScreen;
    TextView emptyScreenText, tryAgain;
    ListView listView;
    ImageView errorImage;
    Toolbar toolbar;
    LinearLayout linearLayout;
    developerAdapter mAdapter;
    private List<NaijaDevelopers> usernames = new ArrayList<>();
    private List<NaijaDevelopers> loadedDevelopers = new ArrayList<>();
    private TextView contextTxt;
    String searchParams = "language:java location:uyo";

    public static developerFragment newInstance() {
        developerFragment fragment = new developerFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        emptyScreen = view.findViewById(R.id.error_screen);
        emptyScreenText = (TextView) view.findViewById(R.id.err_msg);
        errorImage = (ImageView) view.findViewById(R.id.error_image);
        tryAgain = (TextView) view.findViewById(R.id.try_again);
        listView = (ListView) view.findViewById(R.id.list_view);
        contextTxt = (TextView) view.findViewById(R.id.context);
        mAdapter = new developerAdapter(getActivity());
        linearLayout = (LinearLayout) view.findViewById(R.id.linear_layout);

        toolbar = (Toolbar) view.findViewById(R.id.app_bar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.icons));
        setUpSharedPreferences();

        return view;
    }

    public void updateDeveloperList(String searchParams){
        String nullText;
        if (isDeviceOnline(getActivity())){
            FetchNaijaDevelopers(searchParams);
        }else {
            emptyScreen.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            nullText = getResources().getString(R.string.error);
            emptyScreenFormat(R.drawable.cloud_problem, nullText,false);
        }
    }

    // this method helps to configure our sharedPreference
    private void setUpSharedPreferences(){
        SharedPreferences sharedPreferences = android.support.v7.preference.PreferenceManager.getDefaultSharedPreferences(getActivity());
        loadSkillsetFromSharedPreferences(sharedPreferences);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_skill_key))){
            loadedDevelopers.clear();
            loadSkillsetFromSharedPreferences(sharedPreferences);
        }
    }

    // This interface is implemented whenever a particular position in the adapter is clicked
    public interface onDeveloperSelected{
        void onDeveloperClicked(int developerPosition, NaijaDevelopers developers);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (loadedDevelopers.isEmpty()){
            setUpSharedPreferences();
        } else {
            setUpSharedPreferences();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    // this method helps load the skill-set value from the settingsActivity dynamically
    private void loadSkillsetFromSharedPreferences(SharedPreferences sharedPreferences){
        String skillSet = sharedPreferences.getString(getString(R.string.pref_skill_key),
                getString(R.string.pref_skill_value_jav));
        searchParams = "language:"+ skillSet+ " location:uyo";
        String statement = "Showing " + skillSet + " developers in Uyo, AKS, Nigeria";
        contextTxt.setText(statement);
        //Toast.makeText(getContext(), searchParams, Toast.LENGTH_SHORT).show();
        updateDeveloperList(searchParams);
    }



    public void emptyScreenFormat(int theImage, String msg, boolean retry){
        errorImage.setImageDrawable(ContextCompat.getDrawable(getContext(), theImage));
        emptyScreenText.setText(msg);

        if (retry){
            tryAgain.setVisibility(View.VISIBLE);
        }else {
            tryAgain.setVisibility(View.GONE);
        }
    }

    // fetch developers using retrofit request
    public void FetchNaijaDevelopers(String searchParams){
        //String searchParams = "language:java location:uyo";
        DevListService devService = new RestApiBuilder().getDevListService();
        Call<DevList> devListCall = devService.getDevList(searchParams);
        devListCall.enqueue(new Callback<DevList>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<DevList> call, Response<DevList> response) {
                if(response.isSuccessful()){
                    DevList items = response.body();

                    for(int i = 0; i < items.getItems().size(); i++){
                        final NaijaDevelopers naijaDevelopers = new NaijaDevelopers();
                        String theUsername = items.getItems().get(i).getDeveloperUsername();
                        naijaDevelopers.setDeveloperUsername(theUsername);
                        usernames.add(naijaDevelopers);
                    }
                    getUserName();
                } else {
                    Toast.makeText(getContext(), "Unsuccessful response: "+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DevList> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void fixAdapter(List<NaijaDevelopers> developers){
        mAdapter.exchangeData(developers);
        listView.setAdapter(mAdapter);
        progressBar.setVisibility(View.GONE);
        toolbar.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
        listView.setVisibility(View.VISIBLE);
        contextTxt.setVisibility(View.VISIBLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NaijaDevelopers developerProfile = (NaijaDevelopers) mAdapter.getItem(position);

                ((onDeveloperSelected) getActivity()).onDeveloperClicked(position, developerProfile);
            }
        });
    }

    public void getUserName(){

        for (NaijaDevelopers naijaDevelopers: usernames){
            final String theUsername = naijaDevelopers.getDeveloperUsername();
            DevListService myService = new RestApiBuilder().getDevListService();
            Call<NaijaDevelopers> iCall = myService.getDevUsernameDetails(theUsername);
            iCall.enqueue(new Callback<NaijaDevelopers>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(Call<NaijaDevelopers> call, Response<NaijaDevelopers> response) {
                    if (response.isSuccessful()){
                        Log.d("2.0 getFeed> full json developer detail =>",
                                new GsonBuilder().setPrettyPrinting().create().toJson(response));

                        NaijaDevelopers naijaDevelopers1 = new NaijaDevelopers();
                        naijaDevelopers1.setFullName(response.body().getFullName());
                        naijaDevelopers1.setDevBiography(response.body().getDevBiography());
                        naijaDevelopers1.setDevWorkPlace(response.body().getDevWorkPlace());
                        naijaDevelopers1.setPublicRepos(response.body().getPublicRepos());
                        naijaDevelopers1.setDeveloperUsername(response.body().getDeveloperUsername());
                        naijaDevelopers1.setProfileUrl(response.body().getProfileUrl());
                        naijaDevelopers1.setImageUrl(response.body().getImageUrl());
                        loadedDevelopers.add(naijaDevelopers1);
                        fixAdapter(loadedDevelopers);
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<NaijaDevelopers> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        /* Use the inflater's inflate method to inflate our medications_menu layout to this menu */
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(getContext(), SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

}
