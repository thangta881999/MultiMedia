package com.example.multimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class PlayVideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
YouTubePlayerView  youTubePlayerView;
    String id = "";
    int REQUEST_VIDEO =123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        youTubePlayerView=(YouTubePlayerView) findViewById(R.id.myYoutube);
        Intent intent = getIntent();
        id=intent.getStringExtra("idVideoYouTube");
        youTubePlayerView.initialize(FragmentYoutube.API_KEY,this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.loadVideo(id);
        youTubePlayer.setFullscreen(true);

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult youTubeInitializationResult) {
        if(youTubeInitializationResult.isUserRecoverableError())
        {
                youTubeInitializationResult.getErrorDialog(PlayVideoActivity.this,REQUEST_VIDEO);
        }
        else
        {
            Toast.makeText(this, "Eroor", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==REQUEST_VIDEO) {
            youTubePlayerView.initialize(FragmentYoutube.API_KEY,PlayVideoActivity.this);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
