package com.example.multimedia;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FragmentMusic extends Fragment {
    TextView txtTitle, txtTimeSong, txtTimeTotal;
    SeekBar skSong;
    ImageView imgHinh;
    ImageButton btnPre, btnPlay, btnStop, btnNext;
    ArrayList<Song> arraySong;
    int position=0;
    MediaPlayer mediaPlayer;
    Animation animation;
    ListView lvSong;
    SongAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.music,container,false);
        //Anh xa
        imgHinh = (ImageView) view.findViewById(R.id.imageViewDisc);
        txtTimeSong= (TextView) view.findViewById(R.id.txtTimeSong);
        txtTimeTotal =(TextView) view.findViewById(R.id.txtTimeSongTotal);
        txtTitle = (TextView) view.findViewById(R.id.textviewTitle);
        skSong = (SeekBar) view.findViewById(R.id.seekBarSong);
        btnPre = (ImageButton) view.findViewById(R.id.imgbtnPre);
        btnPlay = (ImageButton) view.findViewById(R.id.imgbtnPlay);
        btnStop = (ImageButton) view.findViewById(R.id.imgbtnStop);
        btnNext = (ImageButton) view.findViewById(R.id.imgbtnNext);
        lvSong = (ListView) view.findViewById(R.id.lvMusic);
        arraySong= new ArrayList<>();
        adapter= new SongAdapter(getActivity(),R.layout.row_music,arraySong);
        lvSong.setAdapter(adapter);
        //
        AnhXa();
        AddSong();


        animation = AnimationUtils.loadAnimation(getActivity(),R.anim.disc_rotate);
        KhoiTaoMediPlayer();
        skSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override


            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(skSong.getProgress());

            }
        });
        lvSong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                position= i;
                if (mediaPlayer.isPlaying())
                {
                    mediaPlayer.stop();
                }
                KhoiTaoMediPlayer();

                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause);
                SetTimeTotal();
                UpdateTimeSong();
            }
        });
        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position--;

                if (position <0)
                {
                    position=arraySong.size()-1;
                }
                if (mediaPlayer.isPlaying())
                {
                    mediaPlayer.stop();
                }
                KhoiTaoMediPlayer();

                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause);
                SetTimeTotal();
                UpdateTimeSong();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position++;

                if (position> arraySong.size()-1)
                {
                    position=0;
                }
                if (mediaPlayer.isPlaying())
                {
                    mediaPlayer.stop();
                }
                KhoiTaoMediPlayer();

                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause);
                SetTimeTotal();
                UpdateTimeSong();
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                btnPlay.setImageResource(R.drawable.play);
                KhoiTaoMediPlayer();
            }
        });
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying())
                {
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.play);
                }
                else
                {
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.pause);
                }
                SetTimeTotal();
                UpdateTimeSong();
                imgHinh.startAnimation(animation);
            }
        });
        return view;
    }
    //
    private  void UpdateTimeSong()
    {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss");
                txtTimeSong.setText(dinhDangGio.format(mediaPlayer.getCurrentPosition()));
                //update progress skSong
                skSong.setProgress(mediaPlayer.getCurrentPosition());
                // kiểm tra thời gian bai hát -> nếu kết thúc -> next
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position++;

                        if (position> arraySong.size()-1)
                        {
                            position=0;
                        }
                        if (mediaPlayer.isPlaying())
                        {
                            mediaPlayer.stop();
                        }
                        KhoiTaoMediPlayer();

                        mediaPlayer.start();
                        btnPlay.setImageResource(R.drawable.pause);
                        SetTimeTotal();
                        UpdateTimeSong();
                    }
                });
                handler.postDelayed(this,500);
            }
        },100);
    }
    private  void SetTimeTotal()
    {
        SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss");
        txtTimeTotal.setText(dinhDangGio.format(mediaPlayer.getDuration()) );
        // gán max của slSong= mediaPlayer.getDuration()
        skSong.setMax(mediaPlayer.getDuration());
    }
    private void KhoiTaoMediPlayer() {
//        mediaPlayer = MediaPlayer.create(MainActivity.this, arraySong.get(position).getFile());
        mediaPlayer = MediaPlayer.create(getActivity(), arraySong.get(position).getFile());
//
        txtTitle.setText(arraySong.get(position).getTitle());
    }

    private void AddSong() {
        arraySong.add(new Song("Còn gì đâu hơn chữ đã từng",R.raw.congidauhon));
        arraySong.add(new Song("Em gì ơi",R.raw.emgioi));
        arraySong.add(new Song("Em mới lên phố",R.raw.emoilenpho));
        arraySong.add(new Song("Sai lâm của anh",R.raw.sailamcuaanh));
        adapter.notifyDataSetChanged();
    }

    private void AnhXa() {

    }
}
