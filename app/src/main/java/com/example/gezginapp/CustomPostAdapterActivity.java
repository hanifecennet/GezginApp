package com.example.gezginapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomPostAdapterActivity extends BaseAdapter {
    LayoutInflater layoutInflater;
    List<ListViewModelClass> listArray;
    public CustomPostAdapterActivity(LayoutInflater layoutInflater, List<ListViewModelClass> list){
       this.layoutInflater = layoutInflater;
       this.listArray = list;
    }

    @Override
    public int getCount() {

        return listArray.size();
    }

    @Override
    public Object getItem(int position) {
        return listArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = layoutInflater.inflate(R.layout.listview_satir_tasarimi,null);
        ImageView  img_view = view.findViewById(R.id.post_picture);
        TextView city_name = view.findViewById(R.id.post_title);
        TextView city_description = view.findViewById(R.id.post_description);
        ListViewModelClass modelClass = listArray.get(position);
        img_view.setImageResource(modelClass.getCity_picture());
        city_name.setText(modelClass.getCity_name());
        city_description.setText(modelClass.getCity_description());
        return view;


    }
}
