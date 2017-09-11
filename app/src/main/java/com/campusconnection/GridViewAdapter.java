package com.campusconnection;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GridViewAdapter extends ArrayAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    private ArrayList<String> mImageUrls;

    public GridViewAdapter(Context context, ArrayList<String> images) {
        super(context, R.layout.gridview_image, images);

        mContext = context;
        mImageUrls = images;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.gridview_image, parent, false);
        }

        Picasso
                .with(mContext)
                .load(mImageUrls.get(pos))
                .fit()
                .into((ImageView) convertView);

        return convertView;
    }

}
