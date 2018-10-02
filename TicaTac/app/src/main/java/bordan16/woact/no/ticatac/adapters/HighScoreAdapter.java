package bordan16.woact.no.ticatac.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import bordan16.woact.no.ticatac.R;
import bordan16.woact.no.ticatac.holders.HighScoreHolder;
import bordan16.woact.no.ticatac.model.Users;
import bordan16.woact.no.ticatac.services.UserServices;

/**
 * Created by daniel on 21/03/2018.
 */

public class HighScoreAdapter extends RecyclerView.Adapter<HighScoreHolder> {

    private ArrayList<Users> list;


    public HighScoreAdapter(ArrayList<Users> arrList) {
        this.list = arrList;
    }

    @Override
    public HighScoreHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.highscore_card, parent, false);

        return new HighScoreHolder(v);
    }

    @Override
    public void onBindViewHolder(HighScoreHolder holder, int position) {
        String name = UserServices.getInstance().getUserList().get(position).getName();
        int score = UserServices.getInstance().getUserList().get(position).getScore();
        holder.updateUI(name, score);
    }

    @Override
    public int getItemCount() {
     int l = list.size();
     return l;

    }
}
