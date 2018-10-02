package no.woact.bordan16.ticatac.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import no.woact.bordan16.ticatac.R;

/**
 * Created by daniel on 21/03/2018.
 */

/**
 * Class that initialize our UI elements in recyckerView.
 */
public class HighScoreHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private TextView score;
    private ImageView image;

    public HighScoreHolder(View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.scoreNameText);
        score = (TextView)itemView.findViewById(R.id.scoreText);
        image = (ImageView)itemView.findViewById(R.id.winnerImage);
    }

    /**
     * Method that updates ui elements in recyclerView.
     * @param userName playerName
     * @param userScore playerScore
     * @param img playerSymbol
     */
    public void updateUI(String userName, int userScore, int img) {
        name.setText(userName);
        score.setText(String.valueOf(userScore));
        image.setImageResource(img);
    }
}
