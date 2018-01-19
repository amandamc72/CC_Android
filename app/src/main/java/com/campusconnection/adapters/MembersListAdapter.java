package com.campusconnection.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.campusconnection.ProfileActivity;
import com.campusconnection.R;
import com.campusconnection.model.responses.MemberListResponse;
import com.campusconnection.util.AppUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MembersListAdapter extends RecyclerView.Adapter<MembersListAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<MemberListResponse.MemberListData> mListItems;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MembersListAdapter(Context mContext, ArrayList<MemberListResponse.MemberListData> mListItems) {
        this.mContext = mContext;
        this.mListItems = mListItems;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_list_row, parent, false);
        // create a new view
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //String nameAgeText = mListItems.get(position).getFirstName() + "," + mListItems.get(position).getAge();
        String dob = mListItems.get(position).getAge();
        String [] parts = dob.split("-");
        String age = AppUtils.dateToAge( Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
        Picasso.with(mContext).load(mListItems.get(position).getThumbnail()).fit().into(holder.thumbnail);
        holder.firstNameAndAge.setText(mListItems.get(position).getFirstName() + ", " + age);
        holder.school.setText(mListItems.get(position).getSchool());
        holder.major.setText(mListItems.get(position).getMajor());

        holder.bindUserId(mListItems.get(position).getId());
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mListItems.size();
    }


    // Provide a reference to the views for each data item
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public int id;
        public ImageView thumbnail;
        public TextView firstNameAndAge;
        public TextView school;
        public TextView major;

        public ViewHolder(View v) {
            super(v);
            thumbnail = (ImageView) v.findViewById(R.id.thumbnail);
            firstNameAndAge = (TextView) v.findViewById(R.id.firstNameAndAge);
            school = (TextView) v.findViewById(R.id.school);
            major = (TextView) v.findViewById(R.id.major);
            v.setOnClickListener(this);

        }

        public void bindUserId(int id){
            this.id = id;
        }

        @Override
        public void onClick(View v) {
            Log.d("D","CODE is: " +  id);
            Intent intent = new Intent(mContext, ProfileActivity.class);
            intent.putExtra("id", id);
            mContext.startActivity(intent);
            ((Activity) mContext).overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
        }
    }

}
