package com.lookapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.lookapp.R;
import com.lookapp.api.exception.LookAppException;
import com.lookapp.bean.Spot;
import com.lookapp.bean.SpotForAdmin;
import com.lookapp.support.LookAppService;
import com.lookapp.support.LookAppTask;

/**
 * Created by user on 05/07/2015.
 */
public class AdminFragment extends CustomFragment implements View.OnClickListener{

    private View rootView;
    private EditText freeSitsEt, eventKaEt,eventEnEt, spotNameEtKa, spotNameEtEn, workingHoursStart,workingHoursEnd,contactNumber,addressKa,addressEn;
    private Button freeSitsSaveBtn, eventSaveBtn, otherSaveBtn;
    private CheckBox hasWifiCheckBox, hasNonSmokingAreaCheckBox, canReservePlaceCheckBox;
    private SpotForAdmin spotForAdmin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_admin, container, false);

        initEditLayouts();

        getSpotForAdmin(app.getAdminSpotId());


        return rootView;
    }



    private void getSpotForAdmin(final long adminSpotId) {
        LookAppTask<SpotForAdmin> spotForAdminDownloadTask = new LookAppTask<SpotForAdmin>() {
            @Override
            protected SpotForAdmin doInBackground(Void... params) {
                LookAppService las = LookAppService.getInstance();

                try {
                    return las.getSpotForAdmin(adminSpotId);
                } catch (LookAppException e) {
                    exception = e;
                }

                return null;
            }

            @Override
            protected void onPostExecute(SpotForAdmin spotForAdmin) {

                if(exception == null){
                    AdminFragment.this.spotForAdmin = spotForAdmin;
                    initDefaults();
                }

            }
        };
        spotForAdminDownloadTask.execute();
    }

    private void initDefaults() {

        eventKaEt.setText(spotForAdmin.getEventDescriptionKa(), TextView.BufferType.EDITABLE);
        eventEnEt.setText(spotForAdmin.getEventDescription(), TextView.BufferType.EDITABLE);
        spotNameEtEn.setText(spotForAdmin.getSpotName(), TextView.BufferType.EDITABLE);
        spotNameEtKa.setText(spotForAdmin.getSpotNameKa(), TextView.BufferType.EDITABLE);

        String arr[] = spotForAdmin.getWorkingHours().split("-");

        workingHoursStart.setText(arr[0],TextView.BufferType.EDITABLE);
        workingHoursEnd.setText(arr[1],TextView.BufferType.EDITABLE);

        contactNumber.setText(spotForAdmin.getContactInfo(),TextView.BufferType.EDITABLE);
        addressKa.setText(spotForAdmin.getSpotAddressKa(),TextView.BufferType.EDITABLE);
        addressEn.setText(spotForAdmin.getSpotAddress(),TextView.BufferType.EDITABLE);

        hasWifiCheckBox.setChecked(spotForAdmin.isHasWifi());
        hasNonSmokingAreaCheckBox.setChecked(spotForAdmin.isHasNonSmokerArea());
        canReservePlaceCheckBox.setChecked(spotForAdmin.isCanReservePlace());


    }

    private void initEditLayouts() {
        freeSitsEt = (EditText)rootView.findViewById(R.id.free_sits_et);
        eventKaEt = (EditText)rootView.findViewById(R.id.event_ka);
        eventEnEt = (EditText)rootView.findViewById(R.id.event_en);
        spotNameEtKa = (EditText)rootView.findViewById(R.id.spot_name_et_ka);
        spotNameEtEn = (EditText)rootView.findViewById(R.id.spot_name_et_en);
        workingHoursStart = (EditText)rootView.findViewById(R.id.working_hours_start);
        workingHoursEnd = (EditText)rootView.findViewById(R.id.working_hours_end);
        contactNumber = (EditText)rootView.findViewById(R.id.contact_number);
        addressKa = (EditText)rootView.findViewById(R.id.address_ka);
        addressEn = (EditText)rootView.findViewById(R.id.address_en);


        freeSitsSaveBtn = (Button)rootView.findViewById(R.id.free_sits_save_btn);
        eventSaveBtn = (Button)rootView.findViewById(R.id.event_save_btn);
        otherSaveBtn = (Button)rootView.findViewById(R.id.save_others);

        hasWifiCheckBox = (CheckBox)rootView.findViewById(R.id.has_wifi_checkbox);
        hasNonSmokingAreaCheckBox = (CheckBox)rootView.findViewById(R.id.has_non_smoking_area_checkbox);
        canReservePlaceCheckBox = (CheckBox)rootView.findViewById(R.id.can_reserve_place_checkbox);

        freeSitsSaveBtn.setOnClickListener(this);
        eventSaveBtn.setOnClickListener(this);
        otherSaveBtn.setOnClickListener(this);



    }


    @Override
    public void onClick(View view) {

        if(view.getId() == freeSitsSaveBtn.getId()){

            final String text = freeSitsEt.getText().toString();

            LookAppTask<Void> updateSitsInfo = new LookAppTask<Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    LookAppService las = LookAppService.getInstance();
                    try {
                        las.updateFreeSitsInfo(app.getAdminSpotId(),text);
                    } catch (LookAppException e) {
                        exception = e;
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    if(exception == null){
//                        AdminFragment.this.getSpotForAdmin(app.getAdminSpotId());
                    }
                }
            };
            updateSitsInfo.execute();

        }else if(view.getId() == eventSaveBtn.getId()){

            SpotForAdmin spotForAdmin = new SpotForAdmin();
            spotForAdmin.setSpotId(app.getAdminSpotId());
            spotForAdmin.setEventDescription(eventEnEt.getText().toString());
            spotForAdmin.setEventDescriptionKa(eventKaEt.getText().toString());

            updateEventInfo(spotForAdmin);

        }else if(view.getId() == otherSaveBtn.getId()){

            SpotForAdmin spotForAdmin = new SpotForAdmin();
            spotForAdmin.setSpotId(app.getAdminSpotId());
            spotForAdmin.setSpotName(spotNameEtEn.getText().toString());
            spotForAdmin.setSpotNameKa(spotNameEtKa.getText().toString());
            spotForAdmin.setHasWifi(hasWifiCheckBox.isChecked());
            spotForAdmin.setHasNonSmokerArea(hasNonSmokingAreaCheckBox.isChecked());
            spotForAdmin.setCanReservePlace(canReservePlaceCheckBox.isChecked());

            spotForAdmin.setWorkingHours(workingHoursStart.getText().toString() + "-" + workingHoursEnd.getText().toString());

            spotForAdmin.setContactInfo(contactNumber.getText().toString());
            spotForAdmin.setSpotAddress(addressEn.getText().toString());
            spotForAdmin.setSpotAddressKa(addressKa.getText().toString());

            updateSpotInfo(spotForAdmin);


        }
    }

    private void updateEventInfo(final SpotForAdmin spotForAdmin) {
        LookAppTask<Void> updateSpotInfo = new LookAppTask<Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                LookAppService las = LookAppService.getInstance();
                try {
                    las.updateEventInfo(spotForAdmin);
                } catch (LookAppException e) {
                    exception = e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if(exception == null){
                    AdminFragment.this.getSpotForAdmin(app.getAdminSpotId());
                }
            }
        };
        updateSpotInfo.execute();
    }

    private void updateSpotInfo(final SpotForAdmin spotForAdmin) {
        LookAppTask<Void> updateSpotInfo = new LookAppTask<Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                LookAppService las = LookAppService.getInstance();
                try {
                    las.updateSpotInfo(spotForAdmin);
                } catch (LookAppException e) {
                    exception = e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if(exception == null){
                    AdminFragment.this.getSpotForAdmin(app.getAdminSpotId());
                }
            }
        };
        updateSpotInfo.execute();
    }
}
