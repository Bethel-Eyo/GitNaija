package com.eyo.bethel.gitnaija.Utilities;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eyo.bethel.gitnaija.R;
import com.eyo.bethel.gitnaija.data.NaijaDevelopers;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class devAdapter extends RecyclerView.Adapter<devAdapter.developerViewHolder> {

    private List<NaijaDevelopers> naijaDevelopersList;
    private Context mContext;

    public devAdapter(List<NaijaDevelopers> naijaDevelopersList) {
        this.naijaDevelopersList = naijaDevelopersList;
    }

    @Override
    public developerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dev_list_view_holder, parent, false);
        return new developerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(developerViewHolder holder, int position) {
        // we can therefore bind our views accordingly
        String imageUrl = naijaDevelopersList.get(position).getImageUrl();
        Picasso.with(mContext).load(imageUrl).into(holder.profilePhoto);
        holder.developerName.setText(naijaDevelopersList.get(position).getDeveloperUsername());
        holder.devEmail.setText(naijaDevelopersList.get(position).getProfileUrl());
    }

    @Override
    public int getItemCount() {
        return naijaDevelopersList.size();
    }

    public static class developerViewHolder extends RecyclerView.ViewHolder {
        private TextView developerName, devEmail, devPublicRepos;
        private CircleImageView profilePhoto;

        public developerViewHolder(View itemView){
            super(itemView);
            developerName = (TextView) itemView.findViewById(R.id.dev_name);
            devEmail = (TextView) itemView.findViewById(R.id.dev_email);
            profilePhoto = (CircleImageView) itemView.findViewById(R.id.profile_image);
            devPublicRepos = (TextView) itemView.findViewById(R.id.public_repos);
        }
    }
}
