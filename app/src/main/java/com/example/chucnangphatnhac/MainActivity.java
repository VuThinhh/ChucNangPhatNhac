package com.example.chucnangphatnhac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView txtTime, txtTotal, txtTitle;
    SeekBar SeekbarSong;
    ImageButton btnPlay, btnStop, btnNext, btnPre;
    ArrayList<Song> arraySong;
    ImageView IMGdisk;
    int position = 0;
    MediaPlayer mediaPlayer;
    Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControl();
        addSong();
        KhoiTaoMediaPlayer();
        SetTimeTotal();
        TimeRun();
        animation = AnimationUtils.loadAnimation(this,R.anim.disk_rolate);
        mediaPlayer = MediaPlayer.create(MainActivity.this,arraySong.get(position).getFile());
        mediaPlayer.start();
        txtTitle.setText(arraySong.get(position).getTitle());
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(mediaPlayer.isPlaying())
               {
                   mediaPlayer.pause();
                   btnPlay.setImageResource(R.drawable.play70);

               }
               else {
                   mediaPlayer.start();
                   btnPlay.setImageResource(R.drawable.pause70);
               }
               TimeRun();
               SetTimeTotal();
               IMGdisk.startAnimation(animation);
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                btnPlay.setImageResource(R.drawable.play70);
                KhoiTaoMediaPlayer();
                SetTimeTotal();
                TimeRun();
                IMGdisk.startAnimation(animation);
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position++;
                if(position > arraySong.size()-1){
                    position = 0;
                }
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.stop();
                }
                KhoiTaoMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause70);
                SetTimeTotal();
                TimeRun();
                IMGdisk.startAnimation(animation);
            }
        });
        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position--;
                if(position < 0){
                    position = arraySong.size()-1;
                }
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.stop();
                }
                KhoiTaoMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause70);
                SetTimeTotal();
                TimeRun();
                IMGdisk.startAnimation(animation);
            }
        });
        SeekbarSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(SeekbarSong.getProgress());
            }
        });
    }
    private void TimeRun(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat Dinhdanggio = new SimpleDateFormat("mm:ss");
                txtTime.setText(Dinhdanggio.format(mediaPlayer.getCurrentPosition()));
                SeekbarSong.setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this,500);
                //kt thoi gian bai hat khi ket thuc -> next bai khac
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position++;
                        if(position > arraySong.size()-1){
                            position = 0;
                        }
                        if(mediaPlayer.isPlaying())
                        {
                            mediaPlayer.stop();
                        }
                        KhoiTaoMediaPlayer();
                        mediaPlayer.start();
                        btnPlay.setImageResource(R.drawable.pause70);
                        SetTimeTotal();
                        TimeRun();
                    }
                });
            }
        },100);
    }

    private void SetTimeTotal(){
        SimpleDateFormat Dinhdanggio= new SimpleDateFormat("mm:ss");
        txtTotal.setText(Dinhdanggio.format(mediaPlayer.getDuration()));
        SeekbarSong.setMax(mediaPlayer.getDuration());
    }

private void KhoiTaoMediaPlayer(){
    mediaPlayer = MediaPlayer.create(MainActivity.this,arraySong.get(position).getFile());
    txtTitle.setText(arraySong.get(position).getTitle());
}
    private void addSong() {
        arraySong = new ArrayList<>();
        arraySong.add(new Song("Tuý Âm",R.raw.mymp3));
        arraySong.add(new Song("Yêu Làm Chi",R.raw.mymp4));
        arraySong.add(new Song("Không phải dạng vừa đâu",R.raw.mymp5));
        arraySong.add(new Song("Nguoi bat tu",R.raw.mp6));
    }

    private void addControl() {
        txtTime= findViewById(R.id.txtTime);
        txtTotal=findViewById(R.id.txtTotal);
        txtTitle=findViewById(R.id.txtTitle);
        SeekbarSong=findViewById(R.id.SeekbarSong);
        btnPlay=findViewById(R.id.btnPlay);
        btnStop=findViewById(R.id.btnPause);
        btnNext=findViewById(R.id.btnNext);
        btnPre=findViewById(R.id.btnPre);
        IMGdisk = findViewById(R.id.IMGdisk);
    }


}