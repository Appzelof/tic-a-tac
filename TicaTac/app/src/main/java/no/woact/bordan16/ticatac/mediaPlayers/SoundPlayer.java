package no.woact.bordan16.ticatac.mediaPlayers;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by daniel on 04/03/2018.
 */

/**
 * Class that handle the soundPlayers.
 */
public class SoundPlayer {

    private MediaPlayer menuMusicPlayer, sfxMediaPlayer;
    private Context context;

    public SoundPlayer(Context context) {
        this.context = context;
    }

    public void playMusic(int raw){
        menuMusicPlayer = MediaPlayer.create(context, raw);
        menuMusicPlayer.start();
        menuMusicPlayer.setLooping(true);
        menuMusicPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                menuMusicPlayer.release();
            }
        });

    }

    public MediaPlayer HandleMenuMusic(){
        return menuMusicPlayer;
    }

    public MediaPlayer playSfx(int raw){
        sfxMediaPlayer = MediaPlayer.create(context, raw);
        sfxMediaPlayer.start();
        sfxMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        return sfxMediaPlayer;
    }



}
