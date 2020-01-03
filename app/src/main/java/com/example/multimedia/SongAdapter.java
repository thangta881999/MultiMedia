package com.example.multimedia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SongAdapter  extends BaseAdapter {
    private Context context;
    private  int layout;
    private List<Song> songList;

    public SongAdapter(Context context, int layout, List<Song> songList) {
        this.context = context;
        this.layout = layout;
        this.songList = songList;
    }

    @Override
    public int getCount() {
        return  songList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private  class  ViewHolder{
        TextView txtTitle;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view ==null)
        {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.txtTitle= (TextView) view.findViewById(R.id.TitleMusic);

            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        Song song = songList.get(position);

        holder.txtTitle.setText(song.getTitle());
        return view;
    }
}
