package no.woact.bordan16.ticatac.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import no.woact.bordan16.ticatac.R;
import no.woact.bordan16.ticatac.activities.MainActivity;
import no.woact.bordan16.ticatac.fragments.OptionsFragment;
import no.woact.bordan16.ticatac.holders.HighScoreHolder;
import no.woact.bordan16.ticatac.model.Users;
import no.woact.bordan16.ticatac.services.UserServices;

/**
 * Created by daniel on 21/03/2018.
 */

/**
 * Class that creates an adapter for our recyclerView.
 */
public class HighScoreAdapter extends RecyclerView.Adapter<HighScoreHolder> {

    private ArrayList<Users> usersArrayList;
    public HighScoreAdapter(ArrayList<Users> arrList) {
        this.usersArrayList = arrList;
    }

    /**
     * Method that creates a new view that inflates a layout file.
     * @param parent
     * @param viewType
     * @return ViewHolder
     */
    @Override
    public HighScoreHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //we create a new view so we can inflate our score_card.
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.highscore_card, parent, false);

        return new HighScoreHolder(v);
    }

    /**
     * Method that binds the components to the right UI elements.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(HighScoreHolder holder, int position) {
        String name = UserServices.getInstance().getUserList().get(position).getName();
        int score = UserServices.getInstance().getUserList().get(position).getScore();
        int image = UserServices.getInstance().getUserList().get(position).getImage();
        holder.updateUI(name, score, image);
    }

    /**
     * Method that defines how many positions/index our recyclerView should have.
     * @return int
     */
    @Override
    public int getItemCount() {
     int l = usersArrayList.size();
     return l;

    }
}
