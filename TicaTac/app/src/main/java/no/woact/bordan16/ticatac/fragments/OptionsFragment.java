package no.woact.bordan16.ticatac.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import no.woact.bordan16.ticatac.R;
import no.woact.bordan16.ticatac.activities.MainActivity;
import no.woact.bordan16.ticatac.customAnimations.ButtonAnimations;
import no.woact.bordan16.ticatac.customAnimations.ImageAnimations;
import no.woact.bordan16.ticatac.engines.TimerEngine;
import no.woact.bordan16.ticatac.interfaces.RequestListener;

/**
 * Class that creates our OptionFragment. This is the first fragment
 * the users will see in our application.
 */
public class OptionsFragment extends Fragment implements View.OnClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ButtonAnimations buttonAnimations;
    private ImageView mntImage, logoImg;
    private Button player1Btn, player2Btn, highScoreBtn;
    private TextView textView;
    private Vibrator vibrator;
    private ImageAnimations imageAnimations;
    private ImageView myBackImage, moonImage, dragonImg;
    private boolean clicked;

    public OptionsFragment() {

    }

    public static OptionsFragment newInstance(String param1, String param2) {
        OptionsFragment fragment = new OptionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_options, container, false);
        initializeData(v);
        updateUI();
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.myBackgroundImage:
                if (clicked) {
                    imageAnimations.slideDownImage(mntImage);
                    imageAnimations.fadeOut(moonImage);
                    imageAnimations.fadeIn(dragonImg);
                    imageAnimations.slideImageUp(logoImg);
                    buttonAnimations.fadeIn(player1Btn);
                    buttonAnimations.fadeIn(player2Btn);
                    buttonAnimations.fadeIn(highScoreBtn);
                    MainActivity.soundPlayer.playSfx(R.raw.flute);
                    clicked = false;
                }
                break;
            case R.id.player1Button:
                MainActivity.twoPlayer = false;
                MainActivity.bot = true;
                vibrate();
                getFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.my_main_fragment, new PlayerFragment())
                        .addToBackStack("option")
                        .commit();
                break;

            case R.id.player2Button:
                MainActivity.twoPlayer = true;
                MainActivity.bot = false;
                vibrate();
                getFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.my_main_fragment, new PlayerFragment())
                        .addToBackStack("option")
                        .commit();
                break;

            case R.id.highScoreMainBtn:
                getFragmentManager().beginTransaction()
                        .replace(R.id.my_main_fragment, new HighScoreFragment())
                        .addToBackStack("option")
                        .commit();
                break;
        }
    }

    public void initializeData(View v){
        clicked = true;
        MainActivity.pokemonSelected = false;
        mntImage = (ImageView)v.findViewById(R.id.myMountImg);
        logoImg = (ImageView)v.findViewById(R.id.myLogo);
        myBackImage = (ImageView)v.findViewById(R.id.myBackgroundImage);
        moonImage = (ImageView)v.findViewById(R.id.red_moon);
        dragonImg = (ImageView)v.findViewById(R.id.myDragonImageView);
        player1Btn = (Button)v.findViewById(R.id.player1Button);
        player2Btn = (Button)v.findViewById(R.id.player2Button);
        highScoreBtn = (Button)v.findViewById(R.id.highScoreMainBtn);
        vibrator = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        myBackImage.setOnClickListener(this);
        player1Btn.setOnClickListener(this);
        player2Btn.setOnClickListener(this);
        highScoreBtn.setOnClickListener(this);
        buttonAnimations = new ButtonAnimations(getContext());
        imageAnimations = new ImageAnimations();
        MainActivity.twoPlayer = false;



    }

    public void updateUI() {

        if (!MainActivity.hasLoadedOnce) {
            myBackImage.setEnabled(false);
            logoImg.setVisibility(View.INVISIBLE);
            mntImage.setVisibility(View.INVISIBLE);
            moonImage.setVisibility(View.INVISIBLE);

            player1Btn.setVisibility(View.VISIBLE);
            player2Btn.setVisibility(View.VISIBLE);
            highScoreBtn.setVisibility(View.VISIBLE);
            dragonImg.setVisibility(View.VISIBLE);
        }
        MainActivity.hasLoadedOnce = false;
    }

    public void vibrate() {
        vibrator.vibrate(10);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (MainActivity.soundPlayer != null) {
            if (!MainActivity.soundPlayer.HandleMenuMusic().isPlaying()) {
                MainActivity.soundPlayer.playMusic(R.raw.menu_music);
            }
        } else {
            MainActivity.soundPlayer.playMusic(R.raw.menu_music);
        }

    }


}
