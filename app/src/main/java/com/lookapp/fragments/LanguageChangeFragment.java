package com.lookapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private RadioGroup languageRadioGroup;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_language_change, container, false);
        languageRadioGroup = (RadioGroup) rootView.findViewById(R.id.language_radio_group);

        if (Settings.getLanguage() == Language.EN) {
            languageRadioGroup.check(R.id.radio_btn_en);
        } else {
            languageRadioGroup.check(R.id.radio_btn_ka);
        }
        rootView.findViewById(R.id.language_save).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.language_save) {

            Language lang = Language.EN;
            if (languageRadioGroup.getCheckedRadioButtonId() == R.id.radio_btn_ka) {
                lang = Language.KA;
            }

            if (lang == Settings.getLanguage()) {
                return;
            }

            Settings.setLanguage(lang);
            app.updateLocale();

            getActivity().finish();

            Intent intent = new Intent(getActivity(), StartupActivity.class);
            startActivity(intent);


        }
    }


}
