package com.android.project.chefschoice.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.project.chefschoice.Adapters.CustomListAdapter;
import com.android.project.chefschoice.DB.ProductData;
import com.android.project.chefschoice.DTO.ProductModel;
import com.android.project.chefschoice.R;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private ListView listView;
    private TextView txtQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        listView = (ListView) findViewById(R.id.lstSearch);
        txtQuery = (TextView) findViewById(R.id.txtQuery);

    }

    public void searchIngredients(View view) {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        ArrayList<ProductModel> searchResultList = new ArrayList<>();
        ProductData data = new ProductData(this);
        String query = txtQuery.getText().toString().toLowerCase();
        ArrayList<ProductModel> productModelArrayList = data.getProductList();

        for (ProductModel product : productModelArrayList) {

            if (product.getName().toLowerCase().contains(query) ||
                    product.getDescription().toLowerCase().contains(query)) {
                searchResultList.add(product);

                Log.d("RESULT", "TRUE");
            }
        }

        if (searchResultList.size() == 0) {
            Toast.makeText(this, "No results found :(.", Toast.LENGTH_LONG).show();
        }

        Log.d("SEARCH", productModelArrayList.toString());
        CustomListAdapter listAdapter = new CustomListAdapter(this, searchResultList);
        listView.setAdapter(listAdapter);
    }
}
