package bordan16.woact.no.ticatac.activities;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

import java.util.ArrayList;

import bordan16.woact.no.ticatac.R;
import bordan16.woact.no.ticatac.fragments.GameFragment;
import bordan16.woact.no.ticatac.fragments.HighScoreFragment;
import bordan16.woact.no.ticatac.fragments.OptionsFragment;
import bordan16.woact.no.ticatac.fragments.PlayerFragment;
import bordan16.woact.no.ticatac.mediaPlayers.SoundPlayer;
import bordan16.woact.no.ticatac.mediaPlayers.VideoPlayer;
import bordan16.woact.no.ticatac.model.Users;


public class MainActivity extends AppCompatActivity implements GameFragment.OnFragmentInteractionListener, OptionsFragment.OnOptionFragmentInteractionListener, HighScoreFragment.OnHighScoreFragmentInteractionListener, PlayerFragment.OnPlayerFragmentInteractionListener{

    private FragmentManager fm;
    private SoundPlayer soundPlayer;
    private VideoPlayer videoPlayer;
    private VideoView videoView;

    public static ArrayList<Users> usersArrayList;
    public static boolean hasLoadedOnce;
    public static boolean twoPlayer;
    public static boolean againstCpu;
    public static boolean bot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeData();
        loadFirstFragment();
        usersArrayList = new ArrayList<>();

    }

    public void initializeData(){
        videoView = (VideoView)findViewById(R.id.myVideoView);
        soundPlayer = new SoundPlayer(getApplicationContext());
        videoPlayer = new VideoPlayer(videoView, getPackageName());
        hasLoadedOnce = true;
        twoPlayer = false;
        againstCpu = false;
        bot = false;

    }

    public void loadFirstFragment(){
        fm = getSupportFragmentManager();
        OptionsFragment optionsFragment = (OptionsFragment) fm.findFragmentById(R.id.my_main_fragment);

        if (optionsFragment == null) {
            fm.beginTransaction().add(R.id.my_main_fragment, OptionsFragment.newInstance("","")).commit();

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        hasLoadedOnce = false;


    }

    @Override
    protected void onResume() {
        super.onResume();
        playBackgroundVideo();

    }

    @Override
    protected void onStop() {
        super.onStop();
        videoPlayer.stopVideo();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoPlayer.stopVideo();

    }


    public void playBackgroundVideo() {
        videoPlayer = new VideoPlayer(videoView, getPackageName());
        videoPlayer.execute();

    }

    @Override
    public void onOptionFragmentInteraction() {
        videoPlayer.stopVideo();

    }

    @Override
    public void onGameFragmentInteraction() {

    }


    @Override
    public void onPlayerFragmentInteraction() {


    }


    @Override
    public void onHighScoreFragmentInteraction() {

    }
}
