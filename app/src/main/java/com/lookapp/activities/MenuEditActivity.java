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
public class MenuEditActivity extends CustomActivity implements View.OnClickListener {

    EditText menuName, menuNameKa, menuDescription, menuDescriptionKa, price;

    private long menuId;

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

        Bundle b = getIntent().getExtras();

        menuName.setText(b.getString("menuName"));
        menuNameKa.setText(b.getString("menuNameKa"));
        menuDescription.setText(b.getString("menuDescription"));
        menuDescriptionKa.setText(b.getString("menuDescriptionKa"));
        price.setText(b.getDouble("price") + "");
        menuId = b.getLong("menuId");

    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.add_item){
            deleteMenuItem(menuId);
        }

    }

    private void deleteMenuItem(final long id) {

        LookAppTask<Void> task = new LookAppTask<Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                LookAppService las = LookAppService.getInstance();
                try {
                    las.deleteMenuItem(id);
                } catch (LookAppException e) {
                    exception = e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if(exception == null){
                    MenuEditActivity.this.addMenuItem(app.getAdminSpotId());
                }else {
                    //TODO
                }
            }
        };
        task.execute();

    }

    private void addMenuItem(final long adminSpotId) {
        LookAppTask<Void> task = new LookAppTask<Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                LookAppService las = LookAppService.getInstance();
                try {
                    las.addMenuItem(adminSpotId, menuName.getText().toString(), menuNameKa.getText().toString(), Double.parseDouble(price.getText().toString()), menuDescription.getText().toString(), menuDescriptionKa.getText().toString());
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
