package no.woact.bordan16.ticatac.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import no.woact.bordan16.ticatac.R;
import no.woact.bordan16.ticatac.activities.MainActivity;
import no.woact.bordan16.ticatac.adapters.HighScoreAdapter;
import no.woact.bordan16.ticatac.services.UserServices;

/**
 * Class that creates a highScore fragment which includes a recyclerView instead
 * of a fragment list. The recycler is recommended by Google because of its flexibility and
 * convenience.
 */
public class HighScoreFragment extends Fragment {


    private Button button;

    public HighScoreFragment() {
        // Required empty public constructor
    }

    public static HighScoreFragment newInstance() {
        HighScoreFragment fragment = new HighScoreFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_high_score, container, false);
        initializeData(v);
        initializeRecyclerView(v);
        onClickHandler();
        return v;
    }

    /**
     * This method initialize our recyclerView. We set our recyclerView to have a fixedSize for better
     * performance in the app. We also creates a new instance of our Highscore adapter which focus as a main bridge
     * between our data and the recyclerView. That way we can set our recyclerViews adapter. The recyclerView also
     * requires a layout manager for the correct ScrollPosition.
     * We get our data from a Singleton Class UserService.
     * @param v
     */
    public void initializeRecyclerView(View v){
        RecyclerView recyclerView = v.findViewById(R.id.my_recyclerview);
        recyclerView.setHasFixedSize(true); //fixed size for better performance.
        HighScoreAdapter highScoreAdapter = new HighScoreAdapter(UserServices.getInstance().getUserList());
        recyclerView.setAdapter(highScoreAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.scrollToPosition(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void initializeData(View v){
        button = (Button)v.findViewById(R.id.clearButton);
    }

    public void onClickHandler(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.sqDatabaseHelper.deleteTable();
            }
        });
    }
}
