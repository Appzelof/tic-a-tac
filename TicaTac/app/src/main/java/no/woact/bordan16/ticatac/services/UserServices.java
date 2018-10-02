package no.woact.bordan16.ticatac.services;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import no.woact.bordan16.ticatac.activities.MainActivity;
import no.woact.bordan16.ticatac.model.Users;

/**
 * Created by daniel on 21/03/2018.
 */

/**
 * A singleton class that loads our database.
 */
public class UserServices {

    private Users users;
    private String name;
    private int score;
    private int symbol;
    private ArrayList<Users> usersArrayList;

    private static final UserServices ourInstance = new UserServices();

    public static UserServices getInstance() {
        return ourInstance;
    }

    private UserServices() {
    }

    /**
     * A method that get the necessary data from our database so we can populate a new arrayList which we will
     * introduce to the highscore fragment so we can populate the recycler view with player data.
     */
    public void loadDataBase(){
        usersArrayList = new ArrayList<>();
        Cursor res = MainActivity.sqDatabaseHelper.getAllData();
        while (res.moveToNext()) {
            name = res.getString(0);
            score = Integer.parseInt(res.getString(1));
            symbol = Integer.parseInt(res.getString(2));
            users = new Users(name, score, symbol);
            usersArrayList.add(users);
            Collections.sort(usersArrayList, new Comparator< Users>() {
                @Override public int compare(Users u1, Users u2) {
                    return u2.getScore()- u1.getScore();
                }
            });
        }
    }
    public ArrayList<Users> getUserList() {
        loadDataBase();
        return usersArrayList;
    }
}
