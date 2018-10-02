package no.woact.bordan16.ticatac.model;

/**
 * Created by daniel on 21/03/2018.
 */

public class Users {

    private String name;
    private int score;
    private int image;

    public Users(String name, int score, int image) {
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

    public int getImage() {
        return image;
    }

}
