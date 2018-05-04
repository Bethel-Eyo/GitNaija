package com.eyo.bethel.gitnaija;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eyo.bethel.gitnaija.data.NaijaDevelopers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.eyo.bethel.gitnaija.Utilities.Utils.isDeviceOnline;


public class developerFragment extends Fragment {

    private final String PROFILE_KEY = "profile";
    developerAdapter mDeveloperAdapter;
    Bundle nBundle;

    ProgressBar progressBar;
    View emptyScreen;
    TextView emptyScreenText, tryAgain;
    ListView listView;
    ImageView errorImage;
    Toolbar toolbar;

    private List<NaijaDevelopers> loadDevelopers = new ArrayList<>();
    Context mContext;

    public static developerFragment newInstance() {
        developerFragment fragment = new developerFragment();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (nBundle != null){
            reload(nBundle);
        }
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
        toolbar = (Toolbar) view.findViewById(R.id.app_bar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        mDeveloperAdapter = new developerAdapter(getActivity());
        listView.setAdapter(mDeveloperAdapter);
        if (savedInstanceState != null){
            updateDeveloperList();

            nBundle = savedInstanceState.getParcelable(PROFILE_KEY);
            mDeveloperAdapter.exchangeData(loadDevelopers);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NaijaDevelopers developerProfile = (NaijaDevelopers) mDeveloperAdapter.getItem(position);

                ((OnDeveloperSelected) getActivity()).onDeveloperClicked(position, developerProfile);
            }
        });

        return view;
    }

    public void reload(Bundle bundle){
        loadDevelopers = bundle.getParcelableArrayList(PROFILE_KEY);
        if (loadDevelopers != null){
            mDeveloperAdapter.exchangeData(loadDevelopers);
        }else {
            this.nBundle = bundle;
        }
        updateDeveloperList();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(PROFILE_KEY,((ArrayList<NaijaDevelopers>) loadDevelopers));
    }

    @Override
    public void onStart() {
        super.onStart();
        updateDeveloperList();
    }

    public void updateDeveloperList(){
        String nullText;
        String parameter = "java";
        if (isDeviceOnline(getActivity())){
            FetchDeveloperProfile fetchDeveloperProfile = new FetchDeveloperProfile();
            fetchDeveloperProfile.execute(parameter);
        }else {
            emptyScreen.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            nullText = getResources().getString(R.string.error);
            emptyScreenFormat(R.drawable.cloud_problem, nullText,false);
        }
    }

    public interface OnDeveloperSelected{
        void onDeveloperClicked(int developerPosition, NaijaDevelopers developer);
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

    private class FetchDeveloperProfile extends AsyncTask<String, Void, List<NaijaDevelopers>> {
        private final String LOG_TAG = FetchDeveloperProfile.class.getSimpleName();

        private List<NaijaDevelopers> loadedDeveloper;

        // Json response keys
        private static final String GIT_NAME = "login";
        private static final String GIT_PHOTO = "avatar_url";
        private static final String GIT_PROFILE_LINK = "html_url";
        private static final String GIT_ID = "id";

        final String GIT_DEV_LIST = "items";

        List<NaijaDevelopers> getDeveloperProfile(String devString) throws JSONException {
            loadedDeveloper = new ArrayList<>();

            // parsing commences using the names of the JSON objects to be extracted
            JSONObject devProJson = new JSONObject(devString);
            JSONArray devProArray = devProJson.getJSONArray(GIT_DEV_LIST);

            for (int i = 0; i < devProArray.length(); i++){
                JSONObject singleDevPro = devProArray.getJSONObject(i);

                // get developer id
                int id = singleDevPro.getInt(GIT_ID);
                // get developer username
                String username = singleDevPro.getString(GIT_NAME);
                // get developer profile photo
                String profile_photo = singleDevPro.getString(GIT_PHOTO);
                // get the developer's profile url
                String profile_url = singleDevPro.getString(GIT_PROFILE_LINK);

                NaijaDevelopers mNaijaDevelopers = new NaijaDevelopers(id, profile_photo, profile_url, username);
                loadedDeveloper.add(mNaijaDevelopers);
            }
            return loadedDeveloper;
        }

        @Override
        protected void onPostExecute(List<NaijaDevelopers> NaijaDeveloperses) {
            if (NaijaDeveloperses != null){
                mDeveloperAdapter.exchangeData(NaijaDeveloperses);
                progressBar.setVisibility(View.GONE);
                toolbar.setVisibility(View.VISIBLE);
                listView.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected List<NaijaDevelopers> doInBackground(String... params) {

            if (params.length == 0){
                return null;
            }
            String searchQuery = params[0];

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // will contain raw json responses
            String JsonStrDevList = null;

            String location = "Lagos";

            try {
                // to construct a url for github search query
                final String BASE_URL = "https://api.github.com/search/users?";
                final String Q_PARAM = "q";
                final String LOC_PARAM = "location";

                Uri uri = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter(Q_PARAM, searchQuery)
                        .appendQueryParameter(LOC_PARAM, location).build();

                URL url = new URL(uri.toString().replace("&", "+").replace("location=","location:"));

                // to create the request to developer.github.com and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder stringBuilder = new StringBuilder();
                if (inputStream == null){
                    //Do nothing
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while ((line = reader.readLine()) != null){
                    stringBuilder.append(line).append("\n");
                }
                if (stringBuilder.length() ==0){
                    // stuff is empty
                    return null;
                }

                JsonStrDevList = stringBuilder.toString();
            }catch (IOException e){
                Log.e(LOG_TAG, "Error ooo", e);
            }finally {
                if (urlConnection != null){
                    urlConnection.disconnect();
                }
                if (reader != null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            try {
                return getDeveloperProfile(JsonStrDevList);
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Some Error occured", e);
            }
            return new ArrayList<>();
        }
    }

}
