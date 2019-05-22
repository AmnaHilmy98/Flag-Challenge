package com.android.project.chefschoice.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.project.chefschoice.Adapters.CustomCheckedAdapter;
import com.android.project.chefschoice.DB.ProductData;
import com.android.project.chefschoice.DTO.ProductModel;
import com.android.project.chefschoice.R;

import java.util.ArrayList;

public class AvailabilityActivity extends AppCompatActivity {

    private ListView listView;
    private Button button;
    private ProductData db = new ProductData(this);
    private CustomCheckedAdapter listAdapter;
    private boolean result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability);

        listView = (ListView) findViewById(R.id.lstAvailable);
        button = (Button) findViewById(R.id.btnSave);

        showSelected();
    }

    public void showSelected() {
        ProductData data = new ProductData(this);
        ArrayList<ProductModel> productModelArrayList = data.getSelectedList();
        Log.d("AVAILABLE", productModelArrayList.toString());

        listAdapter = new CustomCheckedAdapter(this, productModelArrayList);
        listView.setAdapter(listAdapter);
        if(productModelArrayList.size() == 0){
            button.setVisibility(View.INVISIBLE);
        }
        Log.d("Selected ", "item count: " + listAdapter.getCount());
        for (int i = 0; i < listView.getCount(); i++) {
            listView.setItemChecked(i, true);
        }
    }

    public void saveAvailable(View view) {
        Log.d("Selected ", "item count: " + listAdapter.getCount());

        for (int i = 0; i < listView.getCount(); i++) {
            if (!listView.isItemChecked(i)) {
                result = db.setUnavailableList(i + 1);
            }
        }

        if (result) {
            Toast.makeText(this, "Updated availability successfully!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
        }
        this.finish();
    }
}
