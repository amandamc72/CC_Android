package com.campusconnection;

import android.app.Activity;
import android.media.Image;
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

public class ListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<MemberListResponse.MemberListData> listItems;

    public ListAdapter(Context context, ArrayList<MemberListResponse.MemberListData> listItems){
        this.context = context;
        this.listItems = listItems;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_row, null);
        }

        int id = listItems.get(position).getId();

        ImageView thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);
        Picasso.with(context).load(listItems.get(position).getThumbnail()).into(thumbnail);

        TextView firstName = (TextView) convertView.findViewById(R.id.firstName);
        firstName.setText(listItems.get(position).getFirstName());

        TextView age = (TextView) convertView.findViewById(R.id.age);
        age.setText(listItems.get(position).getAge());

        TextView school = (TextView) convertView.findViewById(R.id.school);
        school.setText(listItems.get(position).getSchool());

        TextView major = (TextView) convertView.findViewById(R.id.major);
        major.setText(listItems.get(position).getMajor());

        return convertView;
    }
}
