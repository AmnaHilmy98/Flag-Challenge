package com.android.project.chefschoice.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.project.chefschoice.DTO.ProductModel;
import com.android.project.chefschoice.R;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ProductModel> productModelArrayList;

    public CustomListAdapter(Context context, ArrayList<ProductModel> productModelArrayList) {

        this.context = context;
        this.productModelArrayList = productModelArrayList;
    }

    @Override
    public int getCount() {
        return productModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return productModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomListAdapter.ViewHolder holder;

        if (convertView == null) {
            holder = new CustomListAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null, true);

            holder.tvname = (TextView) convertView.findViewById(R.id.listItem);

            convertView.setTag(holder);
        } else {
            holder = (CustomListAdapter.ViewHolder) convertView.getTag();
        }

        Log.d("TAG", String.valueOf(productModelArrayList.get(position)));
        Log.d("TAG", productModelArrayList.get(position).getName());

        holder.tvname.setText(productModelArrayList.get(position).getName());

        return convertView;
    }

    private class ViewHolder {

        protected TextView tvname;
    }

}

