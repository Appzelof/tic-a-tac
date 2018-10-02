package bordan16.woact.no.ticatac.engines;

import java.util.ArrayList;
import java.util.HashMap;

import bordan16.woact.no.ticatac.fragments.GameFragment;
import bordan16.woact.no.ticatac.interfaces.GameWin;

/**
 * Created by daniel on 08/03/2018.
 */

public class GameEngine {

    private ArrayList<Integer[]> list;
    private GameWin gameWin;

    public GameEngine(GameFragment gameFragment) {
        gameWin = gameFragment;
        list = new ArrayList<>();
    }

    public Boolean gameWon(HashMap<Integer, Integer> playerOne) {
        Boolean combFound = false;
        Boolean hasFailed;
        for (Integer[] intArr : winnerCombinations()) {
            combFound = false;
            hasFailed = false;
            for (int i : intArr) {
                if (playerOne.get(i) != null) {
                    if (!hasFailed) {
                        combFound = true;
                    }
                } else {
                    combFound = false;
                    hasFailed = true;
                }
            }
            if (combFound) {
                gameWin.gameWin(intArr);
                System.out.println("winner");
                break;
            }
        }

        return combFound;
    }

    public ArrayList<Integer[]> winnerCombinations() {

        Integer[] win0 = {1,2,3};
        Integer[] win1 = {4,5,6};
        Integer[] win2 = {7,8,9};
        Integer[] win3 = {1,4,7};
        Integer[] win4 = {2,5,8};
        Integer[] win5 = {3,6,9};
        Integer[] win6 = {1,5,9};
        Integer[] win7 = {3,5,7};

        list.add(win0);
        list.add(win1);
        list.add(win2);
        list.add(win3);
        list.add(win4);
        list.add(win5);
        list.add(win6);
        list.add(win7);

        return list;
    }
}
