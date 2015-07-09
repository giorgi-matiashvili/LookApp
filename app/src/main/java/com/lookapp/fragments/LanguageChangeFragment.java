package com.lookapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import com.lookapp.R;
import com.lookapp.activities.RegisterActivity;
import com.lookapp.activities.StartupActivity;
import com.lookapp.settings.Settings;
import com.lookapp.utils.Language;

/**
 * Created by user on 04/07/2015.
 */
public class LanguageChangeFragment extends CustomFragment implements View.OnClickListener {
    private View rootView;
//    private RadioGroup languageRadioGroup;
    private Button btnEn, btnKa;
    private Language language;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_language_change, container, false);
//        languageRadioGroup = (RadioGroup) rootView.findViewById(R.id.language_radio_group);
//        language = Settings.getLanguage();
//
//        if (Settings.getLanguage() == Language.EN) {
//            languageRadioGroup.check(R.id.radio_btn_en);
//        } else {
//            languageRadioGroup.check(R.id.radio_btn_ka);
//        }
        rootView.findViewById(R.id.language_save).setOnClickListener(this);
        btnEn = (Button) rootView.findViewById(R.id.choose_language_button_en);
        btnKa = (Button) rootView.findViewById(R.id.choose_language_button_ka);
        btnEn.setOnClickListener(this);
        btnKa.setOnClickListener(this);

        setLanguage();
        return rootView;
    }

    private void setLanguage() {
        if(language == null)
            language = Settings.getLanguage();
        if (language == Language.EN) {
            btnEn.setEnabled(false);
            btnKa.setEnabled(true);

            btnEn.setBackgroundResource(R.drawable.round_button_selector);
            btnKa.setBackgroundResource(R.drawable.round_shape_grey);

        } else {
            btnEn.setEnabled(true);
            btnKa.setEnabled(false);

            btnKa.setBackgroundResource(R.drawable.round_button_selector);
            btnEn.setBackgroundResource(R.drawable.round_shape_grey);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.language_save) {
            if (language == Settings.getLanguage()) {
                return;
            }

            if(language != null) {
                Settings.setLanguage(language);
                app.updateLocale();

                getActivity().finish();

                Intent intent = new Intent(getActivity(), StartupActivity.class);
                startActivity(intent);
            }
        }else  if (view.getId() == btnEn.getId()) {
            language = Language.EN;
            setLanguage();
        } else if (view.getId() == btnKa.getId()) {
            language = Language.KA;
            setLanguage();
        }
    }

}
