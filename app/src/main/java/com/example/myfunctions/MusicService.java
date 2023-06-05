package com.example.myfunctions;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.example.qbomb.R;

import java.io.IOException;

public class MusicService extends Service {

    private MediaPlayer mediaPlayer = new MediaPlayer();
    private final IBinder binder = new MusicBinder();
    private static int playing = -1;
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void play(Context context, int musicResId,Boolean isLooping){
        try {
            if(mediaPlayer.isPlaying()){
                stop();
            }
//            if(playing == musicResId){
//                play();
//                return;
//            }
            AssetFileDescriptor afd = context.getResources().openRawResourceFd(musicResId);
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mediaPlayer.prepare();
            if(isLooping)
                mediaPlayer.setLooping(true);
            mediaPlayer.start();
            playing = musicResId;
            System.out.println("playing "+playing);

        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public void play(){
        mediaPlayer.start();
    }

    public void pause(){
        mediaPlayer.pause();
    }
    public void resume(){
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+1);
            mediaPlayer.start();
        }
    }
    public void stop(){
        mediaPlayer.stop();
        mediaPlayer.reset();
    }
    public void release(){
        mediaPlayer.release();
    }
    @Override
    public void onDestroy() {
        mediaPlayer.release();
        super.onDestroy();
    }


    public void setBomb(Context context){
        try{
            MediaPlayer setBombPlayer=new MediaPlayer();

            AssetFileDescriptor setBomb = context.getResources().openRawResourceFd(R.raw.setbomb);

            setBombPlayer.setDataSource(setBomb.getFileDescriptor(), setBomb.getStartOffset(), setBomb.getLength());

            setBombPlayer.setLooping(false);

            setBombPlayer.prepare();

            setBombPlayer.start();

            setBombPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    setBombPlayer.release();
                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void Bomb(Context context){
        try{
            MediaPlayer BombPlayer=new MediaPlayer();

            AssetFileDescriptor Bomb = context.getResources().openRawResourceFd(R.raw.bomb);

            BombPlayer.setDataSource(Bomb.getFileDescriptor(), Bomb.getStartOffset(), Bomb.getLength());

            BombPlayer.setLooping(false);

            BombPlayer.prepare();

           BombPlayer.start();

            BombPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    BombPlayer.release();
                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
