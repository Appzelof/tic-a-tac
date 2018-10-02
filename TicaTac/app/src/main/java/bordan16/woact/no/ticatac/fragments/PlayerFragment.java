package bordan16.woact.no.ticatac.fragments;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
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

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.zip.CheckedOutputStream;

import bordan16.woact.no.ticatac.R;
import bordan16.woact.no.ticatac.activities.MainActivity;
import bordan16.woact.no.ticatac.model.Users;
import bordan16.woact.no.ticatac.preferences.SaveStates;


public class PlayerFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Button button;
    private TextView playerOneName, playerTwoName, textLabel;
    private ImageView background, dragonImg, koiImg, pandaImg, yinImg, bruceImg;
    private ImageView playerOneImg, playerTwoImg;
    private ImageView tigerImg, bamboImg, xImg, oImg;
    private HashMap<Integer, ImageView> imageViewHashMap;
    private Boolean selected;
    private Boolean next;
    private Boolean twoPlayer;
    private Boolean checked;
    private int selectedImage;
    private int selectedImagePlayerTwo;
    private SaveStates states;


    private String mParam1;
    private String mParam2;

    private OnPlayerFragmentInteractionListener mListener;

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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_player, container, false);
        initializeData(v);
        initHashMap();
        buttonClickHandler();
        buttonWidgetHandler();
        updateUI();
        updateUIAfterTextIsChanged();

        return v;
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onPlayerFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPlayerFragmentInteractionListener) {
            mListener = (OnPlayerFragmentInteractionListener) context;
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


    public interface OnPlayerFragmentInteractionListener {
        // TODO: Update argument type and name
        void onPlayerFragmentInteraction();
    }

    public void initializeData(View v) {
        selected = false;
        twoPlayer = false;
        next = false;
        checked = false;

        button = (Button) v.findViewById(R.id.playButton);

        playerOneName = (TextView) v.findViewById(R.id.playerOneTextInput);
        playerTwoName = (TextView) v.findViewById(R.id.playerTwoTextInput);
        playerOneImg = (ImageView) v.findViewById(R.id.playerOneImage);
        playerTwoImg = (ImageView) v.findViewById(R.id.playerTwoImage);


        textLabel = (TextView) v.findViewById(R.id.symbolLabel);
        background = (ImageView) v.findViewById(R.id.myPlayerBackground);
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
            final int finalInt = i;
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
                onButtonPressed();
                getFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.my_main_fragment, GameFragment.newInstance(true))
                        .commit();
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
                selectedImagePlayerTwo = i;
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
                button.setAlpha(1);
                button.setEnabled(true);
            }
        } else {
            if (checked) {
                button.setAlpha(1);
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
}
