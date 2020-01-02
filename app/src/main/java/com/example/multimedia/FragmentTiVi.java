package com.example.multimedia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaCodec;
import android.os.Bundle;
import android.os.Handler;

import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer.DefaultLoadControl;
import com.google.android.exoplayer.ExoPlaybackException;
import com.google.android.exoplayer.ExoPlayer;
import com.google.android.exoplayer.LoadControl;
import com.google.android.exoplayer.MediaCodecAudioTrackRenderer;
import com.google.android.exoplayer.MediaCodecSelector;
import com.google.android.exoplayer.MediaCodecVideoTrackRenderer;
import com.google.android.exoplayer.TrackRenderer;
import com.google.android.exoplayer.chunk.Format;
import com.google.android.exoplayer.hls.DefaultHlsTrackSelector;
import com.google.android.exoplayer.hls.HlsChunkSource;
import com.google.android.exoplayer.hls.HlsMasterPlaylist;
import com.google.android.exoplayer.hls.HlsPlaylist;
import com.google.android.exoplayer.hls.HlsPlaylistParser;
import com.google.android.exoplayer.hls.HlsSampleSource;
import com.google.android.exoplayer.hls.PtsTimestampAdjusterProvider;
import com.google.android.exoplayer.upstream.DataSource;
import com.google.android.exoplayer.upstream.DefaultAllocator;
import com.google.android.exoplayer.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer.upstream.DefaultUriDataSource;
import com.google.android.exoplayer.util.ManifestFetcher;
import com.google.android.exoplayer.util.PlayerControl;
import com.google.android.exoplayer.util.Util;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentTiVi extends Fragment implements ManifestFetcher.ManifestCallback<HlsPlaylist>,
        ExoPlayer.Listener,HlsSampleSource.EventListener, AudioManager.OnAudioFocusChangeListener, View.OnClickListener{
    private SurfaceView surface;
    private Button btn_play,btn_pause;
    private ExoPlayer player;
    private PlayerControl playerControl;
    private String video_url;
    private Handler mainHandler;
    private AudioManager am;
    private String userAgent;
    private ManifestFetcher<HlsPlaylist> playlistFetcher;
    private static final int BUFFER_SEGMENT_SIZE = 64 * 1024;
    private static final int MAIN_BUFFER_SEGMENTS = 254;
    public static final int TYPE_VIDEO = 0;
    private TextView txt_playState;
    private TrackRenderer videoRenderer;
    private MediaCodecAudioTrackRenderer audioRenderer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.player_tivi,container,false);
        surface = (SurfaceView) view.findViewById(R.id.surface_view); // we import surface
        txt_playState = (TextView) view.findViewById(R.id.txt_playstate);
        btn_play = (Button) view.findViewById(R.id.btn_play);
        btn_pause = (Button) view.findViewById(R.id.btn_pause);
        btn_play.setOnClickListener(this);
        btn_pause.setOnClickListener(this); // we init buttons and listners
        player = ExoPlayer.Factory.newInstance(2);
        playerControl = new PlayerControl(player); // we init player
        video_url = "http://live.cdn.mobifonetv.vn/motv/myhtv7_hls.smil/chunklist_b1200000.m3u8"; //video url
        am = (AudioManager) getActivity().getApplicationContext().getSystemService(Context.AUDIO_SERVICE); // for requesting audio
        mainHandler = new Handler(); //handler required for hls
        userAgent = Util.getUserAgent(getActivity(), "FragmentTiVi"); //useragent required for hls
        HlsPlaylistParser parser = new HlsPlaylistParser(); // init HlsPlaylistParser
        playlistFetcher = new ManifestFetcher<>(video_url, new DefaultUriDataSource(getActivity(), userAgent),
                parser); // url goes here, useragent and parser
        playlistFetcher.singleLoad(mainHandler.getLooper(), this); //with 'this' we'll implement ManifestFetcher.ManifestCallback<HlsPlaylist>
        //listener with it will come two functions
        return  view;

    }
    @Override
    public void onSingleManifest(HlsPlaylist manifest) {
        LoadControl loadControl = new DefaultLoadControl(new DefaultAllocator(BUFFER_SEGMENT_SIZE));
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        PtsTimestampAdjusterProvider timestampAdjusterProvider = new PtsTimestampAdjusterProvider();
        boolean haveSubtitles = false;
        boolean haveAudios = false;
        if (manifest instanceof HlsMasterPlaylist) {
            HlsMasterPlaylist masterPlaylist = (HlsMasterPlaylist) manifest;
            haveSubtitles = !masterPlaylist.subtitles.isEmpty();

        }
        // Build the video/id3 renderers.
        DataSource dataSource = new DefaultUriDataSource(getActivity(), bandwidthMeter, userAgent);
        HlsChunkSource chunkSource = new HlsChunkSource(true /* isMaster */, dataSource, manifest,
                DefaultHlsTrackSelector.newDefaultInstance(getActivity()), bandwidthMeter,
                timestampAdjusterProvider, HlsChunkSource.ADAPTIVE_MODE_SPLICE);
        HlsSampleSource sampleSource = new HlsSampleSource(chunkSource, loadControl,
                MAIN_BUFFER_SEGMENTS * BUFFER_SEGMENT_SIZE, mainHandler, this, TYPE_VIDEO);
        MediaCodecVideoTrackRenderer videoRenderer = new MediaCodecVideoTrackRenderer(getActivity(), sampleSource,
                MediaCodecSelector.DEFAULT, MediaCodec.VIDEO_SCALING_MODE_SCALE_TO_FIT);
        MediaCodecAudioTrackRenderer audioRenderer = new MediaCodecAudioTrackRenderer(sampleSource,
                MediaCodecSelector.DEFAULT);
        this.videoRenderer = videoRenderer;
        this.audioRenderer = audioRenderer;
        pushSurface(false); // here we pushsurface
        player.prepare(videoRenderer,audioRenderer); //prepare
        player.addListener(this); //add listener for the text field
        if (requestFocus())
            player.setPlayWhenReady(true);
    }
    public boolean requestFocus() {
        return AudioManager.AUDIOFOCUS_REQUEST_GRANTED ==
                am.requestAudioFocus(FragmentTiVi.this, AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN);
    }
    private void pushSurface(boolean blockForSurfacePush) {
        if (videoRenderer == null) {return;}
        if (blockForSurfacePush) {
            player.blockingSendMessage(
                    videoRenderer, MediaCodecVideoTrackRenderer.MSG_SET_SURFACE, surface.getHolder().getSurface());
        } else {
            player.sendMessage(
                    videoRenderer, MediaCodecVideoTrackRenderer.MSG_SET_SURFACE, surface.getHolder().getSurface());
        }
    }

    @Override
    public void onSingleManifestError(IOException e) {

    }
    // I'll upload this code on drive then just extarct it and understand ok
    //lets check
    // also watch my videos with my daughter
    //thanks!!!
    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        String text = "";
        switch (playbackState) {
            case ExoPlayer.STATE_BUFFERING:
                text += "buffering";
                break;
            case ExoPlayer.STATE_ENDED:
                text += "ended";
                break;
            case ExoPlayer.STATE_IDLE:
                text += "idle";
                break;
            case ExoPlayer.STATE_PREPARING:
                text += "preparing";
                break;
            case ExoPlayer.STATE_READY:
                text += "ready";
                break;
            default:
                text += "unknown";
                break;
        }
        txt_playState.setText(text);

        //for the text feild
    }

    @Override
    public void onPlayWhenReadyCommitted() {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onLoadStarted(int sourceId, long length, int type, int trigger, Format format, long mediaStartTimeMs, long mediaEndTimeMs) {

    }

    @Override
    public void onLoadCompleted(int sourceId, long bytesLoaded, int type, int trigger, Format format, long mediaStartTimeMs, long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs) {

    }

    @Override
    public void onLoadCanceled(int sourceId, long bytesLoaded) {

    }

    @Override
    public void onLoadError(int sourceId, IOException e) {

    }

    @Override
    public void onUpstreamDiscarded(int sourceId, long mediaStartTimeMs, long mediaEndTimeMs) {

    }

    @Override
    public void onDownstreamFormatChanged(int sourceId, Format format, int trigger, long mediaTimeMs) {

    }

    @Override
    public void onAudioFocusChange(int focusChange) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_pause:
                playerControl.pause();
                break;
            case R.id.btn_play:
                playerControl.pause();
                video_url = "http://live.cdn.mobifonetv.vn/motv/myhtv9_hls.smil/chunklist_b1200000.m3u8"; //video url
                am = (AudioManager) getActivity().getApplicationContext().getSystemService(Context.AUDIO_SERVICE); // for requesting audio
                mainHandler = new Handler(); //handler required for hls
                userAgent = Util.getUserAgent(getActivity(), "MainActivity"); //useragent required for hls
                HlsPlaylistParser parser = new HlsPlaylistParser(); // init HlsPlaylistParser
                playlistFetcher = new ManifestFetcher<>(video_url, new DefaultUriDataSource(getActivity(), userAgent),
                        parser); // url goes here, useragent and parser
                playlistFetcher.singleLoad(mainHandler.getLooper(), this); //with 'this' we'll implement ManifestFetcher.ManifestCallback<HlsPlaylist>
                //listener with it will come two functions
                playerControl.start();

                break;
        }
        //for play and pause
    }
}
