package com.android.project.chefschoice.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.project.chefschoice.Adapters.CustomRecipeListAdapter;
import com.android.project.chefschoice.ApiUtils.NetworkUtils;
import com.android.project.chefschoice.DTO.RecipeModel;
import com.android.project.chefschoice.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class RecipeTitleActivity extends AppCompatActivity {

    private String query;
    private ArrayList<RecipeModel> recipeModelArrayList;
    private TextView txtFailed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_title);

        Intent intent = getIntent();
        query = intent.getStringExtra("QUERY");

        ListView recipeTitles = (ListView) findViewById(R.id.lstRecipeTitles);
        txtFailed = (TextView) findViewById(R.id.txtFailed);
        recipeModelArrayList = new ArrayList<>();

        new FetchRecipeTitle().execute();

    }


    private class FetchRecipeTitle extends AsyncTask<Void, Void, Void> {

        private ProgressBar progressBar;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressBar.setVisibility(View.GONE);

            ListView listView = (ListView) findViewById(R.id.lstRecipeTitles);

            if (recipeModelArrayList.size() == 0) {
                txtFailed.setText(R.string.no_results);

            } else {
                CustomRecipeListAdapter adapter = new CustomRecipeListAdapter(RecipeTitleActivity.this, recipeModelArrayList);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String url = recipeModelArrayList.get(position).getRecipeUrl();
                        Uri webpage = Uri.parse(url);
                        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        } else {
                            Log.d("RECIPE WEB", "FAILED");
                        }
                    }
                });
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            String recipesString = null;

            recipesString = NetworkUtils.getRecipeInfo(query);

            try {
                if (recipesString != null) {
                    JSONObject recipeJsonObject = new JSONObject(recipesString);
                    JSONArray recipeJsonArray = recipeJsonObject.getJSONArray("recipes");

                    for (int i = 0; i < recipeJsonArray.length(); i++) {
                        String recipeTitle = recipeJsonArray.getJSONObject(i).getString("title");
                        String recipeUrl = recipeJsonArray.getJSONObject(i).getString("source_url");
                        RecipeModel recipe = new RecipeModel(recipeTitle, recipeUrl);
                        recipeModelArrayList.add(recipe);
                    }

                } else {

                    /*
                              TODO

                                        */
                    Toast.makeText(MainActivity.getContext(), R.string.no_network, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
