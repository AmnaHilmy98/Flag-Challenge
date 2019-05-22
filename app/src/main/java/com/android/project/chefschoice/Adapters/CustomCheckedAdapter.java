package com.android.project.chefschoice.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import com.android.project.chefschoice.DTO.ProductModel;
import com.android.project.chefschoice.R;

import java.util.ArrayList;

public class CustomCheckedAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ProductModel> productModelArrayList;

    public CustomCheckedAdapter(Context context, ArrayList<ProductModel> productModelArrayList) {

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
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.checked_list_item, null, true);

            holder.tvname = (CheckedTextView) convertView.findViewById(R.id.checkedListItem);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Log.d("TAG", String.valueOf(productModelArrayList.get(position)));
        Log.d("TAG", productModelArrayList.get(position).getName());

        holder.tvname.setText(productModelArrayList.get(position).getName());

        return convertView;
    }

    private class ViewHolder {

        protected CheckedTextView tvname;
    }

}
