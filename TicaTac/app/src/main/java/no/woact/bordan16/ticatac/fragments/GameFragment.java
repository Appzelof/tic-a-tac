package no.woact.bordan16.ticatac.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v4.app.Fragment;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.HashMap;

import no.woact.bordan16.ticatac.R;
import no.woact.bordan16.ticatac.activities.MainActivity;
import no.woact.bordan16.ticatac.alerts.CustomAlertController;
import no.woact.bordan16.ticatac.customAnimations.ButtonAnimations;
import no.woact.bordan16.ticatac.customAnimations.ImageAnimations;
import no.woact.bordan16.ticatac.engines.GameEngine;
import no.woact.bordan16.ticatac.engines.TimerEngine;
import no.woact.bordan16.ticatac.interfaces.GameWin;
import no.woact.bordan16.ticatac.preferences.SaveStates;

/**
 * This fragment handles some of the game logic. This is where the users interaction calls
 * the necessary logic for win and draw.
 */
public class GameFragment extends Fragment implements GameWin, View.OnClickListener {

    private static final String PLAYER = "player";
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    private Chronometer chronometer;
    private ImageButton musicBtn;
    private ImageButton replayBtn, settingsBtn, highScoreBtn;
    private ButtonAnimations buttonAnimations;
    private ImageAnimations imageAnimations;
    private SaveStates states;
    private Vibrator vibrator;
    private HashMap<Integer, Button> buttonHashMap;
    private GameEngine gameEngine;
    private ImageView dragonImg, myBrushLine, boardImg, backgroundImage, playerOneThumbNail, playerTwoThumbNail;
    private TextView playerOne, playerTwo, playerOneScore, playerTwoScore;
    private HashMap<Integer, Integer> playerOneCombination;
    private HashMap<Integer, Integer> playerTwoCombination;
    private CustomAlertController customAlertController;
    private Boolean twoPlayer;
    private int playerOneCurrentScore;
    private int playerTwoCurrentScore;
    private TimerEngine timerEngine;
    private Boolean next;
    private Boolean musicBtnPressed = false;
    private Boolean settingsPressed = false;
    private int pWon;


    public GameFragment() {

    }

