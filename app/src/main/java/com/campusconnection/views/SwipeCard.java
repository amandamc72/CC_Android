package com.campusconnection.views;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.campusconnection.R;
import com.campusconnection.model.MemberListResponse;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;
import com.squareup.picasso.Picasso;

@NonReusable
@Layout(R.layout.swipe_card_view)
public class SwipeCard implements Parcelable {

    @View(R.id.swipeProfileImage)
    private ImageView swipeProfileImage;

    @View(R.id.swipeNameAgeLabel)
    private TextView swipeNameAgeLabel;

    @View(R.id.swipeSchoolLabel)
    private TextView swipeSchoolLabel;

    @View(R.id.swipeMajorLabel)
    private TextView swipeMajorLabel;

    private MemberListResponse.MemberListData mProfile;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;

    public SwipeCard(Context context, MemberListResponse.MemberListData profile, SwipePlaceHolderView swipeView) {
        mContext = context;
        mProfile = profile;
        mSwipeView = swipeView;
    }

    public MemberListResponse.MemberListData getProfile(){
        return mProfile;
    }

    @Resolve
    private void onResolved() {
        Picasso.with(mContext).load(mProfile.getThumbnail()).into(swipeProfileImage);
        swipeNameAgeLabel.setText(mProfile.getFirstName() + ", " + mProfile.getAge());
        swipeSchoolLabel.setText(mProfile.getSchool());
        swipeMajorLabel.setText(mProfile.getMajor());
    }

    @Click(R.id.swipeProfileImage)
    private void onClick(){
        Log.d("EVENT", "onClick");
    }

    @SwipeOut
    private void onSwipedOut(){
        Log.d("EVENT", "onSwipedOut");
    }

    @SwipeCancelState
    private void onSwipeCancelState(){
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn(){
        Log.d("EVENT", "onSwipedIn");
    }

    @SwipeInState
    private void onSwipeInState(){
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState(){
        Log.d("EVENT", "onSwipeOutState");
    }

    private SwipeCard(Parcel in) {
        swipeProfileImage = (ImageView) in.readValue(ImageView.class.getClassLoader());
        swipeNameAgeLabel = (TextView) in.readValue(TextView.class.getClassLoader());
        swipeSchoolLabel = (TextView) in.readValue(TextView.class.getClassLoader());
        swipeMajorLabel = (TextView) in.readValue(TextView.class.getClassLoader());
        mProfile = (MemberListResponse.MemberListData) in.readValue(MemberListResponse.MemberListData.class.getClassLoader());
        mContext = (Context) in.readValue(Context.class.getClassLoader());
        mSwipeView = (SwipePlaceHolderView) in.readValue(SwipePlaceHolderView.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(swipeProfileImage);
        dest.writeValue(swipeNameAgeLabel);
        dest.writeValue(swipeSchoolLabel);
        dest.writeValue(swipeMajorLabel);
        dest.writeValue(mProfile);
        dest.writeValue(mContext);
        dest.writeValue(mSwipeView);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SwipeCard> CREATOR = new Parcelable.Creator<SwipeCard>() {
        @Override
        public SwipeCard createFromParcel(Parcel in) {
            return new SwipeCard(in);
        }

        @Override
        public SwipeCard[] newArray(int size) {
            return new SwipeCard[size];
        }
    };

}
