package com.android.project.chefschoice.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.project.chefschoice.DTO.RecipeModel;
import com.android.project.chefschoice.R;

import java.util.ArrayList;

public class CustomRecipeListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<RecipeModel> recipeModelArrayList;

    public CustomRecipeListAdapter(Context context, ArrayList<RecipeModel> recipeModelArrayList) {

        this.context = context;
        this.recipeModelArrayList = recipeModelArrayList;
    }

    @Override
    public int getCount() {
        return recipeModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return recipeModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomRecipeListAdapter.ViewHolder holder;

        if (convertView == null) {
            holder = new CustomRecipeListAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null, true);

            holder.tvname = (TextView) convertView.findViewById(R.id.listItem);

            convertView.setTag(holder);
        } else {
            holder = (CustomRecipeListAdapter.ViewHolder) convertView.getTag();
        }

        Log.d("TAG", String.valueOf(recipeModelArrayList.get(position)));
        Log.d("TAG", recipeModelArrayList.get(position).getRecipeTitle());

        holder.tvname.setText(recipeModelArrayList.get(position).getRecipeTitle());

        return convertView;
    }

    private class ViewHolder {

        protected TextView tvname;
    }

}


