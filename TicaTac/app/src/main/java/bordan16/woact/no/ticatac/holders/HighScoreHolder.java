package bordan16.woact.no.ticatac.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import bordan16.woact.no.ticatac.R;
import bordan16.woact.no.ticatac.model.Users;

/**
 * Created by daniel on 21/03/2018.
 */

public class HighScoreHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private TextView score;

    public HighScoreHolder(View itemView) {
        super(itemView);

        name = (TextView)itemView.findViewById(R.id.scoreNameText);
        score = (TextView)itemView.findViewById(R.id.scoreText);
    }

    public void updateUI(String userName, int userScore) {
        name.setText(userName);
        score.setText(String.valueOf(userScore));

    }
}
