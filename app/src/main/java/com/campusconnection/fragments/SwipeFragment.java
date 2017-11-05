package com.campusconnection.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.campusconnection.R;
import com.campusconnection.model.responses.MemberListResponse;
import com.campusconnection.views.SwipeCard;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.util.ArrayList;


public class SwipeFragment extends Fragment {

    private static final String ARG_PARAM1 = "profileList";
    private static final String ARG_PARAM2 = "savedProfileList";

    private SwipePlaceHolderView mSwipeView;
    private MemberListResponse mMembersList;
    private OnFragmentInteractionListener mListener;
    private ArrayList<SwipeCard> mSavedCards;

    public SwipeFragment() {
        // Required empty public constructor
    }


    public static SwipeFragment newInstance(MemberListResponse list) {
        SwipeFragment fragment = new SwipeFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMembersList = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_swipe, container, false);

        mSwipeView = (SwipePlaceHolderView)rootView.findViewById(R.id.swipeView);

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setSwipeRotationAngle(5)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.swipe_out_msg_view));

        if (mSavedCards == null) {
            Log.d("D","No saved cards");
            for(MemberListResponse.MemberListData profiles: mMembersList.getMemberList()) {
                mSwipeView.addView(new SwipeCard(getActivity(), profiles, mSwipeView));
            }
        } else {
            Log.d("D","Saved cards");
            for (int i = 0; i < mSavedCards.size(); i++){
                mSwipeView.addView(mSavedCards.get(i));
            }
        }


        rootView.findViewById(R.id.rejectBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("D","doSwipe false");
                mSwipeView.doSwipe(false);
            }
        });

        rootView.findViewById(R.id.acceptBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("D","doSwipe true");
                mSwipeView.doSwipe(true);
            }
        });

        return rootView;
    }

    // TODO: Rename method, upndate argument and hook method ito UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onPause() {
        super.onPause();
        mSavedCards = (ArrayList)mSwipeView.getAllResolvers();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM2, mSavedCards);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getArguments() != null) {
            mSavedCards = getArguments().getParcelable(ARG_PARAM2);
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
