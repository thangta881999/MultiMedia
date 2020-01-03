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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
public class FragmentTivi1 extends Fragment {
    TextView txtDanhSach;
    ListView lvKenh;
    ArrayList<Kenh> arrKenh;
    Kenh_Adapter adapterKenh;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.play_tivi1, container, false);
        //Anh xa
        AnhXa();
        lvKenh = (ListView) view.findViewById(R.id.lvKenh);
        //
        adapterKenh = new Kenh_Adapter(getActivity(), R.layout.row_kenh, arrKenh);
        lvKenh.setAdapter(adapterKenh);

        // click
        lvKenh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Toast.makeText(getActivity(),""+arrKenh.get(position).getTenKenh(), Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    private void AnhXa() {
        arrKenh = new ArrayList<>();
        arrKenh.add(new Kenh("HTV1", R.drawable.htv1));
        arrKenh.add(new Kenh("HTV2", R.drawable.htv2));
        arrKenh.add(new Kenh("HTV3", R.drawable.htv3));
        arrKenh.add(new Kenh("HTV7", R.drawable.htv7));
        arrKenh.add(new Kenh("HTV9", R.drawable.htv9));
    }


}
