package com.campusconnection;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;


/*
    Starting this fragment
    It needs the photo urls to display

    Things that will happen in this fragment
    Case 1. Click on an empty photo spot -> goto gallery to pick photo -> crop -> upload to server -> return to view of photos
    Case 2. Click on delete icon over photo -> Send delete request to server -> poof photo gone
    Case 3. Click on photo -> enlarge to full screen

    Leaving this fragment
    ??????

 */

public class AddPicturesFragment extends Fragment {
    private static final String ARG_PARAM1 = "pictureUrls";

    private ArrayList<String> mImages;
    private OnFragmentInteractionListener mListener;

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

        View rootView = inflater.inflate(R.layout.fragment_add_pictures, container, false);
        GridView editPicsGrid = (GridView) rootView.findViewById(R.id.editProfilePicGrid);
        editPicsGrid.setAdapter(new GridViewAdapter(getActivity(), mImages));
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
