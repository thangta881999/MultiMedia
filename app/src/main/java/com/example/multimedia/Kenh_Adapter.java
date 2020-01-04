package com.example.multimedia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import java.util.List;

public class Kenh_Adapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Kenh> kenhList;

    public Kenh_Adapter(Context context, int layout, List<Kenh> kenhList) {
        this.context = context;
        this.layout = layout;
        this.kenhList = kenhList;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    public List<Kenh> getKenhList() {
        return kenhList;
    }

    public void setKenhList(List<Kenh> kenhList) {
        this.kenhList = kenhList;
    }

    @Override
    public int getCount() {
        return kenhList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(layout, null);

        // anh xa view
        ImageView imgKenh = (ImageView) convertView.findViewById(R.id.img);
        //TextView txtKenh = (TextView) convertView.findViewById(R.id.textViewKenh);

        // gan gia tri
        Kenh a = kenhList.get(position);
        //txtKenh.setText(a.getTenKenh());
        imgKenh.setImageResource(a.getHinh());

        return convertView;
    }
}
