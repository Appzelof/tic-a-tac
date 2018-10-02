package bordan16.woact.no.ticatac.mediaPlayers;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.Console;
import java.io.IOException;
import java.net.ContentHandler;
import java.net.URI;
import java.util.ArrayList;

import bordan16.woact.no.ticatac.R;

/**
 * Created by daniel on 04/03/2018.
 */

public class SoundPlayer {

    private MediaPlayer backGroundMusicPlayer, menuMusicPlayer, sfxMediaPlayer;
    private Context context;

    public SoundPlayer(Context context) {
        this.context = context;
    }

    public void playMenuMusic(){
        menuMusicPlayer = MediaPlayer.create(context, R.raw.menu_music);
        menuMusicPlayer.start();
        menuMusicPlayer.setLooping(true);
    }

    public void playBackgroundMusic(){
        backGroundMusicPlayer = MediaPlayer.create(context, R.raw.background_music);
        backGroundMusicPlayer.start();
    }

    public void stopMenuMusic(){
        menuMusicPlayer.stop();
    }

    public void stopBackgroundMusic(){
        backGroundMusicPlayer.stop();
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
