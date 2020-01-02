package com.example.multimedia;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentYoutube extends Fragment {
    public static String API_KEY="AIzaSyBoYhHDJs2Mck7PF0L7JHxTyuu5qkaw9m8";
    String ID_PLAYLIST="PLCiaPngqURsM8_hJkHxTCvak9Gvoo63Tc";
    String urlGetJson="https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId="+ ID_PLAYLIST +"&key="+ API_KEY +"&maxResults=50";

    ListView lvVideo;
    ArrayList<VideoYouTube> arrayVideo;
    videoYouTubeAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.youtube,container,false);
        lvVideo = (ListView) view.findViewById(R.id.lvVideo);
        arrayVideo = new ArrayList<>();
        adapter = new videoYouTubeAdapter(getActivity(), R.layout.row_video_youtube,arrayVideo);
        lvVideo.setAdapter(adapter);
        GetJsonYouTube(urlGetJson);
        lvVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                Intent intent= new Intent(getActivity(),PlayVideoActivity.class);
                intent.putExtra("idVideoYouTube",arrayVideo.get(i).getIdVideol());
                startActivity(intent);
            }
        });
        return view;
    }
    //
    private  void GetJsonYouTube(String url)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonItems= response.getJSONArray("items");
                    String title=""; String url=""; String idVideo="";
                    for (int i=0; i< jsonItems.length();i++)
                    {
                        JSONObject jsonItem = jsonItems.getJSONObject(i);
                        JSONObject jsonSnipet = jsonItem.getJSONObject("snippet");
                        title = jsonSnipet.getString("title");
                        JSONObject jsonThumbnail = jsonSnipet.getJSONObject("thumbnails");
                        JSONObject jsonMedium = jsonThumbnail.getJSONObject("medium");
                        url= jsonMedium.getString("url");
                        JSONObject jsonResourceID = jsonSnipet.getJSONObject("resourceId");
                        idVideo = jsonResourceID.getString("videoId");
                        arrayVideo.add(new VideoYouTube(title,url,idVideo));
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Lỗi!!", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
