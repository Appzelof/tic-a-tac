package bordan16.woact.no.ticatac.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v4.app.Fragment;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

import bordan16.woact.no.ticatac.R;
import bordan16.woact.no.ticatac.activities.MainActivity;
import bordan16.woact.no.ticatac.alerts.CustomAlertController;
import bordan16.woact.no.ticatac.customAnimations.ButtonAnimations;
import bordan16.woact.no.ticatac.engines.GameEngine;
import bordan16.woact.no.ticatac.interfaces.GameWin;
import bordan16.woact.no.ticatac.mediaPlayers.SoundPlayer;
import bordan16.woact.no.ticatac.model.Users;
import bordan16.woact.no.ticatac.preferences.SaveStates;


public class GameFragment extends Fragment implements GameWin, View.OnClickListener {

    private static final String PLAYER = "player";
    private static final String ARG_PARAM2 = "param2";


    private Boolean player;
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    private ImageButton replayBtn, settingsBtn, highScoreBtn;
    private ButtonAnimations buttonAnimations;
    private SaveStates states;
    private SoundPlayer soundPlayer;
    private Vibrator vibrator;
    private HashMap<Integer, Button> buttonHashMap;
    private GameEngine gameEngine;
    private ImageView buddahImg, boardImg, backgroundImage;
    private OnFragmentInteractionListener mListener;
    private TextView playerOne, playerTwo, playerOneScore, playerTwoScore;
    private HashMap<Integer, Integer> playerOneCombination;
    private HashMap<Integer, Integer> playerTwoCombination;
    private CustomAlertController customAlertController;
    private int playerOneCurrentScore = 0;
    private int playerTwoCurrentScore = 0;

    public GameFragment() {

    }

    public static GameFragment newInstance(boolean player) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putBoolean(PLAYER, player);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            player = getArguments().getBoolean(PLAYER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_game, container, false);
        initializeData(v);
        updateUI();
        getDataFromSaveStates();

        clickHandler();

