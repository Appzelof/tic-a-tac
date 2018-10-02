package bordan16.woact.no.ticatac.model;

import android.graphics.drawable.Drawable;

/**
 * Created by daniel on 21/03/2018.
 */

public class Users {

    private String name;
    private int score;
    private Drawable image;

    public Users(String name, int score, Drawable image) {
        this.name = name;
        this.score = score;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
}
