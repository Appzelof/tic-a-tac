package bordan16.woact.no.ticatac.mediaPlayers;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.MediaController;
import android.widget.VideoView;

import bordan16.woact.no.ticatac.R;

/**
 * Created by daniel on 20/03/2018.
 */

public class VideoPlayer extends AsyncTask<Void, Void, Void> {

    private VideoView videoView;


    public VideoPlayer(VideoView videoView, String packageName) {
        this.videoView = videoView;
        String path = "android.resource://" + packageName + "/" + R.raw.snowing;
        videoView.setVideoURI(Uri.parse(path));

    }

    @Override
    protected Void doInBackground(Void... voids) {

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        videoView.start();

        return null;
    }

    public void stopVideo(){
        videoView.stopPlayback();

    }
}








