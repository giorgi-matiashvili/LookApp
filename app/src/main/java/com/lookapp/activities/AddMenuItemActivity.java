package com.lookapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.lookapp.R;
import com.lookapp.api.exception.LookAppException;
import com.lookapp.support.LookAppService;
import com.lookapp.support.LookAppTask;

/**
 * Created by user on 07/07/2015.
 */
public class AddMenuItemActivity extends CustomActivity implements View.OnClickListener {


    EditText menuName, menuNameKa, menuDescription, menuDescriptionKa, price;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu_item);

        findViewById(R.id.add_item).setOnClickListener(this);

        menuName = (EditText) findViewById(R.id.name_en_et);
        menuNameKa = (EditText) findViewById(R.id.name_ka_et);
        menuDescription = (EditText) findViewById(R.id.description_en_et);
        menuDescriptionKa = (EditText) findViewById(R.id.description_ka_et);
        price = (EditText) findViewById(R.id.price_et);
        getCustomLayoutActionBar();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.add_item) {
            LookAppTask<Void> task = new LookAppTask<Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    LookAppService las = LookAppService.getInstance();
                    try {
                        las.addMenuItem(app.getAdminSpotId(), menuName.getText().toString(), menuNameKa.getText().toString(), Double.parseDouble(price.getText().toString()), menuDescription.getText().toString(), menuDescriptionKa.getText().toString());
                    } catch (LookAppException e) {
                        exception = e;
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    if(exception == null){
                        setResult(RESULT_OK);
                        finish();
                    }else {
                        //TODO
                    }
                }
            };
            task.execute();
        }
    }
}
