package com.campusconnection;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.campusconnection.model.MemberListResponse;
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
    public MembersListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_list_row, parent, false);
        // create a new view
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Picasso.with(mContext).load(mListItems.get(position).getThumbnail()).into(holder.thumbnail);
        holder.firstName.setText(mListItems.get(position).getFirstName());
        holder.age.setText(mListItems.get(position).getAge());
        holder.school.setText(mListItems.get(position).getSchool());
        holder.major.setText(mListItems.get(position).getMajor());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mListItems.size();
    }



    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public int id;
        public ImageView thumbnail;
        public TextView firstName;
        public TextView age;
        public TextView school;
        public TextView major;

        public ViewHolder (View v) {
            super(v);
            thumbnail = (ImageView) v.findViewById(R.id.thumbnail);
            firstName = (TextView) v.findViewById(R.id.firstName);
            age = (TextView) v.findViewById(R.id.age);
            school = (TextView) v.findViewById(R.id.school);
            major = (TextView) v.findViewById(R.id.major);
        }
    }


    ///////////////////////////////////// OLD CODe ///////////////////////////

//    public MembersListAdapter(Context context, ArrayList<MemberListResponse.MemberListData> listItems){
//        this.context = context;
//        this.listItems = listItems;
//    }
//
//    @Override
//    public int getCount() {
//        return listItems.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return listItems.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            LayoutInflater mInflater = (LayoutInflater)
//                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
//            convertView = mInflater.inflate(R.layout.member_list_row, null);
//        }
//
//        int id = listItems.get(position).getId();
//
//        ImageView thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);
//        Picasso.with(context).load(listItems.get(position).getThumbnail()).into(thumbnail);
//
//        TextView firstName = (TextView) convertView.findViewById(R.id.firstName);
//        firstName.setText(listItems.get(position).getFirstName());
//
//        TextView age = (TextView) convertView.findViewById(R.id.age);
//        age.setText(listItems.get(position).getAge());
//
//        TextView school = (TextView) convertView.findViewById(R.id.school);
//        school.setText(listItems.get(position).getSchool());
//
//        TextView major = (TextView) convertView.findViewById(R.id.major);
//        major.setText(listItems.get(position).getMajor());
//
//        return convertView;
//    }
}