        return v;
    }


    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onGameFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
    public void gameWin(Integer[] integerArr) {

        for (Integer i : integerArr) {
            if (MainActivity.twoPlayer) {
                buttonHashMap.get(i).setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            } else {
                buttonHashMap.get(i).setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
            }

        }

        vibrate(400);
        soundPlayer.playSfx(R.raw.slash);
        cleanBoard();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.replayBtn:
                cleanBoard();
                restartGame();
                break;
            case R.id.highScoreBtn:
                onButtonPressed();
                break;
        }

    }

    public interface OnFragmentInteractionListener {
        void onGameFragmentInteraction();
    }

    private void initializeData(View v) {
        btn1 = (Button) v.findViewById(R.id.button1);
        btn2 = (Button) v.findViewById(R.id.button2);
        btn3 = (Button) v.findViewById(R.id.button3);
        btn4 = (Button) v.findViewById(R.id.button4);
        btn5 = (Button) v.findViewById(R.id.button5);
        btn6 = (Button) v.findViewById(R.id.button6);
        btn7 = (Button) v.findViewById(R.id.button7);
        btn8 = (Button) v.findViewById(R.id.button8);
        btn9 = (Button) v.findViewById(R.id.button9);

        playerOne = (TextView) v.findViewById(R.id.playerOne);
        playerTwo = (TextView) v.findViewById(R.id.playerTwo);
        playerOneScore = (TextView) v.findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) v.findViewById(R.id.playerTwoScore);

        settingsBtn = (ImageButton) v.findViewById(R.id.settingsBtn);
        replayBtn = (ImageButton) v.findViewById(R.id.replayBtn);
        highScoreBtn = (ImageButton) v.findViewById(R.id.highScoreBtn);
        settingsBtn.setOnClickListener(this);
        replayBtn.setOnClickListener(this);
        highScoreBtn.setOnClickListener(this);
        buttonAnimations = new ButtonAnimations(getContext());
        customAlertController = new CustomAlertController(getContext());
        states = new SaveStates(getContext());

        soundPlayer = new SoundPlayer(getContext());
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        buttonHashMap = new HashMap<>();
        gameEngine = new GameEngine(this);
        buddahImg = (ImageView) v.findViewById(R.id.buddahImg);
        boardImg = (ImageView) v.findViewById(R.id.boardImage);
        backgroundImage = (ImageView)v.findViewById(R.id.myBackground);


        playerOneCombination = new HashMap<>();
        playerTwoCombination = new HashMap<>();

        buttonHashMap.put(1, btn1);
        buttonHashMap.put(2, btn2);
        buttonHashMap.put(3, btn3);
        buttonHashMap.put(4, btn4);
        buttonHashMap.put(5, btn5);
        buttonHashMap.put(6, btn6);
        buttonHashMap.put(7, btn7);
        buttonHashMap.put(8, btn8);
        buttonHashMap.put(9, btn9);

    }

    private void updateUI() {
        Picasso.with(getContext()).load(R.drawable.buddha).into(buddahImg);
        Picasso.with(getContext()).load(R.drawable.tic_regular_theme_orange);
        for (int i = 1; i < buttonHashMap.size() + 1; i++) {
            buttonHashMap.get(i).setTag(i);
        }

        customAlertController.getAlertDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                cleanBoard();
                restartGame();
            }
        });
    }


    public void clickHandler(){

            for (int i = 1; i < buttonHashMap.size() + 1; i++){
            final int tag = Integer.parseInt(buttonHashMap.get(i).getTag().toString());
            final int finalI = i;

            buttonHashMap.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vibrate(8);
                    buttonAnimations.bounceAnim(buttonHashMap.get(finalI));
                    buttonAnimations.bounceAnim(buttonHashMap.get(finalI));
                    if (!MainActivity.twoPlayer) {
                        soundPlayer.playSfx(R.raw.sword);
                        buttonHashMap.get(finalI).setBackgroundResource(states.loadInt("imageOne"));
                        playerOneCombination.put(tag, tag);
                        buttonHashMap.get(finalI).setEnabled(false);
                        MainActivity.twoPlayer = true;


                    } else if (MainActivity.twoPlayer) {
                        soundPlayer.playSfx(R.raw.sword_two);
                        if (MainActivity.bot){
                            buttonHashMap.get(finalI).setBackgroundResource(R.drawable.o);
                        } else {
                            buttonHashMap.get(finalI).setBackgroundResource(states.loadInt("imageTwo"));
                        }
                        buttonHashMap.get(finalI).setEnabled(false);
                        playerTwoCombination.put(tag, tag);
                        MainActivity.twoPlayer = false;
                    }

                    if (!MainActivity.twoPlayer) {
                        gameEngine.gameWon(playerOneCombination);
                        scoreLogic();


                    } else {
                        gameEngine.gameWon(playerTwoCombination);
                        scoreLogic();

                    }
                }
            });
        }
    }


    private void cleanBoard() {

        for (int i = 1; i < buttonHashMap.size() + 1; i++){
            buttonHashMap.get(i).setEnabled(false);
            buttonHashMap.get(i).destroyDrawingCache();
            buttonHashMap.get(i).clearFocus();
            buttonHashMap.get(i).clearAnimation();
            buttonHashMap.get(i).getBackground().clearColorFilter();
            buttonHashMap.get(i).getResources().flushLayoutCache();

        }

    }

    private void restartGame() {

        for (int i = 1; i < buttonHashMap.size() + 1; i++){
            buttonHashMap.get(i).setEnabled(true);
            buttonHashMap.get(i).setBackgroundColor(Color.TRANSPARENT);
            buttonHashMap.get(i).setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        }

        playerOneCombination.clear();
        playerTwoCombination.clear();

    }



    public void vibrate(int duration){
        vibrator.vibrate(duration);
        System.out.println("vib");
    }

    public void scoreLogic() {
        if (gameEngine.gameWon(playerOneCombination).equals(true)){
            playerOneCurrentScore = playerOneCurrentScore + 1;
            playerOneScore.setText(String.valueOf(playerOneCurrentScore));
            customAlertController.showAlertDialog(playerOne.getText().toString());

        }

        if (gameEngine.gameWon(playerTwoCombination).equals(true)){
            playerTwoCurrentScore = playerTwoCurrentScore + 1;
            playerTwoScore.setText(String.valueOf(playerTwoCurrentScore));
            customAlertController.showAlertDialog(playerTwo.getText().toString());
        }
    }

    public void getDataFromSaveStates(){
        playerOne.setText(states.load("playerOneName"));
        if (!MainActivity.twoPlayer){
            playerTwo.setText("ASIAN BOT");
        } else {
            playerTwo.setText(states.load("playerTwoName"));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        soundPlayer.playBackgroundMusic();
    }

    @Override
    public void onPause() {
        super.onPause();
        soundPlayer.stopBackgroundMusic();
    }

}
