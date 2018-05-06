package com.udacity.nanodegree.blooddonation.ui.home.presenter;

import com.firebase.geofire.GeoLocation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.udacity.nanodegree.blooddonation.constants.SharedPrefConstants;
import com.udacity.nanodegree.blooddonation.data.model.Location;
import com.udacity.nanodegree.blooddonation.data.model.ReceiverDonorRequestType;
import com.udacity.nanodegree.blooddonation.data.model.User;
import com.udacity.nanodegree.blooddonation.data.source.DonationDataSource;
import com.udacity.nanodegree.blooddonation.storage.SharedPreferenceManager;
import com.udacity.nanodegree.blooddonation.ui.home.RequestDialogContract;
import com.udacity.nanodegree.blooddonation.ui.home.model.RequestDetails;

/**
 * Created by riteshksingh on Apr, 2018
 */
public class RequestDialogPresenter implements RequestDialogContract.Presenter {

    private final FirebaseAuth mFirebaseAuth;
    private final DonationDataSource mDataRepo;
    private SharedPreferenceManager mSharedPreferenceManager;
    private RequestDialogContract.View mView;
    private ReceiverDonorRequestType receiverDonorRequestType;

    public RequestDialogPresenter(RequestDialogContract.View view, FirebaseAuth firebaseAuth,
                                  DonationDataSource dataRepo, SharedPreferenceManager sharedPreferenceManager) {
        this.mView = view;
        this.mFirebaseAuth = firebaseAuth;
        this.mDataRepo = dataRepo;
        mSharedPreferenceManager = sharedPreferenceManager;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    private void prepareReceiverDonorRequestType(RequestDetails requestDetails) {
        Location location = new Location(requestDetails.latitude.get(), requestDetails.longitude.get());
        receiverDonorRequestType = new ReceiverDonorRequestType();
        receiverDonorRequestType.setLocation(location);
        receiverDonorRequestType.setPurpose(requestDetails.purpose.get());
        receiverDonorRequestType.setbGp(requestDetails.bloodGroup.get());
    }

    private void saveReceiverDetailsInDb(RequestDetails requestDetails) {
        prepareReceiverDonorRequestType(requestDetails);
        mDataRepo.saveReceiverDetails(mFirebaseAuth.getCurrentUser().getUid(),
                receiverDonorRequestType);
        mView.dismissDialog(true, receiverDonorRequestType);
    }

    private void saveDonorDetails(RequestDetails requestDetails) {

        requestDetails.bloodGroup.set(new Gson().fromJson(mSharedPreferenceManager.getString(SharedPrefConstants.USER_DETAILS), User.class).bloodGroup);
        prepareReceiverDonorRequestType(requestDetails);
        mDataRepo.saveDonorDetails(mFirebaseAuth.getCurrentUser().getUid(),
                requestDetails.bloodGroup.get(),
                new GeoLocation(requestDetails.latitude.get(), requestDetails.longitude.get()),
                new ISaveDonorDetails() {
                    @Override
                    public void success() {
                        mView.dismissDialog(false, receiverDonorRequestType);
                    }

                    @Override
                    public void fail() {
                        mView.dismissDialog(false, receiverDonorRequestType);
                    }
                });
    }

    @Override
    public void onSubmitButtonClick(RequestDetails requestDetails) {
        // Request Type is receiver
        if (requestDetails.requestType.get().trim().equalsIgnoreCase("0"))
            saveReceiverDetailsInDb(requestDetails);
        else
            // Request Type is donor
            saveDonorDetails(requestDetails);
    }

    @Override
    public void onLocationClick() {
        mView.getLastLocation();
    }

    public interface ISaveDonorDetails {
        void success();

        void fail();
    }
}
