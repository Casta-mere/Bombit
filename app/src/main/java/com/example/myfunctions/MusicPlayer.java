package com.example.myfunctions;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;

public class MusicPlayer {
    private static MusicPlayer instance;
    private MediaPlayer mediaPlayer;
    private static int lastPlayed = -1;

    public MusicPlayer() {
        mediaPlayer = new MediaPlayer();
    }

    public static MusicPlayer getInstance() {
        if (instance == null) {
            instance = new MusicPlayer();
        }
        return instance;
    }

    // 播放音乐
    public void play(Context context, int musicResId) {
        try {
            // 判断是否有音乐正在播放
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.reset();

            }
            // 设置并播放音乐
            AssetFileDescriptor afd = context.getResources().openRawResourceFd(musicResId);
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mediaPlayer.prepare();
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
            lastPlayed = musicResId;
            System.out.println(lastPlayed);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 继续播放音乐
    public void resume() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+1);
            mediaPlayer.start();
        }
    }

    public void stop(){
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    // 暂停音乐
    public void pause() {
        mediaPlayer.pause();
    }

    // 释放资源
    public void release() {
        mediaPlayer.release();
    }

    // 获取上次播放的音乐资源 id
    public int getLastPlayed() {
        return lastPlayed;
    }
}
