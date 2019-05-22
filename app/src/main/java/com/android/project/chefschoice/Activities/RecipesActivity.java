package com.android.project.chefschoice.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.project.chefschoice.Adapters.CustomCheckedAdapter;
import com.android.project.chefschoice.DB.ProductData;
import com.android.project.chefschoice.DTO.ProductModel;
import com.android.project.chefschoice.R;

import java.util.ArrayList;

public class RecipesActivity extends AppCompatActivity {

    private ListView listView;
    private Button button;
    private CustomCheckedAdapter listAdapter;
    private ArrayList<ProductModel> productModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        listView = (ListView) findViewById(R.id.lstRecipes);
        button = (Button) findViewById(R.id.btnSearchRecipe);
        showProducts();

    }

    public void showProducts() {
        ProductData data = new ProductData(this);

        productModelArrayList = data.getProductList();
        if (productModelArrayList.size() == 0) {
            button.setVisibility(View.INVISIBLE);
        }
        listAdapter = new CustomCheckedAdapter(this, productModelArrayList);
        listView.setAdapter(listAdapter);

    }

    public void searchRecipe(View view) {
        ArrayList<String> queryArray = new ArrayList<>();

        Log.d("Selected ", "item count: " + listAdapter.getCount());
        for (int i = 0; i < listAdapter.getCount(); i++) {
            if (listView.isItemChecked(i)) {
                Log.d("Selected ", "onItemClick: " + i);
                queryArray.add(productModelArrayList.get(i).getName());
            }
        }

        StringBuilder queryBuilder = new StringBuilder();
        for (String queryItem : queryArray) {
            queryBuilder.append(queryItem).append(", ");
        }

        Log.d("QUERY STRING", queryBuilder.toString());
        String queryString = queryBuilder.toString();

        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
            if(connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()){
                Toast.makeText(this, "Connected to a WIFI network", Toast.LENGTH_LONG).show();
            }
            if(connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()){
                Toast.makeText(this, "Connected to a MOBILE network", Toast.LENGTH_LONG).show();
            }
        }

        if (networkInfo != null && networkInfo.isConnected() && queryString.length() != 0) {
            Toast.makeText(this, R.string.loading, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(RecipesActivity.this, RecipeTitleActivity.class);
            intent.putExtra("QUERY", queryString);
            startActivity(intent);
        } else {
            if (queryString.length() == 0) {
                Toast.makeText(this, R.string.no_search_term, Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, R.string.no_network, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(intent);
            }
        }
    }
}
