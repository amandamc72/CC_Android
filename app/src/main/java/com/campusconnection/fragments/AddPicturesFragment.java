package com.campusconnection.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.campusconnection.R;
import com.campusconnection.adapters.GridViewAdapter;
import com.campusconnection.model.requests.RemoveRequest;
import com.campusconnection.model.responses.GenericResponse;
import com.campusconnection.model.responses.ImageUploadResponse;
import com.campusconnection.rest.ApiClient;
import com.campusconnection.rest.ApiInterface;
import com.campusconnection.util.AppUtils;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class AddPicturesFragment extends Fragment implements GridViewAdapter.MediaPath, GridViewAdapter.RemovePicture {
    private static final String ARG_PARAM1 = "pictureUrls";

    private ArrayList<String> mImages;
    private OnFragmentInteractionListener mListener;
    private int mPhotoPos;
    private GridView mEditPicsGrid;
    private GridViewAdapter mGridViewAdapter;

    public AddPicturesFragment() {
        // Required empty public constructor
    }

    public static AddPicturesFragment newInstance(ArrayList<String> images) {
        AddPicturesFragment fragment = new AddPicturesFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_PARAM1, images);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mImages = getArguments().getStringArrayList(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mGridViewAdapter = new GridViewAdapter(this, this, getActivity(), mImages);
        View rootView = inflater.inflate(R.layout.fragment_add_pictures, container, false);
        mEditPicsGrid = (GridView) rootView.findViewById(R.id.editProfilePicGrid);
        mEditPicsGrid.setAdapter(mGridViewAdapter);
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(ArrayList<String> imgs) {
        if (mListener != null) {
            mListener.onImagePass(imgs);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSelect(int value, int pos) {
        Log.d("D", "in add pic frag value is: " + value + " and pos is " + pos);
        mPhotoPos = pos;
        if (value == 0) {
            CropImage.activity()
                    .setMinCropResultSize(320,200)
                    .setMaxCropResultSize(620,400)
                    .start(getContext(), this);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("D", "onActivityResult in add pic fragment!!!!");
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Log.d("D", "resultUri: " + resultUri);
                uploadFile(resultUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public String setIsDefaultFlag(int pos) {
        String isDefault = "false";
        if (pos == 0) {
            isDefault = "true";
        }
        return isDefault;
    }

    @Override
    public void onRemove(String path, int pos) {
        final int imgPos = pos;
        ApiInterface apiService = ApiClient.getClient(getActivity()).create(ApiInterface.class);
        Call<GenericResponse> call = apiService.deletePicture(new RemoveRequest(path, setIsDefaultFlag(pos)));
        call.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if(response.isSuccessful()) {
                    GenericResponse res = response.body();
                    Boolean error = res.getError();
                    String message = res.getMessage();
                    if (error) {
                        AppUtils.showPopMessage(getActivity(), message);
                    } else {
                        mImages.remove(imgPos);
                        mGridViewAdapter.notifyDataSetChanged();
                        mEditPicsGrid.setAdapter(mGridViewAdapter);
                        passUpdatedImages(mImages);
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void uploadFile(Uri fileUri) {
        File file = new File(fileUri.getPath());
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestFile);

        RequestBody isDefaultBody =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, setIsDefaultFlag(mPhotoPos));

        ApiInterface apiService = ApiClient.getClient(getActivity()).create(ApiInterface.class);
        Call<ImageUploadResponse> call = apiService.uploadPicture(isDefaultBody, body);

        call.enqueue(new Callback<ImageUploadResponse>() {
            @Override
            public void onResponse(Call<ImageUploadResponse> call, Response<ImageUploadResponse> response) {
                ImageUploadResponse res = response.body();
                Log.d("D", "RESponse is: " + res);
                if (!res.getError()) {
                    String uploadedImgPath = res.getImage();

//                    String s = uploadedImgPath.get(0).toString();
//                    Log.d("D", "before clean is: " + s);
//                    s = s.substring(s.indexOf("=")+1, s.length()-1);
//                    Log.d("D", "cleaned string is: " + s);
                    mImages.add(mImages.size(), uploadedImgPath);
                    mGridViewAdapter.notifyDataSetChanged();
                    mEditPicsGrid.setAdapter(mGridViewAdapter);
                    passUpdatedImages(mImages);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                call.cancel();
            }
        });
    }

    public void passUpdatedImages(ArrayList<String> data) {
        mListener.onImagePass(data);
    }

    public interface OnFragmentInteractionListener {
        void onImagePass(ArrayList<String> imgs);
    }
}
