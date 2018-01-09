package com.campusconnection.adapters;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.campusconnection.R;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class GridViewAdapter extends ArrayAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    private ArrayList<String> mImageUrls;
    private MediaPath mMediaPath;
    private RemovePicture mRemovePicture;

    public GridViewAdapter(MediaPath mediaPath, RemovePicture removePicture,
                           Context context, ArrayList<String> images) {
        super(context, R.layout.gridview_item, images);

        mContext = context;
        mImageUrls = images;
        mInflater = LayoutInflater.from(context);
        mMediaPath = mediaPath;
        mRemovePicture = removePicture;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        final int imagePos = pos;

        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.gridview_item, parent, false);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.editPicImg);
            viewHolder.imageButton = (ImageButton) convertView.findViewById(R.id.editPicButton);
            viewHolder.imageButtonBackground = (View) convertView.findViewById(R.id.editPicButtonBackground);
            try {
                mImageUrls.get(pos);
                Log.d("D", mImageUrls.get(pos));
                Picasso.with(mContext)
                        .load(mImageUrls.get(pos))
                        .fit()
                        .into(viewHolder.imageView);
                viewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String relPath = mImageUrls.get(imagePos).substring(mImageUrls.get(imagePos).indexOf("aws.com")+7);
                        Log.d("D", "Remove pic " + relPath);
                        mRemovePicture.onRemove(relPath, imagePos);
                    }
                });
            } catch (IndexOutOfBoundsException e) {

                Picasso.with(mContext)
                        .load("http://placehold.it/150x150")
                        .fit()
                        .into(viewHolder.imageView);
                viewHolder.imageButton.setVisibility(View.GONE);
                viewHolder.imageButtonBackground.setVisibility(View.GONE);

                viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("D", "Trigger Add Pic");
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle(R.string.choose_media)
                                .setItems(R.array.media_choices_array, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case 0:
                                                mMediaPath.onSelect(0, imagePos);
                                                break;
                                            case 1:
                                                mMediaPath.onSelect(1, imagePos);
                                                break;
                                            case 2:
                                                mMediaPath.onSelect(2, imagePos);
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                });
                        builder.show();
                    }
                });
            }

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return 6;
    }

    public class ViewHolder {
        ImageView imageView;
        ImageButton imageButton;
        View imageButtonBackground;
    }

    public interface MediaPath {
        void onSelect(int value, int pos);
    }

    public interface RemovePicture {
        void onRemove(String path, int pos);
    }

}
