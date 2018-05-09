package com.eyo.bethel.gitnaija;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eyo.bethel.gitnaija.Api.DevListService;
import com.eyo.bethel.gitnaija.Api.RestApiBuilder;
import com.eyo.bethel.gitnaija.Utilities.devAdapter;
import com.eyo.bethel.gitnaija.data.DevList;
import com.eyo.bethel.gitnaija.data.NaijaDevelopers;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import static com.eyo.bethel.gitnaija.Utilities.Utils.isDeviceOnline;


public class developerFragment extends Fragment {

    private final String PROFILE_KEY = "profile";

    ProgressBar progressBar;
    View emptyScreen;
    TextView emptyScreenText, tryAgain;
    RecyclerView recyclerView;
    ImageView errorImage;
    Toolbar toolbar;

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

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        toolbar = (Toolbar) view.findViewById(R.id.app_bar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.icons));

        updateDeveloperList();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateDeveloperList();
    }

    public void updateDeveloperList(){
        String nullText;
        if (isDeviceOnline(getActivity())){
            FetchNaijaDevelopers();
        }else {
            emptyScreen.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            nullText = getResources().getString(R.string.error);
            emptyScreenFormat(R.drawable.cloud_problem, nullText,false);
        }
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

    public void FetchNaijaDevelopers(){
        String searchParams = "language:java location:lagos";
        DevListService devService = new RestApiBuilder().getDevListService();
        Call<DevList> devListCall = devService.getDevList(searchParams);
        devListCall.enqueue(new Callback<DevList>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<DevList> call, Response<DevList> response) {
                if(response.isSuccessful()){
                    DevList naijaDevs = response.body();
                    Log.w("2.0 getFeed> full json wrapped in pretty printed gson =>",
                            new GsonBuilder().setPrettyPrinting().create().toJson(response));
                    fixAdapter(naijaDevs);
                    progressBar.setVisibility(View.GONE);
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

    public void fixAdapter(DevList naijaDevs){
        devAdapter adapter = new devAdapter(naijaDevs.getItems());
        toolbar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(adapter);
        if(adapter.getItemCount()==0) {

            emptyScreen.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            emptyScreenFormat(R.drawable.cloud_problem, "problem setting adapter",false);

        }
    }

}
