package compindia.videoplayer;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * Created by compindi on 03-11-2017.
 */

public class HomeActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener, View.OnClickListener, SurfaceHolder.Callback, MediaPlayer.OnPreparedListener, SeekBar.OnSeekBarChangeListener {
    ArrayList<ModelVideoPlayer> list;
    SurfaceView surfaceView;
    SurfaceHolder holder;
    MediaPlayer mediaPlayer;
    TextView txtHeading;
    TextView txtDescription;
    ImageView imgPlayButton;
    ImageView imgPauseButton;
    RelativeLayout controlsView;
    CountDownTimer countDownTimer;
    SeekBar seekBar;
    int previousSong;
    TextView currentProgress;
    TextView totalProgress;
    ProgressBar progressBar;
    ImageView imgVideo2, imgVideo3;
    TextView txtVideo2, txtVideo3;
    LinearLayout loutProgress;
    ImageView imgLike, imgComment, imgFavorite, thumNailImage;
    boolean b = true;
    ImageView imgBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        surfaceView = (SurfaceView) findViewById(R.id.surf_video);
        txtHeading = (TextView) findViewById(R.id.txt_video_heading);
        txtDescription = (TextView) findViewById(R.id.txt_video_description);
        imgPlayButton = (ImageView) findViewById(R.id.img_play_button);
        imgPauseButton = (ImageView) findViewById(R.id.img_pause_button);
        controlsView = (RelativeLayout) findViewById(R.id.controls_view);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        currentProgress = (TextView) findViewById(R.id.progress);
        totalProgress = (TextView) findViewById(R.id.total_progress);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        imgVideo2 = (ImageView) findViewById(R.id.img_video2);
        imgVideo3 = (ImageView) findViewById(R.id.img_video3);
        txtVideo2 = (TextView) findViewById(R.id.txt_video2);
        txtVideo3 = (TextView) findViewById(R.id.txt_video3);
        loutProgress = (LinearLayout) findViewById(R.id.lout_progress);
        imgLike = (ImageView) findViewById(R.id.img_like);
        imgComment = (ImageView) findViewById(R.id.img_comment);
        imgFavorite = (ImageView) findViewById(R.id.img_favorite);
        thumNailImage = (ImageView) findViewById(R.id.thumb_nail_image);
        imgBack= (ImageView) findViewById(R.id.img_back);
        imgVideo2.setOnClickListener(this);
        imgVideo3.setOnClickListener(this);
        surfaceView.setOnClickListener(this);
        imgPlayButton.setOnClickListener(this);
        imgPauseButton.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);
        imgLike.setOnClickListener(this);
        imgComment.setOnClickListener(this);
        imgFavorite.setOnClickListener(this);
        holder = surfaceView.getHolder();
        holder.addCallback(this);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        imgBack.setOnClickListener(this);
        setValues();
        previousSong = 0;
        playSong(previousSong);
        thumNailImage.setBackgroundResource(list.get(0).getVideoImage());
    }

    void setValues() {
        list = new ArrayList<>();
        list.add(new ModelVideoPlayer(R.drawable.image0, R.string.video1_heading, R.string.video1_description, R.string.video1_path));
        list.add(new ModelVideoPlayer(R.drawable.video1_image, R.string.video2_heading, R.string.video2_description, R.string.video2_path));
        list.add(new ModelVideoPlayer(R.drawable.img, R.string.video3_heading, R.string.video3_description, R.string.video3_path));
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mediaPlayer.setDisplay(surfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
        updateProgress();
        progressBar.setVisibility(View.GONE);
        loutProgress.setVisibility(View.GONE);
        thumNailImage.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.surf_video:
                controlsView.setVisibility(View.VISIBLE);
                loutProgress.setVisibility(View.VISIBLE);
                timer();
                break;
            case R.id.img_play_button:
                updateProgress();
                mediaPlayer.start();
                imgPauseButton.setVisibility(View.VISIBLE);
                imgPlayButton.setVisibility(View.GONE);
                thumNailImage.setVisibility(View.GONE);
                break;
            case R.id.img_pause_button:
                mediaPlayer.pause();
                imgPauseButton.setVisibility(View.GONE);
                imgPlayButton.setVisibility(View.VISIBLE);
                break;
            case R.id.img_video2:
                swipeData(1);
                playSong(0);
                break;
            case R.id.img_video3:
                swipeData(2);
                playSong(0);
                break;
            case R.id.img_like:
                if (b) {
                    imgLike.setBackgroundResource(R.drawable.likes1_pc);
                    b = false;
                } else {
                    imgLike.setBackgroundResource(R.drawable.likes_pc);
                    b = true;
                }

                break;
            case R.id.img_comment:
                if (b) {
                    imgComment.setBackgroundResource(R.drawable.comments1_pc);
                    b = false;
                } else {
                    imgComment.setBackgroundResource(R.drawable.comments_pc);
                    b = true;
                }
                break;
            case R.id.img_favorite:
                if (b) {
                    imgFavorite.setBackgroundResource(R.drawable.love1_pc);
                    b = false;
                } else {
                    imgFavorite.setBackgroundResource(R.drawable.love_pc);
                    b = true;
                }
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }

    void timer() {
        countDownTimer = null;
        countDownTimer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {

                controlsView.setVisibility(View.GONE);
                loutProgress.setVisibility(View.GONE);
            }
        }.start();
    }

    void playSong(int songNumber) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
        String path = getString(list.get(songNumber).getVideoPath());
        try {
            mediaPlayer.setDataSource(HomeActivity.this, Uri.parse(path));
            progressBar.setVisibility(View.VISIBLE);
            thumNailImage.setImageResource(list.get(songNumber).getVideoImage());
            thumNailImage.setVisibility(View.VISIBLE);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgPauseButton.setVisibility(View.VISIBLE);
        imgPlayButton.setVisibility(View.GONE);
        txtHeading.setText(list.get(songNumber).getVideoHeading());
        txtDescription.setText(list.get(songNumber).getVideoDescription());
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if (b)
            mediaPlayer.seekTo(i);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    void updateProgress() {
        countDownTimer = null;
        seekBar.setMax(mediaPlayer.getDuration());
        int totalSeconds = mediaPlayer.getDuration() / 1000;
        int minutes = totalSeconds / 60;
        final int seconds = totalSeconds % 60;
        totalProgress.setText(String.valueOf(minutes + ": " + seconds));
        seekBar.setProgress(mediaPlayer.getCurrentPosition());

        countDownTimer = new CountDownTimer(mediaPlayer.getDuration(), 1000) {

            @Override
            public void onTick(long l)
            {
              long minute= TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.getCurrentPosition());
              long second=TimeUnit.MILLISECONDS.toSeconds(mediaPlayer.getCurrentPosition());
              second=second%60;
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                currentProgress.setText(String.valueOf(minute+":"+second));
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    private void swipeData(int place) {

        if (place == 1) {
            txtVideo2.setText(list.get(0).getVideoHeading());
            imgVideo2.setBackgroundResource(list.get(0).getVideoImage());
        }
        if (place == 2) {
            txtVideo3.setText(list.get(0).getVideoHeading());
            imgVideo3.setBackgroundResource(list.get(0).getVideoImage());
        }
        Collections.swap(list, place, 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.pause();
        mediaPlayer.stop();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        imgPlayButton.setVisibility(View.VISIBLE);
        imgPauseButton.setVisibility(View.GONE);
        thumNailImage.setVisibility(View.VISIBLE);
    }
}
