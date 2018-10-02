package no.woact.bordan16.ticatac.fragments;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.PipedInputStream;
import java.sql.Time;
import java.util.HashMap;

import no.woact.bordan16.ticatac.R;
import no.woact.bordan16.ticatac.activities.MainActivity;
import no.woact.bordan16.ticatac.interfaces.RequestListener;
import no.woact.bordan16.ticatac.preferences.SaveStates;
import no.woact.bordan16.ticatac.requests.RequestManager;


public class PlayerFragment extends Fragment implements RequestListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Button button;
    private TextView playerOneName, playerTwoName, textLabel;
    private ImageView background, dragonImg, koiImg, pandaImg, yinImg, bruceImg;
    private ImageView playerOneImg, playerTwoImg, transparentIamge;
    private ImageView tigerImg, bamboImg, xImg, oImg;
    private HashMap<Integer, ImageView> imageViewHashMap;
    private Boolean selected;
    private Boolean next;
    private Boolean twoPlayer;
    private Boolean checked;
    private int selectedImage;
    private SaveStates states;
    private RequestManager requestManager;
    private static String text;

    public PlayerFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PlayerFragment newInstance(String param1, String param2) {
        PlayerFragment fragment = new PlayerFragment();
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
        View v = inflater.inflate(R.layout.fragment_player, container, false);
        initializeData(v);
        initHashMap();
        buttonWidgetHandler();
        updateUI();
        updateUIAfterTextIsChanged();
        buttonClickHandler();
        return v;
    }

    public void initializeData(View v) {
        selected = false;
        twoPlayer = false;
        next = false;
        checked = false;
        MainActivity.pokemonSelected = false;

        button = (Button) v.findViewById(R.id.playButton);
        playerOneName = (TextView) v.findViewById(R.id.playerOneTextInput);
        playerTwoName = (TextView) v.findViewById(R.id.playerTwoTextInput);
        textLabel = (TextView) v.findViewById(R.id.symbolLabel);

        playerOneImg = (ImageView) v.findViewById(R.id.playerOneImage);
        playerTwoImg = (ImageView) v.findViewById(R.id.playerTwoImage);
        background = (ImageView) v.findViewById(R.id.myPlayerBackground);
        transparentIamge = (ImageView) v.findViewById(R.id.myTransparentImageView);
        dragonImg = (ImageView) v.findViewById(R.id.dragoImg);
        koiImg = (ImageView) v.findViewById(R.id.koiImg);
        pandaImg = (ImageView) v.findViewById(R.id.pandaImg);
        yinImg = (ImageView) v.findViewById(R.id.yinImg);
        bruceImg = (ImageView) v.findViewById(R.id.bruceImg);
        tigerImg = (ImageView) v.findViewById(R.id.tigerImg);
        bamboImg = (ImageView) v.findViewById(R.id.bamboImg);
        xImg = (ImageView) v.findViewById(R.id.xImg);
        oImg = (ImageView) v.findViewById(R.id.oImg);
        background = (ImageView) v.findViewById(R.id.myPlayerBackground);

        imageViewHashMap = new HashMap<>();
        states = new SaveStates(getContext());
        requestManager = new RequestManager(this, playerOneName);
        transparentIamge.setVisibility(View.INVISIBLE);

    }

    public void initHashMap() {
        imageViewHashMap.put(1, dragonImg);
        imageViewHashMap.put(2, koiImg);
        imageViewHashMap.put(3, pandaImg);
        imageViewHashMap.put(4, yinImg);
        imageViewHashMap.put(5, bruceImg);
        imageViewHashMap.put(6, tigerImg);
        imageViewHashMap.put(7, bamboImg);
        imageViewHashMap.put(8, xImg);
        imageViewHashMap.put(9, oImg);

        imageViewHashMap.get(1).setTag(R.drawable.drago);
        imageViewHashMap.get(2).setTag(R.drawable.koi);
        imageViewHashMap.get(3).setTag(R.drawable.panda);
        imageViewHashMap.get(4).setTag(R.drawable.yin);
        imageViewHashMap.get(5).setTag(R.drawable.bruce);
        imageViewHashMap.get(6).setTag(R.drawable.tiger);
        imageViewHashMap.get(7).setTag(R.drawable.bamboo);
        imageViewHashMap.get(8).setTag(R.drawable.x);
        imageViewHashMap.get(9).setTag(R.drawable.o);

    }


    public void buttonClickHandler() {
        for (int i = 1; i < imageViewHashMap.size() + 1; i++) {
            imageViewHashMap.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selected = true;
                    updateLogicAfterClick();
                    buttonWidgetHandler();
                    playerOneImg.setImageDrawable(imageViewHashMap.get(selectedImage).getDrawable());
                }
            });
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.soundPlayer.HandleMenuMusic().stop();
                getFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.my_main_fragment, GameFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });

        transparentIamge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (MainActivity.pokemonSelected){
                getFragmentManager().beginTransaction()
                        .replace(R.id.my_main_fragment, new GameFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack("")
                        .commit();
            }
            }
        });
    }


    private void updateLogicAfterClick() {
        for (int i = 1; i < imageViewHashMap.size() + 1; i++) {
            if (!imageViewHashMap.get(i).isPressed()) {
                imageViewHashMap.get(i).setColorFilter(Color.BLACK);
            } else if (!next) {
                imageViewHashMap.get(i).setColorFilter(getResources().getColor(R.color.red));
                selectedImage = i;
                playerOneImg.setImageDrawable(imageViewHashMap.get(i).getDrawable());
                states.saveString("playerOneName", playerOneName.getText().toString());
                int image = (Integer) imageViewHashMap.get(i).getTag();
                states.saveInt("imageOne", image);
                if (twoPlayer) {
                    next = true;
                    Toast t = Toast.makeText(getContext(), "Symbol for player 1 chosen", Toast.LENGTH_SHORT);
                    t.show();
                }
            } else if (twoPlayer && next) {
                imageViewHashMap.get(i).setColorFilter(Color.BLUE);
                states.saveString("playerTwoName", playerTwoName.getText().toString());
                int image = (Integer) imageViewHashMap.get(i).getTag();
                states.saveInt("imageTwo", image);
                playerTwoImg.setImageDrawable(imageViewHashMap.get(i).getDrawable());
                next = false;
                checked = true;
                Toast t = Toast.makeText(getContext(), "Symbol for player 2 chosen", Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }

    private void buttonWidgetHandler() {
        button.setAlpha(0.5f);
        button.setEnabled(false);
        if (!twoPlayer) {
            if(selected){
                button.setAlpha(0.9f);
                button.setEnabled(true);
            }
        } else {
            if (checked) {
                button.setAlpha(0.9f);
                button.setEnabled(true);
            }
        }
    }

    public void updateUIAfterTextIsChanged() {
        playerOneName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (!playerOneName.getText().toString().equalsIgnoreCase("")) {
                    for (int i = 1; i < imageViewHashMap.size() + 1; i++) {
                        imageViewHashMap.get(i).setAlpha(1f);
                        imageViewHashMap.get(i).setEnabled(true);
                        textLabel.setText("Choose a symbol");
                        requestManager.getJsonObject(getContext());
                    }
                }
                return false;
            }
        });

        playerTwoName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (!playerTwoName.getText().toString().equalsIgnoreCase("")) {
                        for (int i = 1; i < imageViewHashMap.size() + 1; i++) {
                            imageViewHashMap.get(i).setAlpha(1f);
                            imageViewHashMap.get(i).setEnabled(true);
                        }
                    }
                return false;
            }
        });

    }

    public void updateUI(){
        if (MainActivity.twoPlayer){
            twoPlayer = true;
            playerTwoName.setVisibility(View.VISIBLE);
        } else {
            playerTwoName.setVisibility(View.INVISIBLE);
        }
        for (int i = 1; i < imageViewHashMap.size() + 1; i++) {
            imageViewHashMap.get(i).setAlpha(0.5f);
            imageViewHashMap.get(i).setEnabled(false);
            button.setEnabled(false);
            button.setAlpha(0.5f);
        }
    }

    public void updateMusic(){
        if (MainActivity.pokemonSelected){
            MainActivity.soundPlayer.playMusic(R.raw.pokemon_theme);
        } else {
            MainActivity.soundPlayer.playMusic(R.raw.menu_music);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MainActivity.pokemonSelected){
            MainActivity.soundPlayer.playMusic(R.raw.pokemon_theme);
        } else {
            if (!MainActivity.soundPlayer.HandleMenuMusic().isPlaying()) {
                MainActivity.soundPlayer.playMusic(R.raw.menu_music);
            }
        }
    }

    @Override
    public void getString(String string) {
        if (string != null) {
            MainActivity.pokemonSelected = true;
            Picasso.with(getContext()).load(string).resize(1000,1000).into( imageViewHashMap.get(5));
            background.setColorFilter(Color.BLACK);
            transparentIamge.setVisibility(View.VISIBLE);
            textLabel.setText("??????" + "\n A WILD " + playerOneName.getText().toString().toUpperCase() + " APPEARED" + "\n STARTING POKEMON THEME ");
            textLabel.setTextColor(Color.WHITE);
            MainActivity.soundPlayer.HandleMenuMusic().stop();
            updateMusic();
        }
    }
}
