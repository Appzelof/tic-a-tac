package bordan16.woact.no.ticatac.services;

import java.util.ArrayList;

import bordan16.woact.no.ticatac.model.Users;

/**
 * Created by daniel on 21/03/2018.
 */

public class UserServices {

    private ArrayList<Users> userList;

    private static final UserServices ourInstance = new UserServices();

    public static UserServices getInstance() {
        return ourInstance;
    }

    private UserServices() {


    }

    public ArrayList<Users> getUserList() {
        userList = new ArrayList<>();


        return userList;
    }
}
