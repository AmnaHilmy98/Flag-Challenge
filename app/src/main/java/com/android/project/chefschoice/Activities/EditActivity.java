package com.android.project.chefschoice.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.project.chefschoice.Adapters.CustomListAdapter;
import com.android.project.chefschoice.DB.ProductData;
import com.android.project.chefschoice.DTO.ProductModel;
import com.android.project.chefschoice.R;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<ProductModel> productModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        listView = (ListView) findViewById(R.id.lstProducts);

        ProductData data = new ProductData(this);
        productModelArrayList = data.getProductList();

        showProducts();
        editProducts();
    }

    public void showProducts() {
        CustomListAdapter listAdapter = new CustomListAdapter(this, productModelArrayList);
        listView.setAdapter(listAdapter);
    }

    private void editProducts() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                finish();
                Intent intent = new Intent(EditActivity.this, EditPageActivity.class);
                intent.putExtra("product", productModelArrayList.get(position));
                startActivity(intent);
            }
        });
    }
}
