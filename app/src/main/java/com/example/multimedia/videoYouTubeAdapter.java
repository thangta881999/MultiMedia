package com.example.multimedia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class videoYouTubeAdapter extends BaseAdapter {
    private Context context;
    private  int layout;
    private List<VideoYouTube> videoYouTubeList;

    public videoYouTubeAdapter(Context context, int layout, List<VideoYouTube> videoYouTubeList) {
        this.context = context;
        this.layout = layout;
        this.videoYouTubeList = videoYouTubeList;
    }

    @Override

    public int getCount() {
        return videoYouTubeList.size();
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
        ImageView imgThumbnail;
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
                holder.txtTitle= (TextView) view.findViewById(R.id.TitleVideo);
                holder.imgThumbnail=(ImageView) view.findViewById(R.id.imgviewThumbnail);
                view.setTag(holder);
       }
       else {
           holder = (ViewHolder) view.getTag();
       }
            VideoYouTube video = videoYouTubeList.get(position);

        holder.txtTitle.setText(video.getTitle());

        Picasso.with(context).load(video.getThumbnail()).into(holder.imgThumbnail);

        return view;
}
}