    public static GameFragment newInstance() {
        GameFragment fragment = new GameFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game, container, false);
        initializeData(v); //Initialize`s our widgets.
        updateTwoPlayerLogic(); //Updates our twoPlayer logic.
        updateUI(); //Update our UI elements, depending on the easterEgg is chosen or not.
        getDataFromSaveStates(); //Loads our necessary data from sharedPrefs.
        clickHandler(); //Handles our clicks on the gameBoard.
        return v;
    }

    /**
     * A interface that get the correct winnerCombination from the GameEngine class.
     * @param integerArr
     */
    @Override
    public void gameWin(Integer[] integerArr) {
        for (Integer i : integerArr) {
            if (next) { //if next = true : player one.
                pWon = 1;
                buttonHashMap.get(i).setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                //Player one has won. We set a colorTintList on the buttons so we can se the winnerCombination.
            } else { // if next = false : player two.
                pWon = 2;
                buttonHashMap.get(i).setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                //Player two has won. We set a colorTintList on the buttons so we can se the winnerCombination.
            }
            //Stops the timer.
            timerEngine.stopTimer();
        }

        //If the pokemon easter egg is true and one of the players has won, we play our pokemon sounds.
        if (MainActivity.pokemonSelected && pWon == 1){
            MainActivity.soundPlayer.playSfx(R.raw.charmander_cry);
        } else if (MainActivity.pokemonSelected && pWon == 2) {
            MainActivity.soundPlayer.playSfx(R.raw.bulbasaur_cry);
        }
        //By default we play the regular sound effect, even if the pokemon easter egg is false.
        MainActivity.soundPlayer.playSfx(R.raw.slash);
        //Vibrate, vibrates.
        vibrate(400);
        //Cleans the board.
        cleanBoard();
        //Method that uploads player one, or player two to our database.
        saveToDataBase(pWon);
    }

    /**
     * Method that handles some of the clicks! Here we use switch logic based on which button
     * the users have pressed
     * @param v view
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.replayBtn:
                cleanBoard(); //Cleans the board
                restartGame(); //Restarts the game
                break;

            case R.id.highScoreBtn:
                getFragmentManager().beginTransaction() //Loads high score fragment
                        .replace(R.id.my_main_fragment, new HighScoreFragment())
                        .addToBackStack("")
                        .commit();
                break;

                //Logic that handles the settings button.
            case R.id.settingsBtn:
                    if (!settingsPressed) {
                        settingsPressed = true;
                        buttonAnimations.fadeInImgBtn(musicBtn);
                        chronometer.setVisibility(View.INVISIBLE);
                        imageAnimations.fadeOut(myBrushLine);
                    } else {
                        settingsPressed = false;
                        buttonAnimations.fadeOutImgBtn(musicBtn);
                        chronometer.setVisibility(View.VISIBLE);
                        imageAnimations.fadeIn(myBrushLine);
                    }
                break;

                    //Stops the music, and hides the musicButton.
            case R.id.musicButton:
                if (!musicBtnPressed){
                    MainActivity.soundPlayer.HandleMenuMusic().stop();
                    musicBtnPressed = true;
                    buttonAnimations.fadeOutImgBtn(musicBtn);
                    imageAnimations.fadeIn(myBrushLine);
                    chronometer.setVisibility(View.VISIBLE);
                    settingsPressed = false;
                    //Starts the music, and shows the musicButton.
                } else {
                    if (MainActivity.pokemonSelected){
                        //If the easter egg is true we will play a different background theme.
                        MainActivity.soundPlayer.HandleMenuMusic().stop();
                        MainActivity.soundPlayer.playMusic(R.raw.pokemon_theme);
                    } else {
                        //Plays the regular background theme.
                        MainActivity.soundPlayer.HandleMenuMusic().stop();
                        MainActivity.soundPlayer.playMusic(R.raw.background_music);
                    }

                    musicBtnPressed = false;
                    chronometer.setVisibility(View.VISIBLE);
                    buttonAnimations.fadeOutImgBtn(musicBtn);
                    imageAnimations.fadeIn(myBrushLine);
                    settingsPressed = false;
                }
                break;
        }
    }

    private void initializeData(View v) {
        //Initializes our widgets.
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
        musicBtn = (ImageButton) v.findViewById(R.id.musicButton);

        chronometer = (Chronometer) v.findViewById(R.id.myChronometerView);

        settingsBtn.setOnClickListener(this);
        replayBtn.setOnClickListener(this);
        highScoreBtn.setOnClickListener(this);
        musicBtn.setOnClickListener(this);

        buttonAnimations = new ButtonAnimations(getContext());
        imageAnimations = new ImageAnimations();

        customAlertController = new CustomAlertController(getContext());
        states = new SaveStates(getContext());

        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        dragonImg = (ImageView) v.findViewById(R.id.myGameImageView);
        boardImg = (ImageView) v.findViewById(R.id.boardImage);
        myBrushLine = (ImageView) v.findViewById(R.id.myBrushLine);
        backgroundImage = (ImageView)v.findViewById(R.id.myBackground);
        playerOneThumbNail = (ImageView)v.findViewById(R.id.playerOneThumbNail);
        playerTwoThumbNail = (ImageView)v.findViewById(R.id.playerTwoThumbNail);

        timerEngine = new TimerEngine(chronometer);
        timerEngine.startTimer();

        playerOneCombination = new HashMap<>();
        playerTwoCombination = new HashMap<>();
        buttonHashMap = new HashMap<>();

        buttonHashMap.put(1, btn1);
        buttonHashMap.put(2, btn2);
        buttonHashMap.put(3, btn3);
        buttonHashMap.put(4, btn4);
        buttonHashMap.put(5, btn5);
        buttonHashMap.put(6, btn6);
        buttonHashMap.put(7, btn7);
        buttonHashMap.put(8, btn8);
        buttonHashMap.put(9, btn9);

        //Sets a tag on every button in buttonHashMap.
        for (int i = 1; i < buttonHashMap.size() + 1; i++) {
            buttonHashMap.get(i).setTag(i);
        }

        //Creates a new instance of the GameEngine.
        gameEngine = new GameEngine(this, buttonHashMap, playerOneCombination, playerTwoCombination);
    }
    /**
     * Method that updates the UI based on if the easter egg is true or not!
     */
    private void updateUI() {
        //Updates our UI
        if (MainActivity.pokemonSelected){
            //If easter egg is selected this logic will be called.
            dragonImg.setImageResource(R.drawable.pokeball); //Set main logo
            playerOneThumbNail.setImageResource(R.drawable.charmander); //Set player symbol for player one.
            backgroundImage.setImageResource(R.drawable.pokemon_background); //Set background image.

        } else {
            //If the easter egg is not selected this logic will be called.
            dragonImg.setImageResource(R.drawable.long_dragon); //Set main logo
            playerOneThumbNail.setImageResource(states.loadInt("imageOne")); //Set player symbol for player one.
        }

        if (MainActivity.bot){
            if (MainActivity.pokemonSelected) {
                //If bot and easterEgg is true this logic will be called.
                playerTwoThumbNail.setImageResource(R.drawable.bullbasaur);
            } else {
                //If only bot is called, this logic will be called.
                playerTwoThumbNail.setImageResource(R.drawable.o); //Set symbol for player two.
            }
        } else {
            if (MainActivity.pokemonSelected) {
                //If bot is not true, but easterEgg is, this logic will be called.
                playerTwoThumbNail.setImageResource(R.drawable.bullbasaur); //Set symbol for player two.
            } else {
                //If bot and easterEgg is false, we set a new symbol for player two.
                playerTwoThumbNail.setImageResource(states.loadInt("imageTwo"));
            }
        }
    }

    /**
     * Method that handles all the clicks in the main gameBoard. Here we will loop trough our buttonHashMap so
     * we can easily get all the button tags and put them in new hashMaps based on which player is playing.
     * That way we check if someone has won, or if the game is draw.
     */
    public void clickHandler(){
        for (int i = 1; i < buttonHashMap.size() + 1; i++) { //We iterate trough buttonHashMap so we can get all the button tags.
            final int tag = Integer.parseInt(buttonHashMap.get(i).getTag().toString()); //Parsing tag to an int.
            final int finalI = i; //Using final because we are going to access the variable in an inner class.
                buttonHashMap.get(i).setOnClickListener(new View.OnClickListener() { //Sets a new OnclickListener on every button.
                    @Override
                    public void onClick(View v) {
                        vibrate(8); //Custom vibration on every click.
                        buttonAnimations.bounceAnim(buttonHashMap.get(finalI)); //Custom animation on every click.
                        if (!next) {
                            //if next is false this logic will be called.
                            MainActivity.soundPlayer.playSfx(R.raw.sword); //Custom sound while next is false.
                            playerOneThumbNail.setAlpha(0.6f);
                            playerTwoThumbNail.setAlpha(1f);
                            if (MainActivity.pokemonSelected){
                                buttonHashMap.get(finalI).setBackgroundResource(R.drawable.charmander);
                                //if easter is chosen we will set a new symbol for player one.
                            } else {
                                //if easter egg is not chosen wi will load a new symbol.
                                buttonHashMap.get(finalI).setBackgroundResource(states.loadInt("imageOne"));
                            }
                            //We save the button tags in the hashMap depending on what button has been pressed.
                            playerOneCombination.put(tag, tag);
                            buttonHashMap.get(finalI).setEnabled(false); //Setting the button to disabled for our randomGenerator in gameEngine.
                            next = true; //Next turn.

                        } else if (next && !MainActivity.bot) { //If next is true and the bot is false.
                            playerTwoThumbNail.setAlpha(0.6f);
                            playerOneThumbNail.setAlpha(1f);
                            MainActivity.soundPlayer.playSfx(R.raw.sword_two); //Custom sound while next is true.
                            if (MainActivity.pokemonSelected) {
                                buttonHashMap.get(finalI).setBackgroundResource(R.drawable.bullbasaur);
                            } else {
                                buttonHashMap.get(finalI).setBackgroundResource(states.loadInt("imageTwo"));
                            }
                            //We save the button tags in the hashMap depending on what button has been pressed.
                            buttonHashMap.get(finalI).setEnabled(false);
                            playerTwoCombination.put(tag, tag);
                            next = false;
                        }
                        implementAI();
                        disableOnClick();
                        scoreLogic();

                    }
                });

            }
        }

    /**
     * Method that cleans the board by destroying the drawing cache, clearing the animation, and flushes the
     * layout cache for better performance.
     */
    private void cleanBoard() {
        //Looping trough buttonHashMap so we can set everything disabled!
        for (int i = 1; i < buttonHashMap.size() + 1; i++){
            buttonHashMap.get(i).setEnabled(false);
            buttonHashMap.get(i).destroyDrawingCache();
            buttonHashMap.get(i).clearFocus();
            buttonHashMap.get(i).clearAnimation();
            buttonHashMap.get(i).getBackground().clearColorFilter(); //Clear colors.
            buttonHashMap.get(i).getResources().flushLayoutCache(); //Flushing layout cache for better performance.
        }

    }

    /**
     * Method that restarts the game by setting every button enabled in our main buttonHashMap. We also
     * clear our player combinations.
     */
    private void restartGame() {
        for (int i = 1; i < buttonHashMap.size() + 1; i++){
            buttonHashMap.get(i).setEnabled(true);
            buttonHashMap.get(i).setBackgroundColor(Color.TRANSPARENT);
            if (!MainActivity.pokemonSelected) {
                buttonHashMap.get(i).setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
            } else {
                buttonHashMap.get(i).setBackgroundTintList(null);
            }
            implementAI();
        }
        playerOneCombination.clear();
        playerTwoCombination.clear();

    }

    /**
     * Method that creates a custom vibration.
     * @param duration
     */
    public void vibrate(int duration){
        vibrator.vibrate(duration);
        
    }

    public void scoreLogic() {

        if (gameEngine.gameWon(playerOneCombination).equals(true)){
            playerOneCurrentScore = playerOneCurrentScore + 1;
            playerOneScore.setText(String.valueOf(playerOneCurrentScore));
            customAlertController.showAlertDialog(playerOne.getText().toString(), "RED");
        }
        else if (gameEngine.gameWon(playerTwoCombination).equals(true)){
            playerTwoCurrentScore = playerTwoCurrentScore + 1;
            playerTwoScore.setText(String.valueOf(playerTwoCurrentScore));
            customAlertController.showAlertDialog(playerTwo.getText().toString(), "BLUE");
        }

        else if (gameEngine.gameDraw()){
            customAlertController.showAlertDialog("IT`S A TIE", "GOLD");
            timerEngine.stopTimer();
            MainActivity.soundPlayer.playSfx(R.raw.water_drop);
        }

        customAlertController.getAlertDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                cleanBoard();
                restartGame();
                implementAI();
                timerEngine.startTimer();
            }
        });

    }

    /**
     * Method that loads our saveStates.
     */
    public void getDataFromSaveStates(){
        if (MainActivity.pokemonSelected){
            playerOne.setText("CHARM");
        } else {
            playerOne.setText(states.load("playerOneName"));
        }
        if (!MainActivity.twoPlayer){
            if (MainActivity.pokemonSelected){
                playerTwo.setText("BULBA");
            } else {
                playerTwo.setText("TTTBOT");
            }
        } else {
            if (MainActivity.pokemonSelected) {
                playerTwo.setText("BULBA");
            } else {
                playerTwo.setText(states.load("playerTwoName"));
            }
        }
    }

    /**
     * Method that updates the twoPlayer logic.
     */
    private void updateTwoPlayerLogic(){
        if (MainActivity.twoPlayer){ //If two player is true we will set our booleans to true.
            twoPlayer = true;
            next = true;
        } else {
            //If not we will set them to false.
            next = false;
            twoPlayer = false;
        }
        //The next boolean determines player turns.
        if (next){
            playerTwoThumbNail.setAlpha(1f);
            playerOneThumbNail.setAlpha(0.6f);
        } else {
            playerOneThumbNail.setAlpha(1f);
            playerTwoThumbNail.setAlpha(0.6f);
        }
    }

    /**
     * Method that resumes playback!
     */
    @Override
    public void onResume() {
        super.onResume();
        getDataFromSaveStates();
        if (!MainActivity.pokemonSelected && !MainActivity.soundPlayer.HandleMenuMusic().isPlaying()){
            MainActivity.soundPlayer.playMusic(R.raw.background_music);
        } else {
            if (!MainActivity.soundPlayer.HandleMenuMusic().isPlaying()) {
                MainActivity.soundPlayer.playMusic(R.raw.pokemon_theme);
            }
        }
    }

    /**
     * Method that stops music in the onStop method.
     */
    @Override
    public void onPause() {
        super.onPause();
        MainActivity.soundPlayer.HandleMenuMusic().stop();
    }

    /**
     * Method that calls the AI logic. We are using a handler with a new runnable so we can
     * get a delayed execution.
     */
    public void implementAI() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getAiLogic();
                clickHandler();
            }
        }, 400);
    }

    /**
     * Method that implements our randomGenerator and runs the AI logic.
     */
    public void getAiLogic() {
        if (MainActivity.bot && next) {
            //The gameEngine.gameLogicAI returns a random number we can use to declare an int variable.
            int boardNumber = gameEngine.gameLogicAI();
            if (boardNumber != 0) {
                //So if the boardNumber variable not is equal to 0, which holds the random number we can call our logic.
                MainActivity.soundPlayer.playSfx(R.raw.sword_two);
                if (MainActivity.pokemonSelected) { //EasterEgg selected
                    //We use our boardNumber (randomNumber) to get the correct button and we define a backgroundResource.
                    buttonHashMap.get(boardNumber).setBackgroundResource(R.drawable.bullbasaur);
                } else {
                    buttonHashMap.get(boardNumber).setBackgroundResource(R.drawable.o);
                }
                playerTwoThumbNail.setAlpha(0.6f); //Showing the correct player turn.
                playerOneThumbNail.setAlpha(1f); // Showing the correct player turn.
                //By using alphas we can show the players who is next.
                buttonAnimations.bounceAnim(buttonHashMap.get(boardNumber)); //Animates the random button.
                buttonHashMap.get(boardNumber).setEnabled(false);
                playerTwoCombination.put(boardNumber, boardNumber);
                next = false;
                scoreLogic(); //We call the scorelogic() method here so we can show an AlertDialog.Builder if the AI wins.
            }
        }
    }

    /**
     * Method that saves player data to our database which is created in SQDataBaseHelper based on player one or player two`s
     * win ratio. The database will be updated every time someone wins.
     * @param player
     */
    public void saveToDataBase(int player) {
        int score;
        int symbol;
        String playerName;
        if (player == 1) {
            playerName = playerOne.getText().toString();
            score = playerOneCurrentScore;
            symbol = states.loadInt("imageOne");
            if (MainActivity.pokemonSelected) {
                MainActivity.sqDatabaseHelper.updateDataBase(playerName, score, R.drawable.charmander);
            } else {
                MainActivity.sqDatabaseHelper.updateDataBase(playerName, score, symbol);
            }
        } else if (player == 2) {
            System.out.println("Data Inserted");
            playerName = playerTwo.getText().toString();
            score = playerTwoCurrentScore;
            symbol = states.loadInt("imageTwo");
            if (MainActivity.pokemonSelected) {
                MainActivity.sqDatabaseHelper.updateDataBase(playerName, score, R.drawable.bullbasaur);
            } else {
                MainActivity.sqDatabaseHelper.updateDataBase(playerName, score, symbol);
            }
        }

    }

    /**
     * This method disables the clickListener. The method has to be put in use when the AI is making a decision. That way
     * we can avoid multiple presses by player one when the AI is processing.
     */
    public void disableOnClick() {
        if (MainActivity.bot) {
            for (int i = 1; i < buttonHashMap.size() + 1; i++) {
                if (MainActivity.bot && next) {
                    buttonHashMap.get(i).setOnClickListener(null);
                }
            }
        }
    }
}
