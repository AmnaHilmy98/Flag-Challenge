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

public class DisplayActivity extends AppCompatActivity {

    private ListView listView;
    private Button button;
    private ProductData db = new ProductData(this);
    private CustomCheckedAdapter listAdapter;
    private boolean result = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        listView = (ListView) findViewById(R.id.lstProducts);
        button = (Button) findViewById(R.id.btnAddToKitchen);
        ArrayList<String> theSelected = new ArrayList<>();

        showProducts();

    }

    public void showProducts() {
        ProductData data = new ProductData(this);

        ArrayList<ProductModel> productModelArrayList = data.getProductList();
        if(productModelArrayList.size() == 0){
            button.setVisibility(View.INVISIBLE);
        }
        listAdapter = new CustomCheckedAdapter(this, productModelArrayList);
        listView.setAdapter(listAdapter);

    }

    public void addToKitchen(View view) {
        Log.d("Selected ", "item count: " + listAdapter.getCount());

        for (int i = 0; i < listView.getCount(); i++) {
            if (listView.isItemChecked(i)) {
                Log.d("Selected ", "onItemClick: " + i);
                listView.getItemAtPosition(i);
                result = db.setSelectedList(i + 1);
                Log.d("Selected ", "onItemClick: " + result);
            }
        }

        if (result) {
            Toast.makeText(this, "Added to Kitchen successfully!", Toast.LENGTH_LONG).show();

            this.finish();
        } else {
            Toast.makeText(this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
        }
    }

}
