package com.eyo.bethel.gitnaija;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.eyo.bethel.gitnaija.data.NaijaDevelopers;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;


public class developerAdapter extends BaseAdapter {
    private List<NaijaDevelopers> NaijaDevelopersList = new ArrayList<>();
    private Context mContext;
    private boolean notifyOnChange = true;

    public developerAdapter(Context mContext) {
        this.mContext = mContext.getApplicationContext();
    }

    public void exchangeData(List<NaijaDevelopers> developerProfile){
        this.NaijaDevelopersList = developerProfile;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return NaijaDevelopersList.size();
    }

    @Override
    public Object getItem(int position) {
        return NaijaDevelopersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        developerViewHolder viewHolder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.dev_list_view_holder, parent, false);
            viewHolder = new developerViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (developerViewHolder) convertView.getTag();
        }
        // we can therefore bind our views accordingly
        String imageUrl = NaijaDevelopersList.get(position).getImageUrl();
        Picasso.with(mContext).load(imageUrl).into(viewHolder.profilePhoto);
        viewHolder.developerName.setText(NaijaDevelopersList.get(position).getDeveloperUsername());
        return convertView;
    }

    public void clearData(){
        this.NaijaDevelopersList.clear();
        if (notifyOnChange) notifyDataSetChanged();
    }

    public static class developerViewHolder{
        private TextView developerName, devEmail;
        private CircleImageView profilePhoto;

        public developerViewHolder(View itemView){
            developerName = (TextView) itemView.findViewById(R.id.dev_name);
            devEmail = (TextView) itemView.findViewById(R.id.dev_email);
            profilePhoto = (CircleImageView) itemView.findViewById(R.id.profile_image);
        }
    }
}
