package no.woact.bordan16.ticatac.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.VideoView;
import no.woact.bordan16.ticatac.R;
import no.woact.bordan16.ticatac.database.SQDatabaseHelper;
import no.woact.bordan16.ticatac.fragments.OptionsFragment;
import no.woact.bordan16.ticatac.mediaPlayers.SoundPlayer;
import no.woact.bordan16.ticatac.mediaPlayers.VideoPlayer;

/**
 * TicTacToe
 */


public class MainActivity extends AppCompatActivity {

    private FragmentManager fm;
    private VideoPlayer videoPlayer;
    private VideoView videoView;
    public static SQDatabaseHelper sqDatabaseHelper;
    public static SoundPlayer soundPlayer;


    public static boolean hasLoadedOnce;
    public static boolean twoPlayer;
    public static boolean againstCpu;
    public static boolean pokemonSelected;
    public static boolean bot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        initializeData();
        loadFragment();
    }

    /**
     * Method that initializes data.
     */
    public void initializeData(){
        videoView = (VideoView)findViewById(R.id.myVideoView);
        soundPlayer = new SoundPlayer(getApplicationContext());
        sqDatabaseHelper = new SQDatabaseHelper(this);
        videoPlayer = new VideoPlayer(videoView, this.getPackageName());
        soundPlayer.playMusic(R.raw.menu_music);
        pokemonSelected = false;
        hasLoadedOnce = true;
        twoPlayer = false;
        againstCpu = false;
        bot = false;
    }

    /**
     * Method that loads the first fragment in the main container in Main_Activity.
     * We use getSupportManager so the fragments can be supported by older android APIs
     * We check if OptionFragment is null, if it is, we start a transaction so that we can create
     * a new instance of selected fragment, in this case, OptionFragment
     */
    public void loadFragment(){
        fm = getSupportFragmentManager(); //Gets the supportFragmentManager so we can support older devices.
        OptionsFragment optionsFragment = (OptionsFragment) fm.findFragmentById(R.id.my_main_fragment);
        if (optionsFragment == null) {
            fm.beginTransaction().add(R.id.my_main_fragment, OptionsFragment.newInstance("","")).commit();
            //Checks if optionFragment is null, if it is, we will begin a transaction and load our fragment.
        }
    }

    /**
     * Method that will be called on start.
     * We set a boolean to true so that we know that the application has been started only once!
     */
    @Override
    protected void onStart() {
        super.onStart();
        hasLoadedOnce = false;
    }

    /**
     * Method that will be called onResume.
     * We call playBackgrounVideo() in this override method because we need to start
     * whenever the application has resumed.
     */
    @Override
    protected void onResume() {
        super.onResume();
        playBackgroundVideo();
    }

    /**
     * Method that will be called if the application pauses.
     * In this method we will stop the mediaPlayers if the application pauses
     * so that the music and video wont play if you have closed the game. That way we
     * can save a lot of resources.
     */
    @Override
    protected void onPause() {
        super.onPause();
        soundPlayer.HandleMenuMusic().stop();
        videoPlayer.stopVideo(); //Video stops

    }


    /**
     * Method that creates a new instance of videoplayer. Here we also use execute so that
     * we can run the videoplayer on a new thread.
     */
    public void playBackgroundVideo() {
        videoPlayer = new VideoPlayer(videoView, getPackageName());
        videoPlayer.execute(); //Video that runs
    }
}
