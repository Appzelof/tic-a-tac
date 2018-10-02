package bordan16.woact.no.ticatac.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import bordan16.woact.no.ticatac.R;
import bordan16.woact.no.ticatac.activities.MainActivity;
import bordan16.woact.no.ticatac.customAnimations.ButtonAnimations;
import bordan16.woact.no.ticatac.customAnimations.ImageAnimations;
import bordan16.woact.no.ticatac.mediaPlayers.SoundPlayer;
import bordan16.woact.no.ticatac.mediaPlayers.VideoPlayer;


public class OptionsFragment extends Fragment implements View.OnClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ButtonAnimations buttonAnimations;
    private ImageView mntImage, logoImg;
    private Button player1Btn, player2Btn, highScoreBtn;
    private SoundPlayer soundPlayer;
    private Vibrator vibrator;

    private String mParam1;
    private String mParam2;
    private ImageAnimations imageAnimations;
    private ImageView myBackImage, moonImage;
    private boolean clicked;

    private OnOptionFragmentInteractionListener mListener;

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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_options, container, false);
        initializeData(v);
        updateUI();


        return v;
    }


    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onOptionFragmentInteraction();

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnOptionFragmentInteractionListener) {
            mListener = (OnOptionFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.myBackgroundImage:

                if (clicked) {
                    imageAnimations.slideDownImage(mntImage);
                    imageAnimations.fadeOut(moonImage);
                    imageAnimations.slideImageUp(logoImg);
                    buttonAnimations.fadeIn(player1Btn);
                    buttonAnimations.fadeIn(player2Btn);
                    buttonAnimations.fadeIn(highScoreBtn);
                    soundPlayer.playSfx(R.raw.flute);
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
        soundPlayer = new SoundPlayer(getContext());
        mntImage = (ImageView)v.findViewById(R.id.myMountImg);
        logoImg = (ImageView)v.findViewById(R.id.myLogo);
        myBackImage = (ImageView)v.findViewById(R.id.myBackgroundImage);
        moonImage = (ImageView)v.findViewById(R.id.red_moon);

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
        }

        MainActivity.hasLoadedOnce = false;
    }



    public interface OnOptionFragmentInteractionListener {
        void onOptionFragmentInteraction();

    }

    public void vibrate() {
        vibrator.vibrate(10);
    }

}
